/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import cosc202.andie.EditActions.*;
import cosc202.andie.FileActions.*;
import cosc202.andie.TransformActions.RotateAntiClockwise;
import cosc202.andie.TransformActions.RotateClockwise;
import cosc202.andie.ViewActions.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author jamet966
 */
public class KeyboardShortcuts implements KeyListener {
    
    public static boolean isCtrlPressed = false;
    public static boolean isShiftPressed = true;
    
    ImagePanel image;
    
    public KeyboardShortcuts(ImagePanel img) {
    
        this.image = img;
    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
                
            case 17: // CTRL
                    
                isCtrlPressed = true;
                break;
                
            case 16:
                    
                isShiftPressed = true;
                break;
                    
            

        }
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        switch (e.getKeyCode()) {
            
            case 17:
                
                isCtrlPressed = false;
                break;
                
            case 16:
                
                isShiftPressed = false;
                break;
                
            case 83: // S

                if(isCtrlPressed && isShiftPressed) {
                    
                    FileSaveAsAction.saveAs();
                    
                } else if(isCtrlPressed) {
                    
                    FileSaveAction.save();
                    
                }
                break;
                
            case 69: // E
                
                if(isCtrlPressed) {
                    
                    FileExportAction.export();
                }
                break;
                
            case 79:
                
                if(isCtrlPressed) {
                    
                    FileOpenAction.open();
                    
                }
                break;
                
            case 61: // PLUS SIGN
                
                if(isCtrlPressed) {

                    ZoomInAction.zoomIn();
                    
                }
                break;
                
            case 45: // MINUS SIGN
                
                if(isCtrlPressed) {
                    
                    
                    ZoomOutAction.zoomOut();
                }
                break;
                
            case 81: // Q
                
                if(isCtrlPressed) {
                    
                    System.exit(0);
                }
                break;
                
            case 89: // Y
                
                if(isCtrlPressed) {
                    
                    RedoAction.redo();
                }
                break;
                
            case 90: // Z
                
                if(isCtrlPressed) {
                    
                    UndoAction.undo();
                }
                break;
            
            case KeyEvent.VK_RIGHT: // RIGHT ARROW

                RotateClockwise.rotateClockwise();
                break;
                
            case KeyEvent.VK_LEFT: // LEFT ARROW
                
                RotateAntiClockwise.rotateAntiClockwise();
                break;
            
        }
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }
    
}
