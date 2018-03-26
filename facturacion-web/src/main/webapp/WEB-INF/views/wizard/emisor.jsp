<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rp" value="${contextPath}/resources/img/plantillas" />

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
						data-bind="checked: emisorViewModel.emisor.tipo, event: {change: emisorViewModel.clear}"
						value="moral" required="required">Moral
					</label> <label class="radio-inline"> <input type="radio"
						name="tipo-persona" id="tipo-persona-fisica"
						data-bind="checked: emisorViewModel.emisor.tipo, event: {change: emisorViewModel.clear}"
						value="fisica">Física
					</label> <label for="tipo-persona" class="error"></label>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="required">R. F. C.:</label> <input id="text-rfc"
						type="text" name="text-rfc" class="form-control"
						placeholder="R. F. C." minlength="12" maxlength="13" rfc="rfc"
						required="required" data-bind="value: emisorViewModel.emisor.rfc" />
				</div>
			</div>
			<!-- 			<div class="col-md-4"> -->
			<!-- 				<div class="form-group"> -->
			<!-- 					<label for="txt-numero-facturas">Numero de facturas</label> <input -->
			<!-- 						id="text-nfacturas" type="text" name="text-nfacturas" -->
			<!-- 						class="form-control" -->
			<!-- 						placeholder="Facturas asignadasa a este emisor." number="number" -->
			<!-- 						data-bind="value: emisorViewModel.emisor.facturas" /> -->
			<!-- 				</div> -->
			<!-- 			</div> -->
		</div>

		<div class="row" data-bind="visible: emisorViewModel.visibleMoral">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-razon-social" class="required">Razón
						Social: </label> <input id="txt-razon-social" name="txt-razon-social"
						type="text" placeholder="Razón Social" class="form-control"
						required="required"
						data-bind="value: emisorViewModel.emisor.razonSocial" />
				</div>
			</div>
		</div>

		<div class="row"
			data-bind="visible: !emisorViewModel.visibleMoral() && emisorViewModel.emisor.tipo">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-nombre" class="required">Nombre: </label> <input
						id="txt-nombre" name="txt-nombre" type="text" placeholder="Nombre"
						required="required" class="form-control"
						data-bind="value: emisorViewModel.emisor.nombre" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-apellido-paterno" class="required">Apellido
						Paterno: </label> <input id="txt-apellido-paterno"
						name="txt-apellido-paterno" type="text"
						placeholder="Apellido Paterno" class="form-control"
						required="required"
						data-bind="value: emisorViewModel.emisor.apellidoPaterno" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-apellido-materno">Apellido Materno: </label> <input
						id="txt-apellido-materno" name="txt-apellido-materno" type="text"
						placeholder="Apellido Materno" class="form-control"
						data-bind="value: emisorViewModel.emisor.apellidoMaterno" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="panel-body">
				<h4 class="page-header">Regímenes</h4>
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="regimen">Régimen: </label> <input id="txt-regimen"
										type="text" name="txt-regimen" placeholder="Régimen"
										class="form-control"
										data-bind="value: emisorViewModel.regimen" />
									<div class="col-md-6 bottom " style="margin-top: 0px">
										<button type="button" id="btn-add-regimen"
											class="btn btn-info agregarEmil"
											data-bind="click: emisorViewModel.agregarRegimen">Agregar</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th style="width: 75%">Régimen</th>
										<th style="width: 25%"></th>
									</tr>
								</thead>
								<tbody data-bind="foreach: emisorViewModel.emisor.regimenes">
									<tr>
										<td><span data-bind="text: $data"></span></td>
										<td>
											<button id="btnEliminarRegimen" class="btn btn-info"
												data-bind="click: $root.emisorViewModel.eliminarRegimen">Eliminar</button>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="panel-body col-xs-12">
				<h4 class="page-header">Direcciones</h4>

				<div class="row">
					<div class="col-md-3">
						<button type="button" id="btn-add-direccion"
							class="btn btn-info agregarEmil" data-toggle="modal"
							data-target="#modal-direccion-emisor">Agregar</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 ">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th>Calle</th>
										<th>No. Ext.</th>
										<th>No. Int.</th>
										<th>Colonia</th>
										<th>C. P.</th>
										<th>Rerefencias</th>
										<th>&nbsp;</th>
									</tr>
								</thead>
								<tbody data-bind="foreach: emisorViewModel.emisor.direcciones">
									<tr>
										<td data-bind="text: calle"></td>
										<td data-bind="text: noExterior"></td>
										<td data-bind="text: noInterior"></td>
										<td data-bind="text: nombreColonia"></td>
										<td data-bind="text: codigoPostal"></td>
										<td data-bind="text: referencias"></td>
										<td>
											<button type="button" class="btn btn-info"
												data-bind="click: $root.emisorViewModel.eliminarDireccion">Eliminar</button>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
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
									data-bind="value: emisorViewModel.emisor.colorPlantilla" /></td>
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
									data-bind="attr: {src: emisorViewModel.logo}"></td>
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
		<%-- *****************Código para las plantillas en dispositivos de escritorio***************** --%>
		<div style="width: 100%" class="scremplanti">
			<img src="${rp}/Plantilla0.jpg"
				alt="Plantilla 0" height="400" width="310"> <img
				src="${rp}/Plantilla1.jpg"
				alt="Plantilla 1" height="400" width="310"> <img
				src="${rp}/Plantilla2.jpg"
				alt="Plantilla 2" height="400" width="310"> <img
				src="${rp}/Plantilla3.jpg"
				alt="Plantilla 3" height="400" width="310"> <img
				src="${rp}/Default.jpg"
				alt="Plantilla Personalizada" height="400" width="310">
		</div>
		<div class="row radioplan">
			<div class="col-md-12">
				<div class="table-responsive">
					<table class="table">
						<tbody>
							<tr>
								<td><input type="radio" name="plantilla" value="p0"
									data-bind="checked: emisorViewModel.emisor.plantilla, event: {change: emisorViewModel.clear}">
									Plantilla 0</td>
								<td><input type="radio" name="plantilla" value="p1"
									data-bind="checked: emisorViewModel.emisor.plantilla, event: {change: emisorViewModel.clear}">
									Plantilla 1</td>
								<td><input type="radio" name="plantilla" value="p2"
									data-bind="checked: emisorViewModel.emisor.plantilla, event: {change: emisorViewModel.clear}">
									Plantilla 2</td>
								<td><input type="radio" name="plantilla" value="p3"
									data-bind="checked: emisorViewModel.emisor.plantilla, event: {change: emisorViewModel.clear}">
									Plantilla 3</td>
								<td><input type="radio" name="plantilla" value="pp"
										   data-bind="checked: emisorViewModel.emisor.plantilla, event: {change: emisorViewModel.clear}">
									Plantilla Personalizada</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<%-- *****************Código para las plantillas en dispositivos móviles***************** --%>
		<div style="width: 100%" class="movilplanti">
			<h1>Plantilla 0</h1>
			<img id="imga"
				src="/facturacion-web/resources/img/plantillas/Plantilla0.jpg"
				alt="Plantilla 0" height="400" width="310">
			<h1>Plantilla 1</h1>
			<img id="imgb"
				src="/facturacion-web/resources/img/plantillas/Plantilla1.jpg"
				alt="Plantilla 1" height="400" width="310">
			<h1>Plantilla 2</h1>
			<img id="imgc"
				src="/facturacion-web/resources/img/plantillas/Plantilla2.jpg"
				alt="Plantilla 2" height="400" width="310">
			<h1>Plantilla 3</h1>
			<img id="imgd"
				src="/facturacion-web/resources/img/plantillas/Plantilla3.jpg"
				alt="Plantilla 3" height="400" width="310">
			<h1>Plantilla 3</h1>
			<img id="imgf"
				 src="/facturacion-web/resources/img/plantillas/Default.jpg"
				 alt="Plantilla Personalizada" height="400" width="310">

		</div>
		<div class="row radioplantillas">
			<div style="width: 100%" class="scremplanti">
				<%--<h1 >Plantilla 0</h1>--%>

				<img src="${rp}/Plantilla0.jpg" alt="Plantilla 0" height="400"
					width="310">
				<img src="${rp}/Plantilla1.jpg"
					alt="Plantilla 0" height="400" width="310">
				<img src="${rp}/Plantilla2.jpg" alt="Plantilla 2" height="400"
					width="310">
				<img src="${rp}/Plantilla3.jpg"
					alt="Plantilla 3" height="400" width="310">
				<img src="${rp}/Default.jpg"
						 alt="Plantilla Personalizada" height="400" width="310">
			</div>
		</div>
	</fieldset>
</form>