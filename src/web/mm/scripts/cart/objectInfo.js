/*
 * Copyright 2007 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /*
 * Given an form and an element name, returns the uppercased, trimmed value of that element
 */
function cleanupElementValue( kualiForm, name ) {
    var element = kualiForm.elements[ name ];
    
    if ( typeof element == 'undefined' ) {
        alert( 'undefined element "' + name + '"' );
    }
//    element.value = element.value+'';
//    
//    element.value = element.value.toUpperCase().trim();
    
    return element.value;
}

/*
 * Clears the value of the given target.
 */
function clearTarget(kualiForm, targetBase) {
    setTargetValue(kualiForm, targetBase, '');
}

/*
 * Sets the value contained by the named div to the given value, or to a non-breaking space if the given value is empty.
 */
function setTargetValue(kualiForm, targetBase, value, isError) {
	var containerHidden = kualiForm.elements[targetBase];
    var containerName = targetBase;// + '.div';
    
    var containerDiv = document.getElementById( containerName );

    if (containerDiv) {
        if (value == '') {
			DWRUtil.setValue( containerDiv.id, "&nbsp;" );
        } else {
			DWRUtil.setValue( containerDiv.id, value, isError?null:{escapeHtml:true} );
        }
    }
    if (containerHidden) {
        containerHidden.value = value;
    }
}


function loadCatalogInfo( kualiForm, catalogItemFieldName, catalogFieldName, quantityFieldName, profileFieldName, unitOfIssueFieldName, catalogPriceFieldName, catalogDescFieldName) {
	var itemNumber = cleanupElementValue( kualiForm, catalogItemFieldName );
	var catalogId = cleanupElementValue( kualiForm, catalogFieldName );
	var profileId = cleanupElementValue( kualiForm, profileFieldName );
	var quantity = cleanupElementValue( kualiForm, quantityFieldName );
	
	if (itemNumber=='' || catalogId=='') {
		clearTarget(kualiForm, unitOfIssueFieldName);
		clearTarget(kualiForm, catalogPriceFieldName);
		clearTarget(kualiForm, catalogDescFieldName);
	} else {
		var dwrReply = {
			callback:function(data) {
				if ( data != null && typeof data == 'object' ) {
					setTargetValue( kualiForm, unitOfIssueFieldName, data.catalogUnitOfIssueCd );					
					setTargetValue( kualiForm, catalogDescFieldName, data.catalogDesc.substring(0,65) + '...' );
				} else {
					setTargetValue( kualiForm, unitOfIssueFieldName,  "N/A" , true );
					setTargetValue( kualiForm, catalogDescFieldName, "N/A" , true );
				}
			},
			errorHandler:function( errorMessage ) { 
				window.status = errorMessage;
				setTargetValue( kualiForm, unitOfIssueFieldName, "N/A", true );
				setTargetValue( kualiForm, catalogDescFieldName,  "N/A", true );
			}
		};

		ShopCartCatalogService.getCatalogItem( itemNumber, catalogId, dwrReply );
		getMarkup(kualiForm, itemNumber, catalogId, quantity, profileId, catalogPriceFieldName);
	}
}

function getMarkup( kualiForm, itemNumber, catalogId, quantity, profileId, catalogPriceFieldName) {
	
	if (itemNumber=='' || catalogId=='' || profileId=='' || quantity =='') {
		clearTarget(kualiForm, catalogPriceFieldName);
	} else {
		var dwrReply = {
			callback:function(data) {
				if ( data != null ) {
					setTargetValue( kualiForm, catalogPriceFieldName, data + ' USD' );
				} else {
					setTargetValue( kualiForm, catalogPriceFieldName, "N/A" , true );
				}
			},
			errorHandler:function( errorMessage ) { 
				window.status = errorMessage;
				setTargetValue( kualiForm, catalogPriceFieldName,  "N/A", true );
			}
		};

		ShopCartCatalogService.computeCatalogItemPriceByIds( itemNumber, catalogId, profileId, quantity, dwrReply );
	}
}

//Auto-Complete/Search Suggestion box
function getSearchSuggestions( kualiForm, keywordFieldName, catalogFieldName, suggestionFieldName, submitButtonName, event) {
	var catalogId = cleanupElementValue( kualiForm, catalogFieldName );
	var keyword = cleanupElementValue( kualiForm, keywordFieldName );	
	
	var keyCode = getKeyPressed(event);
	if(isKeyToIgnore(keyCode))
		return;
	if(navigateSuggestions(kualiForm, keywordFieldName, suggestionFieldName, submitButtonName, keyCode))
		return;
		
	kualiForm.elements[suggestionFieldName].options.length=0;
	
	if (keyword=='' || keyword.length < 3) {
		closeSuggestionBox(kualiForm, suggestionFieldName);
	}
	else {
		var dwrReply = {
			callback:function(data) {
				if ( data != null && keyword == cleanupElementValue( kualiForm, keywordFieldName )) {
					var element = kualiForm.elements[suggestionFieldName];
					for(var i=0; i < data.length; i++) {
						element[i] = new Option(data[i], data[i]);
						element[i].setAttribute("onmouseover", "highlightSelectedSuggestion(document.forms['KualiForm'], '" + keywordFieldName + "', '" + suggestionFieldName + "', '"+ i + "');");
//						element[i].setAttribute("onclick", "changeSelectedSuggestion(document.forms['KualiForm'], '" + keywordFieldName + "', '" + suggestionFieldName + "');");
					}
					if(data.length > 0) {
						showSuggestionBox(kualiForm, suggestionFieldName);
					}
					else
						closeSuggestionBox(kualiForm, suggestionFieldName);
				} else {
					setTargetValue( kualiForm, suggestionFieldName, "" , true );
					closeSuggestionBox(kualiForm, suggestionFieldName);
				}
			},
			errorHandler:function( errorMessage ) { 
				window.status = errorMessage;
				setTargetValue( kualiForm, suggestionFieldName,  "", true );
			}
		};
		
		ShopCartSearchService.getSearchSuggestions( keyword, catalogId, dwrReply );
	}
}

function highlightSelectedSuggestion(kualiForm, keywordFieldName, fieldName, index) {
	var element = kualiForm.elements[fieldName];
	element.selectedIndex = index;
}

function changeSelectedSuggestion(kualiForm, keywordFieldName, fieldName) {
	var element = kualiForm.elements[fieldName];
	kualiForm.elements[keywordFieldName].value=element[element.selectedIndex].value;
	closeSuggestionBox(kualiForm, fieldName);
}

function navigateSuggestions(kualiForm, keywordFieldName, suggestionFieldName, submitButtonName, keyCode) {
	var element = kualiForm.elements[suggestionFieldName];
    var currentIndex = element.selectedIndex;
    
    if(keyCode == 40) { //Down arrow
    	if(currentIndex < element.length - 1)
    		element.selectedIndex = currentIndex + 1;
    	kualiForm.elements[keywordFieldName].value=element[element.selectedIndex].value;
    	return true;
    }
    else if (keyCode == 38) {  //up arrow
    	if(currentIndex > 0)
    		element.selectedIndex = currentIndex - 1;
    	kualiForm.elements[keywordFieldName].value=element[element.selectedIndex].value;
    	return true;
    }
    else if(keyCode == 13) {	//item selection - enter key
    	document.getElementById(submitButtonName).click();
    	closeSuggestionBox(kualiForm, suggestionFieldName);
    	return true;
    }
    else if(keyCode == 27) {	//escape
    	closeSuggestionBox(kualiForm, suggestionFieldName);
    	return true;
    }
    
    return false;
}

function showSuggestionBox(kualiForm, suggestionFieldName) {
	//show suggestion box
	kualiForm.elements[suggestionFieldName].style.visibility='visible';
	kualiForm.elements[suggestionFieldName].style.top='1.75em';
	kualiForm.elements[suggestionFieldName].style.left='0px';
	kualiForm.elements[suggestionFieldName].style.zIndex='10002';
	
	//Show modal window
	var modalDiv = document.getElementById('modal_div');
	modalDiv.style.height=document.body.parentNode.scrollHeight + 'px';
	modalDiv.style.width=document.body.parentNode.scrollWidth + 'px';
	modalDiv.setAttribute("onclick", "closeSuggestionBox(document.forms['KualiForm'], '" + suggestionFieldName + "')");
	modalDiv.style.visibility='visible';
	
	var suggestionSize = data.length;
	if(data.length > 10)
		suggestionSize = 10;
	else if(data.length < 2)
		suggestionSize = 2;
	kualiForm.elements[suggestionFieldName].setAttribute("size", suggestionSize); 
}

function closeSuggestionBox(kualiForm, suggestionFieldName) {
	var modalDiv = document.getElementById('modal_div');
	modalDiv.style.visibility='hidden';
	kualiForm.elements[suggestionFieldName].style.visibility='hidden';
} 

function isKeyToIgnore(keyCode) {
	switch( keyCode ) {
	  case 37: //KEY.LEFT
	  case 39: //KEY.RIGHT
	  case 33: //KEY.PAGEUP
	  case 34: //KEY.PAGEDOWN
		  return true;
	  default:
		  return false;
	}
}

function getKeyPressed(event) {
	var key;     
	if(event.keyCode)
	     key = event.keyCode; //IE
	else
	     key = event.which; //firefox
	    
	return key;
}