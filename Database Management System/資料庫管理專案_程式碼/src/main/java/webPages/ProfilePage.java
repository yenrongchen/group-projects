package webPages;

import controllers.UserManager;

import entities.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProfilePage")
public class ProfilePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ProfilePage() {
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
	    UserManager manager = new UserManager();
	    User u = manager.getUserByID(request.getQueryString().split("=")[1]);
	    if(u != null) {
	    	request.setAttribute("account", u.getAccount());
	    	request.setAttribute("password", u.getPassword());
	    	request.setAttribute("email", u.getEmail());
	    }
	    manager.close();
	    request.setAttribute("user", request.getQueryString());
	    request.getRequestDispatcher("profile.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}