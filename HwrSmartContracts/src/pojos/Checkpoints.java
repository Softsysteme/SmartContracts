package pojos;
/**
 * 
 * @author mpouma
 *
 */

public class Checkpoints {
	private String publicKey;

    public String getPublicKey ()
    {
        return publicKey;
    }

    public void setPublicKey (String publicKey)
    {
        this.publicKey = publicKey;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [publicKey = "+publicKey+"]";
    }

}
