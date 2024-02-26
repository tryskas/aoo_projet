package Forms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Shap {
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
	        rectangle.ChangeX(rectangle.getX() + deltaX);
	        rectangle.ChangeY(rectangle.getY() + deltaY);
	    }
	}
	
	public void draw(Graphics g) {
        for (Rectangle rectangle : rectangles) {
            g.setColor(Color.GREEN);
            g.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
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
