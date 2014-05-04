<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<c:set var="version" value="${applicationScope.SysVersion}"></c:set>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>MiNiCMS</title>
		<meta name="title" content="MiNi CMS"/>
	    <meta name="application-name" content="MiNi CMS" />
		<link rel="Shortcut Icon" href="${ctx}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${ctx}/ExtJS/resources/css/ext-all.css.gzipfile"/>
		
		<script type="text/javascript" src="${ctx}/ExtJS/adapter/ext/ext-base.js.gzipfile"></script>
		<script type="text/javascript" src="${ctx}/ExtJS/ext-all.js.gzipfile"></script>
		<script type="text/javascript" src="${ctx}/ExtJS/src/locale/ext-lang-zh_CN.js"></script>
		
		<script type="text/javascript" src="${ctx}/ExtJS/plugin/cellselect.js?v=${version}"></script>
		<script type="text/javascript" src="${ctx}/ExtJS/plugin/chrome18up_grid_bug_fix.js?v=${version}"></script>
		
		<script type="text/javascript" src="${ctx}/ExtJS/examples/ux/treegrid/TreeGridSorter.js"></script> 
        <script type="text/javascript" src="${ctx}/ExtJS/examples/ux/treegrid/TreeGridColumnResizer.js"></script> 
        <script type="text/javascript" src="${ctx}/ExtJS/examples/ux/treegrid/TreeGridNodeUI.js"></script> 
        <script type="text/javascript" src="${ctx}/ExtJS/examples/ux/treegrid/TreeGridLoader.js"></script> 
        <script type="text/javascript" src="${ctx}/ExtJS/examples/ux/treegrid/TreeGridColumns.js"></script> 
        <script type="text/javascript" src="${ctx}/ExtJS/examples/ux/treegrid/TreeGrid.js"></script>
        
        <script type="text/javascript" src="${ctx}/ExtJS/examples/ux/DataViewTransition.js"></script>
        
        <script type="text/javascript" src="${ctx}/ExtJS/examples/ux/LockingGridView.js"></script>  
        <link rel="stylesheet" type="text/css" href="${ctx}/ExtJS/examples/ux/css/LockingGridView.css" />
        
		<link rel="stylesheet" type="text/css" href="css/buttons.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="css/menu.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="css/animated-dataview.css" />
		<link rel="stylesheet" type="text/css" href="css/extextr.css?v=${version}"/>

		<!-- 首页JS -->
		<script type="text/javascript" src="${ctx}/ExtJS/examples/ux/Portal.js"></script>
		<script type="text/javascript" src="${ctx}/ExtJS/examples/ux/PortalColumn.js"></script>
		<script type="text/javascript" src="${ctx}/ExtJS/examples/ux/Portlet.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/ExtJS/examples/ux/css/Portal.css"/>
		
		<!--主JS 验证及导出等JS -->
		<script type="text/javascript" src="js/core/gridToExcel.js?v=${version}"></script>
		<script type="text/javascript" src="js/ValidateSession.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/CommonRenderer.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/export.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/CommonChart.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/JSLoader.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/TabCloseMenu.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/App.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/MenuTreePanel.js?v=${version}"></script>
		<script type="text/javascript" src="js/core/DataViewMenuPanel.js?v=${version}"></script>
		
		<script type="text/javascript">
			var globalCtx = '${ctx}';
		</script>
		
		<script type="text/javascript" src="main.js?v=${version}"></script>
		
		<style type="text/css">  
		    .x-selectable, .x-selectable * {  
		        -moz-user-select: text!important;  
		        -khtml-user-select: text!important;  
		    }  
		</style> 
		
		<style type="text/css">
			div.panel_header_main {
			    text-align: center;
			    height: 22px;
			    line-height: 22px;
			    float: left;
			    margin: auto;
			}
			
			
			div.panel_header_icon {
			    text-align: right;
			    float: left;
			    margin-left: 3px;
			    cursor: hand;
			    cursor: pointer;
			}
			
			div.panel_header_extra {
			    text-align: left;
			    float: right;
			    margin-right: 10px;
			}
			
			div.panel_header_icon1 {
			    text-align: right;
			    float: right;
			    margin-left: 3px;
			    cursor: hand;
			    cursor: pointer;
			}
			
			div.panel_header_icon2 {
			    text-align: right;
			    float: right;
			    margin-left: 3px;
			    cursor: hand;
			    cursor: pointer;
			}
		</style>
		
		<script type="text/javascript">
			function chkLeave(event) {
				//return "您确定离开MiNi CMS吗?";
			}
		</script>
	</head>
	<body onbeforeunload="return chkLeave(event);">
		<div id="SystemName-div" class="x-border-layout-ct">
			<table border="0"><tr><td valign="middle"></td><td><a style="font-weight: bold;font-size: 20;text-align: center;color: #3F3A39">MiNi CMS</a></td></tr></table>
		</div>
		
		<div id="StartMenu">
			<table border="0"><tr><td valign="middle"><a style="font-weight: bold;font-size: 20;text-align: center;color: #3F3A39">MiNi CMS</a></td></tr></table>
		</div>
		
		<div id="north-div">
		</div>
		<div id="west-div"></div>
		<div id="center-div"></div>
		<div id="south-div"></div>
	</body>
</html>