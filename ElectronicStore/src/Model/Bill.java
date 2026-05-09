package Model;

import java.sql.Date;

import java.util.ArrayList;

public class Bill {
	private String BillId;
	private Date date;
	private String cashier;
	private ArrayList<BillItems> billList;
	private double total;

	public Bill(String BillId, Date date, String cashier, ArrayList<BillItems> billList, double total) {
		this.BillId = BillId;
		this.date = date;
		this.cashier = cashier;
		this.billList = billList;
		this.total = total;
	}
	
	public double getTotal() {
		return total;
	}
	
	public String getBillId() {
		return BillId;
	}

	public void setBillId(String BillId) {
		this.BillId = BillId;
	}

	public Date getdate() {
		return date;
	}

	public void setdate(Date date) {
		this.date = date;
	}

	public String getcashier() {
		return cashier;
	}

	public void setcashier(String cashier) {
		this.cashier = cashier;
	}

	public ArrayList<BillItems> getItems() {
		return billList;
	}

	public void setItems(ArrayList<BillItems> billList) {
		this.billList = billList;
	}

	public String billItemsToText() {
	    StringBuilder sb = new StringBuilder();
	    for (BillItems item : billList) {
	        sb.append(item.toText()); 
	    }
	    return sb.toString();
	}


	public String toText() {

		return BillId + " " + date + " " + cashier + " " + billItemsToText();
	}

}