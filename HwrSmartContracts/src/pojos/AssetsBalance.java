package pojos;

import java.math.BigInteger;

/**
 * 
 * @author mpouma
 *
 */
public class AssetsBalance {
	private BigInteger balance;

	private String address;

	public BigInteger getBalance() {
		return balance;
	}

	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ClassPojo [balance = " + balance + ", address = " + address
				+ "]";
	}
}
