<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="modal-concepto" tabindex="-1" role="dialog" aria-labelledby="modal-direccion-label" aria-hidden="true">
	<div class="modal-dialog" style="width: 90%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="modal-concepto-label">Concepto</h4>
			</div>
			<div class="modal-body">
				<form id="form-modal-concepto" role="form" novalidate="novalidate">
					<fieldset>
						<jsp:include page="../concepto.jsp" >
							<jsp:param value="${param.tipo}" name="tipo" />
						</jsp:include>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="btn-modal-close" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button type="button" id="btn-modal-add" class="btn btn-primary" data-bind="visible: nuevo, click: agregarConcepto">Agregar</button>
				<button type="button" id="btn-modal-update" class="btn btn-info" data-bind="visible: !nuevo(), click: editarConcepto">Actualizar</button>
			</div>
		</div>
	</div>
</div>