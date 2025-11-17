package org.example.healtech.model;

import java.time.LocalDate;

public class Thuoc {
    private int maThuoc;
    private String tenThuoc;
    private String donViTinh;
    private int soLuongTon;
    private double giaBan;
    private LocalDate ngayTao;

    // ===== CONSTRUCTORS =====
    public Thuoc() {
        this.ngayTao = LocalDate.now(); // Mặc định là ngày hiện tại
    }

    public Thuoc(String tenThuoc, String donViTinh, int soLuongTon, double giaBan) {
        this();
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
    }

    public Thuoc(int maThuoc, String tenThuoc, String donViTinh, int soLuongTon, double giaBan, LocalDate ngayTao) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
        this.ngayTao = ngayTao;
    }

    // ===== GETTERS & SETTERS =====
    public int getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(int maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        if (tenThuoc == null || tenThuoc.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thuốc không được để trống");
        }
        this.tenThuoc = tenThuoc.trim();
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        if (donViTinh == null || donViTinh.trim().isEmpty()) {
            throw new IllegalArgumentException("Đơn vị tính không được để trống");
        }
        this.donViTinh = donViTinh.trim();
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        if (soLuongTon < 0) {
            throw new IllegalArgumentException("Số lượng tồn không thể âm");
        }
        this.soLuongTon = soLuongTon;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        if (giaBan < 0) {
            throw new IllegalArgumentException("Giá bán không thể âm");
        }
        this.giaBan = giaBan;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        if (ngayTao != null && ngayTao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày tạo không thể ở tương lai");
        }
        this.ngayTao = ngayTao;
    }

    // ===== BUSINESS METHODS =====

    /**
     * Kiểm tra thuốc còn hàng hay không
     */
    public boolean isConHang() {
        return soLuongTon > 0;
    }

    /**
     * Kiểm tra thuốc sắp hết hàng (dưới 10 sản phẩm)
     */
    public boolean isSapHetHang() {
        return soLuongTon > 0 && soLuongTon < 10;
    }

    /**
     * Kiểm tra thuốc đã hết hàng
     */
    public boolean isHetHang() {
        return soLuongTon == 0;
    }

    /**
     * Cập nhật số lượng tồn kho khi nhập hàng
     */
    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap <= 0) {
            throw new IllegalArgumentException("Số lượng nhập phải lớn hơn 0");
        }
        this.soLuongTon += soLuongNhap;
    }

    /**
     * Cập nhật số lượng tồn kho khi xuất hàng
     */
    public void xuatHang(int soLuongXuat) {
        if (soLuongXuat <= 0) {
            throw new IllegalArgumentException("Số lượng xuất phải lớn hơn 0");
        }
        if (soLuongXuat > soLuongTon) {
            throw new IllegalArgumentException("Số lượng xuất vượt quá tồn kho. Tồn kho hiện tại: " + soLuongTon);
        }
        this.soLuongTon -= soLuongXuat;
    }

    /**
     * Tính tổng giá trị tồn kho
     */
    public double tinhTongGiaTriTonKho() {
        return soLuongTon * giaBan;
    }

    /**
     * Kiểm tra thuốc có phải là thuốc mới (tạo trong vòng 7 ngày)
     */
    public boolean isThuocMoi() {
        if (ngayTao == null) return false;
        return ngayTao.isAfter(LocalDate.now().minusDays(7));
    }

    // ===== VALIDATION METHODS =====

    /**
     * Validate toàn bộ thông tin thuốc
     */
    public boolean isValid() {
        try {
            validate();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Validate và ném exception nếu có lỗi
     */
    public void validate() {
        if (tenThuoc == null || tenThuoc.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thuốc không được để trống");
        }

        if (donViTinh == null || donViTinh.trim().isEmpty()) {
            throw new IllegalArgumentException("Đơn vị tính không được để trống");
        }

        if (soLuongTon < 0) {
            throw new IllegalArgumentException("Số lượng tồn không thể âm");
        }

        if (giaBan < 0) {
            throw new IllegalArgumentException("Giá bán không thể âm");
        }

        if (ngayTao != null && ngayTao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày tạo không thể ở tương lai");
        }
    }

    // ===== UTILITY METHODS =====

    @Override
    public String toString() {
        return String.format("%s - %s - %d %s - %.2f VND",
                tenThuoc, donViTinh, soLuongTon,
                isConHang() ? "Còn hàng" : "Hết hàng", giaBan);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Thuoc thuoc = (Thuoc) obj;
        return maThuoc == thuoc.maThuoc;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(maThuoc);
    }

    /**
     * Tạo bản sao của thuốc
     */
    public Thuoc copy() {
        return new Thuoc(maThuoc, tenThuoc, donViTinh, soLuongTon, giaBan, ngayTao);
    }

    /**
     * Chuyển đổi thành chuỗi hiển thị đẹp
     */
    public String toDisplayString() {
        return String.format("""
            📋 THÔNG TIN THUỐC
            ├─ Mã thuốc: %d
            ├─ Tên thuốc: %s
            ├─ Đơn vị tính: %s
            ├─ Số lượng tồn: %d
            ├─ Giá bán: %.2f VND
            ├─ Tổng giá trị tồn kho: %.2f VND
            ├─ Trạng thái: %s
            └─ Ngày tạo: %s
            """,
                maThuoc, tenThuoc, donViTinh, soLuongTon, giaBan,
                tinhTongGiaTriTonKho(), getTrangThai(),
                ngayTao != null ? ngayTao.toString() : "Chưa xác định");
    }

    /**
     * Lấy trạng thái tồn kho
     */
    public String getTrangThai() {
        if (isHetHang()) return "🔴 Hết hàng";
        if (isSapHetHang()) return "🟡 Sắp hết hàng";
        return "🟢 Còn hàng";
    }

    /**
     * Định dạng giá tiền
     */
    public String getGiaBanFormatted() {
        return String.format("%,.0f VND", giaBan);
    }

    /**
     * Định dạng tổng giá trị tồn kho
     */
    public String getTongGiaTriTonKhoFormatted() {
        return String.format("%,.0f VND", tinhTongGiaTriTonKho());
    }

    // ===== BUILDER PATTERN (Optional) =====

    public static class Builder {
        private String tenThuoc;
        private String donViTinh;
        private int soLuongTon;
        private double giaBan;

        public Builder tenThuoc(String tenThuoc) {
            this.tenThuoc = tenThuoc;
            return this;
        }

        public Builder donViTinh(String donViTinh) {
            this.donViTinh = donViTinh;
            return this;
        }

        public Builder soLuongTon(int soLuongTon) {
            this.soLuongTon = soLuongTon;
            return this;
        }

        public Builder giaBan(double giaBan) {
            this.giaBan = giaBan;
            return this;
        }

        public Thuoc build() {
            return new Thuoc(tenThuoc, donViTinh, soLuongTon, giaBan);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}