

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;


public class TransparentPane extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Rectangle r = g.getClipBounds();
        
        Color color1 = new Color(3, 194, 146, 130).brighter().brighter();
        Color color2 = new Color(3, 194, 146, 0).brighter();
        
        GradientPaint gp = new GradientPaint(r.x+20, r.y+200, color1, r.width, r.height-200, color2);
        g2d.setPaint(gp);
        
        g.fillRect(r.x, r.y, r.width, r.height);
    }
}
