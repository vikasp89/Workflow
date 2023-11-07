import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.workflow.connection.LoginDAO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet({ "/NewCasesshow1" })
public class NewCasesshow1 extends HttpServlet
{
    static final Logger LOGGER;
    private static final long serialVersionUID = 1L;
    
    static {
        LOGGER = LogManager.getLogger((Class)NewCasesshow1.class);
    }
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final PrintWriter out = response.getWriter();
        final PreparedStatement pstmt = null;
        final PreparedStatement pstmt2 = null;
        final ResultSet rs0 = null;
        ResultSet rs2 = null;
        final String id = "";
        String case_type = "";
        String case_id = "";
        String scan = "";
        final String location = "";
        final String city = "";
        String r_Doctor = "";
        String crm = "";
        String p_graph = "";
        String starter_kit = "";
        String type_request = "";
        String s_shown = "";
        String ppf_fill = "";
        String t_account = "";
        String corporate_account = "";
        String kol = "";
        String c_pkg = "";
        String pkg_name = "";
        String dispatch_crpt = "";
        String dispatch_address = "";
        String bill_address = "";
        String c_doctor = "";
        String case_stage = "";
        String delivery_note_no = "";
        String bgst_no = "";
        String category = "";
        String upper_aligner = "";
        String lower_aligner = "";
        String others = "";
        String Patient_Name = "";
        final String priority = "";
        String remarks = "";
        final String address1 = "";
        final String address2 = "";
        final String address3 = "";
        final String address4 = "";
        final String address5 = "";
        final String default_address = "";
        try {
            final Connection con = LoginDAO.getConnectionDetails();
            Statement st1 = null;
            st1 = con.createStatement();
            final String Case_Id = request.getParameter("caseId");
            final String newcase = request.getParameter("page");
            String cid = "";
            final HttpSession session = request.getSession();
            final ArrayList<String> cidarr = new ArrayList<String>();
            final String sql1 = "SELECT Case_Id FROM cc_crm where Case_Id='" + Case_Id + "'";
            rs2 = st1.executeQuery(sql1);
            while (rs2.next()) {
                cid = rs2.getString("Case_Id");
                cidarr.add(cid);
            }
            if (cidarr.contains(Case_Id)) {
                final String sqlpn = "SELECT * FROM cc_crm where Case_Id='" + Case_Id + "'";
                final ResultSet rs3 = st1.executeQuery(sqlpn);
                while (rs3.next()) {
                    case_id = rs3.getString("case_id");
                    final String clinic = rs3.getString("clinic_name");
                    final String add = rs3.getString("address");
                    final String phn = rs3.getString("phone");
                    final String bcn = rs3.getString("bclinic");
                    final String badd = rs3.getString("badd");
                    case_type = rs3.getString("case_type");
                    scan = rs3.getString("scan");
                    r_Doctor = rs3.getString("registered_doctor");
                    crm = rs3.getString("crm_name");
                    p_graph = rs3.getString("p_graph");
                    starter_kit = rs3.getString("starter_kit");
                    type_request = rs3.getString("type_request");
                    s_shown = rs3.getString("s_shown");
                    ppf_fill = rs3.getString("ppf_fill");
                    t_account = rs3.getString("t_account");
                    corporate_account = rs3.getString("corporate_account");
                    kol = rs3.getString("kol");
                    c_pkg = rs3.getString("c_pkg");
                    pkg_name = rs3.getString("pkg_name");
                    dispatch_crpt = rs3.getString("dispatch_crpt");
                    dispatch_address = rs3.getString("dispatch_address");
                    bill_address = rs3.getString("bill_address");
                    c_doctor = rs3.getString("Doctor_Name");
                    case_stage = rs3.getString("case_stage");
                    delivery_note_no = rs3.getString("delivery_note_no");
                    Patient_Name = rs3.getString("Patient_Name");
                    bgst_no = rs3.getString("bgst_no");
                    category = rs3.getString("category");
                    upper_aligner = rs3.getString("upper_aligner");
                    lower_aligner = rs3.getString("lower_aligner");
                    others = rs3.getString("others");
                    remarks = rs3.getString("remark");
                    final String total_amount = rs3.getString("total_amount");
                    final String payment_processing = rs3.getString("payment_processing");
                    final String payment_mode = rs3.getString("payment_mode");
                    session.setAttribute("cn", (Object)clinic);
                    session.setAttribute("add", (Object)add);
                    session.setAttribute("bcn", (Object)bcn);
                    session.setAttribute("badd", (Object)badd);
                    session.setAttribute("case_id", (Object)case_id);
                    session.setAttribute("case_type", (Object)case_type);
                    session.setAttribute("scan", (Object)scan);
                    session.setAttribute("city", (Object)city);
                    session.setAttribute("r_Doctor", (Object)r_Doctor);
                    session.setAttribute("crm", (Object)crm);
                    session.setAttribute("p_graph", (Object)p_graph);
                    session.setAttribute("starter_kit", (Object)starter_kit);
                    session.setAttribute("type_request", (Object)type_request);
                    session.setAttribute("s_shown", (Object)s_shown);
                    session.setAttribute("ppf_fill", (Object)ppf_fill);
                    session.setAttribute("t_account", (Object)t_account);
                    session.setAttribute("corporate_account", (Object)corporate_account);
                    session.setAttribute("kol", (Object)kol);
                    session.setAttribute("c_pkg", (Object)c_pkg);
                    session.setAttribute("pkg_name", (Object)pkg_name);
                    session.setAttribute("dispatch_crpt", (Object)dispatch_crpt);
                    session.setAttribute("dispatch_address", (Object)dispatch_address);
                    session.setAttribute("bill_address", (Object)bill_address);
                    session.setAttribute("c_doctor", (Object)c_doctor);
                    session.setAttribute("case_stage", (Object)case_stage);
                    session.setAttribute("delivery_note_no", (Object)delivery_note_no);
                    session.setAttribute("bgst_no", (Object)bgst_no);
                    session.setAttribute("category", (Object)category);
                    session.setAttribute("upper_aligner", (Object)upper_aligner);
                    session.setAttribute("lower_aligner", (Object)lower_aligner);
                    session.setAttribute("others", (Object)others);
                    session.setAttribute("Patient_Name", (Object)Patient_Name);
                    session.setAttribute("priority", (Object)priority);
                    session.setAttribute("remarks", (Object)remarks);
                    session.setAttribute("total_amount", (Object)total_amount);
                    session.setAttribute("payment_processing", (Object)payment_processing);
                    session.setAttribute("payment_mode", (Object)payment_mode);
                    session.setAttribute("address1", (Object)rs3.getString("address1"));
                    session.setAttribute("address2", (Object)rs3.getString("address2"));
                    session.setAttribute("address3", (Object)rs3.getString("address3"));
                    session.setAttribute("address4", (Object)rs3.getString("address4"));
                    session.setAttribute("address5", (Object)rs3.getString("address5"));
                    session.setAttribute("pincode1", (Object)rs3.getString("pincode1"));
                    session.setAttribute("pincode2", (Object)rs3.getString("pincode2"));
                    session.setAttribute("pincode3", (Object)rs3.getString("pincode3"));
                    session.setAttribute("pincode4", (Object)rs3.getString("pincode4"));
                    session.setAttribute("pincode5", (Object)rs3.getString("pincode5"));
                    session.setAttribute("phone1", (Object)rs3.getString("phone1"));
                    session.setAttribute("phone2", (Object)rs3.getString("phone2"));
                    session.setAttribute("phone3", (Object)rs3.getString("phone3"));
                    session.setAttribute("phone4", (Object)rs3.getString("phone4"));
                    session.setAttribute("phone5", (Object)rs3.getString("phone5"));
                    session.setAttribute("city1", (Object)rs3.getString("city"));
                    session.setAttribute("city2", (Object)rs3.getString("city2"));
                    session.setAttribute("city3", (Object)rs3.getString("city3"));
                    session.setAttribute("city4", (Object)rs3.getString("city4"));
                    session.setAttribute("city5", (Object)rs3.getString("city5"));
                    session.setAttribute("location1", (Object)rs3.getString("location"));
                    session.setAttribute("location2", (Object)rs3.getString("location2"));
                    session.setAttribute("location3", (Object)rs3.getString("location3"));
                    session.setAttribute("location4", (Object)rs3.getString("location4"));
                    session.setAttribute("location5", (Object)rs3.getString("location5"));
                    session.setAttribute("default_starterkit", (Object)rs3.getString("default_starterkit"));
                    session.setAttribute("default_address", (Object)rs3.getString("default_address"));
                    session.setAttribute("patient_email", (Object)rs3.getString("patient_email"));
                    session.setAttribute("treating_dr_email", (Object)rs3.getString("treating_dr_email"));
                }
                session.setAttribute("cid", (Object)Case_Id);
                if (newcase != null) {
                    response.sendRedirect("EditCaseDetails.jsp?page=newcase");
                }
                else {
                    response.sendRedirect("EditCaseDetails.jsp");
                }
            }
            else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('CASE ID NOT EXIST!');");
                out.println("location='CreateCC';");
                out.println("</script>");
            }
        }
        catch (Exception e) {
            NewCasesshow1.LOGGER.info("Error At NewCaseshow1=" + e.getMessage());
        }
    }
}