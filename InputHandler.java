package gamemaybe;

import java.awt.Component;
import java.awt.event.*;
 
public class InputHandler implements KeyListener,MouseListener {
    boolean keys[] = new boolean[256];
    boolean mouseclicked=false;
    public InputHandler(Component c) {
            c.addKeyListener(this);
            c.addMouseListener(this);
    }

    public boolean isKeyDown(int keyCode) {
            if (keyCode > 0 && keyCode < 256) {
                    return keys[keyCode];
            }

            return false;
    }

    public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
                    keys[e.getKeyCode()] = true;
            }
    }

    public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
                    keys[e.getKeyCode()] = false;
            }
    }

    public void keyTyped(KeyEvent e) {
    }



    public void mouseClicked(MouseEvent e) {
        mouseclicked =true;
    }


    public void mouseReleased(MouseEvent e) {
        mouseclicked =false;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        mouseclicked =false;
    }

    public void mousePressed(MouseEvent e) {
        mouseclicked =true;
    }
}
 