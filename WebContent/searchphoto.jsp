
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isThreadSafe="false" errorPage="Error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.math.MathContext"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="com.vo.ViewVO"%> 

<% List userRights = (List) session.getAttribute("getRight");
session = request.getSession(false);
if (session.getAttribute("userid") == null || session.getAttribute("userid").equals(""))
{
	response.sendRedirect("login.jsp?msg=You are not logged in..!");
} %>
<html lang="en">

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="https://www.32watts.com/images/logo.png">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>WorkFlow :: Search Patient Photo </title><!-- Bootstrap core CSS -->
    <link href="StyleSheet/bootstrap.min1.css" rel="stylesheet">
    <link href="StyleSheet/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/StyleSheet/animate.min.css" rel="stylesheet">
    <!-- Custom styling plus plugins -->
    <link href="${pageContext.request.contextPath}/StyleSheet/custom1.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/JavaScript/jquery.min.js"></script>  
	<script type="text/javascript" src="${pageContext.request.contextPath}/JavaScript/general.js"></script>
    <script src="avaScript/bootstrap.js"></script>
    <script src="JavaScript/custom.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/JavaScript/dataTables.js"></script>
</head>
<body class="nav-md" >
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
            <input type="hidden" name="username" value='<%=request.getAttribute("username")%>'/> 
<div class="container" style="padding-top:20px;">
  <div class="panel panel-primary">
    <div class="panel-heading"><strong>Search Photo Case ID</strong> </div>
    	<br>
    	<form name="wfPhotofrm" id="wfPhotofrm" method="post" action="SearchPhotoData" target="_self" >
        	
    <div class="row" id="SearchPhotoContainer">
									<div class="col-lg-12">
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<label> Case Id <span style="color:red" class="required">*</span></label> 
											 <input class="form-control" id="caseId"  required  name="caseId">
										</div>
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12" id="photoTypeContainer">
											<label>Photo Type<span style="color:red" class="required">*</span></label> <select class="form-control" id="photoType" name="photoType">
												<option value="">Select an option</option>
												<option value="Mid Scan">Mid Assessment</option>
											<!-- 	<option value="newQuery">New Query</option> -->
												<option value="Next Batch Required">Next Batch Required</option>
												<option value="repeat">Repeat</option>
												<option value="Retainer">Retainer</option>
												<option value="wfOnly">Workflow Added Photo</option>
											</select>
										</div>
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<label>Add/View Photos<span style="color:red" class="required">*</span></label>
											 <select class="form-control" id="viewphoto" name="viewphoto">
												<option value="N">Add</option>
												<option value="Y" selected>View</option>
											</select>
										</div>
										
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<button type="submit" class="btn btn-warning btn-block searchReport" id="photobtn" style="margin-top: 24px;">
												<i class="fa-solid fa-magnifying-glass" style="margin-right: 5px;"></i> Search
											</button>
										</div>
									</div>
								</div><br>
								</form>
								</div>						
  </div>
   </div>
		</div>
		</div>
		
	<script>
/* 	let photoType = $("#photoType").val();
	let viewphoto = $("#viewphoto").val();

	$(document).ready(function() {
		

		if($("#reportType").val() === "wfOnly"){
			$("#viewphoto").find("option").eq(1).prop("selected", true);
			
		}else{
			$("#viewphoto").find("option").eq(1).prop("selected", true);
		}
		}); */
		</script>
		<script type="text/javascript">		
		/* $("#photoType").on("change", function(event) {		
			
			let photoType = $("#photoType").val();	
			let caseId = $("#caseId").val();
			let viewphoto = $("#viewphoto").val();
			
			if(viewphoto === 'N' && photoType === 'wfOnly' ){
				$('#wfPhotofrm').attr('action', '${pageContext.request.contextPath}/SearchPhotoData?caseId='+caseId);
	        }
			else if(viewphoto === 'Y' && photoType === 'wfOnly' ){
				//$('#wfPhotofrm').attr('action', '${pageContext.request.contextPath}/SearchPhotoData?caseId='+caseId);
				$('#wfPhotofrm').attr('action', '${pageContext.request.contextPath}/DispImageGrid?caseId='+caseId+'&photoType='+photoType+'&viewphoto='+viewphoto);
           }
			else if(photoType === 'mid'){
						$('#wfPhotofrm').attr('action', 'http://uri-for-button1.com');
			}else{
				$('#wfPhotofrm').attr('action', '${pageContext.request.contextPath}/DispImageGrid?caseId='+caseId+'&photoType='+photoType+'&viewphoto='+viewphoto);
			}
		});
		
		$("#photobtn").on("submit", function(event) {	
			alert("test")
		});*/
		
		</script>
</body>
</html>
