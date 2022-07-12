
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JButton;

public class POSButton extends JButton {
	
	protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g.create();
        Color color1 = new Color(0, 0, 0, 100);
        Color color2 = new Color(0, 0, 0, 100);
        Rectangle r = g.getClipBounds();
        g2.setPaint(color2);
        g2.fillRect(0, getHeight()*4/5, getWidth(), getHeight()/5);
    }
}
