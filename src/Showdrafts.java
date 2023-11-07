import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/Showdrafts"})
public class Showdrafts extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Showdrafts.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Connection con = null;
    PreparedStatement ps = null;
    String sql = null;
    String address = "";
    String phoneNumber = "";
    String clinicName = "";
    String buyersAddress = "";
    String buyersClinicName = "";
    int addressid = 0;
    Statement st = null;
    String logusername = request.getParameter("logusername");
    String caseId = request.getParameter("cid");
    request.setAttribute("abc", caseId);
    request.setAttribute("def", logusername);
    String Patient_Name = request.getParameter("pn");
    String Doctor_Name = request.getParameter("dn");
    String clinicValue = request.getParameter("clinicName");
    String addressValue = request.getParameter("clinicAddress");
    String phoneNumberValue = request.getParameter("phoneNumber");
    String crm = request.getParameter("crm");
    String dno = request.getParameter("dno");
    String buyerClinicNameValue = request.getParameter("bcn1");
    String buyerAddressValue = request.getParameter("badd1");
    String bgn = request.getParameter("bgn");
    String cat = request.getParameter("cat");
    String ua = request.getParameter("ua");
    String la = request.getParameter("la");
    String oth = request.getParameter("oth");
    Enumeration<String> list = request.getParameterNames();
    while (list.hasMoreElements()) {
      String paramName = list.nextElement();
      if (paramName.contains("addr")) {
        String[] paramValues = request.getParameterValues(paramName);
        String value = paramValues[0];
        if (value != null && value != "")
          address = String.valueOf(address) + value + "#"; 
        continue;
      } 
      if (paramName.contains("pno")) {
        String[] paramValues = request.getParameterValues(paramName);
        String value = paramValues[0];
        if (value != null && value != "")
          phoneNumber = String.valueOf(phoneNumber) + value + "#"; 
        continue;
      } 
      if (paramName.contains("cni")) {
        String[] paramValues = request.getParameterValues(paramName);
        String value = paramValues[0];
        if (value != null && value != "")
          clinicName = String.valueOf(clinicName) + value + "#"; 
        continue;
      } 
      if (paramName.contains("bcn")) {
        String[] paramValues = request.getParameterValues(paramName);
        String value = paramValues[0];
        if (value != null && value != "")
          buyersClinicName = String.valueOf(buyersClinicName) + value + "#"; 
        continue;
      } 
      if (paramName.contains("badd")) {
        String[] paramValues = request.getParameterValues(paramName);
        String value = paramValues[0];
        if (value != null && value != "")
          buyersAddress = String.valueOf(buyersAddress) + value + "#"; 
      } 
    } 
    ArrayList<String> list2 = new ArrayList<>();
    list2.add(address);
    list2.add(phoneNumber);
    list2.add(clinicName);
    list2.add(buyersClinicName);
    list2.add(buyersAddress);
    int i;
    for (i = 0; i < list2.size(); i++) {
      if (((String)list2.get(i)).length() > 0) {
        String str = ((String)list2.get(i)).substring(0, ((String)list2.get(i)).length() - 1);
        list2.set(i, str);
      } 
    } 
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = LoginDAO.getConnectionDetails();
      sql = " UPDATE CC_CRM SET Patient_Name=?, Doctor_Name=?, clinic_Name=?, address=?, phone=?,  crm_name=?, delivery_note_no=?, bclinic=?,badd=?, bgst_no=?, category=?, upper_aligner=?, lower_aligner=?, others=?,plan_date=curdate(), plan_time=curtime() where Case_Id=? ";
      ps = con.prepareStatement(sql);
      ps.setString(1, Patient_Name);
      ps.setString(2, Doctor_Name);
      ps.setString(3, list2.get(2));
      ps.setString(4, list2.get(0));
      ps.setString(5, list2.get(1));
      ps.setString(6, crm);
      ps.setString(7, dno);
      ps.setString(8, list2.get(3));
      ps.setString(9, list2.get(4));
      ps.setString(10, bgn);
      ps.setString(11, cat);
      ps.setString(12, ua);
      ps.setString(13, la);
      ps.setString(14, oth);
      ps.setString(15, caseId);
      ps.executeUpdate();
      i = ps.executeUpdate();
      if (i > 0) {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('" + caseId + " has been successfully Updated!');");
        out.println("location='Dashboard.jsp';");
        out.println("</script>");
      } else {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('ERROR OCCUR !');");
        out.println("location='Showdrafts.jsp';");
        out.println("</script>");
      } 
    } catch (Exception e) {
      LOGGER.info("Error At Searchcase=" + e.getMessage());
    } finally {
      if (ps != null)
        try {
          ps.close();
        } catch (Exception exception) {
        
        } finally {
          ps = null;
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception exception) {
        
        } finally {
          con = null;
        }  
      out.flush();
      System.gc();
    } 
  }
}
