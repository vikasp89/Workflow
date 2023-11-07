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

@WebServlet({"/HoldCase"})
public class HoldCase extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static final Logger LOGGER = LogManager.getLogger(HoldCase.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession(false);
      String userName = request.getParameter("username");
      
      request.setAttribute("username", userName);
      if (request.getParameter("createSubmitHold") != null) {
        RequestDispatcher rd = request.getRequestDispatcher("HoldCase.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } 
      if (request.getParameter("createSubmitUnhold") != null) {
        RequestDispatcher rd = request.getRequestDispatcher("UnHoldCase.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } 
      if (request.getParameter("cancelcase") != null) {
          RequestDispatcher rd = request.getRequestDispatcher("cancelcase.jsp");
          rd.forward((ServletRequest)request, (ServletResponse)response);
        } 
    } catch (Exception e) {
      LOGGER.info("Error At HoldCase=" + e.getMessage());
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
