package nodepojos;
/**
 * 
 * @author mpouma
 *
 */

public class P2p {
	private String port;

	private String maxConnections;

	private String[] knownPeers;

	private String upnpGatewayTimeout;

	private String myAddress;

	private String bindAddress;

	private String nodeName;

	private String upnp;

	private String upnpDiscoverTimeout;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(String maxConnections) {
		this.maxConnections = maxConnections;
	}

	public String[] getKnownPeers() {
		return knownPeers;
	}

	public void setKnownPeers(String[] knownPeers) {
		this.knownPeers = knownPeers;
	}

	public String getUpnpGatewayTimeout() {
		return upnpGatewayTimeout;
	}

	public void setUpnpGatewayTimeout(String upnpGatewayTimeout) {
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

	public String getUpnp() {
		return upnp;
	}

	public void setUpnp(String upnp) {
		this.upnp = upnp;
	}

	public String getUpnpDiscoverTimeout() {
		return upnpDiscoverTimeout;
	}

	public void setUpnpDiscoverTimeout(String upnpDiscoverTimeout) {
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
