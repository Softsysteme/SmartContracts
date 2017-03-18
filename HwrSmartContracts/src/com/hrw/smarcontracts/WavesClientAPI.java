package com.hrw.smarcontracts;

import java.io.File;
import java.math.BigInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import pojos.Asset;
import pojos.Transaction1;
import pojos.Transaction;

/**
 * 
 * @author mpouma
 *
 */

public interface WavesClientAPI {

	// issue Asset into the blockchain api-key muss be provided

//	public void issueAsset(Asset asset);

	// asset transfert from an account to an another

	public void sendAsset(Transaction transac);

	// asset transfert from an account to an another
	public void sendAsset(String assetId, String recipient, BigInteger amount, File attachment);

	// asset transfert without attachement
	public void sendAsset(String assetId, String recipient, BigInteger amount);

	// get the Assetsbalance

	public BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String id);

	// get the waves balance of "address"

	public BigInteger getWavesBalance(@PathParam("address") String address);

	public String createContractAccount();
	
	//contract logik
	public void doContractsOperations();
		
	

}
