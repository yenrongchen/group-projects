package webPages;

import controllers.RestSelector;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

@WebServlet("/SelectPage")
public class SelectPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SelectPage() {
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
		request.getRequestDispatcher("select.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		RestSelector s = new RestSelector();
		s.fetchRequest(request);
		
		ArrayList<String> result = s.sortResult();
		String attr = "";
		
		if(result == null || result.size() == 0) {
			attr = "none";}
		else {
			attr = result.get(0);
			
			for(int i = 1; i < result.size(); i++) {
				attr += ",";
				attr += result.get(i);
			}			
		}
		
		PrintWriter out = response.getWriter();
		String url = String.format("/Final_Project_G4/RecommandPage?" + request.getQueryString() + "&Result=" + attr);
		
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(60*10);
		session.setAttribute("last", url);
		
		out.println("<script>");
		out.println("window.location.replace(\"" + url +"\");");
		out.println("</script>");
		out.flush();
		s.close();
	}
}