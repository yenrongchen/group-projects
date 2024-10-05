package webPages;

import controllers.UserManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChangePasswordPage")
public class ChangePasswordPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ChangePasswordPage() {
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
	    request.getRequestDispatcher("changePassword.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
	    String username = request.getParameter("username2");
	    String reusername = request.getParameter("reusername2");
	    
	    PrintWriter out = response.getWriter();
	    
	    if(!username.equals(reusername)) {
	    	out.println("<script>");
	    	out.println("alert('您的密碼前後輸入不一致，請重新輸入')");
	    	out.println("</script>");
	    }else {
	    	UserManager manager = new UserManager();
	    	String res = manager.changePassword(request.getQueryString().split("=")[1], username);
	    	
	    	out.println("<script>");
	    	out.println("alert('" + res + "')");
	    	out.println("window.location.replace(\"/Final_Project_G4/ProfilePage?" + request.getQueryString() +"\");");
	    	out.println("</script>");
	    	manager.close();
	    }
	}
}