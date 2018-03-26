<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Registro de Productos</title>
<script src="${js}/app/emisores/emisores.js"></script>
<script src="${js}/app/commons/catalogos.js?version=${project.version}"></script>
<script src="${js}/app/productos/productos.js?version=${project.version}"></script>
<script src="${js}/app/productos/productos.app.js?version=${project.version}"></script>

<script type="text/javascript">
$(function () {
	var data = {};

	if (window.producto) {
		data.producto = producto;
	}
	
	var modelView = new ProductoViewModel(data)
	ko.applyBindings(modelView);
});
</script>

</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Nuevo Producto</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						Datos del Producto<img data-toggle="modal" class="ayuda" data-target="#myModalayuda" src="${rsrc}/img/icon/interrogacion.png" width="20px" height="20px" alt=""/>
					</div>
					<div class="panel-body">
						<jsp:include page="producto.jsp" />
					</div>
					<div class="panel-footer text-right">
						<button type="button" id="btn-prod-new" class="btn btn-info" data-bind="click: guardar">Guardar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>