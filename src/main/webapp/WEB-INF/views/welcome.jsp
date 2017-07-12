<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/kendo.common.min.css" rel="stylesheet">
	<link href="${contextPath}/resources/css/kendo.default.min.css" rel="stylesheet">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</head>
<body>

<div class="container">

<!-- Modal -->
	<div class="modal fade" id="editTask" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  	<div class="modal-dialog" role="document">
	    	<div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        	<h4 class="modal-title" id="myModalLabel">Task</h4>
	      	</div>
	      
		      <form method="POST" action="${contextPath}/update" class="form-signin">
			      	<div class="modal-body">
				        <div class="form-group">
					        <div class="input-group input-group-lg">
						        <span class="input-group-addon" >Title</span>
						  		<input id="addon1" name="name" type="text" class="form-control" placeholder="Task name">
					  		</div>
					  		<br>
					  		<div class="input-group input-group-lg">
						  		<span class="input-group-addon" id="addon2">Start</span>
						  		<input id="addon2" name="start" type="text" class="form-control" placeholder="e.g. 2017-01-01 08:05">
					  		</div>
					  		<br>
					  		<div class="input-group input-group-lg">
						  		<span class="input-group-addon" id="addon3">End</span>
						  		<input id="addon3" name="end" type="text" class="form-control" placeholder="e.g. 2017-01-20 10:30">
					  		</div>
				  			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  			<input id="addon0" type="hidden" class="form-control" name="id">
				  		
					  		<div class="modal-footer">
						        <button id="closeButton" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        <button type="submit" class="btn btn-primary">Save changes</button>
					      	</div>
			        	</div>
			 		</div>
		    	</form>
	    	</div>
	  	</div>
	</div>
<!-- Modal -->
	
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
    	<h2>${msg} </h2>
    	<div id="team-schedule">
		    <div id="people">
		    </div>
		</div>
		<div>
			<button id="addButton" type="button" class="btn btn-primary" data-toggle="modal" data-target="#editTask" >Add New Task</button>
		</div>
		<br>
		<div class="panel panel-default">
		  <!-- Default panel contents -->
		  <div class="panel-heading" style="text-align:center">To Do Lists</div>
		
		  <!-- Table -->
		  <table  id="bootstrap-overrides" class="table" >
			  <tr>
			  	<td>Id</td>
			  	<td>Task</td>
				<td>Start</td>
				<td>End</td>
				<td>Action</td>
			</tr>
			<tbody>
				<c:forEach var="task" items="${tasks}" varStatus="counter">
		    		<tr>
		    			<td type="hidden">${task.id }</td>
			    		<td>${task.name} </td>
			    		<td>${task.start}</td>
			    		<td>${task.end}</td>
			    		<td>
			    			<ul class="nav nav-pills">
				    			<div>
								  <li class="popup" taskItem = ${task } data-toggle="modal" data-target="#editTask" role="presentation"><a href="#">Edit</a></li>
								  <li role="presentation"><a href="${contextPath}/delete/${task.id}">Delete</a></li>
								</div>
							</ul>
						</td>
		    		</tr>
		    	</c:forEach>
	    	</tbody>
		  </table>
		  <!-- Table -->
		</div>
    </c:if>

</div>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script>
		$("div.container div button#addButton").click(function(){
			$("div#editTask .modal-body input#addon0").val('');
			$("div#editTask .modal-body input#addon1").val('');
		});
		$(".popup").click(function(){
			var task = $(this).closest("tr").get(0).cells;
			var test = $("div#editTask .modal-body input#addon1");
			$("div#editTask .modal-body input#addon0").val(task[0].innerText);
			$("div#editTask .modal-body input#addon1").val(task[1].innerText);
			$("div#editTask .modal-body input#addon2").val(task[2].innerText);
			$("div#editTask .modal-body input#addon3").val(task[3].innerText);
		});
</script>

<style>
#team-schedule {
    background: url(<c:url value="/resources/web/scheduler/team-schedule.png" />) transparent no-repeat;
    height: 115px;
    position: relative;
}

#people {
    background: url(<c:url value="/resources/web/scheduler/scheduler-people.png" />) no-repeat;
    width: 345px;
    height: 115px;
    position: absolute;
    right: 0;
}
table#bootstrap-overrides tr td {
	vertical-align: middle; !important;
	align:middle;
	text-align:center;
}
table#bootstrap-overrides tr td div{
	align:middle;
}

</style>
</body>
</html>