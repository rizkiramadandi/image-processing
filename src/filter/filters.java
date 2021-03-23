package filter;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//</editor-fold>

/*@author Rizki Ramadandi*/
public class filters {

    //<editor-fold defaultstate="collapsed" desc="Face Detection Section">
    static int success, fail;

    BufferedImage colorMapNormalized(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                double new_red = (double) rgb.getRed() / (rgb.getRed() + rgb.getGreen() + rgb.getBlue());
                double new_green = (double) rgb.getGreen() / (rgb.getRed() + rgb.getGreen() + rgb.getBlue());
                int col = 255;
                if (new_red >= 0.36 && new_red <= 0.465) {
                    if (new_green >= 0.28 && new_green <= 0.363) {
                        col = 0;
                    }
                }
                new_img.setRGB(i, j, new Color(col, col, col, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    BufferedImage colorMapSkinRGB(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int col = 255;
                if (rgb.getRed() > 95 && rgb.getGreen() > 40 && rgb.getBlue() > 20) {
                    if (Math.abs((Math.max(Math.max(rgb.getRed(), rgb.getGreen()), rgb.getBlue())) - (Math.min(Math.min(rgb.getRed(), rgb.getGreen()), rgb.getBlue()))) > 15) {
                        if (Math.abs(rgb.getRed() - rgb.getGreen()) > 15) {
                            if (rgb.getRed() > rgb.getGreen() && rgb.getRed() > rgb.getBlue()) {
                                col = 0;
                            }
                        }
                    }
                }
                new_img.setRGB(i, j, new Color(col, col, col, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    BufferedImage colorMapSkinYCbCr(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int col = 255;
                int Y = (int) (16 + ((double) 65.738 * rgb.getRed() / 256) + ((double) 129.057 * rgb.getGreen() / 256) + (double) 25.064 * rgb.getGreen() / 256);
                int Cb = (int) (128 - ((double) 37.945 * rgb.getRed() / 256) - ((double) 74.494 * rgb.getGreen() / 256) + (double) 112.439 * rgb.getGreen() / 256);
                int Cr = (int) (128 + ((double) 112.439 * rgb.getRed() / 256) - ((double) 94.154 * rgb.getGreen() / 256) - (double) 18.285 * rgb.getGreen() / 256);
                if (Y > 60 && Y < 255) {
                    if (Cb > 100 && Cb < 125) {
                        if (Cr > 135 && Cr < 170) {
                            col = 0;
                        }
                    }
                }
                new_img.setRGB(i, j, new Color(col, col, col, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    ArrayList<ArrayList<Point>> res(BufferedImage img) {

        ArrayList<ArrayList<Point>> segmentation = new ArrayList<>();
        boolean visited[][] = new boolean[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int black = rgb.getRed();
                if (black == 0 && !visited[i][j]) {
                    segmentation.add(floodfill(i, j, black, img, visited));
                    visited[i][j] = true;
                }
            }
        }
        return segmentation;
    }

    ArrayList<Point> floodfill(int i, int j, int col, BufferedImage img, boolean visited[][]) {
        if (i < 0 || j < 0 || i > img.getWidth() || j > img.getHeight() || visited[i][j] || new Color(img.getRGB(i, j)).getRed() != 0) {
            return null;
        } else {
            Queue<Point> q = new LinkedList<>();
            q.add(new Point(i, j));
            Point min = new Point(i, j);
            Point max = new Point(i, j);
            while (!q.isEmpty()) {
                Point p = q.remove();
                if (p.x >= 0 && p.x < img.getWidth() && p.y >= 0 && p.y < img.getHeight()) {
                    if (p.x < min.x) {
                        min.x = p.x;
                    }
                    if (p.y < min.y) {
                        min.y = p.y;
                    }
                    if (p.x > max.x) {
                        max.x = p.x;
                    }
                    if (p.y > max.y) {
                        max.y = p.y;
                    }
                    if (!visited[p.x][p.y] && new Color(img.getRGB(p.x, p.y)).getRed() == 0) {
                        visited[p.x][p.y] = true;
                        q.add(new Point(p.x + 1, p.y + 1));
                        q.add(new Point(p.x + 1, p.y - 1));
                        q.add(new Point(p.x - 1, p.y - 1));
                        q.add(new Point(p.x - 1, p.y + 1));
                        q.add(new Point(p.x + 1, p.y));
                        q.add(new Point(p.x, p.y + 1));
                        q.add(new Point(p.x - 1, p.y));
                        q.add(new Point(p.x, p.y - 1));
                    }
                }
            }
            ArrayList<Point> pos = new ArrayList<>();
            pos.add(min);
            pos.add(max);
            return pos;
        }
    }

    boolean checkFace(ArrayList<Point> p, int threshold) {
        int w = p.get(1).x - p.get(0).x;
        int h = p.get(1).y - p.get(0).y;
        if (w < h) {
            if ((double) h / w <= 3) {
                if (w > threshold && h > threshold) {
                    return true;
                }
            }
        }
        return false;
    }

    BufferedImage draw(ArrayList<ArrayList<Point>> segment, BufferedImage img, boolean bool_check[], int slider_val) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                new_img.setRGB(i, j, img.getRGB(i, j));
            }
        }

        Graphics2D g2d = new_img.createGraphics();

        success = fail = 0;

        segment.forEach(temp -> {
            int w = temp.get(1).x - temp.get(0).x;
            int h = temp.get(1).y - temp.get(0).y;
            g2d.setFont(new Font("Arial", 1, w / 4));
            if (checkFace(temp, slider_val)) {
                success++;
                if (bool_check[0]) {
                    g2d.setColor(new Color(255, 255, 255));
                    g2d.drawString("Face", temp.get(0).x + 2, temp.get(0).y + (w / 4));
                    g2d.drawRect(temp.get(0).x, temp.get(0).y, w, h);
                }
            } else {
                fail++;
                if (bool_check[1]) {
                    g2d.setColor(new Color(255, 128, 128));
                    g2d.drawString("Other", temp.get(0).x + 2, temp.get(0).y + (w / 4));
                    g2d.drawRect(temp.get(0).x, temp.get(0).y, w, h);
                }
            }
        });
        return new_img;
    }
    //</editor-fold>

    void sort(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    void setZero(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    int getMax(int arr[]) {
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        return max;
    }

    BufferedImage blendModes(BufferedImage img, String type, int id) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResource("Resources/blend/" + id + ".jpg"));
            Graphics2D g2d = new_img.createGraphics();
            g2d.drawImage(image.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT), 0, 0, null);
        } catch (IOException e) {
            System.out.println("inside");
            System.out.println(e);
        }

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color new_rgb = new Color(img.getRGB(i, j));
                Color over_rgb = new Color(new_img.getRGB(i, j));
                Color res;
                switch (type) {
                    case "Linear Dodge":
                        res = linearDodge(new_rgb, over_rgb);
                        break;
                    case "Color Dodge":
                        res = colorDodge(new_rgb, over_rgb);
                        break;
                    case "Screen":
                        res = screen(new_rgb, over_rgb);
                        break;
                    case "Linear Burn":
                        res = linearBurn(new_rgb, over_rgb);
                        break;
                    case "Color Burn":
                        res = colorBurn(new_rgb, over_rgb);
                        break;
                    case "Multiply":
                        res = multiply(new_rgb, over_rgb);
                        break;
                    case "Darken":
                        res = darken(new_rgb, over_rgb);
                        break;
                    case "Lighten":
                        res = lighten(new_rgb, over_rgb);
                        break;
                    case "Difference":
                        res = difference(new_rgb, over_rgb);
                        break;
                    case "Exclusion":
                        res = exclusion(new_rgb, over_rgb);
                        break;
                    case "Substract":
                        res = substract(new_rgb, over_rgb);
                        break;
                    case "Divide":
                        res = divide(new_rgb, over_rgb);
                        break;
                    case "Overlay":
                        if ((double) new_rgb.getRed() / 255 <= 0.5 && (double) new_rgb.getGreen() / 255 <= 0.5 && (double) new_rgb.getBlue() / 255 <= 0.5) {
                            res = multiplyOverlay(new_rgb, over_rgb);
                        } else {
                            res = screenOverlay(new_rgb, over_rgb);
                        }
                        break;
                    case "Soft Light":
                        if ((double) over_rgb.getRed() / 255 <= 0.5 && (double) over_rgb.getGreen() / 255 <= 0.5 && (double) over_rgb.getBlue() / 255 <= 0.5) {
                            res = multiplySoftLight(new_rgb, over_rgb);
                        } else {
                            res = screenSoftLight(new_rgb, over_rgb);
                        }
                    default:
                        res = linearDodge(new_rgb, over_rgb);
                        break;
                }
                new_img.setRGB(i, j, new Color(res.getRed(), res.getGreen(), res.getBlue(), new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    Color multiplyOverlay(Color rgb1, Color rgb2) {
        double new_red = Math.round(2 * ((double) rgb1.getRed() / 255) * ((double) rgb2.getRed() / 255));
        double new_green = Math.round(2 * ((double) rgb1.getGreen() / 255) * ((double) rgb2.getGreen() / 255));
        double new_blue = Math.round(2 * ((double) rgb1.getBlue() / 255) * ((double) rgb2.getBlue() / 255));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color screenOverlay(Color rgb1, Color rgb2) {
        double new_red = Math.round(1 - (2 * (1 - ((double) rgb1.getRed() / 255)) * (1 - ((double) rgb2.getRed() / 255))));
        double new_green = Math.round(1 - (2 * (1 - ((double) rgb1.getGreen() / 255)) * (1 - ((double) rgb2.getGreen() / 255))));
        double new_blue = Math.round(1 - (2 * (1 - ((double) rgb1.getBlue() / 255)) * (1 - ((double) rgb2.getBlue() / 255))));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color darken(Color rgb1, Color rgb2) {
        int new_red = Math.min(rgb1.getRed(), rgb2.getRed());
        int new_green = Math.min(rgb1.getGreen(), rgb2.getGreen());
        int new_blue = Math.min(rgb1.getBlue(), rgb2.getBlue());
        return new Color(new_red, new_green, new_blue);
    }

    Color lighten(Color rgb1, Color rgb2) {
        int new_red = Math.max(rgb1.getRed(), rgb2.getRed());
        int new_green = Math.max(rgb1.getGreen(), rgb2.getGreen());
        int new_blue = Math.max(rgb1.getBlue(), rgb2.getBlue());
        return new Color(new_red, new_green, new_blue);
    }

    Color multiply(Color rgb1, Color rgb2) {
        int new_red = rgb1.getRed() * rgb2.getRed();
        int new_green = rgb1.getGreen() * rgb2.getGreen();
        int new_blue = rgb1.getBlue() * rgb2.getBlue();
        return new Color(clipping(new_red), clipping(new_green), clipping(new_blue));
    }

    Color colorBurn(Color rgb1, Color rgb2) {
        double new_red = Math.round(1 - ((1 - (double) ((((double) rgb1.getRed() / 255)) / ((double) rgb2.getRed() / 255)))));
        double new_green = Math.round(1 - ((1 - (double) ((((double) rgb1.getGreen() / 255)) / ((double) rgb2.getGreen() / 255)))));
        double new_blue = Math.round(1 - ((1 - (double) (((double) rgb1.getBlue() / 255)) / ((double) rgb2.getBlue() / 255))));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color linearBurn(Color rgb1, Color rgb2) {
        double new_red = Math.round(((double) rgb1.getRed() / 255) + ((double) rgb2.getRed() / 255) - 1);
        double new_green = Math.round(((double) rgb1.getGreen() / 255) + ((double) rgb2.getGreen() / 255) - 1);
        double new_blue = Math.round(((double) rgb1.getBlue() / 255) + ((double) rgb2.getBlue() / 255) - 1);
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color screen(Color rgb1, Color rgb2) {
        double new_red = Math.round(1 - ((1 - ((double) rgb1.getRed() / 255)) * (1 - ((double) rgb2.getRed() / 255))));
        double new_green = Math.round(1 - ((1 - ((double) rgb1.getGreen() / 255)) * (1 - ((double) rgb2.getGreen() / 255))));
        double new_blue = Math.round(1 - ((1 - ((double) rgb1.getBlue() / 255)) * (1 - ((double) rgb2.getBlue() / 255))));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color colorDodge(Color rgb1, Color rgb2) {
        double new_red = Math.round(((double) rgb1.getRed() / 255)) / ((1 - ((double) rgb2.getRed() / 255)));
        double new_green = Math.round(((double) rgb1.getGreen() / 255)) / ((1 - ((double) rgb2.getGreen() / 255)));
        double new_blue = Math.round(((double) rgb1.getBlue() / 255)) / ((1 - ((double) rgb2.getBlue() / 255)));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color linearDodge(Color rgb1, Color rgb2) {
        int new_red = rgb1.getRed() + rgb2.getRed();
        int new_green = rgb1.getGreen() + rgb2.getGreen();
        int new_blue = rgb1.getBlue() + rgb2.getBlue();
        return new Color(clipping(new_red), clipping(new_green), clipping(new_blue));
    }

    Color difference(Color rgb1, Color rgb2) {
        int new_red = Math.abs(rgb1.getRed() - rgb2.getRed());
        int new_green = Math.abs(rgb1.getGreen() - rgb2.getGreen());
        int new_blue = Math.abs(rgb1.getBlue() - rgb2.getBlue());
        return new Color(clipping(new_red), clipping(new_green), clipping(new_blue));
    }

    Color exclusion(Color rgb1, Color rgb2) {
        double new_red = Math.round(0.5 - (2 * (((double) rgb1.getRed() / 255) - 0.5) * (((double) rgb2.getRed() / 255) - 0.5)));
        double new_green = Math.round(0.5 - (2 * (((double) rgb1.getGreen() / 255) - 0.5) * (((double) rgb2.getGreen() / 255) - 0.5)));
        double new_blue = Math.round(0.5 - (2 * (((double) rgb1.getBlue() / 255) - 0.5) * (((double) rgb2.getBlue() / 255) - 0.5)));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color substract(Color rgb1, Color rgb2) {
        int new_red = (rgb2.getRed() - rgb1.getRed());
        int new_green = (rgb2.getGreen() - rgb1.getGreen());
        int new_blue = (rgb2.getBlue() - rgb1.getBlue());
        return new Color(clipping(new_red), clipping(new_green), clipping(new_blue));
    }

    Color divide(Color rgb1, Color rgb2) {
        double new_red = Math.round((double) rgb2.getRed() / rgb1.getRed());
        double new_green = Math.round((double) rgb2.getGreen() / rgb1.getGreen());
        double new_blue = Math.round((double) rgb2.getBlue() / rgb1.getBlue());
        return new Color(clipping((int) new_red), clipping((int) new_green), clipping((int) new_blue));
    }

    Color screenSoftLight(Color rgb1, Color rgb2) {
        double new_red = Math.round(1 * ((1 - ((double) rgb1.getRed() / 255)) * (1 - (((double) rgb2.getRed()) - 0.5))));
        double new_green = Math.round(1 * ((1 - ((double) rgb1.getGreen() / 255)) * (1 - (((double) rgb2.getGreen()) - 0.5))));
        double new_blue = Math.round(1 * ((1 - ((double) rgb1.getBlue() / 255)) * (1 - (((double) rgb2.getBlue()) - 0.5))));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    Color multiplySoftLight(Color rgb1, Color rgb2) {
        double new_red = Math.round((((double) rgb1.getRed() / 255)) * (((double) rgb2.getRed()) + 0.5));
        double new_green = Math.round((((double) rgb1.getGreen() / 255)) * (((double) rgb2.getGreen()) + 0.5));
        double new_blue = Math.round((((double) rgb1.getBlue() / 255)) * (((double) rgb2.getBlue()) + 0.5));
        return new Color(clipping((int) new_red * 255), clipping((int) new_green * 255), clipping((int) new_blue * 255));
    }

    BufferedImage histogramEqualization(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int histr[] = new int[256];
        int histg[] = new int[256];
        int histb[] = new int[256];
        int res_histr[] = new int[256];
        int res_histg[] = new int[256];
        int res_histb[] = new int[256];
        setZero(histr);
        setZero(histg);
        setZero(histb);
        setZero(res_histr);
        setZero(res_histr);
        setZero(res_histr);
        int size = img.getWidth() * img.getHeight();
        double sumr, sumg, sumb;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                histr[new Color(img.getRGB(i, j)).getRed()]++;
                histg[new Color(img.getRGB(i, j)).getGreen()]++;
                histb[new Color(img.getRGB(i, j)).getBlue()]++;
            }
        }
        for (int i = 0; i < 256; i++) {
            sumr = sumg = sumb = 0;
            for (int j = 0; j <= i; j++) {
                sumr += histr[j];
                sumg += histg[j];
                sumb += histb[j];
            }
            res_histr[i] = (int) Math.floor(sumr * 255) / size;
            res_histg[i] = (int) Math.floor(sumg * 255) / size;
            res_histb[i] = (int) Math.floor(sumb * 255) / size;
        }
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j), true);
                new_img.setRGB(i, j, new Color(res_histr[rgb.getRed()], res_histg[rgb.getGreen()], res_histb[rgb.getBlue()], rgb.getAlpha()).getRGB());
            }
        }
        return new_img;
    }

    BufferedImage median(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int arrr[] = new int[9];
        int arrg[] = new int[9];
        int arrb[] = new int[9];
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                setZero(arrr);
                setZero(arrg);
                setZero(arrb);
                int count = 0;
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        try {
                            arrr[count] = (new Color(img.getRGB(i + k, j + l)).getRed());
                            arrg[count] = (new Color(img.getRGB(i + k, j + l)).getGreen());
                            arrb[count] = (new Color(img.getRGB(i + k, j + l)).getBlue());
                        } catch (ArrayIndexOutOfBoundsException e) {
                            arrr[count] = arrg[count] = arrb[count] = 0;
                        }
                        count++;
                    }
                }
                sort(arrr);
                sort(arrg);
                sort(arrb);
                new_img.setRGB(i, j, new Color(arrr[4], arrg[4], arrb[4], new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }
        return new_img;
    }

    public BufferedImage rgb(BufferedImage img, int val_r, int val_g, int val_b) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int new_red = clipping(rgb.getRed() + val_r);
                int new_green = clipping(rgb.getGreen() + val_g);
                int new_blue = clipping(rgb.getBlue() + val_b);
                new_img.setRGB(i, j, new Color(new_red, new_green, new_blue, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    public BufferedImage rgbFilter(BufferedImage img, int val_r, int val_g, int val_b) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int new_red = (rgb.getRed() >= val_r) ? rgb.getRed() : 0;
                int new_green = (rgb.getGreen() >= val_g) ? rgb.getGreen() : 0;
                int new_blue = (rgb.getBlue() >= val_b) ? rgb.getBlue() : 0;
                new_img.setRGB(i, j, new Color(new_red, new_green, new_blue, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    public BufferedImage contrast(BufferedImage img, int val) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int min = 0 + val;
                int max = 255 - val;
                int new_red = clipping(255 * (rgb.getRed() - min) / (max - min));
                int new_green = clipping(255 * (rgb.getGreen() - min) / (max - min));
                int new_blue = clipping(255 * (rgb.getBlue() - min) / (max - min));
                new_img.setRGB(i, j, new Color(new_red, new_green, new_blue, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    public BufferedImage brightness(BufferedImage img, int val) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int new_red = clipping(rgb.getRed() + val);
                int new_green = clipping(rgb.getGreen() + val);
                int new_blue = clipping(rgb.getBlue() + val);
                new_img.setRGB(i, j, new Color(new_red, new_green, new_blue, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    public BufferedImage binarization(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int gray = (rgb.getRed() + rgb.getGreen() + rgb.getBlue()) / 3;
                if (gray < 128) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                new_img.setRGB(i, j, new Color(gray, gray, gray, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    public BufferedImage negative(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int new_red = 255 - rgb.getRed();
                int new_green = 255 - rgb.getGreen();
                int new_blue = 255 - rgb.getBlue();
                new_img.setRGB(i, j, new Color(new_red, new_green, new_blue, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    public BufferedImage grayscale(BufferedImage img, String type) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int gray = 0;
                switch (type) {
                    case "Average":
                        gray = grayscaleAverage(rgb);
                        break;
                    case "Luminosity":
                        gray = grayscaleLuminosity(rgb);
                        break;
                    case "Desaturation":
                        gray = grayscaleDesaturation(rgb);
                        break;
                    case "Max Decomposition":
                        gray = grayscaleMaxDecomposition(rgb);
                        break;
                    case "Min Decomposition":
                        gray = grayscaleMinDecomposition(rgb);
                        break;
                    case "Single Color Channel (Red)":
                        gray = grayscaleSingleColorChannelRed(rgb);
                        break;
                    case "Single Color Channel (Green)":
                        gray = grayscalesingleColorChannelGreen(rgb);
                        break;
                    case "Single Color Channel (Blue)":
                        gray = grayscalesingleColoChannelrBlue(rgb);
                        break;
                    default:
                        break;
                }
                new_img.setRGB(i, j, new Color(gray, gray, gray, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

    public int grayscaleAverage(Color rgb) {
        return (rgb.getRed() + rgb.getGreen() + rgb.getBlue()) / 3;
    }

    public int grayscaleLuminosity(Color rgb) {
        return (int) (rgb.getRed() * 0.3 + rgb.getGreen() * 0.59 + rgb.getBlue() * 0.11);
    }

    public int grayscaleDesaturation(Color rgb) {
        return (int) ((Math.max(Math.max(rgb.getRed(), rgb.getGreen()), rgb.getBlue())) + (Math.min(Math.min(rgb.getRed(), rgb.getGreen()), rgb.getBlue()))) / 2;
    }

    public int grayscaleMaxDecomposition(Color rgb) {
        return (int) (Math.max(Math.max(rgb.getRed(), rgb.getGreen()), rgb.getBlue()));
    }

    public int grayscaleMinDecomposition(Color rgb) {
        return (int) (Math.min(Math.min(rgb.getRed(), rgb.getGreen()), rgb.getBlue()));
    }

    public int grayscaleSingleColorChannelRed(Color rgb) {
        return (int) rgb.getRed();
    }

    public int grayscalesingleColorChannelGreen(Color rgb) {
        return (int) rgb.getGreen();
    }

    public int grayscalesingleColoChannelrBlue(Color rgb) {
        return (int) rgb.getBlue();
    }

    public BufferedImage correlation(BufferedImage img, double filter[][]) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int tempr, tempg, tempb;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                tempr = tempg = tempb = 0;
                int xx = -(int) Math.floor(filter[0].length / 2);
                for (int k = 0; k < filter.length; k++) {
                    int yy = -(int) Math.floor(filter[0].length / 2);
                    for (int l = 0; l < filter[k].length; l++) {
                        try {
                            tempr += filter[k][l] * (new Color(img.getRGB(i + xx, j + yy))).getRed();
                            tempg += filter[k][l] * (new Color(img.getRGB(i + xx, j + yy))).getGreen();
                            tempb += filter[k][l] * (new Color(img.getRGB(i + xx, j + yy))).getBlue();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            tempr += filter[k][l] * 0;
                            tempg += filter[k][l] * 0;
                            tempb += filter[k][l] * 0;
                        }
                        yy++;
                    }
                    xx++;
                }
                tempr = clipping(tempr);
                tempg = clipping(tempg);
                tempb = clipping(tempb);
                new_img.setRGB(i, j, new Color(tempr, tempg, tempb, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }
        return new_img;
    }

    public int clipping(int col) {
        if (col > 255) {
            return 255;
        } else if (col < 0) {
            return 0;
        } else {
            return col;
        }
    }

    public ImageIcon resizeImage(ImageIcon image, int width, int height) {
        return new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public ImageIcon rescaleImage(ImageIcon image, int w, int h) {

        int max_image_width = w;
        int max_image_height = h;

        if (image.getIconWidth() < max_image_width) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(max_image_width, image.getIconHeight() * max_image_width / image.getIconWidth(), Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }
        if (image.getIconHeight() < max_image_height) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(image.getIconWidth() * max_image_height / image.getIconHeight(), max_image_height, Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }

        if (image.getIconWidth() > max_image_width) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(max_image_width, image.getIconHeight() * max_image_width / image.getIconWidth(), Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }
        if (image.getIconHeight() > max_image_height) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(image.getIconWidth() * max_image_height / image.getIconHeight(), max_image_height, Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }
        return image;
    }

    public BufferedImage mirrorX(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int yy = img.getHeight() - 1;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color rgb = new Color(img.getRGB(j, yy), true);
                new_img.setRGB(j, i, rgb.getRGB());
            }
            yy--;
        }
        return new_img;
    }

    public BufferedImage mirrorY(BufferedImage img) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int xx;
        for (int i = 0; i < img.getHeight(); i++) {
            xx = img.getWidth() - 1;
            for (int j = 0; j < img.getWidth(); j++) {
                Color rgb = new Color(img.getRGB(xx, i), true);
                xx--;
                new_img.setRGB(j, i, rgb.getRGB());
            }
        }
        return new_img;
    }

    public BufferedImage rotateCW(BufferedImage img) {
        Color rgb[][] = new Color[img.getHeight()][img.getWidth()];
        BufferedImage new_img = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
        int x = 0, y;
        for (int i = img.getHeight() - 1; i >= 0; i--) {
            y = 0;
            for (int j = 0; j < img.getWidth(); j++) {
                int hex = img.getRGB(j, i);
                rgb[x][y] = new Color(hex, true);
                y++;
            }
            x++;
        }
        for (int i = 0; i < rgb.length; i++) {
            for (int j = 0; j < rgb[i].length; j++) {
                int col = rgb[i][j].getRGB();
                new_img.setRGB(i, j, col);
            }
        }
        return new_img;
    }

    public BufferedImage rotateCCW(BufferedImage img) {
        Color rgb[][] = new Color[img.getHeight()][img.getWidth()];
        BufferedImage new_img = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
        int x = 0, y;
        for (int i = 0; i < img.getHeight(); i++) {
            y = 0;
            for (int j = img.getWidth() - 1; j >= 0; j--) {
                int hex = img.getRGB(j, i);
                rgb[x][y] = new Color(hex, true);
                y++;
            }
            x++;
        }
        for (int i = 0; i < rgb.length; i++) {
            for (int j = 0; j < rgb[i].length; j++) {
                int col = rgb[i][j].getRGB();
                new_img.setRGB(i, j, col);
            }
        }
        return new_img;
    }

    public BufferedImage scale(BufferedImage img, double xx) {
        BufferedImage new_img = new BufferedImage((int) (img.getWidth() * xx), (int) (img.getHeight() * xx), BufferedImage.TYPE_INT_RGB);
        Color rgb[][] = new Color[(int) (img.getWidth() * xx)][(int) (img.getHeight() * xx)];
        int y = 0, z = 0;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int hex = img.getRGB(i, j);
                for (int k = 0; k < xx; k++) {
                    for (int l = 0; l < xx; l++) {
                        try {
                            rgb[(int) (i * xx + k)][(int) (j * xx + l)] = new Color(hex, true);
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                }
            }
        }
        for (int i = 0; i < new_img.getWidth(); i++) {
            for (int j = 0; j < new_img.getHeight(); j++) {
                int col;
                try {
                    col = rgb[i][j].getRGB();
                } catch (NullPointerException e) {
                    col = 0;
                }
                new_img.setRGB(i, j, col);
            }
        }
        return new_img;
    }

}
