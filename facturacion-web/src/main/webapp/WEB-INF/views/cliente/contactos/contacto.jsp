<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="panel-body">
	<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<label for="txt-nombre-contacto" class="required">Nombre: </label>
				<input type="text" id="txt-nombre-contacto" name="txt-nombre-contacto"
					placeholder="Nombre" class="form-control" required="required" maxlength="255"
					data-bind="value: contacto.nombre"/>
			</div> 
		</div>

		<div class="col-md-4">
			<div class="form-group">
				<label for="txt-ape-pat-contacto" class="required">Apellido Paterno: </label>
				<input type="text" id="txt-ape-pat-contacto" name="txt-ape-pat-contacto" 
					placeholder="Apellido Paterno" class="form-control" required="required" maxlength="255"
					data-bind="value: contacto.apellidoPaterno"/>
			</div>
		</div>

		<div class="col-md-4">
			<div class="form-group">
				<label for="txt-ape-mat-contacto">Apellido Materno: </label>
				<input type="text" id="txt-ape-mat-contacto" name="txt-ape-mat-contacto"
					placeholder="Apellido Materno" class="form-control" maxlength="255"
					data-bind="value: contacto.apellidoMaterno"/>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<label for="txt-puesto-contacto" class="required">Puesto: </label>
				<input type="text" id="txt-puesto-contacto" name="txt-puesto-contacto"
					placeholder="Puesto" class="form-control" required="required" maxlength="255"
					data-bind="value: contacto.puesto"/>
			</div>
		</div>

		<div class="col-md-4">
			<div class="form-group">
				<label for="txt-email-contacto" class="required">Email: </label>
				<input type="email" id="txt-email-contacto" name="txt-email-contacto"
					placeholder="Email" class="form-control" required="required" maxlength="255"
					data-bind="value: contacto.email"/>
			</div>
		</div>
	</div>
</div>