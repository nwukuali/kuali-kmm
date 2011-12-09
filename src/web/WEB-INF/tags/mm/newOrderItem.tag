<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

	<c:set var="stockAttributes"
		value="${DataDictionary.Stock.attributes}" />
	<c:set var="orderDetailAttributes"
		value="${DataDictionary.OrderDetail.attributes}" />
	<c:set var="additionalCostTypeAttributes"
		value="${DataDictionary.AdditionalCostType.attributes}" />
		

<kul:tab tabTitle="Add Item on This Agreement" defaultOpen="true"
	tabErrorKey="newOrderDetail*">
	<div class="tab-container" align=center>
	<h3>Add Item on This Agreement</h3>
	<table cellpadding="0"  cellspacing="0" class="datatable" title="Return Items"
		summary="Reorder Items">
	<tr>
							<kul:htmlAttributeHeaderCell forceRequired="true"
								attributeEntry="${stockAttributes.stockDistributorNbr}" />
							<kul:htmlAttributeHeaderCell forceRequired="true"
								attributeEntry="${orderDetailAttributes.orderItemQty}" />
							<kul:htmlAttributeHeaderCell forceRequired="false"
								attributeEntry="${orderDetailAttributes.stockUnitOfIssueCd}" />
							<kul:htmlAttributeHeaderCell forceRequired="false"
								attributeEntry="${orderDetailAttributes.orderItemCostAmt}" />
							<kul:htmlAttributeHeaderCell forceRequired="false"
								attributeEntry="${orderDetailAttributes.extendedCost}" />
							<kul:htmlAttributeHeaderCell 
								attributeEntry="${additionalCostTypeAttributes.additionalCostTypeLookable}" />
							<kul:htmlAttributeHeaderCell 
								attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}" />
							<kul:htmlAttributeHeaderCell
								attributeEntry="${orderDetailAttributes.totalCost}" />
							<kul:htmlAttributeHeaderCell
								attributeEntry="${orderDetailAttributes.expectedDate}" />
							<kul:htmlAttributeHeaderCell literalLabel="Add" />								
	</tr>
						<tr>
							<td class="grid" align="center">
							<c:if test="${!empty KualiForm.newOrderDetail.itemNumber}">
								<a target="_blank" href="stockHistoryLookup.do?stockId=${KualiForm.newOrderDetail.stockId}">
							</c:if>
							<mm:lookupControlAttribute
								attributeEntry="${stockAttributes.stockDistributorNbr}"
								property="newOrderDetail.itemNumber"
								readOnly="true" />
             				<c:if test="${!empty KualiForm.newOrderDetail.itemNumber}">
             					</a>
             				</c:if>					
								<kul:lookup boClassName="org.kuali.ext.mm.businessobject.CatalogItem"  
								lookupParameters="document.agreementNbr:stock.agreementNbr" 
								autoSearch="yes"
			               		fieldConversions="stock.stockDesc:newOrderDetail.orderItemDetailDesc,stock.stockPrice:newOrderDetail.orderItemCostAmt,stock.stockDistributorNbr:newOrderDetail.itemNumber,distributorNbr:newOrderDetail.distributorNumber,manufacturerNbr:newOrderDetail.manufacturerNumber,catalogItemId:newOrderDetail.catalogItemId,catalogUnitOfIssueCd:newOrderDetail.stockUnitOfIssueCd,stock.stockId:newOrderDetail.stockId" />
							</td>
							<td class="grid"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.orderItemQty}"
								property="newOrderDetail.orderItemQty"
								readOnly="false" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.stockUnitOfIssueCd}"
								property="newOrderDetail.stockUnitOfIssueCd"
								readOnly="true" />
								</td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.orderItemCostAmt}"
								property="newOrderDetail.orderItemCostAmt"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.extendedCost}"
								property="newOrderDetail.extendedCost"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${additionalCostTypeAttributes.additionalCostTypeLookable}"
								property="newOrderDetail.additionalCostTypeCode"
								readOnly="false" /></td>								
							<td  class="grid" align="center"><kul:htmlControlAttribute
								property="newOrderDetail.orderItemAdditionalCostAmt"
								attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}"
								readOnly="false" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.totalCost}"
								property="newOrderDetail.totalCost"
								readOnly="true" /></td>
							<td class="grid" align="center">
							
							<kul:dateInput attributeEntry="${orderDetailAttributes.expectedDate}" 
								property="newOrderDetail.expectedDate" />
							</td>
							<td class="grid" align="center">
							<div align="center">
							<html:image property="methodToCall.addOrderLine.line${0}"
								src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
								title="Add Returns" alt="Add Returns" styleClass="tinybutton" />
							</div>
							</td>
						</tr>
						<tr>
						<td class="grid" colspan="12" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.orderItemDetailDesc}"
								property="newOrderDetail.orderItemDetailDesc"
								readOnly="true" /></td>				
						</tr>
						<tr></tr>
	<logic:iterate id="rdetail" name="KualiForm"
		property="document.orderDetailsForReorder" indexId="indexx">
		<tr>
							<td class="grid" align="center">
								<mm:stockHistoryLookup displayLabel="${KualiForm.document.orderDetailsForReorder[indexx].catalogItem.distributorNbr}" stockId="${KualiForm.document.orderDetailsForReorder[indexx].catalogItem.stockId}" />
							</td>
							<td class="grid"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.orderItemQty}"
								property="document.orderDetailsForReorder[${indexx}].orderItemQty"
								readOnly="false" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.stockUnitOfIssueCd}"
								property="document.orderDetailsForReorder[${indexx}].stockUnitOfIssue.unitOfIssueDesc"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.orderItemCostAmt}"
								property="document.orderDetailsForReorder[${indexx}].orderItemCostAmt"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.extendedCost}"
								property="document.orderDetailsForReorder[${indexx}].extendedCost"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${additionalCostTypeAttributes.additionalCostTypeLookable}"
								property="document.orderDetailsForReorder[${indexx}].additionalCostTypeCode"
								readOnly="false" /></td>								
							<td class="grid" align="center"><kul:htmlControlAttribute
								property="document.orderDetailsForReorder[${indexx}].orderItemAdditionalCostAmt"
								attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}"
								readOnly="false" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.totalCost}"
								property="document.orderDetailsForReorder[${indexx}].totalCost"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute
								attributeEntry="${orderDetailAttributes.expectedDate}"
								property="document.orderDetailsForReorder[${indexx}].expectedDate"
								readOnly="false" /></td>
		<td  class="grid" align="center">
		<div align="center">
		<html:image property="methodToCall.deleteOrderLine.line${indexx}"
			src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif"
			title="Delete Returns" alt="Delete Returns" styleClass="tinybutton" />
		</div>
		</td>
		</tr>
		<tr>
		<td class="grid" colspan="12" align="center">
		<kul:htmlControlAttribute
			
			attributeEntry="${orderDetailAttributes.orderItemDetailDesc}"
			property="document.orderDetailsForReorder[${indexx}].orderItemDetailDesc"
			readOnly="true" />
		</td>

		</tr>

	</logic:iterate>

	</table>
	</div>
</kul:tab>