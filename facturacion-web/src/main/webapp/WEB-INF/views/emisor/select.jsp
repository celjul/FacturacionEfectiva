<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />
<c:set var="css" value="${rsrc}/css" />
<c:set var="js" value="${rsrc}/js" />
<c:set var="img" value="${rsrc}/img" />

<script type="text/javascript">
$(function() {
	var view = new EmisorSelectViewModel();

    ko.applyBindings(view);
});
</script>

<body>
	<div  id="page-wrapper" style="margin: 0 0 0 0">
		<sec:authorize access="!hasRole('Webmaster')">
			<c:if test="${empty user.emisores}">
				<script type="text/javascript">
						window.location="${contextPath}/app/wizard/inicio";
					</script>
			</c:if>
		</sec:authorize>
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Bienvenido, seleccione el emisor para
							ingresar</h3>
					</div>
					<div class="panel-body">
						<form role="form" id="form-emisor"
							action="${contextPath}/app/emisores/selected" method="get"
							novalidate="novalidate">
							<fieldset>
								<div class="col-md-12 col-xs-12">
									<div class="form-group">
										<label class="required">Emisor <img data-toggle="modal" class="ayuda" data-target="#myModal" src="${rsrc}/img/icon/interrogacion.png" width="20px" height="20px" alt=""/></label> <select
											class="form-control" name="emisor" id="emisor"
											required="required"
											data-bind="options: $root.emisores, 
	                                                     optionsCaption : '- Seleccione una opción -', 
	                                                     value: emisor, 
	                                                     optionsText: 'nombreCompleto', 
	                                                     optionsValue:'id'">
										</select>
									</div>
								</div>
								<div class="col-md-4 col-md-offset-4 col-xs-11">
									<button type="submit" id="btn-sel-emisor"
										class="btn btn-md btn-success btn-block">Seleccionar</button>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>





	<jsp:include page="/WEB-INF/decorators/short-menu.jsp" />




	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Servicio de ayuda.</h4>
				</div>
				<div class="modal-body">
					<label>Para poder trabajar con algun emisor tienes que darlo de alta previamente.</label>
				</div>
				<div class="modal-footer">
				</div>
			</div>

		</div>
	</div>


</body>



