<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Lista de Usuarios</title>
<script src="${js}/app/usuarios/usuarios.list.app.js?version=${project.version}"></script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Lista de Usuarios Registrados</h3>
            </div>
        </div>
		<div class="row">
			<div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Usuarios
                    </div>
                    <div class="panel-body">
						<div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="table-list-emisor">
                            	<thead>
									<tr>
										<th class="text-center">Usuario</th>
										<th class="text-center">Nombre</th>
<!-- 										<th class="text-center">Apellido Paterno</th> -->
<!-- 										<th class="text-center">Apellido Materno</th> -->
										<th class="text-center">Activo</th>
										<th class="text-center"></th>
									</tr>
								</thead>
								<tbody data-bind="foreach: usuarios">
									<tr>
										<td data-bind="text: login"></td>
										<td data-bind="text: nombre"></td>
<!-- 										<td data-bind="text: apellidoPaterno"></td> -->
<!-- 										<td data-bind="text: apellidoMaterno"></td> -->
										<td data-bind="text: activo ? 'Si' : 'No'"></td>
										<td class="text-center">
											<a class="btn btn-info" data-bind="click: $root.cambiarEstatus, text: activo ? 'Deshabilitar': 'Habilitar'"></a>
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