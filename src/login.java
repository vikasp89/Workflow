import com.security.AES256;
import com.security.Base64Decoder;
import com.workflow.connection.LoginDAO;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"/login"})
public class login extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String passDecoder64 = "";
    String loginButton = request.getParameter("loginButton");
    HttpSession session = request.getSession();
    if (request.getParameter("registerButton") != null) {
      RequestDispatcher rd = request.getRequestDispatcher("Register.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } 
    if (loginButton != null) {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String userid = request.getParameter("user_id");
      request.setAttribute("username", userid);
      System.out.println("userid: "+userid);
      String pwd = request.getParameter("password");
      try {
        passDecoder64 = Base64Decoder.decodeString(pwd);
      } catch (Exception e1) {
        System.out.println(e1);
      } 
      String aes256 = AES256.encrypt(passDecoder64);
      AES256.encrypt(passDecoder64);
      
      System.out.println("shanti============ "+AES256.decrypt("u+q1gRViCzlVA/uLGFdsEA=="));
      
      if (LoginDAO.validate(userid, aes256)) {
        String profile = "";
        String initiationFlag = "";
        String sHold = "";
        
        try {
          profile = LoginDAO.checkProfile(userid);
          String[] profiles = profile.split("~");
          for (int i16 = 0; i16 < profiles.length; i16++);
          profile = profiles[0];
          initiationFlag = profiles[1];
          sHold = profiles[2];
        } catch (ClassNotFoundException e) {
          System.out.println("loginpage " + e);
        } catch (SQLException e) {
          System.out.println("loginpage " + e);
        } 
        session.setAttribute("profile", profile);
        session.setAttribute("userid", userid);
        System.out.println("login userid: "+userid);
        session.setAttribute("initiationFlag", initiationFlag);
        session.setAttribute("sHold", sHold);
        List<String> getRight = LoginDAO.getRight(userid);
        session.setAttribute("getRight", getRight);
        ServletContext context = getServletContext();
        String path = context.getRealPath("/WEB-INF/resources/data32watts.properties");
        FileReader reader = new FileReader(path);
        Properties p = new Properties();
        p.load(reader);
        List<String> Print_Type = new ArrayList<>();
        String[] array_Print_Type = p.getProperty("DprintingDecesion").split(",");
        byte b1;
        int i;
        String[] arrayOfString1;
        for (i = (arrayOfString1 = array_Print_Type).length, b1 = 0; b1 < i; ) {
          String a = arrayOfString1[b1];
          Print_Type.add(a);
          b1++;
        } 
        session.setAttribute("Print_Type", Print_Type);
        List<String> Decesion_Type = new ArrayList<>();
        String[] DecesionFQC_FQc = p.getProperty("DecesionFQc").split(",");
        byte b2;
        int j;
        String[] arrayOfString2;
        for (j = (arrayOfString2 = DecesionFQC_FQc).length, b2 = 0; b2 < j; ) {
          String a = arrayOfString2[b2];
          Decesion_Type.add(a);
          b2++;
        } 
        session.setAttribute("Decesion_Type", Decesion_Type);
        List<String> Planning_Type = new ArrayList<>();
        String[] arrOfStr_Planning_Type = 
          p.getProperty("Planning_Type").split(",");
        String[] arrayOfString3;
        int k = (arrayOfString3 = arrOfStr_Planning_Type).length;
        byte b3 = 0;
        for (; b3 < k; b3++) {
          String a = arrayOfString3[b3];
          Planning_Type.add(a);
        } 
        session.setAttribute("Planning_Type", Planning_Type);
        List<String> Decesionlab = new ArrayList<>();
        String[] Decesionlab_ary = p.getProperty("Decesionlab").split(",");
        byte b4;
        int m;
        String[] arrayOfString4;
        for (m = (arrayOfString4 = Decesionlab_ary).length, b4 = 0; b4 < m; ) {
          String a = arrayOfString4[b4];
          Decesionlab.add(a);
          b4++;
        } 
        session.setAttribute("Decesionlab", Decesionlab);
        List<String> PackingAPR = new ArrayList<>();
        String[] PackingAPR_ary = p.getProperty("PackingAPR").split(",");
        byte b5;
        int n;
        String[] arrayOfString5;
        for (n = (arrayOfString5 = PackingAPR_ary).length, b5 = 0; b5 < n; ) {
          String a = arrayOfString5[b5];
          PackingAPR.add(a);
          b5++;
        } 
        session.setAttribute("PackingAPR", PackingAPR);
        List<String> DispatchedDecesion = new ArrayList<>();
        String[] DispatchedDecesion_ary = p.getProperty("DispatchedDecesion").split(",");
        byte b6;
        int i1;
        String[] arrayOfString6;
        for (i1 = (arrayOfString6 = DispatchedDecesion_ary).length, b6 = 0; b6 < i1; ) {
          String a = arrayOfString6[b6];
          DispatchedDecesion.add(a);
          b6++;
        } 
        session.setAttribute("DispatchedDecesion", DispatchedDecesion);
        List<String> UploadDecesion = new ArrayList<>();
        String[] UploadDecesion_ary = p.getProperty("UploadDecesion").split(",");
        byte b7;
        int i2;
        String[] arrayOfString7;
        for (i2 = (arrayOfString7 = UploadDecesion_ary).length, b7 = 0; b7 < i2; ) {
          String a = arrayOfString7[b7];
          UploadDecesion.add(a);
          b7++;
        } 
        session.setAttribute("UploadDecesion", UploadDecesion);
        List<String> HallowDecesion = new ArrayList<>();
        String[] HallowDecesion_ary = p.getProperty("HallowDecesion").split(",");
        byte b8;
        int i3;
        String[] arrayOfString8;
        for (i3 = (arrayOfString8 = HallowDecesion_ary).length, b8 = 0; b8 < i3; ) {
          String a = arrayOfString8[b8];
          HallowDecesion.add(a);
          b8++;
        } 
        session.setAttribute("HallowDecesion", HallowDecesion);
        List<String> PrintDecesion = new ArrayList<>();
        String[] PrintDecesion_ary = p.getProperty("PrintDecesion").split(",");
        byte b9;
        int i4;
        String[] arrayOfString9;
        for (i4 = (arrayOfString9 = PrintDecesion_ary).length, b9 = 0; b9 < i4; ) {
          String a = arrayOfString9[b9];
          PrintDecesion.add(a);
          b9++;
        } 
        session.setAttribute("PrintDecesion", PrintDecesion);
        List<String> Mode_type = new ArrayList<>();
        String[] mode_ary = p.getProperty("Mode").split(",");
        byte b10;
        int i5;
        String[] arrayOfString10;
        for (i5 = (arrayOfString10 = mode_ary).length, b10 = 0; b10 < i5; ) {
          String a = arrayOfString10[b10];
          Mode_type.add(a);
          b10++;
        } 
        session.setAttribute("Mode_type", Mode_type);
        List<String> Planning_Review = new ArrayList<>();
        String[] arrOfStr_Planning_Review = p.getProperty("Planning_Review").split(",");
        System.out.println(arrOfStr_Planning_Review);
        String[] arrayOfString11;
        int i6 = (arrayOfString11 = arrOfStr_Planning_Review).length;
        byte b11 = 0;
        for (; b11 < i6; b11++) {
          String a = arrayOfString11[b11];
          Planning_Review.add(a);
        } 
        session.setAttribute("Planning_Review", Planning_Review);
        List<String> customerlist = new ArrayList<>();
        String[] arrOfStr = p.getProperty("Type.of.Customer").split(",");
        byte b12;
        int i7;
        String[] arrayOfString12;
        for (i7 = (arrayOfString12 = arrOfStr).length, b12 = 0; b12 < i7; ) {
          String a = arrayOfString12[b12];
          customerlist.add(a);
          b12++;
        } 
        session.setAttribute("customerlist", customerlist);
        List<String> packagelist = new ArrayList<>();
        String[] arrOfStr1 = p.getProperty("PACKAGE").split(",");
        byte b13;
        int i8;
        String[] arrayOfString13;
        for (i8 = (arrayOfString13 = arrOfStr1).length, b13 = 0; b13 < i8; ) {
          String a = arrayOfString13[b13];
          packagelist.add(a);
          b13++;
        } 
        session.setAttribute("packagelist", packagelist);
        List<String> kollist = new ArrayList<>();
        String[] arrOfStr2 = p.getProperty("KOL").split(",");
        byte b14;
        int i9;
        String[] arrayOfString14;
        for (i9 = (arrayOfString14 = arrOfStr2).length, b14 = 0; b14 < i9; ) {
          String a = arrayOfString14[b14];
          kollist.add(a);
          b14++;
        } 
        session.setAttribute("kollist", kollist);
        List<String> typeofaccountlist = new ArrayList<>();
        String[] arrOfStr3 = p.getProperty("Type.of.account").split(",");
        byte b15;
        int i10;
        String[] arrayOfString15;
        for (i10 = (arrayOfString15 = arrOfStr3).length, b15 = 0; b15 < i10; ) {
          String a = arrayOfString15[b15];
          typeofaccountlist.add(a);
          b15++;
        } 
        session.setAttribute("typeofaccountlist", typeofaccountlist);
        List<String> prioritylist = new ArrayList<>();
        String[] arrOfStr4 = p.getProperty("Priority").split(",");
        byte b16;
        int i11;
        String[] arrayOfString16;
        for (i11 = (arrayOfString16 = arrOfStr4).length, b16 = 0; b16 < i11; ) {
          String a = arrayOfString16[b16];
          prioritylist.add(a);
          b16++;
        } 
        session.setAttribute("prioritylist", prioritylist);
        List<String> citylist = new ArrayList<>();
        String[] arrOfStr5 = p.getProperty("City").split(",");
        byte b17;
        int i12;
        String[] arrayOfString17;
        for (i12 = (arrayOfString17 = arrOfStr5).length, b17 = 0; b17 < i12; ) {
          String a = arrayOfString17[b17];
          citylist.add(a);
          b17++;
        } 
        session.setAttribute("citylist", citylist);
        List<String> CRMLlist = new ArrayList<>();
        String[] arrOfStr6 = p.getProperty("CRM").split(",");
        byte b18;
        int i13;
        String[] arrayOfString18;
        for (i13 = (arrayOfString18 = arrOfStr6).length, b18 = 0; b18 < i13; ) {
          String a = arrayOfString18[b18];
          CRMLlist.add(a);
          b18++;
        } 
        session.setAttribute("CRMLlist", CRMLlist);
        List<String> TRequestlist = new ArrayList<>();
        String[] TRequestlist6 = p.getProperty("TRequest").split(",");
        byte b19;
        int i14;
        String[] arrayOfString19;
        for (i14 = (arrayOfString19 = TRequestlist6).length, b19 = 0; b19 < i14; ) {
          String a = arrayOfString19[b19];
          TRequestlist.add(a);
          b19++;
        } 
        session.setAttribute("TRequestlist", TRequestlist);
        List<String> Dispatch_ruleslist = new ArrayList<>();
        String[] Dispatch_rules6 = p.getProperty("Dispatch_rules").split(",");
        byte b20;
        int i15;
        String[] arrayOfString20;
        for (i15 = (arrayOfString20 = Dispatch_rules6).length, b20 = 0; b20 < i15; ) {
          String a = arrayOfString20[b20];
          Dispatch_ruleslist.add(a);
          b20++;
        } 
        session.setAttribute("Dispatch_ruleslist", Dispatch_ruleslist);
      } else {
        out.println("error");
      } 
      out.close();
    } 
  }
  
  public InputStream getResource(String resourcePath) {
    ServletContext servletContext = getServletContext();
    InputStream openStream = servletContext.getResourceAsStream(resourcePath);
    return openStream;
  }
}
