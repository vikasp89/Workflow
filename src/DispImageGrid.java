import com.vo.ImagesVo;
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

@WebServlet({"/DispImageGrid"})
public class DispImageGrid extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  static final Logger LOGGER = LogManager.getLogger(DispImageGrid.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String caseId = request.getParameter("caseId");
    HttpSession session = request.getSession();
    String n = (String)session.getAttribute("userid");
    request.setAttribute("username", n);
    List<ImagesVo> ImagesVo = LoginDAO.getImages(caseId);
    request.setAttribute("ImgVoList", ImagesVo);
    RequestDispatcher rd = request.getRequestDispatcher("images.jsp");
    rd.forward((ServletRequest)request, (ServletResponse)response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
