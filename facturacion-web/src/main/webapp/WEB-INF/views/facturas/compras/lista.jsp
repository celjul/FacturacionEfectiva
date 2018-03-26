<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
	<title>Registro de comprobante</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/facturas/facturas.css">
	<script type="text/javascript">
	$(document).ready(function() {
		$("#txtDel").datepicker();
		
		$("#txtAl").datepicker();
		
		$("#btnBuscar").button();
		
		
	});
	</script>
</head>
<body>
		<div class="form-bg trescomulmas" style="height: 800px;">
			<form id="frmComprobante" action="${pageContext.request.contextPath}/app/comprobantes/compras/lista" method="post">
				<h2>Listado de Comprobantes Subidos</h2>
				<div>
					<p><label for="txtRFC">R.F.C.: </label><input id="txtRFC" type="text" placeholder="R.F.C." name="rfc"/></p>
					<p><label for="txtEmisor">Emisor: </label><input id="txtEmisor" type="text" placeholder="Emisor" name="emisor"/></p>
					<p><label for="txtTipo">Tipo: </label>
						<select id="txtTipo" name="tipo">
							<option value="egreso">Egreso</option>
							<option value="ingreso">Ingreso</option>
						</select>
					</p>
				</div>
				
				<div>
					<p><label for="txtDel">Del: </label><input id="txtDel" name="del" type="text" placeholder="Del" /></p>
					<p><label for="txtAl">Al: </label><input id="txtAl" name="al" type="text" placeholder="Al" /></p>
					<p><input type="submit" id="btnBuscar" value="Buscar" /></p>
				</div>
				
				<div style="height: 80%; overflow-y: scroll;">
				<table>
					<thead>
						<tr>
<!-- 							<th width="5%">Id</th> -->
							<th width="10%">Fecha de<br/>Emisión</th>
							<th width="15%">RFC</th>
							<th>Emisor</th>
							<th width="10%">Retenciones</th>
							<th width="10%">Traslados</th>
							<th width="10%">Total</th>
							<th width="10%">Tipo</th>
							<th width="10%">&nbsp;</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="sumatoriaTotal" value="0" />
						<c:set var="sumatoriaRet" value="0" />
						<c:set var="sumatoriaTras" value="0" />
						<c:forEach var="comprobante" items="${comprobantes}">
							
						<tr class="partida">
							<td>${comprobante.id}</td>
							<td><fmt:formatDate pattern="dd-MM-yyyy" value="${comprobante.fecha}" /></td>
							<td>${comprobante.rfc}</td>
							<td>${fn:substring(comprobante.emisor, 0,40)}</td>
							<td style="text-align: right;"><fmt:formatNumber value="${comprobante.impuestoTrasladado}" pattern="$ #,##0.00" /></td>
							<td style="text-align: right;"><fmt:formatNumber value="${comprobante.impuestoRetenido}" pattern="$ #,##0.00" /></td>
							<td style="text-align: right;"><fmt:formatNumber value="${comprobante.total}" pattern="$ #,##0.00" /></td>
							<td>${comprobante.tipo}</td>
							<td><a class="boton" href="${pageContext.request.contextPath}/app/comprobantes/compras/read?id=${comprobante.id}">Abrir</a></td>
						</tr>
						<c:set var="sumatoriaTotal" value="${sumatoriaTotal + comprobante.total}" />
						<c:set var="sumatoriaRet" value="${sumatoriaRet + comprobante.impuestoRetenido}" />
						<c:set var="sumatoriaTras" value="${sumatoriaTras + comprobante.impuestoTrasladado}" />
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">Total</td>
							<td style="text-align: right;"><fmt:formatNumber value="${sumatoriaTras}" pattern="$ #,##0.00" /></td>
							<td style="text-align: right;"><fmt:formatNumber value="${sumatoriaRet}" pattern="$ #,##0.00" /></td>
							<td style="text-align: right;"><fmt:formatNumber value="${sumatoriaTotal}" pattern="$ #,##0.00" /></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</tfoot>
				</table>
				</div>
			</form>
		</div>
		
</body>
</html>