package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.frame.frame_blend;
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
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class blend extends JFrame {

    blend() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_blend = null;
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

        setLayout(new BorderLayout());

        image_temps = ((image_temp != null) ? image_temp : image_original);
        image_tempz = image_filtered;

        int vertical = 12, horizontal = 24;

        int count = 20;
        String name[] = {"Gradient #1", "Gradient #2", "Gradient #3", "Gradient #4", "Gradient #5", "Gradient #6", "Gradient #7", "Gradient #8", "Sky #1", "Sky #2", "Sky #3", "Sky #4", "Nature #1", "Nature #2", "Nature #3", "Nature #4", "Other #1", "Other #2", "Other #3", "Other #4"};

        panel_button = new JPanel(new GridLayout(1, 2));
        panel_button.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        btn_apply = new JButton("Apply");
        btn_reset = new JButton("Reset");

        panel_button.add(btn_apply);
        panel_button.add(btn_reset);

        panel_combox = new JPanel(new BorderLayout());
        panel_combox.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        combox_type = new JComboBox();
        combox_type.setModel(new DefaultComboBoxModel(new String[]{"Linear Dodge", "Color Dodge", "Screen", "Linear Burn", "Color Burn", "Multiply", "Darken", "Lighten", "Overlay", "Difference", "Exclusion", "Substract", "Divide", "Soft Light"}));

        panel_combox.add(combox_type, BorderLayout.CENTER);

        panel_blend = new JPanel(new GridLayout(count / 4, 4));

        JScrollPane scroll_blend = new JScrollPane(panel_blend, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_blend.getVerticalScrollBar().setUnitIncrement(24);
        scroll_blend.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        radio_blend = new JRadioButton[count];
        group_blend = new ButtonGroup();

        for (int i = 0; i < count; i++) {
            try {
                radio_blend[i] = new JRadioButton();
                radio_blend[i].setText(name[i]);
                radio_blend[i].setName(String.valueOf(i));
                radio_blend[i].setVerticalTextPosition(JRadioButton.BOTTOM);
                radio_blend[i].setHorizontalTextPosition(JRadioButton.CENTER);
                radio_blend[i].setHorizontalAlignment(JRadioButton.CENTER);
                radio_blend[i].setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/blend/" + i + ".jpg")), 100, 100));
                group_blend.add(radio_blend[i]);
                panel_blend.add(radio_blend[i]);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        add(panel_combox, BorderLayout.NORTH);
        add(scroll_blend, BorderLayout.CENTER);
        add(panel_button, BorderLayout.SOUTH);

        setAlwaysOnTop(false);
        setSize(600, 600);
        setTitle("Blend Modes");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        groupBlendListener listener_group = new groupBlendListener();

        comboxListener listener_combox = new comboxListener();

        btnApplyListener listener_apply = new btnApplyListener();
        btnResetListener listener_reset = new btnResetListener();

        btn_apply.addActionListener(listener_apply);
        btn_reset.addActionListener(listener_reset);

        combox_type.addActionListener(listener_combox);

        for (int i = 0; i < count; i++) {
            radio_blend[i].addActionListener(listener_group);
        }

    }
    //</editor-fold>

    class groupBlendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String type = String.valueOf(combox_type.getSelectedItem());
            JComponent btn = (JComponent) ae.getSource();
            image_tempz = image_temps;
            image_filtered = filt.blendModes((image_temp != null) ? image_temp : image_original, type, Integer.parseInt(btn.getName()));
            image_tempz = image_filtered;
            ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
            img_right.setIcon(icon_img);
        }

    }

    class comboxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String type = String.valueOf(combox_type.getSelectedItem());
            JComponent btn = null;
            for (int i = 0; i < radio_blend.length; i++) {
                if (radio_blend[i].isSelected()) {
                    btn = (JComponent) radio_blend[i];
                }
            }
            if (btn != null) {
                image_tempz = image_temps;
                image_filtered = filt.blendModes((image_temp != null) ? image_temp : image_original, type, Integer.parseInt(btn.getName()));
                image_tempz = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            }
        }
    }

    class btnApplyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                list.pushLast((image_temp != null) ? image_temp : image_original);
                image_filtered = image_tempz;
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Blend Modes<br>" + String.valueOf(combox_type.getSelectedItem()));
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
                group_blend.clearSelection();
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
    static JPanel panel_blend, panel_combox, panel_button;
    static JButton btn_apply, btn_reset;
    static JComboBox combox_type;
    static BufferedImage image_temps, image_tempz;
    static JRadioButton radio_blend[];
    static ButtonGroup group_blend;

}
