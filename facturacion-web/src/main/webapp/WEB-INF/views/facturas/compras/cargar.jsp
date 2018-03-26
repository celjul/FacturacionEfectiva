<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cargar comprobante</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/certificados/certificados.css">
</head>
<body>
	<div class="form-bg doscolummas">
		<form id="frmCertificado" method="post" enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/app/comprobantes/send">

			<h2>Cargar Comprobante</h2>
			<p>
				<label for="fileComprobante"><span class="required">*
				</span>Comprobante: </label><input id="fileComprobante" name="fileComprobante"
					type="file"
					data-validation-engine="validate[required, fileExt[xml]]" />
			</p>
			<hr />

			<button type="submit" class="right-align" id="btnNewComprobante">Cargar Certificado</button>

		</form>
	</div>

</body>
</html>