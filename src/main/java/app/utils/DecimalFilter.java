package app.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DecimalFilter extends DocumentFilter {

    public DecimalFilter() {
        super();
    }

    // Cho phép số và dấu chấm (dùng cho giá)
    private static final String VALID_CHARS = "0123456789";

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
            throws BadLocationException {
        if (text == null)
            return;

        if (text.chars().allMatch(c -> VALID_CHARS.indexOf(c) != -1)) {
            super.insertString(fb, offset, text, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }

        if (text.chars().allMatch(c -> VALID_CHARS.indexOf(c) != -1)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    public static DecimalFormat PriceFormatter() {
        DecimalFormat currencyFormatter = new DecimalFormat("#,###");
        return currencyFormatter;
    }

    public static Number PriceParser(String numberString) {
        try {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            Number parsedNumber = format.parse(numberString);
            return parsedNumber;

        } catch (ParseException e) {
            System.err.println("Lỗi định dạng số: Chuỗi không hợp lệ - " + e.getMessage());
            return null;
        }
    }

    public static BigDecimal parseToBigDecimal(Object value) throws ParseException {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }

        String valueStr = value.toString().trim();
        if (valueStr.isEmpty()) {
            return BigDecimal.ZERO;
        }

        NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
        Number parsedNumber = format.parse(valueStr);

        return new BigDecimal(parsedNumber.toString());
    }
}
