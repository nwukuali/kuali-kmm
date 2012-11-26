package org.kuali.ext.mm.businessobject;


public class StagingRental extends MMPersistableBusinessObjectBase implements Cloneable{

	/**
     *
     */
    private static final long serialVersionUID = 7635201468828668042L;

	private String checkinRentalId;

	private CheckinDetail checkinDetail;

	private Integer checkinDetailId;
	
	private ReturnDetail returnDetail;
	
    private String serialNumber;
    
    private Integer returnDetailId;

	public StagingRental() {
	}
	
	public ReturnDetail getReturnDetail() {
        return this.returnDetail;
    }

    public void setReturnDetail(ReturnDetail returnDetail) {
        this.returnDetail = returnDetail;
    }

    public Integer getReturnDetailId() {
        return this.returnDetailId;
    }

    public void setReturnDetailId(Integer returnDetailId) {
        this.returnDetailId = returnDetailId;
    }

	public String getCheckinRentalId() {
		return checkinRentalId;
	}

	public void setCheckinRentalId(String checkinRentalId) {
		this.checkinRentalId = checkinRentalId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String checkinSerialNbr) {
		this.serialNumber = checkinSerialNbr;
	}

	public Integer getCheckinDetailId() {
		return checkinDetailId;
	}

	public void setCheckinDetailId(Integer checkinDetailId) {
		this.checkinDetailId = checkinDetailId;
	}

	public CheckinDetail getCheckinDetail() {
		return checkinDetail;
	}

	public void setCheckinDetail(CheckinDetail checkinDetail) {
		this.checkinDetail = checkinDetail;
	}

	public String getDisplayLabel() {
		return this.serialNumber;
	}

    @Override
    public StagingRental clone()  {
        try
        {
            return (StagingRental)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new InternalError(e.toString());
        }
    }


    public void setRentalTrackingDetailId(RentalTrackingDetail rentalDetail, Integer rentalTrackingDetailId) {
        if(rentalDetail instanceof CheckinDetail)
            setCheckinDetailId(rentalTrackingDetailId);
        else if(rentalDetail instanceof ReturnDetail)
            setReturnDetailId(rentalTrackingDetailId);
    }

//    public Integer getRentalTrackingDetailId() {
//        return rentalTrackingDetailId;
//    }

    public void setRentalTrackingDetail(RentalTrackingDetail rentalTrackingDetail) {
        if(rentalTrackingDetail instanceof CheckinDetail)
            setCheckinDetail((CheckinDetail)rentalTrackingDetail);
        else if(rentalTrackingDetail instanceof ReturnDetail)
            setReturnDetail((ReturnDetail)rentalTrackingDetail);
    }

//    public RentalTrackingDetail getRentalTrackingDetail() {
//        return rentalTrackingDetail;
//    }
}
