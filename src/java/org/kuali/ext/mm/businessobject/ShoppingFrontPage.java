package org.kuali.ext.mm.businessobject;

public class ShoppingFrontPage extends MMPersistableBusinessObjectBase {

    private String frontPageId;
    
    private String frontPageName;
    
    private String frontPageDescription;
    
    private String frontPageHTML;
    
    private String frontPageURL;
    
    private Integer displayHeight;
    
    private boolean current;
    
    public ShoppingFrontPage() {
        
    }
        
	/**
     * Gets the frontPageId property
     * @return Returns the frontPageId
     */
    public String getFrontPageId() {
        return this.frontPageId;
    }

    /**
     * Sets the frontPageId property value
     * @param frontPageId The frontPageId to set
     */
    public void setFrontPageId(String frontPageId) {
        this.frontPageId = frontPageId;
    }

    /**
     * Gets the frontPageName property
     * @return Returns the frontPageName
     */
    public String getFrontPageName() {
        return frontPageName;
    }

    /**
     * Sets the frontPageName property value
     * @param frontPageName The frontPageName to set
     */
    public void setFrontPageName(String frontPageName) {
        this.frontPageName = frontPageName;
    }

    /**
     * Gets the frontPageDescription property
     * @return Returns the frontPageDescription
     */
    public String getFrontPageDescription() {
        return this.frontPageDescription;
    }

    /**
     * Sets the frontPageDescription property value
     * @param frontPageDescription The frontPageDescription to set
     */
    public void setFrontPageDescription(String frontPageDescription) {
        this.frontPageDescription = frontPageDescription;
    }

    /**
     * Gets the frontPageHTML property
     * @return Returns the frontPageHTML
     */
    public String getFrontPageHTML() {
        return this.frontPageHTML;
    }

    /**
     * Sets the frontPageHTML property value
     * @param frontPageHTML The frontPageHTML to set
     */
    public void setFrontPageHTML(String frontPageHTML) {
        this.frontPageHTML = frontPageHTML;
    }

    /**
     * Gets the frontPageURL property
     * @return Returns the frontPageURL
     */
    public String getFrontPageURL() {
        return this.frontPageURL;
    }

    /**
     * Sets the frontPageURL property value
     * @param frontPageURL The frontPageURL to set
     */
    public void setFrontPageURL(String frontPageURL) {
        this.frontPageURL = frontPageURL;
    }

    /**
     * Gets the displayHeight property
     * @return Returns the displayHeight
     */
    public Integer getDisplayHeight() {
        return this.displayHeight;
    }

    /**
     * Sets the displayHeight property value
     * @param displayHeight The displayHeight to set
     */
    public void setDisplayHeight(Integer displayHeight) {
        this.displayHeight = displayHeight;
    }

    /**
     * Gets the current property
     * @return Returns the current
     */
    public boolean isCurrent() {
        return this.current;
    }

    /**
     * Sets the current property value
     * @param current The current to set
     */
    public void setCurrent(boolean current) {
        this.current = current;
    }


}
