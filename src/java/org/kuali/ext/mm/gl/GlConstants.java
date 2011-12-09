/**
 *
 */
package org.kuali.ext.mm.gl;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;


/**
 * @author harsha07
 */
public class GlConstants {

    public static final String GL_CREDIT = "C";
    public static final String GL_DEBIT = "D";
    public static final String DOC_APPROVED_CODE_NO = "N";
    public static final String DOC_APPROVED_CODE_YES = "A";
    public static final String BALANCE_TYPE_EXPENSE = "EX";
    public static final String BALANCE_TYPE_CURRENT_BUDGET = "CB";
    public static final String BALANCE_TYPE_BASE_BUDGET = "BB";
    public static final String BALANCE_TYPE_MONTHLY_BUDGET = "MB";
    public static final String BALANCE_TYPE_EXTERNAL_ENCUMBRANCE = "EX";
    public static final String BALANCE_TYPE_INTERNAL_ENCUMBRANCE = "IE";
    public static final String BALANCE_TYPE_COST_SHARE_ENCUMBRANCE = "CE";
    public static final String BALANCE_TYPE_ACTUAL = "AC";
    public static final String BALANCE_TYPE_AUDIT_TRAIL = "NB";
    public static final String BALANCE_TYPE_A21 = "A2";
    public static final String BALANCE_TYPE_PRE_ENCUMBRANCE = "PE";
    
    public static final String getFinancialSystemOriginCode(){
        return SpringContext.getBean(FinancialSystemConfiguration.class).getFinancialSystemOriginCode();
    }
}
