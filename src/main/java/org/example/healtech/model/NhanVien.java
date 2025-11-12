//package org.example.healtech.model;
//
//public class NhanVien {
//    private int maNhanVien;
//    private String hoTen;
//    private String email;
//    private String matKhau;
//    private String chucVu;
//    private String chuyenKhoa;
//    private String soDienThoai;
//
//    public NhanVien() {
//    }
//
//    public NhanVien(String hoTen, String email, String matKhau) {
//        this.hoTen = hoTen;
//        this.email = email;
//        this.matKhau = matKhau;
//        this.chucVu = "Nhân viên";
//        this.chuyenKhoa = "Chưa xác định";
//        this.soDienThoai = "";
//    }
//
//    // ✅ Constructor đầy đủ dùng cho DAO
//    public NhanVien(int maNhanVien, String hoTen, String chuyenKhoa, String chucVu, String soDienThoai, String email) {
//        this.maNhanVien = maNhanVien;
//        this.hoTen = hoTen;
//        this.chuyenKhoa = chuyenKhoa;
//        this.chucVu = chucVu;
//        this.soDienThoai = soDienThoai;
//        this.email = email;
//    }
//
//    public int getMaNhanVien() { return maNhanVien; }
//    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }
//
//    public String getHoTen() { return hoTen; }
//    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getMatKhau() { return matKhau; }
//    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
//
//    public String getChucVu() { return chucVu; }
//    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
//
//    public String getChuyenKhoa() { return chuyenKhoa; }
//    public void setChuyenKhoa(String chuyenKhoa) { this.chuyenKhoa = chuyenKhoa; }
//
//    public String getSoDienThoai() { return soDienThoai; }
//    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
//}


package org.example.healtech.model;

import javafx.beans.property.*;


public class NhanVien {


    private final IntegerProperty maNhanVien = new SimpleIntegerProperty();
    private final StringProperty hoTen = new SimpleStringProperty();
    private final StringProperty chuyenKhoa = new SimpleStringProperty();
    private final StringProperty chucVu = new SimpleStringProperty();
    private final StringProperty soDienThoai = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty matKhau = new SimpleStringProperty();

    public NhanVien() {}

    public NhanVien(int maNhanVien, String hoTen, String chuyenKhoa,
                    String chucVu, String soDienThoai, String email, String matKhau) {
        this.maNhanVien.set(maNhanVien);
        this.hoTen.set(hoTen);
        this.chuyenKhoa.set(chuyenKhoa);
        this.chucVu.set(chucVu);
        this.soDienThoai.set(soDienThoai);
        this.email.set(email);
        this.matKhau.set(matKhau);
    }


    public NhanVien(String hoTen, String email, String matKhau) {
        this.hoTen.set(hoTen);
        this.email.set(email);
        this.matKhau.set(matKhau);
    }


    public int getMaNhanVien() {
        return maNhanVien.get();
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien.set(maNhanVien);
    }

    public String getHoTen() {
        return hoTen.get();
    }

    public void setHoTen(String hoTen) {
        this.hoTen.set(hoTen);
    }

    public String getChuyenKhoa() {
        return chuyenKhoa.get();
    }

    public void setChuyenKhoa(String chuyenKhoa) {
        this.chuyenKhoa.set(chuyenKhoa);
    }

    public String getChucVu() {
        return chucVu.get();
    }

    public void setChucVu(String chucVu) {
        this.chucVu.set(chucVu);
    }

    public String getSoDienThoai() {
        return soDienThoai.get();
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai.set(soDienThoai);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getMatKhau() {
        return matKhau.get();
    }

    public void setMatKhau(String matKhau) {
        this.matKhau.set(matKhau);
    }


    public IntegerProperty maNhanVienProperty() {
        return maNhanVien;
    }

    public StringProperty hoTenProperty() {
        return hoTen;
    }

    public StringProperty chuyenKhoaProperty() {
        return chuyenKhoa;
    }

    public StringProperty chucVuProperty() {
        return chucVu;
    }

    public StringProperty soDienThoaiProperty() {
        return soDienThoai;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty matKhauProperty() {
        return matKhau;
    }


    @Override
    public String toString() {
        return hoTen.get() + " (" + (chucVu.get() != null ? chucVu.get() : "Chưa xác định") + ")";
    }
}