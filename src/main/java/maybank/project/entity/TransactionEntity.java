package maybank.project.entity;

import java.io.Serializable;

//import java.math.BigDecimal;
//import java.sql.Date;
//import java.sql.Time;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class TransactionEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Access(AccessType.PROPERTY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "account_number", nullable = true)
	private String accountNumber;
	
	@Column(name = "trx_amount", nullable = true)
	private String trxAmount;
	
	@Column(name = "description", nullable = true)
	private String trxDescription;

	@Column(name = "trx_date", nullable = true)
	private String trxDate;
	
	@Column(name = "trx_time", nullable = true)
	private String trxTime;
	
	@Column(name = "customer_id", nullable = true)
	private String customerID;
	
//	@Column(name = "account_number", length = 45)
//	private int accountNumber;
//	
//	@Column(name = "trx_amount", length = 100)
//	private BigDecimal trxAmount;
//	
//	@Column(name = "description", length = 100)
//	private String trxDescription;
//
//	@Column(name = "trx_date", length = 100)
//	private Date trxDate;
//	
//	@Column(name = "trx_time", length = 100)
//	private Time trxTime;
//	
//	@Column(name = "customer_id", length = 100)
//	private int customerID;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public int getAccountNumber() {
//		return accountNumber;
//	}
//
//	public void setAccountNumber(int accountNumber) {
//		this.accountNumber = accountNumber;
//	}
//
//	public BigDecimal getTrxAmount() {
//		return trxAmount;
//	}
//
//	public void setTrxAmount(BigDecimal trxAmount) {
//		this.trxAmount = trxAmount;
//	}
//
//	public String getTrxDescription() {
//		return trxDescription;
//	}
//
//	public void setTrxDescription(String trxDescription) {
//		this.trxDescription = trxDescription;
//	}
//
//	public Date getTrxDate() {
//		return trxDate;
//	}
//
//	public void setTrxDate(Date trxDate) {
//		this.trxDate = trxDate;
//	}
//
//	public Time getTrxTime() {
//		return trxTime;
//	}
//
//	public void setTrxTime(Time trxTime) {
//		this.trxTime = trxTime;
//	}
//
//	public int getCustomerID() {
//		return customerID;
//	}
//
//	public void setCustomerID(int customerID) {
//		this.customerID = customerID;
//	}
	
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

	public String toString() {
		return "Transaction = id : " + id + ", Account Number : " + accountNumber + ", Transaction Amount : " + trxAmount + 
			   ", Transaction Description : " + trxDescription + ", Transaction Date : " + trxDate + ", Transaction Time : " + trxTime + 
			   ", Customer ID : " + customerID;
	}
}
