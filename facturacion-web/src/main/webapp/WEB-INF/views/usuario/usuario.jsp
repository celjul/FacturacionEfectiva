<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<script>
function validarNumero(e) {
var key;
if(window.event) 
	{
	key = e.keyCode;
	}
else if(e.which) 
	{
	key = e.which;
	}

if (key < 48 || key > 57)
    {
    if(key == 46 || key == 8) 
        { return true; }
    else 
        { return false; }
    }
return true;
}
</script>
<form id="form-usuario" role="form" novalidate="novalidate">
	<fieldset>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txtNombre" class="required">Nombre:</label> <input
						id="txtNombre" name="txtNombre" type="text"
						placeholder="Nombre(s)" maxlength="255" required="required"
						class="form-control" data-bind="value: usuario.nombre" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txtApellidoPaterno" class="required">Apellido
						Paterno: </label> <input id="txtApellidoPaterno" name="txtApellidoPaterno"
						type="text" placeholder="Apellido Paterno" class="form-control"
						maxlength="255" required="required"
						data-bind="value: usuario.apellidoPaterno" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txtApellidoMaterno">Apellido Materno: </label> <input
						id="txtApellidoMaterno" name="txtApellidoMaterno" type="text"
						placeholder="Apellido Materno" maxlength="255"
						class="form-control" data-bind="value: usuario.apellidoMaterno" />
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<label for="txtConfirmPassword" class="required">Teléfono:</label>
					<input id="txtTelefono" name="txtTelefono" type="text"
						onkeypress="javascript:return validarNumero(event)"
						placeholder="Teléfono" class="form-control" required="required"
						maxlength="20" data-bind="value: usuario.telefono" />
				</div>
			</div>

			<!-- 			<div class="col-md-4"> -->
			<!-- 				<div class="form-group"> -->
			<!-- 					<label>Extensión:</label> <input id="txtExtension" -->
			<!-- 						name="txtExtension" type="text" -->
			<!-- 						onkeypress="javascript:return validarNumero(event)" -->
			<!-- 						placeholder="Extensión" class="form-control" maxlength="5" -->
			<!-- 						data-bind="value: usuario.extension" /> -->
			<!-- 				</div> -->
			<!-- 			</div> -->
		</div>

		<sec:authorize access="hasAnyRole('Webmaster','Propietario')">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
						<label for="cbo-rol" class="required">Perfil:</label> <select
							class="form-control" name="perfil" id="perfil"
							required="required" multiple="multiple"
							data-bind="options: $root.roles, 
                                        optionsCaption : '- Seleccione una opción -', 
                                        selectedOptions: usuario.roles, 
                                        optionsText: 'nombre', 
                                        optionsValue:'id'">
						</select>
					</div>
				</div>
				<div class="col-md-5">
					<div class="form-group">
						<label for="cbo-rol" class="required">Emisores:</label> <select
							class="form-control" name="emisor" id="emisor"
							required="required" multiple="multiple"
							data-bind="options: $root.emisores, 
                                        optionsCaption : '- Seleccione una opción -', 
                                        selectedOptions: usuario.emisores, 
                                        optionsText: 'nombreCompleto', 
                                        optionsValue:'id'">
						</select>
					</div>
				</div>
			</div>
		</sec:authorize>

		<c:if test="${empty param.tipo}">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label for="txtLogin" class="required">Correo:</label> <input
							id="txtLogin" name="txtLogin" type="email"
							placeholder="nombre@dominio.com" maxlength="128" minlenght="8"
							required="required" class="form-control"
							data-bind="value: usuario.login" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label for="txtPassword" class="required">Password:</label> <input
							id="txtPassword" name="txtPassword" type="password"
							placeholder="Password" maxlength="20" minlenght="8"
							required="required" class="form-control"
							data-bind="value: usuario.password" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label for="txtConfirmPassword" class="required">Confirmar
							Password:</label> <input id="txtConfirmPassword"
							name="txtConfirmPassword" type="password" class="form-control"
							maxlength="20" placeholder="Confirmar Password"
							required="required" equalTo="#txtPassword" />
					</div>
				</div>
			</div>
		</c:if>

		<c:if test="${not empty param.tipo}">
			<div class="row">
				<div class="col-md-4 col-md-offset-4 text-center">
					<button id="btn-cambio-pass" type="button" class="btn btn-info"
						data-bind="click: cambiarPassword">Cambiar Password</button>
				</div>
			</div>
			<div class="row" data-bind="visible: cambio">
				<div class="col-md-4">
					<div class="form-group">
						<label for="txtLogin" class="required">Password Anterior:</label>
						<input id="txtLogin" name="txtLogin" type="text"
							placeholder="Password Anterior" maxlength="20" minlenght="8"
							required="required" class="form-control"
							data-bind="value: usuario.login" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label for="txtPassword" class="required">Nuevo Password:</label>
						<input id="txtPassword" name="txtPassword" type="password"
							placeholder="Password" maxlength="20" minlenght="8"
							required="required" class="form-control"
							data-bind="value: usuario.password" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label for="txtConfirmPassword" class="required">Confirmar
							Password:</label> <input id="txtConfirmPassword"
							name="txtConfirmPassword" type="password" class="form-control"
							maxlength="20" placeholder="Confirmar Password"
							required="required" equalTo="#txtPassword" />
					</div>
				</div>
			</div>
		</c:if>
		</fielset>
</form>