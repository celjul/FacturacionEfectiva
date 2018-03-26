<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${param.action eq 'editar'}">
    <c:set var="disabled" value="disabled='disabled'"/>
</c:if>
<script>

    function validarNumero(e) {
        var key;
        if (window.event) {
            key = e.keyCode;
        }
        else if (e.which) {
            key = e.which;
        }

        if (key < 48 || key > 57) {
            if (key == 46 || key == 8) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }
</script>
<form id="form-serie" role="form" novalidate="novalidate">
    <fieldset>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <div>
                        <label class="required">Nombre Serie:</label>
                    </div>
                    <input id="text-nombreSerie"
                           type="text" name="text-nombreSerie" class="form-control"
                           minlength="1" maxlength="255"
                           required="required" data-bind="value: $root.serie.nombre"/>
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label class="required">N° de Folio:</label> <input id="text-numeroFolio"
                                                                        onkeypress="javascript:return validarNumero(event)"
                                                                        type="text" name="text-numeroFolio"
                                                                        class="form-control"
                                                                        minlength="1"
                                                                        maxlength="10"
                                                                        required="required"
                                                                        data-bind="value: $root.serie.siguienteFolio"/>
                </div>
            </div>
        </div>
    </fieldset>
</form>