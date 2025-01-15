package controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import dao.DBConnectionPool;
import dao.UserDAO;

/**
 * Servlet implementation class UploadServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 *2,// Ngưỡng kích thước file nhỏ hơn 2MB
	maxFileSize = 1024 * 1024 * 10, // Kích thước file tối đa là 10MB
	maxRequestSize = 1024 * 1024 *50// Kích thước yêu cầu tối đa là 50MB
)
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("upload.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			HttpSession session = request.getSession();
            // Lấy đối tượng người dùng từ session
			User user = (User) session.getAttribute("user");
            // Lấy phần file được tải lên từ yêu cầu
			Part part = request.getPart("photo");
			
            // Lấy đường dẫn đến thư mục lưu trữ hình ảnh
			String realPath = request.getServletContext().getRealPath("/image/avatars");
			String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
			
            // Kiểm tra xem thư mục đã tồn tại chưa, nếu chưa thì tạo mới
			if(!Files.exists(Path.of(realPath))) {
				Files.createDirectory(Path.of(realPath));
			}
			
			
			String picPath = realPath + "/" + filename;
			part.write(picPath);            // Ghi file hình ảnh vào thư mục đã chỉ định

			
			UserDAO dao = new UserDAO(DBConnectionPool.getDataSource().getConnection());
			
			
			dao.changeImg(user.getId(), "image/avatars/" + filename);             // Cập nhật đường dẫn hình ảnh mới cho người dùng trong cơ sở dữ liệu
			user.setImg("image/avatars/" + filename);            // Cập nhật đối tượng người dùng

            // Cập nhật lại đối tượng người dùng trong phiên
			session.setAttribute("user", user);
			
			response.sendRedirect(request.getContextPath() + "/Homepage");
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}

}
