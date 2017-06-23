/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamemaybe;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author suleman
 */
public abstract class PlayerController{
    public enum moves{idle ,punch ,walk ,run , jump };
    public enum dir{left,right};
    public enum dir1{up,down};
    
    boolean interrupt=false;
    InputHandler h;
    GameMaybe M;
    character c;
    int count=0;
    BufferedImage b;
    String facing = "right";
    String facing1 = "up";
    int W,H;
    int dir=1;
    int ACT=0;
    boolean finish=false;
    abstract public void run();
    /*
    public void idle(int mirror){
        H =c.frames.get(""+0+" "+((count/actionslatency[0])%c.actionssizes[0])).getHeight();
        W =c.frames.get(""+0+" "+((count/c.actionslatency[0])%c.actionssizes[0])).getWidth();
        b.getGraphics().drawImage(c.frames.get(""+0+" "+((count/c.actionslatency[0])%c.actionssizes[0])), c.MapPosX, c.MapPosY,mirror*W,H, null);
        b.getGraphics().drawImage(c.shadframes.get(""+0+" "+((count/c.actionslatency[0])%c.actionssizes[0])), c.MapPosX, c.MapPosY+H+(H/2),mirror*W,-(H/2), null);
            
    }
    
    public void walk(int mirror){
        H =c.frames.get(""+2+" "+((count/c.actionslatency[2])%c.actionssizes[2])).getHeight();
        W =c.frames.get(""+2+" "+((count/c.actionslatency[2])%c.actionssizes[2])).getWidth();
        b.getGraphics().drawImage(c.frames.get(""+2+" "+((count/c.actionslatency[2])%c.actionssizes[2])), c.MapPosX, c.MapPosY,mirror*W,H, null);
        b.getGraphics().drawImage(c.shadframes.get(""+2+" "+((count/c.actionslatency[2])%c.actionssizes[2])), c.MapPosX, c.MapPosY+H+(H/2),mirror*W,-(H/2), null);
            
    }
    
    public void punch(int mirror){
        H =c.frames.get(""+1+" "+((count/c.actionslatency[1])%c.actionssizes[1])).getHeight();
        W =c.frames.get(""+1+" "+((count/c.actionslatency[1])%c.actionssizes[1])).getWidth();
        b.getGraphics().drawImage(c.frames.get(""+1+" "+((count/c.actionslatency[1])%c.actionssizes[1])),  c.MapPosX, c.MapPosY,mirror*W,H, null);
        b.getGraphics().drawImage(c.shadframes.get(""+1+" "+((count/c.actionslatency[1])%c.actionssizes[1])), c.MapPosX, c.MapPosY+H+(H/2),mirror*W,-(H/2), null);
            
    }
    */
    int disp=0;
    int counter=0;
    public void action(int act,int mirrorx,int mirrory){
        ACT=act;
        try{
            //interrupt = c.actionsinterrupt.get(act);
            System.out.println(""+act+" "+counter);
            if(count >= c.actionslatency.get(""+act+" "+counter)){
                counter++;
                count=0;
            }
            if(counter >= c.actionssizes.get(act)){
                //interrupt = true;
                finish=true;
                counter=0;
            }

            H =c.frames.get(""+act+" "+(counter)).getHeight();
            W =c.frames.get(""+act+" "+(counter)).getWidth();
            c.MapPosX=c.MapPosX+mirrorx*c.dispx.get(""+act+" "+(counter));
            c.abovegrd=c.abovegrd+c.dispy.get(""+act+" "+(counter));
            if(dir == -1){
                disp=(int) (W*c.imageresize);
            }
            //if(/*(*/(count/c.actionslatency.get(""+act+" "+(count%c.actionssizes.get(act))))/*%c.actionssizes.get(act))*/>=c.actionssizes.get(act)){

            //}
            b.getGraphics().drawImage(c.frames.get(""+act+" "+(counter)), c.MapPosX+disp, c.MapPosY+c.abovegrd,(int) (dir*W*c.imageresize),(int) (H*c.imageresize), null);
            b.getGraphics().drawImage(c.shadframes.get(""+act+" "+(counter)), c.MapPosX+disp, c.MapPosY+(int) ((H*c.imageresize)+((H/2)*c.imageresize)),(int) (dir*W*c.imageresize),(int) (-(H/2)*c.imageresize), null);
            disp=0;
        }catch(NullPointerException e){
            counter = 0;
        }
    }
    
}
