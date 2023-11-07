import com.vo.AccountVo;
import com.vo.AddresshandlerVO;
import com.vo.HallowTagVo;
import com.vo.MultiFrmVo;
import com.vo.PlanningVO;
import com.vo.StagingVo;
import com.vo.ThreeDPrintingVo;
import com.workflow.connection.LoginDAO;
import java.io.IOException;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet({"/Multifrm"})
public class Multifrm extends HttpServlet {
  static final Logger LOGGER = LogManager.getLogger(Multifrm.class);
  
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String code_id = request.getParameter("caseId");
    String crm = request.getParameter("crm");
    String patient_Name = request.getParameter("patient_Name");
    String cdoc = request.getParameter("cdoc");
    List<MultiFrmVo> threeDlist = new ArrayList<>();
    List<MultiFrmVo> hallowTag = new ArrayList<>();
    List<MultiFrmVo> upload = new ArrayList<>();
    List<AccountVo> account = new ArrayList<>();
    List<HallowTagVo> hallowtagVo = new ArrayList<>();
    List<PlanningVO> planningVO = new ArrayList<>();
    List<StagingVo> stagingVO = new ArrayList<>();
    List<HallowTagVo> hallowtagVO = new ArrayList<>();
    List<ThreeDPrintingVo> threedprintingVo = new ArrayList<>();
    List<MultiFrmVo> multifrmvo = new ArrayList<>();
    List<MultiFrmVo> multifrmvo1 = new ArrayList<>();
    List<MultiFrmVo> multifrmvo2 = new ArrayList<>();
    List<MultiFrmVo> dispatch = new ArrayList<>();
    List<AddresshandlerVO> addresshandlervo = new ArrayList<>();
    Connection con = null;
    Connection con1 = null;
    Connection con2 = null;
    Connection con3 = null;
    Connection con5 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    PreparedStatement ps0 = null;
    ResultSet rs0 = null;
    try {
      con = LoginDAO.getConnectionDetails();
      con1 = LoginDAO.getConnectionDetails();
      con2 = LoginDAO.getConnectionDetails();
      con3 = LoginDAO.getConnectionDetails();
      con5 = LoginDAO.getConnectionDetails();
      PreparedStatement ps = con.prepareStatement(" select * from  hollow_tagging order by hollow_id desc limit 1 ");
      String query = " select * from  threedprinting  order by printing_id desc limit 1 ";
      String query1 = " select * from  stagging order by planning_id desc limit 1 ";
      String query2 = " select * from  account order by account_id desc limit 1 ";
      rs = ps.executeQuery();
      while (rs.next()) {
        MultiFrmVo nc = new MultiFrmVo();
        nc.setFrom_type(rs.getString("from_type"));
        nc.setTo_type(rs.getString("to_type"));
        nc.setFrom_done(rs.getString("from_done"));
        nc.setTo_done(rs.getString("to_done"));
        threeDlist.add(nc);
      } 
      PreparedStatement ps1 = con1.prepareStatement(query);
      rs1 = ps1.executeQuery();
      while (rs1.next()) {
        MultiFrmVo nc = new MultiFrmVo();
        nc.setFrom_type(rs1.getString("from_type"));
        nc.setTo_type(rs1.getString("to_type"));
        nc.setFrom_done(rs1.getString("from_done"));
        nc.setTo_done(rs1.getString("to_done"));
        hallowTag.add(nc);
      } 
      PreparedStatement ps2 = con2.prepareStatement(query1);
      rs2 = ps2.executeQuery();
      while (rs2.next()) {
        MultiFrmVo nc = new MultiFrmVo();
        nc.setFrom_type(rs2.getString("from_type"));
        nc.setTo_type(rs2.getString("to_type"));
        upload.add(nc);
      } 
      PreparedStatement ps3 = con3.prepareStatement(query2);
      rs3 = ps3.executeQuery();
      while (rs3.next()) {
        AccountVo nc = new AccountVo();
        nc.setTotal(rs3.getString("payment"));
        nc.setPaidAmount(rs3.getString("submitted_amount"));
        nc.setRemainAmount(rs3.getString("remain_amount"));
        account.add(nc);
      } 
      request.setAttribute("account", account);
      request.setAttribute("hallowTag", hallowTag);
      request.setAttribute("threeDlist", threeDlist);
      request.setAttribute("upload", upload);
      PreparedStatement select3d = null;
      ps0 = con.prepareStatement(" select * from hollow_tagging where caseid=?");
      ps0.setString(1, code_id);
      rs0 = ps0.executeQuery();
      while (rs0.next()) {
        HallowTagVo htvo = new HallowTagVo();
        htvo.setHollow_id(rs0.getString("hollow_id"));
        htvo.setCaseid(rs0.getString("caseid"));
        htvo.setCrm(rs0.getString("crm"));
        htvo.setDate(rs0.getString("date"));
        htvo.setDecesion(rs0.getString("decesion"));
        htvo.setDoctor_name(rs0.getString("doctor_name"));
        htvo.setPatient_name(rs0.getString("patient_name"));
        htvo.setLower_aligner_from(rs0.getString("lower_aligner_from"));
        htvo.setLower_aligner_to(rs0.getString("lower_aligner_to"));
        htvo.setUpper_aligner_from(rs0.getString("upper_aligner_from"));
        htvo.setUpper_aligner_to(rs0.getString("upper_aligner_to"));
        htvo.setRemark(rs0.getString("remark"));
        hallowtagVo.add(htvo);
      } 
      select3d = con.prepareStatement(" select * from threedprinting where caseid=?");
      select3d.setString(1, code_id);
      ResultSet tdprs = select3d.executeQuery();
      while (tdprs.next()) {
        ThreeDPrintingVo threedp = new ThreeDPrintingVo();
        threedp.setPrinting_id(tdprs.getString("printing_id"));
        threedp.setCaseid(tdprs.getString("caseid"));
        threedp.setCrm(tdprs.getString("crm"));
        threedp.setDate(tdprs.getString("date"));
        threedp.setDecesion(tdprs.getString("decesion"));
        threedp.setDoctor_name(tdprs.getString("doctor_name"));
        threedp.setLower_aligner_from(tdprs.getString("lower_aligner_from"));
        threedp.setLower_aligner_to(tdprs.getString("lower_aligner_to"));
        threedp.setUpper_aligner_from(tdprs.getString("upper_aligner_from"));
        threedp.setUpper_aligner_to(tdprs.getString("upper_aligner_to"));
        threedp.setPatient_name(tdprs.getString("patient_name"));
        threedp.setPrint(tdprs.getString("print"));
        threedp.setMode(tdprs.getString("mode"));
        threedprintingVo.add(threedp);
      } 
      PreparedStatement labps = con.prepareStatement(" select * from lab where caseid=?");
      labps.setString(1, code_id);
      ResultSet labrs = labps.executeQuery();
      while (labrs.next()) {
        MultiFrmVo multivo = new MultiFrmVo();
        multivo.setCaseid(labrs.getString("caseid"));
        multivo.setCrm(labrs.getString("crm"));
        multivo.setDate(labrs.getString("date"));
        multivo.setDecesion(labrs.getString("decesion"));
        multivo.setDoctor_name(labrs.getString("doctor_name"));
        multivo.setHand_tooling(labrs.getString("hand_tooling"));
        multivo.setLab_id(labrs.getString("lab_id"));
        multivo.setLower_aligner_from(labrs.getString("lower_aligner_from"));
        multivo.setLower_aligner_to(labrs.getString("lower_aligner_to"));
        multivo.setUpper_aligner_from(labrs.getString("upper_aligner_from"));
        multivo.setUpper_aligner_to(labrs.getString("upper_aligner_to"));
        multivo.setMaking(labrs.getString("making"));
        multivo.setName_cat(labrs.getString("name_cat"));
        multivo.setPatient_name(labrs.getString("patient_name"));
        multivo.setRemark(labrs.getString("remark"));
        multivo.setWaxing(labrs.getString("waxing"));
        multivo.setThermoform(labrs.getString("thermoform"));
        multivo.setUpper_att(labrs.getString("upper_att"));
        multivo.setLower_att(labrs.getString("lower_att"));
        multifrmvo.add(multivo);
      } 
      PreparedStatement plnps = null;
      ResultSet plnrs = null;
      plnps = con5.prepareStatement(" select p.planned_no,p.planning_type,p.upper_aligner_from,p.upper_aligner_to,p.lower_aligner_from,p.lower_aligner_to from planning as p ,cc_crm as c where p.case_id='" + code_id + "'" + 
          " and p.planning_id=c.planning_id");
      plnrs = plnps.executeQuery();
      while (plnrs.next()) {
        PlanningVO plnVO = new PlanningVO();
        plnVO.setPlanned_no(plnrs.getString("planned_no"));
        plnVO.setPlanning_type(plnrs.getString("planning_type"));
        plnVO.setUpper_aligner_from(plnrs.getString("upper_aligner_from"));
        plnVO.setUpper_aligner_to(plnrs.getString("upper_aligner_to"));
        plnVO.setLower_aligner_from(plnrs.getString("lower_aligner_from"));
        plnVO.setLower_aligner_to(plnrs.getString("lower_aligner_to"));
        planningVO.add(plnVO);
      } 
      PreparedStatement stgps = null;
      ResultSet stgrs = null;
      stgps = con.prepareStatement(" select s.planning_type,s.molor_upper,s.molor_lower,s.planning_id,s.upper_aligner_from,s.upper_aligner_to,s.lower_aligner_from,s.lower_aligner_to,s.thick_upper,s.thick_lower,s.margin_upper,s.margin_lower,s.molor_upper,s.molor_lower,s.sheet_type  from stagging as s,cc_crm as c where s.caseid='" + 
          code_id + "' and s.planning_id=c.staging_id ");
      stgrs = stgps.executeQuery();
      while (stgrs.next()) {
        StagingVo stgvo = new StagingVo();
        stgvo.setPlanning_type(stgrs.getString("planning_type"));
        stgvo.setUpper_aligner_from(stgrs.getString("upper_aligner_from"));
        stgvo.setUpper_aligner_to(stgrs.getString("upper_aligner_to"));
        stgvo.setLower_aligner_from(stgrs.getString("lower_aligner_from"));
        stgvo.setLower_aligner_to(stgrs.getString("lower_aligner_to"));
        stgvo.setThick_upper(stgrs.getString("thick_upper"));
        stgvo.setThick_lower(stgrs.getString("thick_lower"));
        stgvo.setMargin_upper(stgrs.getString("margin_upper"));
        stgvo.setMargin_lower(stgrs.getString("margin_lower"));
        stgvo.setMolor_upper(stgrs.getString("molor_upper"));
        stgvo.setMolor_lower(stgrs.getString("molor_lower"));
        stgvo.setSheet_type(stgrs.getString("sheet_type"));
        stagingVO.add(stgvo);
      } 
      PreparedStatement mtpps = con.prepareStatement("select * from hollow_tagging where caseid='" + code_id + "' order by date desc limit 1 ");
      ResultSet mptrs = mtpps.executeQuery();
      while (mptrs.next()) {
        HallowTagVo mptvo = new HallowTagVo();
        mptvo.setUpper_aligner_from(mptrs.getString("upper_aligner_from"));
        mptvo.setUpper_aligner_to(mptrs.getString("upper_aligner_to"));
        mptvo.setLower_aligner_from(mptrs.getString("lower_aligner_from"));
        mptvo.setLower_aligner_to(mptrs.getString("lower_aligner_to"));
        mptvo.setRemark(mptrs.getString("lower_aligner_to"));
        hallowtagVO.add(mptvo);
      } 
      PreparedStatement fqcps = con.prepareStatement(" select * from lab where caseid='" + code_id + "'  order by date desc limit 1 ");
      ResultSet fqcrs = fqcps.executeQuery();
      while (fqcrs.next()) {
        MultiFrmVo multivo1 = new MultiFrmVo();
        multivo1.setCaseid(fqcrs.getString("caseid"));
        multivo1.setCrm(fqcrs.getString("crm"));
        multivo1.setDate(fqcrs.getString("date"));
        multivo1.setDecesion(fqcrs.getString("decesion"));
        multivo1.setDoctor_name(fqcrs.getString("doctor_name"));
        multivo1.setHand_tooling(fqcrs.getString("hand_tooling"));
        multivo1.setLab_id(fqcrs.getString("lab_id"));
        multivo1.setLower_aligner_from(fqcrs.getString("lower_aligner_from"));
        multivo1.setLower_aligner_to(fqcrs.getString("lower_aligner_to"));
        multivo1.setUpper_aligner_from(fqcrs.getString("upper_aligner_from"));
        multivo1.setUpper_aligner_to(fqcrs.getString("upper_aligner_to"));
        multivo1.setMaking(fqcrs.getString("making"));
        multivo1.setName_cat(fqcrs.getString("name_cat"));
        multivo1.setPatient_name(fqcrs.getString("patient_name"));
        multivo1.setRemark(fqcrs.getString("remark"));
        multivo1.setWaxing(fqcrs.getString("waxing"));
        multivo1.setThermoform(fqcrs.getString("thermoform"));
        multivo1.setUpper_att(fqcrs.getString("upper_att"));
        multivo1.setLower_att(fqcrs.getString("lower_att"));
        multifrmvo1.add(multivo1);
      } 
      PreparedStatement pckps = con.prepareStatement(" select * from packing where case_id='" + code_id + "' order by date desc limit 1 ");
      ResultSet pckrs = pckps.executeQuery();
      while (pckrs.next()) {
        MultiFrmVo multivo2 = new MultiFrmVo();
        multivo2.setCaseid(pckrs.getString("case_id"));
        multivo2.setCrm(pckrs.getString("crm"));
        multivo2.setDate(pckrs.getString("date"));
        multivo2.setDecesion(pckrs.getString("decesion"));
        multivo2.setDoctor_name(pckrs.getString("doctor_name"));
        multivo2.setPatient_name(pckrs.getString("patient_name"));
        multivo2.setUltra_sonic(pckrs.getString("ultra_sonic"));
        multivo2.setAir(pckrs.getString("air"));
        multivo2.setPouch_seal(pckrs.getString("pouch_seal"));
        multivo2.setRemark(pckrs.getString("remark"));
        multifrmvo2.add(multivo2);
        System.out.println("packing");
      } 
      PreparedStatement pstmt = con.prepareStatement(" select * from dispatched_scan where case_id=?");
      pstmt.setString(1, code_id);
      ResultSet rsd = pstmt.executeQuery();
      while (rsd.next()) {
        MultiFrmVo disp = new MultiFrmVo();
        disp.setDispatched_id(rsd.getString("dispatched_id"));
        disp.setCaseid(rsd.getString("case_id"));
        disp.setDoctor_name(rsd.getString("doctor_name"));
        disp.setPatient_name(rsd.getString("patient_name"));
        disp.setCrm(rsd.getString("crm"));
        disp.setDispatch(rsd.getString("dispatch"));
        disp.setDispatch_no(rsd.getString("dispatch_no"));
        disp.setTracking_id(rsd.getString("tracking_id"));
        disp.setDelivery_nn(rsd.getString("delivery_nn"));
        disp.setRemark(rsd.getString("remark"));
        disp.setDecesion(rsd.getString("decesion"));
        disp.setDate(rsd.getString("date"));
        disp.setNo_of_aligners(rsd.getString("no_of_aligners"));
        disp.setMode_of_dispatch(rsd.getString("mode_of_dispatch"));
        dispatch.add(disp);
        System.out.println("dispatched_scan");
      } 
      PreparedStatement pstmt02 = con.prepareStatement(" select * from cc_crm where case_id=?");
      pstmt02.setString(1, code_id);
      ResultSet rs02 = pstmt02.executeQuery();
      while (rs02.next()) {
        AddresshandlerVO address = new AddresshandlerVO();
        address.setAddress1(rs02.getString("address1"));
        address.setAddress2(rs02.getString("address2"));
        address.setAddress3(rs02.getString("address3"));
        address.setAddress4(rs02.getString("address4"));
        address.setAddress5(rs02.getString("address5"));
        address.setCity1(rs02.getString("city"));
        address.setCity2(rs02.getString("city2"));
        address.setCity3(rs02.getString("city3"));
        address.setCity4(rs02.getString("city4"));
        address.setCity5(rs02.getString("city5"));
        address.setPhone1(rs02.getString("phone1"));
        address.setPhone2(rs02.getString("phone2"));
        address.setPhone3(rs02.getString("phone3"));
        address.setPhone4(rs02.getString("phone4"));
        address.setPhone5(rs02.getString("phone5"));
        address.setPincode1(rs02.getString("pincode1"));
        address.setPincode2(rs02.getString("pincode2"));
        address.setPincode3(rs02.getString("pincode3"));
        address.setPincode4(rs02.getString("pincode4"));
        address.setPincode5(rs02.getString("pincode5"));
        address.setLocation1(rs02.getString("location"));
        address.setLocation2(rs02.getString("location2"));
        address.setLocation3(rs02.getString("location3"));
        address.setLocation4(rs02.getString("location4"));
        address.setLocation5(rs02.getString("location5"));
        address.setDefault_betch(rs02.getString("default_address"));
        addresshandlervo.add(address);
      } 
      request.setAttribute("addresshandler", addresshandlervo);
      request.setAttribute("dispatch", dispatch);
      request.setAttribute("previouslab", multifrmvo);
      request.setAttribute("previousfqc", multifrmvo1);
      request.setAttribute("previouspck", multifrmvo2);
      request.setAttribute("previousotpln", planningVO);
      request.setAttribute("previousstg", stagingVO);
      request.setAttribute("previoushltg", hallowtagVO);
      request.setAttribute("previous3dp", threedprintingVo);
      request.setAttribute("previoushallowtagVo", hallowtagVo);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (Exception exception) {
        
        } finally {
          rs = null;
        }  
      if (rs1 != null)
        try {
          rs1.close();
        } catch (Exception exception) {
        
        } finally {
          rs1 = null;
        }  
      if (rs2 != null)
        try {
          rs2.close();
        } catch (Exception exception) {
        
        } finally {
          rs2 = null;
        }  
      if (rs3 != null)
        try {
          rs3.close();
        } catch (Exception exception) {
        
        } finally {
          rs3 = null;
        }  
      if (rs0 != null)
        try {
          rs0.close();
        } catch (Exception exception) {
        
        } finally {
          rs0 = null;
        }  
      if (ps0 != null)
        try {
          ps0.close();
        } catch (Exception exception) {
        
        } finally {
          ps0 = null;
        }  
      if (con != null)
        try {
          con.close();
        } catch (Exception exception) {
        
        } finally {
          con = null;
        }  
      if (con1 != null)
        try {
          con1.close();
        } catch (Exception exception) {
        
        } finally {
          con1 = null;
        }  
      if (con2 != null)
        try {
          con2.close();
        } catch (Exception exception) {
        
        } finally {
          con2 = null;
        }  
      if (con3 != null)
        try {
          con3.close();
        } catch (Exception exception) {
        
        } finally {
          con3 = null;
        }  
      if (con5 != null)
        try {
          con5.close();
        } catch (Exception exception) {
        
        } finally {
          con5 = null;
        }  
    } 
    RequestDispatcher rd = request.getRequestDispatcher(
        "multifrm.jsp?caseId=" + code_id + "&crm=" + crm + "&patient_Name=" + patient_Name + "&cdoc=" + cdoc);
    rd.forward((ServletRequest)request, (ServletResponse)response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
