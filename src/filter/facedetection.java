package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.filters.success;
import static filter.filters.fail;
import static filter.frame.format_time;
import static filter.frame.frame_facedetection;
import static filter.frame.hist_list;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class facedetection extends JFrame {

    facedetection(BufferedImage image) {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_facedetection = null;
            }
        });

        this.image = image;

        initComponents();

    }

    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {

        panel_faces = new JPanel();
        JScrollPane scroll_faces = new JScrollPane(panel_faces, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll_faces.getHorizontalScrollBar().setUnitIncrement(24);

        slider_val = 0;

        bool_check = new boolean[2];
        bool_check[0] = true;

        JPanel panel_grid = new JPanel(new GridLayout(3, 1));
        panel_grid.setBorder(new EmptyBorder(4, 32, 16, 32));
        JPanel panel_radio = new JPanel(new GridLayout(1, 3));
        JPanel panel_checkbox = new JPanel(new GridLayout(1, 2));

        item_radio_normalized = new JRadioButton("Color Map Skin Filter Normalized");
        item_radio_normalized.setName("norm");
        item_radio_normalized.setHorizontalAlignment(JRadioButton.CENTER);
        item_radio_rgb = new JRadioButton("Color Map Skin Filter RGB");
        item_radio_rgb.setName("rgb");
        item_radio_rgb.setHorizontalAlignment(JRadioButton.CENTER);
        item_radio_rgb.setSelected(true);
        item_radio_ycbcr = new JRadioButton("Color Map Skin Filter YCbCr");
        item_radio_ycbcr.setName("ycbcr");
        item_radio_ycbcr.setHorizontalAlignment(JRadioButton.CENTER);

        group_radio = new ButtonGroup();

        group_radio.add(item_radio_normalized);
        group_radio.add(item_radio_rgb);
        group_radio.add(item_radio_ycbcr);

        panel_radio.add(item_radio_normalized);
        panel_radio.add(item_radio_rgb);
        panel_radio.add(item_radio_ycbcr);

        box_face = new JCheckBox("Face Filter");
        box_face.setName("face");
        box_face.setHorizontalAlignment(JCheckBox.CENTER);
        box_face.setSelected(true);
        box_other = new JCheckBox("Other Filter");
        box_other.setName("other");
        box_other.setHorizontalAlignment(JCheckBox.CENTER);
        box_other.setSelected(false);

        panel_checkbox.add(box_face);
        panel_checkbox.add(box_other);

        slider_threshold = new JSlider(0, image.getWidth(), 0);
        slider_threshold.setToolTipText(slider_threshold.getValue() + "/" + slider_threshold.getMaximum());

        panel_grid.add(panel_checkbox);
        panel_grid.add(panel_radio);
        panel_grid.add(slider_threshold);

        img = new JLabel();
        img.setHorizontalTextPosition(JLabel.CENTER);
        img.setVerticalTextPosition(JLabel.BOTTOM);
        img.setHorizontalAlignment(JLabel.CENTER);

        setLayout(new BorderLayout());
        add(scroll_faces, BorderLayout.NORTH);
        add(img, BorderLayout.CENTER);
        add(panel_grid, BorderLayout.SOUTH);

        image_original = image;
        double filter[][] = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        image_filtered = filt.correlation(image_original, filter);
        slider_threshold.setMaximum(image_original.getWidth());
        slider_threshold.setValue(0);
        setImage();

        setTitle("Face Detection");
        setVisible(true);
        setMinimumSize(new Dimension(1200, 700));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        sliderThresholdListener listener_slider = new sliderThresholdListener();

        radioFilterListener listener_radio = new radioFilterListener();

        checkboxCheckListener listener_checkbox = new checkboxCheckListener();

        item_radio_normalized.addActionListener(listener_radio);
        item_radio_rgb.addActionListener(listener_radio);
        item_radio_ycbcr.addActionListener(listener_radio);

        box_face.addActionListener(listener_checkbox);
        box_other.addActionListener(listener_checkbox);

        slider_threshold.addChangeListener(listener_slider);

    }
    //</editor-fold>

    public void setPanel() {
        panel_faces.removeAll();
        panel_faces.revalidate();
        panel_faces.repaint();
        detect.forEach(temp -> {
            if (filt.checkFace(temp, slider_val)) {
                int min_x = temp.get(0).x;
                int min_y = temp.get(0).y;
                int max_x = temp.get(1).x;
                int max_y = temp.get(1).y;
                int w = max_x - min_x + 1;
                int h = max_y - min_y + 1;
                BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                int x = 0;
                int y = 0;
                for (int i = min_x; i <= max_x; i++) {
                    y = 0;
                    for (int j = min_y; j <= max_y; j++) {
                        img.setRGB(x, y, new Color(image_original.getRGB(i, j)).getRGB());
                        y++;
                    }
                    x++;
                }
                panel_faces.add(new JLabel(filt.rescaleImage(new ImageIcon(img), 64, 64)));
            }
        });
    }

    public void setImage() {
        String str_hist = "Color Map Skin Filter RGB";
        switch (str_radio) {
            case "norm":
                str_hist = "Color Map Skin Filter Normalized";
                image_modeled = filt.colorMapNormalized(image_filtered);
                break;
            case "rgb":
                image_modeled = filt.colorMapSkinRGB(image_filtered);
                break;
            case "ycbcr":
                str_hist = "Color Map Skin Filter YCbCr";
                image_modeled = filt.colorMapSkinYCbCr(image_filtered);
                break;
            default:
                image_modeled = filt.colorMapSkinRGB(image_filtered);
                break;
        }
        detect = filt.res(image_modeled);
        setPanel();
        image_res = filt.draw(detect, image_original, bool_check, slider_val);
        hist_list.pushFirst(image_res, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Face Detection<br>" + str_hist + " [Threshold : " + slider_val + "]<br>Total object(s) detected : " + (success + fail) + "<br>" + success + " face(s)<br>" + fail + " other object(s)<br>Detection Success Count (DSC) = " + (double) (success * 100) / detect.size() + "%<br>" + "False Detection Count (FDC) = " + (double) (fail * 100) / detect.size() + "%");
        img.setText("<html><div style='text-align:center'>Total object(s) detected : " + (success + fail) + "<br>" + success + " face(s)<br>" + fail + " other object(s)<br>Detection Success Count (DSC) = " + (double) (success * 100) / detect.size() + "%<br>" + "False Detection Count (FDC) = " + (double) (fail * 100) / detect.size() + "%</div></html>");
        img.setIcon(filt.rescaleImage(new ImageIcon(image_res), 1000, 400));
    }

    class sliderThresholdListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent ce) {
            slider_threshold.setToolTipText(slider_threshold.getValue() + "/" + slider_threshold.getMaximum());
            if (image_original != null) {
                if (slider_threshold.getValueIsAdjusting()) {
                    return;
                }
                slider_val = slider_threshold.getValue();
                setImage();
            }
        }
    }

    class radioFilterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                JRadioButton temp = (JRadioButton) ae.getSource();
                str_radio = temp.getName();
                setImage();
            }
        }

    }

    class checkboxCheckListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                JCheckBox temp = (JCheckBox) ae.getSource();
                switch (temp.getName()) {
                    case "face":
                        bool_check[0] = (temp.isSelected() ? true : false);
                        break;
                    case "other":
                        bool_check[1] = (temp.isSelected() ? true : false);
                        break;
                    default:
                        break;
                }
            }
            setImage();
        }
    }

    ArrayList<ArrayList<Point>> detect;
    filters filt = new filters();
    JSlider slider_threshold;
    JLabel img;
    BufferedImage image_original, image_filtered, image_modeled, image_res;
    JRadioButton item_radio_normalized, item_radio_rgb, item_radio_ycbcr;
    JCheckBox box_face, box_other;
    ButtonGroup group_radio;
    String str_radio = "rgb";
    static boolean bool_check[];
    static int slider_val;
    JPanel panel_faces;
    BufferedImage image;

}
