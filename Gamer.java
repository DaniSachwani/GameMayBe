/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamemaybe;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suleman
 */
public class Gamer extends PlayerController{

    Gamer(GameMaybe m) {
        M = m;
        h = new InputHandler(M);
    }
    int mirrx=0,mirry=0;
    int curr_act= 0;

    boolean enter=false;
    public void run() {
        
        HashMap<String,Boolean> inter =c.availactions.get(""+super.ACT+" "+counter);
        System.out.println("ACT,Counter = "+ACT+","+counter);
        
        if(inter.get("J")){
            if(h.isKeyDown(KeyEvent.VK_S)){
                if(c.incoming.get(4).contains(curr_act+"")){
                    super.finish=false;
                    curr_act=4;/*jump*/
                    counter=0;
                }
            }
        }
        if(inter.get("A")){
            if(h.isKeyDown(KeyEvent.VK_A)){
                if(c.incoming.get(1).contains(curr_act+"")){
                    super.finish=false;
                    curr_act=1;/*attack*/
                    counter=0;
                }
            }
        }
        if(inter.get("R")){
            if(h.isKeyDown(KeyEvent.VK_F)){
                if(c.incoming.get(3).contains(curr_act+"")){
                    super.finish=false;
                    curr_act=3;/*run*/
                    counter=0;
                }
                if (super.dir==-1){
                    facing="left";
                }
                if (super.dir==1){
                    facing="right";
                }
            }
        }
        if(inter.get("right")){
            if(h.isKeyDown(KeyEvent.VK_RIGHT)){
                if(c.incoming.get(2).contains(curr_act+"")){
                    super.finish=false;
                    curr_act=2;/*walk*/
                }
                facing="right";
            } 
        }
        if(inter.get("left")){
            if(h.isKeyDown(KeyEvent.VK_LEFT)){
                if(c.incoming.get(2).contains(curr_act+"")){
                    super.finish=false;
                    curr_act=2;/*walk*/
                }
                facing="left";
            }
        }
        if(inter.get("down")){
            if(h.isKeyDown(KeyEvent.VK_DOWN)){
                if(c.incoming.get(2).contains(curr_act+"")){
                    super.finish=false;
                    curr_act=2;/*walk*/
                }
                facing1="down";
            } 
        }
        if(inter.get("up")){
            if(h.isKeyDown(KeyEvent.VK_UP)){
                if(c.incoming.get(2).contains(curr_act+"")){
                    super.finish=false;
                    curr_act=2;/*walk*/
                }
                facing1="up";
            }
        }
       
        
            

            if(facing.equals("right"))
                mirrx=1;
            if(facing.equals("left"))
                mirrx=-1;
            if(facing1.equals("up"))
                mirry=-1;
            if(facing1.equals("down"))
                mirry=1;
                        
            
            
            if(curr_act == 1){
                action(1,mirrx,mirry);
            }
            else if(curr_act == 2){
                c.MapPosX+=mirrx*c.walking_speed;
                c.MapPosY+=mirry*c.walking_speed;
                action(2,mirrx,mirry);
            }
            else if(curr_act == 3){
                c.MapPosX+=mirrx*c.running_speed;
                c.MapPosY+=mirry*c.running_speed;
                action(3,mirrx,mirry);
            }
            else if(curr_act == 4){
                c.MapPosX+=mirrx*c.walking_speed;
                action(4,mirrx,mirry);
            }
            else{
                action(0,mirrx,mirry);
            }
               
            if(mirrx!=0)
                super.dir=mirrx;
            
            
            facing="";
            facing1="";
            
            
            count++;
            System.out.println(count);
            mirrx=0;
            mirry=0;
            
            boolean active=false;
            
            for(int l=0;l<h.keys.length;l++){
                active |= h.keys[l];
            }
            if(!active && finish){
                curr_act=0;
            }
            if(!active)
            for(int l=0;l<h.keys.length;l++){
                h.keys[l]=false;
            }
    }
    
}
