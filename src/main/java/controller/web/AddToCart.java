package controller.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.CartDAO;
import dao.CartItemDAO;
import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;
import models.Product;
import models.User;

/**
 * Servlet implementation class AddToCart
 */
// xu li them vao gio hang
@WebServlet("/addToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();

		int quantity = 1;
		int productid;

		User user = (User) session.getAttribute("user");

		CartItemDAO cartItemDAO = new CartItemDAO();
		ProductDAO productDAO = new ProductDAO();

		try {
			Connection connection = DBConnectionPool.getDataSource().getConnection();
			if(req.getParameter("productId") != null) {
				productid = Integer.parseInt(req.getParameter("productId"));
				Product product = productDAO.getProductById(productid);
				if(product != null) {
					CartDAO cartDAO = new CartDAO(connection);
					Cart cart = cartDAO.getCartByUserId(user.getId());
					if(cart == null) {
						cart = new Cart(1, user.getId());
						cartDAO.createCart(cart);
						session.setAttribute("cart", cartDAO.getCartByUserId(user.getId()));
					}

					quantity = cartItemDAO.getQuantity(cart.getCartId(), productid) + 1;

					// Kiểm tra các tham số đầu vào
					if (quantity <= 0) {
						throw new IllegalArgumentException("Quantity must be greater than 0");
					}

					Product item = cart.lookup(product.getId());
					if(item == null && quantity > 0) {
						cartItemDAO.addCartItem(cart, product, quantity);
					}

					if(item != null && quantity > 0) {
						cartItemDAO.setQuantity(cart, product, quantity);
					}

				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		res.sendRedirect(req.getContextPath() + "/secure/cart");
	}



}
