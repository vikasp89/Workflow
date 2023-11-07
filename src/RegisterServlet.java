import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(RegisterServlet.class);
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    Connection con = null;
    Statement insertStmnt = null;
    Statement st = null;
    ResultSet rs = null;
    String userid1 = "";
    String sql1 = "";
    try {
      con = LoginDAO.getConnectionDetails();
      st = con.createStatement();
      insertStmnt = con.createStatement();
      String userid = request.getParameter("USER_ID");
      String firstname = request.getParameter("FIRST_NAME");
      String middlename = request.getParameter("MIDDLE_NAME");
      String lastname = request.getParameter("LAST_NAME");
      String profileid = request.getParameter("PROFILE_ID");
      String password = request.getParameter("PASSWORD");
      String emailid = request.getParameter("EMAIL_ID");
      String phone = request.getParameter("PHONE");
      String fax = request.getParameter("FAX");
      String designation = request.getParameter("DESIGNATION");
      String city = request.getParameter("CITY");
      String district = request.getParameter("DISTRICT");
      String country = request.getParameter("COUNTRY");
      sql1 = "SELECT USER_ID FROM USER_MSTR where USER_ID='" + userid + "'";
      rs = st.executeQuery(sql1);
      if (rs.next()) {
        userid1 = rs.getString("USER_ID");
        out.println("<script type=\"text/javascript\">");
        out.println("alert('UserId already exist!');");
        out.println("location='Register.jsp';");
        out.println("</script>");
      } else {
        String query = "Insert into USER_MSTR(USER_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, PROFILE_ID, STATUS, PASSWORD, EMAIL_ID, PHONE,FAX,DESIGNATION,CITY,DISTRICT,COUNTRY) values('" + userid + "', '" + firstname + "','" + middlename + "', '" + lastname + "','" + profileid + "','Y','" + password + "','" + emailid + "','" + phone + "' ,'" + fax + "','" + designation + "' ,'" + city + "','" + district + "','" + country + "')";
        int i = insertStmnt.executeUpdate(query);
        if (i > 0) {
          out.println("<script type=\"text/javascript\">");
          out.println("location='index.jsp';");
          out.println("</script>");
        } else {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Error occur!');");
          out.println("location='Register.jsp';");
          out.println("</script>");
        } 
      } 
    } catch (SQLException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } catch (ClassNotFoundException e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } finally {
      if (insertStmnt != null)
        try {
          insertStmnt.close();
          insertStmnt = null;
        } catch (SQLException ex) {
          LOGGER.info("Error At AddComment=" + ex.getMessage());
        }  
      if (st != null)
        try {
          st.close();
          st = null;
        } catch (SQLException ex) {
          LOGGER.info("Error At AddComment=" + ex.getMessage());
        }  
      if (rs != null)
        try {
          rs.close();
          rs = null;
        } catch (SQLException ex) {
          LOGGER.info("Error At AddComment=" + ex.getMessage());
        }  
      if (con != null)
        try {
          con.close();
          con = null;
        } catch (SQLException ex) {
          LOGGER.info("Error At AddComment=" + ex.getMessage());
        }  
    } 
  }
}
