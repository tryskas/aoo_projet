package Forms;
import java.io.Serializable;

public class Cercle implements Serializable {
	private int x, y, r;
	
	public Cercle(int x0, int y0, int r0) {
		this.x = x0;
		this.y = y0;
		this.r = r0;
	}
	
	public void setX(int new_x) {
        this.x = new_x;
    }
    public void setY(int new_y) {
        this.y = new_y;
    }
    public void setR(int new_r) {
        this.r = new_r;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getR() {
        return this.r;
    }
    
    public boolean contains(int x0, int y0) {
    	int rayonTest = (int) Math.sqrt(Math.pow(Math.abs(x0-this.x), 2) + Math.pow(Math.abs(y0-this.y), 2));
    	if (rayonTest <= this.r) return true;
        return false;
    }
}
