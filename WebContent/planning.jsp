
<!DOCTYPE html>

<%@page import="com.vo.PlanningVO"%>
<%@page import="com.vo.PendingVO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.*"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.workflow.connection.LoginDAO"%>
 


<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="Images/logo.png">
    
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>WorkFlow</title>
    
    <!-- Bootstrap core CSS -->
  <link href="${pageContext.request.contextPath}/StyleSheet/bootstrap.min.css" rel="stylesheet">
  <!--  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> -->
    <link href="${pageContext.request.contextPath}/StyleSheet/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/StyleSheet/animate.min.css" rel="stylesheet">

    
    <!-- Custom styling plus plugins -->
    <link href="${pageContext.request.contextPath}/StyleSheet/custom1.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/JavaScript/jquery.min.js"></script>    
 <script src="${pageContext.request.contextPath}/JavaScript/bootstrap.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/JavaScript/jquery-1.10.1.js"></script>

<style>
.green-color {
color:green;
}
</style>
</head>

<body class="nav-md">
<%
String n=request.getParameter("caseId");
String crm=request.getParameter("crm");
String patient_Name=request.getParameter("patient_Name");
String cdoc=request.getParameter("cdoc");
String corporate_account=request.getParameter("corporate_account");
List<String> userRight =LoginDAO.getRight((String)session.getAttribute("userid"));
String cccrmpriority=(String)session.getAttribute("plnpriority");
String planning_id=request.getParameter("planning_id");


System.out.println("planning_id: "+planning_id);

List<String> CorporateAccount=(ArrayList<String>)session.getAttribute("customerlist");

/* List<PlanningVO> plnlist=(ArrayList)session.getAttribute("plnlist");

 */
List<PlanningVO> list = (List)session.getAttribute("plnlist");%>

<%
							Connection con01 = null;
							Connection con02 = null;
							PreparedStatement ps01 = null;
							PreparedStatement ps02 = null;
							ResultSet rs01 = null;
							ResultSet rs02 = null;
							int planid=0;
							 int planid1=0;
							 int count=0;
						     String priority1="";

							try {
								LoginDAO connect1 = new LoginDAO();
								con01 = connect1.getConnectionDetails();
								con02 = connect1.getConnectionDetails();
								//////
								

								
								ps02 = con02.prepareStatement(" select count(*) as cnt from planning  where   planned_no IS NOT NULL and  case_id='"+n+"' ");
								rs02 = ps02.executeQuery();
								while (rs02.next()) {
									count=rs02.getInt("cnt");
								}
								ps01 = con01.prepareStatement(" select * from cc_crm where Case_Id='"+n+"' ");
								rs01 = ps01.executeQuery();
								while (rs01.next()) {
									planid=rs01.getInt("planning_id");
									priority1=rs01.getString("priority");

								}
								System.out.println("planidsxaSdsad: "+planid);
			
%>

<div class="container body">
  <div class="main_container">
    <div class="col-md-3 left_col">
                <div class="left_col scroll-view">
                <!-- Top Bar -->
                 	<%@ include file="TopBar.jsp" %>
				<!-- Top Bar -->
             	<!-- menu prile quick info -->

						<%@ include file="LeftTopSide.jsp" %>
						<!-- /menu prile quick info -->
                          
                 	<!-- sidebar menu -->
					    <%@ include file="SideBar.jsp" %>
                    <!-- /sidebar menu -->
                </div>
            </div>

             <!-- top navigation -->
            <%@ include file="TopNavigation.jsp" %>
            <!-- /top navigation -->


            <!-- page content -->
             <div class="right_col" role="main">
<div class="container" style="padding-top:20px;">


<div class="row">
<div class="col-sm-3">
									<label">Case Id : </label><span> <%= n%></span>
								</div>
								<div class="col-sm-3">
									<label> Patient Name :<%= patient_Name%> </label> <span></span>
								</div>
								<div class="col-sm-3">
									<label>Registered Doctor :<%= cdoc%>  </label><span></span>
								</div>
								<div class="col-sm-3">
									<label>Priority : </label> <span><%= cccrmpriority%> </span>

								</div>	
								
								</div>
<div class="row">

								<div class="col-sm-3">
									<label ><a class="btn btn-primary" href="Approval?caseId=<%= n%>&NxtStage=null&crm=<%= crm%>&cdoc=<%= cdoc%>&patient_Name=<%= patient_Name%> ">Fetch data</a>  </label>
								</div>
								<div class="col-sm-3">
									<label ><a class="btn btn-primary" href="DispImageGrid?caseId=<%= n%> ">View Photo</a>  </label>
								</div>
								</div>
								
																<style>
									legend {
											    background-color: #d9edf7;
											    color: #fff;
											    padding: 3px 6px;
											    height: 35px;
											}
											
											.output {
											    font: 1rem 'Fira Sans', sans-serif;
											}
											
											input {
											    margin: .4rem;
											}
								</style>
								
								<%  if(userRight.contains("OTPLN")){ %>
								<fieldset>
    								<legend class="text-center" style="color:black;">Registration link</legend>
								<div class="row">
								<div class="col-sm-12 text-center">		
							
  											<label><a class="btn btn-info" href="https://103.120.178.180:8443/Registration/fetchcreate.jsp">Create IPR</a>  </label><span></span>
 											
											<label ><button class="btn btn-info" onclick="editipr()">Edit IPR</button>  </label>
												<div id="editipr" style="display:none;">
												<form  action="https://103.120.178.180:8443/Registration/editservlet" method="post">			
														<input type="text" name="cid" required pattern="[0-9]{10,10}" placeholder="Enter Case ID" value="<%= n%>">
												  		<input type="text" name="pln" required placeholder="Enter Plan Number" >
												  		<button class="btn btn-info" type="submit" >Go </button><br>
												</form>
											</div>
											
												<label ><button class="btn btn-info" onclick="viewdigiplan()">View Digiplan</button>  </label>
												<div id="viewdigiplan" style="display:none;">
												<form  action="https://103.120.178.180:8443/Registration/logincheckdoctor" method="post">			
														<input type="text" name="cid" required pattern="[0-9]{10,10}" placeholder="Enter Case ID" value="<%= n%>">
												  		<input type="text" name="pln" required placeholder="Enter Plan Number" >
												  		<button class="btn btn-info" type="submit" >Go </button><br>
												</form>
											</div>
		
								   </div>
								   </div>
								   
								   </fieldset>
							<%} %>
								
  <div class="panel panel-primary">
    <div class="panel-heading"><strong>Case Planning Details</strong> </div>

<br>
 

  <% 
  
  /*   for(int i=0;i<list.size(); i++){ */
   

int i=0;
  

    	 for(com.vo.PlanningVO plnnlist:list){
				String crmpln=plnnlist.getCrm();
				String cdocpln=plnnlist.getDoctor_name();
				String patient_Namepln=plnnlist.getPatient_name();

  i++;
 
    %>
    <% if(plnnlist.getPlanned_no()==null){  
   i=0;
   %>
    
    <%}else{ %>
 <div class="container" style="width:99%">
 
   
    
   <% 
   planid1=plnnlist.getPlanning_id();
   if(planid==planid1) {    
   
   %>
    <div class="panel panel-default" style="border-color: green !important;">
    <div class="panel-heading" style="background-color: #d9edf7;" >Planning 
   <i class="fa fa-check-circle green-color" style="font-size:28px;" ></i>
   <%} else{%> 
    <div class="panel panel-default">
	   <div class="panel-heading" >Planning 
  <%}%> 
  
   
     </div>
    <div class="panel-body">
    
    	    <!-- new planning frm repeated-->
 
    
    <div class="row">
    <label class="col-md-2 control-label">Planned No </label>  
  <div class="col-lg-4 ">
  
<input type="text" disabled class="form-control form-control-lg" name="Planned_No" id="Planned_No"  placeholder="Planned No" value="<%=plnnlist.getPlanned_no() %>">
  </div>
  
    <label class="col-md-2 control-label">Planning Type </label>  
  <div class="col-lg-4 ">

	<input type="text"  disabled class="form-control form-control-lg" name="planningType" id="planningType" value="<%=plnnlist.getPlanning_type() %>">
			
  </div>
</div><br>
<div class="row">
    <label class="col-md-2 control-label">Upper Aligner</label>  
  <div class="col-lg-2 ">
  <div class="input-group">
  <label><input type="text"    disabled class="form-control" id="upper_aligner_from"	name="upper_aligner_from" placeholder="From" value="<%=plnnlist.getUpper_aligner_from() %>"></label>
    </div>
  </div>
    <div class="col-lg-2 ">
  <div class="input-group">
  <label><input type="text"    disabled class="form-control" id="upper_aligner_to"	name="upper_aligner_to" placeholder="To" value="<%=plnnlist.getUpper_aligner_to() %>"></label>
    </div>
  </div>
  
    <label class="col-md-2 control-label">Lower Aligner </label>  
  <div class="col-lg-2 ">
  <div class="input-group">
  <label><input type="text"    disabled class="form-control" id="lower_aligner_from" name="lower_aligner_from" placeholder="From" value="<%=plnnlist.getLower_aligner_from()%>"></label>
    </div>
  </div>
    <div class="col-lg-2 ">
  <div class="input-group">
  <label><input type="text"   disabled  class="form-control" id="lower_aligner_to" name="lower_aligner_to" placeholder="To" value="<%=plnnlist.getLower_aligner_to()%>"></label>
    </div>
  </div>
</div>
<div class="row">
	<div class="text-center">
		<% 
   planid1=plnnlist.getPlanning_id();
   if(planid==planid1) {%>
	<button  class="btn btn-primary" style="background:green;color:white;" >Approved Plan</button>
	
	   <%} else{ %>
		<%-- <a href="SavePlanning?query=approveplan&planning_id=<%=plnnlist.getPlanning_id()%>&caseId=<%=plnnlist.getCase_id() %>" class="btn btn-primary">Approve Plan</a> --%>
		
		<a href="SavePlanning?query=approveplan&planning_id=<%=plnnlist.getPlanning_id()%>&caseId=<%=plnnlist.getCase_id() %>&crm=<%=request.getParameter("crm")%>&doctor_name=<%=cdocpln %>&patient_name=<%=patient_Namepln%>" class="btn btn-primary">Approve Plan</a>
		 <%} %>
	</div>
</div>

   <!--  </form> -->
    
    <!-- end  new planning frm  -->
    
    </div>
    </div>
    </div>
    <%} } %>
    
    <hr>
    <!--  planning 2nd repeated-->
  <div class="container" style="width:99%">
  <div class="panel panel-default" >
    <div class="panel-heading" style="background-color: #337ab7;color: #fff;">Add Planning</div>
    <div class="panel-body">
    
    	    <!-- new planning frm-->
    
    <form class="form-verticle" action="SavePlanning?query=planningnew" method="post" >
	 <input type="hidden" class="form-control" value="<%= cdoc%>" name="doctor_name" id="doctor_name" placeholder="Doctor Name" >
	<input type="hidden" class="form-control" value="<%= n%>" name="caseId" id="caseId" >
	<input type="hidden" class="form-control" value="<%= patient_Name%>" name="patient_name" id="patient_name" placeholder="Patient Name">
	<input type="hidden" class="form-control" value="<%= crm%>" name="crm" id="crm">
    
    <div class="row">
    <div class="col-md-6 ">
    <label class="col-md-6 control-label">Planned No </label>  
  <div class="col-lg-6 ">
  
<input type="text" class="form-control form-control-lg" name="Planned_No1" id="Planned_No"   placeholder="Planned No" value="<%=count+1 %>"   oninput="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
  </div>
  </div>
    <div class="col-md-6 ">
    <label class="col-md-6 control-label">Planning Type </label>  
  <div class="col-lg-6 ">
   <select class="form-control " name="planningType" id="planningType">
           		 	<% List<String> Planning_Type1=(ArrayList<String>)session.getAttribute("Planning_Type");
				   	    for(String corpAcnt:Planning_Type1) {%>
				   	    	<option value="<%=corpAcnt %>"><%=corpAcnt %></option>
				   	    <%}%>
			</select>	
  </div>
  </div>
</div><br>
<div class="row">
    <div class="col-md-6 ">
    <label class="col-md-2 control-label">Upper Aligner</label>  
  <div class="col-lg-6 ">
  <div class="input-group">
  <input type="text" required class="form-control" id="upper_aligner_from"	name="upper_aligner_from" placeholder="From"   oninput="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
  <input type="text" required  class="form-control" id="upper_aligner_to"	name="upper_aligner_to" placeholder="To"   oninput="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
    </div>
  </div>
  </div>
      <div class="col-md-6 ">
    <label class="col-md-2 control-label">Lower Aligner </label>  
  <div class="col-lg-6 ">
  <input type="text" required class="form-control" id="lower_aligner_from" name="lower_aligner_from" placeholder="From"   oninput="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
 <input type="text" required  class="form-control" id="lower_aligner_to" name="lower_aligner_to" placeholder="To"   oninput="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
  </div>
  </div>
</div>


 <div class="row">
	<div class="text-center">
		<button type="submit" value="Save" class="btn btn-primary">Submit</button>
	</div>
</div>
    </form>
    
    <!-- end  new planning frm  -->
    
    </div>
    </div>
    </div>
    
  <div class="container" style="width:99%">
  <div class="panel panel-default" style="border-color: #3a5e7c;">
    <div class="panel-heading" style="background-color: #3a5e7c;color: #fff;">Add Planning Details</div>
    <div class="panel-body">

			<form class="form-verticle" action="SavePlanning" method="post"  onsubmit="return validation()" >
			<input type="hidden" value=<%= n%> name="CaseId" id="CaseId" readonly>
			<input type="hidden" name="PatientName" readonly value=<%= patient_Name%> >
			<input type="hidden" name="DoctorName" readonly value=<%= cdoc%> > 
			<input type="hidden" name="crm" readonly value=<%= crm%> >
			<input type="hidden" id="planid" readonly value=<%= planid%> >
			  
			   <div class="row">
			    <div class="col-md-4 ">
			    	<label>BASING/SEGMENTATION CHECKED </label>  
			  </div>
			    <div class="col-md-1 ">
			        <input type="radio" name="basingsegcheck" id="basingsegcheck" checked="" >Yes
				</div>
				<div class="col-md-1 ">
					<input type="radio" name="basingsegcheck" id="basingsegcheck">No
			  </div>
			</div>
			
			<br>
			  <div class="row">
			    <div class="col-md-4 ">
			    	<label>Planned </label>  
			  </div>
			    <div class="col-md-1 ">
						<input type="radio" name="planned" id="planned" checked="">Yes
					</div>
				<div class="col-md-1 ">
						<input type="radio" name="planned" id="planned">No	
				</div>
			  </div>
			
			<br>
			 <div class="row">
			    <div class="col-md-4 ">
			    	<label>IPR Sheet </label>  
			  </div>
			    <div class="col-md-1 ">
			    <input type="radio" name="iprSheet" id="iprSheet" checked="">Yes
				</div>
				<div class="col-md-1 ">
				<input type="radio" name="iprSheet" id="iprSheet">No
			  </div>
			</div> 
			<br>
			<div class="row">
			    <div class="col-md-4 ">
			    	<label>Treatment Plan Report </label>  
			  </div>
			    <div class="col-md-1 ">
			      <input type="radio" name="treatmentreport" id="treatmentreport" checked="">Yes
				</div>
				<div class="col-md-1 ">
  					<input type="radio" name="treatmentreport" id="treatmentreport">No
			  </div>
			</div>
			<br>
			 <div class="row">
			    <div class="col-md-4 ">
			    	<label>Upload Digiplan/Send GIF </label>  
			  </div>
			    <div class="col-md-1 ">
			      <input type="radio" name="uploaddgiplan" id="uploaddgiplan" checked="">Yes
				</div>
				<div class="col-md-1 ">
  				<input type="radio" name="uploaddgiplan" id="uploaddgiplan">No
			  </div>
			</div> 
			<br>
			<div class="row">
			    <div class="col-md-4 ">
			    	<label>Planning Review By </label>  
			  </div>
			    <div class="col-md-6 ">
			       <select class="form-control" name="planningreview" id="planningreview" style="width:437px; ">
           		 	<% List<String> Planning_Review=(ArrayList<String>)session.getAttribute("Planning_Review");
				   	    for(String corpAcnt:Planning_Review) {%>
				   	    	<option value="<%=corpAcnt %>"><%=corpAcnt %></option>
				   	    <%}%>
				</select>	
				</div>
			</div><br>
			  <%  if(userRight.contains("QA")){ %>
			<div class="row">
			    <div class="col-md-4 ">
			    	<label>Priority </label>  
			  </div>
			    <div class="col-md-6 ">
			  
			       <select class="form-control"  name="priority" id="priority"  style="width:437px;" required>
           		 	 <% List<String> priority=(ArrayList<String>)session.getAttribute("prioritylist");
				   	   if(priority1==null){}
					  
					  for(String corpAcnt:priority) { 
						  
				   	    	if(corpAcnt.equals(priority1)){%>
				 	    	<option selected value="<%=corpAcnt %>"><%=corpAcnt %></option>
				   	     <%} else{%>
				   	    
				   	    	<option value="<%=corpAcnt %>"><%=corpAcnt %></option>
				   	    	
				   	    <%}}%>
				</select>	
				
				</div>
			</div>
			<%} %>
				
				<br>
			
			<!--start priority -->
		<!-- 		<div class="row">
			    <div class="col-md-4 ">
			    	<label>Priority </label>  
			  </div>
			    <div class="col-md-6 ">
			       	<select class="form-control" name="priority" id="priority"  style="width:437px;" required>
					  	<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>	
				 	    <option selected="" value="9">9</option>
						<option value="10">10</option>	
				</select>	
				</div>
			</div> -->
			
			<!-- end  priority-->
			<br>
		<div class="row">
			    <div class="col-md-4 ">
			    	<label>Decision</label>  
			  </div>
			    <div class="col-md-6 ">
 <select class="form-control" name="stage" id="stage" style="width:437px; ">
 
		  	   <%  if(userRight.contains("OTPLN")){ %>
 
					  <option value="PLN">Planning Approve</option>	
					  <option value="PRECOR">Prerequisite Correction</option>	
					  <option value="QA">Send to Staging</option>	
				
				<%}else if(userRight.contains("REV")){ %>
				
           		 		<!-- <option value="Y">Work-In-Progress</option> -->
           		 		<option value="UPLCOR">Upload Correction</option>
           		 		<option value="PLNCOR">Plan Correction</option>
					  <option value="REV">Planning Review Approve</option>
					 
				
				<%} else if(userRight.contains("QA")){ %><!-- PRE -->
				
           		 		<!-- <option value="Y">Work-In-Progress</option> -->
           		 		 <option value="QA">Doctor Approved</option>
           		 		 <option value="PLNCOR">Plan Correction</option>
           		 		 <option value="UPLCOR">Upload Correction</option>
           		 		 
           		<%
           			
           		if(corporate_account.equals("PLAN")){%>
           		 <option value="Not Converted">Not Converted</option>
				  <option value="Cancel">Cancel</option>

           		<%}
	
	
	
           	 %>
	
           		 		 
		
				 <% }%>
</select>				</div>
				
			</div>
			<br>
			<div class="row">
			    <div class="col-md-4 ">
			    	<label>Remarks</label>  
			  </div>
			    <div class="col-md-6 ">
  						<textarea class="form-control"  name="remarks" id="remarks" placeholder="Enter remark" placeholder="Remarks"  style="width: 436px;" rows="4" cols="10"></textarea>
				</div>
			</div>
			<br>
			 <div class="row">
			<div class="text-center">
				<button type="submit" value="Save" class="btn btn-primary" id="planning">Submit</button>
			</div>
		</div> 
			</div>
		

   <script type="text/javascript">
    function validation(){
    	var stage = document.getElementById('stage').value;
        var remarks = document.getElementById('remarks').value;
        var planid = document.getElementById('planid').value;
		var planning_id = <%=planid%>;
		
     	        if(stage=='PRECOR' && remarks==""){
        	 alert("Remarks is Required!") 
        	 document.getElementById("remarks").style.borderColor = "#E34234";
             return false;
        }	
        if(stage=='UPLCOR' && remarks==""){
       	 alert("Remarks is Required!") 
       	 document.getElementById("remarks").style.borderColor = "#E34234";
            return false;
       }
        if(stage=='PLNCOR' && remarks==""){
       	 alert("Remarks is Required!") 
       	 document.getElementById("remarks").style.borderColor = "#E34234";
            return false;
       }
        if(stage=='Not Converted' && remarks==""){
          	 alert("Remarks is Required!") 
          	 document.getElementById("remarks").style.borderColor = "#E34234";
               return false;
          }
        if(stage=='Cancel' && remarks==""){
         	 alert("Remarks is Required!") 
         	 document.getElementById("remarks").style.borderColor = "#E34234";
              return false;
         }    
       
    }
	</script>
</form>
</div>
</div>   
</div>

   <!-- end planning 2nd -->
    


  </div>
</div>
    </div>
            <!-- /page content -->
        </div>
    </div>
     <!-- font awesome link -->
 <script src="JavaScript/jquery-3.6.0.js" ></script>
<script src="JavaScript/kit.fontawesome.com/5b8969f8ab.js"></script>
<script>

function editipr() {
	  var x = document.getElementById("editipr");
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } else {
	    x.style.display = "none";
	  }
	}
function viewdigiplan() {
	  var x = document.getElementById("viewdigiplan");
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } else {
	    x.style.display = "none";
	  }
	}

</script>
</body>
</html>
<%
	} catch (Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	} finally {

		 if(rs01!=null) {try{ rs01.close();}catch(Exception e){}finally {rs01=null; }}
		 if(rs02!=null) {try{ rs02.close();}catch(Exception e){}finally {rs02=null; }}
		 if(ps02!=null) {try{ ps02.close();}catch(Exception e){}finally {ps02=null; }}
		 if(con01!=null) {try{ con01.close();}catch(Exception e){}finally {con01=null; }}
		 if(con02!=null) {try{ con02.close();}catch(Exception e){}finally {con02=null; }}	 

	}
%>


