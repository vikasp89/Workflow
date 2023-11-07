import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/geteditables"})
public class geteditables extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(geteditables.class);
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String stage = request.getParameter("stage");
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String currentstage = "";
    try {
      con = LoginDAO.getConnectionDetails();
      String decquery = "select * from editable where stage_code='" + stage + "'";
      ps = con.prepareStatement(decquery);
      rs = ps.executeQuery();
      while (rs.next())
        currentstage = String.valueOf(currentstage) + "," + rs.getString("inputid"); 
    } catch (Exception e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } finally {
      if (rs != null)
        try {
          rs.close();
          rs = null;
        } catch (SQLException ex) {
          ex.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
          ps = null;
        } catch (SQLException ex) {
          ex.printStackTrace();
        }  
      if (con != null)
        try {
          con.close();
          con = null;
        } catch (SQLException ex) {
          ex.printStackTrace();
        }  
    } 
    response.getWriter().write(currentstage);
  }
}
