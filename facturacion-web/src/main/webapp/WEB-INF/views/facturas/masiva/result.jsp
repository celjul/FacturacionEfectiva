<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Facturación Masiva</title>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-md-12">
				<h3 class="page-header">Resultado Facturaci&oacute;n Masiva</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<form:form method="POST" target="_blank" action="${contextPath}/app/facturacion/masiva/download">
						<div class="panel panel-primary">
							<div class="panel-heading">Resultado</div>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<table class="table table-condensed">
									<thead>
										<tr>
											<th class="col-md-2">Folio en CSV</th>
											<th class="col-md-3">Resultado</th>
											<th class="col-md-5">Observaciones</th>
											<th class="col-md-1">PDF</th>
											<th class="col-md-1">XML</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${facturas}" var="factura">
											<tr class="${factura.exitosa ? 'success' : 'danger'}">
												<td>${factura.numero}</td>
												<td>
													<c:choose>
														<c:when test="${factura.exitosa}">
															<input type="hidden" name="ids" value="${factura.id}" />
															Exito
														</c:when>
														<c:otherwise>
															Error
														</c:otherwise>
													</c:choose>
												</td>
												<td>${factura.observaciones}</td>
												<td>
													<c:if test="${factura.exitosa}">
														<a target="_blank" href="${contextPath}/app/comprobantes/${factura.id}/file/pdf">
															<img alt="Descargar PDF" src="${img}/file/file-pdf.png" />
														</a>
													</c:if>
												</td>
												<td>
													<c:if test="${factura.exitosa}">
														<a target="_blank" href="${contextPath}/app/comprobantes/${factura.id}/file/xml">
															<img alt="Descargar XML" src="${img}/file/file-xml.png" />
														</a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="panel-footer text-right">
							<input type="submit" value="Descargar ZIP" />
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>