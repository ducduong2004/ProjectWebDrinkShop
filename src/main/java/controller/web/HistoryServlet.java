package controller.web;

import java.io.IOException;
import java.util.List;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Product;
import models.User;

/**
 * Servlet implementation class HistoryServlet
 */
@WebServlet("/secure/History")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		ProductDAO productService = new ProductDAO();
		List<Product> allproducts = productService.getAllUserProducts(user.getId());
		getServletContext().setAttribute("allproducts", allproducts);
		
		List<Product> onWayProducts = productService.getAllUserProducts(user.getId());
		getServletContext().setAttribute("onWayProducts", onWayProducts);
		
		List<Product> shippedProducts = productService.getAllUserProducts(user.getId());
		getServletContext().setAttribute("onWayProducts", shippedProducts);

		response.sendRedirect(request.getContextPath() + "/secure/historyProduct.jsp");
	}


}
