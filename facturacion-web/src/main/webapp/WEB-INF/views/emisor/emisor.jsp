<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rp" value="${contextPath}/resources/img/plantillas" />

<c:if test="${param.action eq 'editar'}">
	<c:set var="disabled" value="disabled='disabled'" />
</c:if>

<script type="text/javascript">
	function PreviewImage() {
		var oFReader = new FileReader();
		oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);

		oFReader.onload = function(oFREvent) {
			document.getElementById("uploadPreview").src = oFREvent.target.result;
		};

		var file = document.getElementById("uploadImage").files[0];
		var fd = new FormData();
		var xhr = new XMLHttpRequest();
		fd.append("file", file);
		xhr.open("POST", String.format("{0}/app/emisores/subirimagen",
				contextPath), true);
		/*xhr.onreadystatechange = function(){
			if(xhr.readyState==){
				alert("Mensaje");
			}
		}*/
		xhr.send(fd);

	};
</script>
<form id="form-emisor" role="form" novalidate="novalidate">
	<fieldset>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<div>
						<label class="required">Tipo de Emisor:</label>
					</div>
					<label class="radio-inline"> <input type="radio"
						name="tipo-persona" id="tipo-persona-moral"
						data-bind="checked: emisor.tipo, event: {change: clear}"
						value="moral" required="required">Moral
					</label> <label class="radio-inline"> <input type="radio"
						name="tipo-persona" id="tipo-persona-fisica"
						data-bind="checked: emisor.tipo, event: {change: clear}"
						value="fisica">Física
					</label> <label for="tipo-persona" class="error"></label>
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<label class="required">R. F. C.:</label> <input id="text-rfc"
						type="text" name="text-rfc" class="form-control"
						placeholder="R. F. C." minlength="12" maxlength="13" rfc="rfc"
						required="required" data-bind="value: emisor.rfc" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="cbo-promotor">Régimen:</label> <select id="cbo-regimen"
						name="cbo-regimen" class="form-control"
						data-bind="options: $root.regimenes,
								optionsCaption : '- Seleccionar -',
                                value: emisor.regimen.id,
                                optionsText: 'descripcion',
                                optionsValue:'id', event:{ change: $root.changeStatus}">
					</select>
				</div>
			</div>
			<%--<div class="col-md-4">
					<div class="form-group">
						<label for="text-nfacturas">Número Máximo de Folios por Periodo</label> <input
							id="text-nfacturas" type="text" name="text-nfacturas"
							class="form-control"
							placeholder="Facturas asignadasa a este emisor." number="number"
							data-bind="value: emisor.facturas" />
					</div>
				</div>--%>
		</div>

		<div class="row" data-bind="visible: visibleMoral">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-razon-social" class="required">Razón
						Social: </label> <input id="txt-razon-social" name="txt-razon-social"
						type="text" placeholder="Razón Social" class="form-control"
						required="required" data-bind="value: emisor.razonSocial" />
				</div>
			</div>
		</div>

		<div class="row" data-bind="visible: !visibleMoral() && emisor.tipo">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-nombre" class="required">Nombre: </label> <input
						id="txt-nombre" name="txt-nombre" type="text" placeholder="Nombre"
						required="required" class="form-control"
						data-bind="value: emisor.nombre" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-apellido-paterno" class="required">Apellido
						Paterno: </label> <input id="txt-apellido-paterno"
						name="txt-apellido-paterno" type="text"
						placeholder="Apellido Paterno" class="form-control"
						required="required" data-bind="value: emisor.apellidoPaterno" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-apellido-materno">Apellido Materno: </label> <input
						id="txt-apellido-materno" name="txt-apellido-materno" type="text"
						placeholder="Apellido Materno" class="form-control"
						data-bind="value: emisor.apellidoMaterno" />
				</div>
			</div>
		</div>

		<div class="row">
			<div class="panel-body">
				<h4 class="page-header">Plantilla</h4>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th style="width: 75%">Selecciona un color para la
									plantilla:</th>
								<th style="width: 25%"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input name="color" type="color" value="#ffffff"
									data-bind="value: emisor.colorPlantilla" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-md-12">
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th style="width: 75%">Selecciona el logo para la
									plantilla:</th>
								<th style="width: 25%"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><img id="uploadPreview" height="240" width="240"
									data-bind="attr: {src: logo}"></td>

							</tr>
							<tr>
								<td><input id="uploadImage" type="file" name="logo"
									accept="image/*" onchange="PreviewImage();" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th style="width: 75%">Selecciona el tipo de plantilla:</th>
								<th style="width: 25%"></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>

		<img src="${rp}/Plantilla0.jpg"
			alt="Plantilla 0" height="360" width="240"> 
		<img src="${rp}/Plantilla1.jpg"
			alt="Plantilla 1" height="360" width="240"> 
		<img src="${rp}/Plantilla2.jpg"
			alt="Plantilla 2" height="360" width="240"> 
		<img src="${rp}/Plantilla3.jpg"
			alt="Plantilla 3" height="360" width="240">
		<img src="${rp}/Default.jpg"
			 alt="Plantilla Personalizada" height="360" width="240">

		<div class="row">
			<div class="col-md-12">
				<div class="table-responsive">
					<table class="table">
						<tbody>
							<tr>
								<td><input type="radio" name="plantilla" value="p0"
									data-bind="checked: emisor.plantilla, event: {change: clear}">
									Plantilla 0</td>
								<td><input type="radio" name="plantilla" value="p1"
									data-bind="checked: emisor.plantilla, event: {change: clear}">
									Plantilla 1</td>
								<td><input type="radio" name="plantilla" value="p2"
									data-bind="checked: emisor.plantilla, event: {change: clear}">
									Plantilla 2</td>
								<td><input type="radio" name="plantilla" value="p3"
									data-bind="checked: emisor.plantilla, event: {change: clear}">
									Plantilla 3</td>
								<td><input type="radio" name="plantilla" value="pp"
										   data-bind="checked: emisor.plantilla, event: {change: clear}">
									Plantilla Personalizada</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</fieldset>
</form>
<%--

			<div class="col-md-4">
				<div class="form-group">
				</div>
			</div>

	<p>
		<label for="rdioTipo"><span class="required">* </span>Tipo:</label> <br />
		<span id="spnType">
			<input type="radio" value="Fisica" data-bind="checked: fisica" id="rdioFisica" name="rdioTipo" ${disabled } />Física
			<span>&nbsp;</span> 
			<input type="radio" value="Moral" data-bind="checked: fisica" id="rdioMoral" name="rdioTipo" ${disabled }>Moral
		</span>
	</p>
	<p>
		<label for="txtRfc"><span class="required">* </span>RFC: </label><input
			id="txtRfc" type="text" placeholder="RFC"
			data-validation-engine="validate[required, minSize[12], maxSize[13], custom[rfc]]"
			data-bind="value: emisor.rfc" />
	</p>


	<div id="divEmisorMoral" data-bind="visible: visibleMoral">
		<p>
			<label for="txt-razon-social"><span class="required">*
			</span>Razón Social: </label><input id="txt-razon-social" type="text"
				placeholder="Razón Social"
				data-validation-engine="validate[required, maxSize[128]]"
				data-bind="value: emisor.razonSocial" />
		</p>
	</div>
	<div id="divEmisorFisica" data-bind="visible: visibleFisica">
		<p>
			<label for="txt-nombre"><span class="required">* </span>Nombre:
			</label><input id="txt-nombre" type="text" placeholder="Nombre"
				data-validation-engine="validate[required, maxSize[64]]"
				data-bind="value: emisor.nombre" />
		</p>
		<p>
			<label for="txtApellidoPaterno"><span class="required">*
			</span>Apellido Paterno: </label><input id="txtApellidoPaterno" type="text"
				placeholder="Apellido Paterno"
				data-validation-engine="validate[required, maxSize[64]]"
				data-bind="value: emisor.apellidoPaterno" />
		</p>
		<p>
			<label for="txtApellidoMaterno">Apellido Materno: </label><input
				id="txtApellidoMaterno" type="text" placeholder="Apellido Materno"
				data-validation-engine="validate[required, maxSize[64]]"
				data-bind="value: emisor.apellidoMaterno" />
		</p>
	</div>

	<hr />

	<div class="trescomlumnas">
		<h2>Regímenes</h2>
		<p>
			<label for="txtRegimen"><span class="required">* </span>Régimen:
			</label><input id="txtRegimen" type="text" placeholder="Régimen" data-bind="value: regimen" /> <br />
			<label id="btnAgregarRegimen" class="boton"
				data-bind="click: agregarRegimen">Agregar Régimen</label>
		</p>
		<div id="divRegimen">
			<div id="divRegimenes">
				<table id="tblRegimenes">
					<thead>
						<tr>
							<th style="width: 75%">Régimen</th>
							<th style="width: 25%"></th>
						</tr>
					</thead>
					<tbody data-bind="foreach: emisor.regimenes">
						<tr class="contenido">
							<td><span data-bind="text: $data"></span></td>
							<td><label id="btnEliminarRegimen" class="boton"
								data-bind="click: $root.eliminarRegimen">Eliminar</label></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<hr />

	<h2>Direcciones</h2>
	<jsp:include page="../direccion/direccion.jsp"></jsp:include>

	<div id="divDirecciones">
		<table id="tblDirecciones">
			<thead>
				<tr>
					<th style="width: 20%">Calle.</th>
					<th style="width: 10%">No. Ext.</th>
					<th style="width: 10%">No. Int.</th>
					<th style="width: 20%">Colonia</th>
					<th style="width: 10%">C.P.</th>
					<th style="width: 20%">Referencias</th>
					<th style="width: 10%"></th>
				</tr>
			</thead>
			<tbody data-bind="foreach: emisor.direcciones">
				<tr class="contenido">
					<td><span data-bind="text: calle"></span></td>
					<td style="text-align: center;"><span
						data-bind="text: noExterior"></span></td>
					<td style="text-align: center;"><span
						data-bind="text: noInterior"></span></td>
					<td><span data-bind="text: colonia.nombre"></span></td>
					<td style="text-align: center;"><span
						data-bind="text: colonia.codigoPostal"></span></td>
					<td><span data-bind="text: referencias"></span></td>
					<td><label id="btnEliminarDireccion" class="boton"
						data-bind="click: $root.eliminarDireccion">Eliminar</label></td>
				</tr>
			</tbody>
		</table>
	</div>
	<hr />

	<c:choose>
		<c:when test="${param.action eq 'alta'}">
			<button type="button" class="right-align" id="btnNewEmisor" data-bind="click: guardar">
				Crear Emisor
			</button>
		</c:when>
		<c:when test="${param.action eq 'editar'}">
			<button type="button" class="right-align" id="btnEditarEmisor" data-bind="click: actualizar">
				Actualizar Emisor
			</button>
		</c:when>
	</c:choose>
</form>
--%>