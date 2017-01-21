package pojos;

import java.math.BigInteger;

/**
 * 
 * @author mpouma
 *
 *
 *         "assetId" [optional] - Asset ID to transfer or omit that param when
 *         transfer WAVES, Base58-encoded " Base58-encoded "recipient" - Recipient account's address,
 *         Base58-encoded "feeAmount" - Transaction fee for Asset transfer, min
 *         = 100000 (WAVElets) "amount" - amount of asset'lets (or wavelets) to
 *         transfer "attachment" - Arbitrary additional data included in
 *         transaction, max length is 140 bytes
 *        
 *     
 */

public class Transaction {

	private BigInteger amount;

	private BigInteger fee;

	private String assetId;

	private String attachment;

	private String recipient;

	public Transaction(String assetId, String recipient, BigInteger amount, BigInteger fee, String attachment) {

		this.amount = amount;
		this.assetId = assetId;
		this.attachment = attachment;
		this.fee = fee;

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

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Override
	public String toString() {
		return "ClassPojo [timestamp = " + ", amount = " + amount + ", fee = " + fee + ", assetId = " + assetId
				+ ", attachment = " + attachment + ", senderPublicKey = " + ", signature = " + ", recipient = "
				+ recipient + "]";
	}
}
