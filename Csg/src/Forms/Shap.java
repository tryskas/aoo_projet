package Forms;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import java.io.Serializable;

public class Shap implements Serializable {
	private static int nextId = 1;
    private int id;
    private boolean pointdessiner=false; 
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
	public void removeRectangle() {
		int size=rectangles.size();
		for (int i = size - 1; i >= size-8; i--) {
			//System.out.println("de "+rectangles.size()+"à"+(rectangles.size()-8));
			//System.out.println(i);
			rectangles.remove(rectangles.get(i));
		}
		pointdessiner=false;
		//System.out.println("fin de remove");
	}
	
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
	
	public int isTouchInfoCorner(int x,int y) {
		List<Integer> listex = new LinkedList<Integer>(); 
		List<Integer> listey = new LinkedList<Integer>(); 
		for (int i = rectangles.size() - 1; i >= 0; i--) {
			Rectangle rect = rectangles.get(i);
			
			listex.add(rect.getX());
			listex.add((rect.getX()+rect.getWidth()));
			listey.add(rect.getY());
			listey.add((rect.getY()+rect.getHeight()));
		
			Graph.ajouterTexte("Le maximum est: "+ Collections.max(listex)+" et "+ Collections.max(listey));
			Graph.ajouterTexte("Le minimum est: "+ Collections.min(listex)+" et "+ Collections.min(listey));
			int x1=Collections.min(listex);
			int y1=Collections.min(listey);
			int x2=Collections.max(listex);
			int y2=Collections.max(listey);
			
			if (x >= x1 - 5 && x <= x1 + 5 && y >= y1 - 5 && y <= y1 + 5) {
				System.out.println("1");
				return 1;
				
			}
			if (x >= ((x1+x2)/2) - 5 && x <= ((x1+x2)/2) + 5 && y >= y1 - 5 && y <= y1 + 5) {
				System.out.println("2");
				return 2;
				
			}
			if (x >= x2 - 5 && x <= x2 + 5 && y >= y1 - 5 && y <= y1 + 5) {
				System.out.println("3");
				return 3;
				
			}
			if (x >= x1 - 5 && x <= x1 + 5 && y >= y2 - 5 && y <= y2 + 5) {
				System.out.println("4");
				return 4;
				
			}
			if (x >= (x1+x2)/2 - 5 && x <= (x1+x2)/2 + 5 && y >= y2 - 5 && y <= y2 + 5) {
				System.out.println("5");
				return 5;
				
			}
			if (x >= x2 - 5 && x <= x2 + 5 && y >= y2 - 5 && y <= y2 + 5) {
				System.out.println("6");
				return 6;
				
			}
			if (x >= x1 - 5 && x <= x1 + 5 && y >= (y1+y2)/2 - 5 && y <= (y1+y2)/2 + 5) {
				System.out.println("7");
				return 7;
				
			}
			if (x >= x2 - 5 && x <= x2 + 5 && y >= (y1+y2)/2 - 5 && y <= (y1+y2)/2 + 5) {
				System.out.println("8");
				return 8;
				
			}
		}
		return 0;
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
	public void setco(int deltaX,int deltaY,boolean deltaXb,boolean deltaYb,boolean deltaWb,boolean deltaHb) {
		int size=rectangles.size();
		
		
		for (int i = size - 9; i >= 0; i--) {
			System.out.println(" Bool : "+deltaXb +" "+deltaYb+" "+deltaWb+" "+deltaHb);
			int dx=0,dy=0,dw = 0,dh =0;
			int deltaW=deltaY+rectangles.get(i).getWidth();
			int deltaH=deltaX+rectangles.get(i).getHeight();
			if(deltaX<0) {dx=-2;}
	        else {dx=2;}
			if(deltaY<0) {dy=-2;}
	        else {dy=2;}	
			if(deltaW>0) {dw=-2;}
	        else {dw=2;}
			if(deltaH<0) {dh=-2;}
	        else {dh=2;}
			
			//confusion dans la base donc y <=>x W<>H
            if(deltaYb) {
            	System.out.println("X var");
            	rectangles.get(i).setX(rectangles.get(i).getX()+dx);}
            if(deltaXb) {
            	System.out.println("Y var");
            	rectangles.get(i).setY(rectangles.get(i).getY()+dy);}
            if(deltaWb) {
            	System.out.println("W var");
            	rectangles.get(i).setWidth(rectangles.get(i).getWidth()+dw);
            	}
            if(deltaHb) {
            	System.out.println("H var");
            	rectangles.get(i).setHeight(rectangles.get(i).getHeight()+dh);
            	}
			
		}
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
	private boolean dessinerPoints = false;
	public void setDessinerPoints(boolean dessiner) {

	    this.dessinerPoints = dessiner;
	}
	private void dessinerPoints(Graphics g, int x1, int y1, int x2, int y2,JPanel rectPanel) {
	        //coin en haut à gauche
		if(pointdessiner==false) {
			
			g.fillRect(x1-5, y1-5, 10, 10);
			Rectangle rect1 = new Rectangle((x1-5),(y1-5),10,10);
			addRectangle(rect1);
	                
	        
	        g.fillRect((x1+x2)/2-5, y1-5, 10, 10);
	        Rectangle rect2 = new Rectangle((x1+x2)/2,(y1-5),10,10);
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
	        Rectangle rect5 = new Rectangle((x1+x2)/2,(y2-5),10,10);
			addRectangle(rect5);
	        //coin en bas à droite
	        g.fillRect(x2-5, y2-5, 10, 10);
	        Rectangle rect6 = new Rectangle((x2-5),(y2-5),10,10);
			addRectangle(rect6);
	        //milieu gauche
	        g.fillRect(x1-5, (y1+y2)/2-5, 10, 10);
	        Rectangle rect7 = new Rectangle((x1-5),(y1+y2)/2,10,10);
			addRectangle(rect7);
	        //milieu droit
	        g.fillRect(x2-5, (y1+y2)/2-5, 10, 10);
	        Rectangle rect8 = new Rectangle((x2-5),(y1+y2)/2,10,10);
			addRectangle(rect8);
	        pointdessiner=true;
		}
	        
	    
	}
	
private void removePoints(Graphics g, int x1, int y1, int x2, int y2,JPanel rectPanel,Rectangle rect1) {
	if(pointdessiner) {
		rectangles.remove(rect1);
		
	}
}
	public void selectdraw(Graphics g,boolean status,JPanel rectPanel){
		
		List<Integer> listex = new LinkedList<Integer>(); 
		List<Integer> listey = new LinkedList<Integer>(); 
		for (Rectangle rect : rectangles) {
			System.err.println("select draw :");
			//System.err.println("X1 = " + rect.getX() + " Y1 = " + rect.getY() + " X2 = " + (rect.getX()+rect.getWidth()) + " Y2 = " + (rect.getY()+rect.getHeight()) );
			listex.add(rect.getX());
			listex.add((rect.getX()+rect.getWidth()));
			listey.add(rect.getY());
			listey.add((rect.getY()+rect.getHeight()));
		}
			Graph.ajouterTexte("Le maximum est: "+ Collections.max(listex)+" et "+ Collections.max(listey));
			Graph.ajouterTexte("Le minimum est: "+ Collections.min(listex)+" et "+ Collections.min(listey));
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
			
			
		
		if(status){
			
			//System.out.println("dessiner");
			dessinerPoints(g, x1, y1, x2, y2,rectPanel);
        } else {
            System.out.println("effacer");
            g.setColor(Color.WHITE); 
            g.fillRect(50, 50, 50, 50);
        }
		
	}
}