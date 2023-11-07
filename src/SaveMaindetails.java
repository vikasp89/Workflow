import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/SaveMaindetails"})
public class SaveMaindetails extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(SaveMaindetails.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    PreparedStatement pstmt = null;
    int Case_Id = Integer.parseInt(request.getParameter("cid"));
    ArrayList<Integer> cidarr = new ArrayList<>();
    int cid = 0;
    String Patient_Name = request.getParameter("pn");
    String Doctor_Name = request.getParameter("dn");
    String crm = request.getParameter("crm");
    String dno = request.getParameter("dno");
    String bgn = request.getParameter("bgn");
    String cat = request.getParameter("cat");
    String ua = request.getParameter("ua");
    String la = request.getParameter("la");
    String oth = request.getParameter("oth");
    HttpSession session = request.getSession();
    String userid = (String)session.getAttribute("userid");
    String address = "";
    String phoneNumber = "";
    String clinicName = "";
    String buyersAddress = "";
    String buyersClinicName = "";
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
    for (int i = 0; i < list2.size(); i++) {
      if (((String)list2.get(i)).length() > 0) {
        String str = ((String)list2.get(i)).substring(0, ((String)list2.get(i)).length() - 1);
        list2.set(i, str);
      } 
    } 
    Connection con = null;
    try {
      con = LoginDAO.getConnectionDetails();
      Class.forName("com.mysql.jdbc.Driver");
      Statement st = null;
      Statement st2 = null;
      st = con.createStatement();
      st2 = con.createStatement();
      String sql1 = "SELECT Case_Id FROM patient_details";
      ResultSet rs1 = st.executeQuery(sql1);
      while (rs1.next()) {
        cid = rs1.getInt("Case_Id");
        cidarr.add(Integer.valueOf(cid));
      } 
      if (cidarr.contains(Integer.valueOf(Case_Id))) {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('CASE ID ALREADY EXISTS!');");
        out.println("location='maindetails.jsp';");
        out.println("</script>");
      } else {
        String query = "INSERT INTO patient_details VALUES('" + Case_Id + "','" + Patient_Name + "','" + 
          Doctor_Name + "','" + (String)list2.get(2) + "','" + (String)list2.get(0) + "','" + (String)list2.get(1) + "',curdate(),curtime(),'" + crm + "','" + dno + "','" + (String)list2.get(3) + "','" + (String)list2.get(4) + "','" + bgn + "','" + cat + "'," + 
          "'" + ua + "','" + la + "','" + oth + "'," + 
          "'" + userid + "','" + userid + "',now(),'created','Intro','initial stage','N',now(),'N'," + 
          "'','','','','','','" + Doctor_Name + "','','" + Case_Id + "','gp','','','','','','','','  ','','','','','','  ','','',' ',now(),' ','','','',''," + 
          "now(),'','','','','','','','','','',now(),'','','','',now(),'',now(),' ','','','100','100','100','')";
        st.executeUpdate(query);
        String query2 = "insert into newcase values('" + Case_Id + "','" + Patient_Name + "',now(),'" + Doctor_Name + "','" + (String)list2.get(2) + "','y')";
        st2.executeUpdate(query2);
        PreparedStatement ps = null;
        ps = con.prepareStatement("insert into decision_history(decision, Remarks, date_time, stage, UserId, caseid) values('Intro','',now(),'initial stage','" + userid + "','" + Case_Id + "')");
        ps.execute();
      } 
      out.println("<script type=\"text/javascript\">");
      out.println("alert('SUCCESSFULLY SAVED!');");
      out.println("location='Dashboard.jsp';");
      out.println("</script>");
      con.close();
    } catch (Exception e) {
      LOGGER.info("Error At AddComment=" + e.getMessage());
    } 
  }
}
