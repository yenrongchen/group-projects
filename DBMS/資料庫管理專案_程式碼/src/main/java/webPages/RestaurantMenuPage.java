package webPages;

import controllers.RestSearcher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

@WebServlet("/RestaurantMenuPage")
public class RestaurantMenuPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public RestaurantMenuPage() {
	    super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String[] attribute = request.getQueryString().split("&");
		
		HttpSession session = request.getSession(true);
	    String val = (String)session.getAttribute("pass");
	    if(val == null){
	    	PrintWriter writer = response.getWriter();
	    	writer.println("<script>");
	    	writer.println("alert('請先登入！')");
	    	writer.println("window.location.replace(\"/Final_Project_G4/LoginPage\");");
	    	writer.println("</script>");
	    	return;
	    }
	    
	    RestSearcher searcher = new RestSearcher();
	    HashMap<String, String> menu = searcher.getMenu(attribute[1].split("=")[1]);
	    
	    String menuTable = String.format("<table>\n<tr style=\"visibility: hidden;\">\r\n"
	    		+ "      	<td width=\"280px\">\r\n"
	    		+ "      	<td width=\"120px\">\r\n"
	    		+ "      </tr><tr>\n<th>品名</th>\n<th>價格</th>\n</tr>\n");
	    
	    if(menu.isEmpty()) {
	    	menuTable += String.format("<tr>\n<td>%s</td>\n<td>%s</td>\n</tr>\n", "很抱歉，找不到這家餐廳的餐點詳細資訊", "");
	    }else {
	    	for(Entry<String, String> entry : menu.entrySet()) {
		    	menuTable += String.format("<tr>\n<td>%s</td>\n<td>%s</td>\n</tr>\n", entry.getKey(), entry.getValue());
		    }
	    }
	    
	    ArrayList<String> sources = searcher.getSource(attribute[1].split("=")[1]);
	    String sourceHtml = "";
	    
	    if(sources.isEmpty()) {
	    	sourceHtml += String.format("<img src=\"restaurants/" + "Rest0.jpg" + "\" style=\"max-width: 370px; max-height: 300px;\">");
	    }else {
	    	for(String source : sources) {
		    	sourceHtml += String.format("<img src=\"restaurants/" + source + "\" style=\"max-width: 370px; max-height: 300px;\">");
		    }
	    }
	    
	    String lastPage = (String)session.getAttribute("last");
		request.setAttribute("user", attribute[0]);
		request.setAttribute("RestID", attribute[1]);
		request.setAttribute("name", searcher.getNameById(attribute[1].split("=")[1]));
	    request.setAttribute("menu", menuTable);
	    request.setAttribute("source", sourceHtml);
	    request.setAttribute("last", lastPage);
	    
	    searcher.close();
	    request.getRequestDispatcher("restaurantMenu.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}
