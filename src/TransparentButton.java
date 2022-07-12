

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;


public class TransparentButton extends JButton {
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g.create();
        Color color1 = new Color(3, 194, 146, 130).brighter().brighter();
        Color color2 = new Color(3, 194, 146, 130).brighter().brighter();
        Rectangle r = g.getClipBounds();
        g2.setPaint(new GradientPaint(r.x+20, r.y+200, color1, r.width, r.height-200, color2));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        
    }
}
