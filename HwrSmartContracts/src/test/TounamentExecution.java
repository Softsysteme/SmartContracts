package test;

import java.math.BigInteger;

import main.ContractExecution;

public class TounamentExecution extends ContractExecution {

	TournamentContract tContract = new TournamentContract("tournament", "3N8xGEXxBnfkf6p6CDonY3p132KJfLQtCq6",
			"3N8xGEXxBnfkf6p6CDonY3p132KJfLQtCq6", "assetID", BigInteger.valueOf(10000), BigInteger.valueOf(10000),
			3600);

	public TounamentExecution() {
		super();

		super.setContract(tContract);

	}

}
