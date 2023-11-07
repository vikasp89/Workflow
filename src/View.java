import com.vo.ViewVO;
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

@WebServlet({"/View"})
public class View extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(View.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String n = (String)session.getAttribute("userid");
    try {
      List<ViewVO> viewVO = LoginDAO.getCase(n);
      request.setAttribute("viewVOList", viewVO);
      viewVO.size();
      RequestDispatcher rd = request.getRequestDispatcher("View.jsp");
      rd.include((ServletRequest)request, (ServletResponse)response);
    } catch (ClassNotFoundException classNotFoundException) {
    
    } catch (SQLException e) {
      LOGGER.info("Error At View=" + e.getMessage());
    } 
  }
}
