<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form id="form-producto" role="form" novalidate="novalidate">
	<fieldset>
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label  class="required">Código: </label>

					<input id="txt-codigo" name="txt-codigo" type="text" placeholder="Código"
						required="required" maxlength="15"
						class="form-control" data-bind="value: producto.codigo" />
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label for="txt-descripcion" class="required">Descripción: </label>
					<input id="txt-descripcion" name="txt-descripcion" type="text"
						placeholder="Descipción" class="form-control"
						required="required" maxlength="500"
						data-bind="value: producto.nombre" />
				</div>
			</div>
			<div class="col-md-2">
				<div class="form-group">
					<label for="txt-precio" class="required">Precio Unitario: </label>
					<input id="txt-precio" name="txt-precio" type="text" 
						placeholder="Precio Unitario" class="form-control text-right"
						maxlength="15" number="number" required="required"
						data-bind="value: producto.precio" />
				</div>
			</div>
			<div class="col-md-2">
				<div class="form-group">
					<label for="check-iva">Exento de IVA: </label>
					<div class="checkbox">
						<label>
							<input type="checkbox" value="1" name="check-iva" id="check-iva" data-bind="checked: producto.exentoIVA">Sí
						</label>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label for="cbo-unidad-medida" class="required">Unidad de Medida: </label>
					<select id="cbo-unidad-medida" name="cbo-unidad-medida"
						class="form-control" required="required"
						data-bind="options: $root.unidadesMedida, 
								optionsCaption : '- Seleccione una opción -', 
								value: producto.unidadDeMedida.id, 
								optionsText: 'descripcion', 
								optionsValue:'id'">
					</select>
				</div>
			</div>
		</div>
	</fieldset>
</form>


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
				<label>Los campos a registrar son los siguientes: <br></label>

				<ul>
					<li class="txtayuda"> <stron>Codigo.</stron> Clave que identifica al producto.</li>
					<li class="txtayuda"> <strong>Descripcion.</strong> . Descripción del producto. </li>
					<li class="txtayuda"> <strong>Precio unitario.</strong>  Valor monetario del producto. </li>
					<li class="txtayuda"> <strong> Exento de IVA.</strong>  El producto no el incluye IVA si la casilla se encuentra seleccionada. </li>
					<li class="txtayuda"> <strong> Unidad de medida.</strong>  Seleccionar la unidad del producto (pieza o servicio).</li>
					<li class="txtayuda">
						Una vez que el usuario haya capturado los campos correctamente, deberá dar clic en el botón “Guardar”. El sistema guardará el nuevo producto capturado y mostrará el siguiente mensaje de confirmación: “¡Éxito!, Se registró al cliente correctamente”. </li>

					</li>



				</ul>


			</div>
			<div class="modal-footer">
			</div>
		</div>

	</div>
</div>
