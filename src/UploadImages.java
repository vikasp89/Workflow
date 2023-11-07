import com.workflow.connection.LoginDAO;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/UploadImages"})
public class UploadImages extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

static final Logger LOGGER = LogManager.getLogger(UploadImages.class);
  
  private boolean isMultipart;
  
  private String filePath;
  
  private String tempPath;
  
  private int maxFileSize = 5632000;
  
  private int maxMemSize = 1126400;
  
  private File file;
  
  private static final SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    PrintWriter out = res.getWriter();
    Properties prop = new Properties();
    InputStream input = null;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    ServletContext context = getServletContext();
    String path = context.getRealPath("/WEB-INF/resources/data32watts.properties");
    FileReader reader = new FileReader(path);
    Properties p = new Properties();
    p.load(reader);
    this.filePath = p.getProperty("uploadImagesFolder");
    this.isMultipart = ServletFileUpload.isMultipartContent(req);
    req.setAttribute("isMultipart", Boolean.valueOf(this.isMultipart));
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setRepository(new File(this.filePath));
    ServletFileUpload upload = new ServletFileUpload((FileItemFactory)factory);
    String case_id = null;
    String id = null;
    String folder_name = "";
    String doctor_name = "";
    String patient_name = "";
    String clinic_name = "";
    String options = "";
    String loginUser = (String)req.getSession().getAttribute("userid");
    String str = "";
    String fullpath = "";
    String timeStamp = (new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss")).format(new Date());
    try {
      List fileItems = upload.parseRequest(req);
      Iterator<FileItem> i = fileItems.iterator();
      String[] columns = { "upper_img", "lower_img", "front_img", "left_img", "right_img", "simple_img", "simle_img", "side_img", "pdf_doc" };
      String[] values = new String[9];
      int count = 0;
      Connection con = null;
      con = LoginDAO.getConnectionDetails();
      while (i.hasNext()) {
        count++;
        FileItem fi = i.next();
        if (fi.isFormField()) {
          String itemname = fi.getFieldName();
          if (itemname.equals("case_id"))
            case_id = fi.getString(); 
          if (itemname.equals("doctor_name"))
            doctor_name = fi.getString(); 
          if (itemname.equals("patient_name"))
            patient_name = fi.getString(); 
          if (itemname.equals("clinic_name"))
            clinic_name = fi.getString(); 
          if (itemname.equals("options"))
            options = fi.getString(); 
          continue;
        } 
        String fileName = fi.getName();
        if (fileName != "") {
          values[count - 6] = fileName;
          str = String.valueOf(this.filePath) + case_id + '_' + timeStamp;
          fullpath = str;
          this.file = new File(str);
          str = this.file.getAbsolutePath();
          if (!this.file.exists())
            this.file.mkdir(); 
          if (fileName.lastIndexOf("\\") >= 0) {
            str = String.valueOf(str) + '/' + fileName.substring(fileName.lastIndexOf("\\"));
            this.file = new File(str);
          } else {
            str = String.valueOf(str) + '/' + fileName.substring(fileName.lastIndexOf("\\") + 1);
            this.file = new File(str);
          } 
          fi.write(this.file);
        } 
      } 
      try {
        Statement st = null;
        st = con.createStatement();
        folder_name = "'" + folder_name + "'";
        String query = "insert into uploadsimages(case_id,doctor_name,patient_name,clinic_name,options,user_id,file_path,date";
        int j;
        for (j = 0; j < columns.length; j++)
          query = String.valueOf(query) + "," + columns[j]; 
        query = String.valueOf(query) + ") values('" + case_id + "','" + doctor_name + "','" + patient_name + "','" + clinic_name + "','" + options + "','" + loginUser + "','" + fullpath + "',now() ";
        for (j = 0; j < values.length; j++) {
          if (values[j] != null) {
            query = String.valueOf(query) + ",'" + values[j] + "'";
          } else {
            query = String.valueOf(query) + "," + values[j];
          } 
        } 
        query = String.valueOf(query) + ")";
        PreparedStatement ps = con.prepareStatement(query);
        int row = ps.executeUpdate();
        if (row > 0) {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Photo Uploaded Successfully');");
          out.println("location='Case_Stage';");
          out.println("</script>");
        } else {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('Error occur while uploading photos);");
          out.println("location='Case_Stage';");
          out.println("</script>");
        } 
      } catch (Exception e) {
        LOGGER.info("Error At " + getClass().getSimpleName() + " Message1 " + e.getMessage());
        e.printStackTrace();
      } finally {
        con.close();
      } 
    } catch (Exception ex) {
      LOGGER.info("Error At " + getClass().getSimpleName() + " Message2 " + ex.getMessage());
    } 
  }
}
