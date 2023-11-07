import com.vo.AccountVo;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"/AccountRevokedataShow"})
public class AccountRevokedataShow extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  Connection con = null;
	PreparedStatement pstmt = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ResultSet rs0 = null;
    PrintWriter out = response.getWriter();
    String crm = "";
    String case_id = "";
    String doctor_name = "";
    String patient_name = "";
    String payment_mode = "";
    String payment = "";
    String remain_amount = "";
    String paid_amount = "";
    String user = "";
    case_id = request.getParameter("account_number");
    String account = request.getParameter("id");
    String sql = "";
    String pn = "";
    String dr = "";
    String crm1 = "";
    Double total = Double.valueOf(0.0D);
    int ramt = 0;
    int finalramt = 0;
    String query = (request.getParameter("query") == null) ? "" : request.getParameter("query");
    String cid = (request.getParameter("caseId") == null) ? "" : request.getParameter("caseId");
    if (case_id == null)
      case_id = account; 
    try {
      con = LoginDAO.getConnectionDetails();
      HttpSession session = request.getSession();
      if (query.equals("fetchdata")) {
        pstmt1 = con.prepareStatement(" select * from cc_crm where case_id=?");
        pstmt1.setString(1, cid);
        rs = pstmt1.executeQuery();
        if (rs.next()) {
          pn = rs.getString("Doctor_Name");
          dr = rs.getString("Patient_Name");
          crm1 = rs.getString("crm_name");
          total = Double.valueOf(rs.getDouble("total_amount"));
        } 
        out.println(String.valueOf(pn) + "," + dr + "," + crm1 + "," + total);
      } else {
        Statement st1 = null;
        st1 = con.createStatement();
        List<AccountVo> list = new ArrayList<>();
        List<AccountVo> list1 = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        sql = " select * from account where case_id='" + case_id + "' ";
        rs0 = st1.executeQuery(sql);
        if (!rs0.isBeforeFirst()) {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Case Id " + case_id + " Not Exist!');");
          out.println("location='SearchAccountNumber';");
          out.println("</script>");
        } 
        while (rs0.next()) {
          AccountVo accountvo = new AccountVo();
          accountvo.setAccount_id(rs0.getString("account_id"));
          accountvo.setDate(rs0.getString("date"));
          accountvo.setPaidAmount(rs0.getString("paid_amount"));
          accountvo.setRemainAmount(rs0.getString("remain_amount"));
          accountvo.setSubmitted_amount(rs0.getString("submitted_amount"));
          accountvo.setTotal(rs0.getString("payment"));
          accountvo.setModepayment(rs0.getString("payment_mode"));
          accountvo.setRevoke_amount(rs0.getString("revoke_amount"));
          accountvo.setRemarks(rs0.getString("remarks"));
          accountvo.setUser(rs0.getString("user"));
          accountvo.setInvoice_no(rs0.getString("invoice_no"));
          accountvo.setInvoice_date(rs0.getString("invoice_date"));
          list.add(accountvo);
          map.put("case_id", rs0.getString("case_id"));
          map.put("crm", rs0.getString("crm"));
          map.put("doctor_name", rs0.getString("doctor_name"));
          map.put("patient_name", rs0.getString("patient_name"));
          map.put("payment", rs0.getString("payment"));
          map.put("remain_amount", rs0.getString("remain_amount"));
          map.put("submitted_amount", rs0.getString("submitted_amount"));
          map.put("paid_amount", rs0.getString("paid_amount"));
          map.put("user", rs0.getString("user"));
          map.put("invoice_no", rs0.getString("invoice_no"));
          map.put("invoice_date", rs0.getString("invoice_date"));
          session.setAttribute("AccountVoList", list);
          session.setAttribute("crm", map.get("crm"));
          session.setAttribute("finalramt", Integer.valueOf(finalramt));
          session.setAttribute("case_id", map.get("case_id"));
          session.setAttribute("doctor_name", map.get("doctor_name"));
          session.setAttribute("patient_name", map.get("patient_name"));
          session.setAttribute("payment", map.get("payment"));
          session.setAttribute("remain_amount", map.get("remain_amount"));
          session.setAttribute("submitted_amount", map.get("submitted_amount"));
          session.setAttribute("aa",map.get("paid_amount"));
          session.setAttribute("invoice_no",map.get("invoice_no"));
          session.setAttribute("invoice_date",map.get("invoice_date"));
        } 
        
        ps = con.prepareStatement(" select * from account_change where case_id='" + case_id + "' ");
        rs = ps.executeQuery();
        while (rs.next()) {
          AccountVo ac = new AccountVo();
          ac.setCaseid(rs.getString("case_id"));
          ac.setPrevious_amount(rs.getString("previous_amount"));
          ac.setNew_amount(rs.getString("new_amount"));
          ac.setRemarks(rs.getString("reason"));
          ac.setDate(rs.getString("date"));
          ac.setUser(rs.getString("user"));
          list1.add(ac);
          session.setAttribute("acrvk", list1);
        } 
        out.println("<script type=\"text/javascript\">");
        out.println("location='accountrevokeamount.jsp';");
        out.println("</script>");
      } 
    } catch (Exception e) {
    e.printStackTrace();
    } 
    finally {
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
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
