/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamemaybe;

import java.util.LinkedList;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author suleman
 */
public class GameMaybe extends JFrame{

    /**
     * @param args the command line arguments
     */
    LinkedList<Objects> GameObjects;
    Graphics g;
    BufferedImage backBuffer;
    BufferedImage img[] = new BufferedImage[5];
    BufferedImage creditsimg[] = new BufferedImage[2];
    BufferedImage vsimg[] = new BufferedImage[2];
    Graphics bbg;
    boolean running =true;
    boolean display =true;
    boolean lock = false;
    GameMap[] backs =null;
    character[] fighters =null;
            
    
    public static void main(String[] args) {
        // TODO code application logic here
        GameMaybe G= new GameMaybe();
        
        G.setGame();

        Thread t = new Thread(new Game(G));
        t.start();
        
        while(G.running){
            if(G.display){
                G.g.drawImage(G.backBuffer, 0, 0, G);
            }
        }
        //G.displayMenu();
    }
    int W=1300;
    int H=856;
    void setGame(){
        setTitle("Game MayBe"); 
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setVisible(true); 
        setResizable(true);

        W = this.getWidth();
        H = this.getHeight();
        
        backBuffer = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB); 
        bbg = backBuffer.getGraphics(); 
        
        g = getGraphics(); 
        // Create a BufferedImage of the same size as the Image
        try {
            
            //loading menu images
            img[0] = ImageIO.read(new File("system/Menu items/default.jpg"));
            img[1] = ImageIO.read(new File("system/Menu items/1.jpg"));
            img[2] = ImageIO.read(new File("system/Menu items/2.jpg"));
            img[3] = ImageIO.read(new File("system/Menu items/3.jpg"));
            img[4] = ImageIO.read(new File("system/Menu items/4.jpg"));
            
            //loading credits images  
            creditsimg[0] = ImageIO.read(new File("system/Menu items/credits/1.jpg"));
            creditsimg[1] = ImageIO.read(new File("system/Menu items/credits/back.jpg"));
            
            vsimg[0] = ImageIO.read(new File("system/Menu items/vs mode/1.jpg"));
            vsimg[1] = ImageIO.read(new File("system/Menu items/vs mode/back.jpg"));
            
            Scanner s= new Scanner(new File("system/Data.txt"));
            
            int GameMapCount =0;
            int CharacterCount =0;
            
            boolean inputbacks = false;
            boolean inputcharacters = false;
            while(s.hasNext()){
                String inp=s.next();
                if(inputbacks){
                    if(GameMapCount >= backs.length){
                        inputbacks = false;
                    }
                    else{
                        System.out.println(inp);
                        backs[GameMapCount]= new GameMap(inp);
                        GameMapCount++;
                    }
                }
                if(inp.equals("<backgrounds>")){
                    
                    backs = new GameMap[s.nextInt()];
                    inputbacks =true;
                }
                
                if(inputcharacters){
                    if(CharacterCount >= fighters.length){
                        inputcharacters = false;
                    }
                    else{
                        System.out.println(inp);
                        fighters[CharacterCount]= new character(inp);
                        CharacterCount++;
                    }
                }
                if(inp.equals("<characters>")){
                    fighters = new character[s.nextInt()];
                    inputcharacters = true;
                }
                
            }
        } catch (IOException ex) {
            Error E = new Error();
            E.setVisible(true);
        
            Logger.getLogger(GameMaybe.class.getName()).log(Level.SEVERE, null, ex);
        }

        bbg.drawImage(img[0], 0, 0, null);
        
    }
    private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        BufferedImage dest = src.getSubimage(0, 0, rect.width, rect.height);
        return dest; 
    }
}
