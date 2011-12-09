insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MAX_PRICE_TOLERANCE_ALLOWED', SYS_GUID(), 1 'CONFG', '25.00', 'Maximum price Tolerance allowed for resolving items at the time of Stock Count', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'ENABLE_DIRECT_INQUIRIES_IND', SYS_GUID(), 2 'CONFG', 'N', 'Flag for enabling/disabling direct inquiries on screens that are drawn by the nervous system (i.e. lookups and maintenance documents)', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'ENABLE_FIELD_LEVEL_HELP_IND', SYS_GUID(), 2 'CONFG', 'N', 'Indicates whether field level help links are enabled on lookup pages and documents.', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-IDM', 'Document', 'MAX_MEMBERS_PER_PAGE', SYS_GUID(), 1 'CONFG', '20', 'The maximum number of role or group members to display at once on their documents. If the number is above this value, the document will switch into a paging mode with only this many rows displayed at a time.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-IDM', 'PersonDocumentName', 'PREFIXES', SYS_GUID(), 1 'CONFG', 'Ms;Mrs;Mr;Dr', '', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-IDM', 'PersonDocumentName', 'SUFFIXES', SYS_GUID(), 1 'CONFG', 'Jr;Sr;Mr;Md', '', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'CHECK_ENCRYPTION_SERVICE_OVERRIDE_IND', SYS_GUID(), 2 'CONFG', 'Y', 'Flag for enabling/disabling (Y/N) the demonstration encryption check.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'DATE_TO_STRING_FORMAT_FOR_FILE_NAME', SYS_GUID(), 1 'CONFG', 'yyyyMMdd', 'A single date format string that the DateTimeService will use to format dates to be used in a file name when DateTimeServiceImpl.toDateStringForFilename(Date) is called. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'DATE_TO_STRING_FORMAT_FOR_USER_INTERFACE', SYS_GUID(), 1 'CONFG', 'MM/dd/yyyy', 'A single date format string that the DateTimeService will use to format a date to be displayed on a web page. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'DEFAULT_COUNTRY', SYS_GUID(), 1 'CONFG', 'US', 'Used as the default country code when relating records that do not have a country code to records that do have a country code, e.g. validating a zip code where the country is not collected.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'ENABLE_DIRECT_INQUIRIES_IND', SYS_GUID(), 1 'CONFG', 'Y', 'Flag for enabling/disabling direct inquiries on screens that are drawn by the nervous system (i.e. lookups and maintenance documents)', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'ENABLE_FIELD_LEVEL_HELP_IND', SYS_GUID(), 1 'CONFG', 'Y', 'Indicates whether field level help links are enabled on lookup pages and documents.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'MAX_FILE_SIZE_DEFAULT_UPLOAD', SYS_GUID(), 1 'CONFG', '5M', 'Maximum file upload size for the application. Used by PojoFormBase. Must be an integer, optionally followed by "K", "M", or "G". Only used if no other upload limits are in effect.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'SENSITIVE_DATA_PATTERNS', SYS_GUID(), 1 'CONFG', '[0-9]{9};[0-9]{3}-[0-9]{2}-[0-9]{4}', 'A semi-colon delimted list of regular expressions that identify potentially sensitive data in strings. These patterns will be matched against notes, document explanations, and routing annotations (i.e. the document disapproval reason)', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'STRING_TO_DATE_FORMATS', SYS_GUID(), 3 'CONFG', 'MM/dd/yyyy;M/d/yyyy;MM-dd-yyyy;MM/dd/yyyy HH:mm:ss;MM-dd-yyyy HH:mm:ss;yyyy-MM-dd HH:mm:ss.S;yyyy-MM-dd HH:mm:ss.SS;yyyy-MM-dd HH:mm:ss.SSS', 'A semi-colon delimted list of strings representing date formats that the DateTimeService will use to parse dates when DateTimeServiceImpl.convertToSqlDate(String) or DateTimeServiceImpl.convertToDate(String) is called. Note that patterns will be applied in the order listed (and the first applicable one will be used). For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'STRING_TO_TIMESTAMP_FORMATS', SYS_GUID(), 1 'CONFG', 'MM/dd/yyyy hh:mm a;MM/dd/yyyy;MM/dd/yyyy HH:mm:ss;MM/dd/yy;MM-dd-yy;MMMM dd;yyyy;MMddyy', 'A semi-colon delimted list of strings representing date formats that the DateTimeService will use to parse date and times when DateTimeServiceImpl.convertToDateTime(String) or DateTimeServiceImpl.convertToSqlTimestamp(String) is called. Note that patterns will be applied in the order listed (and the first applicable one will be used). For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'TIMESTAMP_TO_STRING_FORMAT_FOR_FILE_NAME', SYS_GUID(), 1 'CONFG', 'yyyyMMdd-HH-mm-ss-S', 'A single date format string that the DateTimeService will use to format a date and time string to be used in a file name when DateTimeServiceImpl.toDateTimeStringForFilename(Date) is called.. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'All', 'TIMESTAMP_TO_STRING_FORMAT_FOR_USER_INTERFACE', SYS_GUID(), 1 'CONFG', 'MM/dd/yyyy hh:mm a', 'A single date format string that the DateTimeService will use to format a date and time to be displayed on a web page. For a more technical description of how characters in the parameter value will be interpreted, please consult the javadocs for java.text.SimpleDateFormat. Any changes will be applied when the application is restarted.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Document', 'ALLOW_ENROUTE_BLANKET_APPROVE_WITHOUT_APPROVAL_REQUEST_IND', SYS_GUID(), 1 'CONFG', 'N', 'Controls whether the nervous system will show the blanket approve button to a user who is authorized for blanket approval but is neither the initiator of the particular document nor the recipient of an active, pending, approve action request.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Document', 'DEFAULT_CAN_PERFORM_ROUTE_REPORT_IND', SYS_GUID(), 2 'CONFG', 'N', 'If Y, the "route report" button will be displayed on the document actions bar if the document is using the default DocumentAuthorizerBase.getDocumentActionFlags to set the canPerformRouteReport property of the returned DocumentActionFlags instance.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Document', 'MAX_FILE_SIZE_ATTACHMENT', SYS_GUID(), 1 'CONFG', '5M', 'Maximum attachment upload size for the application. Used by KualiDocumentFormBase. Must be an integer, optionally followed by "K", "M", or "G".', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Document', 'SEND_NOTE_WORKFLOW_NOTIFICATION_ACTIONS', SYS_GUID(), 1 'CONFG', 'F', 'Some documents provide the functionality to send notes to another user using a workflow FYI or acknowledge functionality. This parameter specifies the default action that will be used when sending notes. This parameter should be one of the following 2 values: "K" for acknowledge or "F" for fyi. Depending on the notes and workflow service implementation, other values may be possible (see edu.iu.uis.eden.EdenConstants javadocs for details).', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Document', 'SESSION_TIMEOUT_WARNING_MESSAGE_TIME', SYS_GUID(), 1 'CONFG', '5', 'The number of minutes before a session expires that user should be warned when a document uses pessimistic locking.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Lookup', 'MULTIPLE_VALUE_RESULTS_EXPIRATION_SECONDS', SYS_GUID(), 2 'CONFG', '86400', 'Lookup results may continue to be persisted in the DB long after they are needed. This parameter represents the maximum amount of time, in seconds, that the results will be allowed to persist in the DB before they are deleted from the DB.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Lookup', 'MULTIPLE_VALUE_RESULTS_PER_PAGE', SYS_GUID(), 2 'CONFG', '100', 'Maximum number of rows that will be displayed on a look-up results screen.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Lookup', 'RESULTS_DEFAULT_MAX_COLUMN_LENGTH', SYS_GUID(), 2 'CONFG', '70', 'If a maxLength attribute has not been set on a lookup result field in the data dictionary, then the result column''s maximum length will be the value of this parameter. Set this parameter to 0 for an unlimited default length or a positive value (i.e. greater than 0) for a finite maximum length.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-NS', 'Lookup', 'RESULTS_LIMIT', SYS_GUID(), 4 'CONFG', '200', 'Maximum number of results returned in a look-up query.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'ActionList', 'ACTION_LIST_DOCUMENT_POPUP_IND', SYS_GUID(), 1 'CONFG', 'N', 'Flag to specify if 
clicking on a Document ID from the Action List will load the Document in 
a new window.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'ActionList', 'ACTION_LIST_ROUTE_LOG_POPUP_IND', SYS_GUID(), 1 'CONFG', 'N', 'Flag to specify if 
clicking on a Route Log from the Action List will load the Route Log in 
a new window.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'ActionList', 'EMAIL_NOTIFICATION_TEST_ADDRESS', SYS_GUID(), 1 'CONFG', '', 'Default email address 
used for testing.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'ActionList', 'PAGE_SIZE_THROTTLE', SYS_GUID(), 1 'CONFG', '', 'Throttles the number of results 
returned on all users Action Lists, regardless of their user 
preferences.  This is intended to be used in a situation where 
excessively large Action Lists are causing performance issues.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'ActionList', 'SEND_EMAIL_NOTIFICATION_IND', SYS_GUID(), 1 'CONFG', 'N', 'Flag to determine whether 
or not to send email notification.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'All', 'MAXIMUM_NODES_BEFORE_RUNAWAY', SYS_GUID(), 1 'CONFG', '', 'The maximum number of 
nodes the workflow engine will process before it determines the process 
is a runaway process.  This is prevent infinite "loops" in the workflow 
engine.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'All', 'SHOW_ATTACHMENTS_IND', SYS_GUID(), 1 'CONFG', 'Y', 'Flag to specify whether or not a 
file upload box is displayed for KEW notes which allows for uploading of 
an attachment with the note.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Backdoor', 'SHOW_BACK_DOOR_LOGIN_IND', SYS_GUID(), 1 'CONFG', 'Y', 'Flag to show the backdoor 
login.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'DocSearchCriteriaDTO', 'DOCUMENT_SEARCH_POPUP_IND', SYS_GUID(), 1 'CONFG', 'Y', 'Flag to 
specify if clicking on a Document ID from Document Search will load the 
Document in a new window.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'DocSearchCriteriaDTO', 'DOCUMENT_SEARCH_ROUTE_LOG_POPUP_IND', SYS_GUID(), 1 'CONFG', 'N', 'Flag to specify if clicking on a Route Log from Document Search will 
load the Route Log in a new window.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'DocSearchCriteriaDTO', 'FETCH_MORE_ITERATION_LIMIT', SYS_GUID(), 1 'CONFG', '', 'Limit of 
fetch more iterations for document searches.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'DocSearchCriteriaDTO', 'RESULT_CAP', SYS_GUID(), 1 'CONFG', '200', 'Maximum number of 
documents to return from a search.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'EDocLite', 'DEBUG_TRANSFORM_IND', SYS_GUID(), 1 'CONFG', 'N', 'Defines whether the debug 
transform is enabled for eDcoLite.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'EDocLite', 'USE_XSLTC_IND', SYS_GUID(), 1 'CONFG', 'N', 'Defines whether XSLTC is used for 
eDocLite.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Feature', 'IS_LAST_APPROVER_ACTIVATE_FIRST_IND', SYS_GUID(), 1 'CONFG', '', 'A flag to specify 
whether the WorkflowInfo.isLastApproverAtNode(...) API method attempts 
to active requests first, prior to execution.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Mailer', 'FROM_ADDRESS', SYS_GUID(), 1 'CONFG', 'quickstart@localhost', 'Default from email 
address for notifications.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Notification', 'NOTIFY_EXCLUDED_USERS_IND', SYS_GUID(), 1 'CONFG', '', 'Defines whether or not to 
send a notification to users excluded from a workgroup.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'QuickLinks', 'RESTRICT_DOCUMENT_TYPES', SYS_GUID(), 1 'CONFG', '', 'Comma seperated list of 
Document Types to exclude from the Rule Quicklinks.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Rule', 'CACHING_IND', SYS_GUID(), 1 'CONFG', 'Y', 'Indicator to determine if rule caching is 
enabled.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Rule', 'CUSTOM_DOCUMENT_TYPES', SYS_GUID(), 1 'CONFG', '', 'Defines custom Document Type 
processes to use for certain types of routing rules.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Rule', 'DELEGATE_LIMIT', SYS_GUID(), 1 'CONFG', '20', 'Specifies that maximum number of 
delegation rules that will be displayed on a Rule inquiry before the 
screen shows a count of delegate rules and provides a link for the user 
to show them.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Rule', 'GENERATE_ACTION_REQUESTS_IND', SYS_GUID(), 1 'CONFG', 'Y', 'Flag to determine whether 
or not a change to a routing rule should be applied retroactively to 
existing documents.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KR-WKFLW', 'Rule', 'RULE_CACHE_REQUEUE_DELAY', SYS_GUID(), 1 'CONFG', '5000', 'Amount of time after a 
rule change is made before the rule cache update message is sent.', 'A', 'KUALI');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'STOCK_PRICE_CODE', SYS_GUID(), 1 'CONFG', '02', 'The Stock price code(s) that is used for calculating stock price', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MAX_NUMBER_OF_WORKSHEET_COUNTERS', SYS_GUID(), 1 'CONFG', '10', 'The maximum number of counters for creating worksheet', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MAX_NUMBER_OF_WORKSHEET_COPIES', SYS_GUID(), 1 'CONFG', '3', 'The maximum number of copies when creating worksheet', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'ShoppingCart', 'MAX_SHOPPING_RESULT_LIMIT', SYS_GUID(), 1 'CONFG', '200', 'Maximum number of displayable results (Catalog Items stored in memory after search)
    ', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'STOCK_TYPES_WITH_SERIAL_NUM', SYS_GUID(), 1 'CONFG', '03', 'The Stock Type code(s) that can be associated with trackable serial numbers', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'DEFAULT_WAREHOUSE_CD', SYS_GUID(), 3 'CONFG', '74', 'The default Warehouse Code used for prepopulation in documents', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'DEFAULT_SORT_CD', SYS_GUID(), 1 'CONFG', 'Orders', 'The default Sort Code to be used for Pick List Generation', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'PACKING_LIST_PDF', SYS_GUID(), 1 'CONFG', 'packing_list_template13.pdf', 'Name of template file to be used for Packing List printing', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'PACKING_LIST_PDF_MAX_LINES', SYS_GUID(), 1 'CONFG', '13', 'The maximum number of lines available per page on the Packing List template', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Lookup', 'MAX_PRINT_ALL_RESULTS', SYS_GUID(), 1 'CONFG', '200', 'The maximum number of Pick Tickets to return from the Lookup for building a Packing List', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Batch', 'CAROUSEL_WAREHOUSE_ZONES', SYS_GUID(), 1 'CONFG', '74=01,02,03,04', 'List of warehouses with their corresponding carousel zone codes. (WH1=ZN1,ZN2;WH2=ZN1,ZN2)', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MM_ORDER_APPROVALS_BEGIN_AT_AMOUNT', SYS_GUID(), 1 'CONFG', '2500', 'Maximum dollar amount for MM orders that does not require Fiscal officer approval', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'ShoppingCart', 'ALLOW_PERSONAL_USE', SYS_GUID(), 2 'CONFG', 'N', 'Determines whether personal use profiles/external users are allowed in the Shopping portal', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Batch', 'FAMIS_FEED_ACCOUNTS', SYS_GUID(), 1 'CONFG', 'MS-XT022907;', 'List of Accounts used for building FAMIS feed', 'A', 'KFS');

