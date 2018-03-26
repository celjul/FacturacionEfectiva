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

    <link href="${css}/jquery.ui.theme/jquery-ui.css" rel="stylesheet">
    <link href="${css}/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${css}/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="${css}/sb-admin-2/sb-admin-2.css" rel="stylesheet">
    <link href="${css}/toastr/toast.css" rel="stylesheet">
    <link href="${css}/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script>
        var contextPath = "${contextPath}";
        var session = {
            <c:if test="${not empty emisorSession}">
            emisor : ${emisorSession.id}
            </c:if>
        }

        function testPlantilla() {
            var url = "${contextPath}/app/comprobantes/test?_=" + new Date().getTime();
            location.href =  url;
        }
    </script>
    <script src="${js}/jquery/jquery.min.js"></script>
    <script src="${js}/jquery/jquery.validate.js"></script>
    <script src="${js}/jquery/jquery.form.js"></script>
    <script src="${js}/jquery/jquery.blockui.js"></script>
    <script src="${js}/jquery/localization/messages_es.js?v=${project.version}"></script>
    <script src="${js}/jquery/jquery.validate.custom.js?v=${project.version}"></script>
    <script src="${js}/jquery/additional-methods.js?v=${project.version}"></script>

    <script src="${js}/jquery/jquery-ui-1.10.3.custom.js"></script>
    <script src="${js}/jquery/jquery-ui-datepicker-es.js" charset="utf8"></script>

    <script src="${js}/bootstrap/bootstrap.min.js"></script>
    <script src="${js}/bootstrap/bootbox.min.js"></script>
    <script src="${js}/metisMenu/metisMenu.min.js"></script>
    <script src="${js}/sb-admin-2/sb-admin-2.js?v=${project.version}"></script>
    <script src="${js}/knockout/knockout-3.3.0.js"></script>
    <script src="${js}/toastr/toastr.js"></script>
    <script src="${js}/utils/string-utils.js?v=${project.version}"></script>
    <script type="text/javascript">
    $(function() {
		$.get(String.format("{0}/app/folios/", contextPath), function(data) {
			if (data) {
				if (data.disponibles != undefined) {
					$("#spanNoFacturas").text(data.utilizados);
					$("#spanTotalFacturas").text(data.disponibles);
	                $("#spanDiasRestantes").text(data.diasRestantes);
	
	                if (data.diasRestantes >= 1) {
	                    $("#mensajeAlerta").html("<span style='color: #cd0a0a'>El paquete adquirido expira en: " + data.diasRestantes + " día" + (data.diasRestantes > 1?'s':'') + "!  </span>");
	                } else if (data.diasRestantes == 0) {
	                    $("#mensajeAlerta").html("<span style='color: #cd0a0a'>El paquete expira hoy!, Atención a clientes al (55) 12041299.</span>");
	                }
	
				} else {
					$("#mensajeFolios").html("Timbres Utilizados : " + data.utilizados);
				}
			} else {
				$("#mensajeFolios").html("Tus timbres han expirado! Contrata un paquete adicional");
			}
		});
    });
    </script>

    <decorator:head />
</head>
<body>
<div id="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class=" row navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <%--<a class="navbar-brand" href="#">BST M&eacute;xico</a>--%>
            <img src="http://logos.bstmexico.com/Logo_FACTURACIONEFECTIVA_SMALL.png"  alt="Facturacion Efectiva" class="logo"/>
        </div>

        <ul class="nav navbar-top-links navbar-right">
            <li id="mensajeAlerta" style="padding-right: 20px"></li>
            <li id="mensajeFolios">
                Timbres Utilizados : <span id="spanNoFacturas"></span> de <span id="spanTotalFacturas"></span>
            </li>
            <li class="emisor">
                <c:if test="${not empty emisorSession}">
                    <ul>Emisor: <strong class="nomemisor">${emisorSession.nombreCompleto}</strong> </ul>
                </c:if>
                <c:if test="${empty emisorSession}">
                    <ul>No se ha seleccionado un emisor</ul>
                </c:if>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="${contextPath}/app/usuarios/profile"><i class="fa fa-user fa-fw"></i>Perfil</a>
                    </li>
                    <li><a href="${contextPath}/app/emisores/select"><i class="fa fa-gear fa-fw"></i> Cambio Emisor</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="<c:url value="/j_spring_security_logout"/>"><i class="fa fa-sign-out fa-fw"></i> Salir</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>

        <div class="navbar-default sidebar" role="navigation">
            <decorator:getProperty property="page.menuAux" />
        </div>
    </nav>

    <decorator:body />

    <footer>
        <div class="container fondo col-md-12 ">

            <div class="row" >
                <div class="col-md-12 abajo "  style="padding-top: 20px;" >
                    <strong>© 2016 FACTURACIÓN EFECTIVA</strong><br>
                    <strong>5512041299 | soporte@facturacionefectiva.com</strong>
                    <p>Un producto de BST MEXICO Tecnologías de la Información. |  <a href="http://facturacionefectiva.com/" style="color: #FFF">www.facturacionefectiva.com </a>| © Copyright Todos los derechos reservados.<br/>



                </div> <!-- /col-xs-7 -->


            </div>
        </div>
    </footer>

</div>
</body>
</html>