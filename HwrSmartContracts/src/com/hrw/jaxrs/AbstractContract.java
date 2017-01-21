package com.hrw.jaxrs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * @author mpouma
 */
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojos.Asset;
import pojos.AssetsBalance;
import pojos.NodePojo;
import pojos.Transaction;
import pojos.TransactionBroadcast;

public class AbstractContract implements WavesClientAPI {

	protected NodePojo Node;
	protected String status;
	protected Asset asset;
	protected String senderAdresse;
	protected String partnerAdresse;
	// the contract's type
	protected int type = 0;
	protected String url;
	protected boolean createasset = true;
	private String transactionInString;

	public AbstractContract(File JsonNode, String partner, boolean createasset) {
		this.createasset = createasset;
		this.setPartnerAdresse(partner);
		ObjectMapper mapper = new ObjectMapper();

		try {
			Node = mapper.readValue(JsonNode, NodePojo.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setSenderAdresse(Node.getP2p().getMyAddress());
		this.url = "http://"+Node.getP2p().getBindAddress()+":" + Node.getP2p().getPort();
        
		if (this.createasset == false) {
			setType(2); // payment with waves
			System.out.println("all transactions for this contract will be done with waves-currency!!");
		}
		
		else{
			setType(1); //asset muss be created
			asset=new Asset();
			System.out.println("creation of a new asset for the contract please follow the instructions below carefully...");
			System.out.println("some asset properties are set automatically...");
			asset.setSender(senderAdresse);
			asset.setSenderPublicKey(Node.getApiKeyHash());
			asset.setSignature(Node.getGenesisSignature());
			asset.setTimestamp(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			Scanner sc=new Scanner(System.in);
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
			
			
		}
		
		if(asset!=null){
			createAsset(asset);
		}
		

	}

	public AbstractContract(String JsonNode, String assetID, String partner) {

		this.setType(1);
		this.setPartnerAdresse(partner);
		ObjectMapper mapper = new ObjectMapper();

		try {
			Node = mapper.readValue(JsonNode, NodePojo.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setSenderAdresse(Node.getP2p().getMyAddress());
		this.url = "http://"+Node.getP2p().getBindAddress()+":"+Node.getP2p().getPort();
	}

	@Override
	@POST
	@Path("/assets/issue")
	@Produces({ "application/json" })
	public void createAsset(Asset asset) {
		ObjectMapper mapper = new ObjectMapper();
		String assetInString = null;
		assert (asset != null);
		try {

			// Convert object to JSON string
			assetInString = mapper.writeValueAsString(asset);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			// Convert object to JSON string
			assetInString = mapper.writeValueAsString(asset);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			ClientRequest request = new ClientRequest(url + "/assets/issue");
			request.accept("application/json");
			request.body("application/json", assetInString);

			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println("Asset successfull create, now just be sure you issued it into the blockchain...:"
						+ "  " + output);
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	// issue Asset into the blockchain
	@Override
	@POST
	@Path("/assets/broadcast/issue")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public void issueAsset(Asset asset) {
		ObjectMapper mapper = new ObjectMapper();
		String assetInString = null;
		assert (asset != null);
		try {

			// Convert object to JSON string
			assetInString = mapper.writeValueAsString(asset);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			// Convert object to JSON string
			assetInString = mapper.writeValueAsString(asset);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			ClientRequest request = new ClientRequest(url + "/assets/broadcast/issue");
			request.accept("application/json");
			request.body("application/json", assetInString);

			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println("Asset successfull issued:" + "  " + output);
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	// asset transfert from an account to an another
	@Override
	@POST
	@Path("/assets/broadcast/transfer")
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
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

			ClientRequest request = new ClientRequest("http://localhost:8080/assets/broadcast/transfert");
			request.accept("application/json");
			request.body("application/json", transactionInString);

			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println("Transfert successfull:" + "  " + output);
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	@Override
	@GET
	@Path("/assets/balance/{address}/{id}")
	@Consumes({ "application/json" })
	public BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String id) {
		String output = null;
		try {

			ClientRequest request = new ClientRequest(url + "/assets/balance/{adress}/{id}");
			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

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

		// TODO Auto-generated method stub
		return balance.getBalance();
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub

	}

	public String getPartnerAdresse() {
		return partnerAdresse;
	}

	public void setPartnerAdresse(String partnerAdresse) {
		this.partnerAdresse = partnerAdresse;
	}

	public String getSenderAdresse() {
		return senderAdresse;
	}

	public void setSenderAdresse(String senderAdresse) {
		this.senderAdresse = senderAdresse;
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

			ClientRequest request = new ClientRequest(url+"/assets/balance/{adress}");
			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

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

		// TODO Auto-generated method stub
		return balance.getBalance();
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	
		@Override
		@POST
		@Path("/assets/transfer")
		@Produces({ "application/json" })
		@Consumes({ "application/json" })
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

				ClientRequest request = new ClientRequest("http://localhost:8080/assets/broadcast/transfert");
				request.accept("application/json");
				request.body("application/json", transactionInString);

				ClientResponse<String> response = request.post(String.class);

				if (response.getStatus() != 201) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println("Transfert successfull:" + "  " + output);
				}

			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			}

		}
		
	}

