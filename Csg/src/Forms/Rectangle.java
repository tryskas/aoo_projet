package Forms;

public class Rectangle extends Forms {

	private int[] coordonate = {0, 0, 0, 0};
	
	public Rectangle() {}
	public Rectangle(int x1, int y1, int x2, int y2) {
		if (x1 < x2) {
			coordonate[0]=x1;
			coordonate[2]=x2;
		}
		else {
			coordonate[0]=x2;
			coordonate[2]=x1;
		}
		if (y1 < y2) {
			coordonate[1]=y1;
			coordonate[3]=y2;
		}
		else {
			coordonate[1]=y2;
			coordonate[3]=y1;
		}
	}
	public void move(int dx, int dy) {}
	public void resize (int kx, int ky) {}
	public void detail() {}
	public int[] get_Coordonate(){
		return coordonate;
	}
	
	public boolean is_touch_by_click(int x, int y){
		System.err.println("x1="+coordonate[0]+ " y1="+coordonate[1]+" x2="+coordonate[2]+" y2="+coordonate[3]);
		if (this.coordonate[0]<=x && x<=this.coordonate[2] && this.coordonate[1]<=y && y<=this.coordonate[3]) return true;
		else return false;
	}
}
