<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">




<script src="./js/jquery/jquery-1.8.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="./js/jquery/jquery.wordlimitor-1.0.1.js"></script>	
<script type="text/javascript" src="./js/jquery/jquery.pointer-1.0.js"></script>	
<script charset="utf-8" src="./kindeditor-4.1.5/kindeditor.js"></script>
<script charset="utf-8" src="./kindeditor-4.1.5/kindeditor-min.js"></script>
<script charset="utf-8" src="./kindeditor-4.1.5/lang/zh_CN.js"></script>
<script type="text/javascript">
//采用ajax提交论坛发帖
KindEditor.ready(function(K) {
	     K.create('#forum_id', {
	             uploadJson : './kindeditor-4.1.5/jsp/upload_json.jsp',
	             fileManagerJson : './kindeditor-4.1.5/jsp/file_manager_json.jsp',
	             allowFileManager : true,
	             //设计kinder的固定宽高
	             width:"625px",
				 minWidth:"625px",
				 height:"230px",
				 minHeight:"230px"
	        });
	});  
</script>
<script type="text/javascript">
$(function(){
	//查询
	$("#submitItem").click(function()
			{
				//获取表单数据插入数据库
				var title = $.trim($("#title").val());
				var content = $.trim($("#forum_id").val());
				//验证为空？
				if(title == "" || title == null)
					{
						alert("主题不能为空!!!");
						return false;
					}
				else if(content == "" || content == null)
					{
						alert("内容不能为空!!!");
						return false;
					}
				//ajax返回状态信息是否发帖成功
				else
					{
						$.post("frontExchangeAction_addForum",{
							title:title,
							content:content
						},function(data){
							//返回状态信息
							alert("发帖成功 ^_^");
							$("#title").val("");
							$("#forum_id").val("");
						});
					}
				
			});
	
});
</script>
<title>学术论坛</title>
<link href="css/second/academicBBSindex.css" rel="stylesheet" media="screen">
</head>
<body>
<c:if test="${article ==null}">
	<div id="div"></div>
	<div id="div1">
		<table class="table table-striped">
			<tr>
				<th style="background-color: #8fa1b8; color: #fff">主题</th>
				<th style="background-color:  #8fa1b8; color: #fff">楼主</th>
				<th style="background-color:  #8fa1b8; color: #fff">发表时间</th>
			</tr>
			<!-- 显示回帖内容 -->
			<c:forEach items="${ForumList}" var="forumTitle">
			<tr>
				<td><a limit="22" fff="frontExchangeAction_queryContentByparentId?id=${forumTitle.id}" onclick="pageQuery(this)" style="cursor: pointer;">${forumTitle.topic_content }</a></td>
				<td style="width: 100px;">${forumTitle.spokerName}</td>
				<td style="width: 200px;">${forumTitle.spokerTime}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
<!-- 分页显示 --------------------------------------------------------------------------------------------->
	<div id="paging">
		当前第${requestScope.pageNow }页,共${requestScope.pageCount }页&nbsp;&nbsp;
		<c:if test="${requestScope.pageNow > 1 }">
			<a fff="frontExchangeAction_part?section=4&pageNow=${requestScope.pageNow-1}" onclick="pageQuery(this)" style="cursor:pointer;">上一页</a>&nbsp; 
		</c:if>
		<a fff="frontExchangeAction_part?section=4&pageNow=1" onclick="pageQuery(this)" style="cursor:pointer;">[1]</a>&nbsp;  
		<c:if test="${requestScope.pageCount != 1}">  
		    <c:choose>  
		        <c:when test="${requestScope.pageNow <= 5}">  
		            <c:forEach var="i" begin="2" end="${requestScope.pageNow}">  
		                <a fff="frontExchangeAction_part?section=4&pageNow=${i}" onclick="pageQuery(this)" style="cursor:pointer;">[${i}]</a>&nbsp;  
		            </c:forEach>  
		        </c:when>  
		        <c:otherwise>  
		            ...&nbsp;  
		            <c:forEach var="i" begin="${requestScope.pageNow - 3}"  
		                end="${requestScope.pageNow}">  
		                <a fff="frontExchangeAction_part?section=4&pageNow=${i}" onclick="pageQuery(this)" style="cursor:pointer;">[${i}]</a>&nbsp;  
		            </c:forEach>  
		        </c:otherwise>  
		    </c:choose>  
		    <c:choose>  
		        <c:when test="${requestScope.pageNow>=requestScope.pageCount-4   
		            || requestScope.pageCount-4<=0}">  
		            <c:forEach var="i" begin="${requestScope.pageNow+1}"  
		                end="${requestScope.pageCount}">  
		                <a fff="frontExchangeAction_part?section=4&pageNow=${i}" onclick="pageQuery(this)" style="cursor:pointer;">[${i }]</a>&nbsp;  
		            </c:forEach>  
		        </c:when>  
		        <c:otherwise>  
		            <c:forEach var="i" begin="${requestScope.pageNow+1}"  
		                end="${requestScope.pageNow+3}">  
		                <a fff="frontExchangeAction_part?section=4&pageNow=${i}" onclick="pageQuery(this)" style="cursor:pointer;">[${i }]</a>&nbsp;  
		            </c:forEach>  
		            ...&nbsp;  
		            <a fff="frontExchangeAction_part?section=4&pageNow=${requestScope.pageCount}" onclick="pageQuery(this)" style="cursor:pointer;">  
		                [${requestScope.pageCount}]</a>&nbsp;  
		        </c:otherwise>  
		    </c:choose>  
		   <c:choose>
		   	 <c:when test="${requestScope.pageNow < requestScope.pageCount}">
		    	<a fff="frontExchangeAction_part?section=4&pageNow=${requestScope.pageNow+1}" onclick="pageQuery(this)" style="cursor:pointer;">下一页</a> 
		    </c:when>
		   </c:choose>
		</c:if>  
	</div>
	<!-- 发帖 -->
	<div id="div2">
		<!-- 
		<p class="p">发表主题</p>
		<form action="" method="post"  enctype="multipart/form-data" >
		<p class="p1">
			标题 : <input type="text" id="title" max="30">
		</p>
		<p class="p1">
			内容 :
			<textarea id="forum_id" name="forum" ></textarea>
		</p>
		<input type="button" class="btn" value="提交" id="submitItem">
		</form>
		 -->
		 <div id="headLine">发表主题</div>
		 <form action="" method="post">
		 	<div id="titleDiv">标题：<input type="text" id="title" maxlength="30"></div>
		 	<div id="contentDiv">内容：<textarea id="forum_id" name="forum" maxlength="500"></textarea></div>
		 	<input type="button" class="btn" value="提交" id="submitItem">
		 </form>
	</div>
</c:if>
<c:if test="${article !=null}">
	${article.content}
	<a href="frontIndexAction_login" title="发帖先登陆">登陆</a>
</c:if>
</body>
</html>