package utool;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/changeLanguage")
public class LanguageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy tham số ngôn ngữ từ URL (lang=vi hoặc lang=en)
		String lang = request.getParameter("lang");

		// Nếu không có tham số lang, mặc định sẽ là "vi" (Tiếng Việt)
		if (lang == null || lang.isEmpty()) {
			lang = "vi"; 
		}

		Locale locale;
		if ("en".equals(lang)) {
			locale = Locale.forLanguageTag("en"); // Locale cho tiếng Anh
		} else {
			locale = Locale.forLanguageTag("vi"); // Locale cho tiếng Việt mặc định
		}
		
		
		// Lưu Locale vào session
		HttpSession session = request.getSession();
		session.setAttribute("locale", locale);

		
		// Tạo ResourceBundle với Locale vừa thiết lập
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		session.setAttribute("bundle", bundle); // Lưu ResourceBundle vào session

		// Quay lại trang trước hoặc trang chính 
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer != null ? referer : request.getContextPath() + "/homepage");
	}
}
