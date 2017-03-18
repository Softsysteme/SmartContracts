package testpackage;

import java.math.BigInteger;

import main.ContractExecution;

/**
 * 
 * @author mpouma
 *
 */
public class TounamentExecution extends ContractExecution {

	public static void main(String[] args) {

		TournamentContract tContract = new TournamentContract("tournament", "3N5aHQhRACDn6eJFi3vwWfnpT4B8Cr3KF6V",
				"3N3o61t2UgwqEkZUeoHHpqte8UE1PBQ1ZQr",  BigInteger.valueOf(0), BigInteger.valueOf(0),
				600);

		execute(tContract);
	}
	
}
