import com.vo.PendingVO;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

@WebServlet({"/Pending"})
public class Pending extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Pending.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      String profile = (String)session.getAttribute("profile");
      String n = (String)session.getAttribute("userid");
      request.setAttribute("username", n);
      String caseLock = (String)request.getAttribute("CaseLock");
      request.setAttribute("caseLock", caseLock);
      List<PendingVO> pendingVO = LoginDAO.getCaseID(n, profile);
      request.setAttribute("pendingVOList", pendingVO);
      RequestDispatcher rd = request.getRequestDispatcher("pending.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } catch (ClassNotFoundException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } catch (SQLException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } 
  }
}
