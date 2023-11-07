/*    */ import com.workflow.connection.LoginDAO;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.annotation.WebServlet;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ @WebServlet({"/NewFile"})
/*    */ public class NewFile extends HttpServlet {
/* 28 */   static final Logger LOGGER = LogManager.getLogger(NewFile.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 35 */     response.setContentType("text/html");
/* 36 */     PrintWriter out = response.getWriter();
/* 37 */     HttpSession session = request.getSession(false);
/* 38 */     String n = (String)session.getAttribute("userid");
/* 39 */     String nprofile = (String)session.getAttribute("profile");
/* 40 */     System.out.println(nprofile);
/* 41 */     System.out.println("usernameq " + n);
/* 42 */     request.setAttribute("username", n);
/* 43 */     request.setAttribute("loginusername", n);
/* 44 */     List<String> declist = new ArrayList<>();
/* 45 */     String listofjs = "";
/* 46 */     String sDecision = "";
/*    */     try {
/* 48 */       declist = LoginDAO.getdecisionprofile();
/* 49 */       listofjs = LoginDAO.geteditables("workorder");
/* 51 */     } catch (ClassNotFoundException e1) {
/* 52 */       LOGGER.info("Error At AddComment=" + e1.getMessage());
/* 55 */     } catch (SQLException e1) {
/* 56 */       LOGGER.info("Error At AddComment=" + e1.getMessage());
/*    */     } 
/* 60 */     for (int i = 0; i < declist.size(); i++)
/* 61 */       sDecision = String.valueOf(sDecision) + (String)declist.get(i) + "~"; 
/* 64 */     sDecision = sDecision.substring(0, sDecision.length() - 1);
/* 66 */     request.setAttribute("sDecision", sDecision);
/* 67 */     request.setAttribute("currentstage", listofjs);
/* 70 */     String[] decArr = sDecision.split("~");
/* 71 */     for (int j = 0; j < decArr.length; j++);
/* 74 */     RequestDispatcher rd = request.getRequestDispatcher("NewFile.jsp");
/* 75 */     rd.forward((ServletRequest)request, (ServletResponse)response);
/*    */   }
/*    */ }


/* Location:              C:\Users\DELL\Desktop\server project\Workflow\WEB-INF\classes\!\NewFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */