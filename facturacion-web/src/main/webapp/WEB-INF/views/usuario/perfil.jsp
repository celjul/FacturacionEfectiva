<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<html>
<head>
<title>Perfil de Usuario</title>

<script src="${js}/app/usuarios/usuarios.js?version=${project.version}"></script>
<script>
		if (window.session) {
			<c:if test="${not empty user}">
		        window.session.usuario = ${user.id}
		    </c:if>
		}
</script>
</head>
<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Datos del Perfil de Usuario</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						Datos del Usuario
					</div>
					<div class="panel-body">
						<jsp:include page="usuario.jsp" >
							<jsp:param name="tipo" value="profile" />
						</jsp:include>
					</div>
					<div class="panel-footer text-right">
						<button type="button" class="btn btn-info" data-bind="click: actualizar">Guardar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/decorators/menu.jsp" />
</body>
</html>