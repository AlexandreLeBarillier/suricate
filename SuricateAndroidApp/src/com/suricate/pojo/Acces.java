package com.suricate.pojo;

import java.util.Date;

public class Acces {

	private String owner;
	private Boolean isNFC;
	private Boolean isPermanent;
	private Date lastUsage;
	private String code;

	public Acces(String owner, Boolean isNFC, Boolean isPermanent,
			Date lastUsage, String code) {
		super();
		this.owner = owner;
		this.isNFC = isNFC;
		this.isPermanent = isPermanent;
		this.lastUsage = lastUsage;
		this.code = code;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Boolean getIsNFC() {
		return isNFC;
	}

	public void setIsNFC(Boolean isNFC) {
		this.isNFC = isNFC;
	}

	public Boolean getIsPermanent() {
		return isPermanent;
	}

	public void setIsPermanent(Boolean isPermanent) {
		this.isPermanent = isPermanent;
	}

	public Date getLastUsage() {
		return lastUsage;
	}

	public void setLastUsage(Date lastUsage) {
		this.lastUsage = lastUsage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String toNFCString(){
		return "{\"nfccode\":\" "
				+ code
				+ "\",\"validity\":\"" + (isPermanent ? 0 : 1) + "\","
				+ "\"validity_rule\":\"00\","
				+ "\"guest\":\"" + owner + "\"}";
	}
	
	public String toDigicodeString(){
		return "{\"pincode\":\"" + code + "\","
				+ "\"validity\":\"" + (isPermanent ? 0 : 1) + "\","
				+ "\"validity_rule\":\"00\"," + "\"guest\":\""
				+ owner + "\"}";
	}

}
