package main;

/**
 * @author mpouma
 * this class containst all methods relative to the contract execution
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.PathParam;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrw.smarcontracts.AbstractContract;
import com.hrw.smarcontracts.WavesClientAPI;

import pojos.AddressPojo;
import pojos.AssetsBalance;
import pojos.Transaction;
import pojos.Transaction1;
import pojos.WavesBalance;

public class ContractExecution {
	private static AbstractContract contract;
	private static String contractExecutorAdresse;
	private static String contractExecutor_api_key;
	private static String contractExecutorRPC_URL;
	public static String contractAdress;
	private static Scanner sc;
	private static long contractExecutionStartTime;
	static int time;

	/**
	 * 
	 * 
	 * @return the Waves address of the contract's account
	 */
	public static String createContractAccount() {
		System.out.println("Generating  a new account for the contract");
		System.out.println();
		System.out.println("please  provide the RPC url of your waves node");
		sc = new Scanner(System.in);
		contractExecutorRPC_URL = sc.nextLine();
		System.out.println();
		System.out.println("please provide your api_key");
		contractExecutor_api_key = sc.next();
		ObjectMapper mapper = new ObjectMapper();
		String contractInString = null;
		AddressPojo addresspojo = null;

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
			while ((contractInString = br.readLine()) != null) {
				addresspojo = mapper.readValue(contractInString, AddressPojo.class);
				System.out.println(addresspojo.getAddress());

			}
			System.out.println("address for the contract succesfull generated");
			System.out.println();
			conn.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return addresspojo.getAddress();
	}

	/**
	 * 
	 * @param assetId
	 * @param recipient
	 * @param amount
	 * @param attachment
	 *            asset transaction from a waves account to an another waves
	 *            account
	 */
	public static void sendAsset(String assetId, String recipient, BigInteger amount) {
		Transaction transac = new Transaction(assetId, recipient, amount, contractExecutor_api_key);
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

			URL uri = new URL(contractExecutorRPC_URL + "/assets/broadcast/transfer");
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

	/**
	 * payment with Waves
	 * 
	 * @param recipient
	 * @param amount
	 *            waves transaction from an waves account to an another
	 */

	public static void sendWaves(String recipient, BigInteger amount) {
		Transaction transac = new Transaction(recipient, amount, contractExecutor_api_key);
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

			URL uri = new URL(contractExecutorRPC_URL + "/assets/broadcast/transfer");
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

	/**
	 * 
	 * @param address
	 * @param assetid
	 * @return the asset balance of the given account
	 */

	public static BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String assetid) {

		try {
			URL uri = new URL(contractExecutorRPC_URL + "/assets/balance/" + address + "/" + assetid);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			// System.out.println("Output from Server .... \n");
			// if((output = br.readLine()) != null) {
			// System.out.println(output);
			// }
			output = br.readLine();
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

	/**
	 * /addresses/balance/{address}
	 * 
	 * @param address
	 * @return the waves balance of the given account
	 */
	public static BigInteger getWavesBalance(String address) {

		try {
			URL uri = new URL(contractExecutorRPC_URL + "/addresses/balance/" + address);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = "";
			// System.out.println("Output from Server .... \n");
			// while ((output += br.readLine()) != null) {
			//
			// System.out.println(output);
			// }

			output = br.readLine();

			ObjectMapper mapper = new ObjectMapper();
			WavesBalance balance;
			assert (output != null);
			try {

				// Convert Json to object
				// System.out.println(mapper.readValue(output,
				// WavesBalance.class));
				balance = mapper.readValue(output, WavesBalance.class);

			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			conn.disconnect();

			return mapper.readValue(output, WavesBalance.class).getBalance();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	/**
	 * 
	 * @param c:
	 *            the contract to be executed this method execute the contract c
	 *            until the contract lifetime is of or the contract successffull
	 *            terminates
	 */

	public static void execute(AbstractContract c) {
		contract = c;

		if (contract != null)

		{
			contractAdress = createContractAccount();
			time = contract.getContractTime();
			System.out.println("the contract's lifetime is running now");
			System.out.println();

			if (contract.getType() == 1) {
				final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				final Runnable cexec = new Runnable() {
					public void run() {
						if (!contract.getStatus().equals("ok") && time > 0) {

							System.out.println("time remaining:" + " " + time + " " + "seconds");
							System.out.println();
							System.out.println("Contract status:  " + getContractType1Status());
							time -= (contract.getContractTime() / 10);//print the contract status every !/10. of the contract's life time

						} else {

							if (time > 0) {
								contract.doContractsOperations();
								doPayments();
								System.exit(0);
							}

							else {
								System.out.println(
										"Whoops! the contract's lifetime is up and there is still not enought funds on the contract's account.");
								System.out.println();
								System.out.println("The contract is no longer valide.");
								Thread.currentThread().interrupt();
								System.exit(0);
							}
						}

					}
				};

				final ScheduledFuture<?> Handle = scheduler.scheduleAtFixedRate(cexec, 0,
						contract.getContractTime() / 10, TimeUnit.SECONDS);

			}
			if (contract.getType() == 2) {
				final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				final Runnable cexec = new Runnable() {
					public void run() {
						if (!contract.getStatus().equals("ok") && time > 0) {

							System.out.println("time remaining:" + " " + time + " " + "seconds");
							System.out.println();
							System.out.println("Contract status:  " + getContractType2Status());
							time -= (contract.getContractTime() / 10);//print the contract status every !/10. of the contract's life time

						} else {

							if (time > 0) {
								contract.doContractsOperations();
								doPayments();
								System.exit(0);
							}

							else {
								System.out.println(
										"Whoops! the contract's lifetime is up and there is still not enought funds on the contract's account.");
								System.out.println();
								System.out.println("The contract is no longer valide.");
								Thread.currentThread().interrupt();
								System.exit(0);
							}
						}

					}
				};

				final ScheduledFuture<?> Handle = scheduler.scheduleAtFixedRate(cexec, 0,
						contract.getContractTime() / 10, TimeUnit.SECONDS);
			}

			if (contract.getType() == 3) {
				final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				final Runnable cexec = new Runnable() {
					public void run() {
						if (!contract.getStatus().equals("ok") && time > 0) {

							System.out.println("time remaining:" + " " + time + " " + "seconds");
							System.out.println();
							System.out.println("Contract status:  " + getContractType3Status());
							time -= (contract.getContractTime() / 10);//print the contract status every !/10. of the contract's life time

						} else {

							if (time > 0) {
								contract.doContractsOperations();
								doPayments();
								System.exit(0);
							}

							else {
								System.out.println(
										"Whoops! the contract's lifetime is up and there is still not enought funds on the contract's account.");
								System.out.println();
								System.out.println("The contract is no longer valide.");
								Thread.currentThread().interrupt();
								System.exit(0);
							}
						}

					}
				};

				final ScheduledFuture<?> Handle = scheduler.scheduleAtFixedRate(cexec, 0,
						contract.getContractTime() / 10, TimeUnit.SECONDS);

			}

		}

	}

	/**
	 * 
	 * @return
	 */
	public String getContractExecutorAdresse() {
		return contractExecutorAdresse;
	}

	public void setContractExecutorAdresse(String contractExecutorAdresse) {
		this.contractExecutorAdresse = contractExecutorAdresse;
	}

	public void setContract(AbstractContract c) {
		contract = c;
	}

	/**
	 * return the status of a type 1 contract: this method returns "ok" only if
	 * both contract parties respect the terms of the contract
	 * 
	 * @return status
	 */
	public static String getContractType1Status() {
		if ((getAssetBalance(contractAdress, contract.getOwnerAssetID())).compareTo(contract.getOwnerDeposit()) == -1
				&& (getAssetBalance(contractAdress, contract.getPartnerAssetID()))
						.compareTo(contract.getPartnerDeposit()) == -1) {
			contract.setStatus("None of the party has deposit suffisent founds");

		} else {
			if (getAssetBalance(contractAdress, contract.getOwnerAssetID())
					.compareTo(contract.getOwnerDeposit()) == -1) {
				contract.setStatus(
						"waiting for the payment of the the contract participant:" + contract.getOwnerAddress());

			}

			if (getAssetBalance(contractAdress, contract.getPartnerAssetID())
					.compareTo(contract.getPartnerDeposit()) == -1) {
				contract.setStatus(
						"waiting for the payment of the the contract participant:" + contract.getPartnerAddress());

			}

		}
		if ((getAssetBalance(contractAdress, contract.getOwnerAssetID())).compareTo(contract.getOwnerDeposit()) > 1
				&& (getAssetBalance(contractAdress, contract.getPartnerAssetID()))
						.compareTo(contract.getPartnerDeposit()) == 1) {
			contract.setStatus("ok");

		}
		return contract.getStatus();
	}

	/**
	 * return the status of a type 2 contract: this method returns "ok" only if
	 * both contract parties respect the terms of the contract
	 * 
	 * @return status
	 */
	public static String getContractType2Status() {
		if ((getAssetBalance(contractAdress, contract.getCommonAssetID()))
				.compareTo(contract.getOwnerDeposit().add(contract.getPartnerDeposit())) != 0) {
			contract.setStatus("not enought mony deposit on the contract account");

		} else {
			contract.setStatus("ok");

		}

		return contract.getStatus();
	}

	/**
	 * return the status of a type 1 contract: this method returns "ok" only if
	 * both contract parties respect the terms of the contract
	 * 
	 * @return status
	 */
	public static String getContractType3Status() {
		if (getWavesBalance(contractAdress)
				.compareTo(contract.getOwnerDeposit().add(contract.getPartnerDeposit())) != 0) {
			contract.setStatus("not enought money deposit on the contract account");

		} else {
			contract.setStatus("ok");

		}

		return contract.getStatus();
	}

	public static void start() {
		contractExecutionStartTime = System.currentTimeMillis() * 1000;
	}

	/**
	 * for the payment of contracts parties according to the contract's terms.
	 */
	public static void doPayments() {
		if (contract.getType() == 1) {
			if (contract.getOwnerGain().compareTo(BigInteger.valueOf((long) 0)) > 1) {
				sendAsset(contract.getPartnerAssetID(), contract.getOwnerAddress(), contract.getOwnerGain());
			}

			if (contract.getOpponentsGain().compareTo(BigInteger.valueOf((long) 0)) > 1) {
				sendAsset(contract.getOwnerAssetID(), contract.getPartnerAddress(), contract.getOpponentsGain());
			}

		}

		if (contract.getType() == 2) {
			if (contract.getOwnerGain().compareTo(BigInteger.valueOf((long) 0)) > 1) {
				sendAsset(contract.getCommonAssetID(), contract.getOwnerAddress(), contract.getOwnerGain());
			}

			if (contract.getOpponentsGain().compareTo(BigInteger.valueOf((long) 0)) > 1) {
				sendAsset(contract.getCommonAssetID(), contract.getPartnerAddress(), contract.getOpponentsGain());
			}

		}

		if (contract.getType() == 3) {
			if (contract.getOwnerGain().compareTo(BigInteger.valueOf((long) 0)) > 1) {
				sendWaves(contract.getOwnerAddress(), contract.getOwnerGain());
			}

			if (contract.getOpponentsGain().compareTo(BigInteger.valueOf((long) 0)) > 1) {
				sendWaves(contract.getPartnerAddress(), contract.getOpponentsGain());
			}

		}

	}

}