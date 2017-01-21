package pojos;

import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * 
 * @author mpouma
 * 
 * 
 *         "name" - Asset name, can be not unique, length from 4 to 16 bytes, in
 *         plain text. "description" - Asset description, max length is 1000
 *         bytes, in plain text. "sender" - Sender account's address that exists
 *         in the node's wallet, Base58-encoded "senderPublicKey" - Sender
 *         account's public key, Base58-encoded "fee" - Transaction fee for
 *         Asset issue, min = 100000000 (1WAVES). "decimals" - Number of
 *         decimals to represent a piece of asset, max = 8. "quantity" -
 *         Quantity of asset'lets to issue (number of indivisible pieces of
 *         assets). "reissuable" - Boolean flag whether it is possible to issue
 *         additional assets. "signature" - Signature of all transaction data,
 *         Base58-encoded
 *
 */
public class Asset {
	private Timestamp timestamp;
    private String assetId;
	private BigInteger fee;

	private int decimals;

	private String sender;

	private String description;

	private String name;

	private BigInteger quantity;

	private String senderPublicKey;

	private boolean reissuable;

	private String signature;
	
	

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public BigInteger getFee() {
		return fee;
	}

	public void setFee(BigInteger fee) {
		this.fee = fee;
	}

	public int getDecimals() {
		return decimals;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getQuantity() {
		return quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	public String getSenderPublicKey() {
		return senderPublicKey;
	}

	public void setSenderPublicKey(String senderPublicKey) {
		this.senderPublicKey = senderPublicKey;
	}

	public boolean getReissuable() {
		return reissuable;
	}

	public void setReissuable(boolean reissuable) {
		this.reissuable = reissuable;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "ClassPojo [timestamp = " + timestamp + ", fee = " + fee + ", decimals = " + decimals + ", sender = "
				+ sender + ", description = " + description + ", name = " + name + ", quantity = " + quantity
				+ ", senderPublicKey = " + senderPublicKey + ", reissuable = " + reissuable + ", signature = "
				+ signature + "]";
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
}
