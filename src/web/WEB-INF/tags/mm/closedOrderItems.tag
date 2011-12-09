<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

	<c:set var="checkinDetailAttributes" value="${DataDictionary.CheckinDetail.attributes}" />
	<c:set var="warehouseAttributes" value="${DataDictionary.Warehouse.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
	
	<kul:tab tabTitle="Closed Items" defaultOpen="true" >

  					<div class="tab-container" align=center>	     
		<h3>Closed Order Line(s)</h3>
      <table cellpadding="0" cellspacing="0" class="datatable" title="Stock Item(s)" summary="Stock Item(s)">
		<tr class="odd">
            
            <kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.distributorNbr}"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${warehouseAttributes.warehouseNme}"/>
			<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.acceptedItemQty}"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.binDisDesc}"/>
           <c:if test="${(KualiForm.document.rentalsCheckedin)}">
            <kul:htmlAttributeHeaderCell literalLabel="Rentals"/>
            </c:if>    
            <kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.rejectedItemQty}" />           
		</tr>
		 	<logic:iterate id="cdetail" name="KualiForm" property="document.checkinDetails" indexId="sctr">
		 	<c:if test="${(KualiForm.document.checkinDetails[sctr].orderDetail.orderLineComplete)}">
							<tr class="odd">
				<td rowspan="1" valign="top">				
				<center>
				<kul:htmlControlAttribute attributeEntry="${stockAttributes.distributorNbr}" property="document.checkinDetails[${sctr}].stock.stockDistributorNbr" readOnly="true"/>
				</center>
				</td>
				<td rowspan="1" valign="top">				
				<center>
				<kul:htmlControlAttribute attributeEntry="${warehouseAttributes.warehouseNme}" property="document.checkinDetails[${sctr}].bin.zone.warehouse.warehouseNme" readOnly="true"/>
				</center>
				</td>
				
					<td class="grid"><center>
						<kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="document.checkinDetails[${sctr}].acceptedItemQty" readOnly="true"/>
							</center>
					</td>
					<td class="grid">
					<center>
               <kul:htmlControlAttribute attributeEntry="${binAttributes.binNbr}" property="document.checkinDetails[${sctr}].binZoneDesc" readOnly="true"/>
             		</center>
					</td>
					<c:if test="${(KualiForm.document.rentalsCheckedin)}">
					<td class="grid">
					<center>
              		<c:out value="${KualiForm.document.checkinDetails[sctr].rentalsForDisplay}" />
	       			</center>
					</td>	
					</c:if>
					<td class="grid">
					<center><kul:htmlControlAttribute
						attributeEntry="${checkinDetailAttributes.rejectedItemQty}"
						property="document.checkinDetails[${sctr}].rejectedItemQty"
						readOnly="true" /></center>
					</td>
				</tr>
				</c:if>
				</logic:iterate>
				
						</table>
					</div>
        </kul:tab>