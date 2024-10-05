package webPages;

import controllers.RestSearcher;

import entities.Restaurant;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RestaurantPage")
public class RestaurantPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public RestaurantPage() {
	    super();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
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
	    
	    String lastPage = (String)session.getAttribute("last");		
		String[] attribute = request.getQueryString().split("&");

		RestSearcher searcher = new RestSearcher();
		Restaurant r = searcher.getRestaurant(attribute[1].split("=")[1]);
		
		searcher.close();
		
		request.setAttribute("last", lastPage);
		request.setAttribute("user", attribute[0]);
		request.setAttribute("RestID", attribute[1]);
		request.setAttribute("Name", r.getName());
		request.setAttribute("Address", r.getAddress());
		request.setAttribute("Phone", r.getPhone());
		request.setAttribute("BusHR", r.getBusinessHour());
		request.setAttribute("Closed", r.getClosed());
		request.setAttribute("Vegan", r.getVegan());
		request.getRequestDispatcher("restaurant.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}
