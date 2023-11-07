import com.vo.Newcase1;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/ShowPendingCases"})
public class ShowPendingCases extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(ShowPendingCases.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(false);
    PreparedStatement ps1 = null;
    String dp = (request.getParameter("casestage") == null) ? "" : request.getParameter("casestage");
    String sp = "";
    String type = "";
    int day = 0;
    String sql = "";
    if (dp.equals("INITIATION CORRECTION")) {
        dp = "'INICOR'";
      }else if (dp.equals("WORK FLOW CHECK")) {
      dp = "'WFCCOR','INI'";
		} /*
			 * else if (dp.equals("CASE INITIATED")) { dp = "'INICOR'"; }
			 */ else if (dp.equals("PREREQUISITE CHECK")) {
      dp = "'PRE','WFC'";
    } else if (dp.equals("CAD BAISING SEGMENTATION")) {
      dp = "'WFC'";
    } else if (dp.equals("PLANNING")) {
      dp = "'PLNCOR','CADBS'";
    } else if (dp.equals("UPLOAD")) {
      dp = "'PLN','UPLCOR'";
    } else if (dp.equals("REVIEW")) {
      dp = "'UPL'";
    } else if (dp.equals("PENDING FOR SIGN OFF")) {
      dp = "'REV'";
    } else if (dp.equals("STAGING")) {
      dp = "'QA'";
    } else if (dp.equals("HOLLOW TAGING")) {
      dp = "'STG'";
    } else if (dp.equals("3D PRINTING")) {
      dp = "'MPT','3DPCOR'";
    } else if (dp.equals("LAB")) {
      dp = "'3DP','LABCOR'";
    } else if (dp.equals("PACKING")) {
      dp = "'FQC','PCKCOR'";
    } else if (dp.equals("DISPATCH")) {
      dp = "'PCK'";
    } else if (dp.equals("FINISHING QUALITY CHECK")) {
      dp = "'LAB'";
    } else if (dp.equals("REJECTED")) {
      dp = "'REJ'";
    } else if (dp.equals("HOLLOW TAGING STARTER KIT")) {
      sp = "'INISTRKIT'";
    } else if (dp.equals("3D PRINTING STARTER KIT")) {
      sp = "'MTPSTRKIT','3DPSTRKITCOR'";
    } else if (dp.equals("LAB STARTER KIT")) {
      sp = "'3DPSTRKIT','LABSTRKITCOR'";
    } else if (dp.equals("FINISHING QUALITY CHECK STARTER KIT")) {
      sp = "'LABSTRKIT'";
    } else if (dp.equals("PACKING STARTER KIT")) {
      sp = "'FQCSTRKIT','PCKSTRKITCOR'";
    } else if (dp.equals("DISPATCH STARTER KIT")) {
      sp = "'PCKSTRKIT'";
    } 
    String[] parts = dp.split(",");
    String[] partsstkit = sp.split(",");
    int arrayLength = parts.length;
    int arrayLength1 = partsstkit.length;
    List<Newcase1> list = new ArrayList<>();
    Connection con = null;
    ResultSet rs = null;
    try {
      con = LoginDAO.getConnectionDetails();
      String query = "";
      if (sp.equals("") || sp == null) {
        type = "";
       
        if (dp.equals("'INICOR'")) {
          query = "select * ,concat(FLOOR(HOUR(TIMEDIFF(inicor_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(inicor_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(inicor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(inicor_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in(" + dp + ") and holdflag <>'Y' ORDER BY priority DESC";
        }
        else if (dp.equals("'WFCCOR','INI'")) {
            query = "select * ,concat(FLOOR(HOUR(TIMEDIFF(wfccor_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(wfccor_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(ini_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(ini_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in(" + dp + ") and holdflag <>'Y' ORDER BY priority DESC";
          }
        else if (dp.equals("'INICOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(inicor_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(inicor_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'PRE'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(pre_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(pre_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(wfc_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(wfc_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'WFC'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(wfc_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(wfc_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ")  and holdflag <>'Y'  ORDER BY priority DESC ";
        } 
        if (dp.equals("'PLNCOR','CADBS'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(plncor_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(plncor_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(cadbs_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(cadbs_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'PLN','UPLCOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(pln_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(pln_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(uplcor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(uplcor_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'UPL'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(upl_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(upl_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'REV'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(rev_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(rev_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'QA'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(qa_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(qa_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'STG'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(stg_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(stg_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'MPT'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(mpt_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(mpt_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'MPT','3DPCOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(mpt_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(mpt_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(3dpcor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(3dpcor_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'3DP','LABCOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(3dp_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(3dp_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(labcor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(labcor_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'FQC','PCKCOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(fqc_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(fqc_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(pckcor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(pckcor_at,now())), 24)) AS HOURS1  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'LAB'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(fqc_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(fqc_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'PCK'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(pck_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(pck_at,now())), 24)) AS HOURS  FROM cc_crm where case_stage in (" + dp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (dp.equals("'REJ'")) {
          query = " SELECT  *   FROM cc_crm where case_stage='REJ' and holdflag <>'Y' ORDER BY priority DESC ";
        } 
      } else {
        type = "STRKIT";
        if (sp.equals("'INISTRKIT'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(inistrkit_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(inistrkit_at,now())), 24)) AS HOURS  FROM cc_crm where starter_case_stage in (" + sp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (sp.equals("'MTPSTRKIT','3DPSTRKITCOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(mtpstrkit_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(mtpstrkit_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(3dpstrkitcor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(3dpstrkitcor_at,now())), 24)) AS HOURS1  FROM cc_crm where starter_case_stage in (" + sp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (sp.equals("'3DPSTRKIT','LABSTRKITCOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(3dpstrkit_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(3dpstrkit_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(labstrkitcor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(labstrkitcor_at,now())), 24)) AS HOURS1  FROM cc_crm where starter_case_stage in (" + sp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (sp.equals("'LABSTRKIT'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(labstrkit_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(labstrkit_at,now())), 24)) AS HOURS  FROM cc_crm where starter_case_stage in (" + sp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (sp.equals("'FQCSTRKIT','PCKSTRKITCOR'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(fqcstrkit_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(fqcstrkit_at,now())), 24)) AS HOURS,concat(FLOOR(HOUR(TIMEDIFF(pckstrkitcor_at,now())) / 24)) AS DAYS1,concat(MOD(HOUR(TIMEDIFF(pckstrkitcor_at,now())), 24)) AS HOURS1  FROM cc_crm where starter_case_stage in (" + sp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } else if (sp.equals("'PCKSTRKIT'")) {
          query = " SELECT  * ,concat(FLOOR(HOUR(TIMEDIFF(pckstrkit_at,now())) / 24)) AS DAYS,concat(MOD(HOUR(TIMEDIFF(pckstrkit_at,now())), 24)) AS HOURS  FROM cc_crm where starter_case_stage in (" + sp + ") and holdflag <>'Y' ORDER BY priority DESC ";
        } 
      } 
      ps1 = con.prepareStatement(query);
      rs = ps1.executeQuery();
      while (rs.next()) {
        Newcase1 nc = new Newcase1();
        nc.setCaseid(rs.getString("case_id"));
        nc.setRdoctorname(rs.getString("registered_doctor"));
        nc.setCdoctorname(rs.getString("Doctor_Name"));
        nc.setCase_stage(rs.getString("case_stage"));
        nc.setCrm(rs.getString("crm_name"));
        nc.setDays(rs.getInt("DAYS"));
        nc.setHours(rs.getInt("HOURS"));
        nc.setStarter_case_stage(rs.getString("starter_case_stage"));
        if (arrayLength == 2 || arrayLength1 == 2) {
          nc.setDays1(rs.getInt("DAYS1"));
          nc.setHours1(rs.getInt("HOURS1"));
        } 
        list.add(nc);
      } 
      request.setAttribute("list_casestage", list);
      request.setAttribute("type", type);
      RequestDispatcher rd = request.getRequestDispatcher("showpendingcases.jsp");
      rd.forward((ServletRequest)request, (ServletResponse)response);
    } catch (Exception e) {
      LOGGER.info("Error At ShowPendingCases=" + e.getMessage());
    } finally {
      try {
        if (rs != null)
          try {
            rs.close();
          } catch (Exception exception) {
          
          } finally {
            rs = null;
          }  
        if (ps1 != null)
          try {
            ps1.close();
          } catch (Exception exception) {
          
          } finally {
            ps1 = null;
          }  
        if (con != null)
          try {
            con.close();
          } catch (Exception exception) {
          
          } finally {
            con = null;
          }  
      } catch (Exception e) {
        LOGGER.info("Error At ShowPendingCases=" + e.getMessage());
      } 
    } 
  }
  
  private void nc(int day) {}
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
