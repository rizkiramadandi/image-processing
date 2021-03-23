package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class frame extends JFrame {

    frame() {
        initComponents();
    }

    public static void main(String[] args) {
        new frame();
        //<editor-fold defaultstate="collapsed" desc="Set Look And Feel">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
    }

    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {
        int vertical = 8, horizontal = 16;

        menubar = new JMenuBar();
        menubar.setBackground(color.c_white);

        menu_file = new JMenu("File");
        menu_file.setForeground(color.c_black);

        menu_file_saveas = new JMenu("Save As");
        menu_file_saveas.setEnabled(false);
        menu_file_saveas.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        menu_file_saveas.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/save.png")), 18, 18));

        item_file_open = new JMenuItem("Open Image");
        item_file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        item_file_open.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_file_open.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/picture.png")), 18, 18));
        item_file_close = new JMenuItem("Close");
        item_file_close.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/exit.jpg")), 18, 18));
        item_file_close.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        item_file_saveas_jpg = new JMenuItem("JPG");
        item_file_saveas_jpg.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_file_saveas_jpeg = new JMenuItem("JPEG");
        item_file_saveas_jpeg.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        menu_file_saveas.add(item_file_saveas_jpg);
        menu_file_saveas.add(item_file_saveas_jpeg);

        menu_file.add(item_file_open);
        menu_file.add(menu_file_saveas);
        menu_file.add(item_file_close);

        menu_filter = new JMenu("Filter");
        menu_filter.setEnabled(false);
        menu_filter.setForeground(color.c_black);

        menu_filter_grayscale = new JMenu("Grayscale");
        menu_filter_grayscale.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        menu_filter_grayscale.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/grayscale.jpg")), 18, 18));

        item_filter_grayscale_average = new JMenuItem("Average");
        item_filter_grayscale_average.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_grayscale_luminosity = new JMenuItem("Luminosity");
        item_filter_grayscale_luminosity.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_grayscale_desaturation = new JMenuItem("Desaturation");
        item_filter_grayscale_desaturation.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_grayscale_maxdecomposition = new JMenuItem("Max Decomposition");
        item_filter_grayscale_maxdecomposition.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_grayscale_mindecomposition = new JMenuItem("Min Decomposition");
        item_filter_grayscale_mindecomposition.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_grayscale_singlered = new JMenuItem("Single Color Channel (Red)");
        item_filter_grayscale_singlered.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_grayscale_singlegreen = new JMenuItem("Single Color Channel (Green)");
        item_filter_grayscale_singlegreen.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_grayscale_singleblue = new JMenuItem("Single Color Channel (Blue)");
        item_filter_grayscale_singleblue.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        menu_edit = new JMenu("Edit");
        menu_edit.setEnabled(false);
        menu_edit.setForeground(color.c_black);

        item_edit_undo = new JMenuItem("Undo");
        item_edit_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        item_edit_undo.setEnabled(false);
        item_edit_undo.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_edit_undo.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/undo.png")), 18, 18));

        item_edit_mirrorx = new JMenuItem("Mirror X");
        item_edit_mirrorx.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_edit_mirrorx.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/mirrorx.png")), 18, 18));
        item_edit_mirrory = new JMenuItem("Mirror Y");
        item_edit_mirrory.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_edit_mirrory.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/mirrory.png")), 18, 18));
        item_edit_rotatecw = new JMenuItem("Rotate Clock Wise");
        item_edit_rotatecw.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_edit_rotatecw.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/rotatecw.png")), 18, 18));
        item_edit_rotateccw = new JMenuItem("Rotate Counter Clock Wise");
        item_edit_rotateccw.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_edit_rotateccw.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/rotateccw.png")), 18, 18));

        menu_edit_scale = new JMenu("Scale");
        menu_edit_scale.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        menu_edit_scale.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/scale.jpg")), 18, 18));

        item_edit_scale_05x = new JMenuItem("0.5X");
        item_edit_scale_05x.setName("0.5X");
        item_edit_scale_05x.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        item_edit_scale_2x = new JMenuItem("2X");
        item_edit_scale_2x.setName("2X");
        item_edit_scale_2x.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        item_edit_clear = new JMenuItem("Clean All Change");
        item_edit_clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        item_edit_clear.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_edit_clear.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/eraser.png")), 18, 18));

        menu_edit_scale.add(item_edit_scale_05x);
        menu_edit_scale.add(item_edit_scale_2x);

        menu_edit.add(item_edit_undo);
        menu_edit.add(item_edit_mirrorx);
        menu_edit.add(item_edit_mirrory);
        menu_edit.add(item_edit_rotatecw);
        menu_edit.add(item_edit_rotateccw);
        menu_edit.add(menu_edit_scale);
        menu_edit.add(item_edit_clear);

        item_filter_sharpening = new JMenuItem("Sharpening");
        item_filter_sharpening.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_sharpening.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/sharpening.png")), 18, 18));
        item_filter_edge = new JMenuItem("Edge Detection");
        item_filter_edge.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_edge.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/border.jpg")), 18, 18));
        item_filter_smoothing = new JMenuItem("Smoothing");
        item_filter_smoothing.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_smoothing.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/smoothing.jpg")), 18, 18));
        item_filter_binarization = new JMenuItem("Binarization");
        item_filter_binarization.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_binarization.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/binary.jpg")), 18, 18));

        menu_filter_custom = new JMenu("Custom Convolution Filter");
        menu_filter_custom.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        menu_filter_custom.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/edit.jpg")), 18, 18));

        item_filter_custom3 = new JMenuItem("3x3");
        item_filter_custom3.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_custom3.setName("convolution 3");
        item_filter_custom5 = new JMenuItem("5x5");
        item_filter_custom5.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_custom5.setName("convolution 5");

        item_filter_brightnesscontrast = new JMenuItem("Brightness and Contrast");
        item_filter_brightnesscontrast.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_brightnesscontrast.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/sun.jpg")), 18, 18));

        item_filter_blendmodes = new JMenuItem("Blend Modes");
        item_filter_blendmodes.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_blendmodes.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/gallery.png")), 18, 18));

        menu_filter_custom.add(item_filter_custom3);
        menu_filter_custom.add(item_filter_custom5);

        item_filter_negative = new JMenuItem("Negative");
        item_filter_negative.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_negative.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/negative.jpg")), 18, 18));

        menu_filter_grayscale.add(item_filter_grayscale_average);
        menu_filter_grayscale.add(item_filter_grayscale_luminosity);
        menu_filter_grayscale.add(item_filter_grayscale_desaturation);
        menu_filter_grayscale.add(item_filter_grayscale_maxdecomposition);
        menu_filter_grayscale.add(item_filter_grayscale_mindecomposition);
        menu_filter_grayscale.add(item_filter_grayscale_singlered);
        menu_filter_grayscale.add(item_filter_grayscale_singlegreen);
        menu_filter_grayscale.add(item_filter_grayscale_singleblue);

        item_filter_histogramequal = new JMenuItem("Histogram Equalization");
        item_filter_histogramequal.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_filter_histogramequal.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/histogram_equal.jpg")), 18, 18));

        menu_filter.add(menu_filter_grayscale);
        menu_filter.add(menu_edit);
        menu_filter.add(item_filter_brightnesscontrast);
        menu_filter.add(item_filter_blendmodes);
        menu_filter.add(item_filter_binarization);
        menu_filter.add(item_filter_negative);
        menu_filter.add(item_filter_sharpening);
        menu_filter.add(item_filter_edge);
        menu_filter.add(item_filter_smoothing);
        menu_filter.add(item_filter_histogramequal);
        menu_filter.add(menu_filter_custom);

        menu_help = new JMenu("Help");
        menu_help.setForeground(color.c_black);

        item_help_about = new JMenuItem("About");
        item_help_about.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_help_about.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/question.jpg")), 18, 18));

        item_help_history = new JMenuItem("History");
        item_help_history.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_help_history.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/book.png")), 18, 18));

        menu_help_pixel = new JMenu("Pixel Value");
        menu_help_pixel.setEnabled(false);
        menu_help_pixel.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        menu_help_pixel.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/color.png")), 18, 18));

        menu_help_histogram = new JMenu("Histogram");
        menu_help_histogram.setEnabled(false);
        menu_help_histogram.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        menu_help_histogram.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/histogram.jpg")), 18, 18));

        item_help_pixel_original = new JMenuItem("Original Image");
        item_help_pixel_original.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_help_pixel_filtered = new JMenuItem("Filtered Image");
        item_help_pixel_filtered.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        item_help_histogram_original = new JMenuItem("Original Image");
        item_help_histogram_original.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_help_histogram_filtered = new JMenuItem("Filtered Image");
        item_help_histogram_filtered.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        menu_help_pixel.add(item_help_pixel_original);
        menu_help_pixel.add(item_help_pixel_filtered);

        menu_help_histogram.add(item_help_histogram_original);
        menu_help_histogram.add(item_help_histogram_filtered);

        menu_help.add(menu_help_pixel);
        menu_help.add(menu_help_histogram);
        menu_help.add(item_help_history);
        menu_help.add(item_help_about);

        menu_feature = new JMenu("Feature");
        menu_feature.setForeground(color.c_black);
        menu_feature.setEnabled(false);

        item_feature_facedetection = new JMenuItem("Face Detection (Skin Based)");
        item_feature_facedetection.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        item_feature_facedetection.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/face.png")), 18, 18));

        menu_feature.add(item_feature_facedetection);

        menubar.add(menu_file);
        menubar.add(menu_edit);
        menubar.add(menu_filter);
        menubar.add(menu_feature);
        menubar.add(menu_help);

        panel_img = new JPanel(new GridLayout(1, 2));
        panel_main = new JPanel(new BorderLayout());

        panel_tool_left = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel_tool_right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panel_left = new JPanel(new BorderLayout());
        panel_right = new JPanel(new BorderLayout());

        panel_detail = new JPanel(new BorderLayout());

        img_detail = new JLabel("No Image Selected");
        img_detail.setOpaque(true);
        img_detail.setBackground(color.c_lightest_blue);
        img_detail.setHorizontalAlignment(JLabel.CENTER);
        img_detail.setFont(new Font("Arial", 1, 16));
        img_detail.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        btn_toggle = new JButton("Toggle Original Image");
        btn_toggle.setBorderPainted(false);
        btn_toggle.setBackground(color.c_gray);
        btn_toggle.setToolTipText("Show or Hide Original Image");
        btn_toggle.setName("toggle");
        btn_toggle.setMnemonic(KeyEvent.VK_T);

        panel_detail.add(img_detail, BorderLayout.CENTER);
        panel_detail.add(btn_toggle, BorderLayout.SOUTH);

        ImageIcon def = filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/no_img.jpg")), 256, 256);
        img_left = new JLabel();
        img_left.setIcon(def);
        img_left.setHorizontalAlignment(JLabel.CENTER);
        img_left.setName("image left");
        img_right = new JLabel();
        img_right.setIcon(def);
        img_right.setHorizontalAlignment(JLabel.CENTER);
        img_right.setName("image right");

        scroll_left = new JScrollPane(img_left, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll_left.getVerticalScrollBar().setUnitIncrement(24);
        scroll_left.getHorizontalScrollBar().setUnitIncrement(24);
        scroll_right = new JScrollPane(img_right, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll_right.getVerticalScrollBar().setUnitIncrement(24);
        scroll_right.getHorizontalScrollBar().setUnitIncrement(24);

        icon_zoomin_left = new JButton();
        icon_zoomin_left.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/zoomin.png")), 18, 18));
        icon_zoomin_left.setBorderPainted(false);
        icon_zoomin_left.setBackground(color.c_gray);
        icon_zoomin_left.setName("zoom in left");
        icon_zoomin_left.setToolTipText("Zoom in (Left Image)");

        icon_zoomout_left = new JButton();
        icon_zoomout_left.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/zoomout.png")), 18, 18));
        icon_zoomout_left.setBorderPainted(false);
        icon_zoomout_left.setBackground(color.c_gray);
        icon_zoomout_left.setName("zoom out left");
        icon_zoomout_left.setToolTipText("Zoom out (Left Image)");

        icon_zoomin_right = new JButton();
        icon_zoomin_right.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/zoomin.png")), 18, 18));
        icon_zoomin_right.setBorderPainted(false);
        icon_zoomin_right.setBackground(color.c_gray);
        icon_zoomin_right.setName("zoom in right");
        icon_zoomin_right.setToolTipText("Zoom in (Right Image)");

        icon_zoomout_right = new JButton();
        icon_zoomout_right.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/zoomout.png")), 18, 18));
        icon_zoomout_right.setBorderPainted(false);
        icon_zoomout_right.setBackground(color.c_gray);
        icon_zoomout_right.setName("zoom out right");
        icon_zoomout_right.setToolTipText("Zoom out (Right Image)");

        icon_histogram_original = new JButton();
        icon_histogram_original.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/histogram.jpg")), 18, 18));
        icon_histogram_original.setBorderPainted(false);
        icon_histogram_original.setBackground(color.c_gray);
        icon_histogram_original.setName("histogram original");
        icon_histogram_original.setToolTipText("Histogram (Original Image)");

        icon_histogram_filtered = new JButton();
        icon_histogram_filtered.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/histogram.jpg")), 18, 18));
        icon_histogram_filtered.setBorderPainted(false);
        icon_histogram_filtered.setBackground(color.c_gray);
        icon_histogram_filtered.setName("histogram filtered");
        icon_histogram_filtered.setToolTipText("Histogram (Filtered Image)");

        icon_pixels_original = new JButton();
        icon_pixels_original.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/color.png")), 18, 18));
        icon_pixels_original.setBorderPainted(false);
        icon_pixels_original.setBackground(color.c_gray);
        icon_pixels_original.setName("pixel original");
        icon_pixels_original.setToolTipText("Pixel (Original Image)");

        icon_pixels_filtered = new JButton();
        icon_pixels_filtered.setIcon(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/color.png")), 18, 18));
        icon_pixels_filtered.setBorderPainted(false);
        icon_pixels_filtered.setBackground(color.c_gray);
        icon_pixels_filtered.setName("pixel filtered");
        icon_pixels_filtered.setToolTipText("Pixel (Filtered Image)");

        label_zoom_right = new JLabel();

        panel_tool_right.add(icon_pixels_filtered);
        panel_tool_right.add(icon_histogram_filtered);
        panel_tool_right.add(label_zoom_right);
        panel_tool_right.add(icon_zoomin_right);
        panel_tool_right.add(icon_zoomout_right);

        label_zoom_left = new JLabel();

        panel_tool_left.add(icon_pixels_original);
        panel_tool_left.add(icon_histogram_original);
        panel_tool_left.add(label_zoom_left);
        panel_tool_left.add(icon_zoomin_left);
        panel_tool_left.add(icon_zoomout_left);

        panel_left.add(scroll_left, BorderLayout.CENTER);
        panel_left.add(panel_tool_left, BorderLayout.SOUTH);

        panel_right.add(scroll_right, BorderLayout.CENTER);
        panel_right.add(panel_tool_right, BorderLayout.SOUTH);

        panel_img.add(panel_left);
        panel_img.add(panel_right);
        panel_img.setBackground(color.c_white);

        panel_icon = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_icon.setBackground(color.c_gray);
        panel_icon.setBorder(new EmptyBorder(0, 4, 8, 4));

        icon_open = new JButton(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/picture.png")), 24, 24));
        icon_open.setText("Open");
        icon_open.setToolTipText("Select an image from computer");
        icon_open.setVerticalTextPosition(JLabel.BOTTOM);
        icon_open.setHorizontalTextPosition(JLabel.CENTER);
        icon_open.setBorderPainted(false);
        icon_open.setBackground(color.c_gray);
        icon_open.setName("open");
        icon_open.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        icon_undo = new JButton(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/undo.png")), 24, 24));
        icon_undo.setText("Undo");
        icon_undo.setEnabled(false);
        icon_undo.setToolTipText("Undo image change");
        icon_undo.setVerticalTextPosition(JLabel.BOTTOM);
        icon_undo.setHorizontalTextPosition(JLabel.CENTER);
        icon_undo.setBorderPainted(false);
        icon_undo.setBackground(color.c_gray);
        icon_undo.setName("undo");
        icon_undo.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        icon_clear = new JButton(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/eraser.png")), 24, 24));
        icon_clear.setText("Clean");
        icon_clear.setEnabled(false);
        icon_clear.setToolTipText("Clean all change from image");
        icon_clear.setVerticalTextPosition(JLabel.BOTTOM);
        icon_clear.setHorizontalTextPosition(JLabel.CENTER);
        icon_clear.setBorderPainted(false);
        icon_clear.setBackground(color.c_gray);
        icon_clear.setName("clear");
        icon_clear.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        icon_brightnesscontrast = new JButton(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/sun.jpg")), 24, 24));
        icon_brightnesscontrast.setText("Intensity");
        icon_brightnesscontrast.setEnabled(false);
        icon_brightnesscontrast.setToolTipText("Custom Intensity");
        icon_brightnesscontrast.setVerticalTextPosition(JLabel.BOTTOM);
        icon_brightnesscontrast.setHorizontalTextPosition(JLabel.CENTER);
        icon_brightnesscontrast.setBorderPainted(false);
        icon_brightnesscontrast.setBackground(color.c_gray);
        icon_brightnesscontrast.setName("brightness & contrast");
        icon_brightnesscontrast.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        icon_blend = new JButton(filt.resizeImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/gallery.png")), 24, 24));

        icon_blend.setText("Blend");
        icon_blend.setEnabled(false);
        icon_blend.setToolTipText("Blend Modes");
        icon_blend.setVerticalTextPosition(JLabel.BOTTOM);
        icon_blend.setHorizontalTextPosition(JLabel.CENTER);
        icon_blend.setBorderPainted(false);
        icon_blend.setBackground(color.c_gray);
        icon_blend.setName("blend modes");
        icon_blend.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        panel_icon.add(icon_open);
        panel_icon.add(icon_undo);
        panel_icon.add(icon_clear);
        panel_icon.add(icon_brightnesscontrast);
        panel_icon.add(icon_blend);

        JScrollPane scroll_icon = new JScrollPane(panel_icon, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollBar bar_hor = scroll_icon.getHorizontalScrollBar();

        //remove arrow keys from scrollpane
        bar_hor.setUI(
                new BasicScrollBarUI() {

            @Override
            protected JButton createDecreaseButton(int orientation
            ) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation
            ) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }

        });

        bar_hor.setUnitIncrement(24);
        bar_hor.setPreferredSize(new Dimension(0, 8));

        panel_mains = new JPanel(new BorderLayout());

        panel_mains.add(scroll_icon, BorderLayout.NORTH);
        panel_mains.add(panel_main, BorderLayout.CENTER);

        panel_main.add(panel_detail, BorderLayout.NORTH);
        panel_main.add(panel_img, BorderLayout.CENTER);
        panel_main.setBackground(color.c_lightest_blue);

        setLayout(new BorderLayout(0, 0));
        add(menubar, BorderLayout.NORTH);
        add(panel_mains, BorderLayout.CENTER);

        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/logo.png")).getImage());
        setMinimumSize(new Dimension(1200, 600));
        setTitle("Image Processing");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        //Listener Section
        menuOpenListener listener_open = new menuOpenListener();
        menuCloseListener listener_close = new menuCloseListener();
        menuSaveAsListener listener_saveas = new menuSaveAsListener();

        menuGrayscaleListener listener_grayscale = new menuGrayscaleListener();
        menuMirrorXListener listener_mirrorx = new menuMirrorXListener();
        menuMirrorYListener listener_mirrory = new menuMirrorYListener();
        menuRotateCWListener listener_rotatecw = new menuRotateCWListener();
        menuRotateCCWListener listener_rotateccw = new menuRotateCCWListener();
        menuScaleListener listener_scale = new menuScaleListener();
        menuHistogramEqualizationListener listener_histogramequal = new menuHistogramEqualizationListener();
        menuSliderListener listener_slider = new menuSliderListener();
        menuBinarizationListener listener_binarization = new menuBinarizationListener();
        menuNegativeListener listener_negative = new menuNegativeListener();
        menuCorrelationListener listener_correlation = new menuCorrelationListener();
        menuClearListener listener_clear = new menuClearListener();
        menuCustomListener listener_custom = new menuCustomListener();

        menuAboutListener listener_about = new menuAboutListener();
        menuHistoryListener listener_history = new menuHistoryListener();
        menuPixelOriginalListener listener_pixel_original = new menuPixelOriginalListener();
        menuPixelFilteredListener listener_pixel_filtered = new menuPixelFilteredListener();
        menuHistogramOriginalListener listener_histogram_original = new menuHistogramOriginalListener();
        menuHistogramFilteredListener listener_histogram_filtered = new menuHistogramFilteredListener();

        menuMedianListener listener_median = new menuMedianListener();

        menuUndoListener listener_undo = new menuUndoListener();

        menuFaceDetectionListener listener_facedetect = new menuFaceDetectionListener();

        menuBlendModesListener listener_blend = new menuBlendModesListener();

        btnToggleListener listener_toggle = new btnToggleListener();

        item_file_open.addActionListener(listener_open);
        item_file_close.addActionListener(listener_close);
        item_file_saveas_jpg.addActionListener(listener_saveas);
        item_file_saveas_jpeg.addActionListener(listener_saveas);

        item_filter_grayscale_average.addActionListener(listener_grayscale);
        item_filter_grayscale_luminosity.addActionListener(listener_grayscale);
        item_filter_grayscale_desaturation.addActionListener(listener_grayscale);
        item_filter_grayscale_maxdecomposition.addActionListener(listener_grayscale);
        item_filter_grayscale_mindecomposition.addActionListener(listener_grayscale);
        item_filter_grayscale_singlered.addActionListener(listener_grayscale);
        item_filter_grayscale_singlegreen.addActionListener(listener_grayscale);
        item_filter_grayscale_singleblue.addActionListener(listener_grayscale);

        item_edit_undo.addActionListener(listener_undo);
        item_edit_mirrorx.addActionListener(listener_mirrorx);
        item_edit_mirrory.addActionListener(listener_mirrory);
        item_edit_rotatecw.addActionListener(listener_rotatecw);
        item_edit_rotateccw.addActionListener(listener_rotateccw);
        item_edit_scale_05x.addActionListener(listener_scale);
        item_edit_scale_2x.addActionListener(listener_scale);

        item_filter_histogramequal.addActionListener(listener_histogramequal);

        item_filter_brightnesscontrast.addActionListener(listener_slider);

        item_filter_blendmodes.addActionListener(listener_blend);

        item_filter_binarization.addActionListener(listener_binarization);

        item_filter_negative.addActionListener(listener_negative);

        item_filter_sharpening.addActionListener(listener_correlation);
        item_filter_edge.addActionListener(listener_correlation);
        item_filter_smoothing.addActionListener(listener_median);

        item_edit_clear.addActionListener(listener_clear);

        item_filter_custom3.addActionListener(listener_custom);
        item_filter_custom5.addActionListener(listener_custom);

        item_help_about.addActionListener(listener_about);

        item_help_history.addActionListener(listener_history);

        item_help_pixel_original.addActionListener(listener_pixel_original);
        item_help_pixel_filtered.addActionListener(listener_pixel_filtered);

        item_help_histogram_original.addActionListener(listener_histogram_original);
        item_help_histogram_filtered.addActionListener(listener_histogram_filtered);

        item_feature_facedetection.addActionListener(listener_facedetect);

        icon_open.addActionListener(listener_open);
        icon_undo.addActionListener(listener_undo);
        icon_clear.addActionListener(listener_clear);
        icon_histogram_original.addActionListener(listener_histogram_original);
        icon_histogram_filtered.addActionListener(listener_histogram_filtered);
        icon_pixels_original.addActionListener(listener_pixel_original);
        icon_pixels_filtered.addActionListener(listener_pixel_filtered);
        icon_brightnesscontrast.addActionListener(listener_slider);
        icon_blend.addActionListener(listener_blend);

        btn_toggle.addActionListener(listener_toggle);

        btnMouseListener mouse_btn = new btnMouseListener();

        btnZoomInListener listener_zoomin = new btnZoomInListener();
        btnZoomOutListener listener_zoomout = new btnZoomOutListener();

        icon_open.addMouseListener(mouse_btn);
        icon_undo.addMouseListener(mouse_btn);
        icon_clear.addMouseListener(mouse_btn);
        icon_histogram_original.addMouseListener(mouse_btn);
        icon_histogram_filtered.addMouseListener(mouse_btn);
        icon_pixels_original.addMouseListener(mouse_btn);
        icon_pixels_filtered.addMouseListener(mouse_btn);
        icon_brightnesscontrast.addMouseListener(mouse_btn);
        icon_zoomin_left.addMouseListener(mouse_btn);
        icon_zoomin_right.addMouseListener(mouse_btn);
        icon_zoomout_left.addMouseListener(mouse_btn);
        icon_zoomout_right.addMouseListener(mouse_btn);
        icon_blend.addMouseListener(mouse_btn);

        btn_toggle.addMouseListener(mouse_btn);

        icon_zoomin_left.addActionListener(listener_zoomin);
        icon_zoomin_right.addActionListener(listener_zoomin);
        icon_zoomout_left.addActionListener(listener_zoomout);
        icon_zoomout_right.addActionListener(listener_zoomout);

    }
    //</editor-fold>

    class btnToggleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (panel_left.isVisible()) {
//                hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Toggle<br>Hide Original Image");
                panel_left.setVisible(false);
                panel_img.removeAll();
                panel_img.setLayout(new GridLayout(1, 1));
                panel_img.add(panel_right);
                panel_img.setBackground(color.c_white);
            } else {
//                hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Toggle<br>Show Original Image");
                panel_left.setVisible(true);
                panel_img.removeAll();
                panel_img.setLayout(new GridLayout(1, 2));
                panel_img.add(panel_left);
                panel_img.add(panel_right);
                panel_img.setBackground(color.c_white);
            }
            panel_main.revalidate();
            panel_main.repaint();
        }

    }

    class btnMouseListener implements MouseListener {

        @Override
        public void mouseExited(MouseEvent ae) {
            JComponent temp = (JComponent) ae.getSource();
            temp.setCursor(cursor.cursor_default);
            temp.setBackground(color.c_gray);
            temp.setForeground(color.c_black);
        }

        @Override
        public void mouseEntered(MouseEvent ae) {
            JComponent temp = (JComponent) ae.getSource();
            if (temp.getName().equals("toggle") || temp.getName().equals("open") || (!temp.getName().equals("undo") && image_original != null) || (temp.getName().equals("undo") && (!list.isEmpty()))) {
                temp.setCursor(cursor.cursor_hand);
                temp.setBackground(color.c_dark_gray);
                temp.setForeground(color.c_white);
            }
        }

        @Override
        public void mouseReleased(MouseEvent ae) {

        }

        @Override
        public void mousePressed(MouseEvent ae) {

        }

        @Override
        public void mouseClicked(MouseEvent ae) {

        }
    }

    class btnZoomInListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                JComponent temp = (JComponent) ae.getSource();
                switch (temp.getName()) {
                    case "zoom in left":
                        if (current_width_left < 5000) {
                            current_width_left *= 1.10;
                            current_height_left *= 1.10;
//                            hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Zoom in<br>Filtered Image<br>" + current_width_left + "x" + current_height_left + "px");
                            ImageIcon image_left = filt.resizeImage(new ImageIcon(image_original), current_width_left, current_height_left);
                            img_left.setIcon(image_left);
                            label_zoom_left.setText(current_width_left + "x" + current_height_left + "px");
                        }
                        break;
                    case "zoom in right":
                        if (current_width_right < 5000) {
                            current_width_right *= 1.10;
                            current_height_right *= 1.10;
//                            hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Zoom in<br>Filtered Image<br>" + current_width_right + "x" + current_height_right + "px");
                            ImageIcon image_right = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                            img_right.setIcon(image_right);
                            label_zoom_right.setText(current_width_right + "x" + current_height_right + "px");
                        }
                        break;

                    default:
                        break;
                }
            }
        }
    }

    class btnZoomOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                JComponent temp = (JComponent) ae.getSource();
                switch (temp.getName()) {
                    case "zoom out left":
                        if (current_width_left > 512) {
                            current_width_left *= 0.90;
                            current_height_left *= 0.90;
//                            hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Zoom out<br>Filtered Image<br>" + current_width_left + "x" + current_height_left + "px");
                            ImageIcon image_left = filt.resizeImage(new ImageIcon(image_original), current_width_left, current_height_left);
                            img_left.setIcon(image_left);
                            label_zoom_left.setText(current_width_left + "x" + current_height_left + "px");
                        }
                        break;
                    case "zoom out right":
                        if (current_width_right > 512) {
                            current_width_right *= 0.90;
                            current_height_right *= 0.90;
//                            hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Zoom out<br>Filtered Image<br>" + current_width_right + "x" + current_height_right + "px");
                            ImageIcon image_right = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                            img_right.setIcon(image_right);
                            label_zoom_right.setText(current_width_right + "x" + current_height_right + "px");
                        }
                        break;
                    default:
                        break;
                }
            }
        }

    }

    class menuFaceDetectionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                if (frame_facedetection == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Feature [Face Detection]");
                    frame_facedetection = new facedetection(image_filtered);
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuSliderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                if (frame_slider == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Intensity Slider");
                    frame_slider = new slider();
                }
            }
        }
    }

    class menuBlendModesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                if (frame_blend == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Blend Modes");
                    frame_blend = new blend();
                }
            }
        }
    }

    class menuPixelOriginalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null && image_filtered != null) {
                String type = ae.getActionCommand();
                if (frame_datapixels == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Pixel Data [Original Image]");
                    frame_datapixels = new datapixels((type.equalsIgnoreCase("Original Image") ? image_original : image_filtered), "Original");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuPixelFilteredListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null && image_filtered != null) {
                String type = ae.getActionCommand();
                if (frame_datapixels == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Pixel Data [Filtered Image]");
                    frame_datapixels = new datapixels((type.equalsIgnoreCase("Original Image") ? image_original : image_filtered), "Filtered");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuHistogramOriginalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null && image_filtered != null) {
                String type = ae.getActionCommand();
                if (frame_datahistogram == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Histogram [Original Image]");
                    frame_datahistogram = new datahistogram(image_original, "Original");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuHistogramFilteredListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null && image_filtered != null) {
                String type = ae.getActionCommand();
                if (frame_datahistogram == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Histogram [Filtered Image]");
                    frame_datahistogram = new datahistogram(image_filtered, "Filtered");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuCustomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                String type = ((JComponent) ae.getSource()).getName();
                if (frame_convolution == null) {
                    hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open Convolution [" + ((type.equalsIgnoreCase("convolution 5")) ? "5x5" : "3x3") + "]");
                    frame_convolution = new convolution((type.equalsIgnoreCase("convolution 5")) ? 5 : 3, (type.equalsIgnoreCase("convolution 5")) ? 5 : 3);
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuAboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (frame_about == null) {
                hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open About");
                frame_about = new about();
            }
        }
    }

    class menuHistoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (frame_history == null) {
                frame_history = new history();
                hist_list.pushFirst("[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open History");
            }
        }
    }

    class menuUndoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                BufferedImage temp = list.pop();
                if (temp != null) {
                    hist_list.pushFirst(temp, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Undo");
                    image_filtered = temp;
                    image_temp = image_filtered;
                    if (image_filtered.getWidth() > image_filtered.getHeight()) {
                        if (current_width_right < current_height_right) {
                            int temps = current_width_right;
                            current_width_right = current_height_right;
                            current_height_right = temps;
                        }
                    } else {
                        if (current_width_right > current_height_right) {
                            int temps = current_width_right;
                            current_width_right = current_height_right;
                            current_height_right = temps;
                        }
                    }
                    ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                    img_right.setIcon(icon_img);
                    img_detail.setText("[" + file_name + "] Image Dimension : " + image_original.getWidth() + " x " + image_original.getHeight() + " px > " + image_filtered.getWidth() + " x " + image_filtered.getHeight() + " px");
                } else {
//                    JOptionPane.showMessageDialog(null, "Cannot undo any further");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuMedianListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                list.pushLast((image_temp != null) ? image_temp : image_original);
                image_filtered = filt.median((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Smoothing (Median Filter)");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }

    }

    class menuClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                if (image_original != image_filtered) {
                    list.pushLast((image_temp != null) ? image_temp : image_original);
                    image_filtered = image_original;
                    image_temp = null;
                    hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Clean All Change");
                    if (image_filtered.getWidth() > image_filtered.getHeight()) {
                        if (current_width_right < current_height_right) {
                            int temps = current_width_right;
                            current_width_right = current_height_right;
                            current_height_right = temps;
                        }
                    } else {
                        if (current_width_right > current_height_right) {
                            int temps = current_width_right;
                            current_width_right = current_height_right;
                            current_height_right = temps;
                        }
                    }
                    ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_original), current_width_right, current_height_right);
                    img_right.setIcon(icon_img);
                    img_detail.setText("[" + file_name + "] Image Dimension : " + image_original.getWidth() + " x " + image_original.getHeight() + " px > " + image_filtered.getWidth() + " x " + image_filtered.getHeight() + " px");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuCorrelationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                String type = ae.getActionCommand();
                double filter[][] = {{}};
                switch (type) {
                    case "Sharpening":
                        double filter_sharpening[][] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
                        filter = filter_sharpening;
                        break;
                    case "Edge Detection":
                        double filter_edge[][] = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
                        filter = filter_edge;
                        break;
                    default:
                        break;
                }
                image_filtered = filt.correlation((image_temp != null) ? image_temp : image_original, filter);
                if (type.equals("Edge Detection")) {
                    image_filtered = filt.binarization(image_filtered);
                }
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>" + type + " (Kernel Filter)");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuGrayscaleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                String type = ae.getActionCommand();
                image_filtered = filt.grayscale((image_temp != null) ? image_temp : image_original, type);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Grayscale [" + type + "]");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuMirrorXListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                String type = ae.getActionCommand();
                image_filtered = filt.mirrorX((image_temp != null) ? image_temp : image_original);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Mirror X");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuMirrorYListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                String type = ae.getActionCommand();
                image_filtered = filt.mirrorY((image_temp != null) ? image_temp : image_original);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Mirror Y");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuRotateCWListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                image_filtered = filt.rotateCW((image_temp != null) ? image_temp : image_original);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Rotate Clock Wise");
                image_temp = image_filtered;
                int temp = current_width_right;
                current_width_right = current_height_right;
                current_height_right = temp;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
                img_detail.setText("[" + file_name + "] Image Dimension : " + image_original.getWidth() + " x " + image_original.getHeight() + " px > " + image_filtered.getWidth() + " x " + image_filtered.getHeight() + " px");
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuRotateCCWListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                image_filtered = filt.rotateCCW((image_temp != null) ? image_temp : image_original);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Rotate Counter Clock Wise");
                image_temp = image_filtered;
                int temp = current_width_right;
                current_width_right = current_height_right;
                current_height_right = temp;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
                img_detail.setText("[" + file_name + "] Image Dimension : " + image_original.getWidth() + " x " + image_original.getHeight() + " px > " + image_filtered.getWidth() + " x " + image_filtered.getHeight() + " px");
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuScaleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                JComponent component = (JComponent) ae.getSource();
                String type = component.getName();
                if ((((image_temp != null) ? image_temp.getWidth() : image_original.getWidth()) * 0.5 < 32 || ((image_temp != null) ? image_temp.getHeight() : image_original.getHeight()) * 0.5 < 32) && type.equalsIgnoreCase("0.5X")) {
                    JOptionPane.showMessageDialog(null, err_toosmall);
                } else if ((((image_temp != null) ? image_temp.getWidth() : image_original.getWidth()) * 2 > 2500 || ((image_temp != null) ? image_temp.getHeight() : image_original.getHeight()) * 2 > 2500) && type.equalsIgnoreCase("2X")) {
                    JOptionPane.showMessageDialog(null, err_toobig);
                } else {
                    image_filtered = filt.scale((image_temp != null) ? image_temp : image_original, (type.equalsIgnoreCase("2X") ? 2 : 0.5));
                    list.pushLast((image_temp != null) ? image_temp : image_original);
                    hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Scale " + type + "<br>width : " + image_filtered.getWidth() + "px, height : " + image_filtered.getHeight() + "px");
                    image_temp = image_filtered;
                    ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                    img_right.setIcon(icon_img);
                    img_detail.setText("[" + file_name + "] Image Dimension : " + image_original.getWidth() + " x " + image_original.getHeight() + " px > " + image_filtered.getWidth() + " x " + image_filtered.getHeight() + " px");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuBinarizationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                image_filtered = filt.binarization((image_temp != null) ? image_temp : image_original);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Binarization");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuNegativeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                image_filtered = filt.negative((image_temp != null) ? image_temp : image_original);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Negative");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuHistogramEqualizationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                image_filtered = filt.histogramEqualization((image_temp != null) ? image_temp : image_original);
                list.pushLast((image_temp != null) ? image_temp : image_original);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Histogram Equalization");
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuCloseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

    class menuSaveAsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_filtered != null) {
                String format = ae.getActionCommand();
                chooser = new JFileChooser();
                chooser.setPreferredSize(new Dimension(1000, 600));
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.setCurrentDirectory(new File("./Resources"));
                chooser.setApproveButtonText("Save");
                chooser.setDialogTitle("Save");
                int res = chooser.showOpenDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    String path = String.valueOf(chooser.getCurrentDirectory());
                    File file = new File(path);
                    String temp_name = chooser.getSelectedFile().getName();
                    String nama_file =  ((temp_name.equals("")||temp_name==null) ? "image":temp_name) + "." + format;
                    System.out.println(nama_file);
                    try {
                        ImageIO.write(image_filtered, format, new File(path + "\\" + nama_file));
                        hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "Image Saved<br>Filename : " + nama_file + "<br>Path : " + path + "");
                        System.out.println("Save Success [" + path + "\\" + nama_file + "]");
                        JOptionPane.showMessageDialog(null, err_savesuccess + " \nFilename : [" + nama_file + "]\nFile Path : [" + path + "\\" + nama_file + "]");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, err_savefailed);
                        System.out.println("Save failed");
                    }
                } else {
//                    System.out.println("Direktori tidak dipilih");
                }
            } else {
                JOptionPane.showMessageDialog(null, err_noimage);
            }
        }
    }

    class menuOpenListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (frame_datahistogram != null) {
                frame_datahistogram.dispose();
            }
            if (frame_datapixels != null) {
                frame_datapixels.dispose();
            }
            if (frame_convolution != null) {
                frame_convolution.dispose();
            }
            if (frame_slider != null) {
                frame_slider.dispose();
            }
            if (frame_blend != null) {
                frame_blend.dispose();
            }
            if (frame_facedetection != null) {
                frame_facedetection.dispose();
            }
            if (frame_about != null) {
                frame_about.dispose();
            }
            if (frame_history != null) {
                frame_history.dispose();
            }
            chooser = new JFileChooser();
            propertyChangeListener property_change = new propertyChangeListener();
            chooser.addPropertyChangeListener(property_change);
            chooser.setApproveButtonText("Select");
            chooser.setPreferredSize(new Dimension(1000, 600));
            chooser.setDialogTitle("Open");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Images (.JPG,.JPEG)", "jpg", "jpeg"));
            chooser.setCurrentDirectory((current_dir == null) ? new File(".") : current_dir);
            chooser.setAcceptAllFileFilterUsed(false);
            int res = chooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = new File(String.valueOf(chooser.getSelectedFile()));
                String path = String.valueOf(chooser.getCurrentDirectory());
                current_dir = file;
                try {
                    BufferedImage temp = ImageIO.read(file);
                    image_original = null;
                    image_filtered = null;
                    image_temp = null;
                    if (temp.getWidth() > 2500 || temp.getHeight() > 2500) {
                        JOptionPane.showMessageDialog(null, err_toobig);
                    } else if (temp.getWidth() < 32 || temp.getHeight() < 32) {
                        JOptionPane.showMessageDialog(null, err_toosmall);
                    } else {
                        list = new list();
                        hist_list = new list();
                        icon_clear.setEnabled(true);
                        icon_brightnesscontrast.setEnabled(true);
                        icon_blend.setEnabled(true);
                        icon_histogram_filtered.setEnabled(true);
                        icon_histogram_original.setEnabled(true);
                        icon_pixels_filtered.setEnabled(true);
                        icon_pixels_original.setEnabled(true);
                        menu_file_saveas.setEnabled(true);
                        menu_edit.setEnabled(true);
                        menu_filter.setEnabled(true);
                        menu_help_pixel.setEnabled(true);
                        menu_help_histogram.setEnabled(true);
                        menu_feature.setEnabled(true);
                        if (list.isEmpty()) {
                            icon_undo.setEnabled(false);
                            item_edit_undo.setEnabled(false);
                        }
                        file_name = file.getName();
                        hist_list.pushFirst(temp, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Open New Image<br>Filename : " + file_name + "<br>Path : " + path + "");
                        image_original = temp;
                        image_filtered = image_original;
                        image_temp = null;
                        ImageIcon icon_img = filt.rescaleImage(new ImageIcon(image_original), 500, 500);
                        current_width_left = icon_img.getIconWidth();
                        current_width_right = icon_img.getIconWidth();
                        current_height_left = icon_img.getIconHeight();
                        current_height_right = icon_img.getIconHeight();
                        default_width = current_width_left;
                        default_height = current_height_left;
                        label_zoom_left.setText(current_width_left + "x" + current_height_left + "px");
                        label_zoom_right.setText(current_width_right + "x" + current_height_right + "px");
                        img_left.setIcon(icon_img);
                        img_right.setIcon(icon_img);
                        img_detail.setText("[" + file_name + "] Image Dimension : " + image_original.getWidth() + " x " + image_original.getHeight() + " px > " + image_filtered.getWidth() + " x " + image_filtered.getHeight() + " px");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, err_noimage);
                }
            } else {
//                System.out.println("Image tidak dipilih");
            }
        }
    }

    class propertyChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            if (pce.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                File file = new File(String.valueOf(chooser.getSelectedFile()));
                try {
                    BufferedImage temp = ImageIO.read(file);
                    ImageIcon icon_preview = filt.rescaleImage(new ImageIcon(temp), 256, 256);
                    JLabel label_preview = new JLabel(icon_preview);
                    chooser.setAccessory(label_preview);
                    label_preview.setIcon(icon_preview);
                    label_preview.setText("Image Preview (" + temp.getWidth() + "x" + temp.getHeight() + "px)");
                    label_preview.setHorizontalTextPosition(JLabel.CENTER);
                    label_preview.setVerticalTextPosition(JLabel.TOP);
                    label_preview.setBorder(new EmptyBorder(8, 8, 8, 8));
                } catch (IOException e) {

                }
            }
        }

    }

    String err_noimage = "No Image Found";
    String err_toobig = "Image Dimension is Too Big [ Max Width : 2500px & Max Height : 2500px ]";
    String err_toosmall = "Image Dimension is Too Small";
    String err_savesuccess = "Save Success";
    String err_savefailed = "Save Failed";
    filters filt = new filters();
    static String file_name = "";
    static BufferedImage image_original, image_filtered, image_temp;
    static about frame_about;
    static history frame_history;
    static convolution frame_convolution;
    static slider frame_slider;
    static datapixels frame_datapixels;
    static datahistogram frame_datahistogram;
    static facedetection frame_facedetection;
    static blend frame_blend;
    File current_dir = null;
    JMenuBar menubar;
    JMenu menu_file, menu_file_saveas, menu_edit, menu_filter, menu_filter_grayscale, menu_edit_scale, menu_filter_custom, menu_help, menu_help_pixel, menu_help_histogram, menu_feature;
    JMenuItem item_file_open, item_file_close, item_file_saveas_jpg, item_file_saveas_jpeg;
    JMenuItem item_filter_grayscale, item_filter_grayscale_average, item_filter_grayscale_luminosity, item_filter_grayscale_desaturation, item_filter_grayscale_maxdecomposition, item_filter_grayscale_mindecomposition, item_filter_grayscale_singlered, item_filter_grayscale_singlegreen, item_filter_grayscale_singleblue;
    JMenuItem item_filter_binarization;
    JMenuItem item_filter_sharpening, item_filter_edge, item_filter_smoothing;
    JMenuItem item_filter_blendmodes;
    JMenuItem item_edit_clear;
    JMenuItem item_filter_custom3, item_filter_custom5;
    JMenuItem item_filter_brightnesscontrast;
    static JMenuItem item_edit_undo, item_edit_mirrorx, item_edit_mirrory, item_edit_rotatecw, item_edit_rotateccw, item_edit_scale_05x, item_edit_scale_2x;
    JMenuItem item_filter_histogramequal;
    JMenuItem item_filter_negative;
    JMenuItem item_help_about, item_help_history;
    JMenuItem item_help_pixel_original, item_help_pixel_filtered;
    JMenuItem item_help_histogram_original, item_help_histogram_filtered;
    JMenuItem item_feature_facedetection;
    static JButton icon_zoomin_left, icon_zoomout_left, icon_zoomin_right, icon_zoomout_right, icon_open, icon_undo, icon_clear, icon_brightnesscontrast, icon_histogram_original, icon_histogram_filtered, icon_pixels_original, icon_pixels_filtered, icon_blend;
    JButton btn_toggle;
    JPanel panel_img, panel_main, panel_icon, panel_mains, panel_tool_left, panel_tool_right, panel_left, panel_right, panel_detail;
    JLabel label_zoom_left, label_zoom_right;
    static JLabel img_left, img_right, img_detail;
    static int current_width_left, current_height_left, current_width_right, current_height_right, default_width, default_height;
    static list list = new list();
    static list hist_list = new list();
    static SimpleDateFormat format_time = new SimpleDateFormat("dd-mm-yyyy 'at' HH:mm:ss z");
    JFileChooser chooser;
    JScrollPane scroll_left, scroll_right;

}
