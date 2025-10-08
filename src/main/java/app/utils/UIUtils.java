package app.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class UIUtils {

    public static void setFontRecursively(Container container, Font font) {
        for (Component c : container.getComponents()) {
            c.setFont(font);
            if (c instanceof Container) {
                setFontRecursively((Container) c, font);
            }
        }
    }

    public static void setGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
