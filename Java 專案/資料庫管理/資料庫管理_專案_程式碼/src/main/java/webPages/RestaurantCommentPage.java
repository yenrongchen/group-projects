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

import java.util.HashMap;
import java.util.Map.Entry;

@WebServlet("/RestaurantCommentPage")
public class RestaurantCommentPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public RestaurantCommentPage() {
	    super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	    
	    RestSearcher searcher = new RestSearcher();
	    String[] attribute = request.getQueryString().split("&");		
		String commentTable = String.format("<table>\n<tr style=\"visibility: hidden;\">\r\n"
				+ "      	<td width=\"200px\">\r\n"
				+ "      	<td width=\"200px\">\r\n"
				+ "      </tr><tr>\n<th>評論</th>\n<th>星星數</th>\n</tr>\n");
		HashMap<String, String> comment = searcher.getComment(attribute[1].split("=")[1]);
		
	    if(comment.isEmpty()) {
	    	commentTable += String.format("<tr>\n<td>%s</td>\n<td>%s</td>\n</tr>\n", "還沒有人評價過這間餐廳", "");
	    }else {
	    	for(Entry<String, String> entry : comment.entrySet()) {
		    	commentTable += String.format("<tr>\n<td>%s</td>\n<td>%s</td>\n</tr>\n", entry.getKey(), entry.getValue());
		    }
	    }
	    
	    
		request.setAttribute("user", attribute[0]);
		request.setAttribute("RestID", attribute[1]);
		request.setAttribute("Rest", searcher.getNameById(attribute[1].split("=")[1]));
		request.setAttribute("comment", commentTable);
		
		searcher.close();
	    request.getRequestDispatcher("restaurantComment.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
