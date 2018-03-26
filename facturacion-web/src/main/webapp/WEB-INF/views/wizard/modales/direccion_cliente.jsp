<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div class="modal fade" id="modal-direccion-cliente" tabindex="-1"
	role="dialog" aria-labelledby="modal-direccion-label"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modal-direccion-label">Direcci&oacute;n</h4>
			</div>
			<div class="modal-body">
				<form id="form-modal-direccion-cliente" role="form"
					novalidate="novalidate">
					<fieldset>
						<jsp:include page="../direccion_cliente.jsp">
							<jsp:param value="${param.tipo}" name="tipo" />
						</jsp:include>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="btn-dir-cancel" class="btn btn-default"
					data-dismiss="modal">Cerrar</button>
				<button type="button" id="btn-dir-ok" class="btn btn-primary"
					data-bind="click: clienteViewModel.agregarDireccion">Agregar</button>
			</div>
		</div>
	</div>
</div>