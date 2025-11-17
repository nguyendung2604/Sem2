package org.example.healtech.model;

public class ChiTietDichVu {
    private String tenDichVu;
    private double phiDichVu;

    // Constructor (Bạn có thể tạo một constructor đầy đủ hơn nếu cần)
    public ChiTietDichVu(String tenDichVu, double phiDichVu) {
        this.tenDichVu = tenDichVu;
        this.phiDichVu = phiDichVu;
    }

    // Getters
    public String getTenDichVu() {
        return tenDichVu;
    }

    public double getPhiDichVu() {
        return phiDichVu;
    }

    // Setters (Nếu bạn cần)
}