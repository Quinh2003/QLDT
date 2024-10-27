package GiaoDien;

import Connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerForm extends JFrame {

    private JTextField txtMaKH, txtHoTen, txtDiaChi, txtSDT, txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection connection;

    public CustomerForm() {
        setTitle("Thông Tin Khách Hàng");
        setSize(900, 500); // Adjusted size for consistency with TrangChu
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(new Color(240, 248, 255)); // Light background to match

        JLabel lblTitle = new JLabel("Quản Lý Khách Hàng", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26)); // Increased font size
        lblTitle.setForeground(new Color(0, 123, 255));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.WEST);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        connectDatabase();
        loadCustomerData();

        setLocationRelativeTo(null);
        setVisible(true);
    }


    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(120, 172, 211)); // Match background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased insets
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left

        String[] labels = {"Mã KH:", "Tên Khách Hàng:", "Địa Chỉ:", "SĐT:"};
        JTextField[] textFields = {
                txtMaKH = new JTextField(15),
                txtHoTen = new JTextField(15),
                txtDiaChi = new JTextField(15),
                txtSDT = new JTextField(15)
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.gridwidth = 1;
            panel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.gridwidth = 3; // Increase the width of text fields
            panel.add(textFields[i], gbc);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(120, 172, 211));

        JButton btnThem = createStyledButton("Thêm", 80);
        JButton btnSua = createStyledButton("Sửa", 80);
        JButton btnXoa = createStyledButton("Xóa", 80);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER; // Center the button panel
        panel.add(buttonPanel, gbc);

        btnThem.addActionListener(e -> addCustomer());
        btnSua.addActionListener(e -> editCustomer());
        btnXoa.addActionListener(e -> deleteCustomer());

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        tableModel = new DefaultTableModel(new Object[]{"Mã KH", "Tên Khách Hàng", "Địa Chỉ", "SĐT"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(120, 172, 211));
        table.setSelectionForeground(Color.BLACK);
        table.setDefaultEditor(Object.class, null); // Make table non-editable

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centered layout
        txtSearch = new JTextField(15);
        JButton btnTimKiem = createStyledButton("Tìm Kiếm", 80);
        btnTimKiem.addActionListener(e -> searchCustomer());

        searchPanel.add(new JLabel("Tìm Kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnTimKiem);

        panel.add(searchPanel, BorderLayout.NORTH);
        return panel;
    }

    private JButton createStyledButton(String text, int width) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 153, 204));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, 30)); // Adjusted button height
        return button;
    }

    private void connectDatabase() {
        try {
            connection = DatabaseConnection.getConnection("central", "sa", "1234567890");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Kết nối database thất bại.");
            e.printStackTrace();
        }
    }

    private void loadCustomerData() {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM KhachHang")) {
            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("MaKH"), rs.getString("TenKH"),
                        rs.getString("DiaChi"), rs.getString("SDT")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCustomer() {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO KhachHang (MaKH, TenKH, DiaChi, SDT) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, txtMaKH.getText());
            stmt.setString(2, txtHoTen.getText());
            stmt.setString(3, txtDiaChi.getText());
            stmt.setString(4, txtSDT.getText());
            stmt.executeUpdate();
            loadCustomerData();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editCustomer() {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE KhachHang SET TenKH = ?, DiaChi = ?, SDT = ? WHERE MaKH = ?")) {
            stmt.setString(1, txtHoTen.getText());
            stmt.setString(2, txtDiaChi.getText());
            stmt.setString(3, txtSDT.getText());
            stmt.setString(4, txtMaKH.getText());
            stmt.executeUpdate();
            loadCustomerData();
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer() {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM KhachHang WHERE MaKH = ?")) {
            stmt.setString(1, txtMaKH.getText());
            stmt.executeUpdate();
            loadCustomerData();
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchCustomer() {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM KhachHang WHERE TenKH LIKE ?")) {
            stmt.setString(1, "%" + txtSearch.getText() + "%");
            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("MaKH"), rs.getString("TenKH"),
                        rs.getString("DiaChi"), rs.getString("SDT")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Uncomment if you want to run this separately
     //public static void main(String[] args) {
     //    SwingUtilities.invokeLater(CustomerForm::new);
     //}
}