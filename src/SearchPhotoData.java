import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/SearchPhotoData"})
public class SearchPhotoData extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  static final Logger LOGGER = LogManager.getLogger(SearchPhotoData.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  System.out.println("post..........................");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(); 
    System.out.println("SearchPhotoData  hit ");
    Connection con = null;
    Connection con1 = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String CaseId = "";
    String DoctorName = "";
    String PatientName = "";
    String ClinicName = "";
    String viewphoto = "";
    String photoType = "";
    CaseId = (request.getParameter("caseId") == null) ? "" : request.getParameter("caseId");
    DoctorName = (request.getParameter("doctorname") == null) ? "" : request.getParameter("doctorname");
    PatientName = (request.getParameter("patient_Name") == null) ? "" : request.getParameter("patient_Name");
    ClinicName = (request.getParameter("clinicname") == null) ? "" : request.getParameter("clinicname");
    viewphoto = (request.getParameter("viewphoto") == null) ? "" : request.getParameter("viewphoto");
    photoType = (request.getParameter("photoType") == null) ? "" : request.getParameter("photoType");
    
    System.out.println("DoctorName "+DoctorName+" photoType: "+photoType+" viewphoto: "+viewphoto);
    
    String wfOnlysql = "select * from cc_crm where Case_Id='" + CaseId + "' ";
    try {
      con = LoginDAO.getConnectionDetails();
      
      if (viewphoto.equalsIgnoreCase("N") && photoType.equals("wfOnly")) {
          response.sendRedirect("DragDropImages?caseId=" + CaseId + "&doctorname=" + DoctorName + "&patient_Name=" + PatientName + "&clinicname=" + ClinicName);
      } else if (viewphoto.equalsIgnoreCase("Y") && photoType.equals("wfOnly")) {
        pstmt = con.prepareStatement(wfOnlysql);
        rs = pstmt.executeQuery();
        if (rs.next()) {
          CaseId = rs.getString("Case_Id");
          DoctorName = rs.getString("Doctor_Name");
          PatientName = rs.getString("Patient_Name");
          ClinicName = rs.getString("clinic_Name");
        } 
        response.sendRedirect("DispImageGrid?caseId=" + CaseId + "&doctorname=" + DoctorName + "&patient_Name=" + PatientName + "&clinicname=" + ClinicName);
      }
      else if (photoType.equalsIgnoreCase("Mid Scan")) {
    	  System.out.println("mid else if");   
    	  response.sendRedirect("NewQueryPhotoGrid?caseId=" + CaseId + "&typeOfRequest=" + photoType); 
    	  }
      else if (photoType.equalsIgnoreCase("Next Batch Required")) {
    	  System.out.println("nextBatch else if");   
          response.sendRedirect("NewQueryPhotoGrid?caseId=" + CaseId + "&typeOfRequest=" + photoType);
      }
      else if (photoType.equalsIgnoreCase("Retainer")) {
    	  System.out.println("Retainer else if");   
          response.sendRedirect("NewQueryPhotoGrid?caseId=" + CaseId + "&typeOfRequest=" + photoType);
      }
      else if (photoType.equalsIgnoreCase("Repeat")) {
    	  System.out.println("repeat else if");   
          response.sendRedirect("NewQueryPhotoGrid?caseId=" + CaseId + "&typeOfRequest=" + photoType);
      }
    } catch (ClassNotFoundException|java.sql.SQLException e) {
      e.printStackTrace();
    } 
  }
}
