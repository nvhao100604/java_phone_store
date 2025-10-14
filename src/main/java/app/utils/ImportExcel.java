package app.utils;

import java.io.File;

import javax.swing.JFileChooser;

public class ImportExcel {
    public static String chooseFile() {
        File selectedFile = null;
        String selectedImagePath = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file danh sách");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "File Excel (*.xlsx, *.xls)", "xlsx", "xls"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
        }
        return selectedImagePath;
    }
}
