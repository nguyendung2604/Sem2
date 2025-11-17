package org.example.healtech.model;

import java.time.LocalDate;

/**
 * Model này tương ứng với bảng 'HoaDon' trong CSDL.
 */
public class HoaDon {

    private int maHoaDon;
    private int maPhieuKham;
    private int maBenhNhan;
    private LocalDate ngayThanhToan;
    private double tongTien;
    private String trangThai;

    // Constructor để tạo mới (khi lưu xuống CSDL)
    public HoaDon(int maPhieuKham, int maBenhNhan, LocalDate ngayThanhToan, double tongTien, String trangThai) {
        this.maPhieuKham = maPhieuKham;
        this.maBenhNhan = maBenhNhan;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // (Bạn có thể thêm constructor đầy đủ nếu cần đọc Hóa Đơn từ CSDL lên)

    // --- Getters ---
    public int getMaPhieuKham() { return maPhieuKham; }
    public int getMaBenhNhan() { return maBenhNhan; }
    public LocalDate getNgayThanhToan() { return ngayThanhToan; }
    public double getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
    // ... (Thêm các getters/setters khác nếu cần)
}