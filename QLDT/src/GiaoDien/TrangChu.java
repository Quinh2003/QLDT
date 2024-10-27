package GiaoDien;

import javax.swing.*;
import java.awt.*;

public class TrangChu extends JFrame {

    private JPanel panelFunction; // Panel chức năng
    private JPanel panelContent;  // Panel nội dung chi tiết

    // Declare buttons as class-level variables
    private JButton btnQuanLyNhanVien;
    private JButton btnQuanLySanPham;
    private JButton btnQuanLyHoaDon;
    private JButton btnThongTinKhachHang;

    public TrangChu(String role) {
        setTitle("Trang Chủ Quản Lý");
        setSize(950, 600); // Kích thước 950x600
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa cửa sổ

        // Tạo JSplitPane với khoảng cách hợp lý
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(8); // Tăng độ rộng chia
        splitPane.setDividerLocation(200); // Vị trí chia mặc định

        // Khởi tạo các panel
        panelFunction = new JPanel(new GridLayout(0, 1, 5, 5)); // 0 rows (dynamic)
        panelFunction.setBackground(new Color(159, 196, 224)); // Màu xanh nhạt

        panelContent = new JPanel(new BorderLayout());
        panelContent.setBackground(Color.WHITE); // Nền trắng cho nội dung

        // Set up logo and buttons
        setupPanelFunction();

        // Kiểm tra quyền và vô hiệu hóa các nút không cần thiết
        configureButtonsForRole(role);

        // Thêm panel vào JSplitPane
        splitPane.setLeftComponent(panelFunction);
        splitPane.setRightComponent(panelContent);

        add(splitPane); // Thêm JSplitPane vào JFrame

        // Lắng nghe sự kiện cho các nút
        btnQuanLyNhanVien.addActionListener(e -> showEmployeeForm());
        btnQuanLySanPham.addActionListener(e -> showProductForm());
        btnQuanLyHoaDon.addActionListener(e -> showFormHoaDon());
        btnThongTinKhachHang.addActionListener(e -> showCustomerForm());
    }

    private void setupPanelFunction() {
        // Create a panel for the logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(159, 196, 224)); // Match the panel background color

        // Load the logo image and resize it
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\quinh\\IdeaProjects\\QLDT\\src\\Image\\logo.jpg");
        // Resize the logo to fit the panel width (200 pixels) and maintain the aspect ratio
        Image scaledLogo = logoIcon.getImage().getScaledInstance(130, -1, Image.SCALE_SMOOTH); // -1 to maintain aspect ratio
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoPanel.add(logoLabel); // Add logo to the logo panel

        // Add the logo panel to the top of the function panel
        panelFunction.add(logoPanel); // Add logo panel first

        // Create buttons and add them to the function panel
        btnQuanLyNhanVien = createButton("Quản Lý Nhân Viên", "C:\\Users\\quinh\\IdeaProjects\\QLDT\\src\\Image\\ql.png");
        btnQuanLySanPham = createButton("Quản Lý Sản Phẩm", "C:\\Users\\quinh\\IdeaProjects\\QLDT\\src\\Image\\package.png");
        btnQuanLyHoaDon = createButton("Quản Lý Hóa Đơn", "C:\\Users\\quinh\\IdeaProjects\\QLDT\\src\\Image\\bill.png");
        btnThongTinKhachHang = createButton("Thông Tin Khách Hàng", "C:\\Users\\quinh\\IdeaProjects\\QLDT\\src\\Image\\service.png");

        // Add buttons to the function panel
        panelFunction.add(btnQuanLyNhanVien);
        panelFunction.add(btnQuanLySanPham);
        panelFunction.add(btnQuanLyHoaDon);
        panelFunction.add(btnThongTinKhachHang);
    }

    private void configureButtonsForRole(String role) {
        if (role.equals("Nhân Viên")) {
            btnQuanLySanPham.setEnabled(false);
            btnQuanLyHoaDon.setEnabled(false);
        } else if (role.equals("Khách Hàng")) {
            btnQuanLyNhanVien.setEnabled(false);
            btnQuanLySanPham.setEnabled(false);
            btnQuanLyHoaDon.setEnabled(false);
        }
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);

        // Set button properties for a cleaner look
        button.setPreferredSize(new Dimension(120, 30)); // Adjust width and height here
        button.setBorderPainted(false); // Remove border
        button.setContentAreaFilled(false); // Make the button background transparent
        button.setFocusPainted(false); // Remove focus indicator

        // Change the size of the icon and add it to the button
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust icon size here
        button.setIcon(new ImageIcon(scaledImage)); // Add icon
        button.setHorizontalTextPosition(SwingConstants.RIGHT); // Set text position

        // Optional: Set text color if needed
        button.setForeground(Color.BLACK); // Change text color

        return button;
    }

    private void showEmployeeForm() {
        panelContent.removeAll();
        EmployeeForm employeeForm = new EmployeeForm();
        panelContent.add(employeeForm, BorderLayout.CENTER);
        panelContent.revalidate();
        panelContent.repaint();
    }

    private void showProductForm() {
        panelContent.removeAll();
        ProductForm productForm = new ProductForm();  // Khởi tạo form Quản Lý Sản Phẩm
        panelContent.add(productForm.getContentPane(), BorderLayout.CENTER);  // Hiển thị nội dung form
        panelContent.revalidate();
        panelContent.repaint();
    }

    private void showFormHoaDon() {
        panelContent.removeAll();
        FormHoaDon formHoaDon = new FormHoaDon();  // Khởi tạo form Quản Lý Sản Phẩm
        panelContent.add(formHoaDon.getContentPane(), BorderLayout.CENTER);  // Hiển thị nội dung form
        panelContent.revalidate();
        panelContent.repaint();
    }

    private void showCustomerForm() {
        panelContent.removeAll();
        CustomerForm customerForm = new CustomerForm();  // Khởi tạo form Quản Lý Khách Hàng
        panelContent.add(customerForm.getContentPane(), BorderLayout.CENTER);  // Hiển thị nội dung form
        panelContent.revalidate();
        panelContent.repaint();

        // Thêm dòng sau để đóng form khi không cần hiển thị nữa
        customerForm.dispose();
    }


    private void showContent(String content) {
        panelContent.removeAll();
        JLabel label = new JLabel(content, SwingConstants.CENTER);
        panelContent.add(label, BorderLayout.CENTER);
        panelContent.revalidate();
        panelContent.repaint();
    }

    //public static void main(String[] args) {
    //  TrangChu trangChu = new TrangChu("Quản Lý");
    //    trangChu.setVisible(true);
    //}
}