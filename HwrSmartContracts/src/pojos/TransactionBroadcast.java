package pojos;

import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 
 * @author mpouma
 *
 *
 *         "assetId" [optional] - Asset ID to transfer or omit that param when
 *         transfer WAVES, Base58-encoded "senderPublicKey" - Sender account's
 *         public key, Base58-encoded "recipient" - Recipient account's address,
 *         Base58-encoded "feeAmount" - Transaction fee for Asset transfer, min
 *         = 100000 (WAVElets) "amount" - amount of asset'lets (or wavelets) to
 *         transfer "attachment" - Arbitrary additional data included in
 *         transaction, max length is 140 bytes, Base58-encoded "timestamp" -
 *         Transaction timestamp "signature" - Signature of all transaction
 *         data, Base58-encoded
 */
public class TransactionBroadcast extends Transaction {
	private Timestamp timestamp;

	private BigInteger amount;

	private BigInteger fee;

	private String assetId;

	private File attachment;

	private String senderPublicKey;

	private String signature;

	private String recipient;

	public TransactionBroadcast(String senderPublicKey, String signature, String assetId, String recipient,
			BigInteger amount, File attachment) {
		super(assetId, recipient, amount,  attachment);

		this.senderPublicKey = senderPublicKey;
		this.signature = signature;
		this.timestamp = new Timestamp(System.currentTimeMillis());
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

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
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

	@Override
	public String toString() {
		return "ClassPojo [timestamp = " + timestamp + ", amount = " + amount + ", fee = " + fee + ", assetId = "
				+ assetId + ", attachment = " + attachment + ", senderPublicKey = " + senderPublicKey + ", signature = "
				+ signature + ", recipient = " + recipient + "]";
	}
}
