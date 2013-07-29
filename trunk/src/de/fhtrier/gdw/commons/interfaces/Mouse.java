package de.fhtrier.gdw.commons.interfaces;

public interface Mouse {
    
    public void mouseMoved(int oldx, int oldy, int newx, int newy);

    public void mouseDragged(int oldx, int oldy, int newx, int newy); 

    public void mouseReleased(int button, int x, int y);

    public void mousePressed(int button, int x, int y);
    
    public void mouseWheelMoved(int newValue);

}
