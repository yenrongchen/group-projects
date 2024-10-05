package webPages;

import controllers.RestSearcher;
import controllers.UserManager;

import entities.Restaurant;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CommentPage")
public class CommentPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public CommentPage() {
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
		String[] attr = request.getQueryString().split("&");
		RestSearcher searcher = new RestSearcher();
		Restaurant r = searcher.getRestaurant(attr[1].split("=")[1]);
		
		searcher.close();
		request.setAttribute("user", attr[0]);
		request.setAttribute("RestID", attr[1]);
		request.setAttribute("Name", r.getName());
		request.getRequestDispatcher("comment.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		UserManager manager = new UserManager();
		String[] attr = request.getQueryString().split("&");
		String[] rateValues = request.getParameterValues("rate");
		String uid = attr[0].split("=")[1];
		String rid = attr[1].split("=")[1];
		String comment = request.getParameter("myTextbox");
		
	    int star = 0;
	    if (rateValues != null) {
	        for (String value : rateValues) {
	            if (value != null) {
	            	star = Integer.parseInt(value);
	            }
	        }
	    }
		
		boolean res = manager.writeReview(uid, rid, comment, String.valueOf(star));
		
		if(res) {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('評論成功')");
			out.println("document.location.assign(\"/Final_Project_G4/RestaurantPage?$id=" + uid + "&RestID=" + rid + "\");");
			out.println("</script>");
			out.flush();
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('評論失敗')");
			out.println("document.location.assign(\"/Final_Project_G4/RestaurantPage?$id=" + uid + "&RestID=" + rid + "\");");
			out.println("</script>");
			out.flush();
		}
		manager.close();
	}
}
