<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<content tag="menuAux">
<div class="sidebar-nav navbar-collapse">
	<sec:authorize access="isAuthenticated()">
		<ul class="nav" id="side-menu">
			<%--
			<li class="sidebar-search">
				<div class="input-group custom-search-form">
					<input type="text" class="form-control" placeholder="Search...">
					<span class="input-group-btn">
					<button class="btn btn-default" type="button">
						<i class="fa fa-search"></i>
					</button>
				</span>
				</div>
			</li>
			<li>
				<a href="index.html"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
			</li>
			--%>

			<li><a href="javascript:;" id="menu-comprobantes"> <i
					class="fa fa-edit fa-fw"></i> Comprobantes<span class="fa arrow"></span>
			</a>

				<ul class="nav nav-second-level">
					<li><a id="menu-comprobantes-nuevo"
						href="${contextPath}/app/comprobantes/new">Crear Comprobante</a></li>
					<li><a id="menu-comprobantes-lista"
						href="${contextPath}/app/comprobantes/list">Listado de
							Comprobantes</a></li>
					<li><a id="menu-masiva"
						href="${contextPath}/app/facturacion/masiva/">Facturaci&oacute;n Masiva</a></li>
					<li><a id="menu-cancelacion-masiva"
						href="${contextPath}/app/cancelacion/masiva/">Cancelaci&oacute;n Masiva</a></li>
				</ul></li>

			<li><a href="javascript:;" id="menu-config-prod"> <i
						class="fa fa-shopping-cart fa-fw"></i>Productos <span
						class="fa arrow"></span>
				</a>
					<ul class="nav nav-third-level">
						<li><a id="menu-config-prod-nuevo"
							   href="${contextPath}/app/productos/new">Registrar Producto</a></li>
						<li><a id="menu-config-prod-lista"
							   href="${contextPath}/app/productos/list">Listado de Productos</a></li>
					</ul></li>
			<li><a href="javascript:;" id="menu-clientes"> <i
					class="fa fa-bar-chart-o fa-fw"></i> Clientes<span class="fa arrow"></span>
			</a>
				<ul class="nav nav-second-level">
					<li><a id="menu-clientes-nuevo"
						href="${contextPath}/app/clientes/new">Registrar Cliente</a></li>
					<li><a id="menu-clientes-lista"
				x|		href="${contextPath}/app/clientes/list">Listado de Clientes</a></li>
				</ul></li>
				<li><a href="javascript:;" id="menu-series"> <i
						class="fa fa-bar-chart-o fa-fw"></i> Series<span class="fa arrow"></span>
				</a>
					<ul class="nav nav-second-level">
						<li><a id="menu-series-nuevo"
							   href="${contextPath}/app/series/new">Registrar Serie</a></li>
						<li><a id="menu-series-lista"
							   x| href="${contextPath}/app/series/list">Listado de Series</a></li>
					</ul>
				</li>
			<li><a href="javascript:;" id="menu-config"> <i
					class="fa fa-wrench fa-fw"></i> Configuraciones<span
					class="fa arrow"></span>
			</a>
				<ul class="nav nav-second-level">
					<sec:authorize access="hasAnyRole('Webmaster','Propietario')">
						<li><a href="javascript:;" id="menu-config-emisores"> <i
								class="fa fa-qrcode fa-fw"></i>Emisores <span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a id="menu-config-emisores-nuevo"
									href="${contextPath}/app/emisores/new">Registrar Emisor</a></li>
								<li><a id="menu-config-emisores-lista"
									href="${contextPath}/app/emisores/list">Listado de Emisores</a></li>
							</ul></li>
						<li><a href="javascript:;" id="menu-config-cert"> <i
								class="fa fa-file fa-fw"></i>Certificados <span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a id="menu-config-cert-new"
									href="${contextPath}/app/certificados/new">Registrar Certificado</a></li>
								<li><a href="${contextPath}/app/certificados/list">Listado de Certificados</a>
								</li>
							</ul></li>

						<li><a href="${contextPath}/app/folios/emisores" id="menu-config-fol"> <i
								class="fa fa-wrench fa-fw"></i>Asignaci&oacute;n de Timbres
						</a>
							<%--<ul class="nav nav-third-level">
								<li><a href="${contextPath}/app/folios/emisores">Lista</a>
								</li>
							</ul>--%>
						</li>

						<%--<li><a href="${contextPath}/app/emails/emisores" id="fa arrow"> <i
								class="fa fa-envelope-o"></i> Email Remitente
						</a>
						</li>--%>

						<li><a href="javascript:;" id="menu-config-usuarios"> <i
								class="fa fa-user fa-fw"></i>Usuarios <span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a id="menu-config-user-nuevo"
									href="${contextPath}/app/usuarios/new">Crear Usuario</a></li>
								<li><a id="menu-config-user-lista"
									href="${contextPath}/app/usuarios/list">Listado de Usuarios</a></li>
							</ul></li>
					</sec:authorize>
				</ul></li>
		</ul>
	</sec:authorize>
</div>
</content>
<!-- 					<li><a id="menu-comprobantes-test" -->
<!-- 						href="javascript: testPlantilla();">Preview de la Plantilla</a></li> -->
<!-- 					<li> -->
<!-- 						<a href="javascript:;" id="menu-config-fol"> -->
<!-- 							<i class="fa fa-file fa-fw"></i>Folios <span class="fa arrow"></span> -->
<!-- 						</a> -->
<!-- 						<ul class="nav nav-third-level"> -->
<!-- 							<li> -->
<%-- 								<a href="${contextPath}/app/folios/emisores">Lista</a> --%>
<!-- 							</li> -->
<!-- 						</ul> -->
<!-- 					</li> -->