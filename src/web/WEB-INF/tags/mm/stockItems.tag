<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
<%@ attribute name="displayMismatchedItems" required="true"%>
<%@ attribute name="tabTitle" required="true"%>
<%@ attribute name="tabErrorKeys" required="true"%>
<c:set var="markupTypeAttributes" value="${DataDictionary.MarkupType.attributes}" />
<c:set var="warehouseAttributes" value="${DataDictionary.Warehouse.attributes}" />
<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />
<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
<c:set var="worksheetCounterAttributes" value="${DataDictionary.WorksheetCounter.attributes}" />
<c:set var="worksheetCountAttributes" value="${DataDictionary.WorksheetCountDocumentLookable.attributes}" />
<c:set var="stockCountAttributes" value="${DataDictionary.StockCount.attributes}" />
<c:set var="readOnly" value="${!KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
<kul:tab tabTitle="${tabTitle}" defaultOpen="true" tabErrorKey="${tabErrorKeys}">
	<div class="tab-container" align=center>
	<h3>
	<c:out value="${tabTitle}" />
	</h3>
	<table cellpadding="0" cellspacing="0" class="datatable" title="Cycle Count Entry" summary="Cycle Count Entry">
	<c:if test="${KualiForm.documentInMyActionList}">
		<tr>
		<kul:htmlAttributeHeaderCell literalLabel="&nbsp;" />
		<kul:htmlAttributeHeaderCell attributeEntry="${warehouseAttributes.warehouseCd}" />
		<kul:htmlAttributeHeaderCell literalLabel="Times Counted" />
		<kul:htmlAttributeHeaderCell attributeEntry="${zoneAttributes.zoneCd}" />
		<kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.binDisDesc}" />
		<kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.distributorNbr}" />
		<kul:htmlAttributeHeaderCell hideRequiredAsterisk="true" attributeEntry="${stockCountAttributes.stockCountItemQty}" />
		<c:if test="${displayMismatchedItems && !readOnly}">
			<kul:htmlAttributeHeaderCell hideRequiredAsterisk="true" attributeEntry="${stockCountAttributes.beforeItemQty}" />
			<kul:htmlAttributeHeaderCell literalLabel="Variance" />
			<kul:htmlAttributeHeaderCell literalLabel="Variance Cost" />
			<kul:htmlAttributeHeaderCell literalLabel="OverRide" />
			<kul:htmlAttributeHeaderCell literalLabel="Reprint" />
		</c:if>
		<c:if test="${!displayMismatchedItems}">
			<kul:htmlAttributeHeaderCell literalLabel="Reason" />
			<kul:htmlAttributeHeaderCell literalLabel="Diff" />
		</c:if>
		</tr>
		<c:set var="index" value="${0}" />
		<logic:iterate id="stockCounts" name="KualiForm" property="document.stockCounts" indexId="sctr">
			<c:set var="itemMathcing" value="${stockCounts.itemCountMatching || stockCounts.reprinted }" />
			<c:if test="${(itemMathcing && !displayMismatchedItems) ||
		   (!itemMathcing && displayMismatchedItems)}">
				<tr>
				<th>
				<c:out value="${index+1}" />:
				</th>
				<td class="grid" align="center">
				<kul:htmlControlAttribute attributeEntry="${warhouseAttributes.warehouseCd}" property="document.warehouseCd" readOnly="true" />
				</td>
				<td class="grid" align="center">
				<kul:htmlControlAttribute attributeEntry="${stockAttributes.distributorNbr}" property="document.stockCounts[${sctr}].timesCounted" readOnly="true" />
				</td>
				<td class="grid" align="center">
				<kul:htmlControlAttribute attributeEntry="${zoneAttributes.zoneCd}" property="document.stockCounts[${sctr}].bin.zone.zoneCd" readOnly="true" />
				</td>
				<td class="grid" align="center">
				<kul:htmlControlAttribute attributeEntry="${binAttributes.binDisDesc}" property="document.stockCounts[${sctr}].bin.binDisDesc" readOnly="true" />
				</td>
				<td class="grid" align="center">
				<kul:htmlControlAttribute attributeEntry="${stockAttributes.distributorNbr}" property="document.stockCounts[${sctr}].stock.stockDistributorNbr" readOnly="true" />
				</td>
				<td class="grid" align="center">
				<kul:htmlControlAttribute attributeEntry="${stockCountAttributes.stockCountItemQty}" property="document.stockCounts[${sctr}].stockCountItemQty" readOnly="true" />
				</td>
				<c:if test="${displayMismatchedItems && !readOnly}">
					<td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${stockCountAttributes.beforeItemQty}" property="document.stockCounts[${sctr}].beforeItemQty" readOnly="true" />
					</td>
					<td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${stockCountAttributes.binDisDesc}" property="document.stockCounts[${sctr}].variance" readOnly="true" />
					</td>
					<td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${stockCountAttributes.binDisDesc}" property="document.stockCounts[${sctr}].varianceCost" readOnly="true" />
					</td>
					<td class="grid" align="center">
					<kul:htmlControlAttribute forceRequired="true" property="document.stockCounts[${sctr}].stockTransReasonCd" attributeEntry="${stockCountAttributes.stockTransReasonCd}" readOnly="${readOnly}" />
					</td>
					<td class="grid" align="center">
					<kul:htmlControlAttribute forceRequired="true" property="document.stockCounts[${sctr}].markedForReprint" attributeEntry="${stockCountAttributes.reprinted}" readOnly="false" />
					</td>
				</c:if>
				<c:if test="${!displayMismatchedItems}">
					<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.stockCounts[${sctr}].stockTransReasonForDisplay" attributeEntry="${stockCountAttributes.stockTransReasonCd}" readOnly="true" />
					</td>
					<td class="grid" align="center">
					<div id="${stockCounts.stockCountId}"><c:if test="${!readOnly and stockCounts.mismatchCount != 0}">
						<c:set var="prfx" value="" />
						<c:if test="${stockCounts.mismatchCount > 0}">
							<c:set var="prfx" value="+" />
						</c:if>
						<a target="${stockCounts.stockId}" href="stockHistoryLookup.do?stockId=${stockCounts.stockId}">${prfx}${stockCounts.mismatchCount}</a>
					</c:if> &nbsp;</div>
					</td>
				</c:if>
				</tr>
				<tr>
				<kul:htmlAttributeHeaderCell literalLabel="&nbsp;" />
				<kul:htmlAttributeHeaderCell literalLabel="Note" />
				<td class="grid" align="center" colspan="9">
				<kul:htmlControlAttribute attributeEntry="${stockCountAttributes.stockCountNote}" property="document.stockCounts[${sctr}].stockCountNote" readOnly="${!displayMismatchedItems}" />
				</td>
				</tr>
				<c:set var="index" value="${index+1}" />
			</c:if>
		</logic:iterate>
	</c:if>
	</table>
	</div>
</kul:tab>