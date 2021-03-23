package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import java.io.File;
import javax.swing.border.EmptyBorder;
import static filter.frame.frame_about;
import java.io.InputStream;
import java.io.InputStreamReader;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class about extends JFrame {

    about() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame_about = null;
            }
        });

        initComponents();

    }

    //<editor-fold defaultstate="collapsed" desc="Init Components">
    private void initComponents() {
        int vertical = 8, horizontal = 16;

        nama = new JLabel("Tugas Akhir Pengenalan Pola");
        nama.setHorizontalAlignment(JLabel.CENTER);
        nama.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
        nama.setFont(new Font("Arial", 1, 16));

        desc = new JTextArea();

        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream("Resources/about.txt");
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String s;
            while ((s = read.readLine()) != null) {
                desc.append(s + "\n");
            }
        } catch (Exception e) {
        }

        desc.setSelectionStart(0);
        desc.setSelectionEnd(0);
        desc.setEditable(false);
        desc.setBackground(color.c_white);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));

        JScrollPane scroller = new JScrollPane(desc, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panel_nama = new JPanel();
        panel_nama.add(nama);
        panel_nama.setBackground(color.c_lightest_blue);

        setLayout(new BorderLayout());
        add(panel_nama, BorderLayout.NORTH);
        add(scroller, BorderLayout.CENTER);

        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("Resources/icon/question.jpg")).getImage());
        setAlwaysOnTop(false);
        setMinimumSize(new Dimension(800, 600));
        setTitle("About");
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    //</editor-fold>

    static JLabel nama;
    static JTextArea desc;

}
