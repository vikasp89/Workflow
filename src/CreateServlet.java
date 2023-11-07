import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vo.ProfileVO;
import com.workflow.connection.LoginDAO;

import oracle.net.aso.e;


@SuppressWarnings("serial")
public class CreateServlet extends HttpServlet { 
	static final Logger LOGGER = LogManager.getLogger(CreateServlet.class);


	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws ServletException, IOException {  
		
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();
		Connection con =null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		CallableStatement callable = null;
		try {
			if(request.getParameter("createSubmit")!=null){
				try {
				  	
				con = LoginDAO.getConnectionDetails();
				
				String userid = request.getParameter("username");
				String field1 = request.getParameter("field1");
				String field2 = request.getParameter("field2");
				String decision = request.getParameter("category");
				
				
				
				
				
				ProfileVO profileVO = LoginDAO.ProfileMapping(userid, decision,"Intro",(long) 0);
					/*String sqlIdentifier = "select CASE_ID_SEQUENCE.NEXTVAL from dual";
				ps = con.prepareStatement(sqlIdentifier);
				   long caseID = 0 ;
				   rs = ps.executeQuery();
				   if(rs.next()){
					   caseID  = rs.getLong(1);
				   }
				  */
				
				//long caseID = 84 ;
				
				//System.out.println(caseID);
				Statement insertStmnt = con.createStatement();
				String query = "Insert into CASE_MSTR( USER_ID,initiated_by,initiated_Date,DECISION, STATUS, STAGE, NEXT_STAGE, FIELD1, FIELD2, created_date) values( '" + userid + "','" + userid + "', sysdate(),'" + profileVO.getDecisionCode() + "', '" + profileVO.getStatusCode() + "', '" + profileVO.getStageCode() + "', '" + profileVO.getNextStage() + "', '" + field1 + "', '" + field2 + "',  sysdate())"; 
				insertStmnt.executeUpdate(query);
				
				
				response.setContentType("text/html");  
			    //PrintWriter out = response.getWriter();  
			    out.print("Values submitted!");
			   
			   request.setAttribute("username", userid); 
			   
				RequestDispatcher rd = request.getRequestDispatcher("Profile1.jsp");
			    rd.include(request,response);
				} catch (SQLException e) {
					  LOGGER.info("Error At AddComment="+e.getMessage());

				} catch (ClassNotFoundException e) {
					  LOGGER.info("Error At AddComment="+e.getMessage());
				}
				

				}
				
				
				
				if(request.getParameter("viewSubmit")!=null){
					
					 try{
						con = LoginDAO.getConnectionDetails();
						
						String userid = request.getParameter("username");
						String caseIdasString = request.getParameter("case");
						Long caseid = Long.parseLong(caseIdasString);
						//int caseid = 12;
						String decision = request.getParameter("category2");
						
						ProfileVO profile2VO = LoginDAO.ProfileMapping(userid, decision,"View",caseid);
						
						profile2VO.getStatusCode();
						profile2VO.getStageCode();
						profile2VO.getNextStage();
						profile2VO.getDecisionCode(); 
						
							
						ps=con.prepareStatement("select * from CASE_MSTR where USER_ID=? and CASE_ID=?");  
					
						ps.setString(1,userid); 
						ps.setLong(2,caseid);
						rs = ps.executeQuery();
						List<String> list = new ArrayList<String>();
						while(rs.next()){
							String caseids =rs.getString("CASE_ID");
							String deci = rs.getString("DECISION");
							String stat =rs.getString("STATUS");
							String sta =rs.getString("STAGE");
							String next =rs.getString("NEXT_STAGE");
							String date =rs.getString("CREATED_DATE");
							 
							list.add(caseids);
							list.add(deci);
							list.add(stat);
							list.add(sta);
							list.add(next);
							list.add(date);
							for(String s:list){
							}
							//RequestDispatcher rd = request.getRequestDispatcher("View2.jsp");
						 //rd.include(request,response); 
							HttpSession session= request.getSession();
							session.setAttribute("List", list);
							response.sendRedirect("view2.jsp"); 
							
					}
						System.out.println("Found data in CASE_MSTR table!");
						out.close();
						
					    } catch (SQLException e) {
							  LOGGER.info("Error At AddComment="+e.getMessage());

						} catch (ClassNotFoundException e) {
							  LOGGER.info("Error At AddComment="+e.getMessage());
						}
					}
				
				
				
				if(request.getParameter("pendingSubmit")!=null){
					System.out.println("here");
				 try{
					con = LoginDAO.getConnectionDetails();
					
					
					String userid = request.getParameter("username");
					String caseIdasString = request.getParameter("caseId");
					String stageform = request.getParameter("NEXT_STAGE");
					//String oldStage=request.getParameter("STAGE");
							//STAGE
					
					System.out.println("stageform "+stageform);
					
					Long caseid = Long.parseLong(caseIdasString);
					//int caseid = 12;
					String decision = request.getParameter("category2");
					
					System.out.println("userid "+userid);
					System.out.println("decision "+decision);
					System.out.println("stageform "+stageform);
					System.out.println("caseid "+caseid);
					String status = "";
					String stage ="";
					String nextstage = "";
					String decsCode = "";
					String decs_code="";
					String updateRoutingFlag="";
					 String cancelRoutingFlag="";
					 
					String RoutingFlag=LoginDAO.updatecaseRouting(caseIdasString, stageform);
					System.out.println("RoutingFlag "+RoutingFlag);
					String routing[]=RoutingFlag.split("~");
					updateRoutingFlag=routing[0];
					cancelRoutingFlag=routing[1];
					
					if(cancelRoutingFlag.equals("Y")) {
						
						response.setContentType("text/html");  
					    //PrintWriter out = response.getWriter();  
					    out.print("Not Allowed to Submit As Case is Distributed");
					   
					   request.setAttribute("username", userid); 
					   
						RequestDispatcher rd = request.getRequestDispatcher("Profile1.jsp");
						rd.include(request,response);
						out.close();
						
						
					}else {
						Statement insertStmnt = con.createStatement();
						String updquery ="";
						if(updateRoutingFlag.equals("Y")) {
							
							String decquery = "select DECS_CODE from DECISION_MASTER where DISPLAY_NAME='<DECISION>'";
							decquery = decquery.replace("<DECISION>", decision);
							System.out.println("decquery : "+decquery);
							ps=con.prepareStatement(decquery);  
							 
							rs=ps.executeQuery(); 
							
							if(rs.next()){
								decs_code =  rs.getString("DECS_CODE");
							}
							
							updquery="select USER_ID, CASE_ID, decision, status, stage, next_stage,  created_date, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,initiated_Date from CASE_MSTR where CASE_ID = "+caseid;
							
							ps=con.prepareStatement(updquery);  
							 System.out.println("updquery "+updquery);
							rs=ps.executeQuery(); 
							System.out.println("rs.getFetchSize() "+rs.getFetchSize());
							
							while(rs.next()){
								System.out.println("in While");
								updquery=	"INSERT INTO CASE_MSTR_HISTORY (USER_ID, CASE_ID, decision, status, stage, next_stage, created_date, field1, field2, field3, field4, field5, field6, field7, field8, field9,field10,initiated_Date) values"
										+ "('"+userid+"','"+rs.getString("CASE_ID")+"','"+decs_code+"','"+status+"','"+stageform+"','"+stageform+"',sysdate(),'"+rs.getString("field1")+"','"+rs.getString("field1")+"','"+rs.getString("field2")+"','"+rs.getString("field3")+"','"+rs.getString("field4")+"','"+rs.getString("field5")+"','"+rs.getString("field6")+"','"+rs.getString("field7")+"','"+rs.getString("field8")+"','"+rs.getString("field9")+"',sysdate())";
								System.out.println("updquery while "+updquery);
								insertStmnt.executeUpdate(updquery);
							//update field data also
							}
							
							
						}else {
						
							ProfileVO profileVO = LoginDAO.ProfileMapping(userid, decision,stageform,caseid);
							status = profileVO.getStatusCode();
							stage = profileVO.getStageCode();
							nextstage = profileVO.getNextStage();
							decsCode = profileVO.getDecisionCode();
							System.out.println(status);
							System.out.println(stage);
							System.out.println(nextstage);
							System.out.println(decsCode);
							System.out.println("userID::pendingSubmit-> " + userid);
							System.out.println("CaseID::pendingSubmit-> " + caseid);
							System.out.println("Decision::pendingSubmit-> " + decision);
							
							System.out.println("updateRoutingFlag "+updateRoutingFlag);
							
							String instquery = "INSERT INTO CASE_MSTR_HISTORY (USER_ID, CASE_ID, decision, status, stage, next_stage, created_date, field1, field2, field3, field4, field5, field6, field7, field8, field9,field10,initiated_Date) \r\n" + 
									"select USER_ID, CASE_ID, decision, status, stage, next_stage,  created_date, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,sysdate() from CASE_MSTR where CASE_ID = "+caseid; 
							System.out.println(instquery);
							insertStmnt.executeUpdate(instquery);
							
							//update field data also
							updquery = "update CASE_MSTR set USER_ID='"+userid+"' , decision='"+decsCode+"' , status='"+status+"' , stage='"+stage+"' , next_stage='"+nextstage+"' , created_date=sysdate() WHERE CASE_ID = "+caseid;
							System.out.println(updquery);
							insertStmnt.executeUpdate(updquery);
							
						}
						
					
						response.setContentType("text/html");  
					 
						System.out.println("Records inserted into CASE_MSTR table!");
					    
					    request.setAttribute("username", userid); 
						RequestDispatcher rd = request.getRequestDispatcher("Profile1.jsp");
					    rd.include(request,response);
						
						out.close();
					}
					
					
					
				    } catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}

				if(request.getParameter("ApendingSubmit")!=null){
					System.out.println("here");
				 try{
					con = LoginDAO.getConnectionDetails();
					callable = null;
					
					String userid = request.getParameter("username");
					String caseIdasString = request.getParameter("caseId");
					Long caseid = Long.parseLong(caseIdasString);
					
					
					String stageform = request.getParameter("NEXT_STAGE");
					
					System.out.println("stageform "+stageform);
					
					
					//int caseid = 12;
					String decision = request.getParameter("category2");
					
					ProfileVO profile3VO = LoginDAO.ProfileMapping(userid, decision,stageform,caseid);
					
					String status = profile3VO.getStatusCode();
					String stage = profile3VO.getStageCode();
					String nextstage = profile3VO.getNextStage();
					String decsCode = profile3VO.getDecisionCode();
					System.out.println(status);
					System.out.println(stage);
					System.out.println(nextstage);
					System.out.println(decsCode);
					System.out.println("userID::pendingSubmit-> " + userid);
					System.out.println("CaseID::pendingSubmit-> " + caseid);
					System.out.println("Decision::pendingSubmit-> " + decision);
					
					
					
					
					Statement insertStmnt = con.createStatement();
					String instquery = "INSERT INTO CASE_MSTR_HISTORY (USER_ID, CASE_ID, decision, status, stage, next_stage, created_date, field1, field2, field3, field4, field5, field6, field7, field8, field9,field10,initiated_Date) \r\n" + 
							"select USER_ID, CASE_ID, decision, status, stage, next_stage, created_date, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,sysdate() from CASE_MSTR where CASE_ID = "+caseid; 
					System.out.println(instquery);
					insertStmnt.executeUpdate(instquery);
					
					//String delquery = "DELETE FROM CASE_MSTR WHERE CASE_ID = "+caseid; 
					//System.out.println(delquery);
					//insertStmnt.executeUpdate(delquery);
					String updquery = "update CASE_MSTR set USER_ID='"+userid+"' , CASE_ID='"+caseid+"' , decision='"+decsCode+"' , status='"+status+"' , stage='"+stage+"' , next_stage='"+nextstage+"' , created_date=sysdate() WHERE CASE_ID = "+caseid;
					System.out.println(updquery);
					insertStmnt.executeUpdate(updquery);
					
					
			
					response.setContentType("text/html");  
				  
					System.out.println("Records inserted into CASE_MSTR table!");
				    
				    request.setAttribute("username", userid); 
					RequestDispatcher rd = request.getRequestDispatcher("Profile1.jsp");
				    rd.include(request,response);
					
					out.close();
					
				    } catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally {
			
			if (callable != null) {
	            // closes the database connection
	            try {
	            	callable.close();
	            	callable=null;
	            } catch (SQLException ex) {
	                          ex.printStackTrace();
	            }
		 }
			 if (rs != null) {
		            // closes the database connection
		            try {
		            	rs.close();
		            	rs=null;
		            } catch (SQLException ex) {
		                          ex.printStackTrace();
		            }
			 }
		            if (ps != null) {
			            // closes the database connection
			            try {
			            	ps.close();
			            	ps=null;
			            } catch (SQLException ex) {
			                          ex.printStackTrace();
			            }
		            }
			
	       if (con != null) {
	           // closes the database connection
	           try {
	               con.close();
	               con=null;
	           } catch (SQLException ex) {
	                         ex.printStackTrace();
	           }
	             
	       }           
	       }
		
		
	
	}
	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}
}
