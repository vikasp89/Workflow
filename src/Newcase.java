

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.vo.PendingVO;
import com.workflow.connection.LoginDAO;
/**
 * Servlet implementation class Newcase
 */
@WebServlet("/Newcase")
public class Newcase extends HttpServlet {
	static final Logger LOGGER = LogManager.getLogger(Newcase.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter(); 
		HttpSession session= request.getSession(false);
		String n = (String)session.getAttribute("userid");
		String nprofile = (String)session.getAttribute("profile");
				 request.setAttribute("username", n);
		 request.setAttribute("loginusername", n);
		 List<com.vo.Newcase> list = new ArrayList<com.vo.Newcase>();
		 Connection con=null;
		 ResultSet rs=null;
			
		 try {
			 con = LoginDAO.getConnectionDetails();
				
			 PreparedStatement ps=con.prepareStatement("select * from newcase where flag='y'");
			 rs=ps.executeQuery();
			 while(rs.next()){
				 
				 com.vo.Newcase nc=new com.vo.Newcase();
				 nc.setCaseid(rs.getString("caseid"));
				 nc.setDate(rs.getString("date"));
				 nc.setClinicname("clinicname");
				 nc.setDoctorname("doctorname");
				 nc.setPatientname("patientname");
				 list.add(nc);
			 }
			 
			 request.setAttribute("newcaselist", list);
				
			 RequestDispatcher rd=request.getRequestDispatcher("newcase.jsp");  
		     rd.forward(request,response); 
		
				
			 
			 
		 }
		 catch(Exception e) {
			 LOGGER.info("Error At AddComment="+e.getMessage());
 
			 
		 }
		finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				 LOGGER.info("Error At AddComment="+e.getMessage());
	}
		}
		 
		
	}
	

}
