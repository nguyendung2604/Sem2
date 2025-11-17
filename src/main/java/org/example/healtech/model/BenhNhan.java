package org.example.healtech.model;

import java.time.LocalDate;

public class BenhNhan {
    private int maBenhNhan;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private String tienSuBenh;
    private LocalDate ngayTao;

    // ===== Constructors =====
    public BenhNhan() {}

    public BenhNhan(String hoTen, LocalDate ngaySinh, String gioiTinh,
                    String diaChi, String soDienThoai, String tienSuBenh) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.tienSuBenh = tienSuBenh;
    }

    public BenhNhan(int maBenhNhan, String hoTen, LocalDate ngaySinh, String gioiTinh,
                    String diaChi, String soDienThoai, String tienSuBenh, LocalDate ngayTao) {
        this.maBenhNhan = maBenhNhan;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.tienSuBenh = tienSuBenh;
        this.ngayTao = ngayTao;
    }

    // ===== Getters & Setters =====
    public int getMaBenhNhan() { return maBenhNhan; }
    public void setMaBenhNhan(int maBenhNhan) { this.maBenhNhan = maBenhNhan; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getTienSuBenh() { return tienSuBenh; }
    public void setTienSuBenh(String tienSuBenh) { this.tienSuBenh = tienSuBenh; }

    public LocalDate getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDate ngayTao) { this.ngayTao = ngayTao; }

    @Override
    public String toString() {
        return hoTen + " - " + soDienThoai;
    }
}