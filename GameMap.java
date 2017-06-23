/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamemaybe;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author suleman
 */
public class GameMap {

    int walkingAreax;
    int walkingAreay;
    int walkingArealimx;
    int walkingArealimy;
    int framesCount;
    String Name;
    HashMap<String, BufferedImage> h;
    HashMap<String, int[]> H;   
    boolean resize=false;

    GameMaybe M;

    GameMap(String path) {

        try {
            h = new HashMap();
            H = new HashMap();
            Scanner s = new Scanner(new File(path));
            Name = s.nextLine();
            framesCount = s.nextInt();
            System.out.println(framesCount);
            int[] framessizes = new int[framesCount];

            String[] paths = new String[framesCount];
            for (int i = 0; i < framesCount; i++) {
                paths[i] = s.next();
                System.out.println(paths[i]);
                BufferedImage tem = ImageIO.read(new File(paths[i]));
                h.put("" + i, tem);
                int[] coord = new int[2];
                coord[0] = s.nextInt();
                coord[1] = s.nextInt();
                System.out.println(coord[0] + " " + coord[1]);
                H.put("" + i, coord);
            }
            walkingAreax = s.nextInt();
            walkingArealimx = s.nextInt();
            walkingAreay = s.nextInt();
            walkingArealimy = s.nextInt();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameMap.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    BufferedImage l, l2;

    BufferedImage in() {
        BufferedImage b = new BufferedImage(this.walkingArealimx, this.walkingArealimy, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < framesCount; i++) {
            b.getGraphics().drawImage(h.get("" + i), H.get("" + i)[0], H.get("" + i)[1], M);
        }
        if(resize){
            double ratiox= ((double)M.W)/this.walkingArealimx;
            double ratioy= ((double)M.H)/this.walkingArealimy;

            l = toBufferedImage(b.getScaledInstance(M.W, M.H, b.SCALE_SMOOTH));
            l2 = toBufferedImage(b.getScaledInstance(M.W, M.H, b.SCALE_SMOOTH));

            this.walkingAreax=(int)(ratiox*this.walkingAreax);
            this.walkingArealimx=(int)(ratiox*this.walkingArealimx);
            this.walkingAreay=(int)(ratioy*this.walkingAreay);
            this.walkingArealimy=(int)(ratioy*this.walkingArealimy);
        }
        else{
            if (walkingArealimx < M.W || walkingArealimy < M.H) {
                double ratiox= ((double)M.W)/this.walkingArealimx;
                double ratioy= ((double)M.H)/this.walkingArealimy;

                l = toBufferedImage(b.getScaledInstance(M.W, M.H, b.SCALE_SMOOTH));
                l2 = toBufferedImage(b.getScaledInstance(M.W, M.H, b.SCALE_SMOOTH));

                resize =true;

                this.walkingAreax=(int)(ratiox*this.walkingAreax);
                this.walkingArealimx=(int)(ratiox*this.walkingArealimx);
                this.walkingAreay=(int)(ratioy*this.walkingAreay);
                this.walkingArealimy=(int)(ratioy*this.walkingArealimy);
            } 
            else {
                l = b;
                l2 = deepCopy(b);
                resize = false;
            }
        }
        return l;
    }

    void Draw(int x, int y) {
        int drawx = 0, drawy = 0;
        if (x <= (M.W / 2)) {
            drawx = 0;
        } else if (x >= (l2.getWidth() - M.W / 2)) {
            drawx = -l2.getWidth() + M.W;
            System.out.println(l2.getWidth() + "dsadasdasdasasa");
        } else {
            drawx = -(x - M.W / 2);
        }
        if (y <= (M.H / 2)) {
            drawy = 0;
        } else if (y >= (l2.getHeight() - M.H / 2)) {
            drawy = -l2.getHeight() + M.H;;
            System.out.println(l2.getWidth() + "dsadasdasdasasa");
        } else {
            drawy = -(y - M.H / 2);
        }

        M.bbg.drawImage(l,drawx, drawy , M);
        l.getGraphics().drawImage(l2,0,0, M);

    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
