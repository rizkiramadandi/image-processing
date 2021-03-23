package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.frame.file_name;
import static filter.frame.frame_datahistogram;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class datahistogram extends JFrame {

    datahistogram(BufferedImage img, String of) {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_datahistogram = null;
            }
        });

        this.img = img;
        this.of = of;

        initComponents();

    }

    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {
        int N = img.getHeight() * img.getWidth();

        filt.setZero(hist_red);
        filt.setZero(hist_green);
        filt.setZero(hist_blue);
        filt.setZero(hist_gray);

        data_desc = new JLabel("[" + file_name + "] " + img.getWidth() + " x " + img.getHeight() + " px");
        data_desc.setFont(new Font("Arial", 1, 16));
        data_desc.setHorizontalAlignment(JLabel.CENTER);
        data_desc.setBorder(new EmptyBorder(8, 12, 8, 12));

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j), true);
                int red = rgb.getRed();
                int green = rgb.getGreen();
                int blue = rgb.getBlue();
                int gray = (rgb.getRed() + rgb.getGreen() + rgb.getBlue()) / 3;
                hist_red[red]++;
                hist_green[green]++;
                hist_blue[blue]++;
                hist_gray[gray]++;
            }
        }

        int max_red = filt.getMax(hist_red);
        int max_green = filt.getMax(hist_green);
        int max_blue = filt.getMax(hist_blue);
        int max_gray = filt.getMax(hist_gray);
        int max = Math.max(Math.max(Math.max(max_red, max_green), max_blue), max_gray);

        setLayout(new BorderLayout());

        int max_height = (int) (((double) max / N) * 10000);

        image = new BufferedImage[5];
        Graphics2D g2d[] = new Graphics2D[5];

        for (int i = 0; i < image.length; i++) {
            image[i] = new BufferedImage(512, max_height, BufferedImage.TYPE_INT_ARGB);
            g2d[i] = image[i].createGraphics();
            g2d[i].setColor(new Color(0, 0, 0));
            g2d[i].fillRect(0, 0, image[i].getWidth(), image[i].getHeight());
        }

        range = new BufferedImage(256, 4, BufferedImage.TYPE_INT_ARGB);

        img_histogram = new JLabel();
        img_range = new JLabel();

        Graphics2D g2drange = range.createGraphics();

        for (int i = 0; i < 256; i++) {

            g2drange.setColor(new Color(i, 0, 0, 255));
            g2drange.drawLine(i, 0, i, 0);
            g2drange.setColor(new Color(0, i, 0, 255));
            g2drange.drawLine(i, 1, i, 1);
            g2drange.setColor(new Color(0, 0, i, 255));
            g2drange.drawLine(i, 2, i, 2);
            g2drange.setColor(new Color(i, i, i, 255));
            g2drange.drawLine(i, 3, i, 3);

            hist_red[i] = (int) (((double) hist_red[i] / N) * 10000);
            g2d[1].setColor(new Color(255, 0, 0, 255));
            g2d[1].drawLine(i*2, max_height, i*2, max_height - hist_red[i]);
            g2d[0].setColor(new Color(255, 0, 0, 160));
            g2d[0].drawLine(i*2, max_height, i*2, max_height - hist_red[i]);

            hist_green[i] = (int) (((double) hist_green[i] / N) * 10000);
            g2d[2].setColor(new Color(0, 255, 0, 255));
            g2d[2].drawLine(i*2, max_height, i*2, max_height - hist_green[i]);
            g2d[0].setColor(new Color(0, 255, 0, 160));
            g2d[0].drawLine(i*2, max_height, i*2, max_height - hist_green[i]);

            hist_blue[i] = (int) (((double) hist_blue[i] / N) * 10000);
            g2d[3].setColor(new Color(0, 0, 255, 255));
            g2d[3].drawLine(i*2, max_height, i*2, max_height - hist_blue[i]);
            g2d[0].setColor(new Color(0, 0, 255, 160));
            g2d[0].drawLine(i*2, max_height, i*2, max_height - hist_blue[i]);

            hist_gray[i] = (int) (((double) hist_gray[i] / N) * 10000);
            g2d[4].setColor(new Color(128, 128, 128, 255));
            g2d[4].drawLine(i*2, max_height, i*2, max_height - hist_gray[i]);
            g2d[0].setColor(new Color(128, 128, 128, 160));
            g2d[0].drawLine(i*2, max_height, i*2, max_height - hist_gray[i]);
        }

        img_histogram.setIcon(filt.resizeImage(new ImageIcon(image[0]),1024,350));
        img_histogram.setHorizontalAlignment(JLabel.CENTER);

        img_range.setIcon(filt.resizeImage(new ImageIcon(range),1024,32));
        img_range.setHorizontalAlignment(JLabel.CENTER);

        panel_hist = new JPanel(new BorderLayout(0, 0));
        panel_hist.add(img_histogram, BorderLayout.CENTER);
        panel_hist.add(img_range, BorderLayout.SOUTH);

        panel_xrange = new JPanel(new BorderLayout());
        panel_main = new JPanel(new BorderLayout());
        panel_yrange = new JPanel(new BorderLayout());

        JLabel zero_x = new JLabel("0");
        JLabel zero_y = new JLabel("0");
        JLabel white = new JLabel("255");
        JLabel y = new JLabel(String.valueOf(max));

        Font fnt = new Font("Arial", 1, 14);

        zero_x.setHorizontalAlignment(JLabel.CENTER);
        zero_x.setFont(fnt);
        zero_y.setHorizontalAlignment(JLabel.CENTER);
        zero_y.setFont(fnt);
        white.setHorizontalAlignment(JLabel.CENTER);
        white.setFont(fnt);
        y.setHorizontalAlignment(JLabel.CENTER);
        y.setFont(fnt);

        panel_yrange.add(zero_y, BorderLayout.SOUTH);
        panel_yrange.add(y, BorderLayout.NORTH);
        panel_yrange.setBorder(new EmptyBorder(20, 0, 36, 0));

        panel_xrange.add(zero_x, BorderLayout.WEST);
        panel_xrange.add(white, BorderLayout.EAST);
        panel_xrange.setBorder(new EmptyBorder(0, 56, 0, 24));

        panel_main.add(panel_hist, BorderLayout.CENTER);
        panel_main.add(panel_xrange, BorderLayout.SOUTH);
        panel_main.add(panel_yrange, BorderLayout.WEST);

        panel_main.setBorder(new EmptyBorder(32, 32, 32, 32));

        add(panel_main, BorderLayout.CENTER);

        data = new JLabel[5];

        for (int i = 0; i < data.length; i++) {
            data[i] = new JLabel();
            data[i].setOpaque(true);
            data[i].setHorizontalAlignment(JLabel.CENTER);
            data[i].setBorder(new EmptyBorder(8, 12, 8, 12));
            data[i].setBackground(color.c_gray);
        }

        data[0].setText("All");
        data[0].setName("0");
        data[0].setBackground(color.c_light_blue);
        data[0].setForeground(color.c_white);

        data[1].setText("Red");
        data[1].setName("1");

        data[2].setText("Green");
        data[2].setName("2");

        data[3].setText("Blue");
        data[3].setName("3");

        data[4].setText("Gray");
        data[4].setName("4");

        panel_datargb = new JPanel(new GridLayout(1, 5));
        panel_datargb.setBackground(color.c_gray);
        panel_datargb.add(data[0]);
        panel_datargb.add(data[1]);
        panel_datargb.add(data[2]);
        panel_datargb.add(data[3]);
        panel_datargb.add(data[4]);

        panel_data = new JPanel(new BorderLayout());
        panel_data.setBorder(new EmptyBorder(12, 64, 12, 64));
        panel_data.setBackground(color.c_lightest_blue);

        panel_data.add(data_desc, BorderLayout.CENTER);
        panel_data.add(panel_datargb, BorderLayout.SOUTH);

        add(panel_data, BorderLayout.NORTH);

        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/histogram.jpg")).getImage());
        setAlwaysOnTop(false);
        setTitle("Histogram [" + ((of.equalsIgnoreCase("Original")) ? "Original" : "Filtered") + " Image (Red Green Blue Gray)]");
        setResizable(true);
        setMinimumSize(new Dimension(1200, 600));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mouseListener mouse_listener = new mouseListener();

        for (int i = 0; i < data.length; i++) {
            data[i].addMouseListener(mouse_listener);
        }
        
    }
    //</editor-fold>

    class mouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            int temp = Integer.parseInt(me.getComponent().getName());
            for (int i = 0; i < data.length; i++) {
                data[i].setBackground(color.c_gray);
                data[i].setForeground(color.c_black);
            }
            current = temp;
            data[current].setBackground(color.c_blue);
            data[current].setForeground(color.c_white);
            img_histogram.setIcon(filt.resizeImage(new ImageIcon(image[current]),1024,350));
        }

        @Override
        public void mousePressed(MouseEvent me) {

        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {
            JLabel temp = (JLabel) me.getSource();
            if (temp != data[current]) {
                temp.setCursor(cursor.cursor_hand);
                temp.setBackground(color.c_blue);
                temp.setForeground(color.c_white);
            }
        }

        @Override
        public void mouseExited(MouseEvent me) {
            JLabel temp = (JLabel) me.getSource();
            if (temp != data[current]) {
                temp.setCursor(cursor.cursor_default);
                temp.setBackground(color.c_gray);
                temp.setForeground(color.c_black);
            }
        }
    }

    static filters filt = new filters();
    static JLabel data_title, data_desc, data[];
    static JPanel panel_data, panel_xrange, panel_yrange, panel_main, panel_hist, panel_datargb;
    static BufferedImage range, image[];
    static int hist_red[] = new int[256];
    static int hist_green[] = new int[256];
    static int hist_blue[] = new int[256];
    static int hist_gray[] = new int[256];
    static JLabel img_histogram, img_range;
    static int current = 0;
    BufferedImage img;
    String of;

}
