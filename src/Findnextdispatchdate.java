import com.vo.nextdispatch;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
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

@WebServlet({"/Findnextdispatchdate"})
public class Findnextdispatchdate extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Findnextdispatchdate.class);
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String case_number = request.getParameter("case_number");
    String fromdate = request.getParameter("fromdate");
    String todate = request.getParameter("todate");
    Connection con = null;
    ResultSet rs = null;
    List<nextdispatch> li = null;
    if (case_number != "") {
      li = LoginDAO.findnextdispatchdata(case_number);
      request.setAttribute("nextdispatch", li);
      RequestDispatcher rd = request.getRequestDispatcher("nextdispatchdisplay.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } else {
      li = LoginDAO.findnextdispatchdatabydate(fromdate, todate);
      request.setAttribute("nextdispatch", li);
      RequestDispatcher rd = request.getRequestDispatcher("nextdispatchdisplay.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
}
