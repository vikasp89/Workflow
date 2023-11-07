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

@WebServlet({"/AddCaseDetails"})
public class AddCaseDetails extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(AddCaseDetails.class);
  
  private static final long serialVersionUID = 1L;
    
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session.getAttribute("userid") == null || session.getAttribute("userid").equals(""))
      response.sendRedirect("login.jsp?msg=You are not logged in..!"); 
    PrintWriter out = response.getWriter();
    Connection con = null;
    PreparedStatement pstmt1 = null, apstmt = null;
    Statement stmt1 = null;
    ResultSet rs = null;
    long case_id = 0L;
    int rowaffected = 0;
    String others = null, lower_aligner = others, upper_aligner = lower_aligner, category = upper_aligner, bgst_no = category, delivery_note_no = bgst_no, bill_address = delivery_note_no, dispatch_address = bill_address, dispatch_crpt = dispatch_address, c_pkg = dispatch_crpt, kol = c_pkg, corporate_account = kol, t_account = corporate_account, batch5 = t_account, batch4 = batch5, batch3 = batch4, batch2 = batch3, batch1 = batch2, starterkit5 = batch1, starterkit4 = starterkit5, starterkit3 = starterkit4, starterkit2 = starterkit3, starterkit1 = starterkit2, location5 = starterkit1, location4 = location5, location3 = location4, location2 = location3, location1 = location2, city5 = location1, city4 = city5, city3 = city4;
    String city2 = city3, city1 = city2, phone5 = city1, phone4 = phone5, phone3 = phone4, phone2 = phone3, phone1 = phone2, pincode5 = phone1, pincode4 = pincode5, pincode3 = pincode4, pincode2 = pincode3, pincode1 = pincode2, address5 = pincode1;
    String address4 = address5, address3 = address4, address2 = address3, address1 = address2, existing_flag = address1, remarks = existing_flag, ppf = remarks, simulation_shown = ppf, SKit = simulation_shown, photograph = SKit, tor = photograph, patient_name = tor, tre_octor = patient_name, crm = tre_octor, reg_doctor = crm, scan_impression = reg_doctor, Existing = scan_impression, user_id = Existing,patient_email,treating_dr_email;
    scan_impression = (request.getParameter("Impression") == null) ? "" : request.getParameter("Impression");
    reg_doctor = (request.getParameter("RDoctor") == null) ? "" : request.getParameter("RDoctor").trim();
    crm = (request.getParameter("crm") == null) ? "" : request.getParameter("crm").trim();
    tre_octor = (request.getParameter("CDoctor") == null) ? "" : request.getParameter("CDoctor").trim();
    treating_dr_email = (request.getParameter("treating_dr_email") == null) ? "" : request.getParameter("treating_dr_email").trim();
    patient_name = (request.getParameter("PATIENT") == null) ? "" : request.getParameter("PATIENT").trim();
    patient_email = (request.getParameter("patient_email") == null) ? "" : request.getParameter("patient_email").trim();
    tor = (request.getParameter("TRequest") == null) ? "" : request.getParameter("TRequest");
    photograph = (request.getParameter("Photograph") == null) ? "" : request.getParameter("Photograph");
    SKit = (request.getParameter("SKit") == null) ? "" : request.getParameter("SKit");
    simulation_shown = (request.getParameter("Shown") == null) ? "" : request.getParameter("Shown");
    ppf = (request.getParameter("PPF") == null) ? "" : request.getParameter("PPF");
    address1 = (request.getParameter("address1") == null) ? "" : request.getParameter("address1").trim();
    address2 = (request.getParameter("address2") == null) ? "" : request.getParameter("address2").trim();
    address3 = (request.getParameter("address3") == null) ? "" : request.getParameter("address3").trim();
    address4 = (request.getParameter("address4") == null) ? "" : request.getParameter("address4").trim();
    address5 = (request.getParameter("address5") == null) ? "" : request.getParameter("address5").trim();
    pincode1 = (request.getParameter("pincode1") == null) ? "" : request.getParameter("pincode1").trim();
    pincode2 = (request.getParameter("pincode2") == null) ? "" : request.getParameter("pincode2").trim();
    pincode3 = (request.getParameter("pincode3") == null) ? "" : request.getParameter("pincode3").trim();
    pincode4 = (request.getParameter("pincode4") == null) ? "" : request.getParameter("pincode4").trim();
    pincode5 = (request.getParameter("pincode5") == null) ? "" : request.getParameter("pincode5").trim();
    phone1 = (request.getParameter("phone1") == null) ? "" : request.getParameter("phone1").trim();
    phone2 = (request.getParameter("phone2") == null) ? "" : request.getParameter("phone2").trim();
    phone3 = (request.getParameter("phone3") == null) ? "" : request.getParameter("phone3").trim();
    phone4 = (request.getParameter("phone4") == null) ? "" : request.getParameter("phone4").trim();
    phone5 = (request.getParameter("phone5") == null) ? "" : request.getParameter("phone5").trim();
    city1 = (request.getParameter("city1") == null) ? "" : request.getParameter("city1").trim();
    city2 = (request.getParameter("city2") == null) ? "" : request.getParameter("city2").trim();
    city3 = (request.getParameter("city3") == null) ? "" : request.getParameter("city3").trim();
    city4 = (request.getParameter("city4") == null) ? "" : request.getParameter("city4").trim();
    city5 = (request.getParameter("city5") == null) ? "" : request.getParameter("city5").trim();
    location1 = (request.getParameter("location1") == null) ? "" : request.getParameter("location1").trim();
    location2 = (request.getParameter("location2") == null) ? "" : request.getParameter("location2").trim();
    location3 = (request.getParameter("location3") == null) ? "" : request.getParameter("location3").trim();
    location4 = (request.getParameter("location4") == null) ? "" : request.getParameter("location4").trim();
    location5 = (request.getParameter("location5") == null) ? "" : request.getParameter("location5").trim();
    starterkit1 = (request.getParameter("starterkit1") == null) ? "" : request.getParameter("starterkit1").trim();
    starterkit2 = (request.getParameter("starterkit2") == null) ? "" : request.getParameter("starterkit2").trim();
    starterkit3 = (request.getParameter("starterkit3") == null) ? "" : request.getParameter("starterkit3").trim();
    starterkit4 = (request.getParameter("starterkit4") == null) ? "" : request.getParameter("starterkit4").trim();
    starterkit5 = (request.getParameter("starterkit5") == null) ? "" : request.getParameter("starterkit5").trim();
    batch1 = (request.getParameter("batch1") == null) ? "" : request.getParameter("batch1");
    batch2 = (request.getParameter("batch2") == null) ? "" : request.getParameter("batch2");
    batch3 = (request.getParameter("batch3") == null) ? "" : request.getParameter("batch3");
    batch4 = (request.getParameter("batch4") == null) ? "" : request.getParameter("batch4");
    batch5 = (request.getParameter("batch5") == null) ? "" : request.getParameter("batch5");
    remarks = (request.getParameter("remark") == null) ? "" : request.getParameter("remark").trim();
    user_id = (String)request.getSession().getAttribute("userid");
    Existing = request.getParameter("Existing");
    case_id = Long.parseLong(request.getParameter("case_id"));
    t_account = (request.getParameter("TAccount") == null) ? "" : request.getParameter("TAccount");
    corporate_account = (request.getParameter("CAccount") == null) ? "" : request.getParameter("CAccount");
    kol = (request.getParameter("KOL") == null) ? "" : request.getParameter("KOL");
    c_pkg = (request.getParameter("Cpkg") == null) ? "" : request.getParameter("Cpkg").trim();
    dispatch_crpt = (request.getParameter("Dispatchcr") == null) ? "" : request.getParameter("Dispatchcr").trim();
    dispatch_address = (request.getParameter("DispatchAdr") == null) ? "" : request.getParameter("DispatchAdr").trim();
    bill_address = (request.getParameter("BillAdr") == null) ? "" : request.getParameter("BillAdr").trim();
    delivery_note_no = (request.getParameter("delivery_note_no") == null) ? "" : request.getParameter("delivery_note_no").trim();
    bgst_no = (request.getParameter("bgst_no") == null) ? "" : request.getParameter("bgst_no").trim();
    category = (request.getParameter("category") == null) ? "" : request.getParameter("category").trim();
    upper_aligner = (request.getParameter("upper_aligner") == null) ? "" : request.getParameter("upper_aligner").trim();
    lower_aligner = (request.getParameter("lower_aligner") == null) ? "" : request.getParameter("lower_aligner").trim();
    others = (request.getParameter("others") == null) ? "" : request.getParameter("others").trim();
    int priority = Integer.parseInt((request.getParameter("Priority") == null) ? "0" : request.getParameter("Priority"));
    delivery_note_no = (request.getParameter("dno") == null) ? "" : request.getParameter("dno").trim();
    String starterkit_default = "";
    String default_address = "";
    if (batch1.equals("1"))
      default_address = "1"; 
    if (batch2.equals("2"))
      default_address = "2"; 
    if (batch3.equals("3"))
      default_address = "3"; 
    if (batch4.equals("4"))
      default_address = "4"; 
    if (batch5.equals("5"))
      default_address = "5"; 
    if (starterkit1.equals("1"))
      starterkit_default = "1"; 
    if (starterkit2.equals("2"))
      starterkit_default = "2"; 
    if (starterkit3.equals("3"))
      starterkit_default = "3"; 
    if (starterkit4.equals("4"))
      starterkit_default = "4"; 
    if (starterkit5.equals("5"))
      starterkit_default = "5"; 
    String phoneNumber = "";
    String clinicName = "";
    String buyersAddress = "";
    String buyersClinicName = "";
    Enumeration<String> list = request.getParameterNames();
    while (list.hasMoreElements()) {
      String paramName = list.nextElement();
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
    long cid = 0L;
    try {
      con = LoginDAO.getConnectionDetails();
      Statement stmt = con.createStatement();
      stmt1 = con.createStatement();
      rs = stmt.executeQuery("SELECT Case_Id FROM cc_crm");
      while (rs.next())
        cid = rs.getLong("Case_Id"); 
      if (cid == case_id) {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Case id : " + case_id + " already exists!');");
        out.println("location='Newcase1';");
        out.println("</script>");
      } else if (Existing.equals("New")) {
        String query = "INSERT INTO cc_crm (case_id,scan,clinic_Name,address1,address2,address3,address4,address5,pincode1,pincode2,pincode3,pincode4,pincode5,phone1,phone2,phone3,phone4,phone5,city,city2,city3,city4,city5,location,location2,location3,location4,location5,default_starterkit,default_address,registered_doctor,Doctor_Name,crm_name,type_request,p_graph,starter_kit,s_shown,ppf_fill,user_id,others,Patient_Name, t_account,corporate_account,kol,c_pkg,dispatch_crpt,dispatch_address,bill_address,delivery_note_no,bgst_no, bclinic,badd,category,upper_aligner,lower_aligner,priority,case_type,case_stage,starter_satus,CREATED_DATE,ini_at,plan_date,plan_time,patient_email,treating_dr_email)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'INI','N',now(),now(),curdate(),curtime(),?,?)";
        pstmt1 = con.prepareStatement(query);
        pstmt1.setLong(1, case_id);
        pstmt1.setString(2, scan_impression);
        pstmt1.setString(3, list2.get(1));
        pstmt1.setString(4, address1);
        pstmt1.setString(5, address2);
        pstmt1.setString(6, address3);
        pstmt1.setString(7, address4);
        pstmt1.setString(8, address5);
        pstmt1.setString(9, pincode1);
        pstmt1.setString(10, pincode2);
        pstmt1.setString(11, pincode3);
        pstmt1.setString(12, pincode4);
        pstmt1.setString(13, pincode5);
        pstmt1.setString(14, phone1);
        pstmt1.setString(15, phone2);
        pstmt1.setString(16, phone3);
        pstmt1.setString(17, phone4);
        pstmt1.setString(18, phone5);
        pstmt1.setString(19, city1);
        pstmt1.setString(20, city2);
        pstmt1.setString(21, city3);
        pstmt1.setString(22, city4);
        pstmt1.setString(23, city5);
        pstmt1.setString(24, location1);
        pstmt1.setString(25, location2);
        pstmt1.setString(26, location3);
        pstmt1.setString(27, location4);
        pstmt1.setString(28, location5);
        pstmt1.setString(29, starterkit_default);
        pstmt1.setString(30, default_address);
        pstmt1.setString(31, reg_doctor);
        pstmt1.setString(32, tre_octor);
        pstmt1.setString(33, crm);
        pstmt1.setString(34, tor);
        pstmt1.setString(35, photograph);
        pstmt1.setString(36, SKit);
        pstmt1.setString(37, simulation_shown);
        pstmt1.setString(38, ppf);
        pstmt1.setString(39, user_id);
        pstmt1.setString(40, others);
        pstmt1.setString(41, patient_name);
        pstmt1.setString(42, t_account);
        pstmt1.setString(43, corporate_account);
        pstmt1.setString(44, kol);
        pstmt1.setString(45, c_pkg);
        pstmt1.setString(46, dispatch_crpt);
        pstmt1.setString(47, dispatch_address);
        pstmt1.setString(48, bill_address);
        pstmt1.setString(49, delivery_note_no);
        pstmt1.setString(50, bgst_no);
        pstmt1.setString(51, list2.get(2));
        pstmt1.setString(52, list2.get(3));
        pstmt1.setString(53, category);
        pstmt1.setString(54, upper_aligner);
        pstmt1.setString(55, lower_aligner);
        pstmt1.setInt(56, priority);
        pstmt1.setString(57, Existing);
        pstmt1.setString(58, patient_email);
        pstmt1.setString(59, treating_dr_email);
        rowaffected = pstmt1.executeUpdate();
        if (rowaffected > 0) {
          stmt1.executeUpdate("INSERT INTO decision_history(decision,Remarks,date_time,stage,UserId,caseid)values('INI','" + remarks + "',now(),'INI','" + user_id + "','" + case_id + "')");
          ArrayList<String> alist = new ArrayList<>();
          alist.add(address1);
          alist.add(address2);
          alist.add(address3);
          alist.add(address4);
          alist.add(address5);
          for (String item : alist) {
            if (!item.equals("")) {
              apstmt = con.prepareStatement(" insert into address_logs(case_id,address,remarks,decision,user_id,created_on) values(?,?,?,?,?,now()) ");
              apstmt.setLong(1, case_id);
              apstmt.setString(2, item);
              apstmt.setString(3, remarks);
              apstmt.setString(4, "INI");
              apstmt.setString(5, user_id);
              apstmt.executeUpdate();
            } 
          } 
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Case Initiated successfully');");
          String apiDownload = "http//97.74.91.187:8080/QRCodeGenerator/qrCode1?casedid=" + case_id;
          out.println("var api='" + apiDownload + "'");
          out.println("window.open(api,'_blank', resizable='yes', scrollbars='yes', titlebar='yes', width='800', height='900', top='10', left='10');");
          out.println("location='Newcase1';");
          out.println("</script>");
        } else {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Error Occured!');");
          out.println("location='Newcase1';");
          out.println("</script>");
        } 
      } 
    } catch (Exception e) {
      System.out.println("Error At AddCaseDetails: " + e.getMessage());
    } finally {
      t_account = corporate_account = kol = c_pkg = dispatch_crpt = dispatch_address = bill_address = delivery_note_no = bgst_no = category = upper_aligner = lower_aligner = 
        address1 = address2 = address3 = address4 = address5 = pincode1 = pincode2 = pincode3 = pincode4 = pincode5 = phone1 = phone2 = phone3 = phone4 = phone5 = city1 = city2 = city3 = city4 = city5 = location1 = location2 = location3 = location4 = location5 = starterkit1 = starterkit2 = starterkit3 = starterkit4 = starterkit5 = batch1 = batch2 = batch3 = batch4 = batch5 = patient_email = treating_dr_email  = "";
      if (rs != null)
        try {
          rs.close();
        } catch (Exception exception) {
        
        } finally {
          rs = null;
        }  
      if (stmt1 != null)
        try {
          stmt1.close();
        } catch (Exception exception) {
        
        } finally {
          stmt1 = null;
        }  
      if (pstmt1 != null)
        try {
          pstmt1.close();
        } catch (Exception exception) {
        
        } finally {
          pstmt1 = null;
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
