package org.example.healtech.model;

public class ChiTietThuoc {

    // Thuộc tính này bị thiếu trong DAO, nhưng cần để chạy
    private int maThuoc;

    private String tenThuoc;
    private String donViTinh;
    private int soLuong;
    private double giaBan;
    private double thanhTien;

    // Constructor mà DAO của bạn đang gọi
    public ChiTietThuoc(String tenThuoc, String donViTinh, int soLuong, double giaBan) {
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = soLuong * giaBan; // Tự động tính thành tiền
    }

    // Constructor đầy đủ (Để sửa DAO sau này)
    public ChiTietThuoc(int maThuoc, String tenThuoc, String donViTinh, int soLuong, double giaBan) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = soLuong * giaBan;
    }

    // --- CÁC HÀM GETTER QUAN TRỌNG ---
    // (Hãy chắc chắn rằng bạn có đủ 5 hàm này)

    public String getTenThuoc() {
        return tenThuoc;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    // Rất có thể bạn đang thiếu hàm này!
    public double getThanhTien() {
        return thanhTien;
    }

    public int getMaThuoc() {
        return maThuoc;
    }
}