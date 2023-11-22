import com.workflow.connection.LoginDAO;
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
@WebServlet({"/Logout"})
public class Logout extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Logout.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession(false);
      String n = (String)session.getAttribute("userid");
      LoginDAO.logOut(n);
      session.invalidate();
      session = request.getSession(false);
      if (session.getAttribute("userid") == null || session.getAttribute("userid").equals("")) {
      	response.sendRedirect("login.jsp?msg=You are not logged in..!");
      }
      RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } catch (Exception e) {
      LOGGER.info("Error At Logout=" + e.getMessage());
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession(false);
      String n = (String)session.getAttribute("userid");
      
      LoginDAO.logOut(n);
      session.invalidate();

      RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } catch (Exception e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } 
  }
}
