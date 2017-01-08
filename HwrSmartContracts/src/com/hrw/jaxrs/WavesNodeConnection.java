package com.hrw.jaxrs;

import java.math.BigInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * 
 * @author mpouma
 *
 */

public interface WavesNodeConnection {

	
	// ceate the Asset for the smart contract Waves-Nodes API-key must be provided!
	
@POST
@Path("/assets/issue")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public String createAsset(Object e);


//issue Asset into the blockchain
@POST
@Path("/assets/broadcast/issue")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public String issueAsset();


//asset transfert from an account to an another
@POST
@Path("/assets/broadcast/transfer")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public void sendAsset();
	

//get  the Assetsbalance
@GET
@Path("/assets/balance/{address}/{id}")
@Consumes
public BigInteger getAssetBalance(@PathParam("address")String address, @PathParam("id")String id);


public void test();

}


