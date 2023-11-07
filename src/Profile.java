import com.vo.PendingVO;
import com.vo.ViewVO;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profile extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Profile.class);
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String n = request.getParameter("username");
    String nprofile = request.getParameter("profile");
    request.setAttribute("username", n);
    List<String> declist = new ArrayList<>();
    String sDecision = "";
    if (request.getParameter("createButton") != null) {
      RequestDispatcher rd = request.getRequestDispatcher("Create.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } 
    if (request.getParameter("viewButton") != null)
      try {
        List<ViewVO> viewVO = LoginDAO.getCase(n);
        request.setAttribute("viewVOList", viewVO);
        if (viewVO.size() == 0)
          out.println("No pending Case IDs found!"); 
        RequestDispatcher rd = request.getRequestDispatcher("View.jsp");
        rd.include((ServletRequest)request, (ServletResponse)response);
      } catch (ClassNotFoundException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      } catch (SQLException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      }  
    if (request.getParameter("pendingButton") != null)
      try {
        HttpSession session = request.getSession();
        String profile = (String)session.getAttribute("profile");
        List<PendingVO> pendingVO = LoginDAO.getCaseID(n, profile);
        request.setAttribute("pendingVOList", pendingVO);
        RequestDispatcher rd = request.getRequestDispatcher("Pending.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } catch (ClassNotFoundException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      } catch (SQLException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      }  
    if (request.getParameter("apendingButton") != null)
      try {
        List<PendingVO> pendingVO = LoginDAO.getCaseIDapproved(n);
        request.setAttribute("pendingVOList", pendingVO);
        RequestDispatcher rd = request.getRequestDispatcher("APending.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } catch (ClassNotFoundException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      } catch (SQLException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      }  
    if (request.getParameter("SearchButton") != null)
      try {
        String caseId = request.getParameter("caseid");
        List<ViewVO> viewVO = LoginDAO.searchCase(caseId);
        List<String> stageList = new ArrayList<>();
        HttpSession session = request.getSession();
        String profile = (String)session.getAttribute("profile");
        stageList = LoginDAO.stageList(profile);
        String nxtstage = "";
        List<PendingVO> pendingVOList = new ArrayList<>();
        List<ViewVO> ViewVOList = new ArrayList<>();
        for (int i = 0; i < viewVO.size(); i++) {
          nxtstage = ((ViewVO)viewVO.get(i)).getNext_stage();
          if (stageList.contains(nxtstage.toLowerCase())) {
            PendingVO pendingVO = new PendingVO();
            pendingVO.setCaseid(((ViewVO)viewVO.get(i)).getCaseid());
            pendingVO.setUserid(((ViewVO)viewVO.get(i)).getUserid());
            pendingVO.setDecision(((ViewVO)viewVO.get(i)).getDecision());
            pendingVO.setStatus(((ViewVO)viewVO.get(i)).getStatus());
            pendingVO.setStage(((ViewVO)viewVO.get(i)).getStage());
            pendingVO.setNext_stage(((ViewVO)viewVO.get(i)).getNext_stage());
            pendingVO.setCraeted(((ViewVO)viewVO.get(i)).getCraeted());
            pendingVO.setInitDate(((ViewVO)viewVO.get(i)).getInitDate());
            pendingVO.setInituserid(((ViewVO)viewVO.get(i)).getInituserid());
            pendingVOList.add(pendingVO);
          } else {
            ViewVO viewVO1 = new ViewVO();
            viewVO1.setCaseid(((ViewVO)viewVO.get(i)).getCaseid());
            viewVO1.setUserid(((ViewVO)viewVO.get(i)).getUserid());
            viewVO1.setDecision(((ViewVO)viewVO.get(i)).getDecision());
            viewVO1.setStatus(((ViewVO)viewVO.get(i)).getStatus());
            viewVO1.setStage(((ViewVO)viewVO.get(i)).getStage());
            viewVO1.setNext_stage(((ViewVO)viewVO.get(i)).getNext_stage());
            viewVO1.setCraeted(((ViewVO)viewVO.get(i)).getCraeted());
            viewVO1.setInitDate(((ViewVO)viewVO.get(i)).getInitDate());
            viewVO1.setInituserid(((ViewVO)viewVO.get(i)).getInituserid());
            ViewVOList.add(viewVO1);
          } 
        } 
        request.setAttribute("pendingVOList", pendingVOList);
        request.setAttribute("viewVOList", ViewVOList);
        RequestDispatcher rd = request.getRequestDispatcher("SearchDisplay.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } catch (ClassNotFoundException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      } catch (SQLException e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      }  
    if (request.getParameter("UploadButton") != null)
      try {
        List<PendingVO> pendingVO = LoginDAO.getCaseIDupload(n);
        request.setAttribute("pendingVOList", pendingVO);
        RequestDispatcher rd = request.getRequestDispatcher("Upload.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } catch (Exception e) {
        LOGGER.info("Error At AddComment=" + e.getMessage());
      }  
    if (request.getParameter("DownloadButton") != null)
      try {
        List<PendingVO> pendingVO = LoginDAO.getCaseIDDownload(n);
        request.setAttribute("pendingVOList", pendingVO);
        RequestDispatcher rd = request.getRequestDispatcher("Download.jsp");
        rd.forward((ServletRequest)request, (ServletResponse)response);
      } catch (Exception e) {
        e.printStackTrace();
      }  
  }
}
