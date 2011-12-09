<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="index" required="true" %>
<%@ attribute name="tabTitle" required="true" %>
<%@ attribute name="pageReadOnly" required="true" %>
<%@ attribute name="tabOpen" required="true" %>
<c:set var="rentalAttributes"	value="${DataDictionary.Rental.attributes}" />

	<kul:tab tabTitle="${tabTitle}" defaultOpen="${tabOpen}" tabErrorKey="document.checkinDetails*,rentals*">
			<div class="tab-container" align=center>
			<h3><c:out value="${tabTitle}"/></h3>

								<table cellpadding="0" class="datatable" title="Stock Item(s)"
									summary="Stock Item(s)">
									<tr class="odd">
									<c:if test="${!pageReadOnly}">
										<td class="grid" align="center">
										<table class="datatable" cellpadding="0" cellspacing="0"
											align="center">
											<tbody align=center>
												<tr>
													<td class="tab-subhead">Add Rental</td>
												</tr>
											</tbody>
										</table>
										<table class="datatable" cellpadding="0" cellspacing="0"
											align="center">
											<tr>
												<th class="grid" width="25.0%" align="center">Seriel
												No:</th>
												<td class="grid" align="center"><kul:htmlControlAttribute
													attributeEntry="${rentalAttributes.rentalSerialNumber}"
													property="rentals[${index}].checkinSerialNbr"
													readOnly="${pageReadOnly}" /></td>
												<td class="grid" align="center">
												<div align="center"><html:image
													property="methodToCall.addRental.line${index}"
													src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
													title="Add Location" alt="Add Location"
													styleClass="tinybutton" /></div>
												</td>
											</tr>
										</table>
										</td>
										</c:if>
										<td class="grid" align="center">
										<table class="datatable" cellpadding="0" cellspacing="0"
											align="center">
											<tbody align=center>
												<tr>
													<td class="tab-subhead">Added Rentals</td>
													<c:if test="${!pageReadOnly}">
													<td class="tab-subhead">Actions</td>
													</c:if>
												</tr>
											</tbody>
											
											<tr>
												<c:if
													test="${KualiForm.document.returnDetails[index].rentalsReturned}">
													<c:if test="${!pageReadOnly}">
													<td class="grid" align="center"><html:select
														property="selectedItems[${index}]">
														<html:optionsCollection
															property="document.returnDetails[${index}].returnRentals"
															value="displayLabel" label="displayLabel" />
													</html:select></td>
													<c:if test="${!pageReadOnly}">
													<td class="grid" align="center">
													<html:image
														property="methodToCall.deleteRental.line${index}"
														src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif"
														title="Delete Rental" alt="Delete Rental"
														styleClass="tinybutton" />
														</c:if>
														</td>
														</c:if>
														</c:if>
												<c:if test="${pageReadOnly}">
												<td class="grid" align="center">
												<c:out value="${KualiForm.document.returnDetails[index].rentalsForDisplay}" />
												</td>
												</c:if>														
											</tr>
											<tr></tr>
										</table>
										</td>
									</tr>
								</table>
								</div>
								</kul:tab>