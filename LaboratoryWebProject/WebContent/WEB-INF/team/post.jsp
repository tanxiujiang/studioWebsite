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
				<td align="center">学术带头人：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.post.academicLeaders }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="center">主任：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.post.directors }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="center">办公室：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.post.offices }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="center">组长：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.post.leaders }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="center">教师：</td>
				<td>
				<c:forEach var="teacher" items="${requestScope.post.teachers }" varStatus="status">
					&nbsp; <a fff="frontTeamAction_getTeacherInclude?teacherId=${teacher.teacherId }" onclick="pageQuery(this)">${teacher.name }</a>&nbsp;
				</c:forEach>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
