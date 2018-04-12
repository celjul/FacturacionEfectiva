<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<script type="text/javascript">
$(function() {
	$('#formUpload').on("submit", function() {
		$('#loading').modal("show");
	    return true;
	});
});
</script>
<title>Facturación Masiva</title>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-md-12">
				<h3 class="page-header">Nueva Facturaci&oacute;n Masiva</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<p class="text-right"> 
					<a  download="Layoutconejemplo.csv">Archivo CSV Ejemplo</a> | <a href="#">Cat&aacute;logos SAT</a>
				</p>
				<p class="text-muted">Tiempo aproximado:</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<form:form method="POST" action="${contextPath}/app/facturacion/masiva/upload"
					enctype="multipart/form-data" id="formUpload">
					<div class="panel panel-primary">
						<div class="panel-heading">Archivo</div>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label for="exampleInputFile">Selecciona el archivo:</label> <input
								type="file" name="file">
						</div>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<select name="id_tipoPago" class="form-control" id="id_status" required="required">
									  	<option value="1">Ingreso </option>
										<option value="2">Egreso</option>
										<option value="71694">Complemento de Pago</option>
							</select>
						</div>
					</div>
					<div class="panel-footer text-right">
						<input type="submit" value="Facturar" />
					</div>
				</form:form>
			</div>
		</div>
		<div class="modal fade" id="loading" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">
							<img alt="Procesando" src="${img}/file/loading.gif" style="height: 32px">
							Procesando
						</h4>
					</div>
					<div class="modal-body">Por favor espere a que se procese el archivo</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
	<script type="text/javascript">
	$("#fileRequest").click(function() {
	    window.location = 'file.doc';
	});</script>
</body>
</html>