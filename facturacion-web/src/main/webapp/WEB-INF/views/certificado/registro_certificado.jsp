<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />
<c:set var="fecha" value="${rsrc}/img" />

<html>
<head>
<title>Registro de Certificado</title>
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

<script language="JavaScript" type="text/javascript">
	function new_window(url) {

		link = window
				.open(
						url,
						"Link",
						"toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,resizable=0,width=430,height=300,left=80,top=180");

	}
</script>

</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Nuevo Certificado de Sello Digital</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">Datos del CSD <img data-toggle="modal" class="ayuda" data-target="#myModalayuda" src="${rsrc}/img/icon/interrogacion.png" width="20px" height="20px" alt=""/></div>
					<div class="panel-body">
						<form id="form-vertificado" method="post"
							enctype="multipart/form-data"
							action="${contextPath}/app/certificados/create">
							<fieldset>
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="txt-nombre" class="required">Nombre para
												Identificar el CSD:</label> <input id="txt-nombreCSD"
												name="txt-nombreCSD" type="text" class="form-control"
												placeholder="Nombre para indentificar la pertenencia del CSD"
												maxlength="64" required="required" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label for="file-certificado" class="required">Certificado:</label>
											<input id="file-certificado" name="file-certificado"
												onclick="verificarCSD()" class="form-control" type="file"
												required="required" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label for="file-clave" class="required">Key:</label> <input
												id="file-clave" name="file-clave" type="file"
												required="required" class="form-control" />
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="txt-password" class="required">Password:</label>
											<input id="txt-password" name="txt-password" type="password"
												placeholder="Password" maxlength="32" class="form-control"
												required="required" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="txt-confirm-pass" class="required">Confirmar
												Password:</label> <input id="txt-confirm-pass" type="password"
												maxlength="32" placeholder="Confirmar Password"
												required="required" equalTo="#txt-password"
												class="form-control" />
										</div>
									</div>
								</div>
							</fieldset>
						</form>
						<div>
							<div class="form-group">
								<label>Nota:</label>
								<p>El Certificado de Sello Digital (CSD) es el archivo con
									la extensión .CER</p>
								<p>
									Este se genera a través del portal del SAT. Para mayor
									información, consulte la guía en el siguiente link: <a
										href="http://www.sat.gob.mx/informacion_fiscal/factura_electronica/Paginas/tramite_csd.aspx"
										target="_blank">www.sat.gob.mx</a>
								</p>
							</div>
						</div>
					</div>
					<div class="panel-footer text-right">
						<button type="submit" class="btn btn-info" id="btnNewCertificado"
							form="form-vertificado">Guardar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
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
					<label>Detalle de los campos son los siguientes:<br></label>

					<ul>
						<li class="txtayuda"> <strong>Nombre.</strong> Nombre con cual se identificara el certificado en el sistema “alias”. </li>
						<li class="txtayuda"> <strong> Certificado.</strong> Al dar clic en el botón “Seleccionar archivo” se mostrará el explorador de archivos donde el usuario podrá elegir el archivo con extensión “.cer”.  </li>
						<li class="txtayuda"> <strong>Key.</strong>  Al dar clic en el botón “Seleccionar archivo” se mostrará el explorador de archivos donde el usuario podrá elegir el archivo con extensión “.key”.  </li>
						<li class="txtayuda"> <strong> Password. </strong>   Es la contraseña del CSD (Certificado del Sello Digital).  </li>
						<li class="txtayuda"> <strong>  Confirmar Password. </strong>   Confirmar nuevamente la contraseña del CSD (Certificado del Sello Digital).</li>
						<li class="txtayuda">
							Una vez que el usuario haya capturado el formulario de nuevo certificado de sello digital con los datos y archivos correctos, deberá dar clic en el botón “Guardar”. El sistema guardará el nuevo certificado capturado y mostrará el siguiente mensaje de confirmación: “¡Éxito!, Se registró al cliente correctamente”.

						</li>



					</ul>


				</div>
				<div class="modal-footer">
				</div>
			</div>

		</div>
	</div>


</body>


</html>
