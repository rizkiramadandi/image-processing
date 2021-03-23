package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.frame.file_name;
import static filter.frame.frame_datapixels;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class datapixels extends JFrame {

    datapixels(BufferedImage img, String of) {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_datapixels = null;
            }
        });
        
        this.img = img;
        this.of = of;

        initComponents();

    }
   
    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {
        
        total_pixel = img.getWidth() * img.getHeight();

        current_page = 1;

        temp_img = img;

        data_desc = new JLabel("[" + file_name + "] " + img.getWidth() + " x " + img.getHeight() + " px");
        data_desc.setFont(new Font("Arial", 1, 16));
        data_desc.setHorizontalAlignment(JLabel.CENTER);
        data_desc.setBorder(new EmptyBorder(4, 8, 4, 8));

        total_page = (int) ((img.getWidth() * img.getHeight()) / max_row);
        total_page = (((double) (img.getWidth() * img.getHeight()) / max_row) > total_page) ? total_page + 1 : total_page;

        panel_page = new JPanel(new FlowLayout());
        panel_page.setBackground(color.c_lightest_blue);
        panel_page.setBorder(new EmptyBorder(0, 0, 16, 0));

        label_page = new JLabel[total_page];

        mouseListener mouse_listener = new mouseListener();

        Font fnt = new Font("Arial", 0, 12);

        for (int i = 0; i < total_page; i++) {
            label_page[i] = new JLabel("" + (i + 1));
            label_page[i].setOpaque(true);
            label_page[i].setToolTipText("Page " + (i + 1));
            label_page[i].setName("" + (i + 1));
            label_page[i].setHorizontalAlignment(JLabel.CENTER);
            label_page[i].setBackground(color.c_gray);
            label_page[i].setBorder(new EmptyBorder(4, 8, 4, 8));
            label_page[i].addMouseListener(mouse_listener);
            label_page[i].setFont(fnt);
            panel_page.add(label_page[i]);
        }

        label_page[0].setBackground(color.c_blue);
        label_page[0].setForeground(color.c_white);

        scroll_horizontal = new JScrollPane(panel_page, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll_horizontal.getHorizontalScrollBar().setUnitIncrement(24);
        scroll_horizontal.setBorder(null);

        insert(img);

        data_list.setModel(model);
        data_list.getColumnModel().getColumn(2).setCellRenderer(data_list.getDefaultRenderer(ImageIcon.class));
        data_list.setEnabled(false);
        data_list.setRowHeight(34);
        data_list.setGridColor(new Color(240, 240, 240));

        scroll_vertical = new JScrollPane(data_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_vertical.getVerticalScrollBar().setUnitIncrement(24);

        panel_data = new JPanel(new BorderLayout());
        panel_data.setBorder(new EmptyBorder(12, 12, 12, 12));

        panel_scroll = new JPanel(new BorderLayout());
        panel_scroll.add(scroll_horizontal, BorderLayout.CENTER);
        label_pagename = new JLabel("Page : 1 of " + total_page + " [Total Row : " + total_pixel + "]");
        label_pagename.setHorizontalAlignment(JLabel.CENTER);
        label_pagename.setOpaque(true);
        label_pagename.setBackground(color.c_lightest_blue);
        label_pagename.setForeground(color.c_black);
        panel_scroll.add(label_pagename, BorderLayout.NORTH);

        panel_data.add(data_desc, BorderLayout.CENTER);
        if (total_page > 0) {
            panel_data.add(panel_scroll, BorderLayout.SOUTH);
        }
        panel_data.setBackground(color.c_lightest_blue);

        setLayout(new BorderLayout());

        add(panel_data, BorderLayout.NORTH);
        add(scroll_vertical, BorderLayout.CENTER);

        setAlwaysOnTop(false);
        setTitle("Image Pixels RGBA [" + ((of.equalsIgnoreCase("Original")) ? "Original" : "Filtered") + " Image" + "]");
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/color.jpg")).getImage());
        
    }
    //</editor-fold>

    void setModel() {
        data_list.setModel(model);
        data_list.getColumnModel().getColumn(2).setCellRenderer(data_list.getDefaultRenderer(ImageIcon.class));
        data_list.setEnabled(false);
        data_list.setRowHeight(34);
        data_list.setGridColor(new Color(240, 240, 240));
    }

    void inisialize() {
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("Row Number");
        model.addColumn("Pixel Position [x,y]");
        model.addColumn("Color Preview");
        model.addColumn("Red");
        model.addColumn("Green");
        model.addColumn("Blue");
        model.addColumn("Alpha");
    }

    void insert(BufferedImage img) {
        inisialize();
        int count = 1;
        int start_count = (current_page - 1) * max_row;
        int max_count = max_row;
        Graphics2D g2d;
        BufferedImage image;
        Color rgb;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if (start_count <= 0) {
                    image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
                    rgb = new Color(img.getRGB(i, j), true);
                    g2d = image.createGraphics();
                    g2d.setColor(new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), rgb.getAlpha()));
                    g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
                    model.addRow(new Object[]{count, "(" + i + "," + j + ")", new ImageIcon(image), rgb.getRed(), rgb.getGreen(), rgb.getBlue(), rgb.getAlpha()});
                    max_count--;
                } else {
                    start_count--;
                }
                count++;
                if (max_count <= 0) {
                    break;
                }
            }
            if (max_count <= 0) {
                break;
            }
        }
        setModel();
    }

    class mouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            int page = Integer.parseInt(me.getComponent().getName());
            label_page[current_page - 1].setBackground(color.c_gray);
            label_page[current_page - 1].setForeground(color.c_black);
            current_page = page;
            label_page[current_page - 1].setBackground(color.c_blue);
            label_page[current_page - 1].setForeground(color.c_white);
            label_pagename.setText("Page : " + current_page + " of " + total_page + " [Total Row : " + total_pixel + "]");
            insert(temp_img);
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
            if (temp != label_page[current_page - 1]) {
                temp.setCursor(cursor.cursor_hand);
                temp.setBackground(color.c_blue);
                temp.setForeground(color.c_white);
            }
        }

        @Override
        public void mouseExited(MouseEvent me) {
            JLabel temp = (JLabel) me.getSource();
            if (temp != label_page[current_page - 1]) {
                temp.setCursor(cursor.cursor_hand);
                temp.setBackground(color.c_gray);
                temp.setForeground(color.c_black);
            }
        }
    }
    
    static JLabel data_title, data_desc, label_page[], label_pagename;
    static JTable data_list = new JTable();
    static JPanel panel_data, panel_page, panel_scroll;
    static JScrollPane scroll_vertical, scroll_horizontal;
    static DefaultTableModel model = new DefaultTableModel();
    static int total_page, current_page, max_row = 10000, total_pixel;
    static BufferedImage temp_img;
    BufferedImage img;
    String of;
    
}
