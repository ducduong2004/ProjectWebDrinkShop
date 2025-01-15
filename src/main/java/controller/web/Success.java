package controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;
import models.CartItem;
import models.Product;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import dao.CartDAO;
import dao.DBConnectionPool;
import dao.OrderDAO;
import dao.ProductDAO;

/**
 * Servlet implementation class Success
 */
@WebServlet("/Success")
public class Success extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
        try (Connection connection = DBConnectionPool.getDataSource().getConnection()) {
            // Instantiate DAOs
            CartDAO cartDAO = new CartDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);
            OrderDAO orderDAO = new OrderDAO(connection);

            // lấy userID từ session
            int userId = (int) request.getSession().getAttribute("userId");
            // lấy card 
            Cart cart = cartDAO.getCartByUserId(userId);
            //danh sách cart
            List<CartItem> cartItems = cartDAO.getCartItems(cart.getCartId());

            // Create a new order and update stock for each product
            int orderId = orderDAO.createOrder(userId, cart.getTotalPrice(), "Payed", user.getAddress());
            // đưa danh sách item trong cart vào danh sach order
            for (CartItem item : cartItems) {
                Product product = item.getProduct();
                product.reduceStock(item.getQuantity());
                productDAO.updateProductStock(product);

                // thêm vào order
                orderDAO.addOrderItem(orderId, item.getProductId(), item.getQuantity(), product.getPrice());
            }
            
            // làm trống giỏ hàng
            cartDAO.clearCart(cart.getCartId());
            cart.clearCart();
            
            
            // Forward to the success JSP to display payment details
            response.sendRedirect(request.getContextPath() + "/template/static/successweb.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/template/static/error-payment.jsp");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
