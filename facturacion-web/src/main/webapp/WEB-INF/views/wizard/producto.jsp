<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form id="form-producto" role="form" novalidate="novalidate">
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
			<div class="col-md-3">
				<div class="form-group">
					<label for="txt-codigo" class="required">Código: </label> <input
						id="txt-codigo" name="txt-codigo" type="text" placeholder="Código"
						required="required" maxlength="15" class="form-control"
						data-bind="value: productoViewModel.producto.codigo" />
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label for="txt-descripcion" class="required">Descripción:
					</label> <input id="txt-descripcion" name="txt-descripcion" type="text"
						placeholder="Descipción" class="form-control" required="required"
						maxlength="500"
						data-bind="value: productoViewModel.producto.nombre" />
				</div>
			</div>
			<div class="col-md-2">
				<div class="form-group">
					<label for="txt-precio" class="required">Precio Unitario: </label>
					<input id="txt-precio" name="txt-precio" type="text"
						placeholder="Precio Unitario" class="form-control text-right"
						maxlength="15" number="number" required="required"
						data-bind="value: productoViewModel.producto.precio" />
				</div>
			</div>
			<div class="col-md-2">
				<div class="form-group">
					<label for="check-iva">Exento IVA: </label>
					<div class="checkbox">
						<label> <input type="checkbox" value="1" name="check-iva"
							id="check-iva"
							data-bind="checked: productoViewModel.producto.exentoIVA">Sí
						</label>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label for="cbo-unidad-medida" class="required">Unidad de
						Medida: </label> <select id="cbo-unidad-medida" name="cbo-unidad-medida"
						class="form-control" required="required"
						data-bind="options: $root.productoViewModel.unidadesMedida, 
								optionsCaption : '- Seleccione una opción -', 
								value: productoViewModel.producto.unidadDeMedida.id, 
								optionsText: 'descripcion', 
								optionsValue:'id'">
					</select>
				</div>
			</div>
		</div>
	</fieldset>
</form>