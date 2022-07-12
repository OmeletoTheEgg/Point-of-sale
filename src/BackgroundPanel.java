

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import javax.swing.JPanel;
import java.util.Vector;


public class BackgroundPanel extends JPanel {
	public BackgroundPanel() {
	}
	int dotCount = 120;
	
	int w=0;
	int h=0;
	int alpha = 0;
	int alphalim = 80;
	int mouseX = 0;
	int mouseY = 0;
	
	Vector<Float> x = new Vector<>();
    Vector<Float> y = new Vector<>();
    Vector<Float> speedX = new Vector<>();
    Vector<Float> speedY = new Vector<>();
    
	
    protected void paintComponent(Graphics g) {
    	if (x.isEmpty()) {
    		reset();
    		x.add(0f);
    		y.add(0f);
    	}
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        //Gradient Background
        w = getWidth();
        h = getHeight();
        Color color1 = new Color(3, 147, 194);
        Color color2 = new Color(3, 194, 146);
        GradientPaint gp = new GradientPaint(0, 0, color1, 180, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        
       
    	for (int i = 0; i <= dotCount; i++) {
    		for (int j = i; j <= dotCount; j++) {
    			double dist = (Math.sqrt(Math.pow((double)y.elementAt(j+1)+2.5f - (double)y.elementAt(i)+2.5f, 2)
						+ Math.pow((double)x.elementAt(j+1)+2.5f - (double)x.elementAt(i)+2.5f, 2)));
    			if (dist >= alphalim) {
    				alpha = 0;
    			} else if (dist >= 0 && dist < alphalim) {				
    				alpha = (int) (-((dist / alphalim) * 255)+255);
    			} else {
    				alpha = 255;
    			}
    			g2d.setColor(new Color(3, 194, 146, alpha).brighter());
    			g2d.setStroke(new BasicStroke(2));
    			if (dist >= 0 && dist < alphalim) {
    				g2d.drawLine(Math.round(x.elementAt(i)+2.5f), Math.round(y.elementAt(i)+2.5f), 
            				Math.round(x.elementAt(j+1)+2.5f), Math.round(y.elementAt(j+1)+2.5f));
    			}
    			
    		}
    		g2d.setColor(new Color(3, 194, 146, 150).brighter());
    		if (i!=0) g2d.fillOval(Math.round(x.elementAt(i)), Math.round(y.elementAt(i)), 5, 5);
    		
    		x.set(i, x.elementAt(i)+speedX.elementAt(i));
    		y.set(i, y.elementAt(i)+speedY.elementAt(i));
    		
    		if (x.elementAt(i) < -80) {
    			x.set(i, (float)w);
    		} else if (x.elementAt(i) > w+80) {
    			x.set(i, (float)0);
    		}
    		if (y.elementAt(i) < -80) {
    			y.set(i, (float)h);
    		} else if (y.elementAt(i) > h+80) {
    			y.set(i, (float)0);
    		}

    		x.set(0, (float) mouseX);
    		y.set(0, (float) mouseY);
        }
    	
    	try {
    		//Thread.sleep(1);
    	} catch (Exception e) {}
    	
    	repaint();
    }
    
    public void reset() {
    	w = getWidth();
        h = getHeight();
    	if (!x.isEmpty()) x.clear();
    	if (!y.isEmpty()) y.clear();
    	if (!speedX.isEmpty()) speedX.clear();
    	if (!speedY.isEmpty()) speedY.clear();
    	for (int i = 0; i <= dotCount; i++) {
        	x.add(((float)Math.random() * (float)w));
        	y.add(((float)Math.random() * (float)h));
        	speedX.add((-0.04f) + (float)Math.random() * (0.04f - (-0.04f)));
        	speedY.add((-0.04f) + (float)Math.random() * (0.04f - (-0.04f)));
        	
        }
    	
    }
    
}