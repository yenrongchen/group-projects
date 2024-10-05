package webPages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FunctionListPage")
public class FunctionListPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public FunctionListPage() {
	    super();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
	 	HttpSession session = request.getSession(true);
	 	
	 	if(request.getQueryString().equals("id=1")) {
	 		session.setAttribute("pass", "ok");
	 		request.setAttribute("signOut", "離開");
	 	}else {
	 		request.setAttribute("signOut", "登出");
	 	}
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
		request.getRequestDispatcher("functionList.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}