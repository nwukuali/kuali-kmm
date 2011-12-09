
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

	<c:set var="checkinDetailAttributes" value="${DataDictionary.CheckinDetail.attributes}" />
	<c:set var="unitOfIssueAttributes" value="${DataDictionary.UnitOfIssue.attributes}" />
	<c:set var="orderStatusAttributes" value="${DataDictionary.OrderStatus.attributes}" />
	<c:set var="returnStatusCodeAttributes" value="${DataDictionary.ReturnStatusCode.attributes}" />
	<c:set var="returnDetailAttributes" value="${DataDictionary.ReturnDetail.attributes}" />
	<c:set var="orderDetailAttributes" value="${DataDictionary.OrderDetail.attributes}" />
	<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
	
			<kul:tab tabTitle="Items Not on the PO" defaultOpen="true" tabErrorKey="newOrderDetailVo*">
		         <c:if test="${empty KualiForm.document.selectedOrderDetailId }">
	<div class="tab-container" align=center>	       
				<h3>Item(s) not on the PO : 
		 </h3>
      <table cellpadding="0" class="datatable" title="Stock Item(s)" summary="Stock Item(s)">
		<tr class="odd">
			<kul:htmlAttributeHeaderCell literalLabel="&nbsp;"/>
            <kul:htmlAttributeHeaderCell forceRequired = "true" attributeEntry="${checkinDetailAttributes.poId}"/>     
            <kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.manufacturerNbr}"/> 
			<kul:htmlAttributeHeaderCell forceRequired = "true" attributeEntry="${stockAttributes.stockDistributorNbr}"/>
			<kul:htmlAttributeHeaderCell forceRequired = "true" attributeEntry="${checkinDetailAttributes.acceptedItemQty}"/>
			<kul:htmlAttributeHeaderCell literalLabel = "Unit Of Issue"/>
            <kul:htmlAttributeHeaderCell forceRequired = "true" attributeEntry="${binAttributes.binDisDesc}"/>
			<kul:htmlAttributeHeaderCell forceRequired = "true" attributeEntry="${orderDetailAttributes.orderStatusCd}"/>
			<kul:htmlAttributeHeaderCell literalLabel="Action"/>
		</tr>
           <tr>
		   <kul:htmlAttributeHeaderCell literalLabel="add:" scope="row"/>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.poId}" property="newOrderDetailVo.poNumber" readOnly="${pageReadOnly}"/>
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${stockAttributes.manufacturerNbr}" property="newOrderDetailVo.manufacturerNumber" readOnly="${true}"/>
                </td>
                <td class="grid" align="center">
					<mm:lookupControlAttribute attributeEntry="${stockAttributes.stockDistributorNbr}" property="newOrderDetailVo.itemNumber"  readOnly="${true}"/>
					<kul:lookup boClassName="org.kuali.ext.mm.businessobject.CatalogItem"   
               		fieldConversions="distributorNbr:newOrderDetailVo.itemNumber,manufacturerNbr:newOrderDetailVo.manufacturerNumber,catalogUnitOfIssueCd:newOrderDetailVo.unitOfIssueCode" />					
                </td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="newOrderDetailVo.acceptedItemQuantityVal" readOnly="${pageReadOnly}"/>
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${unitOfIssueAttributes.unitOfIssueCodeLookable}" property="newOrderDetailVo.unitOfIssueCodeDesc" readOnly="${true}"/>
                </td>
                <td class="grid" align="center">
                	<mm:lookupControlAttribute attributeEntry="${checkinDetailAttributes.binZoneDesc}" property="newOrderDetailVo.binZoneDesc" readOnly="true"/>
               <kul:lookup autoSearch="yes" boClassName="org.kuali.ext.mm.businessobject.BinLookable" lookupParameters="newOrderDetailVo.itemNumber:stockBalance.stock.stockDistributorNbr"  
               		fieldConversions="binDisDesc:newOrderDetailVo.binZoneDesc,zone.zoneCd:newOrderDetailVo.zoneCode,binNbr:newOrderDetailVo.binNumber,shelfId:newOrderDetailVo.shelfId,shelfIdNbr:newOrderDetailVo.shelfIdNumber,binId:newOrderDetailVo.binId" />
                </td>
                <td class="grid" align="center">
                	<kul:htmlControlAttribute attributeEntry="${orderStatusAttributes.orderStatusCodeLookable}" property="newOrderDetailVo.reasonCode" readOnly="${pageReadOnly}"/>
                </td>
                <td class="grid" align="center">
                	<div align="center">
                		<html:image property="methodToCall.addPoOrderDetail" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" title="add" alt="add" styleClass="tinybutton"/>
                	</div>
                </td>
            </tr>
  
        <logic:iterate id="poDetailObj" name="KualiForm" property="poOrderDetails" indexId="ctrss">
            <tr>
                <th>
					<c:out value="${ctrss+1}" />:
				</th>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.poId}" property="poOrderDetails[${ctrss}].poNumber" readOnly="true"/>
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${stockAttributes.manufacturerNbr}" property="poOrderDetails[${ctrss}].manufacturerNumber"  readOnly="true"/>
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${stockAttributes.stockDistributorNbr}" property="poOrderDetails[${ctrss}].itemNumber"  readOnly="true"/>
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="poOrderDetails[${ctrss}].acceptedItemQuantity" readOnly="true"/>
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.returnUnitOfIssueOfCode}" property="poOrderDetails[${ctrss}].unitOfIssue.unitOfIssueDesc" readOnly="true"/>
                </td>
                <td class="grid" align="center">
                	<kul:htmlControlAttribute attributeEntry="${binAttributes.binDisDesc}" property="poOrderDetails[${ctrss}].binZoneDesc" readOnly="true"/>
                </td>
                <td class="grid" align="center">
                	<kul:htmlControlAttribute attributeEntry="${orderStatusAttributes.orderStatusCodeLookable}" property="poOrderDetails[${ctrss}].orderStatus.orderStatusDesc" readOnly="true"/>
                </td>
                  <td class="datacell">
                  	<div align="center">
                  		<html:image property="methodToCall.deletePoOrderDetail.line${ctrss}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif" title="Delete a Po" alt="Delete a Po" styleClass="tinybutton"/>
                  	</div>
                  </td>               
            </tr>
        </logic:iterate>
        </table>
	</div>
	         </c:if>
	         </kul:tab>