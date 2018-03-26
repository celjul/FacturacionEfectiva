<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form id="form-cliente" role="form" novalidate="novalidate">
	<fieldset>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<div>
						<label class="required">Tipo de Cliente:</label>
					</div>
					<label for="tipo-persona-moral" class="radio-inline">
						<input type="radio" name="tipo-persona" id="tipo-persona-moral" 
								data-bind="checked: cliente.tipo, event: {change: clear}" value="moral" required="required">Moral
					</label>
					<label for="tipo-persona-fisica" class="radio-inline">
						<input type="radio" name="tipo-persona" id="tipo-persona-fisica" 
								data-bind="checked: cliente.tipo, event: {change: clear}" value="fisica">Física
					</label>
					<label for="tipo-persona" class="error"></label>
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<label for="text-rfc" class="required">R. F. C.:</label>
					<input id="text-rfc" type="text" name="text-rfc" class="form-control" placeholder="R. F. C." 
							minlength="12" maxlength="13" rfc="rfc" required="required"
							data-bind="value: cliente.rfc"/>
					</select>
				</div>
			</div>
		</div>

		<div class="row" data-bind="visible: visibleMoral">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-razon-social" class="required">Razón Social: </label>
					<input id="txt-razon-social" name="txt-razon-social" type="text"
						placeholder="Razón Social" class="form-control" required="required" maxlength="255"
						data-bind="value: cliente.razonSocial" />
				</div>
			</div>
		</div>

		<div class="row" data-bind="visible: !visibleMoral() && cliente.tipo">

			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-nombre" class="required">Nombre:</label>
					<input id="txt-nombre" name="txt-nombre" type="text"
						   placeholder="Nombre" class="form-control"
						   required="required" maxlength="255"
						   data-bind="value: cliente.nombre" />
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-apellido-paterno" class="required">Apellido Paterno: </label>
					<input id="txt-apellido-paterno" name="txt-apellido-paterno" type="text"
						placeholder="Apellido Paterno" class="form-control"
						required="required" maxlength="255"
						data-bind="value: cliente.apellidoPaterno" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-apellido-materno">Apellido Materno: </label>
					<input id="txt-apellido-materno" name="txt-apellido-materno" type="text" 
						placeholder="Apellido Materno" class="form-control" maxlength="255"
						data-bind="value: cliente.apellidoMaterno" />
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label for="txt-razon-comercial" class="required">Razón Comercial</label>
					<input id="txt-razon-comercial" name="txt-razon-comercial" type="text" 
						placeholder="Razón Comercial" class="form-control" maxlength="255"
						data-bind="value: cliente.razonComercial" required="required" />
				</div>
			</div>
			<!--<div class="col-md-4">
				<div class="form-group">
					<label for="txt-sitio">Página Web</label>
					<input id="txt-sitio" name="txt-sitio" type="text" 
						placeholder="Página Web" class="form-control" maxlength="255" url2="url2"
						data-bind="value: cliente.paginaWeb" />
				</div>
			</div>-->
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label for="txt-observaciones">Observaciones</label>
					<textarea rows="3" cols="10" placeholder="Observaciones"
							id="txt-observaciones" name="txt-observaciones" class="form-control" maxlength="255"
							data-bind="value: cliente.observaciones"></textarea>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="cbo-promotor">Residencia Fiscal:</label> <select
						id="cbo-residenciaFiscal" name="cbo-residenciaFiscal"
						class="form-control"
						data-bind="options: $root.residenciasFiscales,
								optionsCaption : '- Seleccionar -',
                                value: cliente.pais.id,
                                optionsText: 'descripcion',
                                optionsValue:'id', event:{ change: $root.changeStatus}">
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="cbo-promotor">Uso del CFDI:</label> <select
						id="cbo-usoCfdi" name="cbo-usoCfdi" class="form-control"
						data-bind="options: $root.usosCfdi,
								optionsCaption : '- Seleccionar -',
                                value: cliente.usoCFDi.id,
                                optionsText: 'descripcion',
                                optionsValue:'id', event:{ change: $root.changeStatus}">
					</select>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="panel-body">
				<h4 class="page-header">Contactos</h4>

				<div class="row">
					<div class="col-md-3">
						<button type="button" id="btn-add-contacto" class="btn btn-info" data-toggle="modal" data-target="#modal-contacto">Agregar</button>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th>Nombre</th>
										<th>Apellido Paterno</th>
										<th>Apellido Materno</th>
										<th>Puesto</th>
										<th>Email</th>
										<th>&nbsp;</th>
									</tr>
								</thead>
								<tbody data-bind="foreach: cliente.contactos">
									<tr>
										<td data-bind="text: nombre"><span ></span></td>
										<td data-bind="text: apellidoPaterno"><span ></span></td>
										<td data-bind="text: apellidoMaterno"><span></span></td>
										<td data-bind="text: puesto"><span></span></td>
										<td data-bind="text: email"><span></span></td>
										<td>
											<button class="btn btn-info" data-bind="click:$root.eliminarContacto">Eliminar</button>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</fielset>
</form>