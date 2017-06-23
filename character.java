/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamemaybe;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author suleman
 */
public class character {
    int MapPosX =0;
    int MapPosY =0;
    int walking_speed=0;
    int running_speed=0;
    float imageresize;
    int abovegrd=0;
    String Name;
    HashMap<Integer,LinkedList> incoming;
    HashMap<String,BufferedImage> frames,shadframes;
    HashMap<String,Integer> actionslatency,dispx,dispy;
    HashMap<String,HashMap<String,Boolean>> availactions;
    
    int actionsCount =0;
    HashMap<Integer,Integer> actionssizes;
    HashMap<Integer,Boolean> actionsinterrupt;
    
    character(character a){
        MapPosX = a.MapPosX;
        MapPosY = a.MapPosY;
        availactions= new HashMap(a.availactions);
        walking_speed = a.walking_speed;
        running_speed = a.running_speed;
        Name = a.Name;
        incoming = new HashMap(a.incoming);
        frames= new HashMap(a.frames);
        shadframes= new HashMap(a.shadframes);
        actionsCount = a.actionsCount;
        actionssizes = new HashMap(a.actionssizes);
        actionsinterrupt = new HashMap(a.actionsinterrupt);
        actionslatency= new HashMap(a.actionslatency);
        dispx= new HashMap(a.dispx);
        dispy= new HashMap(a.dispy);
        imageresize = a.imageresize;
    }
    
    character(String path){
        frames = new HashMap();
        shadframes = new HashMap();
        actionslatency = new HashMap();
        dispx = new HashMap();
        dispy = new HashMap();
        incoming = new HashMap();
        this.availactions =new HashMap();
        try {
            Scanner s = new Scanner(new File(path));
            
            Name = s.next();
            imageresize = s.nextFloat();
            walking_speed = Integer.parseInt(s.next().substring(14));
            running_speed = Integer.parseInt(s.next().substring(14));
            actionsCount = s.nextInt();
            actionssizes = new HashMap();
            actionsinterrupt= new HashMap();
            String[] paths = new String[actionsCount]; 
            for(int i = 0;i <actionsCount ;i++){
                paths[i] = s.next();
                System.out.println(paths[i]);
            }
            
            int count = 0;
            int c = 0;
            int key = 0;
            boolean action = false;
            while(s.hasNext()){
                String inp=s.next();
                if(action){
                    if(c >= actionssizes.get(count)){
                        key = 0;
                        count++;
                        action = false;
                    }
                    else{
                        String[] indexes = s.nextLine().split(" ");
                        int fileNo= Integer.parseInt(indexes[6].substring(6));
                        BufferedImage temp = ImageIO.read(new File(paths[fileNo]));
                        int x = Integer.parseInt(indexes[2].split(",")[0]);
                        int y = Integer.parseInt(indexes[2].split(",")[1]);
                        int xlim = Integer.parseInt(indexes[2].split(",")[2]);
                        int ylim = Integer.parseInt(indexes[2].split(",")[3]);
                        System.out.print("x:"+x+" y:"+y+" xlim:"+xlim+" ylim:"+ylim);
                        String k = ""+count+" "+c;
                        frames.put(k, cropImage(temp,x,y,xlim,ylim));
                        shadframes.put(k, shadow(deepCopy(cropImage(temp,x,y,xlim,ylim))));
                        System.out.print(" lat="+indexes[3].substring(4));
                        actionslatency.put(k, Integer.parseInt(indexes[3].substring(4)));
                        System.out.print(" dispx="+indexes[4].substring(6));
                        dispx.put(k, Integer.parseInt(indexes[4].substring(6)));
                        System.out.println(" dispy="+indexes[5].substring(6));
                        dispy.put(k, Integer.parseInt(indexes[5].substring(6)));
                        
                        String tmp =indexes[7];
                        System.out.println(tmp);
                        HashMap<String,Boolean> tm = new HashMap();
                        tm.put("right", false);
                        tm.put("left", false);
                        tm.put("up", false);
                        tm.put("down", false);
                        tm.put("A", false);
                        tm.put("J", false);
                        tm.put("D", false);
                        tm.put("R", false);
                        tm.put("idle", false);
                        if(!tmp.equals("[]")){
                            String[] tmm = tmp.substring(1,tmp.length()-1).split(",");
                            for (String tmm1 : tmm) {
                                System.out.println(tmm1+" = true");
                                tm.put(tmm1, true);
                            }
                        }
                        this.availactions.put(k,tm);

                        c++;
                    }
                }
                if(inp.equals("<action>")){
                    key = s.nextInt();
                    actionssizes.put(count, key);
                    //actionsinterrupt.put(count, s.nextBoolean());
                    
                    String tmp =s.next().substring(9);
                    String[] ll = tmp.substring(1,tmp.length()-1).split(",");
                    LinkedList incoming1=new LinkedList();
                    System.out.println("incoming=");
                    for (String tmm1 : ll) {
                        System.out.print(tmm1+" ");
                        incoming1.add(tmm1);
                    }
                    System.out.println("");
                    this.incoming.put(count, incoming1);
                    
                    c=0;
                    action =true;
                    System.out.println("count: "+count);
                }
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(character.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private BufferedImage cropImage(BufferedImage src,int x,int y, int xlim, int ylim) {
        BufferedImage dest = src.getSubimage( x, y, xlim, ylim);
        return dest; 
    }
    private BufferedImage shadow(BufferedImage B){
        
                // Manual manipulation...
                for (int x = 0; x < B.getWidth(); x++) {
                    for (int y = 0; y < B.getHeight(); y++) {
                        int color = B.getRGB(x, y) | 0x00FFFFFF;
                        B.setRGB(x, y, color & 0xff000000);
                    }
                }
                return B;
    }
    
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
