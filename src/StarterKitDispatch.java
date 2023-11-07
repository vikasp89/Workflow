/*     */ import com.vo.AddresshandlerVO;
/*     */ import com.workflow.connection.LoginDAO;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.annotation.WebServlet;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ @WebServlet({"/StarterKitDispatch"})
/*     */ public class StarterKitDispatch extends HttpServlet {
/*  23 */   static final Logger LOGGER = LogManager.getLogger(Multifrm.class);
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  43 */     Connection con = null;
/*  44 */     PreparedStatement pstmt = null;
/*  45 */     ResultSet rs = null;
/*  47 */     String code_id = request.getParameter("caseId");
/*  48 */     String crm = request.getParameter("crm");
/*  49 */     String patient_Name = request.getParameter("patient_Name");
/*  50 */     String cdoc = request.getParameter("cdoc");
/*  53 */     List<AddresshandlerVO> addresshandlervo = new ArrayList<>();
/*     */     try {
/*  56 */       con = LoginDAO.getConnectionDetails();
/*  58 */       pstmt = con.prepareStatement(" select * from cc_crm where case_id=?");
/*  59 */       pstmt.setString(1, code_id);
/*  60 */       rs = pstmt.executeQuery();
/*  61 */       while (rs.next()) {
/*  62 */         AddresshandlerVO address = new AddresshandlerVO();
/*  64 */         address.setAddress1(rs.getString("address1"));
/*  65 */         address.setAddress2(rs.getString("address2"));
/*  66 */         address.setAddress3(rs.getString("address3"));
/*  67 */         address.setAddress4(rs.getString("address4"));
/*  68 */         address.setAddress5(rs.getString("address5"));
/*  70 */         address.setCity1(rs.getString("city"));
/*  71 */         address.setCity2(rs.getString("city2"));
/*  72 */         address.setCity3(rs.getString("city3"));
/*  73 */         address.setCity4(rs.getString("city4"));
/*  74 */         address.setCity5(rs.getString("city5"));
/*  76 */         address.setPhone1(rs.getString("phone1"));
/*  77 */         address.setPhone2(rs.getString("phone2"));
/*  78 */         address.setPhone3(rs.getString("phone3"));
/*  79 */         address.setPhone4(rs.getString("phone4"));
/*  80 */         address.setPhone5(rs.getString("phone5"));
/*  82 */         address.setPincode1(rs.getString("pincode1"));
/*  83 */         address.setPincode2(rs.getString("pincode2"));
/*  84 */         address.setPincode3(rs.getString("pincode3"));
/*  85 */         address.setPincode4(rs.getString("pincode4"));
/*  86 */         address.setPincode5(rs.getString("pincode5"));
/*  88 */         address.setLocation1(rs.getString("location"));
/*  89 */         address.setLocation2(rs.getString("location2"));
/*  90 */         address.setLocation3(rs.getString("location3"));
/*  91 */         address.setLocation4(rs.getString("location4"));
/*  92 */         address.setLocation5(rs.getString("location5"));
/*  93 */         address.setDefault_starterkit(rs.getString("default_starterkit"));
/*  95 */         addresshandlervo.add(address);
/*  96 */         System.out.println("address size str :: " + addresshandlervo.size());
/*     */       } 
/*  98 */       request.setAttribute("addresshandler2", addresshandlervo);
/* 101 */     } catch (Exception e) {
/* 103 */       e.printStackTrace();
/*     */     } finally {
/* 107 */       if (rs != null)
/*     */         try {
/* 107 */           rs.close();
/*     */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 107 */           rs = null;
/*     */         }  
/* 108 */       if (pstmt != null)
/*     */         try {
/* 108 */           pstmt.close();
/*     */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 108 */           pstmt = null;
/*     */         }  
/* 109 */       if (con != null)
/*     */         try {
/* 109 */           con.close();
/*     */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 109 */           con = null;
/*     */         }  
/*     */     } 
/* 112 */     RequestDispatcher rd = request.getRequestDispatcher(
/* 113 */         "starterkitdispatch.jsp?caseId=" + code_id + "&crm=" + crm + "&patient_Name=" + patient_Name + "&cdoc=" + cdoc);
/* 114 */     rd.forward((ServletRequest)request, (ServletResponse)response);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 120 */     doGet(request, response);
/*     */   }
/*     */ }


/* Location:              C:\Users\DELL\Desktop\server project\Workflow\WEB-INF\classes\!\StarterKitDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */