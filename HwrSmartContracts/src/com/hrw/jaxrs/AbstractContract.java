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

public class AbstractContract implements WavesClientAPI {

	private NodePojo Node;
	private String status;
	private Asset asset;
	private String senderAdresse;
	private String partnerAdresse;
	// the contract's type
	private int type = 0;
	private String url;
	private boolean createasset = true;
	private String transactionInString;

	public AbstractContract(String JsonNode, String partner, boolean createasset) {
		this.createasset = createasset;
		if (this.createasset == false) {
			setType(2); // payment with waves
		}
		this.setPartnerAdresse(partner);
		ObjectMapper mapper = new ObjectMapper();

		try {
			Node = mapper.readValue(JsonNode, NodePojo.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setSenderAdresse(Node.getP2p().getMyAddress());
		this.url = "http://localhost:" + Node.getP2p().getPort() + "/" + Node.getP2p().getBindAddress();

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
		this.url = "http://localhost:" + Node.getP2p().getPort() + "/" + Node.getP2p().getBindAddress();
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

			ClientRequest request = new ClientRequest("http://localhost:8080/assets/issue");
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

			ClientRequest request = new ClientRequest("http://localhost:8080/assets/broadcast/issue");
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

	@Override
	@GET
	@Path("/assets/balance/{address}/{id}")
	@Consumes({ "application/json" })
	public BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String id) {
		String output = null;
		try {

			ClientRequest request = new ClientRequest("http://localhost:8080/assets/balance/{adress}/{id}");
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

			ClientRequest request = new ClientRequest("http://localhost:8080/assets/balance/{adress}");
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

}
