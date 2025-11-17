package org.example.healtech.dao;

import org.example.healtech.model.BenhNhan;
import org.example.healtech.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BenhNhanDAO {
    private Connection conn;

    public BenhNhanDAO() {
        this.conn = DBConnection.getConnection();
    }

    // Lấy tất cả bệnh nhân
    public List<BenhNhan> getAllBenhNhan() {
        List<BenhNhan> list = new ArrayList<>();
        String query = "SELECT * FROM BenhNhan ORDER BY MaBenhNhan ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                BenhNhan bn = mapResultSetToBenhNhan(rs);
                list.add(bn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy danh sách bệnh nhân: " + e.getMessage());
        }
        return list;
    }

    // Thêm bệnh nhân mới
    public boolean addBenhNhan(BenhNhan benhNhan) {
        String query = "INSERT INTO BenhNhan (HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, TienSuBenh) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, benhNhan.getHoTen());
            pstmt.setDate(2, Date.valueOf(benhNhan.getNgaySinh()));
            pstmt.setString(3, benhNhan.getGioiTinh());
            pstmt.setString(4, benhNhan.getDiaChi());
            pstmt.setString(5, benhNhan.getSoDienThoai());
            pstmt.setString(6, benhNhan.getTienSuBenh());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Lấy mã bệnh nhân vừa tạo
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        benhNhan.setMaBenhNhan(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi thêm bệnh nhân: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật bệnh nhân
    public boolean updateBenhNhan(BenhNhan benhNhan) {
        String query = "UPDATE BenhNhan SET HoTen=?, NgaySinh=?, GioiTinh=?, DiaChi=?, SoDienThoai=?, TienSuBenh=? WHERE MaBenhNhan=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, benhNhan.getHoTen());
            pstmt.setDate(2, Date.valueOf(benhNhan.getNgaySinh()));
            pstmt.setString(3, benhNhan.getGioiTinh());
            pstmt.setString(4, benhNhan.getDiaChi());
            pstmt.setString(5, benhNhan.getSoDienThoai());
            pstmt.setString(6, benhNhan.getTienSuBenh());
            pstmt.setInt(7, benhNhan.getMaBenhNhan());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi cập nhật bệnh nhân: " + e.getMessage());
            return false;
        }
    }

    // Xóa bệnh nhân
    public boolean deleteBenhNhan(int maBenhNhan) {
        String query = "DELETE FROM BenhNhan WHERE MaBenhNhan=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maBenhNhan);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa bệnh nhân: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm bệnh nhân theo mã, tên hoặc SĐT
    public List<BenhNhan> timKiemBenhNhan(String keyword) {
        List<BenhNhan> list = new ArrayList<>();
        String query = "SELECT * FROM BenhNhan WHERE HoTen LIKE ? OR SoDienThoai LIKE ? OR MaBenhNhan = ? ORDER BY MaBenhNhan ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            // Thử parse keyword thành số để tìm theo mã
            try {
                pstmt.setInt(3, Integer.parseInt(keyword));
            } catch (NumberFormatException e) {
                pstmt.setInt(3, -1); // Nếu không phải số, tìm với mã = -1 (sẽ không có kết quả)
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BenhNhan bn = mapResultSetToBenhNhan(rs);
                list.add(bn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi tìm kiếm bệnh nhân: " + e.getMessage());
        }
        return list;
    }

    // Helper method để map ResultSet sang BenhNhan
    private BenhNhan mapResultSetToBenhNhan(ResultSet rs) throws SQLException {
        BenhNhan bn = new BenhNhan();
        bn.setMaBenhNhan(rs.getInt("MaBenhNhan"));
        bn.setHoTen(rs.getString("HoTen"));

        Date ngaySinh = rs.getDate("NgaySinh");
        if (ngaySinh != null) {
            bn.setNgaySinh(ngaySinh.toLocalDate());
        }

        bn.setGioiTinh(rs.getString("GioiTinh"));
        bn.setDiaChi(rs.getString("DiaChi"));
        bn.setSoDienThoai(rs.getString("SoDienThoai"));
        bn.setTienSuBenh(rs.getString("TienSuBenh"));

        Date ngayTao = rs.getDate("NgayTao");
        if (ngayTao != null) {
            bn.setNgayTao(ngayTao.toLocalDate());
        }

        return bn;
    }
}