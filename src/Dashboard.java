import com.vo.PendingVO;
import com.vo.StageMasterVO;
import com.vo.ViewVO;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
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

@WebServlet({"/Dashboard"})
public class Dashboard extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Dashboard.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      String profile = (String)session.getAttribute("profile");
      String n = (String)session.getAttribute("userid");
      System.out.println(String.valueOf(profile) + "======================== " + n);
      List<PendingVO> pendingVO = LoginDAO.getCaseID(n, profile);
      List<PendingVO> pendingVOList = LoginDAO.getCaseIDStageCount(n, profile);
      List<ViewVO> viewVO = LoginDAO.getCase(n);
      List<PendingVO> list = LoginDAO.totalCaseID(n, profile);
      session.setAttribute("pendingCount", Integer.valueOf(pendingVO.size()));
      session.setAttribute("viewCount", Integer.valueOf(viewVO.size()));
      session.setAttribute("totalCount", Integer.valueOf(list.size()));
      session.setAttribute("pendingVOList", pendingVOList);
      List<StageMasterVO> getStageMaster = LoginDAO.getStageMaster();
      RequestDispatcher rd = request.getRequestDispatcher("Dashboard.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } catch (Exception e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("LogOutServlet");
    doGet(request, response);
  }
}
