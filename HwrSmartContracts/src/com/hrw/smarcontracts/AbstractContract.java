package com.hrw.smarcontracts;

import java.math.BigInteger;

import pojos.Asset;
import pojos.Transaction;

/**
 * 
 * @author mpouma
 *
 */

public abstract class AbstractContract  {
	/**
	 
	 */
	protected String status = "not born";;
	protected Asset asset;
	protected String partnerAdress;
	protected String ownerAdress;
	// the contract's type
	protected int type = 0;
	protected String url;
	private String transactionInString;
	protected String ownerassetID;
	protected String partnerAssetID;
	public String commonAssetID;
	protected String contractName;
	protected BigInteger ownerDeposit;
	protected BigInteger partnerDeposit;
	protected BigInteger ownerGain;
	protected BigInteger partnerGain;
	// protected String feeAssetID;

	// the time before the contract stops (in second) default 0ne day
	protected int contractTime = 86400;

	/**
	 * 
	 * @param contractName
	 * @param ownerAdress
	 * @param parnetAdress
	 * @param ownerAssetId
	 * @param partnerassetID
	 * @param partnerAdress
	 * @param oDeposit
	 * @param pDeposit
	 */
	public AbstractContract(String contractName, String ownerAdress, String parnetAdress, String ownerAssetId,
			String partnerassetID, String partnerAdress, BigInteger oDeposit, BigInteger pDeposit) {

		this.partnerAdress = partnerAdress;
		this.ownerassetID = ownerAssetId;
		this.contractName = contractName;
		this.partnerAssetID = partnerassetID;
		this.ownerDeposit = oDeposit;
		this.partnerDeposit = pDeposit;
		this.setType(1);

	}

	public AbstractContract(String contractName, String ownerAdress, String partnerAdress, String commonassetID,
			BigInteger oDeposit, BigInteger pDeposit) {

		this.partnerAdress = partnerAdress;
		this.commonAssetID = commonassetID;
		this.contractName = contractName;
		this.ownerAdress = ownerAdress;
		this.ownerDeposit = oDeposit;
		this.partnerDeposit = pDeposit;
		this.setType(2);

	}

	/**
	 * 
	 * @param contractName
	 * @param ownerAdress
	 * @param partnerAdress
	 *            waves for payements
	 */
	public AbstractContract(String contractName, String ownerAdress, String partnerAdress, BigInteger oDeposit,
			BigInteger pDeposit) {
		this.partnerAdress = partnerAdress;
		this.contractName = contractName;
		this.ownerAdress = ownerAdress;
		this.ownerDeposit = oDeposit;
		this.partnerDeposit = pDeposit;
		this.setType(3);

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	
	public int getContractTime() {
		return contractTime;
	}

	public void setContractTime(int T) {
		contractTime = T;
	}

	public String getOwnerAssetID() {
		return ownerassetID;
	}

	public String getPartnerAssetID() {
		return partnerAssetID;
	}

	public String getCommonAssetID() {
		return commonAssetID;
	}

	public BigInteger getOwnerDeposit() {
		return this.ownerDeposit;
	}

	public BigInteger getPartnerDeposit() {
		return this.partnerDeposit;
	}

	public String getOwnerAddress() {
		return this.ownerAdress;
	}

	public String getPartnerAddress() {
		return this.partnerAdress;
	}

	public void setOwnerGain(BigInteger oG) {
		this.ownerGain = oG;
	}

	public BigInteger getOwnerGain() {
		return ownerGain;
	}

	public void setOpponentsGain(BigInteger oG) {
		this.partnerGain = oG;
	}

	public BigInteger getOpponentsGain() {
		return partnerGain;
	}

	/**
	 * please implement hier all the operations the contract is supposed to
	 * achieve
	 */
	//@Override
	public abstract void doContractsOperations();

}
