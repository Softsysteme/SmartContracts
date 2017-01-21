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

	
	// ceate the Asset for the smart contract Waves-Nodes API-key must be provided!
	
@POST
@Path("/assets/issue")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public void createAsset(Asset asset);


//issue Asset into the blockchain
@POST
@Path("/assets/broadcast/issue")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public void issueAsset(Asset asset);


//asset transfert from an account to an another
@POST
@Path("/assets/broadcast/transfer")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public void sendAsset(TransactionBroadcast transac);
	



//asset transfert from an account to an another
@POST
@Path("/assets/transfer")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public void sendAsset(Transaction transac);


//get  the Assetsbalance
@GET
@Path("/assets/balance/{address}/{id}")
@Consumes({ "application/json" })
public BigInteger getAssetBalance(@PathParam("address")String address, @PathParam("id")String id);


//get the waves balance of "address"
@GET
@Path("/addresses/balance/{address}")
@Consumes({ "application/json" })
public BigInteger getWavesBalance(@PathParam("address")String address);


public void test();

}


