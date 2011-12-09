<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<kul:documentPage showDocumentInfo="true" htmlFormAction="reprintWorksheetDoc" documentTypeName="SWKC" renderMultipart="true" showTabButtons="true">
	<c:set var="worksheetCountAttributes" value="${DataDictionary.WorksheetCountDocumentLookable.attributes}" />
	<c:set var="stockCountAttributes" value="${DataDictionary.StockCount.attributes}" />
	<kul:hiddenDocumentFields />
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
	<mm:worksheetCounters reviewMode="true" />
	<mm:stockItems displayMismatchedItems="true" tabTitle="Unresolved Stock Items" tabErrorKeys="document.stockCounts*" />
	<mm:stockItems displayMismatchedItems="false" tabTitle="Resolved Stock Items" tabErrorKeys="" />
	<mm:financialGlpes generalLedgerPendingEntries="${KualiForm.document.approvedGeneralLedgerPendingEntries}" />
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls transactionalDocument="${documentEntry.transactionalDocument}" extraButtons="${KualiForm.extraButtons}" />
</kul:documentPage>
