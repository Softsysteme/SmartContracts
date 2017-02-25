package com.hrw.jaxrs;

import java.math.BigInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import pojos.Asset;
import pojos.Transaction;
import pojos.TransactionBroadcast;

/**
 * 
 * @author mpouma
 *
 */

public interface WavesClientAPI {

	// ceate the Asset for the smart contract Waves-Nodes API-key must be
	// provided!

	public void createAndIssueAsset(Asset asset);

	// issue Asset into the blockchain

	public void issueAsset(Asset asset);

	// asset transfert from an account to an another

	public void sendAsset(TransactionBroadcast transac);

	// asset transfert from an account to an another
	public void sendAsset(Transaction transac);

	// get the Assetsbalance

	public BigInteger getAssetBalance(@PathParam("address") String address, @PathParam("id") String id);

	// get the waves balance of "address"

	public BigInteger getWavesBalance(@PathParam("address") String address);

	public String createContractAccount();

	

}
