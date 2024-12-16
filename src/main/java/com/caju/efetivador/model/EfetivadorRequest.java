package com.caju.efetivador.model;

public class EfetivadorRequest {
	private String account;
	private Float totalAmount;
	private String mcc;
	private String merchant;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public EfetivadorRequest(String account, Float totalAmount, String mcc, String merchant) {
		super();
		this.account = account;
		this.totalAmount = totalAmount;
		this.mcc = mcc;
		this.merchant = merchant;
	}
	
	
}
