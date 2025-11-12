package org.example.healtech.dao;

import org.example.healtech.model.NhanVien;
import org.example.healtech.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NhanVienDAO {


    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("ChuyenKhoa"),
                        rs.getString("ChucVu"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("MatKhau")
                );
                list.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean insert(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (HoTen, ChuyenKhoa, ChucVu, SoDienThoai, Email, MatKhau) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChuyenKhoa());
            ps.setString(3, nv.getChucVu());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getMatKhau());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException ex) {
            System.err.println("Lỗi: Số điện thoại hoặc email đã tồn tại!");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(NhanVien nv) {
        String sql = "UPDATE NhanVien SET HoTen=?, ChuyenKhoa=?, ChucVu=?, SoDienThoai=?, Email=?, MatKhau=? WHERE MaNhanVien=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChuyenKhoa());
            ps.setString(3, nv.getChucVu());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getMatKhau());
            ps.setInt(7, nv.getMaNhanVien());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM NhanVien WHERE MaNhanVien=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<NhanVien> searchByName(String keyword) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE HoTen LIKE ? OR SoDienThoai LIKE ? OR Email LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new NhanVien(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("ChuyenKhoa"),
                        rs.getString("ChucVu"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("MatKhau")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}