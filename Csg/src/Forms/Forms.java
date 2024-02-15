package Forms;

public class Forms {
	
	public void union(Rectangle r1, Rectangle r2) {}
	public void diff(Rectangle r1, Rectangle r2) {}
	
	public static void main(String[] args) {
		System.out.println("salut");
		
		Rectangle r1 = new Rectangle(0, 0, 10, 10);
		if (r1.is_touch_by_click(5, 5)) {
			System.out.println("5 5 is inside");
		}
		else {
			System.out.println("5 5 NOP");
		}
		
	}

}
