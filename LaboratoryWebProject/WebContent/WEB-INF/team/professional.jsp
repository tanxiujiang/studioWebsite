<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/second/teamSubPageBase.css" type="text/css"></link>
</head>
<body>
	<div id="div"></div>
	<div id="div1">
		<table class="table table-striped table-hover">
		<tbody>
			<tr>
				<td align="center">博士讲师：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.title.doctors }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="center">教授：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.title.professors }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="center">副教授：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.title.assistprofessors }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="center">其他：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.title.others }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
</body>
</html>