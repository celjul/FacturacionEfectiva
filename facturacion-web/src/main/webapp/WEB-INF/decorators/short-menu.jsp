<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<content tag="menu-short">
	<ul class="dropdown-menu dropdown-user">
		<li>
			<a href="${contextPath}/app/usuarios/profile">
				<i class="fa fa-user fa-fw"></i>Perfil
			</a>
		</li>
		<sec:authorize access="hasAnyRole('Webmaster','Propietario')">
		<li>
			<a id="shot-emisor" href="${contextPath}/app/emisores/new"><i class="fa fa-qrcode fa-fw"></i> Nuevo Emisor</a>
		</li>
		</sec:authorize>
		<li class="divider"></li>
		<li>
			<a href="<c:url value="/j_spring_security_logout"/>"><i class="fa fa-sign-out fa-fw"></i> Salir</a>
		</li>
	</ul>
</content>