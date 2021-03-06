<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>研究生</title>
<link href="css/second/outstandingAndGraduate.css" rel="stylesheet" type="text/css"media="screen">
<script type="text/javascript" src="js/jquery/jquery-1.8.2.min.js"></script>
<!-- 对话框的的相关导入文件 -->
<link rel="stylesheet" href="css/skins/blue.css" type="text/css"></link>
<script type="text/javascript" src="js/front/jumpWindow.js"></script>
<script type="text/javascript">
$(function(){
	//点击查看a标签弹出小窗口，将其中的title值放入窗口内 
	$("#div1 a").each(function(index){
			$(this).click(function(){
				$content = $(this).attr("title");
				art.dialog({ width:400,height:300,title:'详细信息',content:$content});
			});
	});
}) ;
</script>
</head>
<body>

<img src="images/line.png" style="margin-bottom: 10px;">
<div id="div1">
	<table class="table table-striped table-hover">
		<thead>
			<tr style="background-color: #8fa1b8;">
				<th>姓名</th>
				<th>专业</th>
				<th>详细情况</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="student" items="${requestScope.graduates }" varStatus="status">
				<tr>
				<td>${student.name }</td>
				<td>${student.major }</td>
				<td><a href="#" title="as大幅度反弹幅度df">点击查看</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
	<!-- ==================================分页开始======================================== -->
	<div id="paging">
		当前第${requestScope.pageNow }页,共${requestScope.pageCount }页&nbsp;&nbsp;
		<c:if test="${requestScope.pageNow > 1 }">
			<a fff="frontTrainAction_part?section=3&pageNow=${requestScope.pageNow - 1}" onclick="pageQuery(this)">上一页</a>&nbsp;  
		</c:if> 
		<a fff="frontTrainAction_part?section=3&pageNow=1" onclick="pageQuery(this)">[1]</a>&nbsp;  
		<c:if test="${requestScope.pageCount != 1}">  
		    <c:choose>  
		        <c:when test="${requestScope.pageNow <= 5}">  
		            <c:forEach var="i" begin="2" end="${requestScope.pageNow}">  
		                <a fff="frontTrainAction_part?section=3&pageNow=${i}" onclick="pageQuery(this)">[${i }]</a>&nbsp;  
		            </c:forEach>  
		        </c:when>  
		        <c:otherwise>  
		            ...&nbsp;  
		            <c:forEach var="i" begin="${requestScope.pageNow - 3}"  
		                end="${requestScope.pageNow}">  
		                <a fff="frontTrainAction_part?section=3&pageNow=${i}" onclick="pageQuery(this)">[${i }]</a>&nbsp;  
		            </c:forEach>  
		        </c:otherwise>  
		    </c:choose>  
		    <c:choose>  
		        <c:when test="${requestScope.pageNow>=requestScope.pageCount-4   
		            || requestScope.pageCount-4<=0}">  
		            <c:forEach var="i" begin="${requestScope.pageNow+1}"  
		                end="${requestScope.pageCount}">  
		                <a fff="frontTrainAction_part?section=3&pageNow=${i}" onclick="pageQuery(this)">[${i }]</a>&nbsp;  
		            </c:forEach>  
		        </c:when>  
		        <c:otherwise>  
		            <c:forEach var="i" begin="${requestScope.pageNow+1}"  
		                end="${requestScope.pageNow+3}">  
		                <a fff="frontTrainAction_part?section=3&pageNow=${i}" onclick="pageQuery(this)">[${i }]</a>&nbsp;  
		            </c:forEach>  
		            ...&nbsp;  
		            <a fff="frontTrainAction_part?section=3&pageNow=${requestScope.pageCount}" onclick="pageQuery(this)">  
		                [${requestScope.pageCount}]</a>&nbsp;  
		        </c:otherwise>  
		    </c:choose>  
		    <c:if test="${requestScope.pageNow < requestScope.pageCount }">
				<a fff="frontTrainAction_part?section=3&pageNow=${requestScope.pageNow + 1}" onclick="pageQuery(this)">下一页</a>&nbsp;  
			</c:if> 
		</c:if>  
	</div>
	<!-- ==================================分页结束======================================== -->
</body>
</html>