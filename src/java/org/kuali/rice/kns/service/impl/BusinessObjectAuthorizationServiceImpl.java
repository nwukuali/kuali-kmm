package org.kuali.rice.kns.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityManagementService;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.util.KimCommonUtils;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.authorization.BusinessObjectAuthorizer;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictionsBase;
import org.kuali.rice.kns.authorization.InquiryOrMaintenanceDocumentAuthorizer;
import org.kuali.rice.kns.authorization.InquiryOrMaintenanceDocumentPresentationController;
import org.kuali.rice.kns.authorization.InquiryOrMaintenanceDocumentRestrictions;
import org.kuali.rice.kns.authorization.InquiryOrMaintenanceDocumentRestrictionsBase;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.AttributeSecurity;
import org.kuali.rice.kns.datadictionary.BusinessObjectEntry;
import org.kuali.rice.kns.datadictionary.FieldDefinition;
import org.kuali.rice.kns.datadictionary.InquiryCollectionDefinition;
import org.kuali.rice.kns.datadictionary.InquirySectionDefinition;
import org.kuali.rice.kns.datadictionary.MaintainableCollectionDefinition;
import org.kuali.rice.kns.datadictionary.MaintainableItemDefinition;
import org.kuali.rice.kns.datadictionary.MaintainableSectionDefinition;
import org.kuali.rice.kns.datadictionary.MaintenanceDocumentEntry;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.DocumentAuthorizer;
import org.kuali.rice.kns.document.authorization.DocumentPresentationController;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizer;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationController;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentRestrictions;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentRestrictionsBase;
import org.kuali.rice.kns.inquiry.InquiryAuthorizer;
import org.kuali.rice.kns.inquiry.InquiryPresentationController;
import org.kuali.rice.kns.inquiry.InquiryRestrictions;
import org.kuali.rice.kns.service.BusinessObjectAuthorizationService;
import org.kuali.rice.kns.service.BusinessObjectDictionaryService;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.kns.service.DocumentHelperService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.MaintenanceDocumentDictionaryService;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;

public class BusinessObjectAuthorizationServiceImpl implements
		BusinessObjectAuthorizationService {
	private DataDictionaryService dataDictionaryService;
	private IdentityManagementService identityManagementService;
	private BusinessObjectDictionaryService businessObjectDictionaryService;
	private DocumentHelperService documentHelperService;
	private MaintenanceDocumentDictionaryService maintenanceDocumentDictionaryService;

	public BusinessObjectRestrictions getLookupResultRestrictions(
			BusinessObject businessObject, Person user) {
		BusinessObjectRestrictions businessObjectRestrictions = new BusinessObjectRestrictionsBase();
		considerBusinessObjectFieldUnmaskAuthorization(businessObject, user,
				businessObjectRestrictions, "", null);
		return businessObjectRestrictions;
	}

	public InquiryRestrictions getInquiryRestrictions(
			BusinessObject businessObject, Person user) {
		InquiryRestrictions inquiryRestrictions = new InquiryOrMaintenanceDocumentRestrictionsBase();
		BusinessObjectEntry businessObjectEntry = getDataDictionaryService()
				.getDataDictionary().getBusinessObjectEntry(
						businessObject.getClass().getName());
		InquiryPresentationController inquiryPresentationController = getBusinessObjectDictionaryService()
				.getInquiryPresentationController(businessObject.getClass());
		InquiryAuthorizer inquiryAuthorizer = getBusinessObjectDictionaryService()
				.getInquiryAuthorizer(businessObject.getClass());
		considerBusinessObjectFieldUnmaskAuthorization(businessObject, user,
				inquiryRestrictions, "", null);
		considerBusinessObjectFieldViewAuthorization(businessObjectEntry,
				businessObject, null, user, inquiryAuthorizer,
				inquiryRestrictions, "");
		considerInquiryOrMaintenanceDocumentPresentationController(
				inquiryPresentationController, businessObject,
				inquiryRestrictions);
		considerInquiryOrMaintenanceDocumentAuthorizer(inquiryAuthorizer,
				businessObject, user, inquiryRestrictions);
		for (InquirySectionDefinition inquirySectionDefinition : businessObjectEntry
				.getInquiryDefinition().getInquirySections()) {
			if (inquirySectionDefinition.getInquiryCollections() != null) {
				addInquirableItemRestrictions(inquirySectionDefinition
						.getInquiryCollections().values(), inquiryAuthorizer,
						inquiryRestrictions, businessObject, businessObject,
						"", user);
			}
			// Collections may also be stored in the inquiry fields, so we need
			// to parse through that
			List<FieldDefinition> inquiryFields = inquirySectionDefinition
					.getInquiryFields();
			if (inquiryFields != null) {
				for (FieldDefinition fieldDefinition : inquiryFields) {
					addInquirableItemRestrictions(inquiryFields,
							inquiryAuthorizer, inquiryRestrictions,
							businessObject, businessObject, "", user);
				}
			}
		}

		return inquiryRestrictions;
	}

	public MaintenanceDocumentRestrictions getMaintenanceDocumentRestrictions(
			MaintenanceDocument maintenanceDocument, Person user) {

		MaintenanceDocumentRestrictions maintenanceDocumentRestrictions = new MaintenanceDocumentRestrictionsBase();
		BusinessObjectEntry businessObjectEntry = getDataDictionaryService()
				.getDataDictionary().getBusinessObjectEntry(
						maintenanceDocument.getNewMaintainableObject()
								.getBusinessObject().getClass().getName());
		MaintenanceDocumentPresentationController maintenanceDocumentPresentationController = (MaintenanceDocumentPresentationController) getDocumentHelperService()
				.getDocumentPresentationController(maintenanceDocument);
		MaintenanceDocumentAuthorizer maintenanceDocumentAuthorizer = (MaintenanceDocumentAuthorizer) getDocumentHelperService()
				.getDocumentAuthorizer(maintenanceDocument);
		considerBusinessObjectFieldUnmaskAuthorization(maintenanceDocument
				.getNewMaintainableObject().getBusinessObject(), user,
				maintenanceDocumentRestrictions, "", maintenanceDocument);
		considerBusinessObjectFieldViewAuthorization(businessObjectEntry,
				maintenanceDocument.getNewMaintainableObject()
						.getBusinessObject(), null, user,
				maintenanceDocumentAuthorizer, maintenanceDocumentRestrictions,
				"");
		considerBusinessObjectFieldModifyAuthorization(businessObjectEntry,
				maintenanceDocument.getNewMaintainableObject()
						.getBusinessObject(), null, user,
				maintenanceDocumentAuthorizer, maintenanceDocumentRestrictions,
				"");
		considerCustomButtonFieldAuthorization(businessObjectEntry,
				maintenanceDocument.getNewMaintainableObject()
						.getBusinessObject(), null, user,
				maintenanceDocumentAuthorizer, maintenanceDocumentRestrictions,
				"");
		considerInquiryOrMaintenanceDocumentPresentationController(
				maintenanceDocumentPresentationController, maintenanceDocument,
				maintenanceDocumentRestrictions);
		considerInquiryOrMaintenanceDocumentAuthorizer(
				maintenanceDocumentAuthorizer, maintenanceDocument, user,
				maintenanceDocumentRestrictions);
		considerMaintenanceDocumentPresentationController(
				maintenanceDocumentPresentationController, maintenanceDocument,
				maintenanceDocumentRestrictions);
		considerMaintenanceDocumentAuthorizer(maintenanceDocumentAuthorizer,
				maintenanceDocument, user, maintenanceDocumentRestrictions);

		MaintenanceDocumentEntry maintenanceDocumentEntry = getMaintenanceDocumentDictionaryService()
				.getMaintenanceDocumentEntry(
						maintenanceDocument.getDocumentHeader()
								.getWorkflowDocument().getDocumentType());
		for (MaintainableSectionDefinition maintainableSectionDefinition : maintenanceDocumentEntry
				.getMaintainableSections()) {
			addMaintainableItemRestrictions(maintainableSectionDefinition
					.getMaintainableItems(), maintenanceDocumentAuthorizer,
					maintenanceDocumentRestrictions, maintenanceDocument,
					maintenanceDocument.getNewMaintainableObject()
							.getBusinessObject(), "", user);
		}
		return maintenanceDocumentRestrictions;
	}

	protected void considerBusinessObjectFieldUnmaskAuthorization(
			BusinessObject businessObject, Person user,
			BusinessObjectRestrictions businessObjectRestrictions,
			String propertyPrefix, Document document) {
		BusinessObjectEntry businessObjectEntry = getDataDictionaryService()
				.getDataDictionary().getBusinessObjectEntry(
						businessObject.getClass().getName());
		for (String attributeName : businessObjectEntry.getAttributeNames()) {
			AttributeDefinition attributeDefinition = businessObjectEntry
					.getAttributeDefinition(attributeName);
			if (attributeDefinition.getAttributeSecurity() != null) {
				if (attributeDefinition.getAttributeSecurity().isMask()
						&& !canFullyUnmaskField(user,
								businessObject.getClass(), attributeName,
								document)) {
					businessObjectRestrictions.addFullyMaskedField(
							propertyPrefix + attributeName, attributeDefinition
									.getAttributeSecurity().getMaskFormatter());
				}
				if (attributeDefinition.getAttributeSecurity().isPartialMask()
						&& !canPartiallyUnmaskField(user, businessObject
								.getClass(), attributeName, document)) {
					businessObjectRestrictions.addPartiallyMaskedField(
							propertyPrefix + attributeName, attributeDefinition
									.getAttributeSecurity()
									.getPartialMaskFormatter());
				}
			}
		}
	}

	/**
	 * @param businessObjectEntry
	 *            if collectionItemBusinessObject is not null, then it is the DD
	 *            entry for collectionItemBusinessObject. Otherwise, it is the
	 *            entry for primaryBusinessObject
	 * @param primaryBusinessObject
	 *            the top-level BO that is being inquiried or maintained
	 * @param collectionItemBusinessObject
	 *            an element of a collection under the primaryBusinessObject
	 *            that we are evaluating view auths for
	 * @param user
	 *            the logged in user
	 * @param businessObjectAuthorizer
	 * @param inquiryOrMaintenanceDocumentRestrictions
	 * @param propertyPrefix
	 */
	protected void considerBusinessObjectFieldViewAuthorization(
			BusinessObjectEntry businessObjectEntry,
			BusinessObject primaryBusinessObject,
			BusinessObject collectionItemBusinessObject,
			Person user,
			BusinessObjectAuthorizer businessObjectAuthorizer,
			InquiryOrMaintenanceDocumentRestrictions inquiryOrMaintenanceDocumentRestrictions,
			String propertyPrefix) {
		for (String attributeName : businessObjectEntry.getAttributeNames()) {
			AttributeDefinition attributeDefinition = businessObjectEntry
					.getAttributeDefinition(attributeName);
			if (attributeDefinition.getAttributeSecurity() != null) {
				if (attributeDefinition.getAttributeSecurity().isHide()) {
					AttributeSet collectionItemPermissionDetails = new AttributeSet();
					AttributeSet collectionItemRoleQualifications = null;
					if (ObjectUtils.isNotNull(collectionItemBusinessObject)) {
						collectionItemPermissionDetails
								.putAll(getFieldPermissionDetails(
										collectionItemBusinessObject,
										attributeName));
						collectionItemPermissionDetails
								.putAll(businessObjectAuthorizer
										.getCollectionItemPermissionDetails(collectionItemBusinessObject));
						collectionItemRoleQualifications = new AttributeSet(
								businessObjectAuthorizer
										.getCollectionItemRoleQualifications(collectionItemBusinessObject));
					} else {
						collectionItemPermissionDetails
								.putAll(getFieldPermissionDetails(
										primaryBusinessObject, attributeName));
					}
					if (!businessObjectAuthorizer.isAuthorizedByTemplate(
							primaryBusinessObject, KNSConstants.KNS_NAMESPACE,
							KimConstants.PermissionTemplateNames.VIEW_FIELD,
							user.getPrincipalId(),
							collectionItemPermissionDetails,
							collectionItemRoleQualifications)) {
						inquiryOrMaintenanceDocumentRestrictions
								.addHiddenField(propertyPrefix + attributeName);
					}
				}
			}
		}
	}

	/**
	 * @param businessObjectEntry
	 *            if collectionItemBusinessObject is not null, then it is the DD
	 *            entry for collectionItemBusinessObject. Otherwise, it is the
	 *            entry for primaryBusinessObject
	 * @param primaryBusinessObject
	 *            the top-level BO that is being inquiried or maintained
	 * @param collectionItemBusinessObject
	 *            an element of a collection under the primaryBusinessObject
	 *            that we are evaluating view auths for
	 * @param user
	 *            the logged in user
	 * @param businessObjectAuthorizer
	 * @param inquiryOrMaintenanceDocumentRestrictions
	 * @param propertyPrefix
	 */
	protected void considerBusinessObjectFieldModifyAuthorization(
			BusinessObjectEntry businessObjectEntry,
			BusinessObject primaryBusinessObject,
			BusinessObject collectionItemBusinessObject, Person user,
			BusinessObjectAuthorizer businessObjectAuthorizer,
			MaintenanceDocumentRestrictions maintenanceDocumentRestrictions,
			String propertyPrefix) {
		for (String attributeName : businessObjectEntry.getAttributeNames()) {
			AttributeDefinition attributeDefinition = businessObjectEntry
					.getAttributeDefinition(attributeName);
			if (attributeDefinition.getAttributeSecurity() != null) {
				AttributeSet collectionItemPermissionDetails = new AttributeSet();
				AttributeSet collectionItemRoleQualifications = null;
				if (ObjectUtils.isNotNull(collectionItemBusinessObject)) {
					collectionItemPermissionDetails
							.putAll(getFieldPermissionDetails(
									collectionItemBusinessObject, attributeName));
					collectionItemPermissionDetails
							.putAll(businessObjectAuthorizer
									.getCollectionItemPermissionDetails(collectionItemBusinessObject));
					collectionItemRoleQualifications = new AttributeSet(
							businessObjectAuthorizer
									.getCollectionItemRoleQualifications(collectionItemBusinessObject));
				} else {
					collectionItemPermissionDetails
							.putAll(getFieldPermissionDetails(
									primaryBusinessObject, attributeName));
				}
				if (attributeDefinition.getAttributeSecurity().isReadOnly()) {
					if (!businessObjectAuthorizer.isAuthorizedByTemplate(
							primaryBusinessObject, KNSConstants.KNS_NAMESPACE,
							KimConstants.PermissionTemplateNames.MODIFY_FIELD,
							user.getPrincipalId(),
							collectionItemPermissionDetails,
							collectionItemRoleQualifications)) {
						maintenanceDocumentRestrictions
								.addReadOnlyField(propertyPrefix
										+ attributeName);
					}
				}
			}
		}
	}

	/**
	 * @param businessObjectEntry
	 *            if collectionItemBusinessObject is not null, then it is the DD
	 *            entry for collectionItemBusinessObject. Otherwise, it is the
	 *            entry for primaryBusinessObject
	 * @param primaryBusinessObject
	 *            the top-level BO that is being inquiried or maintained
	 * @param collectionItemBusinessObject
	 *            an element of a collection under the primaryBusinessObject
	 *            that we are evaluating view auths for
	 * @param user
	 *            the logged in user
	 * @param businessObjectAuthorizer
	 * @param inquiryOrMaintenanceDocumentRestrictions
	 * @param propertyPrefix
	 */
	protected void considerCustomButtonFieldAuthorization(
			BusinessObjectEntry businessObjectEntry,
			BusinessObject primaryBusinessObject,
			BusinessObject collectionItemBusinessObject, Person user,
			BusinessObjectAuthorizer businessObjectAuthorizer,
			MaintenanceDocumentRestrictions maintenanceDocumentRestrictions,
			String propertyPrefix) {
		for (String attributeName : businessObjectEntry.getAttributeNames()) {
			AttributeDefinition attributeDefinition = businessObjectEntry
					.getAttributeDefinition(attributeName);
			if (attributeDefinition.getControl().isButton()) {
				AttributeSet collectionItemPermissionDetails = new AttributeSet();
				AttributeSet collectionItemRoleQualifications = null;
				if (ObjectUtils.isNotNull(collectionItemBusinessObject)) {
					collectionItemPermissionDetails
							.putAll(getButtonFieldPermissionDetails(
									collectionItemBusinessObject, attributeName));
					collectionItemPermissionDetails
							.putAll(businessObjectAuthorizer
									.getCollectionItemPermissionDetails(collectionItemBusinessObject));
					collectionItemRoleQualifications = new AttributeSet(
							businessObjectAuthorizer
									.getCollectionItemRoleQualifications(collectionItemBusinessObject));
				} else {
					getButtonFieldPermissionDetails(primaryBusinessObject,
							attributeName);
				}

				if (!businessObjectAuthorizer
						.isAuthorizedByTemplate(
								primaryBusinessObject,
								KNSConstants.KNS_NAMESPACE,
								KimConstants.PermissionTemplateNames.PERFORM_CUSTOM_MAINTENANCE_DOCUMENT_FUNCTION,
								user.getPrincipalId(),
								collectionItemPermissionDetails,
								collectionItemRoleQualifications)) {
					maintenanceDocumentRestrictions
							.addHiddenField(propertyPrefix + attributeName);
				}
			}
		}
	}

	protected void considerInquiryOrMaintenanceDocumentPresentationController(
			InquiryOrMaintenanceDocumentPresentationController businessObjectPresentationController,
			BusinessObject businessObject,
			InquiryOrMaintenanceDocumentRestrictions inquiryOrMaintenanceDocumentRestrictions) {
		for (String attributeName : businessObjectPresentationController
				.getConditionallyHiddenPropertyNames(businessObject)) {
			inquiryOrMaintenanceDocumentRestrictions
					.addHiddenField(attributeName);
		}
		for (String sectionId : businessObjectPresentationController
				.getConditionallyHiddenSectionIds(businessObject)) {
			inquiryOrMaintenanceDocumentRestrictions
					.addHiddenSectionId(sectionId);
		}
	}

	protected void considerInquiryOrMaintenanceDocumentAuthorizer(
			InquiryOrMaintenanceDocumentAuthorizer authorizer,
			BusinessObject businessObject, Person user,
			InquiryOrMaintenanceDocumentRestrictions restrictions) {
		for (String sectionId : authorizer
				.getSecurePotentiallyHiddenSectionIds()) {
			Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
			additionalPermissionDetails
					.put(KimAttributes.SECTION_ID, sectionId);
			if (!authorizer.isAuthorizedByTemplate(businessObject,
					KNSConstants.KNS_NAMESPACE,
					KimConstants.PermissionTemplateNames.VIEW_SECTION, user
							.getPrincipalId(), additionalPermissionDetails,
					null)) {
				restrictions.addHiddenSectionId(sectionId);
			}
		}
	}

	protected void considerMaintenanceDocumentPresentationController(
			MaintenanceDocumentPresentationController presentationController,
			MaintenanceDocument document,
			MaintenanceDocumentRestrictions restrictions) {
		for (String attributeName : presentationController
				.getConditionallyReadOnlyPropertyNames(document)) {
			restrictions.addReadOnlyField(attributeName);
		}
		for (String sectionId : presentationController
				.getConditionallyReadOnlySectionIds(document)) {
			restrictions.addReadOnlySectionId(sectionId);
		}
	}

	protected void considerMaintenanceDocumentAuthorizer(
			MaintenanceDocumentAuthorizer authorizer,
			MaintenanceDocument document, Person user,
			MaintenanceDocumentRestrictions restrictions) {
		for (String sectionId : authorizer
				.getSecurePotentiallyReadOnlySectionIds()) {
			Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
			additionalPermissionDetails
					.put(KimAttributes.SECTION_ID, sectionId);
			if (!authorizer.isAuthorizedByTemplate(document,
					KNSConstants.KNS_NAMESPACE,
					KimConstants.PermissionTemplateNames.MODIFY_SECTION, user
							.getPrincipalId(), additionalPermissionDetails,
					null)) {
				restrictions.addReadOnlySectionId(sectionId);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void addInquirableItemRestrictions(Collection sectionDefinitions,
			InquiryAuthorizer authorizer, InquiryRestrictions restrictions,
			BusinessObject primaryBusinessObject,
			BusinessObject businessObject, String propertyPrefix, Person user) {
		for (Object inquirableItemDefinition : sectionDefinitions) {
			if (inquirableItemDefinition instanceof InquiryCollectionDefinition) {
				InquiryCollectionDefinition inquiryCollectionDefinition = (InquiryCollectionDefinition) inquirableItemDefinition;
				BusinessObjectEntry collectionBusinessObjectEntry = getDataDictionaryService()
						.getDataDictionary().getBusinessObjectEntry(
								inquiryCollectionDefinition
										.getBusinessObjectClass().getName());

				try {
					Collection<BusinessObject> collection = (Collection<BusinessObject>) PropertyUtils
							.getProperty(businessObject,
									inquiryCollectionDefinition.getName());
					int i = 0;
					for (Iterator<BusinessObject> iterator = collection
							.iterator(); iterator.hasNext();) {
						String newPropertyPrefix = propertyPrefix
								+ inquiryCollectionDefinition.getName() + "["
								+ i + "].";
						BusinessObject collectionItemBusinessObject = iterator
								.next();
						considerBusinessObjectFieldUnmaskAuthorization(
								collectionItemBusinessObject, user,
								restrictions, newPropertyPrefix, null);
						considerBusinessObjectFieldViewAuthorization(
								collectionBusinessObjectEntry,
								primaryBusinessObject,
								collectionItemBusinessObject, user, authorizer,
								restrictions, newPropertyPrefix);
						addInquirableItemRestrictions(
								inquiryCollectionDefinition
										.getInquiryCollections(), authorizer,
								restrictions, primaryBusinessObject,
								collectionItemBusinessObject,
								newPropertyPrefix, user);
						i++;
					}
				} catch (Exception e) {
					throw new RuntimeException(
							"Unable to resolve collection property: "
									+ businessObject.getClass() + ":"
									+ inquiryCollectionDefinition.getName(), e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void addMaintainableItemRestrictions(
			List<? extends MaintainableItemDefinition> itemDefinitions,
			MaintenanceDocumentAuthorizer authorizer,
			MaintenanceDocumentRestrictions restrictions,
			MaintenanceDocument maintenanceDocument,
			BusinessObject businessObject, String propertyPrefix, Person user) {
		for (MaintainableItemDefinition maintainableItemDefinition : itemDefinitions) {
			if (maintainableItemDefinition instanceof MaintainableCollectionDefinition) {
				try {
					MaintainableCollectionDefinition maintainableCollectionDefinition = (MaintainableCollectionDefinition) maintainableItemDefinition;
					Object prop = PropertyUtils.getProperty(businessObject,
							maintainableItemDefinition.getName());
					if (prop != null
							&& Collection.class.isAssignableFrom(prop
									.getClass())) {
						Collection<BusinessObject> collection = (Collection<BusinessObject>) prop;
						BusinessObjectEntry collectionBusinessObjectEntry = getDataDictionaryService()
								.getDataDictionary().getBusinessObjectEntry(
										maintainableCollectionDefinition
												.getBusinessObjectClass()
												.getName());
						int i = 0;
						for (Iterator<BusinessObject> iterator = collection
								.iterator(); iterator.hasNext();) {
							String newPropertyPrefix = propertyPrefix
									+ maintainableItemDefinition.getName()
									+ "[" + i + "].";
							BusinessObject collectionBusinessObject = iterator
									.next();
							considerBusinessObjectFieldUnmaskAuthorization(
									collectionBusinessObject, user,
									restrictions, newPropertyPrefix,
									maintenanceDocument);
							considerBusinessObjectFieldViewAuthorization(
									collectionBusinessObjectEntry,
									maintenanceDocument,
									collectionBusinessObject, user, authorizer,
									restrictions, newPropertyPrefix);
							considerBusinessObjectFieldModifyAuthorization(
									collectionBusinessObjectEntry,
									maintenanceDocument,
									collectionBusinessObject, user, authorizer,
									restrictions, newPropertyPrefix);
							addMaintainableItemRestrictions(
									((MaintainableCollectionDefinition) maintainableItemDefinition)
											.getMaintainableCollections(),
									authorizer, restrictions,
									maintenanceDocument,
									collectionBusinessObject,
									newPropertyPrefix, user);
							addMaintainableItemRestrictions(
									((MaintainableCollectionDefinition) maintainableItemDefinition)
											.getMaintainableFields(),
									authorizer, restrictions,
									maintenanceDocument,
									collectionBusinessObject,
									newPropertyPrefix, user);
							i++;
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(
							"Unable to resolve collection property: "
									+ businessObject.getClass() + ":"
									+ maintainableItemDefinition.getName(), e);
				}
			}
		}
	}

	public <T extends BusinessObject> boolean canFullyUnmaskField(Person user,
			Class<T> businessObjectClass, String fieldName, Document document) {
		Boolean result = null;
		if (document != null) { // if a document was passed, evaluate the
			// permission in the context of a document
			try { // try/catch and fallthrough is a fix for KULRICE-3365
				result = getDocumentHelperService().getDocumentAuthorizer(
						document).isAuthorizedByTemplate(
						document,
						KNSConstants.KNS_NAMESPACE,
						KimConstants.PermissionTemplateNames.FULL_UNMASK_FIELD,
						user.getPrincipalId(),
						getFieldPermissionDetails(businessObjectClass,
								fieldName), null);
			} catch (IllegalArgumentException e) {
				// document didn't have needed metadata
				// TODO: this requires intimate knowledge of
				// DocumentHelperServiceImpl
			}
		}
		if (result == null) {
			result = getIdentityManagementService().isAuthorizedByTemplateName(
					user.getPrincipalId(),
					KNSConstants.KNS_NAMESPACE,
					KimConstants.PermissionTemplateNames.FULL_UNMASK_FIELD,
					new AttributeSet(getFieldPermissionDetails(
							businessObjectClass, fieldName)), null);
		}
		return result; // should be safe to return Boolean here since the only
		// circumstances that
		// will leave it null will result in an exception being thrown above.
	}

	public <T extends BusinessObject> boolean canPartiallyUnmaskField(
			Person user, Class<T> businessObjectClass, String fieldName,
			Document document) {
		if (document == null) {
			return getIdentityManagementService().isAuthorizedByTemplateName(
					user.getPrincipalId(),
					KNSConstants.KNS_NAMESPACE,
					KimConstants.PermissionTemplateNames.PARTIAL_UNMASK_FIELD,
					new AttributeSet(getFieldPermissionDetails(
							businessObjectClass, fieldName)), null);
		} else { // if a document was passed, evaluate the permission in the
			// context of a document
			return getDocumentHelperService()
					.getDocumentAuthorizer(document)
					.isAuthorizedByTemplate(
							document,
							KNSConstants.KNS_NAMESPACE,
							KimConstants.PermissionTemplateNames.PARTIAL_UNMASK_FIELD,
							user.getPrincipalId(),
							getFieldPermissionDetails(businessObjectClass,
									fieldName), null);
		}
	}

	public <T extends BusinessObject> boolean canCreate(Class<T> boClass,
			Person user, String docTypeName) {
		DocumentPresentationController documentPresentationController = getDocumentHelperService()
				.getDocumentPresentationController(docTypeName);
		boolean canCreate = ((MaintenanceDocumentPresentationController) documentPresentationController)
				.canCreate(boClass);
		if (canCreate) {
			DocumentAuthorizer documentAuthorizer = getDocumentHelperService()
					.getDocumentAuthorizer(docTypeName);
			canCreate = ((MaintenanceDocumentAuthorizer) documentAuthorizer)
					.canCreate(boClass, user);
		}
		return canCreate;
	}

	public boolean canMaintain(BusinessObject businessObject, Person user,
			String docTypeName) {
		return ((MaintenanceDocumentAuthorizer) getDocumentHelperService()
				.getDocumentAuthorizer(docTypeName)).canMaintain(
				businessObject, user);
	}

	protected <T extends BusinessObject> Map<String, String> getFieldPermissionDetails(
			Class<T> businessObjectClass, String attributeName) {
		try {
			return getFieldPermissionDetails(businessObjectClass.newInstance(),
					attributeName);
		} catch (Exception e) {
			throw new RuntimeException(
					"The getPermissionDetails method of BusinessObjectAuthorizationServiceImpl was unable to instantiate the businessObjectClass"
							+ businessObjectClass, e);
		}
	}

	protected Map<String, String> getFieldPermissionDetails(
			BusinessObject businessObject, String attributeName) {
		Map<String, String> permissionDetails = null;
		// JHK: commenting out for KFSMI-2398 - permission checks need to be
		// done at the level specified
		// that is, if the parent object specifies the security, that object
		// should be used for the
		// component
		// if (attributeName.contains(".")) {
		// try {
		// permissionDetails = KimCommonUtils
		// .getNamespaceAndComponentSimpleName(PropertyUtils
		// .getPropertyType(businessObject, attributeName
		// .substring(0, attributeName
		// .lastIndexOf("."))));
		// } catch (Exception e) {
		// throw new RuntimeException(
		// "Unable to discover nested business object class: "
		// + businessObject.getClass() + " : "
		// + attributeName, e);
		// }
		// permissionDetails.put(KimAttributes.PROPERTY_NAME, attributeName
		// .substring(attributeName.indexOf(".") + 1));
		// } else {
		permissionDetails = KimCommonUtils
				.getNamespaceAndComponentSimpleName(businessObject.getClass());
		permissionDetails.put(KimAttributes.PROPERTY_NAME, attributeName);
		// }
		return permissionDetails;
	}

	protected Map<String, String> getButtonFieldPermissionDetails(
			BusinessObject businessObject, String attributeName) {
		Map<String, String> permissionDetails = new AttributeSet();
		if (attributeName.contains(".")) {
			permissionDetails.put(KimAttributes.BUTTON_NAME, attributeName);
		} else {
			permissionDetails.put(KimAttributes.BUTTON_NAME, attributeName);
		}
		return permissionDetails;
	}

	private DataDictionaryService getDataDictionaryService() {
		if (dataDictionaryService == null) {
			dataDictionaryService = KNSServiceLocator
					.getDataDictionaryService();
		}
		return dataDictionaryService;
	}

	private IdentityManagementService getIdentityManagementService() {
		if (identityManagementService == null) {
			identityManagementService = KIMServiceLocator
					.getIdentityManagementService();
		}
		return identityManagementService;
	}

	private BusinessObjectDictionaryService getBusinessObjectDictionaryService() {
		if (businessObjectDictionaryService == null) {
			businessObjectDictionaryService = KNSServiceLocator
					.getBusinessObjectDictionaryService();
		}
		return businessObjectDictionaryService;
	}

	private DocumentHelperService getDocumentHelperService() {
		if (documentHelperService == null) {
			documentHelperService = KNSServiceLocator
					.getDocumentHelperService();
		}
		return documentHelperService;
	}

	private MaintenanceDocumentDictionaryService getMaintenanceDocumentDictionaryService() {
		if (maintenanceDocumentDictionaryService == null) {
			maintenanceDocumentDictionaryService = KNSServiceLocator
					.getMaintenanceDocumentDictionaryService();
		}
		return maintenanceDocumentDictionaryService;
	}

	/**
	 * @see org.kuali.rice.kns.service.BusinessObjectAuthorizationService#attributeValueNeedsToBeEncryptedOnFormsAndLinks(java.lang.Class,
	 *      java.lang.String)
	 */
	public boolean attributeValueNeedsToBeEncryptedOnFormsAndLinks(
			Class<? extends BusinessObject> businessObjectClass,
			String attributeName) {
		AttributeSecurity attributeSecurity = getDataDictionaryService()
				.getAttributeSecurity(businessObjectClass.getName(),
						attributeName);
		return attributeSecurity != null
				&& attributeSecurity.hasRestrictionThatRemovesValueFromUI();
	}

}
