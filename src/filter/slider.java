package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.frame.frame_slider;
import static filter.frame.image_filtered;
import static filter.frame.image_original;
import static filter.frame.image_temp;
import static filter.frame.img_right;
import static filter.frame.current_width_right;
import static filter.frame.current_height_right;
import static filter.frame.format_time;
import static filter.frame.hist_list;
import static filter.frame.list;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class slider extends JFrame {

    slider() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_slider = null;
                image_filtered = image_temps;
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            }
        });

        initComponents();
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {
        
        image_temps = ((image_temp != null) ? image_temp : image_original);
        image_tempz = image_filtered;

        int min = -255, max = 255;

        int vertical = 4, horizontal = 12;

        panel_brightness = new JPanel(new GridLayout(2, 1));
        panel_contrast = new JPanel(new GridLayout(2, 1));
        panel_top = new JPanel(new GridLayout(1, 2));
        panel_red = new JPanel(new GridLayout(2, 1));
        panel_green = new JPanel(new GridLayout(2, 1));
        panel_blue = new JPanel(new GridLayout(2, 1));
        panel_bottom = new JPanel(new GridLayout(5, 1));
        setLayout(new GridLayout(2, 1));

        btn_apply = new JButton("Apply");
        btn_reset = new JButton("Reset Slider");

        slider_brightness = new JSlider(JSlider.HORIZONTAL, min, max, 0);
        slider_contrast = new JSlider(JSlider.HORIZONTAL, -127, 127, 0);

        label_brightness = new JLabel("Brightness : " + String.valueOf(slider_brightness.getValue()));
        label_contrast = new JLabel("Contrast : " + String.valueOf(slider_contrast.getValue()));

        panel_brightness.add(label_brightness);
        panel_brightness.add(slider_brightness);
        panel_brightness.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        panel_contrast.add(label_contrast);
        panel_contrast.add(slider_contrast);
        panel_contrast.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        slider_red = new JSlider(JSlider.HORIZONTAL, min, max, 0);
        slider_green = new JSlider(JSlider.HORIZONTAL, min, max, 0);
        slider_blue = new JSlider(JSlider.HORIZONTAL, min, max, 0);

        label_red = new JLabel("Red : " + String.valueOf(slider_red.getValue()));
        label_green = new JLabel("Green : " + String.valueOf(slider_green.getValue()));
        label_blue = new JLabel("Blue : " + String.valueOf(slider_blue.getValue()));

        panel_red.add(label_red);
        panel_red.add(slider_red);
        panel_red.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        panel_green.add(label_green);
        panel_green.add(slider_green);
        panel_green.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        panel_blue.add(label_blue);
        panel_blue.add(slider_blue);
        panel_blue.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        
        panel_top.add(panel_brightness);
        panel_top.add(panel_contrast);

        panel_bottom.add(panel_red);
        panel_bottom.add(panel_green);
        panel_bottom.add(panel_blue);
        panel_bottom.add(btn_apply);
        panel_bottom.add(btn_reset);

        add(panel_top);
        add(panel_bottom);

        setAlwaysOnTop(false);
        setSize(400, 400);
        setTitle("Brightness and Contrast");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnApplyListener listener_apply = new btnApplyListener();
        btnResetListener listener_reset = new btnResetListener();

        sliderListener change_slider = new sliderListener();

        btn_apply.addActionListener(listener_apply);
        btn_reset.addActionListener(listener_reset);

        slider_brightness.addChangeListener(change_slider);
        slider_contrast.addChangeListener(change_slider);
        slider_red.addChangeListener(change_slider);
        slider_green.addChangeListener(change_slider);
        slider_blue.addChangeListener(change_slider);

    }
    //</editor-fold>

    class sliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent ce) {
            if (image_original != null) {
                if (slider_red.getValueIsAdjusting() || slider_green.getValueIsAdjusting() || slider_blue.getValueIsAdjusting() || slider_brightness.getValueIsAdjusting() || slider_contrast.getValueIsAdjusting()) {
                    return;
                }
                int val_r = slider_red.getValue();
                int val_g = slider_green.getValue();
                int val_b = slider_blue.getValue();
                int val_br = slider_brightness.getValue();
                int val_co = slider_contrast.getValue();
                label_red.setText("Red : " + String.valueOf(val_r));
                label_green.setText("Green : " + String.valueOf(val_g));
                label_blue.setText("Blue : " + String.valueOf(val_b));
                label_brightness.setText("Brightness : " + String.valueOf(slider_brightness.getValue()));
                label_contrast.setText("Contrast : " + String.valueOf(slider_contrast.getValue()));
                image_tempz = image_temps;
                image_filtered = filt.rgb(filt.contrast(filt.brightness((image_temp != null) ? image_temp : image_original, val_br), val_co), val_r, val_g, val_b);
                image_tempz = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(rootPane, "There is no image to change");
            }
        }

    }

    class btnApplyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                list.pushLast((image_temp != null) ? image_temp : image_original);
                image_filtered = image_tempz;
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Intensity Slider<br>Brightness : +(" + slider_brightness.getValue() + ")<br>Contrast : +(" + slider_contrast.getValue() + ")<br>R : +(" + slider_red.getValue() + ")<br>G : +(" + slider_green.getValue() + ")<br>B : +(" + slider_blue.getValue() + ")");
                image_temp = image_filtered;
                image_temps = image_temp;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(rootPane, "There is no image to change");
            }
        }

    }

    class btnResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                slider_red.setValue(0);
                slider_green.setValue(0);
                slider_blue.setValue(0);
                slider_brightness.setValue(0);
                slider_contrast.setValue(0);
                image_filtered = image_temps;
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(rootPane, "There is no image to reset");
            }
        }
    }

    static filters filt = new filters();
    static JSlider slider_brightness, slider_contrast, slider_red, slider_green, slider_blue;
    static JPanel panel_top, panel_bottom, panel_brightness, panel_contrast, panel_red, panel_green, panel_blue;
    static JButton btn_apply, btn_reset;
    static BufferedImage image_temps, image_tempz;
    static JLabel label_brightness, label_contrast, label_red, label_green, label_blue;

}
