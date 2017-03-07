package main;

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
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.PathParam;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrw.jaxrs.AbstractContract;

import pojos.AssetsBalance;
import pojos.Transaction;

public class ContractExecution {
	private static AbstractContract contract;
	private static String contractExecutorAdresse;
	private static String contractExecutor_api_key;
	private static String contractExecutorRPC_URL;
	public static String contractAdress;
	private static Scanner sc;
	private static long contractExecutionStartTime;

	public static String createContractAccount() {
		System.out.println("Generating  a new account for the contract");
		System.out.println("please  provide the RPC url of your waves node");
		sc = new Scanner(System.in);
		contractExecutorRPC_URL = sc.nextLine();
		System.out.println("please provide your api_key");
		contractExecutor_api_key = sc.next();

		String output = null;

		try {

			URL uri = new URL(contractExecutorRPC_URL + "/addresses");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			conn.setRequestProperty("api_key", contractExecutor_api_key);

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
			System.out.println("adresse for the contract succesfull generated");
			conn.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return output;
	}

	public void sendAsset(String assetId, String recipient, BigInteger amount, File attachment) {
		Transaction transac = new Transaction(assetId, recipient, amount, attachment);
		ObjectMapper mapper = new ObjectMapper();
		String transactionInString = null;
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

			URL uri = new URL(contractExecutorRPC_URL + "/assets/transfer");
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

	public static BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String assetid) {

		try {
			URL uri = new URL(contractExecutorRPC_URL + "/assets/balance/{address}/{id}");
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

	public static void main(String[] args) {
		start();
		if (contract != null) {
			contractAdress = createContractAccount();
			while (contract.getContractTime() >= ((System.currentTimeMillis() - contractExecutionStartTime) / 1000.0)) {
				if (contract.getType() == 1) {
					while (!(getContractType1Status().equals("ok"))) {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() { // Function runs every MINUTES
												// minutes.
								// Run the code you want here
								getContractType1Status(); // If the function you
															// wanted was static
							}
						}, 0, 1000 * 60 * 20);
					}

					contract.doContractsOperations();
				}
				if (contract.getType() == 2) {
					while (!(getContractType1Status().equals("ok"))) {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() { // Function runs every MINUTES
												// minutes.
								// Run the code you want here
								getContractType2Status(); // If the function you
															// wanted was static
							}
						}, 0, 1000 * 60 * 20);
					}

					contract.doContractsOperations();
				}

			}
			if (contract.getType() == 3) {
				while (!(getContractType1Status().equals("ok"))) {
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {

							getContractType3Status();

						}
					}, 0, 1000 * 60 * 20);
				}

				contract.doContractsOperations();

			}
		}

		else
			System.out.println("No contract provided!");

	}

	public String getContractExecutorAdresse() {
		return contractExecutorAdresse;
	}

	public void setContractExecutorAdresse(String contractExecutorAdresse) {
		this.contractExecutorAdresse = contractExecutorAdresse;
	}

	public void setContract(AbstractContract c) {
		contract = c;
	}

	// return the status of the contract
	public static String getContractType1Status() {
		if ((getAssetBalance(contractExecutorAdresse, contract.getOwnerAssetID()))
				.compareTo(contract.getOwnerDeposit()) == -1
				&& (getAssetBalance(contractExecutorAdresse, contract.getPartnerAssetID()))
						.compareTo(contract.getPartnerDeposit()) == -1) {
			contract.setStatus("None of the party has deposit suffisent founds");
		} else {
			if (getAssetBalance(contractExecutorAdresse, contract.getOwnerAssetID())
					.compareTo(contract.getOwnerDeposit()) == -1) {
				contract.setStatus(
						"waiting for the payment of the the contract participant:" + contract.getOwnerAddress());
			}

			if (getAssetBalance(contractExecutorAdresse, contract.getPartnerAssetID())
					.compareTo(contract.getPartnerDeposit()) == -1) {
				contract.setStatus(
						"waiting for the payment of the the contract participant:" + contract.getPartnerAddress());
			}

		}
		if ((getAssetBalance(contractExecutorAdresse, contract.getOwnerAssetID()))
				.compareTo(contract.getOwnerDeposit()) > 1
				&& (getAssetBalance(contractExecutorAdresse, contract.getPartnerAssetID()))
						.compareTo(contract.getPartnerDeposit()) == 1) {
			contract.setStatus("ok");
		}
		return contract.getStatus();
	}

	public static String getContractType2Status() {
		if ((getAssetBalance(contractExecutorAdresse, contract.getCommonAssetID()))
				.compareTo(contract.getOwnerDeposit().add(contract.getPartnerDeposit())) != 0) {
			contract.setStatus("not enought mony deposit on the contract account");
		} else {
			contract.setStatus("ok");
		}

		return contract.getStatus();
	}

	public static String getContractType3Status() {
		if (contract.getWavesBalance(contractExecutorAdresse)
				.compareTo(contract.getOwnerDeposit().add(contract.getPartnerDeposit())) != 0) {
			contract.setStatus("not enought money deposit on the contract account");
		} else {
			contract.setStatus("ok");
		}

		return contract.getStatus();
	}

	public static void start() {
		contractExecutionStartTime = System.currentTimeMillis();
	}
}
