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
		for (int i = rectangles.size() - 1; i >= rectangles.size()-7; i--) {
			rectangles.remove(rectangles.get(i));
		}
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
			Rectangle rect1 = new Rectangle((x1-5),(y1-5),10,10);
			addRectangle(rect1);
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