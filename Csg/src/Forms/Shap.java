package Forms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import java.io.Serializable;

public class Shap implements Serializable {
	private static int nextId = 1;
    private int id;
	private List<Rectangle> rectangles;
	
	public Shap() {
		id = nextId;
		nextId++;
		rectangles = new ArrayList();
	}
	public void addRectangle(Rectangle rectangle) {
		rectangles.add(rectangle);
	}
	
	public List<Rectangle> getRectangles() {
        return rectangles;
    }
	
	public boolean isTouch(int x, int y) {
		for (int i = rectangles.size() - 1; i >= 0; i--) {
			Rectangle rectangle = rectangles.get(i);
	        if (rectangle.contains(x, y)) return true;
	    }
	    return false;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void move(int deltaX, int deltaY) {
	    for (Rectangle rectangle : rectangles) {
	        rectangle.setX(rectangle.getX() + deltaX);
	        rectangle.setY(rectangle.getY() + deltaY);
	    }
	}
	
	public void draw(Graphics g) {
        for (Rectangle rectangle : rectangles) {
            g.setColor(Color.GREEN);
            g.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
    }
	private boolean dessinerPoints = false;
	public void setDessinerPoints(boolean dessiner) {

	    this.dessinerPoints = dessiner;
	}
	private void dessinerPoints(Graphics g, int x1, int y1, int x2, int y2) {
	    if (dessinerPoints) {
	    	System.out.println("créer...");
	    	Shape ancienneForme = g.getClip();
	        //coin en haut à gauche
	        g.fillRect(x1-5, y1-5, 10, 10);

	        //milieu en haut
	        g.fillRect((x1+x2)/2-5, y1-5, 10, 10);

	        //coin en haut à droite
	        g.fillRect(x2-5, y1-5, 10, 10);

	        //coin en bas à gauche
	        g.fillRect(x1-5, y2-5, 10, 10);

	        //milieu en bas
	        g.fillRect((x1+x2)/2-5, y2-5, 10, 10);

	        //coin en bas à droite
	        g.fillRect(x2-5, y2-5, 10, 10);

	        //milieu gauche
	        g.fillRect(x1-5, (y1+y2)/2-5, 10, 10);

	        //milieu droit
	        g.fillRect(x2-5, (y1+y2)/2-5, 10, 10);
	    }else {
	    	System.out.println("effacer...");
	    	
	    	
            
	    }
	    
	}
	
	public void resetdraw(Graphics g) {
		for (Rectangle forme : rectangles) {
            
        }
	}
	public void selectdraw(Graphics g,boolean status){
		if(status){
			System.out.println("dessiner");
            g.drawRect(50, 50, 50, 50); 
        } else {
            System.out.println("effacer");
            g.setColor(Color.WHITE); 
            g.fillRect(50, 50, 50, 50);
        }
	}
}
