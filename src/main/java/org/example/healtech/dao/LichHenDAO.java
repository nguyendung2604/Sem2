package org.example.healtech.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.healtech.model.LichHen;
import org.example.healtech.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class LichHenDAO {

    // Lấy toàn bộ danh sách lịch hẹn
    public static ObservableList<LichHen> getAllLichHen() {
        ObservableList<LichHen> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM LichHen";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                LocalDateTime thoiGianHen = rs.getTimestamp("ThoiGianHen").toLocalDateTime();

                LichHen lichHen = new LichHen(
                        rs.getInt("MaLichHen"),
                        rs.getInt("MaBenhNhan"),
                        rs.getInt("MaBacSi"),
                        thoiGianHen,
                        rs.getString("LyDoKham"),
                        rs.getString("TrangThai")
                );

                list.add(lichHen);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy danh sách lịch hẹn: " + e.getMessage());
        }

        return list;
    }

    // Thêm mới lịch hẹn
    public static boolean addLichHen(LichHen lichHen) {
        String query = "INSERT INTO LichHen (MaBenhNhan, MaBacSi, ThoiGianHen, LyDoKham, TrangThai) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, lichHen.getMaBenhNhan());
            pstmt.setInt(2, lichHen.getMaBacSi());
            pstmt.setTimestamp(3, Timestamp.valueOf(lichHen.getThoiGianHen()));
            pstmt.setString(4, lichHen.getLyDoKham());
            pstmt.setString(5, lichHen.getTrangThai());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi thêm lịch hẹn: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật trạng thái lịch hẹn
    public static boolean updateTrangThai(int maLichHen, String trangThai) {
        String query = "UPDATE LichHen SET TrangThai = ? WHERE MaLichHen = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, trangThai);
            pstmt.setInt(2, maLichHen);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi cập nhật trạng thái: " + e.getMessage());
            return false;
        }
    }

    // Xóa lịch hẹn
    public static boolean deleteLichHen(int maLichHen) {
        String query = "DELETE FROM LichHen WHERE MaLichHen = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, maLichHen);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa lịch hẹn: " + e.getMessage());
            return false;
        }
    }
}
