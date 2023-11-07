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

@WebServlet({"/AccountHandler"})
public class AccountHandler extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(AccountHandler.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    PreparedStatement pstmt = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement pstmt3 = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    Connection con = null;
   
    int totalpaid = 0;
    int RNewAmount = 0;
    int dbRemainAmount = 0;
    int planAmount = 0;
    int dbTotalAmount = 0;
    int dbsubmitted_amount = 0;
    int dbpaid_amount = 0;
    int remainingamt = 0;
    int finaltotal = 0;
    String dbmod = "";
    String dbcrm = "";
    String dbdoctor_name = "";
    String dbpatient_name = "";
    String dbinvoice_no = "";
    String dbinvoice_date = "";
    String user = "";
    HttpSession session = request.getSession();

    user = (String) session.getAttribute("userid");
    
    String crm = (request.getParameter("CRM") == null) ? "" : request.getParameter("CRM");
    String DoctorName = (request.getParameter("DoctorName") == null) ? "" : request.getParameter("DoctorName");
    String PatientName = (request.getParameter("PatientName") == null) ? "" : request.getParameter("PatientName");
    String caseid = (request.getParameter("caseid") == null) ? "" : request.getParameter("caseid");
    String remarks = (request.getParameter("remarks") == null) ? "" : request.getParameter("remarks");
    String query = (request.getParameter("query") == null) ? "" : request.getParameter("query");
    int NewAmount = Integer.parseInt((request.getParameter("NewAmount") == null) ? "0" : request.getParameter("NewAmount"));
    int RevokeAmount = Integer.parseInt((request.getParameter("RevAmount") == null) ? "0" : request.getParameter("RevAmount"));

 try {
      con = LoginDAO.getConnectionDetails();
     
      if (query.equals("newamt")) {
        String sql = "SELECT case_id FROM  cc_crm where case_id='" + caseid + "'";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rst = stmt.executeQuery();
        if (rst.next()) {
          pstmt1 = con.prepareStatement(" select submitted_amount,payment,remain_amount,invoice_no,invoice_date from account where case_id=? order by account_id desc ");
          pstmt1.setString(1, caseid);
          rs1 = pstmt1.executeQuery();
          if (rs1.next()) {
            totalpaid = rs1.getInt("submitted_amount");
            dbTotalAmount = rs1.getInt("payment");
            dbRemainAmount = rs1.getInt("remain_amount");
            dbinvoice_no = rs1.getString("invoice_no");
            dbinvoice_date = rs1.getString("invoice_date");
            
            if(dbinvoice_date!=null) {
      	    	 dbinvoice_date = rs1.getString("invoice_date");
      	    }
            
          } 
          
          RNewAmount = NewAmount - totalpaid;
          PreparedStatement pstmt01 = con.prepareStatement(" INSERT INTO account_change(case_id,new_amount,previous_amount,reason,user,date)VALUES(?,?,?,?,?,now()) ");
          pstmt01.setString(1, caseid);
          pstmt01.setInt(2, NewAmount);
          pstmt01.setInt(3, dbTotalAmount);
          pstmt01.setString(4, remarks);
          pstmt01.setString(5, user);
          pstmt01.executeUpdate();
          
          pstmt = con.prepareStatement(" INSERT INTO account (crm, doctor_name,patient_name,payment,remain_amount,case_id,submitted_amount,date,invoice_no,invoice_date,user)  values(?,?,?,?,?,?,?,now(),?,?,?) ");
        
          pstmt.setString(1, crm);
          pstmt.setString(2, DoctorName);
          pstmt.setString(3, PatientName);
          pstmt.setInt(4, NewAmount);
          pstmt.setInt(5, RNewAmount);
          pstmt.setString(6, caseid);
          pstmt.setInt(7, totalpaid);
          pstmt.setString(8, dbinvoice_no);
          pstmt.setString(9, dbinvoice_date);
          pstmt.setString(10, user);
          int rowaffected = pstmt.executeUpdate();
          if (rowaffected > 0) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Amount Updated Successfully!');");
            out.println("location='Case_Stage';");
            out.println("</script>");
            con.close();
          } 
        } else {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Case id " + caseid + " does not exists!');");
          out.println("location='accountupdateamount.jsp';");
          out.println("</script>");
        } 
      } 
    
	  
      if (query.equals("revokeamt")) {
	  
        pstmt2 = con.prepareStatement(" select remain_amount,payment,submitted_amount,payment_mode,crm,doctor_name,patient_name,user"
        		+ " from account where case_id=? order by account_id desc limit 1");
        pstmt2.setString(1, caseid);
        rs2 = pstmt2.executeQuery();
        if (rs2.next()) {
          dbRemainAmount = rs2.getInt("remain_amount");
          dbTotalAmount = rs2.getInt("payment");
          dbsubmitted_amount = rs2.getInt("submitted_amount");
          dbmod = rs2.getString("payment_mode");
          dbcrm = rs2.getString("crm");
          dbdoctor_name = rs2.getString("doctor_name");
          dbpatient_name = rs2.getString("patient_name");
          user = rs2.getString("user");
        } 
        
        int paidAmount = dbTotalAmount - dbRemainAmount;
        boolean amountCheck = (RevokeAmount <= paidAmount);
        if (!amountCheck) {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Revoke amount is higher than paid amount!');");
          out.println("window.location='AccountRevokedataShow?id=" + caseid + "';");
          out.println("</script>");
        } else {
          remainingamt = dbRemainAmount + RevokeAmount;
          pstmt3 = con.prepareStatement(" insert into account(case_id,payment,remain_amount,remarks,revoke_amount,submitted_amount,user,date) values(?,?,?,?,?,?,?,now()) ");
          pstmt3.setString(1, caseid);
          pstmt3.setInt(2, dbTotalAmount);
          pstmt3.setInt(3, remainingamt);
          pstmt3.setString(4, remarks);
          pstmt3.setInt(5, RevokeAmount);
          pstmt3.setInt(6, dbsubmitted_amount - RevokeAmount);
          pstmt3.setString(7, user);
          int rowaffected = pstmt3.executeUpdate();
          if (rowaffected > 0) {
            out.println("<script type='text/javascript'>");
            out.println("alert('Amount Revoked Successfully!');");
            String value = "AccountRevokedataShow?id=" + caseid;
            out.println("location='" + value + "';");
            out.println("</script>");
          } 
        } 
      } 
    } catch (Exception e) {
    	e.printStackTrace();
      LOGGER.info("Error At =" + e.getMessage());
    } finally {
      if (rs1 != null)
        try {
          rs1.close();
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
          rs1 = null;
        }  
      if (rs2 != null)
        try {
          rs2.close();
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
          rs2 = null;
        }  
      if (pstmt != null)
        try {
          pstmt.close();
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
          pstmt = null;
        }  
      if (pstmt1 != null)
        try {
          pstmt1.close();
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
          pstmt1 = null;
        }  
      if (pstmt2 != null)
        try {
          pstmt2.close();
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
          pstmt2 = null;
        }  
      if (pstmt3 != null)
        try {
          pstmt3.close();
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
          pstmt3 = null;
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
          con = null;
        }  
 
      out.flush();
      System.gc();
    } 
  }
}
