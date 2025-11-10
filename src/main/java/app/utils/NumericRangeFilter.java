package app.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Một DocumentFilter chỉ cho phép nhập các số nguyên (integer)
 * nằm trong một khoảng (range) min/max xác định.
 */
public class NumericRangeFilter extends DocumentFilter {

    private int min, max;

    public NumericRangeFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (isValid(fb.getDocument(), offset, 0, string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (isValid(fb.getDocument(), offset, length, text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        Document doc = fb.getDocument();
        String currentText = doc.getText(0, doc.getLength());
        String newText = currentText.substring(0, offset) + currentText.substring(offset + length);

        if (newText.isEmpty() || isValid(newText)) {
            super.remove(fb, offset, length);
        }
    }

    /**
     * Kiểm tra xem văn bản *kết quả* (sau khi thay đổi) có hợp lệ không.
     */
    private boolean isValid(Document doc, int offset, int length, String text) {
        try {
            String currentText = doc.getText(0, doc.getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

            return isValid(newText);

        } catch (BadLocationException e) {
            return false;
        }
    }

    /**
     * Hàm kiểm tra cốt lõi.
     */
    private boolean isValid(String text) {
        if (text.isEmpty()) {
            return true;
        }

        try {
            int value = Integer.parseInt(text);

            return (value >= min && value <= max);

        } catch (NumberFormatException e) {
            return false;
        }
    }
}