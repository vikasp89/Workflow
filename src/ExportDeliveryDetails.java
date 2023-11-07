import com.vo.ExportVo;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

@WebServlet({"/ExportDeliveryDetails"})
public class ExportDeliveryDetails extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  static final Logger LOGGER = LogManager.getLogger(ExportDeliveryDetails.class);
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = LoginDAO.getConnectionDetails();
      List<ExportVo> list = new ArrayList<>();
      pstmt = con.prepareStatement("  select * from cc_crm as c,dispatched_scan as t where t.case_id=c.case_id and date(date)=date(current_date())  group by t.case_id ");
      rs = pstmt.executeQuery();
      while (rs.next()) {
        ExportVo ex = new ExportVo();
        ex.setCase_id(rs.getString("case_id"));
        ex.setTracking_id(rs.getString("tracking_id"));
        ex.setPatient_Name(rs.getString("patient_Name"));
        ex.setDoctor_Name(rs.getString("doctor_Name"));
        ex.setPhone(rs.getString("phone"));
        ex.setAddress1(rs.getString("address1"));
        ex.setAddress2(rs.getString("address2"));
        ex.setAddress3(rs.getString("address3"));
        ex.setAddress4(rs.getString("address4"));
        ex.setAddress5(rs.getString("address5"));
        ex.setDefault_address(rs.getString("default_address"));
        list.add(ex);
      } 
      request.setAttribute("exportlist", list);
      RequestDispatcher rd = request.getRequestDispatcher("addbarcodeinfo.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } catch (Exception e) {
      LOGGER.info("Error At ExportDeliveryDetails=" + e.getMessage());
    } finally {
      if (pstmt != null)
        try {
          pstmt.close();
        } catch (Exception exception) {
        
        } finally {
          pstmt = null;
        }  
      if (rs != null)
        try {
          rs.close();
        } catch (Exception exception) {
        
        } finally {
          rs = null;
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception exception) {
        
        } finally {
          con = null;
        }  
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
