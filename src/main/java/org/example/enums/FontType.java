package org.example.enums;

import java.awt.*;

public enum FontType {
    PLAIN(Font.PLAIN),
    BOLD(Font.BOLD),
    ITALIC(Font.ITALIC),
    BOLD_ITALIC(Font.BOLD | Font.ITALIC);

    private final int style;

    FontType(int style) {
        this.style = style;
    }

    public int getStyle() {
        return style;
    }
}
