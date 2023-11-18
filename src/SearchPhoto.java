import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/SearchPhoto"})
public class SearchPhoto extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  static final Logger LOGGER = LogManager.getLogger(SearchPhoto.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 HttpSession session = request.getSession(false);
     session = request.getSession(false);
	  if (session.getAttribute("userid") == null || session.getAttribute("userid").equals(""))
	  {
	  	response.sendRedirect("login.jsp?msg=You are not logged in..!");
	  }
	  
    String userid = (String)session.getAttribute("userid");
    request.setAttribute("username", userid);
    RequestDispatcher rd = request.getRequestDispatcher("searchphoto.jsp");
    rd.include((ServletRequest)request, (ServletResponse)response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
