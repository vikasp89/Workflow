import com.vo.DraftVo;
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

@WebServlet({"/Drafts"})
public class Drafts extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Drafts.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    String profile = (String)session.getAttribute("profile");
    String n = (String)session.getAttribute("userid");
    request.setAttribute("username", n);
    List<DraftVo> DraftVo = LoginDAO.getDraft(n);
    request.setAttribute("DraftVoList", DraftVo);
    for (DraftVo draftVo : DraftVo);
    RequestDispatcher rd = request.getRequestDispatcher("drafts.jsp");
    rd.forward((ServletRequest)request, (ServletResponse)response);
  }
}
