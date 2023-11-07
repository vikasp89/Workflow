
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

import com.workflow.connection.LoginDAO;

/**
 * Servlet implementation class RepeatCase
 */
@WebServlet("/RepeatCase")
public class RepeatCase extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 * 
	 */
	public RepeatCase() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		Connection con = null;
		Connection con1 = null;
		Connection con2 = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmtSELECT = null;
		
		ResultSet rs = null;

		String Doctor_Name = "";
		String Patient_Name = "";
		String crm_name = "";
		
		String query = (request.getParameter("query") == null) ? "" : request.getParameter("query");
		String caseId = (request.getParameter("caseId") == null) ? "" : request.getParameter("caseId");
		String patientName = (request.getParameter("patientName") == null) ? "" : request.getParameter("patientName");
		
		String doctorName = (request.getParameter("doctorName") == null) ? "" : request.getParameter("doctorName");
		String remarks = (request.getParameter("remarks") == null) ? "" : request.getParameter("remarks");
		String case_stage = (request.getParameter("case_stage") == null) ? "" : request.getParameter("case_stage");
		String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
		String sheet = (request.getParameter("sheet") == null) ? "" : request.getParameter("sheet");
		String issue = (request.getParameter("issue") == null) ? "" : request.getParameter("issue");
	
		String upper_aligner = (request.getParameter("upper_aligner") == null) ? "" : request.getParameter("upper_aligner");
		String lower_aligner = (request.getParameter("lower_aligner") == null) ? "" : request.getParameter("lower_aligner");
		String photos = (request.getParameter("photos") == null) ? "" : request.getParameter("photos");
	
		String upper_thichness = (request.getParameter("upper_thichness") == null) ? "" : request.getParameter("upper_thichness");
		String lower_thichness = (request.getParameter("lower_thichness") == null) ? "" : request.getParameter("lower_thichness");
	    
		String malocclusion = (request.getParameter("malocclusion") == null) ? "" : request.getParameter("malocclusion");
				
		 System.out.println("upper_thickness: "+upper_thichness+" lower_thickness: "+lower_thichness+" malocclusion: "+malocclusion+" sheet: "+sheet);

	    
		String SQL ="";
		String dbcase_id ="";
	
		try {
			con = LoginDAO.getConnectionDetails();
			con1 = LoginDAO.getConnectionDetails();
			con2 = LoginDAO.getConnectionDetails();

			 if (query.equals("checkcaseid")) {
								 
				  pstmtSELECT = con.prepareStatement(" SELECT * FROM repeat_case WHERE case_id='"+caseId+"' ");
			      rs = pstmtSELECT.executeQuery();
			      if(rs.next()) {
			    	  dbcase_id = rs.getString("case_id");
			    	  System.out.println("1");
			    	  query="updateRPT";
			      } else {
			    	  System.out.println("2");
			    	  query="insertRPT";
		    	  }
			 }
		      
			  if (query.equals("getdata")) {
		    	  
					pstmt = con.prepareStatement(" select * from cc_crm where case_id=?");
					pstmt.setString(1, caseId);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						Patient_Name = rs.getString("Patient_Name");
						Doctor_Name = rs.getString("Doctor_Name");
						upper_aligner = rs.getString("upper_aligner");
						
						System.out.println("Patient_Name: "+Patient_Name + "," + Doctor_Name +","+lower_aligner);						
						out.println(String.valueOf(Patient_Name) + "," + Doctor_Name);
					} else {
						out.println("This case Id not Present!");
					}
					
		      }
			  if (query.equals("insertRPT")) {
				 PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO repeat_case (case_id,doctor_name,"
				 		+ "patient_name,issue,upper_aligner,lower_aligner,wfc_remarks,user_id,photos)"
				 		+ " VALUES (?,?,?,?,?,?,?,?,?) ");
			     preparedStatement.setString(1, caseId);
			     preparedStatement.setString(2, doctorName);
			     preparedStatement.setString(3, patientName);
			     preparedStatement.setString(4, issue);
			     preparedStatement.setString(5, upper_aligner);
			     preparedStatement.setString(6, lower_aligner);
			     preparedStatement.setString(7, remarks);
			     preparedStatement.setString(8, userId);
			     preparedStatement.setString(9, photos);

			     System.out.println("preparedStatement: "+preparedStatement.toString());
			     int row = preparedStatement.executeUpdate();
			         if (row > 0) {
			        	 if(case_stage.equals("RPTWFC")) {
			        		 SQL = " update cc_crm set  Rpt_stage='"+case_stage+"',wfc_rpt_at=now() where Case_Id='" + caseId + "' ";
			        	 }
				             pstmt1 = con1.prepareStatement(SQL);
				             pstmt1.executeUpdate();
				             
				             pstmt2 = con1.prepareStatement("INSERT INTO decision_history(decision, Remarks, date_time, stage, UserId, caseid) values('" + case_stage + "','" + remarks + "' ,now(),'" + case_stage + "','" + userId + "','" + caseId + "')");
				             pstmt2.executeUpdate();
						     out.println("Record has been Successfully Saved");
			         }			
			}
			
			if (query.equals("updateRPT")) {
				 PreparedStatement preparedStatement = con.prepareStatement(" UPDATE repeat_case set sheet=?,remarks=?,upper_thichness=?,lower_thichness=?,malocclusion=?,user_id=? WHERE case_id='"+caseId+"' ");
			     preparedStatement.setString(1, sheet);
			     preparedStatement.setString(2, remarks);
			     preparedStatement.setString(3, upper_thichness);
			     preparedStatement.setString(4, lower_thichness);
			   //  preparedStatement.setString(5, case_stage);
			     preparedStatement.setString(5, malocclusion);
			     preparedStatement.setString(6, userId);				
			     System.out.println("pstmt: "+preparedStatement);
					  int row = preparedStatement.executeUpdate();
					  
					  if (row > 0) {
						  if(case_stage.equals("RPTWFC")) { 
						  System.out.println("1");
						  SQL= " update cc_crm set  Rpt_stage='"+case_stage+"',wfc_rpt_at=now() where Case_Id='" + caseId + "' ";
						  }
					  if(case_stage.equals("RPTPLN")) {
						  System.out.println("2");
						  SQL =" update cc_crm set  Rpt_stage='"+case_stage+"',pln_rpt_at=now() where Case_Id='" + caseId + "' ";
						  }
					  if(case_stage.equals("RPTLAB")) {
						  System.out.println("3");
						  SQL =" update cc_crm set  Rpt_stage='"+case_stage+"',lab_rpt_at=now() where Case_Id='" + caseId + "' ";
						  }
					  if(case_stage.equals("RPTFQC")) {
						  System.out.println("4");
						  SQL =" update cc_crm set  Rpt_stage='"+case_stage+"',qc_rpt_at=now() where Case_Id='" + caseId + "' ";
						  }
					  if(case_stage.equals("RPTPCK")) {
						  System.out.println("5");
						  SQL =" update cc_crm set  Rpt_stage='"+case_stage+"',pck_rpt_at=now() where Case_Id='" + caseId + "' ";
						  }
					  if(case_stage.equals("RPTDPH")) {
						  System.out.println("6");
						  SQL =" update cc_crm set  Rpt_stage='"+case_stage+"',dispatch_rpt_at=now() where Case_Id='" + caseId + "' "; 
						  }
					  	  pstmt1 = con1.prepareStatement(SQL);
					  	  pstmt1.executeUpdate(); 
					     pstmt2 = con1.prepareStatement("INSERT INTO decision_history(decision, Remarks, date_time, stage, UserId, caseid) values('" + case_stage + "','" + remarks + "' ,now(),'" + case_stage + "','" + userId +"','" + caseId + "')");
					     pstmt2.executeUpdate(); 
					     out.println("Record has been Successfully updated");
					     }					 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
