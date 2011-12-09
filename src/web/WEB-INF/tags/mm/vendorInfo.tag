<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

	<c:set var="checkinDetailAttributes" value="${DataDictionary.CheckinDetail.attributes}" />
	<c:set var="unitOfIssueAttributes" value="${DataDictionary.UnitOfIssue.attributes}" />
	<c:set var="orderStatusAttributes" value="${DataDictionary.OrderStatus.attributes}" />
	<c:set var="returnStatusCodeAttributes" value="${DataDictionary.ReturnStatusCode.attributes}" />
	<c:set var="returnDetailAttributes" value="${DataDictionary.ReturnDetail.attributes}" />
	<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
	
			<kul:tab tabTitle="Vendor" defaultOpen="true" tabErrorKey="newOrderDetailVo*">
			<!-- Div for displaying vendor address ifnormation  -->
		<div class="tab-container" align=center>	       
				<h3>Vendor Address
		 </h3>
      <table cellpadding="0" class="datatable" title="Stock Item(s)" summary="Stock Item(s)">
		<tr class="odd">
		        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel="Vendor"/>
			</td>
			<td class="grid" align="center">
			</td>
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel="City"/>
			</td>
			<td class="grid" align="center">
			</td>
			</tr>
			<tr class="odd">
			        <td class="grid" align="center">			
			<kul:htmlAttributeHeaderCell literalLabel="Vendor Number"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel="state required for US"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			</tr>
			<tr class="odd">
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel="Address 1"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel="Postal Code required for US"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			</tr>
			<tr class="odd">
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell literalLabel = "Address 2"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel="Country"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			</tr>		
        </table>
	</div>
	<!-- Div for displaying vendor address ifnormation end here -->

		<div class="tab-container" align=center>	       
				<h3>Vendor Info
		 </h3>
      <table cellpadding="0" class="datatable" title="Stock Item(s)" summary="Stock Item(s)">
		<tr class="odd">
		        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel="Vendor Choice"/>
			</td>
			<td class="grid" align="center">
			</td>
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel=""/>
			</td>
			<td class="grid" align="center">
			</td>
			</tr>
			<tr class="odd">
			        <td class="grid" align="center">			
			<kul:htmlAttributeHeaderCell literalLabel="Customer Number"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell forceRequired = "true" literalLabel=""/>
			</td>
						<td class="grid" align="center">
			</td>
			
			</tr>
			
			<!--  notes to vendor three rows start heree -->
			
	
	<tr class="odd">
	<td class="grid" width="25.0%" align="right" rowspan="3">
				Notes to Vendor:
	</td>
					<td class="grid" width="25.0%" rowspan="3">
								<textarea rows="2" cols="45" name='VendorNotes'
									id='VendorNotes'
									style=""  
									class="">
								</textarea>
					</td>
	<td class="grid" width="25.0%" align="right">
				Payment Terms:
	</td>
					<td class="grid" width="25.0%">2% 10 Days Net 30 Days
								<input type="hidden" name="payterms" 
									id='payterms.div'
 						      value='2% 10 Days Net 30 Days'
									size='25'
									maxlength='25'
									style=""  
									class=""/>
					</td>
	</tr>
  <tr class="odd">
	<td class="grid" width="25.0%" align="right">
				Shipping Title:
	</td>
					<td class="grid" width="25.0%">ORIGIN (VENDOR LOCATION)
								<input type="hidden" name="shiptitle" 
									id='shiptitle.div'
 						      value='ORIGIN (VENDOR LOCATION)'
									size='40'
									maxlength='40'
									style=""  
									class=""/>
					</td>
	</tr>
  <tr class="odd" >
	<td class="grid" width="25.0%" align="right">
				Shipping Payment Terms:
	</td>
					<td class="grid" width="25.0%">INST PAYS, SEPARATE BILL ("COLLECT")
								<input type="hidden" name="paymentterms" 
									id='paymentterms.div'
 						      value='INST PAYS, SEPARATE BILL ("COLLECT")'
									size='50'
									maxlength='50'
									style=""  
									class=""/>
					</td>
	</tr>
			<!-- notes to vendor three rows end here -->
			<tr class="odd">
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell literalLabel="Contact Name"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			        <td class="grid" align="center">
			<kul:htmlAttributeHeaderCell literalLabel="Contacts"/>
			</td>
						<td class="grid" align="center">
			</td>
			
			</tr>
			<!-- tow rows expression starts here -->
			  <tr class="odd">
	<td class="grid" width="25.0%" align="right">
				Phone Number:
	</td>
					<td class="grid" width="25.0%">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<br>
					</td>
	<td class="grid" width="25.0%" align="right" rowspan="2">
				Supplier Diversity: 
	</td>
					<td class="grid" width="25.0%" rowspan="2">
								&nbsp;
					</td>
	</tr>
  <tr class="odd" >
	<td class="grid" width="25.0%" align="right">
				Fax Number:
	</td>
					<td class="grid" width="25.0%">
								<input type="text" name="fax" 
									id='fax.div'
 						      value='612-546-1332'
									size='40'
									maxlength='40'
									style=""  
									class=""/>
					</td>
	</tr>
			<!--  two rows expression ends here  -->
        </table>
	</div>
	         </kul:tab>