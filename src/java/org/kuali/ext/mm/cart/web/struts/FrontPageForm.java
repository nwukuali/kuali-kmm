package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.ShoppingFrontPage;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.ShoppingFrontPageService;

public class FrontPageForm extends StoresShoppingFormBase {

    private String html;

    @Override
    public void populate(HttpServletRequest request) {
        ShoppingFrontPage frontPage = SpringContext.getBean(ShoppingFrontPageService.class).getCurrentShoppingFrontPage();
        if(frontPage != null) {
            setHtml(frontPage.getFrontPageHTML());
        }
    }
    
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
    
    @Override
    protected boolean requiresProfile() {
        return false;
    }
}
