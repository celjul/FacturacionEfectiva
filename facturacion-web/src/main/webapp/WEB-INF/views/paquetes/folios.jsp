<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />
<html>
<head>
<title>Folios</title>
<script src="${js}/app/commons/catalogos.js?v=${project.version}"></script>
<script
	src="${js}/app/paquetes/emisores-facturas.js?v=${project.version}"></script>
<script
	src="${js}/app/paquetes/emisores-factura.app.js?v=${project.version}"></script>
<script src="${js}/app/paquetes/paquete-usuario.js?v=${project.version}"></script>
<script src="${js}/app/emisores/emisores.js?version=${project.version}"></script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Lista de Timbres Asignables por Emisor</h3>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">Paquete Contratado</div>
					<div class="panel-body">
						<form role="form-folios" id="form-list" method="get"
							novalidate="novalidate">
							<fieldset></fieldset>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="cboTipoDocumento">Nombre del Paquete: </label> <input
											id="text-nombre-paquete" type="text" class="form-control"
											placeholder="Tipo de paquete" readonly=true
											data-bind="value: paquete.paquete.nombrePaquete" /> <span
											class="input-group-btn"></span>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="cboTipoDocumento">Facturas por Paquete: </label> <input
											id="text-numero-facturas" type="text" class="form-control"
											readonly=true data-bind="value: paquete.paquete.facturas" />
										<span class="input-group-btn"></span>
									</div>
								</div>
								<div class="col-md-6">
									<div class=" form-group">
										<label>Fecha de Adquisición:</label> <input
											id="text-fecha-inicio" type="text" name="fi"
											class="form-control" placeholder="Inicio" readonly=true
											data-bind="value: paquete.adquirido" /> <span
											class="input-group-btn"></span>
									</div>
								</div>
								<div class="col-md-6">
									<div class=" form-group">
										<label for="text-fecha-fin">Fecha de Vencimiento:</label> <input
											id="text-fecha-fin" type="text" class="form-control"
											readonly=true data-bind="value: paquete.vencimiento" /> <span
											class="input-group-btn"></span>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">Facturas</div>
					<div class="panel-body">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover"
								id="table-list-emisor">
								<thead>
									<tr>
										<th class="text-center">Nombre del Emisor</th>
										<th class="text-center">RFC</th>
										<th class="text-center">Número de Timbres Asignados</th>
										<%--<th class="text-center">Asignar Timbres</th>--%>
									</tr>
								</thead>
								<tbody data-bind="foreach: emisores">
									<tr>
										<td data-bind="text: nombreCompleto"></td>
										<td data-bind="text: rfc"></td>
										<%--<td> <input	id="text-facturas" type="text" class="form-control"
											readonly=true data-bind="value: emisor.colorPlantilla" /></td>--%>
										<td class="text-center"><input id="text-nfacturas"
											type="text" name="text-nfacturas" class="form-control"
											placeholder="Timbres ilimitados."
											number="number" data-bind="value: facturas" /></td>
									</tr>
								</tbody>
							</table>
							<button type="button" id="btn-guardar" class="btn btn-info"
								data-bind="click: $root.guardar">Guardar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>