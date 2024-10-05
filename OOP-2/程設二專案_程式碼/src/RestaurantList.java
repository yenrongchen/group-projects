import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.sql.*;

public class RestaurantList {
	public static void main(String[] args) {
		ArrayList <Restaurant> restaurants = new ArrayList <Restaurant>();
		ArrayList <String> dt = new ArrayList<String>();
		
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "mongroup7";
		String url = server + database + "?useSSL=false";
		String username = "mongroup7";
		String password = "qug6740";
		
		
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			System.out.println("DB Connectd");
			Statement stat = conn.createStatement();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet result;
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			System.out.println("DB Connectd");
			Statement stat = conn.createStatement();
			String query = "SELECT * FROM RestaurantsList;";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
			result = stat.getResultSet(); 
			showResultSet(result, restaurants);
			result.close();
			}
		}
		catch(SQLException e) {
			
		}
		String rName; 
		String rPrice; 
		String rGate; 
		String rCuisine;
		String rvegan;
		String rTakeOut;
		String rSeats;
		String openTime;
		String telefon;
		String address;
		
	
		

		Page1 frame = new Page1(restaurants);   
	}
	
	public static void showResultSet(ResultSet result, ArrayList restaurants) throws SQLException { 
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
		}
		System.out.println();
		while (result.next()) {
			ArrayList <String> dt = new ArrayList<String>();
			for (int i = 1; i <= columnCount; i++) {
				dt.add(result.getString(i));
			}

			Restaurant r = new Restaurant(dt.get(1), dt.get(2), dt.get(3), dt.get(4), dt.get(5), dt.get(6), dt.get(7), dt.get(8), dt.get(9), dt.get(10));
			restaurants.add(r);
			System.out.println(); 
		}
	}
	
	public static void getData(ResultSet result, ArrayList restaurants) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			System.out.printf("%15s", metaData.getColumnLabel(i)); 
		}
		while (result.next()) {
			ArrayList <String> dt = new ArrayList<String>();
			for (int i = 1; i <= columnCount; i++) {
				System.out.printf("%15s", result.getString(i));
				dt.add(result.getString(i));
				
			}
			System.out.println(); 
		}
	}
}