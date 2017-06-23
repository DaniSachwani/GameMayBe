/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamemaybe;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suleman
 */

public class Game implements Runnable{
    GameMaybe M;
    InputHandler i;
    int selected =1;
    int num_of_players=3;
    Random r = new Random();
    Game(GameMaybe m){
        M=m;
        i = new InputHandler(M);        
    }
    
    public void run(){
        while(M.running){
            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(selected==1){
                selected = menu();
            }
            if(selected==2){
                selected = vsmode();
            }
            if(selected==3){
                selected = options();
            }
            if(selected==4){
                selected = online();
            }
            if(selected==5){
                selected = credits();
            }
            if(selected==6){
                selected = ingame();
            }
            
            
        }
    }
    
    public int menu(){
        
        if(MouseInfo.getPointerInfo().getLocation().getX() >=100 &&
           MouseInfo.getPointerInfo().getLocation().getY() >=160 &&
           MouseInfo.getPointerInfo().getLocation().getX() <=450 &&
           MouseInfo.getPointerInfo().getLocation().getY() <=210){
            M.bbg.drawImage(M.img[1], 0, 0, null);
            if(i.mouseclicked){
                i.mouseclicked=false;
            
                return 2;
            }
        }
        else if(MouseInfo.getPointerInfo().getLocation().getX() >=120 &&
           MouseInfo.getPointerInfo().getLocation().getY() >=280 &&
           MouseInfo.getPointerInfo().getLocation().getX() <=410 &&
           MouseInfo.getPointerInfo().getLocation().getY() <=330){
            M.bbg.drawImage(M.img[2], 0, 0, null);
            if(i.mouseclicked){
                i.mouseclicked=false;
            
                return 3;
            }
        }
        else if(MouseInfo.getPointerInfo().getLocation().getX() >=175 &&
           MouseInfo.getPointerInfo().getLocation().getY() >=400 &&
           MouseInfo.getPointerInfo().getLocation().getX() <=430 &&
           MouseInfo.getPointerInfo().getLocation().getY() <=450){
            M.bbg.drawImage(M.img[3], 0, 0, null);
            if(i.mouseclicked){
                i.mouseclicked=false;
            
                return 4;
            }
        }
        else if(MouseInfo.getPointerInfo().getLocation().getX() >=200 &&
           MouseInfo.getPointerInfo().getLocation().getY() >=530 &&
           MouseInfo.getPointerInfo().getLocation().getX() <=500 &&
           MouseInfo.getPointerInfo().getLocation().getY() <=580){
            M.bbg.drawImage(M.img[4], 0, 0, null);
            if(i.mouseclicked){
                i.mouseclicked=false;
            
                return 5;
            }
        }
        else{
            M.bbg.drawImage(M.img[0], 0, 0, null);
            i.mouseclicked=false;
            return 1;
        }
        
        return 1;
    }
    
    private int options() {
        return 0;
        
    }

    private int ingame(){
        
        GameMap gb = M.backs[r.nextInt(100)%M.backs.length];
        gb.M=M;
        BufferedImage b= gb.in();
        //gb.Draw(0,0);
            
        PlayerController[] g= new PlayerController[num_of_players];
        g[0]= new Gamer(M);
        g[0].b= b;
        g[0].c = new character(M.fighters[r.nextInt(100)%M.fighters.length]);
        g[0].c.MapPosX = r.nextInt(M.W);
        g[0].c.MapPosY = r.nextInt(M.H);    
        
        for(int j=1;j<num_of_players;j++){
            g[j] = new Computer(M);
            g[j].b = b;
            g[j].c = new character(M.fighters[r.nextInt(100)%M.fighters.length]);
            g[j].c.MapPosX = r.nextInt(M.W);
            g[j].c.MapPosY = r.nextInt(M.H);
        }
        int play =0;
        while(!i.isKeyDown(KeyEvent.VK_ESCAPE)){
            
            gb.Draw(g[play].c.MapPosX,g[play].c.MapPosY);
            for(int j=0;j<num_of_players;j++){
                g[j].run();
                
                if(g[j].c.MapPosX+g[j].W*g[j].c.imageresize>=gb.walkingArealimx){
                    g[j].c.MapPosX=gb.walkingArealimx-(int) (g[j].W*g[j].c.imageresize);
                } if(g[j].c.MapPosX<=gb.walkingAreax ){
                    g[j].c.MapPosX=gb.walkingAreax;
                } if(g[j].c.MapPosY+(int) (g[j].H*g[j].c.imageresize) >= gb.walkingArealimy){
                    g[j].c.MapPosY=gb.walkingArealimy-(int) (g[j].H*g[j].c.imageresize);
                } if(g[j].c.MapPosY+(int) (g[j].H*g[j].c.imageresize) <=gb.walkingAreay ){
                    g[j].c.MapPosY=gb.walkingAreay-(int) (g[j].H*g[j].c.imageresize);
                }
                System.out.println(gb.walkingAreax+" "+gb.walkingArealimx+" "+gb.walkingAreay+" "+gb.walkingArealimy);
                System.out.println(g[j].c.MapPosX+" "+g[j].c.MapPosY);
            }
            for(int x=0;x<g.length;x++){
                for(int xx=0;xx<g.length;xx++){
                    if(x!=xx)
                    if(g[x].c.MapPosY < g[xx].c.MapPosY){
                        if(x==play){
                            play=xx;
                        }   
                        if(xx==play){
                            play=x;
                        }
                        
                        
                        PlayerController temp = g[x];
                        g[x]= g[xx];
                        g[xx]= temp;
                    }
                }
            }
            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return 1;
    }
    private int online() {
        return -1;
    }

    private int credits() {
        if(MouseInfo.getPointerInfo().getLocation().getX() >=1030 &&
           MouseInfo.getPointerInfo().getLocation().getY() >=600 &&
           MouseInfo.getPointerInfo().getLocation().getX() <=1270 &&
           MouseInfo.getPointerInfo().getLocation().getY() <=700){
            M.bbg.drawImage(M.creditsimg[1], 0, 0, null);
            if(i.mouseclicked){
                i.mouseclicked=false;
            
                return 1;
            }
        }
        else{
            M.bbg.drawImage(M.creditsimg[0], 0, 0, null);
            i.mouseclicked=false;
            return 5;
        }
        return 5;
    }
    
    private int vsmode() {
        count++;
        if(i.isKeyDown(KeyEvent.VK_ENTER)){
            return 6;
        }
        
        if(MouseInfo.getPointerInfo().getLocation().getX() >=1030 &&
           MouseInfo.getPointerInfo().getLocation().getY() >=600 &&
           MouseInfo.getPointerInfo().getLocation().getX() <=1270 &&
           MouseInfo.getPointerInfo().getLocation().getY() <=700){
            BufferedImage b = new BufferedImage(M.vsimg[0].getWidth(),M.vsimg[0].getHeight(),BufferedImage.TYPE_INT_RGB);
            b.getGraphics().drawImage(M.vsimg[1], 0, 0, null);
            b.getGraphics().drawImage(M.fighters[0].frames.get(""+0+" "+((count/M.fighters[0].actionslatency.get(""+0+" "+(count%M.fighters[0].actionssizes.get(0))))%M.fighters[0].actionssizes.get(0))), 400, 450, null);
            ((Graphics2D)b.getGraphics()).drawString(M.fighters[0].Name, 400, 400);
            M.bbg.drawImage(b, 0, 0, null);
            if(i.mouseclicked){
                i.mouseclicked=false;
            
                return 1;
            }
        }
        else{
            BufferedImage b = new BufferedImage(M.vsimg[0].getWidth(),M.vsimg[0].getHeight(),BufferedImage.TYPE_INT_RGB);
            b.getGraphics().drawImage(M.vsimg[0], 0, 0, null);
            b.getGraphics().drawImage(M.fighters[0].frames.get(""+0+" "+((count/M.fighters[0].actionslatency.get(""+0+" "+(count%M.fighters[0].actionssizes.get(0))))%M.fighters[0].actionssizes.get(0))), 400, 450, null);
            ((Graphics2D)b.getGraphics()).drawString(M.fighters[0].Name, 400, 400);
            M.bbg.drawImage(b, 0, 0, null);
            
            i.mouseclicked=false;
            return 2;
        }
        return 2;
    }
    
    int count=0;
    
}
