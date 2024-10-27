package GiaoDien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import Connection.DatabaseConnection;

public class LoginForm extends JFrame {
    private JComboBox<String> cbBranch;
    private JComboBox<String> cbRole;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnConnect;

    public LoginForm() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login Form");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));
        getContentPane().setBackground(Color.decode("#D6EAF8"));

        add(createImagePanel());
        add(createLoginPanel());
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBackground(Color.decode("#2C3E50"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblImage = new JLabel(resizeImage("C:\\Users\\quinh\\IdeaProjects\\QLDT\\src\\Image\\295128.png", 150, 150));
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(lblImage, gbc);

        gbc.gridy = 1;
        JLabel lblLoginText = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblLoginText.setForeground(Color.WHITE);
        lblLoginText.setFont(new Font("Arial", Font.BOLD, 18));
        imagePanel.add(lblLoginText, gbc);

        return imagePanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.decode("#F0F8FF"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Branch
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Chi Nhánh:"), gbc);
        cbBranch = new JComboBox<>(new String[]{"central", "Chi_Nhanh_1", "Chi_Nhanh_2"});
        gbc.gridx = 1;
        loginPanel.add(cbBranch, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Vai Trò:"), gbc);
        cbRole = new JComboBox<>(new String[]{"Quản Lý", "Nhân Viên"});
        gbc.gridx = 1;
        loginPanel.add(cbRole, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(new JLabel("Tên Đăng Nhập:"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        loginPanel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(new JLabel("Mật Khẩu:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        loginPanel.add(txtPassword, gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        btnConnect = new JButton("Đăng Nhập");
        btnConnect.setBackground(Color.decode("#27AE60"));
        btnConnect.setForeground(Color.WHITE);
        btnConnect.setFocusPainted(false);
        btnConnect.addActionListener(e -> connectToDatabase());
        loginPanel.add(btnConnect, gbc);

        return loginPanel;
    }

    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void connectToDatabase() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String branch = (String) cbBranch.getSelectedItem();
        String role = (String) cbRole.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập và mật khẩu không được để trống!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection(branch, username, password)) {
            String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND MatKhau = ? " +
                    "AND (Quyen = ? OR Quyen = 'QuanLyCuaHang')";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role.equals("Quản Lý") ? "QuanLyCuaHang" : "NhanVien");

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        openEmployeeForm(role); // Gọi hàm với vai trò
                    } else {
                        JOptionPane.showMessageDialog(this, "Thông tin đăng nhập không chính xác hoặc bạn không có quyền!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối: " + e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEmployeeForm(String role) {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            TrangChu trangChu = new TrangChu(role); // Truyền vai trò vào TrangChu
            trangChu.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}
