<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="rsrc" value="${contextPath}/resources"/>
<c:set var="css" value="${rsrc}/css"/>
<c:set var="js" value="${rsrc}/js"/>
<c:set var="img" value="${rsrc}/img"/>


<link href="${css}/jquery.ui.theme/jquery-ui.css" rel="stylesheet">
<link href="${css}/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="${css}/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="${css}/sb-admin-2/sb-admin-2.css" rel="stylesheet">
<link href="${css}/toastr/toast.css" rel="stylesheet">
<link href="${css}/font-awesome/font-awesome.min.css" rel="stylesheet"
      type="text/css">

<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Registro de Usuario</title>

    <script src="${js}/app/usuarios/newregistro.js"></script>
    <script src="${js}/jquery/jquery.min.js"></script>
    <script src="${js}/jquery/jquery.validate.js"></script>
    <script src="${js}/jquery/jquery.form.js"></script>
    <script src="${js}/jquery/jquery.blockui.js"></script>
    <script src="${js}/jquery/localization/messages_es.js"></script>
    <script src="${js}/jquery/jquery.validate.custom.js"></script>
    <script src="${js}/jquery/additional-methods.js"></script>
    <script src="${js}/jquery/jquery-ui-1.10.3.custom.js"></script>
    <script src="${js}/jquery/jquery-ui-datepicker-es.js" charset="utf8"></script>
    <script src="${js}/knockout/knockout-3.3.0.js"></script>
    <script src="${js}/bootstrap/bootstrap.min.js"></script>
    <script src="${js}/bootstrap/bootbox.min.js"></script>
    <script src="${js}/toastr/toastr.js"></script>
    <script src="${js}/metisMenu/metisMenu.min.js"></script>
    <script src="${js}/sb-admin-2/sb-admin-2.js"></script>
    <script src="${js}/utils/string-utils.js"></script>
    <script src="${js}/jquery/localization/messages_es.js"></script>
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
    <script>
        var contextPath = "${pageContext.request.contextPath}";
        $(function () {
            var modelView = new ClienteViewModel({
                // 			self.roles : ${roles}
            })
            ko.applyBindings(modelView);
        });
    </script>
    <script type="text/javascript">
        var RecaptchaOptions = {
            theme: 'clean'
        };
    </script>
</head>
<body>
<div class="cont container">
    <div class="row">
        <div class="col-lg-10 col-md-11 col-xs-7"><img src="${img}/LogoFactEfectiva.png" alt="" style="margin-top: 1%;">
        </div>
        <div class="col-lg-2 col-xs-6">
            <button style="margin-top: 2%" type="button" class="btn btn-info  btn-sm"
                    onclick="window.location.href='/'">Iniciar Sesion
            </button>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 "></div>
        <div class="col-md-6 col-xs-12" style="text-align: center">

            <form id="form-registro" role="form" novalidate="novalidate" action="#" method="post">

                <div class="row">
                    <div class="col-xs-12"><h3 style=" text-shadow: 1px 1px #1F78CB;">Resgitrate y obten tu prueba gratis.</h3></div>

                    <div class="col-xs-12"><input
                            id="txtNombre" name="txtNombre" type="text"
                            placeholder="Nombre(s)" maxlength="255" required="required"
                            class=" form-control"
                            data-bind="value: usuarioCliente.nombre"/>
                    </div>

                    <div class="col-xs-12">

                        <input id="txtApellidoPaterno"
                               name="txtApellidoPaterno" type="text"
                               placeholder="Apellido Paterno"
                               class=" form-control"
                               maxlength="255" required="required"
                               data-bind="value: usuarioCliente.apellidoPaterno"/>
                    </div>
                    <div class="col-xs-12">

                        <input
                                id="txtApellidoMaterno" name="txtApellidoMaterno"
                                type="text" placeholder="Apellido Materno" maxlength="255"
                                class=" form-control"
                                data-bind="value: usuarioCliente.apellidoMaterno"/>


                    </div>
                    <c:if test="${empty param.tipo}">
                        <div class="col-xs-12">

                            <input
                                    id="txtLogin" name="txtLogin" type="email"
                                    placeholder="nombre@dominio.com" maxlength="255"
                                    minlenght="8" required="required" class="form-control"
                                    data-bind="value: usuarioCliente.login"/>
                        </div>

                        <div class="col-xs-12">
                            <input id="txtPassword" name="txtPassword" type="password"
                                   placeholder="Password" maxlength="20" minlenght="8"
                                   required="required" class="form-control form-control"
                                   data-bind="value: usuarioCliente.password"/>
                        </div>

                        <div class="col-xs-12">
                            <input id="txtConfirmPassword"
                                   name="txtConfirmPassword" type="password"
                                   class="form-control form-control" maxlength="20"
                                   placeholder="Confirmar Password"
                                   required="required"
                                   equalTo="#txtPassword"/>

                        </div>

                        <div class="col-xs-12">
                            <input id="txtTelefono" name="txtTelefono" type="text"
                                   onkeypress="javascript:return validarNumero(event)"
                                   placeholder="Teléfono" class="form-control"
                                   required="required" maxlength="10"
                                   data-bind="value: usuarioCliente.telefono"/>

                        </div>
                        <div class="col-xs-12">
                            <input id="txtCodigoPromocional"
                                   name="txtCodigoPromocional"
                                   type="text"
                                   placeholder="Código Promocional"
                                   class="form-control"
                                   maxlength="4"
                                   data-bind="value: usuarioCliente.codigo"/>

                        </div>
                    </c:if>
                    <div class="col-xs-12">
                        <!-- Perfil de Recaptcha para DESARROLLO -->
                        <script type="text/javascript"
                                src="http://www.google.com/recaptcha/api/challenge?k=6Lei0g0TAAAAAM8b2lteVdJ5teh0a14GOHtE0PTd"></script>
                        <noscript>
                            <iframe
                                    src="http://www.google.com/recaptcha/api/noscript?k=6Lei0g0TAAAAAM8b2lteVdJ5teh0a14GOHtE0PTd"
                                    height="300" width="500" frameborder="0"> </iframe>
                            <br>
                            <textarea name="recaptcha_challenge_field" rows="3" cols="40"></textarea>
                            <input type="hidden" name="recaptcha_response_field"
                                   value="manual_challenge">
                        </noscript>

                        <div class="col-xs-12">
                            <br> <input type="checkbox" name="terminos"
                                        value="acepto" required="required"> Acepto haber leído los <a
                                href="http://www.facturacionefectiva.com/avisodeprivacidad">Términos y
                            Condiciones.</a><br>
                            <br>
                            <button type="button" class="btn btn-info"
                                    data-bind="click: guardar">Registrarse
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
