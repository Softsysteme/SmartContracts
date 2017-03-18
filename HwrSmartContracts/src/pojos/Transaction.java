package pojos;

import java.math.BigInteger;
import java.sql.Timestamp;

import cryptography.HashGenerationException;
import cryptography.HashGeneratorUtils;

/**
 * 
 * @author mpouma
 *
 *
 *         "assetId" [optional] - Asset ID to transfer or omit that param when
 *         transfer WAVES, Base58-encoded "senderPublicKey" - Sender account's
 *         public key, Base58-encoded "recipient" - Recipient account's address,
 *         Base58-encoded "fee" - Transaction fee for Asset transfer, min =
 *         100000 (WAVElets) "feeAssetId" [optional] - Asset ID of transaction
 *         fee. WAVES by default, if empty or absent "amount" - amount of
 *         asset'lets (or wavelets) to transfer "attachment" - Arbitrary
 *         additional data included in transaction, max length is 140 bytes,
 *         Base58-encoded "timestamp" - Transaction timestamp "signature" -
 *         Signature of all transaction data, Base58-encoded
 */
public class Transaction {

	private Timestamp timestamp;

	private BigInteger amount;

	private BigInteger fee;

	private String assetId;

	private String attachment;

	private String senderPublicKey;

	private String signature;

	private String recipient;

	private String feeAssetId;

	// payement wiht asset
	public Transaction(String assetId, String recipient, BigInteger amount, String feeAssetID,
			String senderPublicKey) {

		this.amount = amount;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.assetId = assetId;
		this.feeAssetId = feeAssetID;
		this.attachment = "Smart-contract-transaction";
		this.recipient = recipient;
		this.senderPublicKey = senderPublicKey;
		this.fee = amount.multiply((BigInteger.valueOf((long) 0.05)));
		try {
			this.signature = HashGeneratorUtils.generateSHA256(toString());
		} catch (HashGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Transaction(String assetId, String recipient, BigInteger amount, String senderPublicKey) {

		this.amount = amount;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.assetId = assetId;
		this.attachment = "Smart-contract-transaction";
		this.recipient = recipient;
		this.senderPublicKey = senderPublicKey;
		this.fee = amount.multiply((BigInteger.valueOf((long) 0.05)));
		try {
			this.signature = HashGeneratorUtils.generateSHA256(toString());
		} catch (HashGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Transaction(String recipient, BigInteger amount, String senderPublicKey, String feeAssetID) {

		this.amount = amount;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.attachment = "Smart-contract-transaction-payment-with-Waves";
		this.recipient = recipient;
		this.senderPublicKey = senderPublicKey;
		this.feeAssetId = feeAssetID;
		this.fee = amount.multiply((BigInteger.valueOf((long) 0.05)));
		try {
			this.signature = HashGeneratorUtils.generateSHA256(toString());
		} catch (HashGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Transaction(String recipient, BigInteger amount, String senderPublicKey) {
		this.amount = amount;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.attachment = "Smart-contract-transaction-payment-with-Waves";
		this.recipient = recipient;
		this.senderPublicKey = senderPublicKey;
		this.fee = amount.multiply((BigInteger.valueOf((long) 0.05)));
		try {
			this.signature = HashGeneratorUtils.generateSHA256(toString());
		} catch (HashGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public BigInteger getFee() {
		return fee;
	}

	public void setFee(BigInteger fee) {
		this.fee = fee;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getSenderPublicKey() {
		return senderPublicKey;
	}

	public void setSenderPublicKey(String senderPublicKey) {
		this.senderPublicKey = senderPublicKey;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getFeeAssetId() {
		return feeAssetId;
	}

	public void setFeeAssetId(String feeAssetId) {
		this.feeAssetId = feeAssetId;
	}

	@Override
	public String toString() {
		return " [timestamp = " + timestamp + ", amount = " + amount + ", fee = " + fee + ", assetId = " + assetId
				+ ", attachment = " + attachment + ", senderPublicKey = " + senderPublicKey + ", signature = "
				+ signature + ", recipient = " + recipient + ", feeAssetId = " + feeAssetId + "]";
	}
}
