package app.utils;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class ImportImage {

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
        String destinationFolderName = "resources/images/products";
        File destinationDir = new File(destinationFolderName);

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
}
