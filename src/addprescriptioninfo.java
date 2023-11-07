import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet({"/addprescriptioninfo"})
public class addprescriptioninfo extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(addprescriptioninfo.class);
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String caseid = request.getParameter("caseid");
    String form_id = request.getParameter("form_id");
    Connection con = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    HashMap<String, String> citymap = new HashMap<>();
    try {
      con = LoginDAO.getConnectionDetails();
      PreparedStatement ps = con.prepareStatement("select * from alignwise_citymaster");
      rs2 = ps.executeQuery();
      while (rs2.next())
        citymap.put(rs2.getString("id"), rs2.getString("name")); 
    } catch (Exception e) {
      LOGGER.info("@addprescriptioninfo Exception=" + e.getMessage());
    } 
    try {
      LOGGER.info(String.valueOf(citymap.size()) + " size of citymap");
      String query = "select * from incompleteform where form_id=" + form_id;
      con = LoginDAO.getConnectionDetails();
      PreparedStatement ps = con.prepareStatement(query);
      rs = ps.executeQuery();
      while (rs.next()) {
        String submittedon = rs.getString("submittedon");
        String submittedby = rs.getString("submittedby");
        String formdata = rs.getString("formdata");
        String patientname = rs.getString("patientname");
        JSONParser parser = new JSONParser();
        JSONObject j = null;
        j = (JSONObject)parser.parse(formdata);
        String doctorname = j.get("DoctorName").toString();
        String clinicaddress = j.get("ClinicAddress").toString();
        String city = citymap.get(j.get("City").toString());
        String age = j.get("Age").toString();
        con = LoginDAO.getConnectionDetails();
        String query2 = "insert into patient_details set registered_doctor='" + doctorname + "',patient_name='" + patientname + "',clinic_address='" + clinicaddress + "',city='" + city + "',patient_age='" + age + "' where case_id=" + caseid;
        try {
          ps = con.prepareStatement("update patient_details set registered_doctor='" + doctorname + "',patient_name='" + patientname + "',clinic_address='" + clinicaddress + "',city='" + city + "',patient_age='" + age + "',form_id='" + form_id + "' where case_id=" + caseid);
          ps.executeUpdate();
          response.getWriter().write("Prescription form with form id " + form_id + "  added Successfully");
        } catch (Exception e) {
          LOGGER.info("exception in update query " + e.getMessage());
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
