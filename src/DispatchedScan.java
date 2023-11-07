import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/DispatchedScan"})
public class DispatchedScan extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  static final Logger LOGGER = LogManager.getLogger(DispatchedScan.class);
  
  private static final String SQL_INSERT = "INSERT INTO dispatched_scan (case_id,doctor_name,patient_name,crm,dispatch,dispatch_no,tracking_id,delivery_nn,mode_of_dispatch,decesion,remark,no_of_aligners,address,default_address,confirm_status,date,upper_aligner_from,upper_aligner_to,lower_aligner_from,lower_aligner_to,upper_att,lower_att,phone,location,city,pincode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?)";
  
  private static final String SQL_INSERTSTR = "INSERT INTO dispatched_scan (case_id,doctor_name,patient_name,crm,tracking_id,delivery_nn,mode_of_dispatch,decesion,remark,address,default_starterkit,confirm_status,date,phone,location,city,pincode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    String UserId = (String)session.getAttribute("userid");
    String crm = request.getParameter("crm");
    String doctor_name = request.getParameter("doctor_name");
    String patient_name = request.getParameter("patient_name");
    String case_id = request.getParameter("case_id");
    String Sr_no = request.getParameter("Sr_no");
    String item = request.getParameter("item");
    String dispatch = request.getParameter("dispatch");
    String dispatch_no = request.getParameter("dispatch_no");
    String tracking_id = request.getParameter("tracking_id");
    String delivery_nn = request.getParameter("delivery_nn");
    String Type_of_dispatch = request.getParameter("Type_of_dispatch");
    String decesion = request.getParameter("decesion");
    String remark = request.getParameter("remark");
    String query = (request.getParameter("query") == null) ? "" : request.getParameter("query");
    String no_of_aligners = request.getParameter("noofaligner");
    String mode_of_dispatch = request.getParameter("modeofdispatch");
    String upper_aligner_from = request.getParameter("upper_aligner_from");
    String upper_aligner_to = request.getParameter("upper_aligner_to");
    String lower_aligner_from = request.getParameter("lower_aligner_from");
    String lower_aligner_to = request.getParameter("lower_aligner_to");
    String upper_att = request.getParameter("upper_att");
    String lower_att = request.getParameter("lower_att");
    String default_address = (request.getParameter("default_address") == null) ? "" : request.getParameter("default_address");
    String address = "";
    String phone = "";
    String location = "";
    String city = "";
    String pincode = "";
    String starterkit1 = (request.getParameter("starterkit1") == null) ? "" : request.getParameter("starterkit1");
    String starterkit2 = (request.getParameter("starterkit2") == null) ? "" : request.getParameter("starterkit2");
    String starterkit3 = (request.getParameter("starterkit3") == null) ? "" : request.getParameter("starterkit3");
    String starterkit4 = (request.getParameter("starterkit4") == null) ? "" : request.getParameter("starterkit4");
    String starterkit5 = (request.getParameter("starterkit5") == null) ? "" : request.getParameter("starterkit5");
    String batch1 = (request.getParameter("batch1") == null) ? "" : request.getParameter("batch1");
    String batch2 = (request.getParameter("batch2") == null) ? "" : request.getParameter("batch2");
    String batch3 = (request.getParameter("batch3") == null) ? "" : request.getParameter("batch3");
    String batch4 = (request.getParameter("batch4") == null) ? "" : request.getParameter("batch4");
    String batch5 = (request.getParameter("batch5") == null) ? "" : request.getParameter("batch5");
    if (starterkit1.equals("1") || batch1.equals("1")) {
      address = (request.getParameter("address1") == null) ? "" : request.getParameter("address1");
      phone = (request.getParameter("phone1") == null) ? "" : request.getParameter("phone1");
      location = (request.getParameter("location1") == null) ? "" : request.getParameter("location1");
      city = (request.getParameter("city1") == null) ? "" : request.getParameter("city1");
      pincode = (request.getParameter("pincode1") == null) ? "" : request.getParameter("pincode1");
      default_address = batch1;
    } 
    if (starterkit2.equals("2") || batch2.equals("2")) {
      address = (request.getParameter("address2") == null) ? "" : request.getParameter("address2");
      phone = (request.getParameter("phone2") == null) ? "" : request.getParameter("phone2");
      location = (request.getParameter("location2") == null) ? "" : request.getParameter("location2");
      city = (request.getParameter("city2") == null) ? "" : request.getParameter("city2");
      pincode = (request.getParameter("pincode2") == null) ? "" : request.getParameter("pincode2");
      default_address = batch2;
    } 
    if (starterkit3.equals("3") || batch3.equals("3")) {
      address = (request.getParameter("address3") == null) ? "" : request.getParameter("address3");
      phone = (request.getParameter("phone3") == null) ? "" : request.getParameter("phone3");
      location = (request.getParameter("location3") == null) ? "" : request.getParameter("location3");
      city = (request.getParameter("city3") == null) ? "" : request.getParameter("city3");
      pincode = (request.getParameter("pincode13") == null) ? "" : request.getParameter("pincode3");
      default_address = batch3;
    } 
    if (starterkit4.equals("4") || batch4.equals("4")) {
      address = (request.getParameter("address4") == null) ? "" : request.getParameter("address4");
      phone = (request.getParameter("phone4") == null) ? "" : request.getParameter("phone4");
      location = (request.getParameter("location4") == null) ? "" : request.getParameter("location4");
      city = (request.getParameter("city4") == null) ? "" : request.getParameter("city4");
      pincode = (request.getParameter("pincode4") == null) ? "" : request.getParameter("pincode4");
      default_address = batch4;
    } 
    if (starterkit5.equals("5") || batch5.equals("5")) {
      address = (request.getParameter("address5") == null) ? "" : request.getParameter("address5");
      phone = (request.getParameter("phone5") == null) ? "" : request.getParameter("phone5");
      location = (request.getParameter("location5") == null) ? "" : request.getParameter("location5");
      city = (request.getParameter("city5") == null) ? "" : request.getParameter("city5");
      pincode = (request.getParameter("pincode5") == null) ? "" : request.getParameter("pincode5");
      default_address = batch5;
    } 
    String confirm_status = request.getParameter("confirm_status");
    Connection con = null;
    Connection con1 = null;
    Connection con2 = null;
    Connection con3 = null;
    Connection con4 = null;
    Connection con5 = null;
    String sql1 = "";
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement preparedStatement1 = null;
    Statement st2 = null;
    Statement st3 = null;
    try {
      con = LoginDAO.getConnectionDetails();
      con1 = LoginDAO.getConnectionDetails();
      con2 = LoginDAO.getConnectionDetails();
      con3 = LoginDAO.getConnectionDetails();
      con4 = LoginDAO.getConnectionDetails();
      con5 = LoginDAO.getConnectionDetails();
      st3 = con3.createStatement();
      st2 = con5.createStatement();
      if (query.equals("strdispatch")) {
        pstmt1 = con4.prepareStatement("INSERT INTO dispatched_scan (case_id,doctor_name,patient_name,crm,tracking_id,delivery_nn,mode_of_dispatch,decesion,remark,address,default_starterkit,confirm_status,date,phone,location,city,pincode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)");
        pstmt1.setString(1, case_id);
        pstmt1.setString(2, doctor_name);
        pstmt1.setString(3, patient_name);
        pstmt1.setString(4, crm);
        pstmt1.setString(5, tracking_id);
        pstmt1.setString(6, delivery_nn);
        pstmt1.setString(7, mode_of_dispatch);
        pstmt1.setString(8, decesion);
        pstmt1.setString(9, remark);
        pstmt1.setString(10, address);
        pstmt1.setString(11, default_address);
        pstmt1.setString(12, confirm_status);
        pstmt1.setString(13, phone);
        pstmt1.setString(14, location);
        pstmt1.setString(15, city);
        pstmt1.setString(16, pincode);
        System.out.println("pstmt1 :: " + pstmt1.toString());
        int row = pstmt1.executeUpdate();
        if (row > 0) {
          if (decesion.equals("DPHSTRKIT")) {
            sql1 = " update cc_crm set remark='" + remark + "',starter_case_stage='" + decesion + "',dispatchstrkit=now() where Case_Id='" + case_id + "' ";
          } else if (decesion.equals("PCKSTRKITCOR")) {
            sql1 = " update cc_crm set remark='" + remark + "',starter_case_stage='" + decesion + "',pckstrkitcor_at=now()  where Case_Id='" + case_id + "' ";
          } 
          pstmt2 = con5.prepareStatement(sql1);
          int i = pstmt2.executeUpdate();
          if (i > 0) {
            int j = st2.executeUpdate(" INSERT INTO decision_history(decision, Remarks, date_time, stage, UserId, caseid) values('" + decesion + "','" + remark + "' ,now(),'" + decesion + "','" + UserId + "','" + case_id + "')");
            System.out.println("j== :: " + j);
            if (j > 0) {
              out.println("<script type=\"text/javascript\">");
              out.println("alert('SUCCESSFULLY SAVED!');");
              out.println("location='Newcase1';");
              out.println("</script>");
            } 
          } 
        } 
      } else if (query.equals("dispatch")) {
        PreparedStatement pstmt = con.prepareStatement(" INSERT INTO dispatched_scan (case_id,doctor_name,patient_name,crm,no_of_aligners,delivery_nn,tracking_id,mode_of_dispatch,date) VALUES (?,?,?,?,?,?,?,?,now())");
        pstmt.setString(1, case_id);
        pstmt.setString(2, doctor_name);
        pstmt.setString(3, patient_name);
        pstmt.setString(4, crm);
        pstmt.setString(5, no_of_aligners);
        pstmt.setString(6, delivery_nn);
        pstmt.setString(7, tracking_id);
        pstmt.setString(8, mode_of_dispatch);
        int row = pstmt.executeUpdate();
        if (row > 0) {
          String id = "Multifrm?caseId=" + case_id + "&crm=" + crm + "&patient_Name=" + patient_name + "&cdoc=" + doctor_name;
          out.println("<script type=\"text/javascript\">");
          out.println("alert('SUCCESSFULLY SAVED!');");
          out.println("location='" + id + "';");
          out.println("</script>");
        } 
      } else {
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO dispatched_scan (case_id,doctor_name,patient_name,crm,dispatch,dispatch_no,tracking_id,delivery_nn,mode_of_dispatch,decesion,remark,no_of_aligners,address,default_address,confirm_status,date,upper_aligner_from,upper_aligner_to,lower_aligner_from,lower_aligner_to,upper_att,lower_att,phone,location,city,pincode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, case_id);
        preparedStatement.setString(2, doctor_name);
        preparedStatement.setString(3, patient_name);
        preparedStatement.setString(4, crm);
        preparedStatement.setString(5, dispatch);
        preparedStatement.setString(6, dispatch_no);
        preparedStatement.setString(7, tracking_id);
        preparedStatement.setString(8, delivery_nn);
        preparedStatement.setString(9, mode_of_dispatch);
        preparedStatement.setString(10, decesion);
        preparedStatement.setString(11, remark);
        preparedStatement.setString(12, no_of_aligners);
        preparedStatement.setString(13, address);
        preparedStatement.setString(14, default_address);
        preparedStatement.setString(15, confirm_status);
        preparedStatement.setString(16, upper_aligner_from);
        preparedStatement.setString(17, upper_aligner_to);
        preparedStatement.setString(18, lower_aligner_from);
        preparedStatement.setString(19, lower_aligner_to);
        preparedStatement.setString(20, upper_att);
        preparedStatement.setString(21, lower_att);
        preparedStatement.setString(22, phone);
        preparedStatement.setString(23, location);
        preparedStatement.setString(24, city);
        preparedStatement.setString(25, pincode);
        System.out.println("Batch :: " + preparedStatement.toString());
        int row = preparedStatement.executeUpdate();
        if (row > 0) {
          if (decesion.equals("Starter Kit Dispatch")) {
            sql1 = " update cc_crm set remark='" + remark + "',starter_case_stage='" + decesion + "'where Case_Id='" + case_id + "' ";
          } else if (decesion.equals("PCKCOR")) {
            sql1 = " update cc_crm set remark='" + remark + "',case_stage='" + decesion + "',pckcor_at=now()  where Case_Id='" + case_id + "' ";
          } else {
            sql1 = " update cc_crm set remark='" + remark + "',case_stage='" + decesion + "'  where Case_Id='" + case_id + "' ";
          } 
          preparedStatement1 = con1.prepareStatement(sql1);
          int i = preparedStatement1.executeUpdate();
          if (i > 0) {
            String query3 = "INSERT INTO decision_history(decision,Remarks,date_time,stage,UserId,caseid) values('" + decesion + "','" + remark + "' ,now(),'" + decesion + "','" + UserId + "','" + case_id + "')";
            int j = st3.executeUpdate(query3);
            String redirect = "localhost:8080/Registeration/Loginfetchedit?cid=" + case_id;
            if (j > 0) {
              out.println("<script type=\"text/javascript\">");
              out.println("alert('SUCCESSFULLY SAVED!');");
              out.println("location='Case_Stage';");
              out.println("</script>");
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      LOGGER.info("Error At DispatchedScan=" + e.getMessage());
    } finally {
      if (pstmt1 != null)
        try {
          pstmt1.close();
        } catch (Exception e) {
          System.out.println("pstmt1 str catch me :: " + e.getMessage());
        } finally {
          pstmt1 = null;
        }  
      if (pstmt2 != null)
        try {
          pstmt2.close();
        } catch (Exception e) {
          System.out.println("pstmt2 str catch me :: " + e.getMessage());
        } finally {
          pstmt2 = null;
        }  
      if (preparedStatement1 != null)
        try {
          preparedStatement1.close();
        } catch (Exception e) {
          System.out.println("preparedStatement1 catch me :: " + e.getMessage());
        } finally {
          preparedStatement1 = null;
        }  
      if (st2 != null)
        try {
          st2.close();
        } catch (Exception e) {
          System.out.println("st2 catch me :: " + e.getMessage());
        } finally {
          st2 = null;
        }  
      if (st3 != null)
        try {
          st3.close();
        } catch (Exception e) {
          System.out.println("st3 catch me :: " + e.getMessage());
        } finally {
          st3 = null;
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception exception) {
        
        } finally {
          con = null;
        }  
      if (con1 != null)
        try {
          con1.close();
        } catch (Exception exception) {
        
        } finally {
          con1 = null;
        }  
      if (con2 != null)
        try {
          con2.close();
        } catch (Exception exception) {
        
        } finally {
          con2 = null;
        }  
      if (con3 != null)
        try {
          con3.close();
        } catch (Exception exception) {
        
        } finally {
          con3 = null;
        }  
      if (con4 != null)
        try {
          con4.close();
        } catch (Exception exception) {
        
        } finally {
          con4 = null;
        }  
      if (con5 != null)
        try {
          con5.close();
        } catch (Exception exception) {
        
        } finally {
          con5 = null;
        }  
    } 
  }
}
