<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Lista de Productos</title>
<script src="${js}/app/productos/productos.list.app.js?version=${project.version}"></script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Lista de Productos Registrados</h3>
            </div>
        </div>
		<div class="row">
			<div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Productos
                    </div>
                    <div class="panel-body">
						<div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="table-list-emisor">
                            	<thead>
									<tr>
<!-- 										<th class="text-center">Id</th> -->
										<th class="text-center">Código</th>
										<th class="text-center">Nombre</th>
										<th class="text-center">Unidad de Medida</th>
										<th class="text-center">Precio Sin IVA</th>
										<th class="text-center">Precio Con IVA</th>
										<th class="text-center"></th>
									</tr>
								</thead>
								<tbody data-bind="foreach: productos">
									<tr>
<!-- 										<td data-bind="text: id"></td> -->
										<td data-bind="text: codigo"></td>
										<td data-bind="text: nombre"></td>
										<td data-bind="text: unidadDeMedida.descripcion"></td>
										<td data-bind="text: precio"></td>
										<td data-bind="text: !exentoIVA ? precio * 1.16: precio"></td>
										<td class="text-center">
											<a class="btn btn-info" data-bind="attr: {href: String.format('{0}/app/productos/{1}', contextPath, id)}">Editar</a>
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