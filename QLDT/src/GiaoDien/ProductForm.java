package GiaoDien;

import Connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ProductForm extends JFrame {
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtManHinh, txtHeDieuHanh,
            txtCamera, txtRam, txtBoNho, txtPin, txtTimKiem;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JLabel lblHinhAnh;
    private String hinhAnhPath = "";

    public ProductForm() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create UI components
        JPanel formPanel = createFormPanel();
        JScrollPane scrollPane = createTablePanel();
        JPanel buttonPanel = createButtonPanel();

        // Add components to the main window
        add(formPanel, BorderLayout.NORTH); // Form panel at the top
        add(scrollPane, BorderLayout.CENTER); // Table panel in the center
        add(buttonPanel, BorderLayout.SOUTH); // Button panel at the bottom

        // Load product data from SQL
        loadProductData();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5)); // Bố trí hai cột

        // Panel bên trái
        JPanel leftPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(new Color(120, 172, 211)); // Màu xanh nhạt

        JLabel lblMaSP = new JLabel("Mã SP:");
        JLabel lblTenSP = new JLabel("Tên SP:");
        JLabel lblDonGia = new JLabel("Đơn Giá:");
        JLabel lblManHinh = new JLabel("Màn Hình:");
        JLabel lblHeDieuHanh = new JLabel("Hệ Điều Hành:");

        txtMaSP = new JTextField();
        txtTenSP = new JTextField();
        txtDonGia = new JTextField();
        txtManHinh = new JTextField();
        txtHeDieuHanh = new JTextField();

        leftPanel.add(lblMaSP); leftPanel.add(txtMaSP);
        leftPanel.add(lblTenSP); leftPanel.add(txtTenSP);
        leftPanel.add(lblDonGia); leftPanel.add(txtDonGia);
        leftPanel.add(lblManHinh); leftPanel.add(txtManHinh);
        leftPanel.add(lblHeDieuHanh); leftPanel.add(txtHeDieuHanh);

        // Panel bên phải
        JPanel rightPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(new Color(120, 172, 211)); // Màu xanh nhạt

        JLabel lblCamera = new JLabel("Camera:");
        JLabel lblRam = new JLabel("Ram:");
        JLabel lblBoNho = new JLabel("Bộ Nhớ:");
        JLabel lblPin = new JLabel("Dung Lượng Pin:");
        lblHinhAnh = new JLabel("Hình Ảnh:");

        txtCamera = new JTextField();
        txtRam = new JTextField();
        txtBoNho = new JTextField();
        txtPin = new JTextField();

        JButton btnChonHinh = new JButton("Chọn Hình");
        btnChonHinh.addActionListener(e -> chonHinhAnh());

        rightPanel.add(lblCamera); rightPanel.add(txtCamera);
        rightPanel.add(lblRam); rightPanel.add(txtRam);
        rightPanel.add(lblBoNho); rightPanel.add(txtBoNho);
        rightPanel.add(lblPin); rightPanel.add(txtPin);
        rightPanel.add(lblHinhAnh); rightPanel.add(btnChonHinh);

        // Thêm các panel vào panel chính
        panel.add(leftPanel);
        panel.add(rightPanel);

        return panel;
    }


    private JScrollPane createTablePanel() {
        String[] columnNames = {"Mã SP", "Tên SP", "Đơn Giá", "Màn Hình", "Hệ Điều Hành",
                "Camera", "Ram", "Bộ Nhớ", "Pin", "Hình Ảnh"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.getColumnModel().getColumn(9).setCellRenderer(new ImageRenderer()); // Set custom image renderer

        productTable.setRowHeight(40);

        return new JScrollPane(productTable);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(120, 172, 211));  // Light Blue

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLuu = new JButton("Lưu");

        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnLuu.addActionListener(e -> luuSanPham());

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLuu);

        return panel;
    }

    private void loadProductData() {
        try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
            String query = "SELECT * FROM DienThoai";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("MaDT"));
                row.add(rs.getString("TenDT"));
                row.add(rs.getString("DonGia"));
                row.add(rs.getString("ManHinh"));
                row.add(rs.getString("HeDieuHanh"));
                row.add(rs.getString("Camera"));
                row.add(rs.getString("Ram"));
                row.add(rs.getString("BoNho"));
                row.add(rs.getString("DungLuongPin"));
                row.add(rs.getString("HinhAnh")); // Image path

                tableModel.addRow(row);
            }

            // Set the custom image renderer for the last column (image column)
            productTable.getColumnModel().getColumn(9).setCellRenderer(new ImageRenderer());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void chonHinhAnh() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            hinhAnhPath = fileChooser.getSelectedFile().getAbsolutePath();
            lblHinhAnh.setText(hinhAnhPath);
        }
    }

    private void themSanPham() {
        String maSP = txtMaSP.getText();
        String tenSP = txtTenSP.getText();
        String donGia = txtDonGia.getText();
        String manHinh = txtManHinh.getText();
        String heDieuHanh = txtHeDieuHanh.getText();
        String camera = txtCamera.getText();
        String ram = txtRam.getText();
        String boNho = txtBoNho.getText();
        String pin = txtPin.getText();

        Vector<String> row = new Vector<>();
        row.add(maSP);
        row.add(tenSP);
        row.add(donGia);
        row.add(manHinh);
        row.add(heDieuHanh);
        row.add(camera);
        row.add(ram);
        row.add(boNho);
        row.add(pin);
        row.add(hinhAnhPath);

        // Thêm sản phẩm vào bảng
        tableModel.addRow(row);

        // Lưu sản phẩm vào cơ sở dữ liệu
        try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
            String sql = "INSERT INTO DienThoai (MaDT, TenDT, DonGia, ManHinh, HeDieuHanh, Camera, Ram, BoNho, DungLuongPin, HinhAnh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maSP);
            pstmt.setString(2, tenSP);
            pstmt.setString(3, donGia);
            pstmt.setString(4, manHinh);
            pstmt.setString(5, heDieuHanh);
            pstmt.setString(6, camera);
            pstmt.setString(7, ram);
            pstmt.setString(8, boNho);
            pstmt.setString(9, pin);
            pstmt.setString(10, hinhAnhPath);

            pstmt.executeUpdate(); // Thực thi câu lệnh INSERT
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm: " + e.getMessage());
        }

        clearForm(); // Xóa form sau khi thêm
    }

    private void suaSanPham() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(txtMaSP.getText(), selectedRow, 0);
            tableModel.setValueAt(txtTenSP.getText(), selectedRow, 1);
            tableModel.setValueAt(txtDonGia.getText(), selectedRow, 2);
            tableModel.setValueAt(txtManHinh.getText(), selectedRow, 3);
            tableModel.setValueAt(txtHeDieuHanh.getText(), selectedRow, 4);
            tableModel.setValueAt(txtCamera.getText(), selectedRow, 5);
            tableModel.setValueAt(txtRam.getText(), selectedRow, 6);
            tableModel.setValueAt(txtBoNho.getText(), selectedRow, 7);
            tableModel.setValueAt(txtPin.getText(), selectedRow, 8);
            tableModel.setValueAt(hinhAnhPath, selectedRow, 9);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa.");
        }
    }

    private void xoaSanPham() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy mã sản phẩm từ hàng đã chọn
            String maSP = tableModel.getValueAt(selectedRow, 0).toString();

            // Xóa sản phẩm khỏi cơ sở dữ liệu
            try (Connection conn = DatabaseConnection.getConnection("central", "sa", "1234567890")) {
                String sql = "DELETE FROM DienThoai WHERE MaDT = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, maSP);
                pstmt.executeUpdate(); // Thực hiện câu lệnh DELETE
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm: " + e.getMessage());
            }

            // Xóa hàng khỏi bảng
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa.");
        }
    }

    private void luuSanPham() {
        JOptionPane.showMessageDialog(this, "Dữ liệu đã được lưu.");
    }

    private void clearForm() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        txtManHinh.setText("");
        txtHeDieuHanh.setText("");
        txtCamera.setText("");
        txtRam.setText("");
        txtBoNho.setText("");
        txtPin.setText("");
        lblHinhAnh.setText("Hình Ảnh:");
        hinhAnhPath = "";
    }

    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> new ProductForm().setVisible(true));
    //}

    // Custom cell renderer for displaying images in the JTable
    static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            if (value instanceof String) {
                String imagePath = (String) value;
                ImageIcon imageIcon = new ImageIcon(imagePath);
                // Resize the image if needed
                Image img = imageIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH); // Resize to 40x40 pixels
                label.setIcon(new ImageIcon(img));
            }
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    }
}
