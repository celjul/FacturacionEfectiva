<%@ page contentType="text/html;charset=UTF-8" language="java"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Registro de comprobante</title>

	<%--<meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link href="${css}/footable/footable.core.css" rel="stylesheet" type="text/css" />

<script src="${js}/numeral/numeral.js"></script>
<script src="${js}/jquery/footable.js"></script>
<script src="${js}/app/commons/catalogos.js?version=${project.version}"></script>
<script src="${js}/app/emisores/emisores.js"></script>
<script src="${js}/app/clientes/clientes.js"></script>
<script src="${js}/app/facturas/facturas.lista.app.js"></script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row" id="separacionHeader">
			<div class="col-lg-12">
				<h3 class="page-header">&nbsp;</h3>
			</div>
		</div>
		<div class="row busqueda">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading" id="titulofiltro">
						<span class="icon-arrow-down"></span> Filtros de búsqueda
					</div>
					<div class="panel-body" id="panelfiltro">
						<form role="form" id="form-list" method="get"
							novalidate="novalidate">
							
							<fieldset>
								<div class="row">
									<div class="col-md-2">
										<div class=" form-group">
											<label>Fecha De Inicio:</label>
											<div class="input-group">
												<input id="text-fecha-inicio" type="text" name="fi"
													class="form-control" placeholder="Inicio"
													data-bind="value: fechaInicio" /> <span
													class="input-group-btn"/>
													<button class="btn btn-default" type="button"
														datepicker-clean="text-fecha-inicio">
														<i class="fa fa-remove"></i>
													</button>
											</div>
										</div>
									</div>
									<div class="col-md-2">
										<div class=" form-group">
											<label for="text-fecha-fin">Fecha De Fin:</label>
											<div class="input-group">
												<input id="text-fecha-fin" type="text" class="form-control"
													requiredIf="#text-fecha-inicio" placeholder="Final"
													future="#text-fecha-inicio, 'Fecha de Inicio'"
													data-bind="value: fechaFin" /> <span
													class="input-group-btn"/>
													<button class="btn btn-default" type="button"
														datepicker-clean="text-fecha-fin">
														<i class="fa fa-remove"></i>
													</button>
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="cboTipoDocumento">Tipo de Documento: </label> <select
												id="cboTipoDocumento" class="form-control"
												data-bind="options: $root.tiposDocumento,
                                                optionsCaption : '- Seleccione una opción -',
                                                value: tipoDocumento,
                                                optionsText: 'descripcion',
                                                optionsValue:'id'">
											</select>
										</div>
									</div>
									<div class="col-md-5">
										<div class="form-group">
											<label for="text-nombre-cliente">Cliente: </label> <input
												id="text-nombre-cliente" type="text" class="form-control"
												placeholder="Cliente" data-bind="value: cliente.nombre" />
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-2">
										<div class=" form-group">
											<label>Monto Inicial:</label> <input id="text-monto-inicial"
												type="text" class="form-control" placeholder="Monto Inicial"
													data-bind="value: montoInicial" />
										</div>
									</div>
									<div class="col-md-2">
										<div class=" form-group">
											<label for="text-fecha-fin">Monto Final:</label> <input
												id="text-monto-final" type="text" class="form-control"
												placeholder="Monto Final" data-bind="value: montoFinal" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="text-rfc">R.F.C.: </label> <input
												id="text-rfc" type="text" class="form-control"
												placeholder="R.F.C." data-bind="value: cliente.rfc" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="text-estatus">Estatus: </label> <select
												id="text-estatus" class="form-control"
												data-bind="options: $root.tiposEstatus,
                                                optionsCaption : '- Seleccione una opción -',
                                                value: estatus,
                                                optionsText: 'descripcion',
                                                optionsValue:'id'">
											</select>
										</div>
									</div>
						
								</div>

								<div class="row">
									<div class="col-md-6 col-md-offset-6 text-center"
										id="contenedor_botones">
										<button type="button" class="btn btn-sm btn-success"
											data-bind="click: buscar" id="btn-abrir">Buscar</button>
										<button type="button" class="btn btn-sm btn-info"
											data-bind="click: descargar" id="btn-abrir">Descargar ZIP</button>
										<a class="btn btn-sm btn-success"
											data-bind="attr: {href : linkPDF}" target="_blank"
											id="btn-exportpdf">Exportar a PDF</a> <a
											class="btn btn-sm btn-success"
											data-bind="attr: {href : linkExcel}" target="_blank"
											id="btn-exportexcel">Exportar a Excel</a>
									</div>
								</div>
							</fieldset>

						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12  container">
				<div class="panel panel-default">
					<div class="panel-heading" id="tituloListado">Listado de
						Remisiones y/o CFDI  <img data-toggle="modal" class="ayuda" data-target="#myModalayuda" src="${rsrc}/img/icon/interrogacion.png" width="20px" height="20px" alt=""/></div>
						<div class="panel-body content ">
						<div class="dataTable_wrapper table-responsive toggle-circle-filled ">
							<form id="frmDownload" method="POST" target="_blank" action="${contextPath}/app/facturacion/masiva/download">
							<table class="table table-striped table-hover footable"
								id="table-list-envoice">
								<thead>
									<tr>
										<!--                              <th class="text-center">Id</th> -->
										<th  data-hide="phone" class="text-center">Fecha de<br />Elaboración
										</th>
										<th data-hide=" phone" class="text-center">RFC</th>
										<th  data-toggle="true"  class="text-center">Cliente</th>
										<th  data-hide=" phone" class="text-center">Monto <br />sin Impuestos
										</th>
										<th  data-hide="phone" class="text-center">PDF</th>
										<th  data-hide=" phone" class="text-center">XML</th>
										<th  data-hide=" phone" class="text-center">Enviar</th>
										<th  data-hide="phone" class="text-center">Activo</th>
										<th></th>
										<th  data-hide="phone" class="text-center">
											Seleccionar <input type="checkbox" class="form-check-input" data-bind="checked: seleccionadas, click: seleccionar" >
										</th>
									</tr>
								</thead>
								<tbody data-bind="foreach: comprobantes">
									<tr>
										<td data-bind="text: fechaCreacion"></td>
										<td  data-bind="text: rfc"></td>
										<td  data-bind="text: cliente"></td>

										<td  class="text-right"
											data-bind="text: numeral(montoSinIVA).format('$ 0,0.00')"></td>
										<td  class="text-center"><a target="_blank"
											data-bind="visible: uuid, attr: {href: String.format('{0}/app/comprobantes/{1}/file/pdf', contextPath, id)}">
												<img alt="Descargar PDF" src="${img}/file/file-pdf.png" />
										</a></td>
										<td  class="text-center"><a target="_blank"
											data-bind="visible: uuid, attr: {href: String.format('{0}/app/comprobantes/{1}/file/xml', contextPath, id)}">
												<img alt="Descargar XML" src="${img}/file/file-xml.png" />
										</a></td>
										<td class="text-center" data-bind="attr: { id: 'email-' + id }"><a
											data-bind="visible: estatus, click: $root.enviarPorEmail"
										<%--	javascript:;	--%>	href="" data-toggle="modal" data-target="#myModal"> <img alt="Enviar Comprobante"
												src="${img}/icon/send.png" />
										</a></td>
										<td class="text-center" data-bind="attr: { id: 'activo-' + id }"><a
											data-bind="click: function(data){$root.cancelarComprobante(data);}, visible: uuid && estatus">
												<img alt="Comprobante Activo" src="${img}/icon/activo.png" />
										</a> <a
											data-bind="visible: uuid && !estatus, attr: {href: String.format('{0}/app/comprobantes/{1}/file/cancelacion', contextPath, id)}"
											target="_blank"> <img alt="Comprobante Cancelado"
												src="${img}/icon/inactivo.png" />
										</a></td>
										<td  class="text-center"><a
											class="btn btn-sm btn-success"
											data-bind="visible: !uuid, attr: {href: String.format('{0}/app/comprobantes/{1}', contextPath, id)}">Abrir</a>
										</td>
										<td  class="text-center">
											<input type="checkbox" class="form-check-input" name="ids" data-bind="visible: uuid && estatus, attr: { value: id }" >
										</td>
									</tr>
								</tbody>
							</table>
						
							</form>
							<form action="${contextPath}/app/comprobantes/?lblclave=3=1" method="GET" id="form-lista">
							<input type="hidden" id="lblclave" name="lblclave" value="1">
							</form>
							
							<ul class="pagination">
						  		<li>
						    		<a	href="#" id="myLink"  onclick="pasaridclave(1)">1 <span class="sr-only">(página actual)</span></a>
							  	</li>
							  	<li >
							    	<a href="#" id="myLink"  onclick="pasaridclave(2)">2 <span class="sr-only">(página actual)</span></a>
							  	</li>
								<li >
							    	<a href="#" id="myLink" onclick="pasaridclave(3) ">3 <span class="sr-only">(página actual)</span></a>
							  	</li>
							</ul>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Envio de Comprobante.</h4>
				</div>
				<div class="modal-body">
					<div class="modal-body" >
						<table class="table">
							<thead>
							<tr>
								<th>Contactos a enviar:</th>
							</tr>
							</thead>
							<tbody data-bind="foreach: $root.cliente.contactos">
							<tr>
								<td data-bind="text: email"><span></span></td>
								<td>
									<input type="checkbox" name="marcado" id="marcado" data-bind="checked:marcado"><br>
								</td>
							</tr>
							</tbody>
							
						</table>
						
					</div>
					
					<div class="row">
						<div class="panel-body">
							<h4 class="page-header">Enviar tambien a:</h4>

							<div class="row">
								<div class="col-md-12">
									<div class="col-md-8 col-xs-12"><input class="cc" data-bind="value:$root.ccaux"  type="email" placeholder="mandar copia a:"/></div>
									<div class="col-md-4"><button type="button" class="btn btn-default" data-bind="click: $root.agregarcc">Agregar</button></div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="table-responsive">
										<table class="table">
											<thead>
											<tr>
											</tr>
											</thead>
											<tbody data-bind="foreach: { data: $root.cc, as: 'emailcc' }">
											<tr>
												<td data-bind="text:emailcc" class="col-md-8"></td>

												<td class="col-md-4">
													<button type="button" class="btn btn-info" data-bind="click: $root.eliminarCC">Eliminar</button>
												</td>
											</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<textarea placeholder="Escribe el mensaje a adjuntar al comprobante." data-bind="value: $root.mensaje"></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" data-bind="click: $root.enviarEmail">Enviar</button>
				</div>
			</div>
		</div>
		
	</div>
	<div id="myModalayuda" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Servicio de ayuda.</h4>
				</div>
				<div class="modal-body">
					<label>Los campos que deberá registrar son los siguientes: <br></label>

					<ul>
						<li class="txtayuda"> <stron>Fecha de elaboración.</stron> Fecha en la que se crea la factura o nota de crédito.</li>
						<li class="txtayuda"> <strong>RFC.</strong> RFC del cliente al que se le emitió la factura. </li>
						<li class="txtayuda"> <strong>Cliente.</strong> Razón social del cliente al que se le emitió la factura.</li>
						<li class="txtayuda"> <strong>PDF.</strong> Al dar clic en el icono “PDF” se descargará la factura. <img src="${img}/file/file-pdf.png" alt=""/></li>
			 			<li class="txtayuda"> <strong>XML.</strong> Al dar clic en el icono “XML” se descargará la factura.<img src="${img}/file/file-xml.png" alt=""/></li>
						<li class="txtayuda"> <strong>Enviar.</strong> Al dar clic en el icono “Correo” se mostrará una pantalla donde el cliente usuario puede capturar un mensaje opcional que se incluirá en el correo.</li>
						<li class="txtayuda"> <strong>Activo.</strong> Casilla que le permite al usuario conocer el estatus del comprobante, mostrará una paloma color verde indicando que el estatus de la factura se encuentra “Vigente”, si el estatus de la factura es “Cancelada” se mostrará un tache color rojo.
							</li>
					</ul>
				</div>
				<div class="modal-footer">
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
	<script>
      $(document).ready(function(){
		  $("table").footable();
		  $("#titulofiltro").click(function(){
            $("#panelfiltro").slideToggle("slow");
         });
      });
      
      ('#myLink').click(function(){ 
    	  pasaridclave(2); 
    	  return false;
    	});
      
      function pasaridclave(id){
    	  alert(id);
    	  document.getElementById('lblclave').value=id;
    	  var myform = document.getElementById('form-lista');
	      myform.submit();
      }

      
   </script>
</body>
</html>