package maybank.project.dto;

import java.io.Serializable;

public class TransactionDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String accountNumber;
	private String trxAmount;
	private String trxDescription;
	private String trxDate;
	private String trxTime;
	private String customerID;
	
	public TransactionDTO() {
    }
	
	public TransactionDTO(String accountNumber, String trxAmount, String trxDescription, String trxDate, String trxTime, String customerID) {
        this.accountNumber = accountNumber;
        this.trxAmount = trxAmount;
        this.trxDescription = trxDescription;
        this.trxDate = trxDate;
        this.trxTime = trxTime;
        this.customerID = customerID;
    }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}
	public String getTrxDescription() {
		return trxDescription;
	}
	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}
	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
	public String getTrxTime() {
		return trxTime;
	}
	public void setTrxTime(String trxTime) {
		this.trxTime = trxTime;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
}
