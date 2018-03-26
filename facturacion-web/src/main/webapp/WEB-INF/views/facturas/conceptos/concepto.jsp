<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<div class="row">
	<div class="col-md-1">
		<div class="form-group">
			<label for="" class="required">Cant.</label> <input type="text"
				id="cantidad" name="cantidad" required="required"
				placeholder="Cant." class="form-control text-right" number="number"
				maxlength="10" data-bind="value: partida.cantidad" />
		</div>
	</div>
	<div class="col-md-2">
		<div class="form-group">
			<label class="required">Código Interno</label> <input type="text"
				id="txt-codigo" name="txt-codigo" required="required"
				placeholder="Código" class="form-control" maxlength="25"
				data-bind="value: partida.codigo" autocomplete="on	" />
		</div>
	</div>
	<div class="col-md-2">
		<div class="form-group">
			<label for="cbo-clave-unidad" class="required">Catalogo
				Prod/Serv:</label> <input type="text" id="txt-prodserv" name="txt-prodserv"
				required="required" placeholder="Código" class="form-control" /> </select>
		</div>
	</div>
	<div class="col-md-3">
		<div class="form-group">
			<label for="" class="required">Descripción</label> <input type="text"
				id="txt-descripcion" name="txt-descripcion" required="required"
				placeholder="Descripción" class="form-control" maxlength="2048"
				data-bind="value: partida.descripcion" />
		</div>
	</div>
	<div class="col-md-4">
		<div class="form-group">
			<label for="cbo-clave-unidad" class="required">Clave de
				Unidad:</label> <select id="cbo-clave-unidad" name="cbo-clave-unidad"
				required="required" class="form-control"
				data-bind="options: $root.clavesUnidad,
                        optionsCaption : '- Seleccione una opción -',
                        value: partida.claveUnidad.id,
                        optionsText: function(item) {
                            return item.descripcion;
                        },
                        optionsValue:'id',
                        event:{ change: $root.changeStatus}">
			</select>
		</div>
	</div>
	<div class="col-md-2">
		<div class="form-group">
			<label for="" class="required">Unidad Interna</label> <input
				type="text" id="txt-unidad-medida" name="txt-unidad-medida"
				required="required" placeholder="Unidad De Medida"
				class="form-control" maxlength="20"
				data-bind="value: partida.unidadDeMedida" />
		</div>
	</div>
	<div class="col-md-2">
		<div class="form-group">
			<label for="" class="required">Precio Unitario</label> <input
				type="text" id="txt-precio-unitario" name="txt-precio-unitario"
				required="required" placeholder="Precio Unitario"
				class="form-control text-right" number="number" min="0"
				maxlength="15" data-bind="value: partida.precioUnitario" />
		</div>
	</div>
	<div class="col-md-1">
		<div class="form-group">
			<label for="" class="required">% Desc</label> <input type="text"
				id="txt-pje-descuento" name="txt-pje-descuento" required="required"
				placeholder="% Desc" class="form-control text-right" maxlength="5"
				min="0" max="100" maxlength="5" data-bind="value: partida.descuento" />
		</div>
	</div>
	<div class="col-md-7">
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label for="" class="required">% Iva Trasladado</label> <input
						type="text" id="txt-ivaTrasladado" name="txt-ivaTrasladado"
						required="required" class="form-control text-right"
						number="number" min="0" maxlength="5"
						data-bind="value: partida.ivaTrasladado" />
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label for="" class="required">% IEPS Trasladado</label> <input
						type="text" id="txt-iepsTrasladado" name="txt-iepsTrasladado"
						required="required" class="form-control text-right"
						number="number" min="0" maxlength="5"
						data-bind="value: partida.iepsTrasladado" />
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label for="" class="required">% IVA Retenido</label> <input
						type="text" id="txt-ivaRetenido" name="txt-ivaRetenido"
						required="required" class="form-control text-right"
						number="number" min="0" maxlength="5"
						data-bind="value: partida.ivaRetenido" />
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label for="" class="required">% ISR Retenido</label> <input
						type="text" id="txt-isrRetenido" name="txt-isrRetenido"
						required="required" class="form-control text-right"
						number="number" min="0" maxlength="5"
						data-bind="value: partida.isrRetenido" />
				</div>
			</div>
		</div>
	</div>
</div>