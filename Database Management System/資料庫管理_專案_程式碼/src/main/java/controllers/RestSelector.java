package controllers;

import entities.Restaurant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class RestSelector {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db_project?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "000000";
	
	private ArrayList<String> budget;
	private ArrayList<String> time;
	private ArrayList<String> type;
	private ArrayList<String> distance;
    private ArrayList<Restaurant> result;
    
    private Connection conn;
    
	public RestSelector() {
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
    
    private void fetchBudget(HttpServletRequest request) {
    	budget = new ArrayList<String>();
    	
    	if(request.getParameter("budget1") != null) {
			budget.add("'" + request.getParameter("budget1") + "'");
		}
		
		if(request.getParameter("budget2") != null) {
			budget.add("'" + request.getParameter("budget2") + "'");
		}
		
		if(request.getParameter("budget3") != null) {
			budget.add("'" + request.getParameter("budget3") + "'");
		}
		
		if(request.getParameter("budget4") != null) {
			budget.add("'" + request.getParameter("budget4") + "'");
		}
		
		if(request.getParameter("budget5") != null) {
			budget.add("'" + request.getParameter("budget5") + "'");
		}
    }
    
    private void fetchTime(HttpServletRequest request) {
    	time = new ArrayList<String>();
    	
		if(request.getParameter("time1") != null) {
			time.add("'" + request.getParameter("time1") + "'");
		}
		
		if(request.getParameter("time2") != null) {
			time.add("'" + request.getParameter("time2") + "'");
		}
		
		if(request.getParameter("time3") != null) {
			time.add("'" + request.getParameter("time3") + "'");
		}
    }
    
    private void fetchType(HttpServletRequest request) {
    	type = new ArrayList<String>();
    	
    	if(request.getParameter("type1") != null) {
			type.add("'中式'");
		}
		
		if(request.getParameter("type2") != null) {
			type.add("'韓式'");
			type.add("'日式'");
			type.add("'美式'");
			type.add("'義式'");
			type.add("'大馬風味'");
		}
		
		if(request.getParameter("type3") != null) {
			type.add("'輕食'");
			type.add("'速食'");
		}
    }
    
    private void fetchDistance(HttpServletRequest request) {
    	distance = new ArrayList<String>();
    	
		if(request.getParameter("dist1") != null) {
			distance.add("'" + request.getParameter("dist1") + "'");
		}
		
		if(request.getParameter("dist2") != null) {
			distance.add("'" + request.getParameter("dist2") + "'");
		}
		
		if(request.getParameter("dist3") != null) {
			distance.add("'" + request.getParameter("dist3") + "'");
		}
    }

    private void suggest() {
        String query = "SELECT * " +
        			   "FROM Restaurant WHERE (";
        String weekdayQuery = "SELECT CONVERT(WEEKDAY(CURDATE()), char) as weekday";

        if (budget != null && !budget.isEmpty()) {
        	int count = 0;
        	
        	for (int i = 0; i < budget.size(); i++) {
                if (count > 0) {
                    query += " OR ";
                }
                query += "Budget = " + budget.get(i);
                count++;
            }
            query += ")";
        }else {
        	query += "1)";
        }

        if (time != null && !time.isEmpty()) {
        	query += " AND (";
        	int count = 0;
        	
        	for (int i = 0; i < time.size(); i++) {
                if (count > 0) {
                    query += " OR ";
                }
                query += "Dining_time = " + time.get(i);
                count++;
            }
            query += ")";
        }
        
        if (type != null && !type.isEmpty()) {
        	query += " AND (";
        	int count = 0;
        	
        	for (int i = 0; i < type.size(); i++) {
                if (count > 0) {
                    query += " OR ";
                }
                query += "Type = " + type.get(i);
                count++;
            }
        	query += ")";
        }

        if (distance != null && !distance.isEmpty()) {
        	query += " AND (";
        	int count = 0;
        	
        	for (int i = 0; i < distance.size(); i++) {
                if (count > 0) {
                    query += " OR ";
                }
                query += "Distance = " + distance.get(i);
                count++;
            }
        	query += ")";
        }

        String weekday = "";
        try {
            PreparedStatement stat = conn.prepareStatement(weekdayQuery);
            ResultSet rs = stat.executeQuery();
            
            if (rs.next()) {
            	weekday = Integer.toString(rs.getInt("weekday"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query += "AND (Open1 LIKE \'%" + weekday + "%\' AND CURTIME() BETWEEN " +
                 "CONVERT(substring(Time1, 1, 8), time) AND CONVERT(substring(Time1, 10, 8), time)) " +
                 "OR (Open2 LIKE \'%" + weekday + "%\' AND CURTIME() BETWEEN " +
                 "CONVERT(substring(Time2, 1, 8), time) AND CONVERT(substring(Time2, 10, 8), time)) " +
        		 "OR (Open3 LIKE \'%" + weekday + "%\' AND CURTIME() BETWEEN " +
                 "CONVERT(substring(Time3, 1, 8), time) AND CONVERT(substring(Time3, 10, 8), time));";
        
        try {
        	PreparedStatement stat = conn.prepareStatement(query);
        	ResultSet rs = stat.executeQuery();
        	result = new ArrayList<Restaurant>();
        	
        	while(rs.next()) {
        		result.add(new Restaurant(rs.getString("RestID"), rs.getString("Budget"), rs.getString("Dining_time"), rs.getString("Type"), rs.getString("Distance")));
        	}
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public void fetchRequest(HttpServletRequest request) {
    	fetchBudget(request);
		fetchTime(request);
		fetchType(request);
		fetchDistance(request);
    }
    
    public ArrayList<String> sortResult(){
    	suggest();
    	
    	ArrayList<String> sortedResult = new ArrayList<String>();
    	
    	if(result.size() == 0) {
    		return null;
    	}else if(result.size() > 0 && result.size() <= 3) {
    		for(Restaurant r : result) {
    			sortedResult.add(r.getRestID());
    		}
    		return sortedResult;
    	}else {
    		for(Restaurant r : result) {
    			r.calScore();
    		}
    		quickSort(0, result.size() - 1);
    		for(int i = 0; i <= 2; i++) {
    			sortedResult.add(result.get(i).getRestID());
    		}
    		return sortedResult;
    	}
    }
    
    public ArrayList<String> random(){
    	ArrayList<String> res = new ArrayList<String>();
    	try {
        	PreparedStatement stat = conn.prepareStatement("SELECT Name "
        												 + "FROM restaurant "
        												 + "ORDER BY RAND() "
        												 + "LIMIT 8");
        	ResultSet rs = stat.executeQuery();
        	
        	while(rs.next()) {
        		res.add(rs.getString("Name"));
        	}
        	return res;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    private void quickSort(int l, int r) {
    	if(l < r) {
    		int i = l - 1;
    		
    		for(int j = l; j <= r - 1; j++) {
    			if(result.get(j).getScore() <= result.get(r).getScore()) {
    				i++;
    				swap(i, j);
    			}
    		}
    		swap(i+1, r);
    		
    		quickSort(l, i);
    		quickSort(i + 2, r);
    	}
    }
    
    private void swap(int i, int j) {
    	Restaurant temp = result.get(i);
    	result.set(i, result.get(j));
    	result.set(j, temp);
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
