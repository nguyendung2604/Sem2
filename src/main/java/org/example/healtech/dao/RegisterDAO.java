package org.example.healtech.dao;

import org.example.healtech.model.NhanVien;
import org.example.healtech.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAO {

    // 🔹 Kiểm tra trùng Email hoặc SĐT
    private boolean isDuplicate(String email, String phone) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE Email = ? OR SoDienThoai = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Lỗi khi kiểm tra trùng dữ liệu: " + e.getMessage());
        }
        return false;
    }

    // 🔹 Thực hiện đăng ký nhân viên
    public boolean registerUser(NhanVien nv) {
        // 1️⃣ Kiểm tra dữ liệu trùng
        if (isDuplicate(nv.getEmail(), nv.getSoDienThoai())) {
            System.err.println("❌ Email hoặc Số điện thoại đã tồn tại!");
            return false;
        }

        // 2️⃣ Câu lệnh chèn dữ liệu
        String sql = "INSERT INTO NhanVien (HoTen, Email, MatKhau, ChucVu, ChuyenKhoa, SoDienThoai) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getEmail());
            stmt.setString(3, nv.getMatKhau()); // sau này có thể thay bằng BCrypt hash
            stmt.setString(4, nv.getChucVu());
            stmt.setString(5, nv.getChuyenKhoa());
            stmt.setString(6, nv.getSoDienThoai());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi đăng ký: " + e.getMessage());
            return false;
        }
    }
}
