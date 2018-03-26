<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
    prefix="decorator"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />

<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="es"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="es"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="es"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html lang="es"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>Facturación Efectiva - BST M&eacute;xico ::.</title>

    <link href="${css}/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${css}/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="${css}/sb-admin-2/sb-admin-2.css" rel="stylesheet">
    <link href="${css}/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	
    <script>
        var contextPath = "${contextPath}";
    </script>
    <script src="${js}/jquery/jquery.min.js"></script>
    <script src="${js}/jquery/jquery.validate.js"></script>
    <script src="${js}/jquery/jquery.blockui.js"></script>
	<script src="${js}/jquery/localization/messages_es.js"></script>
    <script src="${js}/bootstrap/bootstrap.min.js"></script>
    <script src="${js}/metisMenu/metisMenu.min.js"></script>
    <script src="${js}/sb-admin-2/sb-admin-2.js"></script>
    <script src="${js}/knockout/knockout-3.3.0.js"></script>
    <script src="${js}/utils/string-utils.js?v=${project.version}"></script>
    <script src="${js}/toastr/toastr.js"></script>
    
    <script src="${js}/app/emisores/select.emisor.js?v=${project.version}"></script>
</head>
<body>
	<div id="wrapper">
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
                <img src="http://logos.bstmexico.com/Logo_FACTURACIONEFECTIVA_SMALL.png"  alt="Facturacion Efectiva" class="logo"/>
            </div>
            <ul class="nav navbar-top-links navbar-right">
                <li>
                    <c:if test="${not empty emisorSession}">
                    <ul>${emisorSession.nombreCompleto}</ul>
                    </c:if>
                    <c:if test="${empty emisorSession}">
                    <ul class="msjError">No se ha seleccionado un emisor</ul>
                    </c:if>
                </li>
                <li class="dropdown">
                    <a id="short-menu" class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <decorator:getProperty property="page.menu-short" />
                </li>
            </ul>
		</nav>
        <decorator:body />
	</div>
</body>
</html>