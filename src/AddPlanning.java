import com.vo.PlanningVO;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/AddPlanning"})
public class AddPlanning extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(AddPlanning.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    String n = request.getParameter("caseId");
    String crm = request.getParameter("crm");
    String patient_Name = request.getParameter("patient_Name");
    String cdoc = request.getParameter("cdoc");
    HttpSession session = request.getSession();
    List<PlanningVO> planningVO = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    PreparedStatement ps0 = null;
    ResultSet rs = null;
    ResultSet rs0 = null;
    String crm_priority = "";
    String corporate_account = "";
    try {
      con = LoginDAO.getConnectionDetails();
      ps0 = con.prepareStatement(" select * from cc_crm where Case_Id=?");
      ps0.setString(1, n);
      rs0 = ps0.executeQuery();
      while (rs0.next()) {
        crm_priority = rs0.getString("priority");
        corporate_account = rs0.getString("corporate_account");
      } 
      ps = con.prepareStatement(" select * from planning where case_id=?");
      ps.setString(1, n);
      rs = ps.executeQuery();
      while (rs.next()) {
        PlanningVO pvo = new PlanningVO();
        pvo.setPlanning_id(rs.getInt("planning_id"));
        pvo.setCase_id(rs.getString("case_id"));
        pvo.setDoctor_name(rs.getString("doctor_name"));
        pvo.setPatient_name(rs.getString("patient_name"));
        pvo.setCrm(rs.getString("crm"));
        pvo.setDate(rs.getString("date"));
        pvo.setPlanned_no(rs.getString("planned_no"));
        pvo.setLower_aligner_from(rs.getString("lower_aligner_from"));
        pvo.setLower_aligner_to(rs.getString("lower_aligner_to"));
        pvo.setUpper_aligner_from(rs.getString("upper_aligner_from"));
        pvo.setUpper_aligner_to(rs.getString("upper_aligner_to"));
        pvo.setPlanning_type(rs.getString("planning_type"));
        planningVO.add(pvo);
      } 
      session.setAttribute("plnlist", planningVO);
      session.setAttribute("plnpriority", crm_priority);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } 
    finally {
        if (ps != null)
          try {
        	  ps.close();
          } catch (Exception exception) {
          
          } finally {
        	  ps = null;
          } 
        if (ps0 != null)
            try {
          	  ps0.close();
            } catch (Exception exception) {
            
            } finally {
          	  ps0 = null;
            } 
        if (rs != null)
          try {
            rs.close();
          } catch (Exception exception) {
          
          } finally {
            rs = null;
          }  
        if (rs0 != null)
            try {
              rs0.close();
            } catch (Exception exception) {
            
            } finally {
              rs0 = null;
            }  
        if (con != null)
          try {
            con.close();
          } catch (Exception exception) {
          
          } finally {
            con = null;
          }  
      }
    out.print("Hello==== " + n + " | crm=" + crm + " || patient_Name=" + patient_Name + " || cdoc=" + cdoc);
    response.sendRedirect("planning.jsp?caseId=" + n + "&crm=" + crm + "&cdoc=" + cdoc + "&patient_Name=" + patient_Name + "&corporate_account=" + corporate_account);
  }
}
