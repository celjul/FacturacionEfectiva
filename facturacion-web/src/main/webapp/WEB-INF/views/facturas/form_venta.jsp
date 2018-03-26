<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function myFunction() {
        document.getElementById("txt-lista-clientes").innerHTML = "Hello World";
    }
</script>

<form id="form-comprobante" role="form" novalidate="novalidate">
    <fieldset>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label for="cbo-tipo-documento" class="required">Tipo de
                        Documento:</label> <select id="cbo-tipo-documento"
                                                   name="cbo-tipo-documento" class="form-control" required="required"
                                                   data-bind="options: $root.tiposDocumento,
								optionsCaption : '- Seleccione una opción -', 
								value: comprobante.tipo.id, 
								optionsText: 'descripcion', 
								optionsValue:'id', event:{ change: $root.changeStatus}">
                </select>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="txt-fecha-creacion" class="required">Fecha de
                        Creación:</label> <input type="text" id="txt-fecha-creacion"
                                                 name="txt-fecha-creacion" placeholder="Fecha" class="form-control"
                                                 readonly="readonly" required="required"
                                                 data-bind="value: comprobante.fechaCreacion,
								event:{ change: $root.changeStatus}"/>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="txt-fecha-entrega" class="required">Fecha de
                        Entrega: </label> <input type="text" id="txt-fecha-entrega"
                                                 name="txt-fecha-entrega" required="required"
                                                 placeholder="Fecha de Entrega" class="form-control"
                                                 readonly="readonly"
                                                 data-bind="value: comprobante.fechaEntrega,
								event:{ change: $root.changeStatus}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label for="txt-lista-clientes" class="required">Listado de
                        Clientes: </label> <select id="cbo-cliente" name="cbo-cliente"
                                                   class="form-control" class="required"
                                                   data-bind="options: $root.listaClientes,
					 								   optionsCaption : '- Mostrar Clientes -',
					 								   value: comprobante.cliente.id,
					 								   optionsText: 'nombreCompleto',
													   optionsValue:'id', event:{ change: $root.changeStatus}"></select>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="cbo-metodo-pago" class="required">Método de
                        Pago:</label> <select id="cbo-metodo-pago" name="cbo-metodo-pago"
                                              required="required" class="form-control"
                                              data-bind="options: $root.metodosPago,
								optionsCaption : '- Seleccione una opción -', 
								value: comprobante.metodo.id, 
								optionsText: 'descripcion', optionsValue:'id', 
								event:{ change: function(item){
										$root.changeStatus();
										$root.changeNumeroCuenta();
									}
								}">
                </select>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="cbo-tipo-serie" class="required">Serie:</label>
                    <select id="cbo-tipo-serie"
                            name="cbo-tipo-serie"
                            class="form-control"
                            required="required"
                            data-bind="options: $root.listaSeries,
						optionsCaption : '- Seleccione una Serie -',
						value: comprobante.serie.id,
						optionsText: 'nombre',
						optionsValue:'id', event:{ change: function () {$root.changeStatus(); $root.setSeccionCompleta();}}">
                    </select>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="txt-lugar-expedicion" class="required">No. de Folio:</label>
                    <input type="text"
                           readonly="readonly"
                           id="txt-sig-folio"
                           name="txt-sig-folio"
                           class="form-control"
                           data-bind="value: comprobante.serie.siguienteFolio,
        						event:{ change: $root.changeStatus}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
				<div class="form-group">
					<label for="cbo-metodo-pago" class="required">Forma de
						Pago:</label> <select id="cbo-forma-pago" name="cbo-forma-pago"
						required="required" class="form-control"
						data-bind="options: $root.formasPagos,
                                optionsCaption : '- Seleccione una opción -',
                                value: comprobante.forma.id,
                                optionsText: 'descripcion', optionsValue:'id',
                                event:{ change: $root.changeStatus}">
					</select>
				</div>
			</div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="txt-condicion-pago" class="required">Condición
                        de Pago:</label> <input type="text" id="txt-condicion-pago"
                                                name="txt-condicion-pago" placeholder="Condición de Pago"
                                                class="form-control" required="required"
                                                data-bind="value: comprobante.condicion,
								event:{ change: $root.changeStatus}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label for="txt-lugar-expedicion" class="required">Lugar de
                        Expedición (Código Postal):</label> <input type="text" id="txt-lugar-expedicion"
                                                   name="txt-lugar-expedicion" placeholder="Lugar de Expedición"
                                                   required="required" class="form-control"
                                                   data-bind="value: comprobante.lugarDeExpedicion,
								event:{ change: $root.changeStatus}"/>
                </div>
            </div>
        </div>
        <div class="row" data-bind="visible: !plantilla()">
            <div class="col-md-6 col-md-offset-3">
                <div class="alert alert-danger alert-dismissable">La configuración de la plantilla aún no ha sido completada.
                </div>
            </div>
        </div>
        <div class="row" data-bind="visible: alertaCertificado">
            <div class="col-md-6 col-md-offset-3">
                <div class="alert alert-warning alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <p>
                        <strong>¡Atención!</strong>
                    </p>

                    <p>
                        Su CSD caduca en <span data-bind="text: dias"></span> días
                    </p>
                </div>
            </div>
        </div>
        <div class="row" data-bind="visible: certificado">
            <div class="col-md-6 col-md-offset-3">
                <div class="alert alert-danger alert-dismissable">
                    <p>
                        <strong>¡Atención!</strong>
                    </p>

                    <p>Su CSD ha caducado, debe actualizarlo para poder facturar.</p>
                </div>
            </div>
        </div>
        <div class="row" data-bind="visible: timbres">
            <div class="col-md-6 col-md-offset-3">
                <div class="alert alert-danger alert-dismissable">
                    <p>
                        <strong>¡Atención!</strong>
                    </p>

                    <p data-bind="text: disponibles"></p>
                </div>
            </div>
        </div>
        <div class="row" data-bind="visible: sinCertificado">
            <div class="col-md-6 col-md-offset-3">
                <div class="alert alert-danger alert-dismissable">
                    <p>
                        <strong>¡Atención!</strong>
                    </p>

                    <p>Debe configurar al menos un CSD para poder facturar.</p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="panel-body">
                <h4 class="page-header">Conceptos</h4>
                <div class="row">
                    <div class="col-md-3">
                        <button type="button" id="btn-add-concepto" class="btn btn-info"
                                data-toggle="modal" data-target="#modal-concepto">Agregar
                        </button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead>
                                <tr>
                                    <th class="text-center">Cantidad</th>
                                    <th class="text-center">Unidad de<br/>Medida
                                    </th>
                                    <th class="text-center">Código</th>
                                    <th class="text-center">Clave de Pr/Serv</th>
                                    <th class="text-center">Descripción</th>
                                    
                                    <th class="text-center" style="width: 10%">Precio <br/>Unitario
                                    </th>
                                    <th class="text-center" style="width: 10%">Importe</th>
                                    <th class="text-center" style="width: 10%">% Desc.</th>
                                    <th class="text-center" style="width: 10%">% Iva Tras</th>
                                    <th class="text-center" style="width: 10%">% Ieps Tras</th>
                                    <th class="text-center" style="width: 10%">% Iva Ret</th>
                                    <th class="text-center" style="width: 10%">% Isr Ret</th>
                                    <th>&nbsp;</th>
                                </tr>
                                </thead>
                                <tbody
                                        data-bind="foreach: { data: comprobante.conceptos() }, event:{ change: $root.changeStatus}">
                                <tr>
                                    <td class="text-right" data-bind="text: cantidad"></td>
                                    <td data-bind="text: unidadDeMedida"></td>
                                    <td data-bind="text: codigo"></td>
                                     <td data-bind="text: claveProdServ.descripcion"></td>
                                    <td data-bind="text: descripcion"></td>
                                    <td class="text-right"
                                        data-bind="text: numeral(precioUnitario()).format('$0,0.00')"></td>
                                    <td class="text-right"
                                        data-bind="text: numeral(importe()).format('$0,0.00')"></td>
                                    <td class="text-right"
                                        data-bind="text: numeral(ivaTrasladado() * 100).format('0,0.00')"></td>
                                    <td class="text-right"
                                        data-bind="text: numeral(iepsTrasladado() * 100).format('0,0.00')"></td>
                                    <td class="text-right"
                                        data-bind="text: numeral(ivaRetenido() * 100).format('0,0.00')"></td>
                                    <td class="text-right"
                                        data-bind="text: numeral(isrRetenido() * 100).format('0,0.00')"></td>
                                    <td>
                                        <button type="button" class="btn btn-info"
                                                data-bind="click: $root.eliminarPartida"><i class="fa fa-trash"></i>
                                        </button>
                                        <button type="button" class="btn btn-info"
                                                data-bind="click: function (data, event) { $root.cargarPartida(data, $index) }">
                                            <i class="fa fa-pencil"></i>
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th class="text-right" colspan="11">Importe con descuento:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.subtotal()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th class="text-right" colspan="11">Descuento:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.montoDelDescuento()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th class="text-right" colspan="11">Subtotal:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.subtotal() - comprobante.montoDelDescuento()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th class="text-right" colspan="11">IVA:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.montoIva()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th class="text-right" colspan="11">IEPS:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.montoIeps()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th class="text-right" colspan="11">Ret. IVA:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.montoRetIva()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th class="text-right" colspan="11">Ret. ISR:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.montoRetIsr()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th class="text-right" colspan="11">Total:</th>
                                    <td class="text-right" colspan="2"
                                        data-bind="text: numeral(comprobante.total()).format('$0,0.00')"></td>
                                    <td>&nbsp;</td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="">Motivo Descuento:</label>
					<textarea rows="2" cols="300" id="txt-motivo-desc"
                              name="txt-motivo-desc" placeholder="Motivo de descuento"
                              class="form-control"
                              data-bind="value: comprobante.motivoDescuento,
								event:{ change: $root.changeStatus}" maxlength="165"></textarea>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="">Leyenda del Comprobante:</label>
					<textarea rows="2" cols="200" id="txt-leyenda" id="txt-leyenda"
                              placeholder="Leyenda del Comprobante" class="form-control"
                              data-bind="value: comprobante.leyendaComprobante,
								event:{ change: $root.changeStatus}" maxlength="165"></textarea>
                </div>
            </div>
        </div>
    </fieldset>
</form>
<div id="myModalayuda" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Servicio de ayuda.</h4>
            </div>
            <div class="modal-body">
                <label>Los datos que se muestran en la lista de facturas emitidas por el usuario actualmente conectado
                    son: <br></label>
                <ul>
                    <li class="txtayuda"><strong>Tipo de documento.</strong> Selecciona la opción Factura o Nota de
                        crédito.
                    </li>
                    <li class="txtayuda"><strong>Listado de ciente.</strong> El usuario captura la “Razón Social” del
                        cliente registrado en el sistema al que le emitirá el comprobante (al capturar las primeras 3
                        letras se desplegará una lista de todos los nombres que coinciden en la lista de clientes
                        registrados en el catálogo de clientes). Así mismo el usuario deberá seleccionar una opción.
                    </li>
                    <li class="txtayuda"><strong>Fecha de creación.</strong>Este campo muestra la fecha actual (Es un
                        campo no modificable).
                    </li>
                    <li class="txtayuda"><strong>Condición de pago.</strong> Por default se le presenta al usuario el
                        texto “Inmediato”. El usuario puede modificar la condición de pago.
                    </li>
                    <li class="txtayuda"><strong>Método de pago.</strong> Muestra una lista de opciones con los
                        siguientes valores: Cheque, Efectivo, No identificado, Tarjeta de crédito, Tarjeta de débito y
                        Transferencia. El usuario podrá elegir una sola opción de la lista.
                    </li>
                    <li class="txtayuda"><strong>Fecha de entrega. </strong> Al dar clic en este campo el usuario podrá
                        elegir una fecha. (El sistema solo permitirá elegir un día a partir de la fecha actual).
                    </li>
                    <li class="txtayuda"><strong>Forma de pago.</strong>Por default se le presenta al usuario el texto
                        “En una sola exhibición”. El usuario puede modificar la forma de pago.
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>