package GiaoDien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.DatabaseConnection;

public class FormHoaDon extends JFrame {
    private JTable tableHoaDon;
    private DefaultTableModel tableModel;
    private JTextField txtMaHD, txtMaNV, txtMaKH, txtMaKho, txtMaDT;
    private JButton btnThem, btnSua, btnXoa, btnChiTiet;

    public FormHoaDon() {
        setTitle("Quản Lý Hóa Đơn");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLookAndFeel();

        initializeComponents();
        loadHoaDonData();
    }

    private void initializeComponents() {
        // Initialize table model and JTable
        tableModel = new DefaultTableModel(new String[]{"Mã HĐ", "Mã NV", "Mã KH", "Mã Kho", "Mã ĐT"}, 0);
        tableHoaDon = new JTable(tableModel);
        tableHoaDon.setFillsViewportHeight(true);
        tableHoaDon.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font size for table
        JScrollPane scrollPane = new JScrollPane(tableHoaDon);

        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Quản lý Hóa Đơn"));
        inputPanel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Text fields for input with custom font size
        txtMaHD = createTextField();
        txtMaNV = createTextField();
        txtMaKH = createTextField();
        txtMaKho = createTextField();
        txtMaDT = createTextField();

        // Adding labels and text fields to input panel
        inputPanel.add(createLabel("Mã HĐ:"));
        inputPanel.add(txtMaHD);
        inputPanel.add(createLabel("Mã NV:"));
        inputPanel.add(txtMaNV);
        inputPanel.add(createLabel("Mã KH:"));
        inputPanel.add(txtMaKH);
        inputPanel.add(createLabel("Mã Kho:"));
        inputPanel.add(txtMaKho);
        inputPanel.add(createLabel("Mã ĐT:"));
        inputPanel.add(txtMaDT);

        // Define a common button background color
        Color buttonBackgroundColor = new Color(120, 172, 211); // Light blue color

       // Buttons with custom colors
        btnThem = createButton("Thêm", buttonBackgroundColor, Color.BLACK);
        btnSua = createButton("Sửa", buttonBackgroundColor, Color.BLACK);
        btnXoa = createButton("Xóa", buttonBackgroundColor, Color.BLACK);
        btnChiTiet = createButton("Chi Tiết Hóa Đơn", buttonBackgroundColor, Color.BLACK);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnChiTiet);

        // Main panel layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(120, 172, 211)); // Light gray background for main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Button panel background color
        buttonPanel.setBackground(new Color(120, 172, 211)); // Set button panel color

        // If needed, also set the background color for inputPanel to match
        inputPanel.setBackground(new Color(120, 172, 211)); // Matching input panel color

        // Thiết lập bảng và tiêu đề
        JTable tableHoaDon = new JTable(tableModel);
        tableHoaDon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Kích thước chữ lớn hơn
        tableHoaDon.getTableHeader().setBackground(new Color(135, 206, 235)); // Màu nền tiêu đề
        tableHoaDon.getTableHeader().setForeground(Color.BLACK); // Màu chữ tiêu đề

        // Action listeners
        btnThem.addActionListener(e -> themHoaDon());
        btnSua.addActionListener(e -> suaHoaDon());
        btnXoa.addActionListener(e -> xoaHoaDon());
        btnChiTiet.addActionListener(e -> xemChiTiet());
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font size
        return textField;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12)); // Set font size
        return label;
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 13)); // Set font size
        button.setBackground(background); // Set custom background color
        button.setForeground(foreground); // Set custom text color
        return button;
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHoaDonData() {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
            String query = "SELECT * FROM HoaDon";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("MaHD");
                    String maNV = rs.getString("MaNV");
                    String maKH = rs.getString("MaKH");
                    String maKho = rs.getString("MaKho");
                    String maDT = rs.getString("MaDT");
                    tableModel.addRow(new Object[]{maHD, maNV, maKH, maKho, maDT});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void themHoaDon() {
        try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
            String query = "INSERT INTO HoaDon (MaHD, MaNV, MaKH, MaKho, MaDT) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, txtMaHD.getText());
            stmt.setString(2, txtMaNV.getText());
            stmt.setString(3, txtMaKH.getText());
            stmt.setString(4, txtMaKho.getText());
            stmt.setString(5, txtMaDT.getText());
            stmt.executeUpdate();
            loadHoaDonData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void suaHoaDon() {
        int selectedRow = tableHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
                String query = "UPDATE HoaDon SET MaNV=?, MaKH=?, MaKho=?, MaDT=? WHERE MaHD=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, txtMaNV.getText());
                stmt.setString(2, txtMaKH.getText());
                stmt.setString(3, txtMaKho.getText());
                stmt.setString(4, txtMaDT.getText());
                stmt.setString(5, txtMaHD.getText());
                stmt.executeUpdate();
                loadHoaDonData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa!", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xoaHoaDon() {
        int selectedRow = tableHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            String maHD = tableModel.getValueAt(selectedRow, 0).toString();
            try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
                String query = "DELETE FROM HoaDon WHERE MaHD=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, maHD);
                stmt.executeUpdate();
                loadHoaDonData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xemChiTiet() {
        int selectedRow = tableHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            String maHD = tableModel.getValueAt(selectedRow, 0).toString();
            showChiTietHoaDon(maHD);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn!", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showChiTietHoaDon(String maHD) {
        JFrame chiTietFrame = new JFrame("Chi Tiết Hóa Đơn: " + maHD);
        chiTietFrame.setSize(600, 400);
        chiTietFrame.setLocationRelativeTo(this);

        // Khởi tạo các component cho giao diện chi tiết
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> chiTietFrame.dispose());

        // Lấy dữ liệu chi tiết hóa đơn từ CSDL và hiển thị
        try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
            String query = """
            SELECT 
                COALESCE(KhachHang.TenKH, 'Không có') AS TenKH,
                COALESCE(KhachHang.DiaChi, 'Không có') AS DiaChi,
                COALESCE(KhachHang.SDT, 'Không có') AS SDT,
                COALESCE(DienThoai.TenDT, 'Không có') AS TenDT,
                COALESCE(DienThoai.DonGia, 0) AS DonGia,
                COALESCE(ChiTietHoaDon.SoLuong, 0) AS SoLuong
            FROM HoaDon
            LEFT JOIN KhachHang ON HoaDon.MaKH = KhachHang.MaKH
            LEFT JOIN ChiTietHoaDon ON HoaDon.MaHD = ChiTietHoaDon.MaHD
            LEFT JOIN DienThoai ON ChiTietHoaDon.MaDT = DienThoai.MaDT
            WHERE HoaDon.MaHD = ?
            """;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            StringBuilder chiTiet = new StringBuilder();
            while (rs.next()) {
                String tenKH = rs.getString("TenKH");
                String diaChi = rs.getString("DiaChi");
                String sdtKH = rs.getString("SDT");
                String tenDT = rs.getString("TenDT");
                double donGia = rs.getDouble("DonGia");
                int soLuong = rs.getInt("SoLuong");
                double thanhTien = donGia * soLuong;

                chiTiet.append("Khách Hàng: ").append(tenKH)
                        .append("\nĐịa Chỉ: ").append(diaChi)
                        .append("\nSĐT: ").append(sdtKH)
                        .append("\nĐiện Thoại: ").append(tenDT)
                        .append("\nĐơn Giá: ").append(String.format("%.2f", donGia)) // Format to 2 decimal places
                        .append("\nSố Lượng: ").append(soLuong)
                        .append("\nThành Tiền: ").append(String.format("%.2f", thanhTien)) // Format to 2 decimal places
                        .append("\n\n");
            }
            textArea.setText(chiTiet.toString());
        } catch (SQLException e) {
            textArea.setText("Lỗi khi lấy dữ liệu chi tiết hóa đơn!");
        }

        // Bố trí các thành phần trên giao diện
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnDong, BorderLayout.SOUTH);

        chiTietFrame.add(panel);
        chiTietFrame.setVisible(true);
    }

    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> new FormHoaDon().setVisible(true));
    //}
}