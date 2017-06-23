/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamemaybe;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suleman
 */
public class Computer extends PlayerController{
    Computer(GameMaybe m){
        M = m;
        h = new InputHandler(M);
    }

    @Override
    public void run() {
        
            action(0,1,-1);
            count++;
            System.out.println(count);

        
    }
}
