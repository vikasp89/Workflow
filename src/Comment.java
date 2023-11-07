

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.workflow.connection.LoginDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class Newcase1
 */
@WebServlet("/Comment")
public class Comment extends HttpServlet {
	static final Logger LOGGER = LogManager.getLogger(Comment.class);

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter(); 
		HttpSession session= request.getSession(false);
		String n = (String)session.getAttribute("userid");
		String caseid="";
		String nprofile = (String)session.getAttribute("profile");
		System.out.println(nprofile);
		System.out.println("usernameq "+n);
		
		 request.setAttribute("username", n);
		 request.setAttribute("loginusername", n);
		 
		 List<com.vo.Comment> list = new ArrayList<com.vo.Comment>();
		 Connection con=null;
		 ResultSet rs=null;
		System.out.println("Newcase1     ");
		 try {
			 con = LoginDAO.getConnectionDetails();
				
			 PreparedStatement ps=con.prepareStatement(" select * from  comment  where status='N'");
			 rs=ps.executeQuery();
			 while(rs.next()){ 
				 com.vo.Comment nc=new com.vo.Comment();
				 nc.setCase_id(rs.getString("case_id"));
				 nc.setComment(rs.getString("Comment"));
				 nc.setUser_name(rs.getString("user_name"));
				 nc.setDate(rs.getString("date"));
				 nc.setSeen_at(rs.getString("seen_at"));
				 nc.setSeen_by(rs.getString("seen_by"));
			    nc.setComment_id(rs.getInt("comment_id"));
			    nc.setStatus(rs.getString("status"));
			    nc.setStage(rs.getString("stage"));
			    caseid=rs.getString("case_id");
			    
				 list.add(nc);
			 }
			 System.out.println("caseid=="+caseid);
			 request.setAttribute("commentlist", list);
			 RequestDispatcher rd=request.getRequestDispatcher("comment.jsp");  
		     rd.forward(request,response);  
			 
		 }
		 catch(Exception e) {
			 
				LOGGER.info("Error At AddComment="+e.getMessage());
	 
		 }
		finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.info("Error At AddComment="+e.getMessage());
			}
		}
		 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
