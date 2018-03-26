<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
	<title>Registro de comprobante</title>
	
	<script src="${js}/app/emisores/emisores.js?version=${project.version}"></script>
	<script src="${js}/app/commons/catalogos.js?version=${project.version}"></script>
	<script src="${js}/app/clientes/clientes.js?version=${project.version}"></script>
	<script src="${js}/app/facturas/facturas.js?version=${project.version}"></script>
	<script src="${js}/app/facturas/facturas.app.js?version=${project.version}"></script>
	<script src="${js}/app/series/series.js?version=${project.version}"></script>
	<script src="${js}/numeral/numeral.js"></script>
	
	<script>
		var data = {
			facturable : true,
			comprobante : ${comprobante} 
		}
	</script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Editar Comprobante Fiscal Digital</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						Datos del Comprobante
					</div>
					<div class="panel-body">
						<jsp:include page="form_venta.jsp" />
					</div>
					<div class="panel-footer text-right">
						<button type="button" class="btn btn-info" data-bind="click: guardar">Guardar</button>
						<button type="button" class="btn btn-info" data-bind="visible: (facturable() && plantilla() && !certificado() && !timbres()), click: facturar">Facturar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="conceptos/modales/concepto.jsp" />
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>