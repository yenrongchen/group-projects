package webPages;

import controllers.UserManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Random;

@WebServlet("/VerificationPage")
public class VerificationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VerificationPage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
	 	HttpSession session = request.getSession(true);
	 	UserManager manager = new UserManager();
	 	
	    String val = (String)session.getAttribute("email");
	    String code = String.format("%06d", new Random().nextInt(1000000));
	     
	    manager.sendVerification(val, code);
	    manager.close();
	    session.setAttribute("verificationCode", code);
		request.getRequestDispatcher("verification.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		HttpSession session = request.getSession(true);
		
		String input = request.getParameter("verification");
		String code = (String)session.getAttribute("verificationCode");
		
		PrintWriter out = response.getWriter();
		
		if(input.equals(code)) {
			UserManager manager = new UserManager();
			manager.activeAccount((String)session.getAttribute("email"));
			
			out.println("<script>");
			out.println("alert('驗證完成，您的帳號已啟用');");
			out.println("window.location.replace(\"/Final_Project_G4/LoginPage\");");
			out.println("</script>");
			manager.close();
		}else {
			out.println("<script>");
			out.println("alert('驗證碼錯誤，新驗證碼已寄出，請重新驗證');");
			out.println("window.location.replace(\"/Final_Project_G4/VerificationPage\");");
			out.println("</script>");
		}
	}
}