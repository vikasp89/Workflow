import com.workflow.connection.LoginDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/DisplayImage"})
public class DisplayImage extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Statement stmt = null;
    String sql = null;
    BufferedInputStream bin = null;
    BufferedOutputStream bout = null;
    InputStream in = null;
    response.setContentType("image/jpeg");
    ServletOutputStream out = response.getOutputStream();
    Connection conn = null;
    int ID = Integer.parseInt(request.getParameter("ID"));
    System.out.println("ID===" + ID);
    try {
      conn = LoginDAO.getConnectionDetails();
      stmt = conn.createStatement();
      sql = "SELECT * FROM uploadsimages where id='" + ID + "' ";
      ResultSet result = stmt.executeQuery(sql);
      if (result.next())
        in = result.getBinaryStream(3); 
      bin = new BufferedInputStream(in);
      bout = new BufferedOutputStream((OutputStream)out);
      int ch = 0;
      while ((ch = bin.read()) != -1)
        bout.write(ch); 
    } catch (SQLException ex) {
      Logger.getLogger(DisplayImage.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bin != null)
          bin.close(); 
        if (in != null)
          in.close(); 
        if (bout != null)
          bout.close(); 
        if (out != null)
          out.close(); 
        if (conn != null)
          conn.close(); 
      } catch (IOException|SQLException ex) {
        System.out.println("Error : " + ex.getMessage());
      } 
    } 
  }
}
