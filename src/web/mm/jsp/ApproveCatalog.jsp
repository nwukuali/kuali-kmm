<%@ include file="mm/jsp/mmTldHeader.jsp"%>

<kul:documentPage showDocumentInfo="true"
     htmlFormAction="financialInternalBilling"
     documentTypeName="InternalBillingDocument" renderMultipart="true"
     showTabButtons="true">

     <html:hidden property="document.nextItemLineNumber" />	
     <kul:hiddenDocumentFields />

     <kul:documentOverview editingMode="${KualiForm.editingMode}" />
     <fin:accountingLines editingMode="${KualiForm.editingMode}"
       		editableAccounts="${KualiForm.editableAccounts}" />
     <fin:items editingMode="${KualiForm.editingMode}" />
     <gl:generalLedgerPendingEntries />
     <kul:notes />
     <kul:adHocRecipients />
    <kul:routeLog />
   <kul:panelFooter />
   <kul:documentControls
       transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>