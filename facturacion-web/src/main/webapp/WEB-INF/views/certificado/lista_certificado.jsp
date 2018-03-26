<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Lista de Certificados</title>
<script	src="${js}/app/certificados/certificados.js?version=${project.version}"></script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Lista de Certificados Registrados</h3>
            </div>
        </div>
		<div class="row">
			<div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Cartificados
                    </div>
                    <div class="panel-body">
						<div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="table-list-clientes">
                            	<thead>
									<tr>
<!-- 										<th class="text-center">Id</th> -->
										<th class="text-center">Fecha de Inicio</th>
										<th class="text-center">Fecha de Fin</th>
										<th class="text-center">Nombre</th>
										<th class="text-center">Serie</th>
										<th class="text-center"></th>
									</tr>
								</thead>
								<tbody data-bind="foreach: certificados">
									<tr class="contenido">
<!-- 										<td class="text-center" data-bind="text: id"></td> -->
										<td class="text-center" data-bind="text: inicio"></td>
										<td class="text-center" data-bind="text: fin"></td>
										<td class="text-center" data-bind="text: nombre"></td>
										<td class="text-center" data-bind="text: serie"></td>
										<td class="text-center">
											<a class="btn btn-info" data-bind="click: $root.eliminar">Eliminar</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
    </div>
    <jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>