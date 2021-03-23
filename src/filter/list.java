package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import static filter.frame.icon_undo;
import static filter.frame.item_edit_undo;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class list {
    node head;
    
    list() {
        head = null;
    }
    
    public boolean isEmpty() {
        return (head==null);
    }
    
    public void pushLast(String name) {
        icon_undo.setEnabled(true);
        item_edit_undo.setEnabled(true);
        node data = new node(name);
        data.next = null;
        if(head == null) {
            head = data;
            head.next = null;
        }
        else {
            node temp = head;
            while(temp.next != null) {
                temp = temp.next;
            }
            temp.next = data;
        }
    }
    
    public void pushLast(BufferedImage new_img) {
        icon_undo.setEnabled(true);
        item_edit_undo.setEnabled(true);
        node data = new node(new_img);
        data.next = null;
        if(head == null) {
            head = data;
            head.next = null;
        }
        else {
            node temp = head;
            while(temp.next != null) {
                temp = temp.next;
            }
            temp.next = data;
        }
    }
    
    public void pushLast(BufferedImage new_img, String name) {
        icon_undo.setEnabled(true);
        item_edit_undo.setEnabled(true);
        node data = new node(new_img, name);
        data.next = null;
        if(head == null) {
            head = data;
            head.next = null;
        }
        else {
            node temp = head;
            while(temp.next != null) {
                temp = temp.next;
            }
            temp.next = data;
        }
    }
    
    public void pushFirst(BufferedImage new_img, String name) {
        node data = new node(new_img, name);
        if(head == null) {
            head = data;
            head.next = null;
        }
        else {
            data.next = head;
            head = data;
        }
    }
    
    public void pushFirst(String name) {
        node data = new node(name);
        if(head == null) {
            head = data;
            head.next = null;
        }
        else {
            data.next = head;
            head = data;
        }
    }
    
    public void pushFirst(BufferedImage new_img) {
        node data = new node(new_img);
        if(head == null) {
            head = data;
            head.next = null;
        }
        else {
            data.next = head;
            head = data;
        }
    }
 
    public BufferedImage pop() {
        if(head == null) {
            return null;
        }
        else if(head.next == null) {
            icon_undo.setEnabled(false);
            item_edit_undo.setEnabled(false);
            BufferedImage temp = head.img;
            head = null;
            return temp;
        }
        else {
            node cur = head;
            node prev = null;
            while(cur.next != null) {
                prev = cur;
                cur = cur.next;
            }
            BufferedImage temp = cur.img;
            prev.next = null;
            return temp;
        }
    }
    
}
