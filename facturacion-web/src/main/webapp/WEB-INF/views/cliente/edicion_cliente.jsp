<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Registro de Cliente</title>
<script src="${js}/app/emisores/emisores.js"></script>
<script src="${js}/app/commons/catalogos.js?version=${project.version}"></script>
<script src="${js}/app/direcciones/direcciones.js?version=${project.version}"></script>
<script src="${js}/app/clientes/clientes.js?version=${project.version}"></script>
<script
	src="${js}/app/clientes/cliente.app.js?version=${project.version}"></script>
<script type="text/javascript">
$(function() {
	var cliente;
	if (window.cliente) {
		cliente = window.cliente;
	}

	var modelView = new ClienteViewModel(${cliente});
	ko.applyBindings(modelView);
	
// 	var autocompleteDirecciones = {
// 		minLength : 3,
// 		source : function(request, response) {
// 			$.ajax({
// 				url: String.format("{0}/app/direcciones/municipios/{1}/colonias", contextPath, modelView.direccion.municipio.id()),
// 				dataType: "json",
// 				data : {
// 					nombre: $("#txt-colonia").val()
// 				},
// 				success: function(data) {
// 					response($.map(data, function(item) {
// 						return {
// 							label: item.nombre,
// 							value: item.id, 
// 							codigoPostal: item.codigoPostal
// 						};
// 					}));
// 				}
// 			});
// 		},
// 		select: function(event, ui) {
// 			console.log(ui.item);
// 			modelView.direccion.colonia.id(ui.item.value);
// 			modelView.direccion.colonia.nombre(ui.item.label);
// 			modelView.direccion.colonia.codigoPostal(ui.item.codigoPostal);
			
// 			return false;
// 		}, 
// 		focus: function(event, ui) {
// 			modelView.direccion.colonia.nombre(ui.item.label);
// 			modelView.direccion.colonia.codigoPostal(ui.item.codigoPostal);
// 			return false;
// 		},
// 		change: function(event, ui) {
// 			if (!ui.item) {
// 				modelView.direccion.limpiar();
// 			} else {
// 				modelView.direccion.colonia.id(ui.item.value);
// 				modelView.direccion.colonia.nombre(ui.item.label);
// 				modelView.direccion.colonia.codigoPostal(ui.item.codigoPostal);
// 			}
// 			return false;
// 		}
// 	};

// 	$("#txt-colonia").autocomplete(autocompleteDirecciones);
});
</script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Actualizar Cliente</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">Datos del Cliente</div>
					<div class="panel-body">
						<jsp:include page="cliente.jsp">
							<jsp:param value="alta" name="action" />
						</jsp:include>
					</div>
					<div class="panel-footer text-right">
						<button type="button" class="btn btn-info"
							data-bind="click: guardar">Actualizar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../direccion/modales/direccion.jsp">
		<jsp:param value="cliente" name="tipo" />
	</jsp:include>
	<jsp:include page="../cliente/contactos/modales/contacto.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>