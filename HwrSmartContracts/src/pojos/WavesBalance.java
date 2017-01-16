package pojos;

import java.math.BigInteger;

public class WavesBalance
{
    private BigInteger balance;

    private int confirmations;

    private String address;

    public BigInteger getBalance ()
    {
        return balance;
    }

    public void setBalance (BigInteger balance)
    {
        this.balance = balance;
    }

    public int getConfirmations ()
    {
        return confirmations;
    }

	public void setConfirmations(int confirmations)
    {
        this.confirmations = confirmations;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [balance = "+balance+", confirmations = "+confirmations+", address = "+address+"]";
    }
}
			
	