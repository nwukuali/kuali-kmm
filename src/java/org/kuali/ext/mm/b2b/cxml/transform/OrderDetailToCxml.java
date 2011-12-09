/**
 *
 */
package org.kuali.ext.mm.b2b.cxml.transform;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.ext.mm.b2b.cxml.types.Accounting;
import org.kuali.ext.mm.b2b.cxml.types.AccountingSegment;
import org.kuali.ext.mm.b2b.cxml.types.Any;
import org.kuali.ext.mm.b2b.cxml.types.Charge;
import org.kuali.ext.mm.b2b.cxml.types.Description;
import org.kuali.ext.mm.b2b.cxml.types.Distribution;
import org.kuali.ext.mm.b2b.cxml.types.ItemDetail;
import org.kuali.ext.mm.b2b.cxml.types.ItemID;
import org.kuali.ext.mm.b2b.cxml.types.ItemOut;
import org.kuali.ext.mm.b2b.cxml.types.Money;
import org.kuali.ext.mm.b2b.cxml.types.UnitPrice;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;

/**
 * @author schneppd
 */
public class OrderDetailToCxml implements CxmlTransformer<ItemOut, OrderDetail> {

    public ItemOut transform(OrderDetail detail, Object... options) {
        ItemOut itemOut = new ItemOut();

        itemOut.setLineNumber(String.valueOf(detail.getItemLineNumber()));
        itemOut.setQuantity(String.valueOf(detail.getOrderItemQty()));
        ItemID itemId = new ItemID();
        itemId.setSupplierPartID(detail.getDistributorNbr());
        itemId.setSupplierPartAuxiliaryID(new Any(detail.getSPaidId()));

        ItemDetail itemDetail = new ItemDetail();
        Description description = new Description();
        description.getContent().add(detail.getOrderItemDetailDesc());
        itemDetail.getDescription().add(description);
        itemDetail.setUnitOfMeasure(detail.getStockUnitOfIssueCd());
        itemDetail.setUnitPrice(createUnitPrice(detail.getOrderItemPriceAmt().toString(), "USD"));
        itemOut.setItemID(itemId);
        itemOut.setItemDetail(itemDetail);
        Distribution distribution = new Distribution();
        distribution.setAccounting(new Accounting());
        distribution.setCharge(new Charge());
        distribution.getCharge().setMoney(
                CxmlUtil.createMoneyElement(MMServiceLocator.getOrderService()
                        .computeLineTotalWithTax(detail).toString(), "USD"));
        itemOut.getDistribution().add(distribution);
        List<Accounts> accounts = detail.getAccounts();
        for (Accounts account : accounts) {
            if (account.getAccountPct() != null && !account.getAccountPct().equals(BigDecimal.ZERO)) {
                AccountingSegment segment = new AccountingSegment();
                segment.setId(account.getAccountsId());
                segment.setDescription(new Description());
                segment.getDescription().setLang("en");
                segment.getDescription().getContent().add(
                        account.getFinCoaCd() + "-" + account.getAccountNbr() + "-"
                                + account.getFinObjectCd() + "-"
                                + new MMDecimal(account.getAccountPct().doubleValue()) + "%");
                distribution.getAccounting().getAccountingSegment().add(segment);
            }
        }
        return itemOut;
    }
    
    private UnitPrice createUnitPrice(String amount, String currency) {
        UnitPrice moneyElement = new UnitPrice();
        Money money = new Money();
        money.setContent(amount);
        money.setCurrency(currency);
        moneyElement.setMoney(money);
        return moneyElement;
    }
}
