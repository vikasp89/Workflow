import comm.CaseLock;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CaseLockSet extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(CaseLockSet.class);
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    String caseid = request.getParameter("a");
    String logusername = request.getParameter("b");
    String caseLock = CaseLock.getCaseLock(caseid, logusername);
    String[] array = caseLock.split("~");
    if (caseLock.equals("N") || caseLock.equals("Y")) {
      CaseLock.setCaseLock(caseid, logusername);
      out.print("allowed");
    } else {
      out.print("Case is Locked by " + array[1]);
    } 
  }
}
