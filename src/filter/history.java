package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.frame.image_filtered;
import static filter.frame.image_original;
import static filter.frame.image_temp;
import static filter.frame.img_right;
import static filter.frame.hist_list;
import static filter.frame.format_time;
import static filter.frame.default_width;
import static filter.frame.default_height;
import static filter.frame.file_name;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static filter.frame.frame_history;
import static filter.frame.img_detail;
import static filter.frame.list;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class history extends JFrame {

    history() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_history = null;
            }
        });

        initComponents();

    }

    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {

        int vertical = 8, horizontal = 16;

        nama = new JLabel("History");
        nama.setHorizontalAlignment(JLabel.CENTER);
        nama.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        nama.setFont(new Font("Arial", 1, 16));

        panel_image = new JPanel();
        panel_image.setLayout(new BoxLayout(panel_image, BoxLayout.Y_AXIS));
        panel_image.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        image_list = new ArrayList();

        int count = 0;
        node cur = hist_list.head;
        if (cur == null) {
            img = new JLabel();
            img.setText("<html><div style='text-align:center;width:100%;padding:4px 8px;'>There is no history yet</div></html>");
            img.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
            img.setFont(new Font("Arial", 0, 24));
            img.setHorizontalAlignment(JLabel.CENTER);
            img.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel_image.add(img);
        } else if (cur.next == null) {
            JPanel panel = new JPanel(new BorderLayout());
            img = (cur.img != null) ? new JLabel(filt.rescaleImage(new ImageIcon(cur.img), 512, 256)) : new JLabel();
            panel.add(img, BorderLayout.CENTER);
            if (cur.img != null) {
                image_list.add(cur.img);
            } else {
                image_list.add(null);
            }
            count++;
            img.setText("<html><div style='text-align:center;width:100%;border:solid 1px black;padding:4px 8px;'>" + cur.activity + "</div></html>");
            img.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
            img.setHorizontalAlignment(JLabel.CENTER);
            img.setAlignmentX(Component.CENTER_ALIGNMENT);
            img.setVerticalTextPosition(JLabel.TOP);
            img.setHorizontalTextPosition(JLabel.CENTER);
            panel_image.add(panel);
        } else {
            while (cur != null) {
                JPanel panel = new JPanel(new BorderLayout());
                img = (cur.img != null) ? new JLabel(filt.rescaleImage(new ImageIcon(cur.img), 512, 256)) : new JLabel();
                panel.add(img, BorderLayout.CENTER);
                if (cur.img != null) {
                    image_list.add(cur.img);
                } else {
                    image_list.add(null);
                }
                panel_image.add(panel);
                count++;
                img.setText("<html><div style='text-align:center;width:100%;border:solid 1px black;padding:4px 8px;'>" + cur.activity + "</div></html>");
                img.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
                img.setHorizontalAlignment(JLabel.CENTER);
                img.setAlignmentX(Component.CENTER_ALIGNMENT);
                img.setVerticalTextPosition(JLabel.TOP);
                img.setHorizontalTextPosition(JLabel.CENTER);
                panel_image.getComponent(0);
                cur = cur.next;
            }
        }

        JScrollPane scroller = new JScrollPane(panel_image, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar bar = scroller.getVerticalScrollBar();
        bar.setAutoscrolls(true);
        bar.setUnitIncrement(24);

        JPanel panel_nama = new JPanel();
        panel_nama.add(nama);
        panel_nama.setBackground(color.c_lightest_blue);

        setLayout(new BorderLayout());
        add(panel_nama, BorderLayout.NORTH);
        add(scroller, BorderLayout.CENTER);

        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/book.png")).getImage());
        setAlwaysOnTop(false);
        setMinimumSize(new Dimension(600, 600));
        setTitle("History");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

    }
    //</editor-fold>

    static filters filt = new filters();
    static JLabel nama;
    static JPanel panel_image;
    static JLabel img;
    static ArrayList<BufferedImage> image_list;

}
