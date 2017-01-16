package pojos;

import java.math.BigInteger;

/**
 * 
 * @author mpouma
 *
 */

public class NodePojo {

	private Checkpoints checkpoints;

	private BigInteger blockGenerationDelay;

	private boolean rpcEnabled;

	private String loggingLevel;

	private String walletSeed;

	private String walletPassword;

	private String rpcPort;

	private BigInteger historySynchronizerTimeout;

	private String dataDir;

	private boolean cors;

	private boolean offlineGeneration;

	private P2p p2p;

	private String rpcAddress;

	private String history;

	private String apiKeyHash;

	private boolean testnet;

	private String walletDir;

	private int maxRollback;

	private String genesisSignature;

	public Checkpoints getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(Checkpoints checkpoints) {
		this.checkpoints = checkpoints;
	}

	public BigInteger getBlockGenerationDelay() {
		return blockGenerationDelay;
	}

	public void setBlockGenerationDelay(BigInteger blockGenerationDelay) {
		this.blockGenerationDelay = blockGenerationDelay;
	}

	public boolean getRpcEnabled() {
		return rpcEnabled;
	}

	public void setRpcEnabled(boolean rpcEnabled) {
		this.rpcEnabled = rpcEnabled;
	}

	public String getLoggingLevel() {
		return loggingLevel;
	}

	public void setLoggingLevel(String loggingLevel) {
		this.loggingLevel = loggingLevel;
	}

	public String getWalletSeed() {
		return walletSeed;
	}

	public void setWalletSeed(String walletSeed) {
		this.walletSeed = walletSeed;
	}

	public String getWalletPassword() {
		return walletPassword;
	}

	public void setWalletPassword(String walletPassword) {
		this.walletPassword = walletPassword;
	}

	public String getRpcPort() {
		return rpcPort;
	}

	public void setRpcPort(String rpcPort) {
		this.rpcPort = rpcPort;
	}

	public BigInteger getHistorySynchronizerTimeout() {
		return historySynchronizerTimeout;
	}

	public void setHistorySynchronizerTimeout(BigInteger historySynchronizerTimeout) {
		this.historySynchronizerTimeout = historySynchronizerTimeout;
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public boolean getCors() {
		return cors;
	}

	public void setCors(boolean cors) {
		this.cors = cors;
	}

	public boolean getOfflineGeneration() {
		return offlineGeneration;
	}

	public void setOfflineGeneration(boolean offlineGeneration) {
		this.offlineGeneration = offlineGeneration;
	}

	public P2p getP2p() {
		return p2p;
	}

	public void setP2p(P2p p2p) {
		this.p2p = p2p;
	}

	public String getRpcAddress() {
		return rpcAddress;
	}

	public void setRpcAddress(String rpcAddress) {
		this.rpcAddress = rpcAddress;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getApiKeyHash() {
		return apiKeyHash;
	}

	public void setApiKeyHash(String apiKeyHash) {
		this.apiKeyHash = apiKeyHash;
	}

	public boolean getTestnet() {
		return testnet;
	}

	public void setTestnet(boolean testnet) {
		this.testnet = testnet;
	}

	public String getWalletDir() {
		return walletDir;
	}

	public void setWalletDir(String walletDir) {
		this.walletDir = walletDir;
	}

	public int getMaxRollback() {
		return maxRollback;
	}

	public void setMaxRollback(int maxRollback) {
		this.maxRollback = maxRollback;
	}

	public String getGenesisSignature() {
		return genesisSignature;
	}

	public void setGenesisSignature(String genesisSignature) {
		this.genesisSignature = genesisSignature;
	}

	@Override
	public String toString() {
		return "ClassPojo [checkpoints = " + checkpoints + ", blockGenerationDelay = " + blockGenerationDelay
				+ ", rpcEnabled = " + rpcEnabled + ", loggingLevel = " + loggingLevel + ", walletSeed = " + walletSeed
				+ ", walletPassword = " + walletPassword + ", rpcPort = " + rpcPort + ", historySynchronizerTimeout = "
				+ historySynchronizerTimeout + ", dataDir = " + dataDir + ", cors = " + cors + ", offlineGeneration = "
				+ offlineGeneration + ", p2p = " + p2p + ", rpcAddress = " + rpcAddress + ", history = " + history
				+ ", apiKeyHash = " + apiKeyHash + ", testnet = " + testnet + ", walletDir = " + walletDir
				+ ", maxRollback = " + maxRollback + ", genesisSignature = " + genesisSignature + "]";
	}
}