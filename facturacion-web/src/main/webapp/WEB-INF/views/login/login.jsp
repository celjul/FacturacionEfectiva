<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

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

	<title>Facturaci&oacute;n Efectiva - BST M&eacute;xico ::.</title>

    <link href="${css}/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${css}/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="${css}/sb-admin-2/sb-admin-2.css" rel="stylesheet">
    <link href="${css}/font-awesome/f ont-awesome.min.css" rel="stylesheet" type="text/css">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
    	var contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${js}/jquery/jquery.min.js"></script>
    <script src="${js}/jquery/jquery.validate.js"></script>
    <script src="${js}/jquery/jquery.blockui.js"></script>
	<script src="${js}/jquery/localization/messages_es.js"></script>
    <script src="${js}/bootstrap/bootstrap.min.js"></script>
    <script src="${js}/toastr/toastr.js"></script>
    <script src="${js}/metisMenu/metisMenu.min.js"></script>
    <script src="${js}/utils/string-utils.js"></script>
    <script src="${js}/sb-admin-2/sb-admin-2.js"></script>
    
</head>
<body>
	<div class="container " style="padding-top: 60px; align-content: center">

        <div class="row">
            <div class="col-md-4 col-md-offset-4" >
                <div class="">
                    <!--<div class="panel-heading">
                        <h3 class="panel-title">Login</h3>
                    </div>-->
                    <div class="panel-body text-center">
                        <div >
                            <img src="${img}/LogoFactEfectiva.png" alt="">
                        </div>
                    </div>
                    <div class="panel-body" style="margin-left: 10%">
                        <c:if test="${not empty error}">
                            <div class="alert alert-warning alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                Lo sentimos, no reconocemos tu nombre de usuario o password.
                            </div>
                        </c:if>
                        <form role="form" id="loginForm" action="<c:url value='j_spring_security_check' />" method="post" novalidate="novalidate">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Usuario" id="j_username" name="j_username" required="required" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" id="j_password" name="j_password" required="required" type="password">
                                </div>
                                <button  type="submit" class="btn btn-info sesion">Iniciar Sesion</button>
                               <%-- <button type="submit" class="btn btn-lg btn-success btn-block" id="btn-login">Siguiente</button>--%>
                            </fieldset>
                            <div class="panel-heading">
                                <h1 class="registrate">Facturaci&oacute;n<br/>
                                    <a id="autoregistro" href="newAccount">Versi&oacute;n CFDI 3.3</a></h1>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
</html>