package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.frame.image_original;
import static filter.frame.image_filtered;
import static filter.frame.image_temp;
import static filter.frame.img_right;
import static filter.frame.current_width_right;
import static filter.frame.current_height_right;
import static filter.frame.format_time;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static filter.frame.frame_convolution;
import static filter.frame.hist_list;
import static filter.frame.list;
import java.util.Date;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class convolution extends JFrame {

    convolution(int x, int y) {
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_convolution = null;
            }
        });
        
        this.x = x;
        this.y = y;

        initComponents();
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {
        panel_filter = new JPanel(new GridLayout(x, y));
        panel_filterby = new JPanel(new FlowLayout());
        panel_main = new JPanel(new BorderLayout());

        btn_confirm = new JButton("Apply Filter");
        btn_confirm.setPreferredSize(new Dimension(0, 64));

        field = new JTextField[x][y];

        fieldby = new JTextField("1");
        fieldby.setHorizontalAlignment(JTextField.CENTER);
        fieldby.setFont(new Font("Arial", 1, 24));

        panel_filterby.add(new JLabel("1"));
        panel_filterby.add(new JLabel("/"));
        panel_filterby.add(fieldby);
        fieldby.setPreferredSize(new Dimension(16 * x, 16 * y));

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new JTextField("0");
                field[i][j].setHorizontalAlignment(JTextField.CENTER);
                field[i][j].setFont(new Font("Arial", 1, 24));
                panel_filter.add(field[i][j]);
            }
        }

        panel_main.add(panel_filterby, BorderLayout.WEST);
        panel_main.add(panel_filter, BorderLayout.CENTER);

        btnConfirmListener listener_btn = new btnConfirmListener();

        btn_confirm.addActionListener(listener_btn);

        setLayout(new BorderLayout());

        add(panel_main, BorderLayout.CENTER);
        add(btn_confirm, BorderLayout.SOUTH);

        setAlwaysOnTop(false);
        setSize(100 * x, 100 * y);
        setTitle("Custom Filter");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    //</editor-fold>

    class btnConfirmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                list.pushLast((image_temp != null) ? image_temp : image_original);
                double filter[][] = new double[field.length][field[0].length];
                String hist_filt = "<br>1/" + fieldby.getText() + "<br>[";
                for (int i = 0; i < field.length; i++) {
                    if (i != 0) {
                        hist_filt += ",<br>";
                    }
                    hist_filt += "[";
                    for (int j = 0; j < field[i].length; j++) {
                        try {
                            filter[i][j] = (double) (Integer.parseInt(field[i][j].getText())) / (Integer.parseInt(fieldby.getText()));

                            //For History
                            if (j != 0) {
                                hist_filt += ",";
                            }
                            hist_filt += field[i][j].getText();

                        } catch (NumberFormatException e) {
                            filter[i][j] = 0;
                            JOptionPane.showMessageDialog(null, "Non-Numeric Input Detected At [" + i + "," + j + "]\nYour Input : " + field[i][j].getText() + "\nInput Will Be Replaced By 0");
                            field[i][j].setText("0");
                        }
                    }
                    hist_filt += "]";
                }
                hist_filt += "]";
                image_filtered = filt.correlation((image_temp != null) ? image_temp : image_original, filter);
                hist_list.pushFirst(image_filtered, "[" + format_time.format(new Date(System.currentTimeMillis())) + "]<br>Convolution (" + field.length + "x" + field[0].length + ")" + hist_filt);
                image_temp = image_filtered;
                ImageIcon icon_img = filt.resizeImage(new ImageIcon(image_filtered), current_width_right, current_height_right);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(rootPane, "There is no image to convolution");
            }
        }
    }

    static filters filt = new filters();
    static JButton btn_confirm;
    static JTextField fieldby;
    static JTextField field[][];
    static JPanel panel_filter, panel_filterby, panel_main;
    int x, y;

}
