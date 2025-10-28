package app.GUI.PromotionGUI;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import app.BUS.PromotionBUS;
import app.BUS.CategoryBUS;
import app.BUS.BrandBUS;
import app.DTO.Brand;
import app.DTO.Category;
import app.DTO.Promotion;

public class UpdatePromotionFrame extends JFrame {

    private JRadioButton valueRadio;
    private JRadioButton percentRadio;
    private JTextField txtCode, txtValue, txtQuantity;
    private JTextArea txtDescription;
    private JDateChooser dateApply, dateExpire;
    private JComboBox<app.DTO.Category> categoryComboBox;
	private JComboBox<app.DTO.Brand> brandComboBox;
    private BrandBUS brandBUS;
    private CategoryBUS categoryBUS;
    private PromotionBUS promotionBUS;
    private int promotionId;
    private qlkhuyenmai promotionPanel;

    public UpdatePromotionFrame(String title, qlkhuyenmai promotionPanel, int promotionId) {
        super(title);
        this.promotionPanel = promotionPanel;
        this.promotionId = promotionId;
        promotionBUS = new PromotionBUS();
        brandBUS = new BrandBUS();
        categoryBUS = new CategoryBUS();
        initializeUI();
        loadInitialData();
    }

    private void initializeUI() {
        setSize(700, 350);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel RadioPanel = createRadioPanel();
        add(RadioPanel, BorderLayout.NORTH);

        JPanel PromotionPanel = createPromotionPanel();
        add(PromotionPanel, BorderLayout.CENTER);

        JPanel ButtonPanel = createButtonPanel();
        add(ButtonPanel, BorderLayout.SOUTH);
    }

    private JPanel createRadioPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Chọn loại khuyến mãi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        valueRadio = new JRadioButton("Khuyến mãi giá trị");
        percentRadio = new JRadioButton("Khuyến mãi phần trăm");

        ButtonGroup group = new ButtonGroup();
        group.add(valueRadio);
        group.add(percentRadio);

        gbc.gridx = 0;
        panel.add(valueRadio, gbc);
        gbc.gridx = 1;
        panel.add(percentRadio, gbc);

        return panel;
    }

    private JPanel createPromotionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin khuyến mãi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(100, 25);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblCode = new JLabel("Mã khuyến mãi:");
        lblCode.setPreferredSize(labelSize);
        panel.add(lblCode, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        txtCode = new JTextField(15);
        panel.add(txtCode, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblValue = new JLabel("Giá trị:");
        lblValue.setPreferredSize(labelSize);
        panel.add(lblValue, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        txtValue = new JTextField(15);
        panel.add(txtValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblApplyDate = new JLabel("Ngày áp dụng:");
        lblApplyDate.setPreferredSize(labelSize);
        panel.add(lblApplyDate, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        dateApply = new JDateChooser();
        dateApply.setDateFormatString("yyyy-MM-dd");
        dateApply.setPreferredSize(new Dimension(200, 25));
        panel.add(dateApply, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel lblExpireDate = new JLabel("Hạn sử dụng:");
        lblExpireDate.setPreferredSize(labelSize);
        panel.add(lblExpireDate, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1;
        dateExpire = new JDateChooser();
        dateExpire.setDateFormatString("yyyy-MM-dd");
        dateExpire.setPreferredSize(new Dimension(200, 25));
        panel.add(dateExpire, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblQuantity = new JLabel("Số lượng:");
        lblQuantity.setPreferredSize(labelSize);
        panel.add(lblQuantity, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        txtQuantity = new JTextField(20);
        panel.add(txtQuantity, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblBrand = new JLabel("Hãng:");
        lblBrand.setPreferredSize(labelSize);
        panel.add(lblBrand, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        brandComboBox = new JComboBox<>();
        panel.add(brandComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblCategory = new JLabel("Danh mục:");
        lblCategory.setPreferredSize(labelSize);
        panel.add(lblCategory, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        categoryComboBox = new JComboBox<>();
        panel.add(categoryComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel lblDescription = new JLabel("Mô tả:");
        lblDescription.setPreferredSize(labelSize);
        panel.add(lblDescription, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 1; 
        gbc.fill = GridBagConstraints.BOTH;

        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JScrollPane scrollPane = new JScrollPane(txtDescription);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());

        panel.add(btnSave);
        panel.add(btnCancel);
        return panel;
    }

    private void loadInitialData() {
        List<Brand> brands = brandBUS.getAllBrands();
        for (Brand brand : brands) {
            brandComboBox.addItem(brand);
        }

        List<Category> categories = categoryBUS.getAllCategories();
        for (Category category : categories) {
            categoryComboBox.addItem(category);
        }

        Promotion promotion = promotionBUS.getPromotionById(promotionId);
        if (promotion != null) {
            txtCode.setText(promotion.getCode());
            txtValue.setText(promotion.isPercent() ? String.valueOf(promotion.getPercent()) : promotion.getValue().toString());
            dateApply.setDate(new java.util.Date(promotion.getStartDate().getTime()));
            dateExpire.setDate(new java.util.Date(promotion.getExpirationDate().getTime()));
            txtQuantity.setText(String.valueOf(promotion.getQuantity()));
            txtDescription.setText(promotion.getDescription());

            for (int i = 0; i < brandComboBox.getItemCount(); i++) {
                if (brandComboBox.getItemAt(i).getId() == promotion.getBrandId()) {
                    brandComboBox.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < categoryComboBox.getItemCount(); i++) {
                if (categoryComboBox.getItemAt(i).getCategoryId() == promotion.getCategoryId()) {
                    categoryComboBox.setSelectedIndex(i);
                    break;
                }
            }

            if (promotion.isPercent()) {
                percentRadio.setSelected(true);
            } else {
                valueRadio.setSelected(true);
            }
        }
    }

    private void onSave() {
        try {
            Promotion promotion = promotionBUS.getPromotionById(promotionId);
            if (promotion == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String code = txtCode.getText().trim();
            String description = txtDescription.getText().trim();
            boolean isPercent = percentRadio.isSelected();
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            java.util.Date startDate = dateApply.getDate();
            java.util.Date endDate = dateExpire.getDate();

            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khuyến mãi!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txtQuantity.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày áp dụng và hạn sử dụng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this, "Hạn sử dụng phải sau ngày áp dụng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Brand selectedBrand = (Brand) brandComboBox.getSelectedItem();
            Category selectedCategory = (Category) categoryComboBox.getSelectedItem();
            int brandId = (selectedBrand != null) ? selectedBrand.getId() : 0;
            int categoryId = (selectedCategory != null) ? selectedCategory.getCategoryId() : 0;

            BigDecimal value = BigDecimal.ZERO;
            int percent = 0;

            if (isPercent) {
                try {
                    percent = Integer.parseInt(txtValue.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Phần trăm khuyến mãi không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                try {
                    value = new BigDecimal(txtValue.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Giá trị khuyến mãi không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            promotion.setCode(code);
            promotion.setDescription(description);
            promotion.setIsPercent(isPercent);
            promotion.setValue(value);
            promotion.setPercent(percent);
            promotion.setQuantity(quantity);
            promotion.setStartDate(startDate);
            promotion.setExpirationDate(endDate);
            promotion.setBrandId(brandId);
            promotion.setCategoryId(categoryId);

            int result = promotionBUS.updatePromotion(promotion);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật khuyến mãi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}