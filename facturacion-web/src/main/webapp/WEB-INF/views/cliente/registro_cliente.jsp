
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
<title>Registro de Cliente</title>

<script type="text/javascript">
$(function() {
	var cliente;
	if (window.cliente) {
		cliente = window.cliente;
	}

	var modelView = new ClienteViewModel(cliente);
	ko.applyBindings(modelView);
	
	var autocompleteDirecciones = {
		minLength : 3,
		source : function(request, response) {
			$.ajax({
				url: String.format("{0}/app/direcciones/municipios/{1}/colonias", contextPath, modelView.direccion.municipio.id()),
				dataType: "json",
				data : {
					nombre: $("#txt-colonia").val()
				},
				success: function(data) {
					response($.map(data, function(item) {
						return {
							label: item.nombre,
							value: item.id, 
							codigoPostal: item.codigoPostal
						};
					}));
				}
			});
		},
		select: function(event, ui) {
			console.log(ui.item);
			modelView.direccion.colonia.id(ui.item.value);
			modelView.direccion.colonia.nombre(ui.item.label);
			modelView.direccion.colonia.codigoPostal(ui.item.codigoPostal);
			
			return false;
		}, 
		focus: function(event, ui) {
			modelView.direccion.colonia.nombre(ui.item.label);
			modelView.direccion.colonia.codigoPostal(ui.item.codigoPostal);
			return false;
		},
		change: function(event, ui) {
			if (!ui.item) {
				modelView.direccion.colonia.limpiar();
			} else {
				modelView.direccion.colonia.id(ui.item.value);
				modelView.direccion.colonia.nombre(ui.item.label);
				modelView.direccion.colonia.codigoPostal(ui.item.codigoPostal);
			}
			return false;
		}
	};

	$("#txt-colonia").autocomplete(autocompleteDirecciones);
});
</script>
<script src="${js}/app/emisores/emisores.js"></script>
<script src="${js}/app/direcciones/direcciones.js?version=${project.version}"></script>
<script src="${js}/app/commons/catalogos.js?version=${project.version}"></script>
<script src="${js}/app/clientes/clientes.js?version=${project.version}"></script>
<script src="${js}/app/clientes/cliente.app.js?version=${project.version}"></script>

</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Nuevo Cliente</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						Datos del Cliente  <img data-toggle="modal" class="ayuda" data-target="#myModalayuda" src="${rsrc}/img/icon/interrogacion.png" width="20px" height="20px" alt=""/>
					</div>
					<div class="panel-body">
						<jsp:include page="cliente.jsp">
							<jsp:param value="alta" name="action"/>
						</jsp:include>
					</div>
					<div class="panel-footer text-right">
						<button type="button" id="btn-guardar" class="btn btn-info" data-bind="click: guardar">Guardar</button>
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





	<!-- Modal -->
	<div id="myModalayuda" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal conte	nt-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Servicio de ayuda.</h4>
				</div>
				<div class="modal-body">
					<label>Los campos a registrar son los siguientes:<br></label>

					<ul>
						<li class="txtayuda"> <strong> Tipo de cliente.</strong> Persona Física o Persona Moral
							.</li>
						<li class="txtayuda"> <strong>RFC.</strong> Para mas informacion <a href="https://rfc.siat.sat.gob.mx/PTSC/RFC/menu/index.jsp?opcion=2">ir a:</a></li>
						<li class="txtayuda"> <strong>Razon Social.</strong>La razon social es la denominacion por la cual se conoce colectivamente a una empresa.</li>
						<li class="txtayuda"> <strong>Razon Comercial.</strong>La razon comercial esel giro en el cual la empresa se encuentra.</li>




					</ul>


				</div>
				<div class="modal-footer">
				</div>
			</div>

		</div>
	</div>

</body>
</html>
