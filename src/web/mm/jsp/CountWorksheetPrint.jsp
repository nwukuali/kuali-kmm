<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
	<kul:page showDocumentInfo="false" htmlFormAction="initiateWorksheetDoc" renderMultipart="true"
	showTabButtons="false" docTitle="Create Worksheets" 
	transactionalDocument="false" headerDispatch="true" headerTabActive="true"
	sessionDocument="false" headerMenuBar="" feedbackKey="true" defaultMethodToCall="start" >
<script>
	function checkInventory(){
		if(document.getElementById('fullInventory').checked){
			document.getElementById('countFreq').value="";
			document.getElementById('countFreq').disabled='disabled';
		}else{
			document.getElementById('countFreq').disabled='';
		}
	}
</script>
	<c:set var="WorksheetDocumentAttributes" value="${DataDictionary.WorksheetCountDocumentLookable.attributes}" />
	<c:set var="CycleCountParameterAttributes" value="${DataDictionary.CycleCount.attributes}" />
	<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />


	<kul:tabTop tabTitle="Count Worksheet Print" defaultOpen="true" tabErrorKey="*">
	<!-- getting the attributes for worksheet item -->
			<div class="tab-container" align=center>				
			<h3>Count Worksheet Print</h3>		
			<table cellpadding="0" class="datatable" title="Print Worksheet" summary="Print Worksheet">
			<tr>
				<th width="50%" class="bord-l-b">
			        	<div align="right">		    
				     		<kul:htmlAttributeLabel attributeEntry="${WorksheetDocumentAttributes.warehouseCd}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="warehouseCode" attributeEntry="${WorksheetDocumentAttributes.warehouseCd}" readOnly="${readOnly}"/>
						<kul:lookup boClassName="org.kuali.ext.mm.businessobject.Warehouse" fieldConversions="warehouseCd:warehouseCode" />			
					</td>
			</tr>			
			<tr>
				<th width="50%" class="bord-l-b">
			        	<div align="right">		    
				     		<kul:htmlAttributeLabel attributeEntry="${zoneAttributes.zoneCd}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="zoneCd" attributeEntry="${zoneAttributes.zoneCd}" readOnly="${readOnly}"/>
						<kul:lookup boClassName="org.kuali.ext.mm.businessobject.Zone" lookupParameters="warehouseCode:warehouseCd,'INIT':pickStatusCode.pickStatusCode" fieldConversions="zoneCd:zoneCd,warehouseCd:warehouseCode" />			
					</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
			        	<div align="right">		    
				     		Full Inventory:	
						</div>
					</th>
					<td class="datacell-nowrap">
						<input type="checkbox" id="fullInventory" name="fullInventory" value="yes" onclick="checkInventory();">														
					</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
			        	<div align="right">		    
				     		<kul:htmlAttributeLabel attributeEntry="${CycleCountParameterAttributes.cycleCntCdLookable}" readOnly="${readOnly}"/>	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="countFreq" attributeEntry="${CycleCountParameterAttributes.cycleCntCdLookable}" readOnly="${readOnly}"/>									
					</td>
			</tr>			
			<tr>
				<th width="50%" class="bord-l-b">
			        	<div align="right">		    
				     		Copies :
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute forceRequired="true" property="copies" attributeEntry="${WorksheetDocumentAttributes.worksheetCnt}" readOnly="${readOnly}"/>	
					</td>
			</tr>		
			<tr>
				<th width="50%" class="bord-l-b">
   					<div align="right">	
				     		Exclude Empty Bins :
					</div>
					</th>
					<td class="datacell-nowrap">
						<html:checkbox styleId="quantityOnHandLessThanZero" property="quantityOnHandLessThanZero"/>
					</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
   					<div align="right">	
				     		<kul:htmlAttributeLabel attributeEntry="${WorksheetDocumentAttributes.worksheetCnt}" />				     		
					</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute forceRequired="true" property="counters" attributeEntry="${WorksheetDocumentAttributes.worksheetCnt}" readOnly="${readOnly}"/>
					</td>
			</tr>
	  </table>
	  </div>
	</kul:tabTop>
	<kul:panelFooter />
	<div id="globalbuttons" class="globalbuttons">
        <c:if test="${not readOnly}">	        
			<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" property="methodToCall.createWorksheetDocument" title="Save" alt="Save"/>
	    	<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_cancel.gif" styleClass="globalbuttons" property="methodToCall.start" title="Cancel" alt="Cancel"/>
        </c:if>		
    </div>
    <c:if test="${KualiForm.fullInventory eq 'yes'}">
	<script>
	document.getElementById('fullInventory').checked = true;
	document.getElementById('countFreq').disabled='disabled';		
	</script>
	</c:if>	
</kul:page>
