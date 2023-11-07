import com.workflow.connection.LoginDAO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/uploadFile"})
@MultipartConfig(fileSizeThreshold = 2097152, maxFileSize = 10485760L, maxRequestSize = 52428800L)
public class UploadFileServlet extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(UploadFileServlet.class);
  
  private static final long serialVersionUID = 1L;
  
  public static final String SAVE_DIRECTORY = "uploadDir";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    CallableStatement callable = null;
    HttpSession session = request.getSession(false);
    String userid = (String)session.getAttribute("userid");
    String case_id = new String();
    try {
      con = LoginDAO.getConnectionDetails();
      String query3 = "select case_id from patient_details where user_id='<userid>' order by created_date desc";
      query3 = query3.replace("<userid>", userid);
      ps = con.prepareStatement(query3);
      ResultSet rst = ps.executeQuery();
      if (rst.next())
        case_id = rst.getString("case_id"); 
    } catch (Exception e1) {
      LOGGER.info("Error At UploadFileServlet=" + e1.getMessage());
    } 
    try {
      Part part1 = request.getPart("myFile");
      String appPath = "D:/DCM/" + case_id;
      String fullSavePath = appPath;
      File fileSaveDir = new File(fullSavePath);
      if (!fileSaveDir.exists())
        fileSaveDir.mkdir(); 
      String fileName = extractFileName(part1);
      if (fileName != null && fileName.length() > 0) {
        String filePath = String.valueOf(fullSavePath) + File.separator + fileName;
        part1.write(filePath);
      } 
      response.sendRedirect(String.valueOf(request.getContextPath()) + "/uploadFileResults");
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("errorMessage", "Error: " + e.getMessage());
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsps/uploadFile.jsp");
      dispatcher.forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  private String extractFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = items).length, b = 0; b < i; ) {
      String s = arrayOfString1[b];
      if (s.trim().startsWith("filename")) {
        String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
        clientFileName = clientFileName.replace("\\", "/");
        int j = clientFileName.lastIndexOf('/');
        return clientFileName.substring(j + 1);
      } 
      b++;
    } 
    return null;
  }
}
