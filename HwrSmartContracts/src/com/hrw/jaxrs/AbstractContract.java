package com.hrw.jaxrs;

import java.io.File;
import java.io.IOException;
/**
 * @author mpouma
 */
import java.math.BigInteger;

import com.fasterxml.jackson.databind.ObjectMapper;

import nodepojos.NodePojo;

public class AbstractContract implements WavesNodeConnection {

	private NodePojo Node;

	public AbstractContract(File jsonFile) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			Node = mapper.readValue(jsonFile, NodePojo.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String createAsset(Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String issueAsset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendAsset() {
		// TODO Auto-generated method stub

	}

	@Override
	public BigInteger getAssetBalance(String address, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub

	}

}
