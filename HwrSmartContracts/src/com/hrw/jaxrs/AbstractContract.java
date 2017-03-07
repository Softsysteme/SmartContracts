package com.hrw.jaxrs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.ws.rs.PathParam;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojos.Asset;
import pojos.AssetsBalance;
import pojos.NodePojo;
import pojos.Transaction;
import pojos.TransactionBroadcast;

/**
 * 
 * @author mpouma
 *
 */

public abstract class AbstractContract implements WavesClientAPI {
	/**
	 
	 */
	protected NodePojo Node;
	protected String status = "not born";;
	protected Asset asset;
	protected String partnerAdress;
	protected String ownerAdress;
	// the contract's type
	protected int type = 0;
	protected String url;
	private String transactionInString;
	protected String ownerassetID;
	protected String partnerAssetID;
	public String commonAssetID;
	protected String contractName;
	protected BigInteger ownerDeposit;
	protected BigInteger partnerDeposit;
	protected BigInteger ownerGain;
	protected BigInteger partnerGain;

	// the time before the contract stops (in second) default 0ne day
	protected int contractTime = 86400;

	/**
	 * 
	 * @param partnerAssetID
	 * @param partnerAdress
	 * @param JsonNode:the
	 *            node of the contract owner
	 * @param createasset:
	 *            says if the a asset specific currency have to be created
	 */

	public AbstractContract(String contractName, String ownerAdress, String parnetAdress, String ownerAssetId,
			String partnerassetID, String partnerAdress, BigInteger oDeposit, BigInteger pDeposit) {

		this.partnerAdress = partnerAdress;
		this.ownerassetID = ownerAssetId;
		this.contractName = contractName;
		this.partnerAssetID = partnerassetID;
		this.ownerDeposit = oDeposit;
		this.partnerDeposit = pDeposit;
		this.setType(1);

	}

	public AbstractContract(String contractName, String ownerAdress, String partnerAdress, String commonassetID,
			BigInteger oDeposit, BigInteger pDeposit) {

		this.partnerAdress = partnerAdress;
		this.commonAssetID = commonassetID;
		this.contractName = contractName;
		this.ownerAdress = ownerAdress;
		this.ownerDeposit = oDeposit;
		this.partnerDeposit = pDeposit;
		this.setType(2);

	}

	/**
	 * 
	 * @param contractName
	 * @param ownerAdress
	 * @param partnerAdress
	 *            waves for payements
	 */
	public AbstractContract(String contractName, String ownerAdress, String partnerAdress, BigInteger oDeposit,
			BigInteger pDeposit) {
		this.partnerAdress = partnerAdress;
		this.contractName = contractName;
		this.ownerAdress = ownerAdress;
		this.ownerDeposit = oDeposit;
		this.partnerDeposit = pDeposit;
		this.setType(3);

	}

	// issue Asset into the blockchain
	@Override
	public void issueAsset(Asset asset) {
		this.setStatus("running");

		try {

			ObjectMapper mapper = new ObjectMapper();
			String assetInString = null;
			URL uri = new URL(url + "/assets/broadcast/issue");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			assetInString = mapper.writeValueAsString(asset);

			OutputStream os = conn.getOutputStream();
			os.write(assetInString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// asset transfert from an account to an another with attachement
	@Override
	public void sendAsset(String assetId, String recipient, BigInteger amount, File attachment) {
		Transaction transac = new Transaction(assetId, recipient, amount, attachment);
		ObjectMapper mapper = new ObjectMapper();
		transactionInString = null;
		assert (transac != null);
		try {

			// Convert object to JSON string
			transactionInString = mapper.writeValueAsString(transac);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			URL uri = new URL(url + "/assets/transfer");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(transactionInString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// transfert asset from an account to an another without attachement
	@Override
	public void sendAsset(String assetId, String recipient, BigInteger amount) {
		Transaction transac = new Transaction(assetId, recipient, amount);
		ObjectMapper mapper = new ObjectMapper();
		transactionInString = null;
		assert (transac != null);
		try {

			// Convert object to JSON string
			transactionInString = mapper.writeValueAsString(transac);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			URL uri = new URL(url + "/assets/transfer");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(transactionInString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String assetid) {

		try {
			URL uri = new URL(url + "/assets/balance/{address}/{id}");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			ObjectMapper mapper = new ObjectMapper();
			AssetsBalance balance = null;
			assert (output != null);
			try {

				// Convert Json to object
				balance = mapper.readValue(output, AssetsBalance.class);

			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			conn.disconnect();

			return balance.getBalance();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public BigInteger getWavesBalance(@PathParam("address") String address) {
		String output = null;

		try {
			URL uri = new URL(url + "/assets/balance/{address}");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			ObjectMapper mapper = new ObjectMapper();
			AssetsBalance balance = null;
			assert (output != null);
			try {

				// Convert Json to object
				balance = mapper.readValue(output, AssetsBalance.class);

			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			conn.disconnect();

			return balance.getBalance();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	/**
	 * Generate a new account address in the wallet (for the contract). Requires
	 * API_KEY to be provided
	 */
	@Override
	public String createContractAccount() {
		System.out.println("Generating  a new account for the contract");

		String output = null;

		try {

			URL uri = new URL(url + "/addresses");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			System.out.println("please provide your api key");
			Scanner sc = new Scanner(System.in);

			conn.setRequestProperty("api_key", sc.next());

			OutputStream os = conn.getOutputStream();
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return output;
	}

	@Override
	public void sendAsset(TransactionBroadcast transac) {
		// TODO Auto-generated method stub

	}

	public int getContractTime() {
		return contractTime;
	}

	public void setContractTime(int T) {
		contractTime = T;
	}

	public String getOwnerAssetID() {
		return ownerassetID;
	}

	public String getPartnerAssetID() {
		return partnerAssetID;
	}

	public String getCommonAssetID() {
		return commonAssetID;
	}

	public BigInteger getOwnerDeposit() {
		return this.ownerDeposit;
	}

	public BigInteger getPartnerDeposit() {
		return this.partnerDeposit;
	}

	public String getOwnerAddress() {
		return this.ownerAdress;
	}

	public String getPartnerAddress() {
		return this.partnerAdress;
	}

	public void setOwnerGain(BigInteger oG) {
		this.ownerGain = oG;
	}

	public BigInteger getOwnerGain() {
		return ownerGain;
	}

	public void setOpponentsGain(BigInteger oG) {
		this.partnerGain = oG;
	}

	public BigInteger getOpponentsGain() {
		return partnerGain;
	}

	/**
	 * please implement hier all the operations the contract is supposed to
	 * achieve
	 */
	@Override
	public abstract void doContractsOperations();

}
