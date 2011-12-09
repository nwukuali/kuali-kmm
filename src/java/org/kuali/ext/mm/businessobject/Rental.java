package org.kuali.ext.mm.businessobject;
	
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
	
public class Rental extends StoresPersistableBusinessObject {

    private static final long serialVersionUID = 2752488722931654761L;

    private String rentalId;
		
	private String rentalTypeCode;
	
	private RentalType rentalType;
		
	private String stockId;
	
	private Stock stock;
	
	private String rentalStatusCode;
		
	private String rentalSerialNumber;
		
	private Timestamp issueDate;
		
	private Timestamp lastChargeDate;
		
	private Timestamp returnDate;
		
	private String pickListLineId;
		
	private Integer checkinDetailId;
		
	private Integer returnDetailId;
	
	private PickListLine pickListLine;
	
	private CheckinDetail checkinDetail;
	
	private ReturnDetail returnDetail;
	
	public List<Accounts> accountingLines = new ArrayList<Accounts>();
	
	public Rental(){
	    super();
	}
	
    public Rental(Rental rental) {
        super();
        this.rentalTypeCode = rental.rentalTypeCode;
        this.rentalType = rental.rentalType;
        this.stockId = rental.stockId;
        this.stock = rental.stock;
        this.rentalStatusCode = rental.rentalStatusCode;
        this.rentalSerialNumber = rental.rentalSerialNumber;
        this.issueDate = rental.issueDate;
        this.lastChargeDate = rental.lastChargeDate;
        this.returnDate = rental.returnDate;
        this.pickListLineId = rental.pickListLineId;
        this.checkinDetailId = rental.checkinDetailId;
        this.returnDetailId = rental.returnDetailId;
        this.pickListLine = rental.pickListLine;
        this.checkinDetail = rental.checkinDetail;
        this.returnDetail = rental.returnDetail;
    }

    public String getRentalId() {
		return rentalId;
	}
	
	public void setRentalId(String rentalId) {
		this.rentalId = rentalId;
	}

    public String getStockId() {
		return stockId;
	}
	
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
		
	public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public String getRentalSerialNumber() {
		return rentalSerialNumber;
	}
	
	public void setRentalSerialNumber(String rentalSerialNumber) {
		this.rentalSerialNumber = rentalSerialNumber;
	}
		
	public Timestamp getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Timestamp issueDate) {
		this.issueDate = issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
        this.issueDate = new Timestamp(issueDate.getTime());
    }
		
	public Timestamp getLastChargeDate() {
		return lastChargeDate;
	}
	
	public void setLastChargeDate(Timestamp lastChargeDate) {
		this.lastChargeDate = lastChargeDate;
	}
		
	public Timestamp getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}
		
	public String getPickListLineId() {
		return pickListLineId;
	}
	
	public void setPickListLineId(String pickListLineId) {
		this.pickListLineId = pickListLineId;
	}
		
	public Integer getCheckinDetailId() {
		return checkinDetailId;
	}
	
	public void setCheckinDetailId(Integer checkinDetailId) {
		this.checkinDetailId = checkinDetailId;
	}
		
	public Integer getReturnDetailId() {
		return returnDetailId;
	}
	
	public void setReturnDetailId(Integer returnDetailId) {
		this.returnDetailId = returnDetailId;
	}

    public PickListLine getPickListLine() {
        return this.pickListLine;
    }

    public void setPickListLine(PickListLine pickListLine) {
        this.pickListLine = pickListLine;
    }

    public CheckinDetail getCheckinDetail() {
        return this.checkinDetail;
    }

    public void setCheckinDetail(CheckinDetail checkinDetail) {
        this.checkinDetail = checkinDetail;
    }

    public ReturnDetail getReturnDetail() {
        return this.returnDetail;
    }

    public void setReturnDetail(ReturnDetail returnDetail) {
        this.returnDetail = returnDetail;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalStatusCode(String rentalStatusCode) {
        this.rentalStatusCode = rentalStatusCode;
    }

    public String getRentalStatusCode() {
        return rentalStatusCode;
    }

    public void setRentalTypeCode(String rentalTypeCode) {
        this.rentalTypeCode = rentalTypeCode;
    }

    public String getRentalTypeCode() {
        return rentalTypeCode;
    }
    
    public List<Accounts> getAccountingLines() {
        return this.accountingLines;
    }

    public void setAccountingLines(List<Accounts> accountingLines) {
        this.accountingLines = accountingLines;
    }
    
    public List buildListOfDeletionAwareLists() {
        List managedLists = super.buildListOfDeletionAwareLists();
        managedLists.add(getAccountingLines());

        return managedLists;
    }

    /**
     * toStringMapper
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        //TODO:  Autogenerated method

        return propMap;
    }
    
}
