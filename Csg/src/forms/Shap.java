package forms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import java.io.Serializable;

public class Shap implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int nextId = 1;
    private int id;
    private List<Rectangle> rectangles;
	
	public Shap() {
		id = nextId;
		nextId++;
		rectangles = new ArrayList<Rectangle>();
	}
	public void addRectangle(Rectangle rectangle) {
		
		rectangles.add(rectangle);
	}
	public void removeRectangle() {
		int size=rectangles.size();
		for (int i = size - 1; i >= size-8; i--) {
			rectangles.remove(rectangles.get(i));
		}
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
	
	public int isTouchInfoCorner(int x,int y,int nbrRectCreer,int last) {
		int realsize=rectangles.size() -1;
		for (int i = rectangles.size()-1; i >= 0; i--) {
			Rectangle rect = rectangles.get(i);
			if(rect.contains(x,y)) {
				
				if(i==realsize) {
					return 8;
				}
				if(i==realsize-1) {
					return 7;
				}
				if(i==realsize-2) {
					return 6;
				}
				if(i==realsize-3) {
					return 5;
				}
				if(i==realsize-4) {
					return 4;
				}
				if(i==realsize-5) {
					return 3;
				}
				if(i==realsize-6) {
					return 2;
				}
				if(i==realsize-7) {
					return 1;
				}
			}
		}
		return last;
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
	public void setco(int selection,int curseur_x,int curseur_y) {
		int size=rectangles.size();
		
		
		for (int i = size - 9; i >= 0; i--) {
			Rectangle rect=rectangles.get(i);
			
			switch(selection) {
			case 1:
				rect.setWidth(rectangles.get(i).getWidth()-curseur_x);
				rect.setHeight(rectangles.get(i).getHeight()-curseur_y);
				
				rect.setX(rectangles.get(i).getX()+curseur_x);
				rect.setY(rectangles.get(i).getY()+curseur_y);
				selection=1;
				break;
			case 2:
				
				rect.setHeight(rectangles.get(i).getHeight()-curseur_y);
				rect.setY(rectangles.get(i).getY()+curseur_y);
				selection=2;
				break;
			case 3:
				rect.setWidth(rectangles.get(i).getWidth()+curseur_x);
				rect.setHeight(rectangles.get(i).getHeight()-curseur_y);
				rect.setY(rectangles.get(i).getY()+curseur_y);
				selection=3;
				break;
			case 4:
				
				rect.setWidth(rectangles.get(i).getWidth()-curseur_x);
				rect.setHeight(rectangles.get(i).getHeight()+curseur_y);
				rect.setX(rectangles.get(i).getX()+curseur_x);
				selection=4;
				break;
			case 5:
				rect.setHeight(rectangles.get(i).getHeight()+curseur_y);
				selection=5;
				break;
			case 6:
				rect.setWidth(rectangles.get(i).getWidth()+curseur_x);
				rect.setHeight(rectangles.get(i).getHeight()+curseur_y);
				selection=6;
				break;
			case 7:
				
				rect.setWidth(rectangles.get(i).getWidth()-curseur_x);
				rect.setX(rectangles.get(i).getX()+curseur_x);

				selection=7;
				break;
			case 8:
				rect.setWidth(rectangles.get(i).getWidth()+curseur_x);
				selection=8;
				break;
			}
		}
	}
	
	public void draw(Graphics g) {
        for (Rectangle rectangle : rectangles) {
            g.setColor(Color.GREEN);
            g.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
    }
	private void dessinerPoints(Graphics g, int x1, int y1, int x2, int y2,JPanel rectPanel) {
	        //coin en haut à gauche
		
			g.setColor(Color.BLACK);
			g.fillRect(x1-5, y1-5, 10, 10);
			Rectangle rect1 = new Rectangle((x1-5),(y1-5),10,10);
			addRectangle(rect1);
	                
	        
	        g.fillRect((x1+x2)/2-5, y1-5, 10, 10);
	        Rectangle rect2 = new Rectangle((x1+x2)/2-5,(y1-5),10,10);
			addRectangle(rect2);
			
	        //coin en haut à droite
	        g.fillRect(x2-5, y1-5, 10, 10);
	        Rectangle rect3 = new Rectangle((x2-5),(y1-5),10,10);
			addRectangle(rect3);
	        //coin en bas à gauche
	        g.fillRect(x1-5, y2-5, 10, 10);
	        Rectangle rect4 = new Rectangle((x1-5),(y2-5),10,10);
			addRectangle(rect4);
	        //milieu en bas
	        g.fillRect((x1+x2)/2-5, y2-5, 10, 10);
	        Rectangle rect5 = new Rectangle((x1+x2)/2-5,(y2-5),10,10);
			addRectangle(rect5);
	        //coin en bas à droite
	        g.fillRect(x2-5, y2-5, 10, 10);
	        Rectangle rect6 = new Rectangle((x2-5),(y2-5),10,10);
			addRectangle(rect6);
	        //milieu gauche
	        g.fillRect(x1-5, (y1+y2)/2-5, 10, 10);
	        Rectangle rect7 = new Rectangle((x1-5),(y1+y2)/2-5,10,10);
			addRectangle(rect7);
	        //milieu droit
	        g.fillRect(x2-5, (y1+y2)/2-5, 10, 10);
	        Rectangle rect8 = new Rectangle((x2-5),(y1+y2)/2-5,10,10);
			addRectangle(rect8);
	}
	

	public void selectdraw(Graphics g,boolean status,JPanel rectPanel){
		
		List<Integer> listex = new LinkedList<Integer>(); 
		List<Integer> listey = new LinkedList<Integer>(); 
		for (Rectangle rect : rectangles) {
			listex.add(rect.getX());
			listex.add((rect.getX()+rect.getWidth()));
			listey.add(rect.getY());
			listey.add((rect.getY()+rect.getHeight()));
		}
			int x1=Collections.min(listex);
			int y1=Collections.min(listey);
			int x2=Collections.max(listex);
			int y2=Collections.max(listey);

			Graphics2D g2d = (Graphics2D) g;
			float[] dashPattern = {5, 5};
			BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f);
			g2d.setStroke(dashedStroke);
			
			g.drawLine(x1,y1,x2,y1);

			g.drawLine(x2,y1,x2,y2);

			g.drawLine(x2,y2,x1,y2);

			g.drawLine(x1,y2,x1,y1);
			dessinerPoints(g, x1, y1, x2, y2,rectPanel);
		
	}
}