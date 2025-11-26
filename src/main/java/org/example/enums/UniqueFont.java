package org.example.enums;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public enum UniqueFont {



    SALSA   {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/Salsa-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        }
    },


    INDIE_FLOWER    {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/IndieFlower-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, fontSize);
        }
    },

    FUGAZ_ONE   {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/FugazOne-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        }
    },

    MONOFETT    {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/Monofett-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        }
    },

    AVERIA_SERIF_LIBRE    {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/AveriaSerifLibre-Bold.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(fontType.getStyle(), fontSize);
        }
    },

    PERMANENT_MARKER    {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/PermanentMarker-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        }
    },

    IBMPLEXSANS_SEMIBOLD_ITALIC {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/IBMPlexSans-SemiBoldItalic.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(fontType.getStyle(), fontSize);
        }
    },

    LILITA_ONE   {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/LilitaOne-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(fontType.getStyle(), fontSize);
        }
    },

    ACLONICA    {
        @Override
        public Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException {
            File fontFile = new File("src/main/resources/font/Aclonica-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(fontType.getStyle(), fontSize);
        }
    };

    public abstract Font createUniqueFont(FontType fontType, Integer fontSize) throws IOException, FontFormatException;

    private UniqueFont(){
        System.out.println("Construcotr called for: " + this.toString());
    }
}
