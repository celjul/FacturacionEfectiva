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
				<h3 class="page-header">Configuración de cuenta de E-mail</h3>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">Información de perfil</div>
					<div class="panel-body">
						<form role="form-folios" id="form-list" method="get"
							novalidate="novalidate">
							<fieldset></fieldset>
							<div class="row">

								<div class="col-md-9">
									<div class="form-group">
										<label for="txt-nombre" class="required">Su nombre: </label>
										<input id="txt-nombre" name="txt-nombre" type="text"
											   placeholder="Nombre completo" class="form-control"
											   required="required" maxlength="255"
											   data-bind="value: email.nombre" />
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="txt-direccion-correo" class="required">Direccción de correo electrónico: </label>
										<input id="txt-direccion-correo" name="txt-direccion-correo" type="text"
											   placeholder="Correo electrónico" class="form-control"
											   required="required" maxlength="255"
											   data-bind="value: email.direccionEmail" />
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="txt-password-correo" class="required">Contraseña de Email: </label>
										<input id="txt-password-correo" name="txt-password-correo" type="text"
											   placeholder="Contraseña de email" class="form-control"
											   required="required" maxlength="255"
											   data-bind="value: email.direccionEmail" />
									</div>
								</div>


							</div>
							<button type="button" id="btn-guardar" class="btn btn-info"
									data-bind="click: $root.guardar">Guardar</button>
						</form>
					</div>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>