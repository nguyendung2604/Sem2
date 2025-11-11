package org.example.healtech.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.healtech.dao.LichHenDAO;
import org.example.healtech.model.LichHen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LichHenController {

    @FXML
    private TableView<LichHen> tableLichHen;
    @FXML
    private TableColumn<LichHen, Integer> colMaLichHen;
    @FXML
    private TableColumn<LichHen, Integer> colMaBenhNhan;
    @FXML
    private TableColumn<LichHen, Integer> colMaBacSi;
    @FXML
    private TableColumn<LichHen, LocalDateTime> colThoiGianHen;
    @FXML
    private TableColumn<LichHen, String> colLyDoKham;
    @FXML
    private TableColumn<LichHen, String> colTrangThai;

    @FXML
    private TextField txtMaBenhNhan;
    @FXML
    private TextField txtMaBacSi;
    @FXML
    private DatePicker dateNgayHen;
    @FXML
    private TextField txtGioHen; // định dạng: HH:mm
    @FXML
    private TextField txtLyDoKham;
    @FXML
    private ComboBox<String> cbTrangThai;

    @FXML
    private Button btnThem, btnCapNhat, btnXoa, btnTaiLai;



    @FXML
    public void initialize() {
        // Thiết lập cột cho bảng
        colMaLichHen.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getMaLichHen()).asObject());
        colMaBenhNhan.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getMaBenhNhan()).asObject());
        colMaBacSi.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getMaBacSi()).asObject());
        colThoiGianHen.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getThoiGianHen()));
        colLyDoKham.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLyDoKham()));
        colTrangThai.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTrangThai()));

        // Thiết lập ComboBox trạng thái
        cbTrangThai.getItems().addAll("Đã đặt", "Đã đến", "Đã hủy");
        cbTrangThai.setValue("Đã đặt");

        // Tải dữ liệu từ DB
        loadLichHen();
    }

    private void loadLichHen() {
        ObservableList<LichHen> list = LichHenDAO.getAllLichHen();
        tableLichHen.setItems(list);
    }

    @FXML
    private void handleThem(ActionEvent event) {
        try {
            int maBenhNhan = Integer.parseInt(txtMaBenhNhan.getText());
            int maBacSi = Integer.parseInt(txtMaBacSi.getText());
            LocalDate ngayHen = dateNgayHen.getValue();
            LocalTime gioHen = LocalTime.parse(txtGioHen.getText()); // VD: 14:30
            LocalDateTime thoiGianHen = LocalDateTime.of(ngayHen, gioHen);
            String lyDo = txtLyDoKham.getText();

            if (ngayHen == null || lyDo.isEmpty()) {
                showAlert("⚠️ Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING);
                return;
            }

            LichHen lichHen = new LichHen(maBenhNhan, maBacSi, thoiGianHen, lyDo);
            boolean success = LichHenDAO.addLichHen(lichHen);

            if (success) {
                showAlert("✅ Thêm lịch hẹn thành công!", Alert.AlertType.INFORMATION);
                loadLichHen();
                clearForm();
            } else {
                showAlert("❌ Không thể thêm lịch hẹn!", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("⚠️ Lỗi khi thêm lịch hẹn: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCapNhat(ActionEvent event) {
        LichHen selected = tableLichHen.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("⚠️ Vui lòng chọn lịch hẹn cần cập nhật!", Alert.AlertType.WARNING);
            return;
        }

        String newTrangThai = cbTrangThai.getValue();
        boolean success = LichHenDAO.updateTrangThai(selected.getMaLichHen(), newTrangThai);

        if (success) {
            showAlert("✅ Cập nhật trạng thái thành công!", Alert.AlertType.INFORMATION);
            loadLichHen();
        } else {
            showAlert("❌ Không thể cập nhật trạng thái!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleXoa(ActionEvent event) {
        LichHen selected = tableLichHen.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("⚠️ Vui lòng chọn lịch hẹn cần xóa!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn xóa lịch hẹn này?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            boolean success = LichHenDAO.deleteLichHen(selected.getMaLichHen());
            if (success) {
                showAlert("✅ Xóa lịch hẹn thành công!", Alert.AlertType.INFORMATION);
                loadLichHen();
            } else {
                showAlert("❌ Không thể xóa lịch hẹn!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleTaiLai(ActionEvent event) {
        loadLichHen();
    }

    private void clearForm() {
        txtMaBenhNhan.clear();
        txtMaBacSi.clear();
        txtGioHen.clear();
        txtLyDoKham.clear();
        dateNgayHen.setValue(null);
        cbTrangThai.setValue("Đã đặt");
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
