/*    */ import java.io.IOException;
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
/*    */ @WebServlet({"/ShowDragDropImages"})
/*    */ public class ShowDragDropImages extends HttpServlet {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 27 */   static final Logger LOGGER = LogManager.getLogger(ShowDragDropImages.class);
/*    */   
/*    */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 44 */     String id = request.getParameter("id");
/* 45 */     String caseId = request.getParameter("caseId");
/* 46 */     HttpSession session = request.getSession();
/* 51 */     RequestDispatcher rd = request.getRequestDispatcher("showimages.jsp");
/* 52 */     rd.forward((ServletRequest)request, (ServletResponse)response);
/*    */   }
/*    */   
/*    */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 60 */     doGet(request, response);
/*    */   }
/*    */ }


/* Location:              C:\Users\DELL\Desktop\server project\Workflow\WEB-INF\classes\!\ShowDragDropImages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */