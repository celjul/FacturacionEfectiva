<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />
<c:set var="fecha" value="${rsrc}/img" />

<html>
<head>
	<title>Registro de comprobante</title>
	<script src="${js}/app/emisores/emisores.js?version=${project.version}"></script>
	<script src="${js}/app/commons/catalogos.js?version=${project.version}"></script>
	<script src="${js}/app/clientes/clientes.js?version=${project.version}"></script>
	<script src="${js}/app/facturas/facturas.js?version=${project.version}"></script>
	<script src="${js}/app/series/series.js?version=${project.version}"></script>
	<script src="${js}/app/facturas/facturas.app.js?version=${project.version}"></script>
	<script src="${js}/numeral/numeral.js"></script>

	<script>
		var data = {
			facturable : false,
			comprobante : {
				fechaEntrega : $.datepicker.formatDate("dd-mm-yy", new Date()),
				fechaCreacion : $.datepicker.formatDate("dd-mm-yy", new Date()),
				condicion : "Inmediato",
				forma : "En una sola exhibición"
			} 
		}
	</script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Nuevo Comprobante Fiscal Digital 3.3</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						Datos del Comprobante <img data-toggle="modal" data-target="#myModalayuda" class="ayuda" src="${rsrc}/img/icon/interrogacion.png" width="20px" height="20px" alt=""/>
					</div>
					<div class="panel-body">
						<jsp:include page="form_venta.jsp" />
					</div>
					<div class="panel-footer text-right">
						<button type="button" id="btn-guardar" class="btn btn-info" data-bind="click: guardar">Guardar</button>
						<button type="button" id="btn-timbrar" class="btn btn-info" data-bind="visible: (facturable() && plantilla() && !certificado() && !timbres()), click: facturar">Facturar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="conceptos/modales/concepto.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>