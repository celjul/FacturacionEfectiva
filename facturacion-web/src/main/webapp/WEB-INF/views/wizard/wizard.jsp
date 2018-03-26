<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />
<c:set var="fecha" value="${rsrc}/img" />

<html>
<head>
<title>Wizard</title>

<script src="${js}/app/wizard/wizard.js?version=${project.version}"></script>
<script
	src="${js}/app/direcciones/direcciones.js?version=${project.version}"></script>
<script src="${js}/app/emisores/emisores.js?version=${project.version}"></script>
<script
	src="${js}/app/emisores/emisores.new.app.js?version=${project.version}"></script>
<script
	src="${js}/app/emisores/select.emisor.js?version=${project.version}"></script>
<script src="${js}/app/clientes/clientes.js?version=${project.version}"></script>
<script
	src="${js}/app/clientes/cliente.app.js?version=${project.version}"></script>
<script src="${js}/app/commons/catalogos.js?version=${project.version}"></script>
<script
	src="${js}/app/productos/productos.js?version=${project.version}"></script>
<script
	src="${js}/app/productos/productos.app.js?version=${project.version}"></script>
<script src="${js}/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<script src="${js}/app/wizard/certificado.js?version=${project.version}"></script>
<script
	src="${js}/app/plugin/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
<script
	src="${js}/app/plugin/jquery-fileupload/js/jquery.iframe-transport.js"></script>
<script src="${js}/app/plugin/jquery-fileupload/js/jquery.fileupload.js"></script>

<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			$("#btnCertificado").click(function() {
				$("#form-certificado").valid();
			});
		});
		var data = {};
		var modelView = new WizardViewModel({
			logo: ${logo}
		});
		ko.applyBindings(modelView);
		var autocompleteDireccionesEmisor = {
			minLength : 3,
			source : function(request, response) {
				$
						.ajax({
							url : String
									.format(
											"{0}/app/direcciones/municipios/{1}/colonias",
											contextPath,
											modelView.emisorViewModel.direccion.municipio
													.id()),
							dataType : "json",
							data : {
								nombre : $("#txt-colonia-emisor").val()
							},
							success : function(data) {
								response($.map(data, function(item) {
									return {
										label : item.nombre,
										value : item.id,
										codigoPostal : item.codigoPostal
									};
								}));
							}
						});
			},
			select : function(event, ui) {
				console.log(ui.item);
				modelView.emisorViewModel.direccion.colonia.id(ui.item.value);
				modelView.emisorViewModel.direccion.colonia
						.nombre(ui.item.label);
				modelView.emisorViewModel.direccion.colonia
						.codigoPostal(ui.item.codigoPostal);
				return false;
			},
			focus : function(event, ui) {
				modelView.emisorViewModel.direccion.colonia
						.nombre(ui.item.label);
				modelView.emisorViewModel.direccion.colonia
						.codigoPostal(ui.item.codigoPostal);
				return false;
			},
			change : function(event, ui) {
				if (!ui.item) {
					modelView.emisorViewModel.direccion.colonia.limpiar();
				} else {
					modelView.emisorViewModel.direccion.colonia
							.id(ui.item.value);
					modelView.emisorViewModel.direccion.colonia
							.nombre(ui.item.label);
					modelView.emisorViewModel.direccion.colonia
							.codigoPostal(ui.item.codigoPostal);
				}
				return false;
			}
		};
		var autocompleteDireccionesCliente = {
			minLength : 3,
			source : function(request, response) {
				$
						.ajax({
							url : String
									.format(
											"{0}/app/direcciones/municipios/{1}/colonias",
											contextPath,
											modelView.clienteViewModel.direccion.municipio
													.id()),
							dataType : "json",
							data : {
								nombre : $("#txt-colonia-cliente").val()
							},
							success : function(data) {
								response($.map(data, function(item) {
									return {
										label : item.nombre,
										value : item.id,
										codigoPostal : item.codigoPostal
									};
								}));
							}
						});
			},
			select : function(event, ui) {
				console.log(ui.item);
				modelView.clienteViewModel.direccion.colonia.id(ui.item.value);
				modelView.clienteViewModel.direccion.colonia
						.nombre(ui.item.label);
				modelView.clienteViewModel.direccion.colonia
						.codigoPostal(ui.item.codigoPostal);
				return false;
			},
			focus : function(event, ui) {
				modelView.clienteViewModel.direccion.colonia
						.nombre(ui.item.label);
				modelView.clienteViewModel.direccion.colonia
						.codigoPostal(ui.item.codigoPostal);
				return false;
			},
			change : function(event, ui) {
				if (!ui.item) {
					modelView.clienteViewModel.direccion.colonia.limpiar();
				} else {
					modelView.clienteViewModel.direccion.colonia
							.id(ui.item.value);
					modelView.clienteViewModel.direccion.colonia
							.nombre(ui.item.label);
					modelView.clienteViewModel.direccion.colonia
							.codigoPostal(ui.item.codigoPostal);
				}
				return false;
			}
		};
		$("#txt-colonia-emisor").autocomplete(autocompleteDireccionesEmisor);
		$("#txt-colonia-cliente").autocomplete(autocompleteDireccionesCliente);
	});
</script>

<script type="text/javascript">
	$(function() {
		var response = '${type}';
		if (response == 'true') {
			toastr.success("${message}", "!Éxito¡");
		} else if (response == 'false') {
			toastr.error("${message}", "¡Error!");
		}
	});
</script>

<script type="text/javascript">
	// DO NOT REMOVE : GLOBAL FUNCTIONS!
	$(document).ready(
			function() {
				$('#bootstrap-wizard-1').bootstrapWizard(
						{
							'tabClass' : 'form-wizard',
							'onNext' : function(tab, navigation, index) {
								var $valid = $("#wizard-1").valid();
								if (!$valid) {
									$validator.focusInvalid();
									return false;
								} else {
									$('#bootstrap-wizard-1').find(
											'.form-wizard').children('li').eq(
											index - 1).addClass('complete');
									$('#bootstrap-wizard-1').find(
											'.form-wizard').children('li').eq(
											index - 1).find('.step').html(
											'<i class="fa fa-check"></i>');
								}
							}
						});
			})
</script>

</head>
<body>
	<div class="panel-body">
		<div class="col-lg-3 col-md-6">
			<a href="#tab1" data-toggle="tab">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-user fa-5x"></i>
							</div>
							<div class="col-xs-3" data-bind="visible: checkedEmisor()">
								<button type="button" class="btn btn-success btn-circle btn-xl">
									<i class="fa fa-check"></i>
								</button>
							</div>
							<div class="col-xs-6 text-right">
								<div class="huge">1</div>
								<div>
									<span class="title">Emisores</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</a>
		</div>
		<div class="col-lg-3 col-md-6">
			<a href="#tab2" data-toggle="tab">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-group fa-5x"></i>
							</div>
							<div class="col-xs-3" data-bind="visible: checkedCliente()">
								<button type="button" class="btn btn-success btn-circle btn-xl">
									<i class="fa fa-check"></i>
								</button>
							</div>
							<div class="col-xs-6 text-right">
								<div class="huge">2</div>
								<div>
									<span class="title">Clientes</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</a>
		</div>
		<div class="col-lg-3 col-md-6">
			<a href="#tab3" data-toggle="tab">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-shopping-cart fa-5x"></i>
							</div>
							<div class="col-xs-3" data-bind="visible: checkedProducto()">
								<button type="button" class="btn btn-success btn-circle btn-xl">
									<i class="fa fa-check"></i>
								</button>
							</div>
							<div class="col-xs-6 text-right">
								<div class="huge">3</div>
								<div>
									<span class="title">Productos</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</a>
		</div>
		<div class="col-lg-3 col-md-6">
			<a href="#tab4" data-toggle="tab">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-file fa-5x"></i>
							</div>
							<div class="col-xs-3" data-bind="visible: checkedCertificado()">
								<button type="button" class="btn btn-success btn-circle btn-xl">
									<i class="fa fa-check"></i>
								</button>
							</div>
							<div class="col-xs-6 text-right">
								<div class="huge">4</div>
								<div>
									<span class="title">Certificados</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</a>
		</div>
	</div>
	<div class="panel-body">
		<div class="row">
			<!-- 		<form id="wizard-1" novalidate="novalidate"> -->
			<div id="bootstrap-wizard-1" class="col-sm-12">
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<div class="row">
							<div class="col-lg-11">
								<br>
								<h3 class="page-header">Lista de Emisores Registrados</h3>
							</div>
							<!-- 							<div class="col-lg-1 bottom"> -->
							<!-- 								<button type="button" id="btn-add-regimen" class="btn btn-info" -->
							<!-- 									data-bind="click: visibleTrueEmisor">Agregar</button> -->
							<!-- 							</div> -->
							<div style="margin-bottom: 90px">
								<button type="button"
									<%--id="btn-add-regimen"--%>id="agregarEmi"
									class="btn btn-info left agregarEmi"
									data-bind="click: visibleTrueEmisor">Agregar</button>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-primary">
									<div class="panel-heading">Emisores</div>
									<div class="panel-body">
										<div class="dataTable_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="table-list-emisor">
												<thead>
													<tr>
														<th class="text-center">Nombre</th>
														<th class="text-center">RFC</th>
													</tr>
												</thead>
												<tbody data-bind="foreach: emisores">
													<tr>
														<td data-bind="text: nombreCompleto"></td>
														<td data-bind="text: rfc"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div data-bind="visible: visibleFormEmisor()" class="panel-body">
							<div class="row">
								<div class="col-lg-12 col-xs-12">
									<h3 class="page-header">Nuevo Emisor</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12 col-xs-12">
									<div class="panel panel-primary">
										<div class="panel-heading">Datos del Emisor</div>
										<div class="panel-body">
											<jsp:include page="emisor.jsp">
												<jsp:param value="alta" name="action" />
											</jsp:include>
										</div>
										<div class="panel-footer text-right">
											<button type="button" id="btn-modal-cto-close"
												class="btn btn-default"
												data-bind="click: visibleFalseEmisor">Cerrar</button>
											<button type="button" id="btn-guardar-emisor"
												class="btn btn-info" data-bind="click: guardarEmisor">Guardar</button>
										</div>
									</div>
									<jsp:include page="modales/direccion_emisor.jsp" />
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tab2">
						<div class="row">
							<div class="col-lg-11">
								<br>
								<h3 class="page-header">Lista de Clientes Registrados</h3>
							</div>
							<!-- 							<div class="col-lg-1 bottom"> -->
							<!-- 								<button type="button" id="btn-add-regimen" class="btn btn-info" -->
							<!-- 									data-bind="click: visibleTrueCliente">Agregar</button> -->
							<!-- 							</div> -->
							<div style="margin-bottom: 90px">
								<button type="button"
									<%--id="btn-add-regimen"--%>id="agregarEmi"
									class="btn btn-info left agregarEmi"
									data-bind="click: visibleTrueCliente">Agregar</button>
							</div>
							<!-- 							<div> -->
							<!-- 								<button type="button" id="btn-add-regimen" -->
							<!-- 									class="btn btn-info left" data-bind="click: visibleTrueCliente">Agregar</button> -->
							<!-- 							</div> -->
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-primary">
									<div class="panel-heading">Clientes</div>
									<div class="panel-body">
										<div class="dataTable_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="table-list-emisor">
												<thead>
													<tr>
														<th class="text-center">Nombre Completo</th>
														<th class="text-center">Emisor</th>
													</tr>
												</thead>
												<tbody data-bind="foreach: clientes">
													<tr>
														<td data-bind="text: nombreCompleto"></td>
														<td data-bind="text: emisor.nombreCompleto"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div data-bind="visible: visibleFormCliente()" class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<h3 class="page-header">Nuevo Cliente</h3>
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
											<button type="button" id="btn-modal-cto-close"
												class="btn btn-default"
												data-bind="click: visibleFalseCliente">Cerrar</button>
											<button type="button" id="btn-guardar" class="btn btn-info"
												data-bind="click: guardarCliente">Guardar</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<jsp:include page="modales/direccion_cliente.jsp">
							<jsp:param value="cliente" name="tipo" />
						</jsp:include>
						<jsp:include page="modales/contacto_cliente.jsp"></jsp:include>
					</div>
					<div class="tab-pane" id="tab3">
						<div class="row">
							<div class="col-lg-11">
								<br>
								<h3 class="page-header">Lista de productos registrados</h3>
							</div>
							<!-- 							<div class="col-lg-1 bottom"> -->
							<!-- 								<button type="button" id="btn-add-regimen" class="btn btn-info" -->
							<!-- 									data-bind="click: visibleTrueProducto">Agregar</button> -->
							<!-- 							</div> -->
							<div>
								<button type="button" id="btn-add-regimen"
									class="btn btn-info left agregarEmi"
									data-bind="click: visibleTrueProducto">Agregar</button>
							</div>
							<br>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-primary">
									<div class="panel-heading">Productos</div>
									<div class="panel-body">
										<div class="dataTable_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="table-list-emisor">
												<thead>
													<tr>
														<th class="text-center">Codigo</th>
														<th class="text-center">Nombre</th>
														<th class="text-center">Emisor</th>
													</tr>
												</thead>
												<tbody data-bind="foreach: productos">
													<tr>
														<td data-bind="text: codigo"></td>
														<td data-bind="text: nombre"></td>
														<td data-bind="text: emisor.nombreCompleto"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div data-bind="visible: visibleFormProducto()" class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<h3 class="page-header">Nuevo Producto</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12">
									<div class="panel panel-primary">
										<div class="panel-heading">Datos del Producto</div>
										<div class="panel-body">
											<jsp:include page="producto.jsp" />
										</div>
										<div class="panel-footer text-right">
											<button type="button" id="btn-modal-cto-close"
												class="btn btn-default"
												data-bind="click: visibleFalseProducto">Cerrar</button>
											<button type="button" id="btn-prod-new" class="btn btn-info"
												data-bind="click: guardarProducto">Guardar</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tab4">
						<div class="row">
							<div class="col-lg-11">
								<br>
								<h3 class="page-header">Lista de certificados registrados</h3>
							</div>
							<!-- 							<div class="col-lg-1 bottom"> -->
							<!-- 								<button type="button" id="btn-add-regimen" class="btn btn-info" -->
							<!-- 									data-bind="click: visibleTrueCertificado">Agregar</button> -->
							<!-- 							</div> -->
							<!-- 							<div> -->
							<!-- 								<button type="button" id="btn-add-regimen" -->
							<!-- 									class="btn btn-info left" -->
							<!-- 									data-bind="click: visibleTrueCertificado">Agregar</button> -->
							<!-- 							</div> -->
							<div style="margin-bottom: 90px">
								<button type="button"
									<%--id="btn-add-regimen"--%>id="agregarEmi"
									class="btn btn-info left agregarEmi"
									data-bind="click: visibleTrueCertificado">Agregar</button>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-primary">
									<div class="panel-heading">Certificados</div>
									<div class="panel-body">
										<div class="dataTable_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="table-list-emisor">
												<thead>
													<tr>
														<th class="text-center">Nombre</th>
														<th class="text-center">Emisor</th>
													</tr>
												</thead>
												<tbody data-bind="foreach: certificados">
													<tr>
														<td data-bind="text: nombre"></td>
														<td data-bind="text: emisor.nombreCompleto"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div data-bind="visible: visibleFormCertificado()"
							class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<h3 class="page-header">Nuevo Certificado de Sello Digital</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12">
									<div class="panel panel-primary">
										<div class="panel-heading">Datos del CSD</div>
										<div class="panel-body">
											<jsp:include page="certificado.jsp" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>