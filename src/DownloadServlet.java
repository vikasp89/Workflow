import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(DownloadServlet.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int BUFFER_SIZE = 4096;
    String caseId = request.getParameter("caseId");
    try {
      con = LoginDAO.getConnectionDetails();
      ps = con.prepareStatement("select * from CASE_MSTR where CASE_ID=?");
      ps.setString(1, caseId);
      rs = ps.executeQuery();
      if (rs.next()) {
        String fileName = rs.getString("FILENAME");
        Blob blob = rs.getBlob("FILEDATA");
        int fileLength = rs.getInt("FILESIZE");
        InputStream inputStream = blob.getBinaryStream();
        ServletContext context = getServletContext();
        String mimeType = context.getMimeType(fileName);
        if (mimeType == null)
          mimeType = "application/octet-stream"; 
        response.setContentType(mimeType);
        response.setContentLength(fileLength);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", new Object[] { fileName });
        response.setHeader(headerKey, headerValue);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1)
          servletOutputStream.write(buffer, 0, bytesRead); 
        inputStream.close();
        servletOutputStream.close();
      } else {
        response.getWriter().print("File not found for the id: " + caseId);
      } 
    } catch (SQLException ex) {
      ex.printStackTrace();
      response.getWriter().print("SQL Error: " + ex.getMessage());
    } catch (IOException ex) {
      LOGGER.info("Error At AddComment=" + ex.getMessage());
      response.getWriter().print("IO Error: " + ex.getMessage());
    } catch (ClassNotFoundException classNotFoundException) {
    
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
  }
}
