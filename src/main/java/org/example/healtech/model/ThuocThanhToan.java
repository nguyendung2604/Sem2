// Đặt file này trong một package model, ví dụ: org.example.healtech.model
package org.example.healtech.model;

public class ThuocThanhToan {

    // Các thuộc tính này PHẢI KHỚP với các cột bạn cần
    private String tenThuoc;
    private String donVi;
    private int soLuong;
    private double giaBan;
    private double thanhTien;

    // Constructor
    public ThuocThanhToan(String tenThuoc, String donVi, int soLuong, double giaBan, double thanhTien) {
        this.tenThuoc = tenThuoc;
        this.donVi = donVi;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
    }

    // Các hàm Getters
    // **Tên hàm getter RẤT QUAN TRỌNG, nó phải khớp với PropertyValueFactory**
    public String getTenThuoc() {
        return tenThuoc;
    }

    public String getDonVi() {
        return donVi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    // Bạn có thể thêm các hàm Setters nếu cần, nhưng cho hiển thị thì chỉ cần getter
}