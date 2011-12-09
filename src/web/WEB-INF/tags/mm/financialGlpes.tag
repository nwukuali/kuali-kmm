<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
<%@ attribute name="generalLedgerPendingEntries" required="false" type="java.util.List" %>
<%@ attribute name="fromMaintenance" required="false" type="java.lang.Boolean" %>
<c:set var="generalLedgerPendingEntriesList" value="${generalLedgerPendingEntries}" />
<c:if test="${empty generalLedgerPendingEntries && !fromMaintenance}">
	<c:set var="generalLedgerPendingEntriesList" value="${KualiForm.document.financialGeneralLedgerPendingEntries}" />
</c:if>
<kul:tab tabTitle="General Ledger Pending Entries" defaultOpen="false">
<div class="tab-container" align=center>
	<h3>General Ledger Pending Entries</h3>
    <table cellpadding="0" cellspacing="0" class="datatable" summary="view/edit pending entries">
    
	<c:if test="${empty generalLedgerPendingEntriesList}">
		<tr>
			<td class="datacell" height="50"colspan="12"><div align="center">There are currently no General Ledger Pending Entries associated with this document.</div></td>
		</tr>
	</c:if>
	<c:if test="${!empty generalLedgerPendingEntriesList}">
        <c:set var="entryAttributes" value="${DataDictionary.FinancialGeneralLedgerPendingEntry.attributes}" />
		<tr>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.universityFiscalYear}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.chartOfAccountsCode}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.accountNumber}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.subAccountNumber}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.financialObjectCode}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.financialSubObjectCode}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.projectCode}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.financialDocumentTypeCode}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.financialBalanceTypeCode}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.financialObjectTypeCode}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.transactionLedgerEntryAmount}" hideRequiredAsterisk="true" scope="col"/>
            <kul:htmlAttributeHeaderCell attributeEntry="${entryAttributes.transactionDebitCreditCode}" hideRequiredAsterisk="true" scope="col"/>
		</tr>
		<c:forEach var="entry" items="${generalLedgerPendingEntriesList}">
		<tr>
			<td class="datacell center"><c:out value="${entry.universityFiscalYear}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.chartOfAccountsCode}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.accountNumber}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.subAccountNumber}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.financialObjectCode}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.financialSubObjectCode}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.projectCode}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.financialDocumentTypeCode}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.financialBalanceTypeCode}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.financialObjectTypeCode}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.transactionLedgerEntryAmount}" />&nbsp;</td>
			<td class="datacell center"><c:out value="${entry.transactionDebitCreditCode}" />&nbsp;</td>
		</tr>
		</c:forEach>
	</c:if>
	</table>
</div>
</kul:tab>
