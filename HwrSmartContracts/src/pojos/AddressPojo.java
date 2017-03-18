package pojos;

/**
 * 
 * @author mpouma
 *
 */
public class AddressPojo
{
    private String address;

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
        return "ClassPojo [address = "+address+"]";
    }
}