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
import java.util.Calendar;
import java.util.Scanner;
import javax.ws.rs.Path;
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

public class AbstractContract implements WavesClientAPI {

	/**
	 
	 */
	protected NodePojo Node;
	protected String status;
	protected Asset asset;
	protected String contractAdresse;
	// protected String partnerAdresse;
	// the contract's type
	protected int type = 0;
	protected String url;
	protected boolean createasset = true;
	private String transactionInString;

	/**
	 * 
	 * @param JsonNode:the
	 *            node of the contract owner
	 * @param createasset:
	 *            says if the a asset specific currency have to be created
	 */

	public AbstractContract(File JsonNode, boolean createasset) {
		this.createasset = createasset;
		this.status = "blocked";
		// this.setPartnerAdresse(partner);
		ObjectMapper mapper = new ObjectMapper();

		try {
			Node = mapper.readValue(JsonNode, NodePojo.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.url = "http://"+Node.getRpcAddress()+":"+Node.getRpcPort();
		this.setContractAdresse(createContractAccount());

		

		if (this.createasset == false) {
			setType(2); // payment with waves
			System.out.println("all transactions for this contract will be done with waves-currency!!");
		}

		else {
			createAsset(assetCreationDialog());
		}

	}

	public AbstractContract(String JsonNode, String assetID) {
		this.status = "blocked";
		this.setType(1);
		// this.setPartnerAdresse(partner);
		ObjectMapper mapper = new ObjectMapper();

		try {
			Node = mapper.readValue(JsonNode, NodePojo.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setContractAdresse(createContractAccount());
		this.url = "http://"+Node.getRpcAddress() + ":" + Node.getRpcPort();
	}

	@Override
	public void createAsset(Asset asset) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			String assetInString = null;
			URL uri = new URL(url+"/assets/issue");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			assetInString = mapper.writeValueAsString(asset);

			OutputStream os = conn.getOutputStream();
			os.write(assetInString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode()!=308) {
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

	// issue Asset into the blockchain
	@Override
	public void issueAsset(Asset asset) {
		this.setStatus("running");

		try {

			ObjectMapper mapper = new ObjectMapper();
			String assetInString = null;
			URL uri = new URL(url+"/assets/broadcast/issue");
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

	// asset transfert from an account to an another
	@Override
	@Path("/assets/broadcast/transfer")
	public void sendAsset(TransactionBroadcast transac) {

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

			URL uri = new URL(url+"/assets/broadcast/transfer");
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
	public BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String id) {

		try {
			URL uri = new URL(url+"/assets/balance/{address}/{id}");
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

	@Override
	public void test() {
		// TODO Auto-generated method stub

	}

	// public String getPartnerAdresse() {
	// return partnerAdresse;
	// }

	// public void setPartnerAdresse(String partnerAdresse) {
	// this.partnerAdresse = partnerAdresse;
	// }

	public String getSenderAdresse() {
		return contractAdresse;
	}

	public void setContractAdresse(String senderAdresse) {
		this.contractAdresse = senderAdresse;
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
			URL uri = new URL(url+"/assets/balance/{address}");
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

	@Override
	public void sendAsset(Transaction transac) {

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

			URL uri = new URL(url+"/assets/broadcast/transfert");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			transactionInString = mapper.writeValueAsString(transac);

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

	/**
	 * Generate a new account address in the wallet (for the contract). Requires
	 * API_KEY to be provided
	 */
	@Override
	public String createContractAccount() {
		System.out.println("Generating  a new account for the contract");

		String output = null;

		try {

			URL uri = new URL(url+"/addresses");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("api_key", "thierry88");

			OutputStream os = conn.getOutputStream();
//			String header="{\"API-Key\":thierry88}";
//			os.write(header.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode()!=200) {
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

	public Asset assetCreationDialog() {
		setType(1); // asset muss be created
		asset = new Asset();
		System.out
				.println("creation of a new asset for the contract please follow the instructions below carefully...");
		System.out.println("some asset properties are set automatically...");
		asset.setSender(contractAdresse);
		asset.setSenderPublicKey(Node.getApiKeyHash());
		asset.setSignature(Node.getGenesisSignature());
		asset.setTimestamp(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		Scanner sc = new Scanner(System.in);
		System.out.println("please enter the name of the asset  length from 4 to 16 bytes, in plain text...");
		asset.setName(sc.nextLine());
		System.out.println("please enter a description of your asset");
		asset.setDescription(sc.nextLine());
		System.out.println("please enter the quantity of your asset...");
		asset.setQuantity(sc.nextBigInteger());
		System.out.println("please enter a fee for the miners... min = 100000000");
		asset.setFee(sc.nextBigInteger());
		System.out.println("will your asset be reussuable? please answer with true or false");
		asset.setReissuable(sc.nextBoolean());
		System.out.println(" Please enter the Number of decimals to represent a piece of asset, max = 8.");
		asset.setDecimals(sc.nextInt());
		return asset;
	}

}
