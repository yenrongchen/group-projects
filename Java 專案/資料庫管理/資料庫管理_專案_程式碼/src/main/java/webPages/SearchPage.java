package webPages;

import controllers.RestSearcher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SearchPage")
public class SearchPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchPage() {
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
		request.setAttribute("user", request.getQueryString());
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		RestSearcher searcher = new RestSearcher();
		
		String name = request.getParameter("name");
		String Rname = searcher.searchByName(name);
		String RestID = "";
		
		if(Rname != null) {
			RestID = searcher.getIdByName(Rname);
			PrintWriter out = response.getWriter();
			String url = String.format("/Final_Project_G4/SearchPage?" + request.getQueryString());
			
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(60*10);
			session.setAttribute("last", url);
			
			out.println("<script>");
			out.println("window.location.replace(\"/Final_Project_G4/RestaurantPage?" + request.getQueryString() + "&RestID=" + RestID +"\");");
			out.println("</script>");
			out.flush();
			
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('很抱歉，找不到您所輸入的餐廳。')");
			out.println("window.location.replace(\"/Final_Project_G4/SearchPage?" + request.getQueryString() + "\");");
			out.println("</script>");
			out.flush();
		}
		searcher.close();
	}
}
