import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/Approval"})
public class Approval extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static final Logger LOGGER = LogManager.getLogger(Approval.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String logusername = request.getParameter("logusername");
      String caseId = request.getParameter("caseId");
      String NxtStage = request.getParameter("NxtStage");
      String SearchStage = request.getParameter("SearchStage");
      String errorAletrt = "N";
      request.setAttribute("abc", caseId);
      request.setAttribute("def", logusername);
      String caseLock = "N";
      if (caseLock.equals("N") || caseLock.equals("Y")) {
        request.setAttribute("caseId", caseId);
        RequestDispatcher rd = request.getRequestDispatcher("Approval.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } else {
        String[] array = caseLock.split("~");
        if (array[0].equals(logusername.toLowerCase())) {
          errorAletrt = "Case is Locked by " + array[1];
          request.setAttribute("CaseLock", errorAletrt);
          if (SearchStage.equals("Y")) {
            RequestDispatcher rd = request.getRequestDispatcher("SearchCase");
            rd.forward((ServletRequest)request, (ServletResponse)response);
          } else {
            RequestDispatcher rd = request.getRequestDispatcher("Pending");
            rd.forward((ServletRequest)request, (ServletResponse)response);
          } 
        } 
      } 
    } catch (Exception e) {
      LOGGER.info("Error At Approval =" + e.getMessage());
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
