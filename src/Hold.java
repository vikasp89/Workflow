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

@WebServlet({"/Hold"})
public class Hold extends HttpServlet {
  /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    static final Logger LOGGER = LogManager.getLogger(Hold.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String n = (String)session.getAttribute("userid");
    System.out.println("Hold n : "+n);
    try {
      List<ViewVO> viewVO = LoginDAO.getHoldCase(n);
      request.setAttribute("viewVOList", viewVO);
      request.setAttribute("username", n);
      viewVO.size();
      RequestDispatcher rd = request.getRequestDispatcher("Hold.jsp");
      rd.include((ServletRequest)request, (ServletResponse)response);
    } catch (ClassNotFoundException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } catch (SQLException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } 
  }
}
