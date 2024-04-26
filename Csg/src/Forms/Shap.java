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
	// private List<Cercle> cercles; FOR CERCLE
	
	public Shap() {
		id = nextId;
		nextId++;
		rectangles = new ArrayList<Rectangle>();
		// cercles = new ArrayList<Cercle>(); FOR CERCLE
	}
	public void addRectangle(Rectangle rectangle) {
		rectangles.add(rectangle);
	}
	/* FOR CERCLE
	public void addCercle(Cercle cercle) {
		cercles.add(cercle);
	}
	*/
	
	public List<Rectangle> getRectangles() {
        return rectangles;
    }
	/* FOR CERCLE
	public List<Cercle> getCercles(){
		return this.cercles;
	}
	*/
	
	public boolean isTouch(int x, int y) {
		for (int i = rectangles.size() - 1; i >= 0; i--) {
			Rectangle rectangle = rectangles.get(i);
	        if (rectangle.contains(x, y)) return true;
	    }
		/* FOR CERCLE
		for (int i = cercles.size() - 1; i >= 0; i--) {
			Cercle cercle = cercles.get(i);
	        if (cercle.contains(x, y)) return true;
	    }
	    */
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
	    /* FOR CERCLE
	    for (Cercle cercle : cercles) {
	    	cercle.setX(cercle.getX() + deltaX);
	        cercle.setY(cercle.getY() + deltaY);
	    }
	    */
	}
	
	public void draw(Graphics g) {
        for (Rectangle rectangle : rectangles) {
            g.setColor(Color.GREEN);
            g.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
        /* FOR CERCLE
        for (Cercle cercle : cercles){
        	g.setColor(Color.red);
        	g.fillOval(cercle.getX()-cercle.getR(), cercle.getY()-cercle.getR(), cercle.getR()*2, cercle.getR()*2);
        }
        */
    }
	
	public void selectdraw(Graphics g){
		for (Rectangle rect : rectangles) {
			System.err.println("select draw :");
			//System.err.println("X1 = " + rect.getX() + " Y1 = " + rect.getY() + " X2 = " + (rect.getX()+rect.getWidth()) + " Y2 = " + (rect.getY()+rect.getHeight()) );
			List<Integer> listex = new LinkedList<Integer>(); 
			List<Integer> listey = new LinkedList<Integer>(); 
			listex.add(rect.getX());
			listex.add((rect.getX()+rect.getWidth()));
			listey.add(rect.getY());
			listey.add((rect.getY()+rect.getHeight()));


			System.out.println("Le maximum est: "+ Collections.max(listex)+" et "+ Collections.max(listey)); 
			System.out.println("Le minimum est: "+ Collections.min(listex)+" et "+ Collections.min(listey)); 

			int x1=Collections.min(listex);
			int y1=Collections.min(listey);
			int x2=Collections.max(listex);
			int y2=Collections.max(listey);

			g.drawLine(x1,y1,x2,y1);

			g.drawLine(x2,y1,x2,y2);

			g.drawLine(x2,y2,x1,y2);

			g.drawLine(x1,y2,x1,y1);
		}
	}
}
