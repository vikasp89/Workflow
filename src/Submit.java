import com.vo.ProfileVO;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/Submit"})
@MultipartConfig(fileSizeThreshold = 2097152, maxFileSize = 10485760L, maxRequestSize = 52428800L)
public class Submit extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Submit.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession(false);
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      CallableStatement callable = null;
      con = LoginDAO.getConnectionDetails();
      String reviewnumber = request.getParameter("reviewnumber");
      String userid = (String)session.getAttribute("userid");
      String registered_doctor = request.getParameter("registered_doctor");
      String prerequisite = request.getParameter("prerequisite");
      String case_number = request.getParameter("case_number");
      String payment_grid_table = request.getParameter("payment_grid_table");
      String no_of_aligners_table = request.getParameter("table3Content");
      String user_id = request.getParameter("user_id");
      String file = request.getParameter("file");
      String clinic_name = request.getParameter("clinic_name");
      String clinic_address = request.getParameter("clinic_address");
      String gp = request.getParameter("gp");
      String city = request.getParameter("city");
      String patient_name = request.getParameter("patient_name");
      String patient_age = request.getParameter("patient_age");
      String referred_by = request.getParameter("referred_by");
      String type_of_account = request.getParameter("type_of_account");
      String channel = request.getParameter("channel");
      String kol = request.getParameter("kol");
      String discount = request.getParameter("discount");
      String starter_kit = request.getParameter("starter_kit");
      String no_of_aligners = request.getParameter("no_of_aligners");
      String scanning = request.getParameter("scanning");
      String pickup = request.getParameter("pickup");
      String dispatch = request.getParameter("dispatch");
      String dispatch_to = request.getParameter("dispatch_to");
      String advance_starter = request.getParameter("advance_starter");
      String advance_scanning = request.getParameter("advance_scanning");
      String scanning_service = request.getParameter("scanning_service");
      String dispatch_date = request.getParameter("dispatch_date");
      String person_dispatch = request.getParameter("person_dispatch");
      String person_scan = request.getParameter("person_scan");
      String bill_to = request.getParameter("bill_to");
      String gst_number = request.getParameter("gst_number");
      String payment_mode = request.getParameter("payment_mode");
      String wo_number = request.getParameter("wo_number");
      String wo_date = request.getParameter("wo_date");
      String decision = request.getParameter("decision");
      String no_of_stages = request.getParameter("no_of_stages");
      String current_stage = request.getParameter("current_stage");
      String no_of_aligners_u_p = request.getParameter("no_of_aligners_u_p");
      String no_of_aligners_u_a = request.getParameter("no_of_aligners_u_a");
      String no_of_aligners_l_a = request.getParameter("no_of_aligners_l_a");
      String no_of_aligners_l_p = request.getParameter("no_of_aligners_l_p");
      String attachment_applicable = request.getParameter("attachment_applicable");
      String dispatch_grid = request.getParameter("dispatch_grid");
      String dispatch_date2 = request.getParameter("dispatch_date2");
      String data_grid = request.getParameter("data_grid");
      String final_amount = request.getParameter("final_amount");
      String status_grid = request.getParameter("status_grid");
      String payment_grid = request.getParameter("payment_grid");
      String batch_number = request.getParameter("batch_number");
      String work_date = request.getParameter("work_date");
      String next_work = request.getParameter("next_work");
      String next_work_date = request.getParameter("next_work_date");
      String refinement_package = request.getParameter("refinement_package");
      String case_review = request.getParameter("case_review");
      String corporate_account = request.getParameter("corporate_account");
      String priority = request.getParameter("priority");
      String remarks = request.getParameter("remarks");
      String flag = request.getParameter("xy");
      String[] profiles = payment_grid_table.split("#");
      String[][] paymentTable = new String[100][100];
      for (int i = 0; i < profiles.length; i++) {
        String[] x = profiles[i].split("~");
        for (int y = 0; y < x.length; y++)
          paymentTable[i][y] = x[y]; 
      } 
      String[] aligners = no_of_aligners_table.split("#");
      String[][] alignerstable = new String[100][100];
      for (int j = 0; j < aligners.length; j++) {
        String[] x2 = aligners[j].split("~");
        for (int y = 0; y < x2.length; y++)
          alignerstable[j][y] = x2[y]; 
      } 
      String[] review_grid = reviewnumber.split("#");
      String[][] reviewtable = new String[100][100];
      for (int k = 0; k < review_grid.length; k++) {
        String[] x1 = review_grid[k].split("~");
        for (int y = 0; y < x1.length; y++)
          reviewtable[k][y] = x1[y]; 
      } 
      ProfileVO profileVO = LoginDAO.ProfileMapping(userid, decision, "workorder", Long.valueOf(0L));
      if (decision.equals("Save As Drafts") && flag.equals("abc")) {
        Statement insertStmnt1 = con.createStatement();
        String query4 = "Insert into patient_details( USER_ID,initiated_by,initiated_Date,DECISION, STATUS, STAGE, NEXT_STAGE,prerequisite,case_number,registered_doctor,clinic_name,clinic_address,gp,city,patient_name,patient_age,type_of_account,referred_by,channel,corporate_account,kol,discount,no_of_aligners,starter_kit,scanning,pickup,dispatch,dispatch_to,advance_starter,advance_scanning, scanning_service,dispatch_date,person_dispatch,person_scan,bill_to,gst_number,payment_mode,wo_number,wo_date,priority,created_date,no_of_stages,current_stage,no_of_aligners_u_a,no_of_aligners_u_p,no_of_aligners_l_a,no_of_aligners_l_p,attachment_applicable,dispatch_grid,data_grid,dispatch_date2,final_amount,status_grid,payment_grid,batch_number,work_date,next_work,next_work_date,case_review,refinement_package,draftflag,priority) values( '" + userid + "','" + userid + "', sysdate(),'" + decision + "', '" + profileVO.getStatusCode() + "', '" + "Intro" + "', '" + profileVO.getNextStage() + "', '" + prerequisite + "', '" + case_number + "', '" + registered_doctor + "', '" + clinic_name + "', '" + clinic_address + "', '" + gp + "', '" + city + "', '" + patient_name + "', '" + patient_age + "', '" + type_of_account + "', '" + referred_by + "', '" + channel + "', '" + corporate_account + "', '" + kol + "', '" + discount + "', '" + no_of_aligners + "', '" + starter_kit + "', '" + scanning + "', '" + pickup + "', '" + dispatch + "', '" + dispatch_to + "', '" + advance_starter + "', '" + advance_scanning + "', '" + scanning_service + "', '" + dispatch_date + "', '" + person_dispatch + "', '" + person_scan + "', '" + bill_to + "', '" + gst_number + "', '" + payment_mode + "', '" + wo_number + "', '" + wo_date + "', '" + priority + "',  sysdate(), '" + no_of_stages + "', '" + current_stage + "', '" + no_of_aligners_u_a + "', '" + no_of_aligners_u_p + "', '" + no_of_aligners_l_a + "', '" + no_of_aligners_l_p + "', '" + attachment_applicable + "', '" + dispatch_grid + "', '" + data_grid + "', '" + dispatch_date2 + "', '" + final_amount + "', '" + status_grid + "', '" + payment_grid + "', '" + batch_number + "', '" + work_date + "', '" + next_work + "', '" + next_work_date + "', '" + case_review + "', '" + refinement_package + "', 'Y','5')";
        insertStmnt1.executeUpdate(query4);
        out.print("saved to drafs");
      } else if (decision.equals("Save As Drafts") && !flag.equals("abc")) {
        Statement insertStmnt1 = con.createStatement();
        String query4 = "update patient_details set USER_ID='" + userid + "',initiated_by='" + userid + "',initiated_Date=sysdate(),DECISION='" + profileVO.getDecisionCode() + "', STATUS='" + profileVO.getStatusCode() + "', STAGE='" + profileVO.getStageCode() + "', NEXT_STAGE= '" + profileVO.getNextStage() + "',prerequisite='" + prerequisite + "',case_number='" + case_number + "',registered_doctor='" + registered_doctor + "',clinic_name='" + clinic_name + "',clinic_address='" + clinic_address + "',gp='" + gp + "',city='" + city + "',patient_name='" + patient_name + "',patient_age='" + patient_age + "',type_of_account='" + type_of_account + "',referred_by='" + referred_by + "',channel='" + channel + "',corporate_account='" + corporate_account + "',kol='" + kol + "',discount='" + discount + "',no_of_aligners='" + no_of_aligners + "',starter_kit='" + starter_kit + "',scanning='" + scanning + "',pickup='" + pickup + "',dispatch='" + dispatch + "',dispatch_to='" + dispatch_to + "',advance_starter='" + advance_starter + "',advance_scanning='" + advance_scanning + "', scanning_service='" + scanning_service + "',dispatch_date='" + dispatch_date + "',person_dispatch='" + person_dispatch + "',person_scan='" + person_scan + "',bill_to='" + bill_to + "',gst_number='" + gst_number + "',payment_mode='" + payment_mode + "',wo_number='" + wo_number + "',wo_date='" + wo_date + "',priority='" + priority + "',created_date=sysdate(),no_of_stages='" + no_of_stages + "',current_stage= '" + current_stage + "',no_of_aligners_u_a='" + no_of_aligners_u_a + "',no_of_aligners_u_p='" + no_of_aligners_u_p + "',no_of_aligners_l_a='" + no_of_aligners_l_a + "',no_of_aligners_l_p= '" + no_of_aligners_l_p + "',attachment_applicable='" + attachment_applicable + "',dispatch_grid='" + dispatch_grid + "',data_grid='" + data_grid + "',dispatch_date2='" + dispatch_date2 + "',final_amount='" + final_amount + "',status_grid='" + status_grid + "',payment_grid='" + payment_grid + "',batch_number='" + batch_number + "',work_date='" + work_date + "',next_work='" + next_work + "',next_work_date='" + next_work_date + "',case_review='" + case_review + "',refinement_package='" + refinement_package + "',draftflag='Y'  where case_id='" + flag + "'";
        insertStmnt1.executeUpdate(query4);
        out.print("Completely Saved To Drafts");
      } else {
        Statement insertStmnt = con.createStatement();
        Statement insertStmnt1 = con.createStatement();
        String query4 = "Insert into patient_details( USER_ID,initiated_by,initiated_Date,DECISION, STATUS, STAGE, NEXT_STAGE,prerequisite,case_number,registered_doctor,clinic_name,clinic_address,gp,city,patient_name,patient_age,type_of_account,referred_by,channel,corporate_account,kol,discount,no_of_aligners,starter_kit,scanning,pickup,dispatch,dispatch_to,advance_starter,advance_scanning, scanning_service,dispatch_date,person_dispatch,person_scan,bill_to,gst_number,payment_mode,wo_number,wo_date,created_date,no_of_stages,current_stage,no_of_aligners_u_a,no_of_aligners_u_p,no_of_aligners_l_a,no_of_aligners_l_p,attachment_applicable,dispatch_grid,data_grid,dispatch_date2,final_amount,status_grid,payment_grid,batch_number,work_date,next_work,next_work_date,case_review,refinement_package,draftflag,crmname,priority) values( '" + userid + "','" + userid + "', sysdate(),'" + profileVO.getDecisionCode() + "', '" + profileVO.getStatusCode() + "', '" + profileVO.getStageCode() + "', '" + profileVO.getNextStage() + "', '" + prerequisite + "', '" + case_number + "', '" + registered_doctor + "', '" + clinic_name + "', '" + clinic_address + "', '" + gp + "', '" + city + "', '" + patient_name + "', '" + patient_age + "', '" + type_of_account + "', '" + referred_by + "', '" + channel + "', '" + corporate_account + "', '" + kol + "', '" + discount + "', '" + no_of_aligners + "', '" + starter_kit + "', '" + scanning + "', '" + pickup + "', '" + dispatch + "', '" + dispatch_to + "', '" + advance_starter + "', '" + advance_scanning + "', '" + scanning_service + "', '" + dispatch_date + "', '" + person_dispatch + "', '" + person_scan + "', '" + bill_to + "', '" + gst_number + "', '" + payment_mode + "', '" + wo_number + "', '" + wo_date + "',  sysdate(), '" + no_of_stages + "', '" + current_stage + "', '" + no_of_aligners_u_a + "', '" + no_of_aligners_u_p + "', '" + no_of_aligners_l_a + "', '" + no_of_aligners_l_p + "', '" + attachment_applicable + "', '" + dispatch_grid + "', '" + data_grid + "', '" + dispatch_date2 + "', '" + final_amount + "', '" + status_grid + "', '" + payment_grid + "', '" + batch_number + "', '" + work_date + "', '" + next_work + "', '" + next_work_date + "', '" + case_review + "', '" + refinement_package + "','N','" + user_id + "','5')";
        insertStmnt1.executeUpdate(query4);
        String query3 = "select case_id from patient_details where user_id='<userid>' order by created_date desc";
        query3 = query3.replace("<userid>", userid);
        ps = con.prepareStatement(query3);
        ResultSet rst = ps.executeQuery();
        String case_id = new String();
        if (rst.next())
          case_id = rst.getString("case_id"); 
        query4 = "Insert into decision_history(decision, Remarks, date_time, stage, UserId, caseid) values( '" + decision + "','" + remarks + "', sysdate(),'Intro','" + userid + "','" + case_id + "') ";
        insertStmnt1.executeUpdate(query4);
        String query2 = "";
        try {
          int z;
          for (z = 0; z < profiles.length; z++) {
            if (paymentTable[z][1] != null || paymentTable[z][2] != null) {
              query2 = "insert into payment_grid values('" + case_id + "','" + paymentTable[z][0] + "','" + paymentTable[z][1] + "','" + paymentTable[z][2] + "',sysdate())";
              insertStmnt.executeUpdate(query2);
            } 
          } 
          for (z = 0; z < review_grid.length; z++) {
            if (reviewtable[z][1] != null || reviewtable[z][2] != null || reviewtable[z][3] != null) {
              query2 = "insert into reviewnumbergrid values('" + case_id + "','" + reviewtable[z][0] + "','" + reviewtable[z][1] + "','" + reviewtable[z][2] + "','" + reviewtable[z][3] + "',sysdate())";
              insertStmnt.executeUpdate(query2);
            } 
          } 
          for (z = 0; z < aligners.length; z++) {
            if (alignerstable[z][1] != null || alignerstable[z][2] != null || alignerstable[z][3] != null) {
              query2 = "insert into no_of_aligers_table values('" + alignerstable[z][0] + "','" + alignerstable[z][1] + "','" + alignerstable[z][2] + "','" + alignerstable[z][3] + "','" + case_id + "',sysdate())";
              insertStmnt.executeUpdate(query2);
            } 
          } 
        } catch (Exception e) {
          LOGGER.info("Error At Searchcase=" + e.getMessage());
        } 
        out.print("Case Number  id " + case_id + " Created Successfully");
      } 
    } catch (Exception e) {
      LOGGER.info("Error At Searchcase=" + e.getMessage());
    } 
  }
}
