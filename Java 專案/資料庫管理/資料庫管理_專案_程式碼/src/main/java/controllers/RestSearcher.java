package controllers;

import entities.Restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;

public class RestSearcher {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db_project?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "000000";
	
	private Connection conn;
	
	public RestSearcher() {
		mysql_connect();
	}
	
	public void mysql_connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find driver");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String searchByName(String input) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT Name "
														 + "FROM `Restaurant` "
														 + "WHERE Name = ?");
			stat.setString(1, input);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getString("Name");
			}else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getIdByName(String name) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT RestID"
														 + " FROM Restaurant"
														 + " WHERE Name=?");
			stat.setString(1, name);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString("RestID");
				return id;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Restaurant getRestaurant(String restID) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * "
														 + "FROM Restaurant "
														 + "WHERE RestID=?");
			stat.setString(1, restID);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				Restaurant r = new Restaurant(restID, rs.getString("Name"), rs.getString("Address"), rs.getString("Phone"), rs.getString("Business_hour"), rs.getString("Closed"), rs.getString("Vegan"));
				return r;
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getNameById(String id) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * "
														 + "FROM Restaurant "
														 + "WHERE RestID=?");
			stat.setString(1, id);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getString("Name");
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<String> getSource(String rid){
		try {
			ArrayList<String> result = new ArrayList<String>();
			PreparedStatement stat = conn.prepareStatement("SELECT Source "
														 + "FROM menupicture "
														 + "WHERE RestID = ?");
			stat.setString(1, rid);
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("Source"));
			}
			
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, String> getMenu(String rid){
		try {
			HashMap<String, String> menu = new HashMap<String, String>();
			PreparedStatement stat = conn.prepareStatement("SELECT * "
														 + "FROM menu "
														 + "WHERE RestID = ?");
			stat.setString(1, rid);
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				menu.put(rs.getString("DishName"), rs.getString("Price"));
			}
			return menu;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, String> getComment(String rid){
		try {
			HashMap<String, String> comment = new HashMap<String, String>();
			PreparedStatement stat = conn.prepareStatement("SELECT * "
														 + "FROM review "
														 + "WHERE RestID = ? "
														 + "ORDER BY Time DESC");
			stat.setString(1, rid);
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				comment.put(rs.getString("Comment"), rs.getString("Stars"));
			}
			return comment;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void close(){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
