	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rsrc" value="${contextPath}/resources" />

<c:set var="img" value="${rsrc}/img" />

<head>
	<title>Detalle de comprobante</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/facturas/facturas.css">
	<style type="text/css">
	.div-sumary {
		width: 93%;
	}
	
	div.sumary {
		margin-top: 10px;
	}
	
	.form-bg {
		height: 600px;
	}
	</style>
</head>
<body>
	
	<div class="form-bg trescomulmas">
		<form id="frmComprobante" action="#">
			<h2>Comprobante Fiscal Digital <span data-bind="text: comprobante.id"></span> </h2>
			<div>
			<p><label for="cboTipoDocumento"><span class="required">* </span>Documento: </label>
			<select id="cboTipoDocumento" disabled="disabled">
				<option value="ingreso" ${comprobante.tipo eq 'ingreso' ? 'selected' : '' }>Ingreso</option>
				<option value="egreso" ${comprobante.tipo eq 'egreso' ? 'selected' : '' }>Engreso</option>
			</select> </p>
			<p><label for="txtCliente">Emisor: </label><input id="txtCliente" type="text" readonly="readonly" value="${comprobante.emisor }"/></p>
			<fmt:formatDate pattern="dd-MM-yyyy" value="${comprobante.fecha }" var="dateFormat"/>
			<p><label>Fecha de Emisión: </label><input type="text" readonly="readonly" value="${dateFormat}"/></p>
			</div>
			<div>
			<p><label for="txtCondicionPago">UUID: </label><input id="txtCondicionPago" type="text" value="${comprobante.uuid }" readonly="readonly"/></p>
			<p><label><span class="required">* </span>R.F.C.: </label>
			<input type="text" readonly="readonly" value="${comprobante.rfc}"/>
			</p>
			</div>
			
			<hr />
			
			<div style="height: 220px; overflow-y: scroll;">
			<table>
				<thead>
					<tr>
						<th width="6%">Cant.</th>
						<th width="8%">U. de M.</th>
						<th width="10%">Código</th>
						<th>Descripción<div id="promptPartidas"></div> </th>
						<th width="10%">Precio <br />Unitario</th>
						<th width="10%">Importe</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${comprobante.conceptos}" var="concepto">
					<tr class="partida">
						<td><input type="text" class="numeric cantidad partidas" value="${concepto.cantidad }" readonly="readonly"/></td>
						<td><input type="text" class="partidas" value="${concepto.unidadDeMedida }" readonly="readonly"/></td>
						<td><input type="text" class="partidas" value="${concepto.codigo }" readonly="readonly"/></td>
						<td><input type="text" class="partidas" value="${concepto.descripcion }" readonly="readonly"/></td>
						<td><input type="text" class="currency partidas" value="${concepto.precioUnitario }" readonly="readonly"/></td>
						<td><input type="text" class="currency partidas"  value="${concepto.importe }" readonly="readonly"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>

				</div>
			

				<div class=sumary>
			
			<div class="div-sumary text-right">
				<p class="sumary"><label>Total Imp. Retenidos:</label> <input type="text" class="sumary currency" readonly="readonly" value="${comprobante.impuestoRetenido }"/></p>
				<p class="sumary"><label>Total Imp. Trasladados:</label> <input type="text" class="sumary currency" readonly="readonly" value="${comprobante.impuestoTrasladado }"/></p>
				<p class="sumary"><label>Total:</label> <input type="text" class="sumary currency" readonly="readonly" value="${comprobante.total }"/></p>
			</div>
			
			
			</div>
		</form>
	</div>
	
	<form action="${pageContext.request.contextPath}/app/comprobantes/timbrar" method="POST" id="frmTimbrarComprobante" style="display: none;">
		<input name="id" id="idComprobante" value=""/>
	</form>
</body>
</html>