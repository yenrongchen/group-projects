import java.sql.SQLException;
import javax.swing.JFrame;

public class Tester {	
	
	public static void main(String[] args) throws SQLException {
		
		HomeFrame frame=new HomeFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}