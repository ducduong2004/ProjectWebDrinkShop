package controller.load;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;

/**
 * Servlet implementation class UpdateQuantityServlet
 */
@WebServlet("/updateQuantity")
public class UpdateQuantityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdstr = request.getParameter("id");
        int productId = Integer.parseInt(productIdstr);
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        System.out.println(productId+ " + ");
        System.out.println(quantity + " + ");
        
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null && cart.getItems().containsKey(productId)) {
            cart.updateQuantityCart(productId, quantity);

            // Tính toán lại tổng
            double subtotal = cart.calculateTotal();
            double shipping = 10.00; // Ví dụ phí ship cố định
            double total = subtotal + shipping;

            // Gửi JSON phản hồi
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"subtotal\": " + subtotal + ", \"shipping\": " + shipping + ", \"total\": " + total + "}");
        } else {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Sản phẩm không tồn tại trong giỏ hàng.\"}");
        }
    }
}
