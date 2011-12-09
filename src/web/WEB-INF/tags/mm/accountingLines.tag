<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="accountingLines" required="true" type="java.util.List" %>
<%@ attribute name="accountingLinesPropertyName" required="true" %>
<%@ attribute name="newAccountingLinePropertyName" required="true" %>
<%@ attribute name="tabTitle" required="true" %>
<%@ attribute name="readOnly" required="true" %>
<%@ attribute name="lineId" required="false" %>
<%@ attribute name="tabErrorKey" required="false" %>
<%@ attribute name="remainingBalance" required="false" %>
<%@ attribute name="defaultOpen" required="false" %>

<c:set var="accountsAttributes" value="${DataDictionary.Accounts.attributes}" />

<kul:tab tabTitle="${tabTitle}" defaultOpen="${defaultOpen}" useCurrentTabIndexAsKey="true" tabErrorKey="${tabErrorKey }">
	<div class="tab-container" align=center>
<!--	<c:if test="${not empty remainingBalance }" >-->
<!--		<div style="text-align:right">Remaining Balance: ${remainingBalance}</div>-->
<!--	</c:if>-->
		<h3>Accounting Lines</h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Add Accounting Line" summary="Add Accounting Line">
			<tr>
				<th scope="col">&nbsp;</th>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.finCoaCd}" useShortLabel="true"  scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.accountNbr}" useShortLabel="true"  scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.subAcctNbr}" useShortLabel="true" scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.finObjectCd}" useShortLabel="true"  scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.finSubObjectCd}" useShortLabel="true"  scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.projectCd}" useShortLabel="true"  scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.departmentReferenceText}" useShortLabel="true" scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.amountType}" useShortLabel="true" scope="col"/>
				<kul:htmlAttributeHeaderCell attributeEntry="${accountsAttributes.formAmount}" useShortLabel="true" scope="col"/>
				<c:if test="${!readOnly}"><th width="8%" scope="col">Action</th></c:if>
			</tr>
			<c:if test="${!readOnly}">
				<tr>
					<th	>add:</th>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.finCoaCd" attributeEntry="${accountsAttributes.finCoaCd}" readOnly="${readOnly}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialChart" fieldConversions="chartOfAccountsCode:${newAccountingLinePropertyName}.finCoaCd" lookupParameters="${newAccountingLinePropertyName}.finCoaCd:chartOfAccountsCode" />
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.accountNbr" attributeEntry="${accountsAttributes.accountNbr}" readOnly="${readOnly}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount" fieldConversions="chartOfAccountsCode:${newAccountingLinePropertyName}.finCoaCd,accountNumber:${newAccountingLinePropertyName}.accountNbr" lookupParameters="${newAccountingLinePropertyName}.finCoaCd:chartOfAccountsCode,${newAccountingLinePropertyName}.accountNbr:accountNumber" />
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.subAcctNbr" attributeEntry="${accountsAttributes.subAcctNbr}" readOnly="${readOnly}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialSubAccount" fieldConversions="chartOfAccountsCode:${newAccountingLinePropertyName}.finCoaCd,accountNumber:${newAccountingLinePropertyName}.accountNbr,subAccountNumber:${newAccountingLinePropertyName}.subAcctNbr" lookupParameters="${newAccountingLinePropertyName}.finCoaCd:chartOfAccountsCode,${newAccountingLinePropertyName}.accountNbr:accountNumber,${newAccountingLinePropertyName}.subAcctNbr:subAccountNumber" />
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.finObjectCd" attributeEntry="${accountsAttributes.finObjectCd}" readOnly="${readOnly}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode" fieldConversions="chartOfAccountsCode:${newAccountingLinePropertyName}.finCoaCd,financialObjectCode:${newAccountingLinePropertyName}.finObjectCd" lookupParameters="${newAccountingLinePropertyName}.finCoaCd:chartOfAccountsCode,${newAccountingLinePropertyName}.finObjectCd:financialObjectCode" />
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.finSubObjectCd" attributeEntry="${accountsAttributes.finSubObjectCd}" readOnly="${readOnly}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialSubObjectCode" fieldConversions="chartOfAccountsCode:${newAccountingLinePropertyName}.finCoaCd,accountNumber:${newAccountingLinePropertyName}.accountNbr,financialObjectCode:${newAccountingLinePropertyName}.finObjectCd,financialSubObjectCode:${newAccountingLinePropertyName}.finSubObjectCd" lookupParameters="${newAccountingLinePropertyName}.finCoaCd:chartOfAccountsCode,${newAccountingLinePropertyName}.accountNbr:accountNumber,${newAccountingLinePropertyName}.finObjectCd:financialObjectCode,${newAccountingLinePropertyName}.finSubObjectCd:financialSubObjectCode" />
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.projectCd" attributeEntry="${accountsAttributes.projectCd}" readOnly="${readOnly}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialProjectCode" fieldConversions="code:${newAccountingLinePropertyName}.projectCd" lookupParameters="${newAccountingLinePropertyName}.projectCd:code" />
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.departmentReferenceText" attributeEntry="${accountsAttributes.departmentReferenceText}" readOnly="${readOnly}" /></td>
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.amountType" attributeEntry="${accountsAttributes.amountType}" readOnly="${readOnly}" />
					<td class="infoline"><kul:htmlControlAttribute property="${newAccountingLinePropertyName}.formAmount" attributeEntry="${accountsAttributes.formAmount}" readOnly="${readOnly}" /></td>
					<c:choose>
						<c:when test="${empty lineId }">
							<td class="infoline"><html:image property="methodToCall.addAccountingLine" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" styleClass="tinybutton" value="Add Line" title="Add Line" alt="Add Line" /></td>
						</c:when>
						<c:otherwise>
							<td class="infoline"><html:image property="methodToCall.addAccountingLineToLineItem.line${lineId}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" styleClass="tinybutton" value="Add Line" title="Add Line" alt="Add Line" /></td>	
						</c:otherwise>
					</c:choose>
				</tr>
			</c:if>
			<c:forEach var="account" items="${accountingLines }" varStatus="rowCounter" >
				<tr>
					<th>${rowCounter.count}:</th>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd" attributeEntry="${accountsAttributes.finCoaCd}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}">
							&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialChart" fieldConversions="chartOfAccountsCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd" lookupParameters="${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd:chartOfAccountsCode" />
						</c:if>
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].accountNbr" attributeEntry="${accountsAttributes.accountNbr}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}">
							&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount" fieldConversions="chartOfAccountsCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd,accountNumber:${accountingLinesPropertyName}[${rowCounter.count-1}].accountNbr" lookupParameters="${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd:chartOfAccountsCode,${accountingLinesPropertyName}[${rowCounter.count-1}].accountNbr:accountNumber" />
						</c:if>
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].subAcctNbr" attributeEntry="${accountsAttributes.subAcctNbr}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}">
							&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialSubAccount" fieldConversions="chartOfAccountsCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd,accountNumber:${accountingLinesPropertyName}[${rowCounter.count-1}].accountNbr,subAccountNumber:${accountingLinesPropertyName}[${rowCounter.count-1}].subAcctNbr" lookupParameters="${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd:chartOfAccountsCode,${accountingLinesPropertyName}[${rowCounter.count-1}].accountNbr:accountNumber,${accountingLinesPropertyName}[${rowCounter.count-1}].subAcctNbr:subAccountNumber" />
						</c:if>
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].finObjectCd" attributeEntry="${accountsAttributes.finObjectCd}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}">
							&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode" fieldConversions="chartOfAccountsCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd,financialObjectCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finObjectCd" lookupParameters="${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd:chartOfAccountsCode,${accountingLinesPropertyName}[${rowCounter.count-1}].finObjectCd:financialObjectCode" />
						</c:if>
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].finSubObjectCd" attributeEntry="${accountsAttributes.finSubObjectCd}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}">
							&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialSubObjectCode" fieldConversions="chartOfAccountsCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd,accountNumber:${accountingLinesPropertyName}[${rowCounter.count-1}].accountNbr,financialObjectCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finObjectCd,financialSubObjectCode:${accountingLinesPropertyName}[${rowCounter.count-1}].finSubObjectCd" lookupParameters="${accountingLinesPropertyName}[${rowCounter.count-1}].finCoaCd:chartOfAccountsCode,${accountingLinesPropertyName}[${rowCounter.count-1}].accountNbr:accountNumber,${accountingLinesPropertyName}[${rowCounter.count-1}].finObjectCd:financialObjectCode,${accountingLinesPropertyName}[${rowCounter.count-1}].finSubObjectCd:financialSubObjectCode" />
						</c:if>
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].projectCd" attributeEntry="${accountsAttributes.projectCd}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}">
							&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialProjectCode" fieldConversions="code:${accountingLinesPropertyName}[${rowCounter.count-1}].projectCd" lookupParameters="${accountingLinesPropertyName}[${rowCounter.count-1}].projectCd:code" />
						</c:if>
					</td>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].departmentReferenceText" attributeEntry="${accountsAttributes.departmentReferenceText}" readOnly="${readOnly}" /></td>
					<td class="infoline"><kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].amountType" attributeEntry="${accountsAttributes.amountType}" readOnly="${readOnly}" />
					<c:choose>
						<c:when test="${account.amountType eq KualiForm.amountTypeFixed}">
							<td class="infoline" >
								<kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].accountFixedAmt" attributeEntry="${accountsAttributes.accountFixedAmt}" readOnly="${readOnly}" />
							</td>							
						</c:when>
						<c:otherwise>
							<td class="infoline" >
								<kul:htmlControlAttribute property="${accountingLinesPropertyName}[${rowCounter.count-1}].accountPct" attributeEntry="${accountsAttributes.accountPct}" readOnly="${readOnly}" />
							</td>
						</c:otherwise>
					</c:choose>
					<c:if test="${!readOnly}">
						<c:choose>
							<c:when test="${empty lineId }">
								<td class="infoline"><html:image property="methodToCall.deleteAccountingLine.line${rowCounter.count-1}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif" styleClass="tinybutton" alt="Delete line" title="Delete line"/></td>
							</c:when>
							<c:otherwise>
								<td class="infoline"><html:image property="methodToCall.deleteAccountingLineFromLineItem.line${rowCounter.count-1}.item${lineId}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif" styleClass="tinybutton" alt="Delete line" title="Delete line"/></td>
							</c:otherwise>
						</c:choose>
					</c:if>
				</tr>
			</c:forEach>				
		</table>
	</div>
</kul:tab>		