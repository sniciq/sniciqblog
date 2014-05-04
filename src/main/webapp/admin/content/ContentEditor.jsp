<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/admin/ckeditor/ckeditor.js"></script>
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(function() {
				CKEDITOR.replace('editor1',{});
				$j("#testBtn").click(function() {
					
					var objEditor = CKEDITOR.instances["editor1"];
					var q = objEditor.getData();
	                var url = "${ctx}/mini/cms/admin/content/ContentConroller/saveContent.sdo";
					var data = {detail:q, id:$j('#id').val(), itemId:$j("#itemId").val()};
					$j.post(url,data,function(data,status) {
						if(data.result == 'success') {
							alert('保存成功');
							window.location.href = '${ctx}/mini/cms/admin/content/ContentConroller/preview.sdo?itemId='+$j("#itemId").val();
						}
						else {
						}
					},'json');
	            });
			});
		</script>
	</head>
	<body style="overflow: hidden">
		<form id="contentForm" method="post" action="${ctx}/mini/cms/admin/content/ContentConroller/saveContent.sdo">
		<input type="hidden" id="id" name="id" value="${id}">
		<input type="hidden" id="itemId" name="itemId" value="${itemId}">
		
		<textarea id="editor1" name="detail" cols="180" rows="80">
			${content}
		</textarea>
		</form>
	</body>
</html>
		