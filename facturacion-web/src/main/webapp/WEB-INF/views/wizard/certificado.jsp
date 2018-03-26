<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script>
	function verificarCSD() {
		var x;
		if (confirm("Este archivo es generado a partir de la FIEL.\nPara mayor información, consulte la guía en el portal del SAT.\nhttp://www.sat.gob.mx/informacion_fiscal/factura_electronica/Paginas/tramite_csd.aspx") == true) {
			x = "Recuerda cargar el CSD!";
		} else {
			x = "Gracias!";
		}
	}
</script>
<form id="form-certificado" role="form" novalidate="novalidate">
	<fieldset>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="required">Emisor</label> <select class="form-control"
						name="emisor" id="emisor" required="required"
						data-bind="options: $root.emisorSelectViewModel.emisores, 
	                                                     value: $root.emisor, 
	                                                     optionsText: 'nombreCompleto', 
	                                                     optionsValue:'id',
	                                                     event: {change: $root.colocarEmisor}">
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-nombre" class="required">Nombre para
						Identificar el CSD:</label> <input id="txt-nombreCSD" name="txt-nombreCSD"
						type="text" class="form-control"
						placeholder="Nombre para indentificar la pertenencia del CSD"
						maxlength="64" required="required"
						data-bind="value: certificado.nombre" />
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<label for="file-certificado" class="required">Certificado:</label>
					<input id="fileuploadcer" name="file" class="form-control"
						onclick="verificarCSD()" type="file"
						data-url="${contextPath}/app/wizard/subir/certificado"
						data-bind="enable: $root.editableCer()" />
					<div id="progresscer" class="progress">
						<div class="progress-bar" role="progressbar" style="width: 0%;"></div>
					</div>
					<div>
						<table id="uploaded-files-cer" class="table">
							<tr>
								<th class="col-md-6">Archivo</th>
								<th class="col-md-3">Tamaño</th>
								<th class="col-md-3">Tipo</th>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center;"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<label for="file-clave" class="required">Clave:</label> <input
						id="fileuploadclave" name="file" class="form-control" type="file"
						data-url="${contextPath}/app/wizard/subir/clave"
						data-bind="enable: $root.editableClave()" />
					<div id="progressclave" class="progress">
						<div class="progress-bar" role="progressbar" style="width: 0%;"></div>
					</div>
					<div>
						<table id="uploaded-files-clave" class="table">
							<tr>
								<th class="col-md-6">Archivo</th>
								<th class="col-md-3">Tamaño</th>
								<th class="col-md-3">Tipo</th>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center;"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-password" class="required">Password:</label> <input
						id="txt-password" name="txt-password" type="password"
						placeholder="Password" maxlength="32" class="form-control"
						accept="application/cer" required="required"
						data-bind="value: certificado.password" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-confirm-pass" class="required">Confirmar
						Password:</label> <input id="txt-confirm-pass" type="password"
						maxlength="32" placeholder="Confirmar Password"
						required="required" equalTo="#txt-password" class="form-control" />
				</div>
			</div>
		</div>
		<div>
			<div class="form-group">
				<label>Nota:</label>
				<p>El Certificado de Sello Digital (CSD) es el archivo con la
					extensión .CER</p>
				<p>
					Este se genera a través del portal del SAT. Para mayor información,
					consulte la guía en el siguiente link: <a
						href="http://www.sat.gob.mx/informacion_fiscal/factura_electronica/Paginas/tramite_csd.aspx"
						target="_blank">www.sat.gob.mx</a>
				</p>
			</div>
		</div>
		<div class="panel-footer text-right">
			<button type="button" id="btn-modal-cto-close"
				class="btn btn-default" data-bind="click: visibleFalseCertificado">Cerrar</button>
			<button type="button" id="btn-guardar-certificado"
				class="btn btn-info" data-bind="click: guardarCertificado">Guardar</button>
		</div>
	</fieldset>
</form>