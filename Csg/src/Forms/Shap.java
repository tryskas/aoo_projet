package Forms;

import java.awt.*;
import java.util.ArrayList;
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
	    for (Rectangle rectangle : rectangles) {
	        if (rectangle.contains(x, y)) return true;
	    }
	    return false;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void draw(Graphics g) {
        for (Rectangle rectangle : rectangles) {
            g.setColor(Color.GREEN);
            g.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
    }
}
