import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet({"/SearchAccountRevokeNumber"})
public class SearchAccountRevokeNumber extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  static final Logger LOGGER = LogManager.getLogger(SearchAccountRevokeNumber.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String n = (String)session.getAttribute("userid");
    request.setAttribute("username", n);
    try {
      String stages = LoginDAO.getStages(n);
      request.setAttribute("stages", stages);
      RequestDispatcher rd = request.getRequestDispatcher("accountrevokesearch.jsp");
      rd.include((ServletRequest)request, (ServletResponse)response);
    } catch (ClassNotFoundException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } catch (SQLException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } 
  }
}
