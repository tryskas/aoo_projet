package Forms;

public class Rectangle {

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
        int w = this.width;
        int h = this.height;
        
        //coordonnée du coin supérieur gauche
        int x0=this.x;
        int y0=this.y;
        
        // coordonnée du coin inférieur droite
        int x1= this.x + this.width;
        int y1= this.y + this.height;
        
        if (x>=x0 && x<=x1 && y>=y0 && y<=y1) return true;
        else return false;
    }
}
