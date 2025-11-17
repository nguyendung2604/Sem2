package org.example.healtech.dao;

import org.example.healtech.model.Thuoc;
import org.example.healtech.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThuocDAO {
    private Connection conn;

    public ThuocDAO() {
        this.conn = DBConnection.getConnection();
    }

    // Lấy tất cả thuốc - SỬA LẠI CÂU TRUY VẤN
    public List<Thuoc> getAllThuoc() {
        List<Thuoc> list = new ArrayList<>();
        // CHỈ SELECT CÁC CỘT CÓ TRONG DATABASE
        String query = "SELECT MaThuoc, TenThuoc, DonViTinh, SoLuongTon, GiaBan FROM Thuoc ORDER BY MaThuoc ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Thuoc thuoc = mapResultSetToThuoc(rs);
                list.add(thuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy danh sách thuốc: " + e.getMessage());
        }
        return list;
    }

    // Thêm thuốc mới - SỬA LẠI CÂU TRUY VẤN
    public boolean addThuoc(Thuoc thuoc) {
        // LOẠI BỎ NgayTao khỏi câu lệnh INSERT
        String query = "INSERT INTO Thuoc (TenThuoc, DonViTinh, SoLuongTon, GiaBan) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, thuoc.getTenThuoc());
            pstmt.setString(2, thuoc.getDonViTinh());
            pstmt.setInt(3, thuoc.getSoLuongTon());
            pstmt.setDouble(4, thuoc.getGiaBan());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        thuoc.setMaThuoc(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi thêm thuốc: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thuốc
    public boolean updateThuoc(Thuoc thuoc) {
        String query = "UPDATE Thuoc SET TenThuoc=?, DonViTinh=?, SoLuongTon=?, GiaBan=? WHERE MaThuoc=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, thuoc.getTenThuoc());
            pstmt.setString(2, thuoc.getDonViTinh());
            pstmt.setInt(3, thuoc.getSoLuongTon());
            pstmt.setDouble(4, thuoc.getGiaBan());
            pstmt.setInt(5, thuoc.getMaThuoc());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi cập nhật thuốc: " + e.getMessage());
            return false;
        }
    }

    // Xóa thuốc
    public boolean deleteThuoc(int maThuoc) {
        String query = "DELETE FROM Thuoc WHERE MaThuoc=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maThuoc);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa thuốc: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm thuốc - SỬA LẠI CÂU TRUY VẤN
    public List<Thuoc> timKiemThuoc(String keyword) {
        List<Thuoc> list = new ArrayList<>();
        String query = "SELECT MaThuoc, TenThuoc, DonViTinh, SoLuongTon, GiaBan FROM Thuoc WHERE TenThuoc LIKE ? OR MaThuoc = ? ORDER BY MaThuoc ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + keyword + "%");

            try {
                pstmt.setInt(2, Integer.parseInt(keyword));
            } catch (NumberFormatException e) {
                pstmt.setInt(2, -1);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Thuoc thuoc = mapResultSetToThuoc(rs);
                list.add(thuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi tìm kiếm thuốc: " + e.getMessage());
        }
        return list;
    }

    // Helper method - SỬA LẠI ĐỂ KHÔNG ĐỌC NgayTao
    private Thuoc mapResultSetToThuoc(ResultSet rs) throws SQLException {
        Thuoc thuoc = new Thuoc();
        thuoc.setMaThuoc(rs.getInt("MaThuoc"));
        thuoc.setTenThuoc(rs.getString("TenThuoc"));
        thuoc.setDonViTinh(rs.getString("DonViTinh"));
        thuoc.setSoLuongTon(rs.getInt("SoLuongTon"));
        thuoc.setGiaBan(rs.getDouble("GiaBan"));

        thuoc.setNgayTao(LocalDate.now());

        return thuoc;
    }
}