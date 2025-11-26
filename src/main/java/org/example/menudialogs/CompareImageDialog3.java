package org.example.menudialogs;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class CompareImageDialog3 extends JFrame {

    private JScrollPane imageScrollPane;
    private ImagePanel imagePanel;

    public CompareImageDialog3() throws IOException, UnsupportedLookAndFeelException {
        setTitle("Image Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Apply FlatLaf Theme
        UIManager.setLookAndFeel(new FlatDarkLaf());

        // **BUTTON to Open Report in Separate Window**
        JButton openReportButton = new JButton("Open Report");
        openReportButton.setFont(new Font("Consolas", Font.PLAIN, 14));
        openReportButton.setBackground(new Color(52, 152, 219));
        openReportButton.setForeground(Color.WHITE);
        openReportButton.setFocusPainted(false);
        openReportButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        openReportButton.addActionListener(e -> ReportViewer.openReport());

        // **IMAGE PANEL** (Custom repainting & dynamic resizing)
        imagePanel = new ImagePanel();
        imageScrollPane = new JScrollPane(imagePanel);
        imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        imageScrollPane.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
        imageScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Load images
        loadImages();

        // **Repaint & Resize Images when window resizes**
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                imagePanel.resizeImages(getWidth()); // Resize based on new width
            }
        });

        // **Main Panel**
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(openReportButton, BorderLayout.NORTH);
        mainPanel.add(imageScrollPane, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(30, 30, 30));

        add(mainPanel);
        setVisible(true);
    }

    private void loadImages() {
        File imageDir = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_same_pics\\qa2_pl");
        if (imageDir.exists() && imageDir.isDirectory()) {
            File[] imageFiles = imageDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
            if (imageFiles != null) {
                for (File file : imageFiles) {
                    try {
                        BufferedImage img = ImageIO.read(file);
                        imagePanel.addImage(img);
                    } catch (IOException e) {
                        System.err.println("Error loading image: " + file.getName());
                    }
                }
            }
        }
    }

    // **Custom JPanel for Smooth Repainting & Resizing**
    private static class ImagePanel extends JPanel {
        private java.util.List<BufferedImage> images = new java.util.ArrayList<>();
        private java.util.List<BufferedImage> resizedImages = new java.util.ArrayList<>();
        private int lastWidth = -1;

        public void addImage(BufferedImage image) {
            images.add(image);
            resizeImages(getWidth()); // Ensure resizing immediately
        }

        public void resizeImages(int panelWidth) {
            if (panelWidth <= 20 || panelWidth == lastWidth) return; // Avoid resizing if the width is too small or unchanged
            lastWidth = panelWidth;

            resizedImages.clear();
            for (BufferedImage img : images) {
                // Ensure a minimum width of 20 for images, otherwise prevent resizing
                int newWidth = Math.max(panelWidth - 40, 20); // Leave padding, and enforce minimum width of 20
                int newHeight = (int) (((double) img.getHeight() / img.getWidth()) * newWidth);

                if (newHeight <= 0 || newWidth <= 0) {
                    continue; // Skip invalid sizes (non-positive)
                }

                BufferedImage scaledImg = new BufferedImage(newWidth, newHeight, img.getType());
                Graphics2D g2d = scaledImg.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
                g2d.dispose();
                resizedImages.add(scaledImg);
            }

            setPreferredSize(new Dimension(panelWidth, calculateTotalHeight()));
            revalidate();
            repaint();
        }

        private int calculateTotalHeight() {
            int totalHeight = 10;
            for (BufferedImage img : resizedImages) {
                totalHeight += img.getHeight() + 10;
            }
            return totalHeight;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int y = 10;
            for (BufferedImage img : resizedImages) {
                g2d.drawImage(img, 10, y, img.getWidth(), img.getHeight(), null);
                y += img.getHeight() + 10;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new CompareImageDialog3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
