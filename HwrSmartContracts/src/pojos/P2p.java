package pojos;

import java.math.BigInteger;

/**
 * 
 * @author mpouma
 *
 */

public class P2p {
	private int port;

	private int maxConnections;

	private String[] knownPeers;

	private BigInteger upnpGatewayTimeout;

	private String myAddress;

	private String bindAddress;

	private String nodeName;

	private boolean upnp;

	private BigInteger upnpDiscoverTimeout;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public String[] getKnownPeers() {
		return knownPeers;
	}

	public void setKnownPeers(String[] knownPeers) {
		this.knownPeers = knownPeers;
	}

	public BigInteger getUpnpGatewayTimeout() {
		return upnpGatewayTimeout;
	}

	public void setUpnpGatewayTimeout(BigInteger upnpGatewayTimeout) {
		this.upnpGatewayTimeout = upnpGatewayTimeout;
	}

	public String getMyAddress() {
		return myAddress;
	}

	public void setMyAddress(String myAddress) {
		this.myAddress = myAddress;
	}

	public String getBindAddress() {
		return bindAddress;
	}

	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public boolean getUpnp() {
		return upnp;
	}

	public void setUpnp(boolean upnp) {
		this.upnp = upnp;
	}

	public BigInteger getUpnpDiscoverTimeout() {
		return upnpDiscoverTimeout;
	}

	public void setUpnpDiscoverTimeout(BigInteger upnpDiscoverTimeout) {
		this.upnpDiscoverTimeout = upnpDiscoverTimeout;
	}

	@Override
	public String toString() {
		return "ClassPojo [port = " + port + ", maxConnections = " + maxConnections + ", knownPeers = " + knownPeers
				+ ", upnpGatewayTimeout = " + upnpGatewayTimeout + ", myAddress = " + myAddress + ", bindAddress = "
				+ bindAddress + ", nodeName = " + nodeName + ", upnp = " + upnp + ", upnpDiscoverTimeout = "
				+ upnpDiscoverTimeout + "]";
	}

}
