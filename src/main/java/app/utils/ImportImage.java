package app.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class ImportImage {
    private static final String directoryPath = "src/main/resources/images/products";

    public static File chooseImage(JLabel imagePreviewLabel) {
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Ảnh (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            String selectedImagePath = selectedFile.getAbsolutePath();
            copyImage(selectedFile);
            setPreviewImage(selectedImagePath, imagePreviewLabel);
        }
        return selectedFile;
    }

    public static void setPreviewImage(String imagePath, JLabel imagePreviewLabel) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        imagePreviewLabel.setIcon(new ImageIcon(img));
        imagePreviewLabel.setText(null);
    }

    public static void copyImage(File selectedFile) {
        File destinationDir = new File(directoryPath);

        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        Path sourcePath = selectedFile.toPath();
        Path destinationPath = Paths.get(destinationDir.getAbsolutePath(), selectedFile.getName());

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean createImagesFolder() {
        Path images_path = Paths.get(directoryPath);
        // Path exp_path = Paths.get(directoryPath + "/");
        try {
            Files.createDirectories(images_path);
            // Files.createDirectories(exp_path);
            System.out.println("Tạo thư mục thành công hoặc thư mục đã tồn tại: " + directoryPath);
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi khi tạo thư mục: " + e.getMessage());
            return false;
        }
    }
}
