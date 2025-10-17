package app.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class qlkhuyenmai_gdthem{

    private JFrame frame;
    private JTextField txtTenSP;
    private JComboBox<String> cbLoaiSP;
    private JFormattedTextField txtGiaNhap;
    private JFormattedTextField txtGiaBan;
    private JTable tableChiTiet;
    private JTextField textField;
    private JTextField textField_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                qlkhuyenmai_gdthem window = new qlkhuyenmai_gdthem();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public qlkhuyenmai_gdthem() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Thêm Khuyến Mãi");
        frame.setSize(750, 580);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null); // Absolute layout

        // Panel thông tin cơ bản
        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setLayout(null);
        pnlThongTin.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(), 
                "Thông tin khuyến mãi", TitledBorder.LEFT, TitledBorder.TOP));
        pnlThongTin.setBounds(10, 10, 710, 150);
        frame.getContentPane().add(pnlThongTin);

        // --- Tên SP ---
        JLabel lblTenSP = new JLabel("Giá trị KM:");
        lblTenSP.setBounds(20, 25, 60, 25);
        pnlThongTin.add(lblTenSP);

        txtTenSP = new JTextField();
        txtTenSP.setBounds(126, 25, 219, 25);
        pnlThongTin.add(txtTenSP);

        // --- Loại SP ---
        JLabel lblLoaiSP = new JLabel("Đơn vị:");
        lblLoaiSP.setBounds(355, 25, 60, 25);
        pnlThongTin.add(lblLoaiSP);

        cbLoaiSP = new JComboBox<>(new String[]{"%", "VND"});
        cbLoaiSP.setBounds(457, 25, 232, 25);
        pnlThongTin.add(cbLoaiSP);

        // --- Giá nhập ---
        JLabel lblGiaNhap = new JLabel("Code KM:");
        lblGiaNhap.setBounds(20, 65, 60, 25);
        pnlThongTin.add(lblGiaNhap);

        txtGiaNhap = new JFormattedTextField(java.text.NumberFormat.getNumberInstance());
        txtGiaNhap.setValue(0.00);
        txtGiaNhap.setBounds(126, 65, 219, 25);
        pnlThongTin.add(txtGiaNhap);

        // --- Giá bán ---
        JLabel lblGiaBan = new JLabel("Số lượng:");
        lblGiaBan.setBounds(355, 65, 60, 25);
        pnlThongTin.add(lblGiaBan);

        txtGiaBan = new JFormattedTextField(java.text.NumberFormat.getNumberInstance());
        txtGiaBan.setValue(0.00);
        txtGiaBan.setBounds(457, 65, 232, 25);
        pnlThongTin.add(txtGiaBan);

        // --- Hãng SX ---
        JLabel lblHangSX = new JLabel("Ngày áp dụng:");
        lblHangSX.setBounds(20, 105, 85, 25);
        pnlThongTin.add(lblHangSX);

        // --- Mô tả ---
        JLabel lblMoTa = new JLabel("Hạn sử dụng:");
        lblMoTa.setBounds(355, 105, 85, 25);
        pnlThongTin.add(lblMoTa);
        
        textField = new JTextField();
        textField.setBounds(457, 108, 232, 19);
        pnlThongTin.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(126, 108, 219, 19);
        pnlThongTin.add(textField_1);
        textField_1.setColumns(10);

        // Panel chi tiết
        JPanel pnlChiTiet = new JPanel();
        pnlChiTiet.setLayout(null);
        pnlChiTiet.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(),
                "Chi tiết khuyến mãi", TitledBorder.LEFT, TitledBorder.TOP));
        pnlChiTiet.setBounds(10, 170, 710, 300);
        frame.getContentPane().add(pnlChiTiet);

        // Nút "Thêm Chi tiết" và "Xóa Hàng chọn"
        JButton btnThemChiTiet = new JButton("Thêm Chi tiết");
        btnThemChiTiet.setBounds(470, 20, 110, 25);
        pnlChiTiet.add(btnThemChiTiet);

        JButton btnXoaHang = new JButton("Xóa Hàng chọn");
        btnXoaHang.setBounds(590, 20, 110, 25);
        pnlChiTiet.add(btnXoaHang);

        // Bảng chi tiết sản phẩm
        String[] columns = {"Hãng", "Danh Mục", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableChiTiet = new JTable(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null},
        		{null, null, null, null},
        	},
        	new String[] {
        		"H\u00E3ng", "Danh M\u1EE5c", "Tr\u1EA1ng Th\u00E1i", "M\u00F4 T\u1EA3"
        	}
        ));
        JScrollPane scrollTable = new JScrollPane(tableChiTiet);
        scrollTable.setBounds(15, 60, 680, 220);
        pnlChiTiet.add(scrollTable);

        // --- Nút Lưu và Hủy ---
        JButton btnLuu = new JButton("Lưu Khuyến Mãi");
        btnLuu.setBounds(270, 490, 120, 30);
        frame.getContentPane().add(btnLuu);

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setBounds(410, 490, 80, 30);
        frame.getContentPane().add(btnHuy);

        // --- Sự kiện ---
        btnThemChiTiet.addActionListener(e -> {
            model.addRow(new Object[]{"Đen", "128GB", "0.00", "0"});
        });

        btnXoaHang.addActionListener(e -> {
            int row = tableChiTiet.getSelectedRow();
            if (row >= 0) model.removeRow(row);
            else JOptionPane.showMessageDialog(frame, "Hãy chọn dòng cần xóa!");
        });

        btnLuu.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Đã lưu sản phẩm (demo)."));

        btnHuy.addActionListener(e -> frame.dispose());
    }
}