package GiaoDien;

import Connection.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;

public class EmployeeForm extends JPanel {
    private JTextField txtMaNV, txtSDT, txtDiaChi, txtHoNV, txtTenNV, txtGhiChu, txtLuong, txtMaCN;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cbQuyen, cbGioiTinh, cbNgaySinh, cbThangSinh, cbNamSinh;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private String serverKey = "central"; // Dynamic login credentials
    private String user = "sa"; // Dynamic login credentials
    private String password = "1234567890"; // Dynamic login credentials

    public EmployeeForm() {
        setLayout(new BorderLayout());
        setBackground(new Color(52, 114, 162));

        // Create form panels
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        JScrollPane scrollPane = createTablePanel();

        // Add components to the main panel
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // Giảm khoảng cách
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Giảm padding
        formPanel.setBackground(new Color(120, 172, 211));

        // Left panel
        JPanel leftPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        leftPanel.setBackground(new Color(120, 172, 211));
        addInputFields(leftPanel);

        // Right panel
        JPanel rightPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        rightPanel.setBackground(new Color(120, 172, 211));
        addRightInputFields(rightPanel);

        formPanel.add(leftPanel);
        formPanel.add(rightPanel);
        return formPanel;
    }

    private void addInputFields(JPanel panel) {
        panel.add(new JLabel("Mã NV:"));
        txtMaNV = new JTextField();
        panel.add(txtMaNV);

        panel.add(new JLabel("Mật Khẩu:"));
        txtMatKhau = new JPasswordField();
        panel.add(txtMatKhau);

        panel.add(new JLabel("Họ NV:"));
        txtHoNV = new JTextField();
        panel.add(txtHoNV);

        panel.add(new JLabel("Tên NV:"));
        txtTenNV = new JTextField();
        panel.add(txtTenNV);

        panel.add(new JLabel("SDT:"));
        txtSDT = new JTextField();
        panel.add(txtSDT);

        panel.add(new JLabel("Địa Chỉ:"));
        txtDiaChi = new JTextField();
        panel.add(txtDiaChi);
    }

    private void addRightInputFields(JPanel panel) {
        panel.add(new JLabel("Lương:"));
        txtLuong = new JTextField();
        panel.add(txtLuong);

        panel.add(new JLabel("Mã CN:"));
        txtMaCN = new JTextField();
        panel.add(txtMaCN);

        panel.add(new JLabel("Ghi Chú:"));
        txtGhiChu = new JTextField();
        panel.add(txtGhiChu);

        panel.add(new JLabel("Quyền:"));
        cbQuyen = new JComboBox<>(new String[]{"Nhân Viên", "Quản Lý"});
        panel.add(cbQuyen);

        panel.add(new JLabel("Giới Tính:"));
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        panel.add(cbGioiTinh);

        panel.add(new JLabel("Ngày Sinh:"));
        cbNgaySinh = new JComboBox<>(generateNumbers(1, 31));
        cbThangSinh = new JComboBox<>(generateNumbers(1, 12));
        cbNamSinh = new JComboBox<>(generateNumbers(1900, 2024));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.add(cbNgaySinh);
        datePanel.add(cbThangSinh);
        datePanel.add(cbNamSinh);
        panel.add(datePanel);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20)); // Giảm khoảng cách
        buttonPanel.setBackground(new Color(120, 172, 211));

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(e -> themNhanVien());
        buttonPanel.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> suaNhanVien());
        buttonPanel.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e -> xoaNhanVien());
        buttonPanel.add(btnXoa);

        JButton btnXemDanhSach = new JButton("Xem Danh Sách");
        btnXemDanhSach.addActionListener(e -> xemDanhSachNhanVien());
        buttonPanel.add(btnXemDanhSach);

        return buttonPanel;
    }

    private JScrollPane createTablePanel() {
        String[] columnNames = {"Mã NV", "Mật Khẩu", "Quyền", "Họ NV", "Tên NV", "SDT", "Địa Chỉ", "Ngày Sinh", "Giới Tính", "Mã CN", "Lương", "Ghi Chú"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        // Đặt chiều cao tối đa cho JScrollPane
        scrollPane.setPreferredSize(new Dimension(800, 300)); // Điều chỉnh chiều cao tại đây
        return scrollPane;
    }

    private void themNhanVien() {
        try {
            Connection conn = DatabaseConnection.getConnection(serverKey, user, password);
            String sql = "INSERT INTO NhanVien (MaNV, MatKhau, Quyen, HoNV, TenNV, SDT, DiaChi, NgaySinh, GioiTinh, MaCN, Luong, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, txtMaNV.getText());
            statement.setString(2, new String(txtMatKhau.getPassword()));
            statement.setString(3, (String) cbQuyen.getSelectedItem());
            statement.setString(4, txtHoNV.getText());
            statement.setString(5, txtTenNV.getText());
            statement.setString(6, txtSDT.getText());
            statement.setString(7, txtDiaChi.getText());
            statement.setString(8, cbNgaySinh.getSelectedItem() + "-" + cbThangSinh.getSelectedItem() + "-" + cbNamSinh.getSelectedItem());
            statement.setString(9, (String) cbGioiTinh.getSelectedItem());
            statement.setString(10, txtMaCN.getText());
            statement.setBigDecimal(11, new BigDecimal(txtLuong.getText()));
            statement.setString(12, txtGhiChu.getText());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại: " + e.getMessage());
        }
    }

    // Phương thức sửa thông tin nhân viên
    private void suaNhanVien() {
        String maNV = txtMaNV.getText();
        String matKhau = new String(txtMatKhau.getPassword());
        String quyen = (String) cbQuyen.getSelectedItem();
        String hoNV = txtHoNV.getText();
        String tenNV = txtTenNV.getText();
        String sdt = txtSDT.getText();
        String diaChi = txtDiaChi.getText();
        String ghiChu = txtGhiChu.getText();
        String luong = txtLuong.getText();
        String maCN = txtMaCN.getText();
        String gioiTinh = (String) cbGioiTinh.getSelectedItem();
        String ngaySinh = cbNamSinh.getSelectedItem() + "-" + cbThangSinh.getSelectedItem() + "-" + cbNgaySinh.getSelectedItem();

        String sql = "UPDATE NhanVien SET MatKhau = ?, Quyen = ?, HoNV = ?, TenNV = ?, SDT = ?, DiaChi = ?, NgaySinh = ?, GioiTinh = ?, GhiChu = ?, Luong = ?, MaCN = ? WHERE MaNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(serverKey, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, matKhau);
            pstmt.setString(2, quyen);
            pstmt.setString(3, hoNV);
            pstmt.setString(4, tenNV);
            pstmt.setString(5, sdt);
            pstmt.setString(6, diaChi);
            pstmt.setDate(7, Date.valueOf(ngaySinh));
            pstmt.setString(8, gioiTinh);
            pstmt.setString(9, ghiChu);
            pstmt.setBigDecimal(10, new BigDecimal(luong));
            pstmt.setString(11, maCN);
            pstmt.setString(12, maNV);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công!");
            clearInputFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private boolean isEmployeeExists(String maNV) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNV = ?";
        try (Connection conn = DatabaseConnection.getConnection(serverKey, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNV);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu tồn tại
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
        return false;
    }

    private void xoaNhanVien() {
        String maNV = txtMaNV.getText();
        if (maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!");
            return;
        }

        if (!isEmployeeExists(maNV)) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên với mã: " + maNV);
            return;
        }

        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
        try (Connection conn = DatabaseConnection.getConnection(serverKey, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNV);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
            clearInputFields();
            xemDanhSachNhanVien(); // Làm mới danh sách nhân viên
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    // Phương thức xem danh sách nhân viên
    private void xemDanhSachNhanVien() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        String sql = "SELECT * FROM NhanVien";

        try (Connection conn = DatabaseConnection.getConnection(serverKey, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String maNV = rs.getString("MaNV");
                String matKhau = rs.getString("MatKhau");
                String quyen = rs.getString("Quyen");
                String hoNV = rs.getString("HoNV");
                String tenNV = rs.getString("TenNV");
                String sdt = rs.getString("SDT");
                String diaChi = rs.getString("DiaChi");
                String ngaySinh = rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toString() : ""; // Kiểm tra NULL
                String gioiTinh = rs.getString("GioiTinh");
                String maCN = rs.getString("MaCN");
                BigDecimal luong = rs.getBigDecimal("Luong") != null ? rs.getBigDecimal("Luong") : BigDecimal.ZERO; // Kiểm tra NULL
                String ghiChu = rs.getString("GhiChu");

                tableModel.addRow(new Object[]{maNV, matKhau, quyen, hoNV, tenNV, sdt, diaChi, ngaySinh, gioiTinh, maCN, luong, ghiChu});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    // Phương thức xóa các trường nhập liệu
    private void clearInputFields() {
        txtMaNV.setText("");
        txtMatKhau.setText("");
        txtHoNV.setText("");
        txtTenNV.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtGhiChu.setText("");
        txtLuong.setText("");
        txtMaCN.setText("");
        cbQuyen.setSelectedIndex(0);
        cbGioiTinh.setSelectedIndex(0);
        cbNgaySinh.setSelectedIndex(0);
        cbThangSinh.setSelectedIndex(0);
        cbNamSinh.setSelectedIndex(0);
    }

    private String[] generateNumbers(int start, int end) {
        String[] numbers = new String[end - start + 1];
        for (int i = start; i <= end; i++) {
            numbers[i - start] = String.valueOf(i);
        }
        return numbers;
    }
}
