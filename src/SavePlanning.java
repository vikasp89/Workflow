import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/SavePlanning"})
public class SavePlanning extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  static final Logger LOGGER = LogManager.getLogger(Case_Stage.class);
  
  private static final String SQL_INSERT = "INSERT INTO Planning (case_id,doctor_name,crm ,patient_name,base_segment,planned,ipr_sheet ,treat_report,upload_digiplan,plan_review,decesion,date,remark) VALUES (?,?,?,?,?,?,?,?,?,?,?,now(),?)";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(false);
    Connection con = null;
    Statement st3 = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement preparedStatement1 = null;
    ResultSet rs = null;
    String crm_name = "";
    String case_id = "";
    String patient_name = "";
    String doctor_name = "";
    String upper_aligner_from = "";
    String upper_aligner_to = "";
    String lower_aligner_from = "";
    String lower_aligner_to = "";
    String pn = "";
    String dr = "";
    String PNo = "";
    String UserId = (String)session.getAttribute("userid");
    String CaseId = request.getParameter("CaseId");
    String DoctorName = request.getParameter("DoctorName");
    String crm = request.getParameter("crm");
    String PatientName = request.getParameter("PatientName");
    String planningType = request.getParameter("planningType");
    String basingsegcheck = request.getParameter("basingsegcheck");
    String planned = request.getParameter("planned");
    String iprSheet = request.getParameter("iprSheet");
    String treatmentreport = request.getParameter("treatmentreport");
    String uploaddgiplan = request.getParameter("uploaddgiplan");
    String planningreview = request.getParameter("planningreview");
    String remarks = request.getParameter("remarks");
    String stage = request.getParameter("stage");
    String Planned_No = request.getParameter("Planned_No");
    String query = (request.getParameter("query") == null) ? "" : request.getParameter("query");
    String cid = (request.getParameter("caseId") == null) ? "" : request.getParameter("caseId");
    String pid = (request.getParameter("planning_id") == null) ? "" : request.getParameter("planning_id");
    PNo = (request.getParameter("Planned_No1") == null) ? "" : request.getParameter("Planned_No1");
    pn = (request.getParameter("patient_name") == null) ? "" : request.getParameter("patient_name");
    dr = (request.getParameter("doctor_name") == null) ? "" : request.getParameter("doctor_name");
    upper_aligner_from = (request.getParameter("upper_aligner_from") == null) ? "" : request.getParameter("upper_aligner_from");
    upper_aligner_to = (request.getParameter("upper_aligner_to") == null) ? "" : request.getParameter("upper_aligner_to");
    lower_aligner_from = (request.getParameter("lower_aligner_from") == null) ? "" : request.getParameter("lower_aligner_from");
    lower_aligner_to = (request.getParameter("lower_aligner_to") == null) ? "" : request.getParameter("lower_aligner_to");
    try {
      con = LoginDAO.getConnectionDetails();
      if (query.equals("approveplan")) {
        pstmt = con.prepareStatement(" update cc_crm set planning_id=? where Case_Id=? ");
        pstmt.setString(1, pid);
        pstmt.setString(2, cid);
        int row = pstmt.executeUpdate();
        if (row > 0) {
          String redirect = "AddPlanning?caseId=" + cid + "&crm=" + crm + "&cdoc=" + dr + "&patient_Name=" + pn;
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Plan Approved Successfully');");
          out.println("location='" + redirect + "';");
          out.println("</script>");
        } 
      } else if (query.equals("planningnew")) {
        pstmt = con.prepareStatement("insert into planning(case_id,planned_no,planning_type,upper_aligner_from,upper_aligner_to,lower_aligner_from,lower_aligner_to,date,patient_name,doctor_name,crm) VALUES (?,?,?,?,?,?,?,now(),?,?,?)");
        pstmt.setString(1, cid);
        pstmt.setString(2, PNo);
        pstmt.setString(3, planningType);
        pstmt.setString(4, upper_aligner_from);
        pstmt.setString(5, upper_aligner_to);
        pstmt.setString(6, lower_aligner_from);
        pstmt.setString(7, lower_aligner_to);
        pstmt.setString(8, pn);
        pstmt.setString(9, dr);
        pstmt.setString(10, crm);
        int row = pstmt.executeUpdate();
        if (row > 0) {
          String sqlselect = " select case_id,crm_name,Patient_Name,Doctor_Name from  cc_crm where case_id='" + cid + "' ";
          pstmt1 = con.prepareStatement(sqlselect);
          rs = pstmt1.executeQuery();
          while (rs.next()) {
            case_id = rs.getString("case_id");
            crm_name = rs.getString("crm_name");
            patient_name = rs.getString("patient_name");
            doctor_name = rs.getString("Doctor_Name");
          } 
          String id = "AddPlanning?caseId=" + case_id + "&crm=" + crm_name + "&patient_Name=" + patient_name + "&cdoc=" + doctor_name;
          out.println("<script type=\"text/javascript\">");
          out.println("alert('SUCCESSFULLY SAVED!');");
          out.println("location='" + id + "';");
          out.println("</script>");
        } 
      } else {
        preparedStatement = con.prepareStatement("INSERT INTO Planning (case_id,doctor_name,crm ,patient_name,base_segment,planned,ipr_sheet ,treat_report,upload_digiplan,plan_review,decesion,date,remark) VALUES (?,?,?,?,?,?,?,?,?,?,?,now(),?)");
        preparedStatement.setString(1, CaseId);
        preparedStatement.setString(2, DoctorName);
        preparedStatement.setString(3, crm);
        preparedStatement.setString(4, PatientName);
        preparedStatement.setString(5, basingsegcheck);
        preparedStatement.setString(6, planned);
        preparedStatement.setString(7, iprSheet);
        preparedStatement.setString(8, treatmentreport);
        preparedStatement.setString(9, uploaddgiplan);
        preparedStatement.setString(10, planningreview);
        preparedStatement.setString(11, stage);
        preparedStatement.setString(12, remarks);
        int row = preparedStatement.executeUpdate();
        String sql1 = "";
        if (row > 0) {
          if (stage.equals("PLN")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', pln_at=now()  where Case_Id='" + CaseId + "' ";
          } else if (stage.equals("PRECOR") && !remarks.equals("")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', precor_at=now()  where Case_Id='" + CaseId + "' ";
          } else if (stage.equals("PLNCOR") && !remarks.equals("")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', plncor_at=now()  where Case_Id='" + CaseId + "' ";
          } else if (stage.equals("UPLCOR") && !remarks.equals("")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', uplcor_at=now()  where Case_Id='" + CaseId + "' ";
          } else if (stage.equals("Not Converted") && !remarks.equals("")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', ntcnt_at=now()  where Case_Id='" + CaseId + "' ";
          } else if (stage.equals("Cancel") && !remarks.equals("")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', cnl_at=now()  where Case_Id='" + CaseId + "' ";
          } else if (stage.equals("REV")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', rev_at=now()  where Case_Id='" + CaseId + "' ";
          } else if (stage.equals("QA")) {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "', qa_at=now()  where Case_Id='" + CaseId + "' ";
          } else {
            sql1 = " update cc_crm set remark='" + remarks + "',case_stage='" + stage + "' where Case_Id='" + CaseId + "' ";
          } 
          preparedStatement1 = con.prepareStatement(sql1);
          int i = preparedStatement1.executeUpdate();
          if (i > 0) {
            con = LoginDAO.getConnectionDetails();
            st3 = con.createStatement();
            String query3 = "INSERT INTO decision_history(decision, Remarks, date_time, stage, UserId, caseid) values('" + stage + "','" + remarks + "' ,now(),'" + stage + "','" + UserId + "','" + CaseId + "')";
            int j = st3.executeUpdate(query3);
            if (j > 0) {
              out.println("<script type=\"text/javascript\">");
              out.println("alert('SUCCESSFULLY SAVED!');");
              out.println("location='Newcase1';");
              out.println("</script>");
              con.close();
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      LOGGER.info("Error At SavePlanning =" + e.getMessage());
    } finally {
      try {
        LoginDAO.getConnectionDetails().close();
      } catch (ClassNotFoundException e) {
        LOGGER.info("@Exception=" + e);
      } catch (SQLException e) {
        LOGGER.info("@Exception=" + e);
      } finally {
        if (rs != null)
          try {
            rs.close();
          } catch (Exception exception) {
          
          } finally {
            rs = null;
          }  
        if (st3 != null)
          try {
            st3.close();
          } catch (Exception exception) {
          
          } finally {
            st3 = null;
          }  
        if (pstmt != null)
          try {
            pstmt.close();
          } catch (Exception exception) {
          
          } finally {
            pstmt = null;
          }  
        if (pstmt1 != null)
          try {
            pstmt1.close();
          } catch (Exception exception) {
          
          } finally {
            pstmt1 = null;
          }  
        if (preparedStatement != null)
          try {
            preparedStatement.close();
          } catch (Exception exception) {
          
          } finally {
            preparedStatement = null;
          }  
        if (preparedStatement1 != null)
          try {
            preparedStatement1.close();
          } catch (Exception exception) {
          
          } finally {
            preparedStatement1 = null;
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
  }
}
