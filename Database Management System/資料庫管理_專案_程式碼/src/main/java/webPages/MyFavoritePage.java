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

import java.util.ArrayList;

@WebServlet("/MyFavoritePage")
public class MyFavoritePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public MyFavoritePage() {
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
		UserManager manager = new UserManager();
		ArrayList<String> favorite = manager.getFavorite(request.getQueryString().split("=")[1]);
		
		if(favorite == null || favorite.isEmpty()) {
			request.setAttribute("Rest1", "無");
			request.setAttribute("Rest2", "無");
			request.setAttribute("Rest3", "無");
		}else {
			request.setAttribute("Rest1", favorite.get(0));
			if(favorite.size() == 1) {
				request.setAttribute("Rest2", "無");
				request.setAttribute("Rest3", "無");
			}else {				
				if(favorite.size() == 2) {
					request.setAttribute("Rest2", favorite.get(1));
					request.setAttribute("Rest3", "無");
				} else {
					request.setAttribute("Rest2", favorite.get(1));
					request.setAttribute("Rest3", favorite.get(2));
				}
			}
		}
		manager.close();
		request.setAttribute("user", request.getQueryString());
		request.getRequestDispatcher("myFavorite.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		UserManager manager = new UserManager();
		PrintWriter out = response.getWriter();
		
		String[] attr = request.getQueryString().split("&");
		String uid = attr[0].split("=")[1];
		String rid = attr[1].split("=")[1];
		String res = manager.addFavorite(uid, rid);
		
		if(res.equals("新增成功")) {
			out.println("<script>");
			out.println("alert('" + res + "!')");
			out.println("document.location.assign(\"/Final_Project_G4/RestaurantPage?$id=" + uid + "&RestID=" + rid + "\");");
			out.println("</script>");
			out.flush();
		}else {
			out.println("<script>");
			out.println("alert('" + res + "')");
			out.println("document.location.assign(\"/Final_Project_G4/RestaurantPage?$id=" + uid + "&RestID=" + rid + "\");");
			out.println("</script>");
			out.flush();
		}
		manager.close();
	}
}