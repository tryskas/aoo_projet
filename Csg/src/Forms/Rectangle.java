package Forms;

import java.io.Serializable;

public class Rectangle implements Serializable {

	private int x;
	private int y;
	private int width;
	private int height;
	
	public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void setX(int new_x) {
        this.x = new_x;
    }
    public void setY(int new_y) {
        this.y = new_y;
    }
    public void setWidth(int new_Width) {
        this.width = new_Width;
    }
    public void setHeight(int new_Height) {
        this.height = new_Height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public boolean contains(int x, int y) {
        
        //coordonnée du coin supérieur gauche
        int x0=this.x;
        int y0=this.y;
        
        // coordonnée du coin inférieur droite
        int x1= this.x + this.width;
        int y1= this.y + this.height;
        
        if (x>=x0 && x<=x1 && y>=y0 && y<=y1) return true;
        else return false;
    }
    
    public Rectangle intersection(Rectangle r) {
        int x1 = Math.max(this.x, r.x);
        int y1 = Math.max(this.y, r.y);
        int x2 = Math.min(this.x + this.width, r.x + r.width);
        int y2 = Math.min(this.y + this.height, r.y + r.height);

        int intersectionWidth = Math.max(0, x2 - x1);
        int intersectionHeight = Math.max(0, y2 - y1);

        return new Rectangle(x1, y1, intersectionWidth, intersectionHeight);
    }
}
