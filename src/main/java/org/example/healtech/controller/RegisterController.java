package org.example.healtech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.healtech.dao.RegisterDAO;
import org.example.healtech.model.NhanVien;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField departmentField;

    @FXML
    private ComboBox<String> positionBox;

    private final RegisterDAO registerDAO = new RegisterDAO();

    @FXML
    public void initialize() {
        // Danh sách chức vụ
        positionBox.getItems().addAll("Bác sĩ", "Y tá", "Lễ tân", "Quản lý");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String fullname = fullnameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String phone = phoneField.getText().trim();
        String position = positionBox.getValue();
        String department = departmentField.getText().trim();

        // --- Kiểm tra dữ liệu ---
        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty() ||
                phone.isEmpty() || position == null || position.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Thiếu thông tin", "Vui lòng nhập đầy đủ các trường bắt buộc!");
            return;
        }

        if (!password.equals(confirm)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mật khẩu không khớp!");
            return;
        }

        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert(Alert.AlertType.WARNING, "Email không hợp lệ", "Vui lòng nhập địa chỉ email hợp lệ!");
            return;
        }

        if (!phone.matches("^\\d{9,11}$")) {
            showAlert(Alert.AlertType.WARNING, "Số điện thoại không hợp lệ", "Số điện thoại phải từ 9–11 chữ số!");
            return;
        }

        // --- Tạo đối tượng nhân viên ---
        NhanVien nv = new NhanVien();
        nv.setHoTen(fullname);
        nv.setEmail(email);
        nv.setMatKhau(password); // ⚠️ Lưu mật khẩu dạng thường
        nv.setChucVu(position);
        nv.setChuyenKhoa(department);
        nv.setSoDienThoai(phone);

        // --- Gọi DAO ---
        boolean success = registerDAO.registerUser(nv);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký thành công! Hãy đăng nhập.");
            goToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Email hoặc số điện thoại đã tồn tại!");
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        goToLogin();
    }

    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/healtech/view/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) fullnameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng nhập");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể chuyển sang trang đăng nhập!");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
