import com.vo.PlanningVO;
import com.vo.StagingVo;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"/SelectStaging"})
public class SelectStaging extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    String n = request.getParameter("caseId");
    String crm = request.getParameter("crm");
    String patient_Name = request.getParameter("patient_Name");
    String cdoc = request.getParameter("cdoc");
    HttpSession session = request.getSession();
    List<StagingVo> stagingVo = new ArrayList<>();
    List<PlanningVO> planningVO = new ArrayList<>();
    String crm_priority = "";
    Connection con = null;
    Connection con5 = null;
    PreparedStatement ps = null;
    PreparedStatement ps0 = null;
    PreparedStatement plnps = null;
    ResultSet plnrs = null;
    ResultSet rs0 = null;
    ResultSet rs = null;
    try {
      con = LoginDAO.getConnectionDetails();
      con5 = LoginDAO.getConnectionDetails();
      ps0 = con.prepareStatement(" select * from cc_crm where Case_Id=?");
      ps0.setString(1, n);
      rs0 = ps0.executeQuery();
      while (rs0.next())
        crm_priority = rs0.getString("priority"); 
      ps = con.prepareStatement(" select * from stagging where caseid=?");
      ps.setString(1, n);
      rs = ps.executeQuery();
      while (rs.next()) {
        StagingVo stvo = new StagingVo();
        stvo.setPlanning_id(rs.getString("planning_id"));
        stvo.setPlanning_type(rs.getString("planning_type"));
        stvo.setD_name(rs.getString("d_name"));
        stvo.setPatient_name(rs.getString("patient_name"));
        stvo.setCrm(rs.getString("crm"));
        stvo.setUpper_aligner_from(rs.getString("upper_aligner_from"));
        stvo.setUpper_aligner_to(rs.getString("upper_aligner_to"));
        stvo.setLower_aligner_from(rs.getString("lower_aligner_from"));
        stvo.setLower_aligner_to(rs.getString("lower_aligner_to"));
        stvo.setThick_upper(rs.getString("thick_upper"));
        stvo.setThick_lower(rs.getString("thick_lower"));
        stvo.setMargin_upper(rs.getString("margin_upper"));
        stvo.setMargin_lower(rs.getString("margin_lower"));
        stvo.setMolor_upper(rs.getString("molor_upper"));
        stvo.setMolor_lower(rs.getString("molor_lower"));
        stvo.setSheet_type(rs.getString("sheet_type"));
        stagingVo.add(stvo);
      } 
      plnps = con5.prepareStatement(" select p.planned_no,p.planning_type,p.upper_aligner_from,p.upper_aligner_to,p.lower_aligner_from,p.lower_aligner_to from planning as p ,cc_crm as c where p.case_id='" + n + "'" + 
          " and p.planning_id=c.planning_id");
      plnrs = plnps.executeQuery();
      while (plnrs.next()) {
        System.out.println("inside while :: " + plnrs.getString("planned_no"));
        PlanningVO plnVO = new PlanningVO();
        plnVO.setPlanned_no(plnrs.getString("planned_no"));
        plnVO.setPlanning_type(plnrs.getString("planning_type"));
        plnVO.setUpper_aligner_from(plnrs.getString("upper_aligner_from"));
        plnVO.setUpper_aligner_to(plnrs.getString("upper_aligner_to"));
        plnVO.setLower_aligner_from(plnrs.getString("lower_aligner_from"));
        plnVO.setLower_aligner_to(plnrs.getString("lower_aligner_to"));
        planningVO.add(plnVO);
      } 
      session.setAttribute("previousotpln", planningVO);
      session.setAttribute("previousstagging", stagingVo);
      session.setAttribute("cccrmpriority", crm_priority);
    } catch (Exception e) {
      System.out.println("Message  : " + e.getMessage());
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (Exception exception) {
        
        } finally {
          rs = null;
        }  
      if (rs0 != null)
        try {
          rs0.close();
        } catch (Exception exception) {
        
        } finally {
          rs0 = null;
        }  
      if (plnrs != null)
        try {
          plnrs.close();
        } catch (Exception exception) {
        
        } finally {
          plnrs = null;
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (Exception exception) {
        
        } finally {
          ps = null;
        }  
      if (ps0 != null)
        try {
          ps0.close();
        } catch (Exception exception) {
        
        } finally {
          ps0 = null;
        }  
      if (plnps != null)
        try {
          plnps.close();
        } catch (Exception exception) {
        
        } finally {
          plnps = null;
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception exception) {
        
        } finally {
          con = null;
        }  
      if (con5 != null)
        try {
          con5.close();
        } catch (Exception exception) {
        
        } finally {
          con5 = null;
        }  
    } 
    response.sendRedirect("newplanning.jsp?caseId=" + n + "&crm=" + crm + "&cdoc=" + cdoc + "&patient_Name=" + patient_Name);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
