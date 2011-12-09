<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="reviewMode" required="true" %>
	<c:set var="warehouseAttributes" value="${DataDictionary.Warehouse.attributes}" />
	<c:set var="worksheetCounterAttributes" value="${DataDictionary.WorksheetCounter.attributes}" />
	<c:set var="worksheetCountAttributes" value="${DataDictionary.WorksheetCountDocumentLookable.attributes}" />
	
	<kul:tab tabTitle="Worksheet Counters" defaultOpen="true" tabErrorKey="newWorksheetCounter*">

  		<div class="tab-container" align=center>
			<h3>
			<c:if test="${not reviewMode}"> Add/Remove  
			</c:if>Counters for Worksheet : <c:out value="${KualiForm.document.documentNumber}"/> </h3>	
		
		<table cellpadding="0" cellspacing="0" class="datatable" title="Cycle Count Entry" summary="Cycle Count Entry">
		<tr>
            <kul:htmlAttributeHeaderCell literalLabel="&nbsp;"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${warehouseAttributes.warehouseNme}"/>
            <kul:htmlAttributeHeaderCell hideRequiredAsterisk = "${reviewMode}" attributeEntry="${worksheetCounterAttributes.worksheetPrncplId}"/>
            <kul:htmlAttributeHeaderCell hideRequiredAsterisk = "${reviewMode}" attributeEntry="${worksheetCounterAttributes.lastUpdateDate}"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${worksheetCountAttributes.documentNumber}"/>
            <c:if test="${not reviewMode}">
                <kul:htmlAttributeHeaderCell literalLabel="Actions"/>
            </c:if>
		</tr>  
		
        <c:if test="${not readOnly && not reviewMode}">        
           <tr>
                <kul:htmlAttributeHeaderCell literalLabel="add:" scope="row"/>
                <c:set var="docNbr" value="${KualiForm.document.worksheetCnt}" />
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${warehouseAttributes.warehouseNme}" property="document.warehouse.warehouseNme" readOnly="true"/>
                </td>
                <td class="grid" align="center">
                	<kul:htmlControlAttribute attributeEntry="${worksheetCounterAttributes.worksheetPrncplId}"  property="newWorksheetCounter.worksheetPrncplName" />&nbsp;&nbsp;
                	<kul:lookup boClassName="org.kuali.rice.kim.bo.impl.PersonImpl" fieldConversions="principalId:newWorksheetCounter.worksheetPrncplId,principalName:newWorksheetCounter.worksheetPrncplName" />
                </td>
                <td class="grid" align="center">
                	<kul:dateInput attributeEntry="${worksheetCounterAttributes.lastUpdateDate}" property="newWorksheetCounter.lastUpdateDate" />
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${worksheetCountAttributes.documentNumber}" property="document.documentNumber" readOnly="true"/>
                </td>               
                <td class="grid" align="center">
                	<div align="center">
                		<html:image property="methodToCall.addWorkSheetCounter" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" title="Add a Worksheet counter" alt="Add a Worksheet counter" styleClass="tinybutton"/>
                	</div>
                </td>
            </tr>
        </c:if>     
  
        <logic:iterate id="worksheetCounter" name="KualiForm" property="document.worksheetCounters" indexId="ctr">
            <tr>
                <th>
					<c:out value="${ctr+1}" />:
				</th>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${warehouseAttributes.warehouseNme}" property="document.warehouse.warehouseNme" readOnly="true"/>
                </td>
                <td class="grid" align="center">
                	<kul:htmlControlAttribute attributeEntry="${worksheetCounterAttributes.worksheetPrncplId}" property="document.worksheetCounters[${ctr}].worksheetPrncplName" readOnly="true"/>&nbsp;&nbsp;                	
                </td>
                <td class="grid" align="center">
                	<kul:htmlControlAttribute attributeEntry="${worksheetCounterAttributes.lastUpdateDate}" property="document.worksheetCounters[${ctr}].lastUpdateDate" readOnly="true"/>
                </td>
                <td class="grid" align="center">
					<kul:htmlControlAttribute attributeEntry="${worksheetCountAttributes.worksheetDocNumber}" property="document.documentNumber" readOnly="true"/>
                </td>                
                <c:if test="${not reviewMode}">
                    <td class="datacell">
                    	<div align="center">
                    		<html:image property="methodToCall.deleteWorkSheetCounter.line${ctr}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif" title="Delete a Worksheet Counter" alt="Delete a Worksheet Counter" styleClass="tinybutton"/>
                    	</div>
                    </td>
                </c:if>               
            </tr>
        </logic:iterate>
        </table>
        </div>
        </kul:tab>