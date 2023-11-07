/*     */ import com.workflow.connection.LoginDAO;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.annotation.WebServlet;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ @WebServlet({"/NewCasesshow"})
/*     */ public class NewCasesshow extends HttpServlet {
/*  24 */   static final Logger LOGGER = LogManager.getLogger(NewCasesshow.class);
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  41 */     doPost(request, response);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  50 */     PrintWriter out = response.getWriter();
/*  52 */     Connection con = null;
/*  53 */     PreparedStatement pstmt = null;
/*  54 */     PreparedStatement pstmt1 = null;
/*  55 */     PreparedStatement pstmt2 = null;
/*  57 */     ResultSet rs = null;
/*  58 */     String sql = "";
/*  59 */     String query = "";
/*  60 */     String password = "";
/*  61 */     String decline = "";
/*  63 */     String loguser = "";
/*  64 */     String caseid = "";
/*  65 */     HttpSession session = request.getSession();
/*  67 */     password = (request.getParameter("password") == null) ? "" : request.getParameter("password");
/*  68 */     caseid = (request.getParameter("caseid") == null) ? "" : request.getParameter("caseid");
/*  69 */     query = (request.getParameter("pass") == null) ? "" : request.getParameter("pass");
/*  70 */     decline = (request.getParameter("decline") == null) ? "" : request.getParameter("decline");
/*     */     try {
/*  74 */       con = LoginDAO.getConnectionDetails();
/*  76 */       if (LoginDAO.validate((String)session.getAttribute("userid"), password)) {
/*  79 */         sql = " UPDATE patient_details SET decline_reason='" + decline + "' , decline_status='Y' where case_id='" + caseid + "' ";
/*  80 */         pstmt = con.prepareStatement(sql);
/*  81 */         int i = pstmt.executeUpdate();
/*  83 */         if (i > 0) {
/*  86 */           pstmt2 = con.prepareStatement("insert into decision_history(decision, Remarks, date_time, stage, UserId, caseid) values('Decline','" + decline + "',sysdate(),'Decline','" + (String)session.getAttribute("userid") + "','" + caseid + "')");
/*  87 */           pstmt2.execute();
/*  89 */           sql = " UPDATE newcase SET  flag='n' where caseid='" + caseid + "' ";
/*  90 */           pstmt1 = con.prepareStatement(sql);
/*  91 */           int j = pstmt1.executeUpdate();
/*  92 */           if (j > 0) {
/*  95 */             out.println("<script type=\"text/javascript\">");
/*  96 */             out.println("alert('" + caseid + " has been successfully Decline!');");
/*  97 */             out.println("location='Dashboard.jsp';");
/*  98 */             out.println("</script>");
/*     */           } else {
/* 101 */             out.println("<script type=\"text/javascript\">");
/* 102 */             out.println("alert('ERROR OCCUR !');");
/* 103 */             out.println("location='Dashboard.jsp';");
/* 104 */             out.println("</script>");
/*     */           } 
/*     */         } 
/*     */       } else {
/* 114 */         out.println("<script type=\"text/javascript\">");
/* 115 */         out.println("alert('Wrong Password');");
/* 116 */         out.println("location='Dashboard.jsp';");
/* 117 */         out.println("</script>");
/*     */       } 
/* 121 */     } catch (Exception e) {
/* 122 */       LOGGER.info("Error At AddComment=" + e.getMessage());
/*     */     } finally {
/* 128 */       if (rs != null)
/*     */         try {
/* 128 */           rs.close();
/*     */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 128 */           rs = null;
/*     */         }  
/* 129 */       if (pstmt != null)
/*     */         try {
/* 129 */           pstmt.close();
/*     */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 129 */           pstmt = null;
/*     */         }  
/* 130 */       if (pstmt2 != null)
/*     */         try {
/* 130 */           pstmt2.close();
/*     */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 130 */           pstmt2 = null;
/*     */         }  
/* 131 */       if (con != null)
/*     */         try {
/* 131 */           con.close();
/*     */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 131 */           con = null;
/*     */         }  
/* 132 */       out.flush();
/* 133 */       System.gc();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DELL\Desktop\server project\Workflow\WEB-INF\classes\!\NewCasesshow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */