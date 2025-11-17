package org.example.healtech.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.healtech.dao.BenhNhanDAO;
import org.example.healtech.model.BenhNhan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BenhNhanController {

    @FXML private TableView<BenhNhan> tableBenhNhan;
    @FXML private TableColumn<BenhNhan, Integer> colMaBN;
    @FXML private TableColumn<BenhNhan, String> colHoTen;
    @FXML private TableColumn<BenhNhan, LocalDate> colNgaySinh;
    @FXML private TableColumn<BenhNhan, String> colGioiTinh;
    @FXML private TableColumn<BenhNhan, String> colDiaChi;
    @FXML private TableColumn<BenhNhan, String> colSoDienThoai;
    @FXML private TableColumn<BenhNhan, String> colTienSuBenh;

    @FXML private TextField txtHoTen;
    @FXML private DatePicker dateNgaySinh;
    @FXML private ComboBox<String> cbGioiTinh;
    @FXML private TextField txtDiaChi;
    @FXML private TextField txtSoDienThoai;
    @FXML private TextArea txtTienSuBenh;
    @FXML private TextField txtTimKiem;

    private BenhNhanDAO benhNhanDAO;
    private BenhNhan benhNhanDangChon;

    @FXML
    public void initialize() {
        try {
            benhNhanDAO = new BenhNhanDAO();
            setupTableColumns();
            setupComboBox();
            loadBenhNhanTable();

            // Thêm listener cho table selection
            tableBenhNhan.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> showBenhNhanDetails(newVal)
            );

        } catch (Exception e) {
            showAlert("❌ Lỗi khởi tạo: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void setupTableColumns() {
        colMaBN.setCellValueFactory(new PropertyValueFactory<>("maBenhNhan"));
        colHoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colTienSuBenh.setCellValueFactory(new PropertyValueFactory<>("tienSuBenh"));
    }

    private void setupComboBox() {
        cbGioiTinh.getItems().addAll("Nam", "Nữ", "Khác");
    }

    private void loadBenhNhanTable() {
        try {
            List<BenhNhan> danhSach = benhNhanDAO.getAllBenhNhan();
            tableBenhNhan.setItems(FXCollections.observableArrayList(danhSach));
        } catch (Exception e) {
            showAlert("❌ Lỗi tải dữ liệu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // ===== PHƯƠNG THỨC CLEARFORM HOÀN CHỈNH =====
    @FXML
    private void clearForm() {
        // Xóa tất cả các trường nhập liệu
        txtHoTen.clear();
        dateNgaySinh.setValue(null);
        cbGioiTinh.setValue(null);
        txtDiaChi.clear();
        txtSoDienThoai.clear();
        txtTienSuBenh.clear();

        // Xóa lựa chọn trên bảng
        tableBenhNhan.getSelectionModel().clearSelection();

        // Reset biến lưu bệnh nhân đang chọn
        benhNhanDangChon = null;

        // Focus vào ô tìm kiếm để tiện sử dụng
        txtTimKiem.requestFocus();

        System.out.println("✅ Đã làm mới form thành công!");
    }

    // ===== CÁC PHƯƠNG THỨC XỬ LÝ SỰ KIỆN =====
    @FXML
    private void handleThem(ActionEvent event) {
        Optional<BenhNhan> benhNhanOpt = getBenhNhanFromForm();
        if (benhNhanOpt.isPresent()) {
            boolean success = benhNhanDAO.addBenhNhan(benhNhanOpt.get());
            if (success) {
                showAlert("✅ Thêm bệnh nhân thành công!", Alert.AlertType.INFORMATION);
                loadBenhNhanTable();
                clearForm(); // Gọi clearForm sau khi thêm thành công
            } else {
                showAlert("❌ Thêm bệnh nhân thất bại!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleCapNhat(ActionEvent event) {
        if (benhNhanDangChon == null) {
            showAlert("⚠️ Chọn bệnh nhân cần cập nhật!", Alert.AlertType.WARNING);
            return;
        }

        Optional<BenhNhan> benhNhanOpt = getBenhNhanFromForm();
        if (benhNhanOpt.isPresent()) {
            BenhNhan bn = benhNhanOpt.get();
            bn.setMaBenhNhan(benhNhanDangChon.getMaBenhNhan());

            boolean success = benhNhanDAO.updateBenhNhan(bn);
            if (success) {
                showAlert("✅ Cập nhật thành công!", Alert.AlertType.INFORMATION);
                loadBenhNhanTable();
            } else {
                showAlert("❌ Cập nhật thất bại!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleXoa(ActionEvent event) {
        if (benhNhanDangChon == null) {
            showAlert("⚠️ Chọn bệnh nhân cần xóa!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText("Bạn có chắc muốn xóa bệnh nhân: " + benhNhanDangChon.getHoTen() + "?");
        confirm.setContentText("Hành động này không thể hoàn tác!");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            boolean success = benhNhanDAO.deleteBenhNhan(benhNhanDangChon.getMaBenhNhan());
            if (success) {
                showAlert("✅ Xóa thành công!", Alert.AlertType.INFORMATION);
                loadBenhNhanTable();
                clearForm(); // Gọi clearForm sau khi xóa thành công
            } else {
                showAlert("❌ Xóa thất bại!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleTimKiem(ActionEvent event) {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            loadBenhNhanTable();
            return;
        }

        List<BenhNhan> ketQua = benhNhanDAO.timKiemBenhNhan(keyword);
        tableBenhNhan.setItems(FXCollections.observableArrayList(ketQua));

        if (ketQua.isEmpty()) {
            showAlert("🔍 Không tìm thấy kết quả!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void handleTaiLai(ActionEvent event) {
        loadBenhNhanTable();
        clearForm(); // Gọi clearForm khi tải lại
        showAlert("🔄 Đã tải lại dữ liệu!", Alert.AlertType.INFORMATION);
    }

    // ===== PHƯƠNG THỨC HỖ TRỢ =====
    private void showBenhNhanDetails(BenhNhan benhNhan) {
        if (benhNhan != null) {
            benhNhanDangChon = benhNhan;
            txtHoTen.setText(benhNhan.getHoTen());
            dateNgaySinh.setValue(benhNhan.getNgaySinh());
            cbGioiTinh.setValue(benhNhan.getGioiTinh());
            txtDiaChi.setText(benhNhan.getDiaChi());
            txtSoDienThoai.setText(benhNhan.getSoDienThoai());
            txtTienSuBenh.setText(benhNhan.getTienSuBenh());
        }
    }

    private Optional<BenhNhan> getBenhNhanFromForm() {
        try {
            // Validate dữ liệu
            if (txtHoTen.getText().trim().isEmpty()) {
                showAlert("⚠️ Nhập họ tên!", Alert.AlertType.WARNING);
                return Optional.empty();
            }
            if (dateNgaySinh.getValue() == null) {
                showAlert("⚠️ Chọn ngày sinh!", Alert.AlertType.WARNING);
                return Optional.empty();
            }
            if (cbGioiTinh.getValue() == null) {
                showAlert("⚠️ Chọn giới tính!", Alert.AlertType.WARNING);
                return Optional.empty();
            }
            if (txtDiaChi.getText().trim().isEmpty()) {
                showAlert("⚠️ Nhập địa chỉ!", Alert.AlertType.WARNING);
                return Optional.empty();
            }
            if (txtSoDienThoai.getText().trim().isEmpty()) {
                showAlert("⚠️ Nhập số điện thoại!", Alert.AlertType.WARNING);
                return Optional.empty();
            }

            // Tạo đối tượng bệnh nhân
            BenhNhan bn = new BenhNhan();
            bn.setHoTen(txtHoTen.getText().trim());
            bn.setNgaySinh(dateNgaySinh.getValue());
            bn.setGioiTinh(cbGioiTinh.getValue());
            bn.setDiaChi(txtDiaChi.getText().trim());
            bn.setSoDienThoai(txtSoDienThoai.getText().trim());
            bn.setTienSuBenh(txtTienSuBenh.getText().trim());

            return Optional.of(bn);

        } catch (Exception e) {
            showAlert("❌ Lỗi dữ liệu: " + e.getMessage(), Alert.AlertType.ERROR);
            return Optional.empty();
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}