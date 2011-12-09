<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="vendorLookupRequired" required="true"%>

<c:set var="warehouseAttributes"
	value="${DataDictionary.Warehouse.attributes}" />
<c:set var="worksheetCounterAttributes"
	value="${DataDictionary.WorksheetCounter.attributes}" />
<c:set var="worksheetCountAttributes"
	value="${DataDictionary.WorksheetCountDocumentLookable.attributes}" />

<kul:tab tabTitle="Vendor Information" defaultOpen="true">
	<c:if test="${vendorLookupRequired}">
		<div class="tab-container" align=center>
		<h3>Select a Vendor</h3>
	

	<table width="100%" cellpadding=0 cellspacing=0 class="datatable">
		<tr>
		
				
		</tr>
		<tr>
		<kul:htmlAttributeHeaderCell literalLabel="Vendor:" />

		<td colspan="3">
		<kul:htmlControlAttribute
			attributeEntry="${warehouseAttributes.warehouseNme}"
			property="document.vendorNm" readOnly="true" />
			<c:if test="${vendorLookupRequired}">
					<kul:lookup
			boClassName="org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail"
			fieldConversions="vendorHeaderGeneratedIdentifier:document.vendorHeaderGeneratedId,vendorName:document.vendorNm,vendorDetailAssignedIdentifier:document.vendorDetailAssignedId" />
			</c:if>
		</td>
		</tr>
		</table>
			</div>
	</c:if>
	<div class="tab-container" align=center>
	<h3>Vendor Address</h3>
	<table width="100%" cellpadding=0 cellspacing=0 class="datatable">
	<tr>
	</tr>
	<tr>
	<kul:htmlAttributeHeaderCell literalLabel="Vendor:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorDetail.vendorName" readOnly="true" />
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="City:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorCityName"
		readOnly="true" />
	</td>
	</tr>
	<tr>
	<kul:htmlAttributeHeaderCell literalLabel="Vendor Number:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorDetail.vendorNumber" readOnly="true" />
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="State:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorStateCode"
		readOnly="true" />
	</td>
	</tr>
	<tr>
	<kul:htmlAttributeHeaderCell literalLabel="Address 1:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorLine1Address"
		readOnly="true" />
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="Postal Code:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorZipCode"
		readOnly="true" />
	</td>
	</tr>
	<tr>
	<kul:htmlAttributeHeaderCell literalLabel="Address 2:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorLine2Address"
		readOnly="true" />
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="Country" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorCountryCode"
		readOnly="true" />
	</td>
	</tr>
	</table>
	</div>	
	<div class="tab-container" align=center>
	<h3>Vendor Info</h3>
	<table width="100%" cellpadding=0 cellspacing=0 class="datatable">
	<tr>
	</tr>
	<tr>
	<kul:htmlAttributeHeaderCell literalLabel="Vendor Choice:" />
	<td class="tab-subheadr" align="right">
	<br>
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="Customer Number:" />
	<td class="tab-subheadr" align="right">
	<br>
	</td>
	</tr>
	<tr>

	<kul:htmlAttributeHeaderCell literalLabel="Shipping Title:" />

	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorDetail.vendorShippingTitleCode"
		readOnly="true" />

	</td>

	<kul:htmlAttributeHeaderCell literalLabel="Payment Terms:" />

	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorDetail.vendorPaymentTermsCode"
		readOnly="true" />

	</td>
	</tr>

	<tr>

	<kul:htmlAttributeHeaderCell literalLabel="Shipping Payment Terms:" />

	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorDetail.vendorShippingPaymentTermsCode"
		readOnly="true" />
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="Fax Number:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorFaxNumber"
		readOnly="true" />
	</td>
	</tr>
	<tr>
	<kul:htmlAttributeHeaderCell literalLabel="Contact Name:" />
	<td>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<br>
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="Contacts:" />
	<td>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
	</tr>
	<tr>
	<kul:htmlAttributeHeaderCell literalLabel="Phone Number:" />
	<td>
	<kul:htmlControlAttribute
		attributeEntry="${warehouseAttributes.warehouseNme}"
		property="document.financialVendorAddress.vendorFaxNumber"
		readOnly="true" />
	</td>
	<kul:htmlAttributeHeaderCell literalLabel="Supplier Diversity:" />
	<td >
								&nbsp;
					</td>
	</tr>
	</table>
	</div>	
</kul:tab>