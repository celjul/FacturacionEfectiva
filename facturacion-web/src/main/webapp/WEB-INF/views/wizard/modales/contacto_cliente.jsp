<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="modal-contacto" tabindex="-1" role="dialog" aria-labelledby="modal-contacto-label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modal-contacto-label">Contacto</h4>
			</div>
			<div class="modal-body">
				<form id="form-modal-contacto" role="form" novalidate="novalidate">
					<fieldset>
						<jsp:include page="../contacto_cliente.jsp" />
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="btn-modal-cto-close" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button type="button" id="btn-modal-cto-agregar" class="btn btn-primary" data-bind="click: clienteViewModel.agregarContacto">Agregar</button>
			</div>
		</div>
	</div>
</div>