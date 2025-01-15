package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import models.Product;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection connection) {
        this.conn = connection;
    }

    // Tạo đơn hàng mới
    public int createOrder(int userId, double totalPrice, String status, String address) {
        String sql = "INSERT INTO Orders (userId, totalAmount, status, shippingAddress) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, totalPrice);
            stmt.setString(3, status);
            stmt.setString(4, address);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
            	System.out.println(rs.getInt(1));
            	return rs.getInt(1); // Trả về orderId
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Lỗi
    }

    // Thêm item vào đơn hàng
    public void addOrderItem(int orderId, int productId, int quantity, double price) {
        String sql = "INSERT INTO OrderItems (orderId, productId, quantity, price) VALUES (?, ?, ?, ?)";
        System.out.println(productId);
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //kiem tra có product không
    public boolean productExists(int productId) {
        String sql = "SELECT COUNT(*) FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if product exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Product does not exist
    }
    
}