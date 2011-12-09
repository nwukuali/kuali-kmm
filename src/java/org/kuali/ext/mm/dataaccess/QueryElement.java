package org.kuali.ext.mm.dataaccess;

import java.util.ArrayList;
import java.util.List;


public class QueryElement {

	private List<QueryElement> andList;
	private List<QueryElement> orList;

	private String field;
	private Object value;

	private boolean lessThan;
	private boolean greaterThan;

	//private boolean andElement;
	private boolean exactMatch;
	private boolean startsWith;
	private boolean negative;
	
	//If dbPlatform implements text indexing
	private boolean textIndexSearch;

	public QueryElement() {
		this(null, null, true, false, false);
	}

	public QueryElement(String field, Object value) {
		this(field, value, true, false, false);
	}

	public QueryElement(String field, Object value, boolean exactMatch) {
		this(field, value, exactMatch, false, false);
	}

	public QueryElement(String field, Object value, boolean exactMatch, boolean negative) {
		this(field, value, exactMatch, negative, false);
	}

	public QueryElement(String field, Object value, boolean exactMatch, boolean negative, boolean startsWith) {
		setAndList(new ArrayList<QueryElement>());
		setOrList(new ArrayList<QueryElement>());
		setExactMatch(exactMatch);
		setField(field);
		setValue(value);
		setNegative(negative);
		setStartsWith(startsWith);
		setLessThan(false);
		setGreaterThan(false);
	}

	public List<QueryElement> getAndList() {
		return andList;
	}

	public void setAndList(List<QueryElement> andList) {
		this.andList = andList;
	}

	public List<QueryElement> getOrList() {
		return orList;
	}

	public void setOrList(List<QueryElement> orList) {
		this.orList = orList;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(boolean exactMatch) {
		this.exactMatch = exactMatch;
		if(exactMatch) {
			this.greaterThan = false;
			this.lessThan = false;
		}
	}

	public void setNegative(boolean negative) {
		this.negative = negative;
	}

	public boolean isNegative() {
		return negative;
	}

	public void setStartsWith(boolean startsWith) {
		this.startsWith = startsWith;
	}

	public boolean isStartsWith() {
		return startsWith;
	}

	public void setLessThan(boolean lessThan) {
		this.lessThan = lessThan;
		this.greaterThan = false;
		if(lessThan)
			this.exactMatch = false;
	}

	public boolean isLessThan() {
		return lessThan;
	}

	public void setGreaterThan(boolean greaterThan) {
		this.greaterThan = greaterThan;
		this.lessThan = false;
		if(greaterThan)
			this.exactMatch = false;
	}

	public boolean isGreaterThan() {
		return greaterThan;
	}

    public boolean isTextIndexSearch() {
        return textIndexSearch;
    }

    public void setTextIndexSearch(boolean textIndexSearch) {
        this.textIndexSearch = textIndexSearch;
    }

//	public void setAndElement(boolean andElement) {
//		this.andElement = andElement;
//	}
//
//	public boolean isAndElement() {
//		return andElement;
//	}


}
