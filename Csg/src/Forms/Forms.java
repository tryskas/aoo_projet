package Forms;
import java.util.List;
import java.util.ArrayList;

public class Forms {

	private List<Forms> listeForm; 
	
	public Forms(){
		listeForm = new ArrayList();
	}
	
	public void union(Rectangle r1, Rectangle r2) {
		//create a Form composite by two rectangle
	}
	public void diff(Rectangle r1, Rectangle r2) {
		//create a Form composite by two rectangle
	}
	
	public static void main(String[] args) {
		System.out.println("salut");
		
		Forms r_list = new Forms();
		
		
		Rectangle r1 = new Rectangle(0, 0, 10, 10);
		if (r1.is_touch_by_click(5, 5)) {
			System.out.println("5 5 is inside");
		}
		else {
			System.out.println("5 5 NOP");
		}
		
	}

}
