package org.example.menu;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.animations.CustomIcon;
import org.example.animations.MyPanel;
import org.example.animations.ScreenImage;
import org.example.enums.FontType;
import org.example.enums.UniqueFont;
import org.example.mayuse.MyPanel2;
import org.example.menudialogs.*;
import org.example.utils.DeleteByCode;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class WindowMenu {

    private Set<String> addedModel = new HashSet<>();
    private Set<String> runMethodNames = new HashSet<>();
    private Set<String> methodsWithFailedImages = new HashSet<>();

    public WindowMenu(Set<String> addedModel, Set<String> runMethodNames, Set<String> methodsWithFailedImages){
        this.addedModel = addedModel;
        this.runMethodNames = runMethodNames;
        this.methodsWithFailedImages = methodsWithFailedImages;
    }

    public WindowMenu(){

    }

    private static  Queue<JMenu> allMenus = new LinkedList<>();

    private final CompareImage compareImage = new CompareImage();
    JFrame window = new JFrame("Képek böngészése");
    JMenuBar menuBar = new JMenuBar();

    JMenu menu1;
    public JMenu getMenu1(){
        return this.menu1;
    }



    JButton customButton = new JButton("Cool");
    public void run() throws IOException, FontFormatException, UnsupportedLookAndFeelException {

        /*FlatIntelliJLaf.install();
        UIManager.setLookAndFeel(new FlatIntelliJLaf());*/

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int windowWidth = window.getWidth();
        int windowHeight = window.getHeight();

        //JMenu fileMenu = new CustomMenu("Fájl", Color.YELLOW);
        JMenu fileMenu = new JMenu("Fájl");
        JMenuItem exitMenuItem = new JMenuItem("Kilépés");
        exitMenuItem.setFont(UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD, 18));
        exitMenuItem.setBackground(Color.BLACK);
        exitMenuItem.setForeground(Color.WHITE);
        exitMenuItem.setBorder(new LineBorder(Color.WHITE));
        exitMenuItem.setMargin(new Insets(16,16,16,32));

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteImagesProperties("src/main/resources/images.properties");
                deleteImagesProperties("src/main/resources/images_copy.properties");

                System.exit(0);
            }
        });

        JMenuItem openReportItem = new JMenuItem("Tesztjelentés megnyitása");
        openReportItem.setFont(UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD, 18));
        openReportItem.setBackground(Color.BLACK);
        openReportItem.setForeground(Color.WHITE);
        openReportItem.setBorder(new LineBorder(Color.WHITE));
        openReportItem.setMargin(new Insets(16,16,16,32));

        openReportItem.addActionListener(e -> new TestReportDialog());

        JMenuItem openHTMLReportItem = new JMenuItem("HTML Report");
        openHTMLReportItem.setFont(UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD, 18));
        openHTMLReportItem.setBorder(new LineBorder(Color.BLACK));
        openHTMLReportItem.setMargin(new Insets(16,16,16,32));

        openHTMLReportItem.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.html"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        fileMenu.add(openReportItem);
        fileMenu.add(openHTMLReportItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(Box.createRigidArea(new Dimension(5,0)));
        menuBar.add(fileMenu);
        menuBar.add(Box.createRigidArea(new Dimension(10,0)));


        // Első menü
        menu1 = new JMenu("ORIDZSINÁL PICS");
        JMenuItem menu1Item = new JMenuItem("Eredeti képek betöltése");
        menu1Item.setFont(UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD, 18));
        menu1Item.setBorder(new LineBorder(Color.WHITE));
        menu1Item.setMargin(new Insets(16,16,16,32));
        menu1Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //loadImagesFromDirectory("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures");
                String thDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\qa2_pl";
                String plOutOutDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\qa2_pl";
                String plInDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\qa2_pl";
                String gbDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\uat_gb";

                if (addedModel.contains("TH")) {
                    loadImagesFromDirectoryBasedOnMethodName2(thDirectory);
                }
                if (addedModel.contains("PLIN")) {
                    loadImagesFromDirectoryBasedOnMethodName2(plInDirectory);
                }
                if (addedModel.contains("PLOUT")) {
                    loadImagesFromDirectoryBasedOnMethodName2(plOutOutDirectory);
                }
                if (addedModel.contains("GB")) {
                    loadImagesFromDirectoryBasedOnMethodName2(gbDirectory);
                }
            }
        });


        menu1.add(menu1Item);
        menu1.add(new JSeparator());
        menuBar.add(menu1);
        menuBar.add(Box.createRigidArea(new Dimension(10,0)));

        // Második menü
        JMenu menu2 = new JMenu("ACTUAL PICS");
        JMenuItem menu2Item = new JMenuItem("Aktuális képek betöltése");
        menu2Item.setFont(UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD,18));
        menu2Item.setBorder(new LineBorder(Color.WHITE));
        menu2Item.setMargin(new Insets(16,16,16,32));
        menu2Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //loadImagesFromDirectory("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime");
                String thDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime";
                String plOutOutDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime";
                String plInDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime";
                String gbDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\uat_gb_overtime";

                if (addedModel.contains("TH")) {
                    loadImagesFromDirectoryBasedOnMethodName2(thDirectory);
                }
                if (addedModel.contains("PLIN")) {
                    loadImagesFromDirectoryBasedOnMethodName2(plInDirectory);
                }
                if (addedModel.contains("PLOUT")) {
                    loadImagesFromDirectoryBasedOnMethodName2(plOutOutDirectory);
                }
                if (addedModel.contains("GB")) {
                    loadImagesFromDirectoryBasedOnMethodName2(gbDirectory);
                }
            }
        });
        menu2.add(menu2Item);
        menu2.add(new JSeparator());
        menuBar.add(menu2);
        menuBar.add(Box.createRigidArea(new Dimension(10,0)));

        // Harmadik menü
        JMenu menu3 = new JMenu("DIFFERENTS PICS");
        JMenuItem menu3Item = new JMenuItem("Diff Képek");
        menu3Item.setFont(UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD,18));
        menu3Item.setBorder(new LineBorder(Color.WHITE));
        menu3Item.setMargin(new Insets(16,16,16,32));
        menu3Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (addedModel.contains("PLIN")){
                    loadImagesFromDirectoryBasedOnMethodName("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl", methodsWithFailedImages);
                }
                if (addedModel.contains("GB")){
                    loadImagesFromDirectoryBasedOnMethodName("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb", methodsWithFailedImages);
                }
            }
        });
        menu3.add(menu3Item);
        menuBar.add(menu3);
        menuBar.add(Box.createRigidArea(new Dimension(10,0)));

        // Negyedik menü
        JMenu fileMenu2 = new JMenu("SAME PICS");

        //TODO just like different image
        JMenuItem sameImagesItem = new JMenuItem("Same fájlok megnyitása");
        sameImagesItem.setFont(UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD,18));
        sameImagesItem.setBorder(new LineBorder(Color.WHITE));
        sameImagesItem.setMargin(new Insets(16,16,16,32));
        sameImagesItem.addActionListener(e -> {
            try {
                new CompareImageDialog3();
            } catch (IOException | UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            }
        });

        /*sameImagesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CompareImageDialog();
                loadImagesFromDirectory("/Users/sheriff/Documents/same_demopics");
            }
        });*/

        fileMenu2.add(sameImagesItem);
        menuBar.add(fileMenu2);

        allMenus.add(fileMenu);
        allMenus.add(menu1);
        allMenus.add(menu2);
        allMenus.add(fileMenu2);
        allMenus.add(menu3);
        formatMenus();

        window.setJMenuBar(menuBar);

        ScreenImage shellImage = new ScreenImage("shell_image_icon.png");
        shellImage.setCoordinates(0,0);
        shellImage.setVelocity(1,2);
        shellImage.setForegroundImageSize(100,100);

        ScreenImage shellImageReverseMove = new ScreenImage("shell_image_icon.png");
        shellImageReverseMove.setCoordinates(0,400);
        shellImageReverseMove.setVelocity(1,2);
        shellImageReverseMove.setForegroundImageSize(100,100);

        ScreenImage wonderLineImage = new ScreenImage("wonderline_imgicon.png");
        wonderLineImage.setCoordinates(30,300);
        wonderLineImage.setVelocity(2,1);
        wonderLineImage.setForegroundImageSize(120,120);


        //TODO THIS WORKS FINE FOR ONE IMAGE ON THE PANEL
       /* MyPanel animationPanel = new MyPanel("shellsonbeach.jpg" );

        animationPanel.addRectangles();
        //animationPanel.initializeImageOnPanel(wonderLineImage);
        animationPanel.initializeImageOnPanel(shellImage);


        window.getContentPane().setLayout(new GridLayout());
        window.getContentPane().add(animationPanel, BorderLayout.CENTER);

        window.getContentPane().setBackground(Color.WHITE);
        //window.getContentPane().add(new Canvas());
        window.pack();
        window.setVisible(true);*/

        MyPanel2 leftAnimationPanel = new MyPanel2("shellsonbeach.jpg");
        leftAnimationPanel.setPANEL_WIDTH(100);
        leftAnimationPanel.initializeImageOnPanel(shellImage);
        MyPanel2 leftAdditionalAnimationPanel = new MyPanel2("shellsonbeach.jpg");
        leftAdditionalAnimationPanel.setPANEL_WIDTH(100);
        leftAdditionalAnimationPanel.setBorder(new LineBorder(Color.BLACK));
        leftAdditionalAnimationPanel.initializeImageOnPanel(shellImageReverseMove);

        MyPanel2 middleAnimationPanel = new MyPanel2("shellsonbeach.jpg");
        middleAnimationPanel.setPANEL_WIDTH(440);
        middleAnimationPanel.initializeImageOnPanel(wonderLineImage);

        MyPanel2 rightAnimationPanel = new MyPanel2("shellsonbeach.jpg");
        rightAnimationPanel.setPANEL_WIDTH(100);
        rightAnimationPanel.initializeImageOnPanel(shellImage);
        MyPanel2 rightAdditionalAnimationPanel = new MyPanel2("shellsonbeach.jpg");
        rightAdditionalAnimationPanel.setPANEL_WIDTH(100);
        rightAdditionalAnimationPanel.initializeImageOnPanel(shellImageReverseMove);


        JPanel panelSouth = new JPanel();
        //panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.X_AXIS));
        panelSouth.setLayout(new FlowLayout());
        panelSouth.add(leftAnimationPanel);
        panelSouth.add(leftAdditionalAnimationPanel);
        panelSouth.add(middleAnimationPanel);
        panelSouth.add(rightAdditionalAnimationPanel);
        panelSouth.add(rightAnimationPanel);

        window.getContentPane().setLayout(new GridLayout());
        window.add(panelSouth);
        /*window.getContentPane().add(leftAnimationPanel, FlowLayout.LEFT);
        window.getContentPane().add(middleAnimationPanel, FlowLayout.CENTER);
        window.getContentPane().add(rightAnimationPanel, FlowLayout.RIGHT);*/

        window.pack();
        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new DeleteByCode("apple");
                new DeleteByCode("counter");
                new DeleteByCode("counter_output");
                new DeleteByCode("properties");
                //deleteImagesProperties("src/main/resources/images.properties");
                //deleteImagesProperties("src/main/resources/images_copy.properties");

            }
        });
    }

    private void loadImagesFromDirectory(String directoryPath) {

        /*Set<String> addedCountries = this.addedModel;

        IntStream.range(0, addedCountries.size()).forEach(i -> {
            case(addedCountries.)
        });*/

        BufferedImage[] allImages;

        File path = new File(directoryPath);
        File[] allFiles = path.listFiles();

        if (allFiles != null) {
            for (File file : allFiles) {
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    if (file.delete()) {
                        System.out.println("A(z) " + file.getName() + " fájl sikeresen törölve.");
                    } else {
                        System.out.println("Nem sikerült törölni a(z) " + file.getName() + " fájlt.");
                    }
                }
            }
        }
        assert allFiles != null;
        Arrays.stream(allFiles)
                .forEach(file -> System.out.println(file.getName()));
        allImages = new BufferedImage[allFiles.length];

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel[] label = new JLabel[allFiles.length];

        for(int i = 0; i < allFiles.length; i++){
            try{
                allImages[i] = ImageIO.read(allFiles[i]);
                label[i] = new JLabel();
                ImageIcon icon = new ImageIcon(allImages[i].getScaledInstance(-1, -1, Image.SCALE_SMOOTH));
                label[i].setIcon(icon);
                panel.add(label[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        panel.setBackground(Color.WHITE);
        JFrame frame = new JFrame("Képek böngészése");
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadImagesFromDirectoryBasedOnMethodName(String directoryPath, Set<String> methodNames) {

        /*Set<String> addedCountries = this.addedModel;

        IntStream.range(0, addedCountries.size()).forEach(i -> {
            case(addedCountries.)
        });*/
        BufferedImage[] allImages;

        System.out.println(methodNames);
        Iterator<String> iterator = methodNames.iterator();
        System.out.println("Methods with failed images: " );
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " , ");
        }

        File path = new File(directoryPath);
        File[] allFiles = path.listFiles();
        if (allFiles == null) {
            System.out.println("Nincs fájl a megadott könyvtárban.");
            return;
        }

        List<File> cleanedFilesList = new ArrayList<>();
        for (File file : allFiles) {
            if (file.getName().toLowerCase().endsWith(".png") &&
                    methodNames.contains(file.getName().split("\\.")[0])) {
                cleanedFilesList.add(file);
            }
        }

        File[] cleanedAllFiles = cleanedFilesList.toArray(new File[0]);
        allImages = new BufferedImage[cleanedAllFiles.length];

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10)); // Rácsos elrendezés, függőlegesen

        JLabel[] label = new JLabel[cleanedAllFiles.length];
        for (int i = 0; i < cleanedAllFiles.length; i++) {
            try {
                allImages[i] = ImageIO.read(cleanedAllFiles[i]);
                label[i] = new JLabel();
                ImageIcon icon = new ImageIcon(allImages[i].getScaledInstance(800, -1, Image.SCALE_SMOOTH));
                label[i].setIcon(icon);
                panel.add(label[i]); // Hozzáadjuk a képet a panelhez
            } catch (IOException e) {
                throw new RuntimeException("Nem sikerült beolvasni a fájlt: " + cleanedAllFiles[i].getName(), e);
            }
        }

        panel.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame("Eredeti képek böngészése");
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadImagesFromDirectoryBasedOnMethodName2(String directoryPath, Set<String> methodNames) {

        /*Set<String> addedCountries = this.addedModel;

        IntStream.range(0, addedCountries.size()).forEach(i -> {
            case(addedCountries.)
        });*/
        System.out.println(methodsWithFailedImages);
        Iterator<String> iterator = methodsWithFailedImages.iterator();
        System.out.println("Methods with failed images: " );
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " , ");
        }



        BufferedImage[] allImages;

        File path = new File(directoryPath);
        File[] allFiles = path.listFiles();
        File[] cleanedAllFiles = new File[methodNames.size()];

        if (allFiles != null) {
            int index = 0;
            for (File file : allFiles) {
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    if (file.delete()) {
                        System.out.println("A(z) " + file.getName() + " fájl sikeresen törölve.");
                    } else {
                        System.out.println("Nem sikerült törölni a(z) " + file.getName() + " fájlt.");
                    }
                } else if (methodNames.contains(file.getName().split("\\.")[0])) {
                    System.out.println(file.getName());
                    methodNames.forEach(System.out::println);
                    cleanedAllFiles[index++] = file;
                } else {
                    System.out.println("Else path invoked");
                    System.out.println(file.getName());
                    methodNames.forEach(System.out::println);
                }
            }
        }
        assert allFiles != null;
        Arrays.stream(cleanedAllFiles)
                .filter(file -> file != null)
                .forEach(file -> System.out.println(file.getName()));

        allImages = Arrays.stream(cleanedAllFiles)
                .filter(file -> file != null)
                .map(file -> {
                    try {
                        return ImageIO.read(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(BufferedImage[]::new);
        //allImages = new BufferedImage[cleanedAllFiles.length];

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel[] label = new JLabel[allFiles.length];

        for(int i = 0; i < cleanedAllFiles.length; i++){
            //try{
            //allImages[i] = ImageIO.read(cleanedAllFiles[i]);
            label[i] = new JLabel();
            ImageIcon icon = new ImageIcon(allImages[i].getScaledInstance(-1, -1, Image.SCALE_SMOOTH));
            label[i].setIcon(icon);
            panel.add(label[i]);
            //} catch (IOException e) {
                /*throw new RuntimeException(e);
            }*/
        }

        panel.setBackground(Color.WHITE);
        JFrame frame = new JFrame("Képek böngészése");
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }



    private void loadImagesFromDirectoryBasedOnMethodName2(String directoryPath) {
        BufferedImage[] allImages;

        System.out.println(methodsWithFailedImages);
        Iterator<String> iterator = methodsWithFailedImages.iterator();
        System.out.println("Methods with failed images: " );
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " , ");
        }

        File path = new File(directoryPath);
        File[] allFiles = path.listFiles();
        if (allFiles == null) {
            System.out.println("Nincs fájl a megadott könyvtárban.");
            return;
        }

        List<File> cleanedFilesList = new ArrayList<>();
        for (File file : allFiles) {
            if (file.getName().toLowerCase().endsWith(".png") &&
                    runMethodNames.contains(file.getName().split("\\.")[0])) {
                cleanedFilesList.add(file);
            }
        }

        File[] cleanedAllFiles = cleanedFilesList.toArray(new File[0]);
        allImages = new BufferedImage[cleanedAllFiles.length];

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10)); // Rácsos elrendezés, függőlegesen

        JLabel[] label = new JLabel[cleanedAllFiles.length];
        for (int i = 0; i < cleanedAllFiles.length; i++) {
            try {
                allImages[i] = ImageIO.read(cleanedAllFiles[i]);
                label[i] = new JLabel();
                ImageIcon icon = new ImageIcon(allImages[i].getScaledInstance(800, -1, Image.SCALE_SMOOTH));
                label[i].setIcon(icon);
                panel.add(label[i]); // Hozzáadjuk a képet a panelhez
            } catch (IOException e) {
                throw new RuntimeException("Nem sikerült beolvasni a fájlt: " + cleanedAllFiles[i].getName(), e);
            }
        }

        panel.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame("Eredeti képek böngészése");
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    private static void deleteImagesProperties(String filePath){
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties.clear();

        try (OutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void formatMenus() {

        allMenus.stream().forEach(menu -> {
            //Font font = new Font("IndieFlower-Regular.ttf", Font.BOLD, 14);
            Font font = null;
            try {
                font = UniqueFont.MONOFETT.createUniqueFont(FontType.BOLD,25);
            } catch (IOException | FontFormatException e) {
                throw new RuntimeException(e);
            }
            Color background = Color.ORANGE;
            Color foreground = Color.BLACK;
            Border border = BorderFactory.createLineBorder(Color.BLACK, 6);

            menu.setOpaque(true);
            menu.setFont(font);
            menu.setBackground(background);
            menu.setForeground(foreground);
            menu.setBorder(border);
        });

    }



    /*public void showAlertMessage(Integer failedTests){
        if(failedTests == 0){
            CustomIcon customIcon = new CustomIcon(30,30, Color.GREEN);
            JOptionPane.showMessageDialog(window, "Great Day! All of the test cases have been passed :)", "Succes", JOptionPane.INFORMATION_MESSAGE, customIcon);
        } else {
            CustomIcon customIcon = new CustomIcon(30,30, Color.RED);
            JOptionPane.showMessageDialog(window, STR."There are some test cases, which have been failed: \{failedTests}", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
        }
    }*/

    public int getMenuBarWidth(){
        return menuBar.getWidth();
    }


    public void showAlertMessage(Integer failedTests) {
        String message;
        String title;
        CustomIcon customIcon;
        int messageType;

        if (failedTests == 0) {
            customIcon = new CustomIcon(30, 30, Color.GREEN);
            message = "Great Day! All of the test cases have been passed :)";
            title = "Success";
            messageType = JOptionPane.INFORMATION_MESSAGE;
            Object[] options = new Object[]{"Yippi"};
            JOptionPane.showOptionDialog(
                    this.window,
                    message,
                    title,
                    JOptionPane.DEFAULT_OPTION,
                    messageType,
                    customIcon,
                    options,
                    options[0]
            );
        } else {
            customIcon = new CustomIcon(30, 30, Color.RED);
            message = "There are some test cases which have been failed: " + failedTests;
            title = "Error";
            messageType = JOptionPane.ERROR_MESSAGE;
            Object[] options = new Object[]{"Kutya vigye el"};
            JOptionPane.showOptionDialog(
                    this.window,
                    message,
                    title,
                    JOptionPane.DEFAULT_OPTION,
                    messageType,
                    customIcon,
                    options,
                    options[0]
            );
        }

    }

    public void showInternalMessage(){

    }
}
