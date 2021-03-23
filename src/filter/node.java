package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class node {

    BufferedImage img;
    node next;
    String activity;

    node(BufferedImage new_img) {
        img = new_img;
    }
    
    node(String name) {
        activity = name;
    }

    node(BufferedImage new_img, String name) {
        img = new_img;
        activity = name;
    }

}
