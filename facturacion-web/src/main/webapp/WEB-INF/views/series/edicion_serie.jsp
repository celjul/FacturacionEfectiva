<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="rsrc" value="${contextPath}/resources"/>
<c:set var="css" value="${rsrc}/css"/>
<c:set var="js" value="${rsrc}/js"/>
<c:set var="img" value="${rsrc}/img"/>

<html>
<head>
    <title>Actualización de una Serie</title>

    <script type="text/javascript">
        $(function () {
            var data = {};
            if (window.series) {
                data.series = series;
            }
            var modelView = new SeriesViewModel(${serie})
            ko.applyBindings(modelView);
        });
    </script>

    <script src="${js}/app/series/series.js"></script>
    <script src="${js}/app/series/series.app.js?version=${project.version}"></script>

</head>
<body>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header">Actualizar Serie</h3>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Datos de la Serie
                </div>
                <div class="panel-body">
                    <jsp:include page="serie.jsp">
                        <jsp:param value="alta" name="action"/>
                    </jsp:include>
                </div>
                <div class="panel-footer text-right">
                    <button type="button" class="btn btn-info" data-bind="click: guardar">Guardar</button>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/decorators/menu.jsp"/>
</body>
</html>