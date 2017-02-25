package test;

import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;

import com.hrw.jaxrs.AbstractContract;

import pojos.Transaction;

public class ExampleContract extends AbstractContract {

	private int mychoice;
	private int opponentschoice;
	private double preis;
	private String partnerAdresse;

	public ExampleContract(File JsonNode, String partner, boolean createasset, double Preis) {

		super(JsonNode, createasset);
		this.preis = Preis;
		this.partnerAdresse=partner;
	}

	public void wetten() {
		System.out.println("player 1 choose a number 1 , 2 or 0");
		Scanner sc = new Scanner(System.in);
		setMychoice(sc.nextInt());
		System.out.println("now player 2 choose a number 1 or 2");
		setOpponentschoice(sc.nextInt());

	}

	public void bewerteErgebnisse() {

		int result = (int) (Math.random() * 2 + 1);
		if (result == this.getMychoice()) {

			if (getAssetBalance(partnerAdresse, getAsset().getAssetId()).compareTo(BigInteger.valueOf((long) preis)) == -1) {
				System.out.println("the user with Address:" + partnerAdresse + " " + "does not have enough money");

			}

			if (getAssetBalance(contractAdresse, getAsset().getAssetId()).compareTo(BigInteger.valueOf((long) preis)) == -1) {
				System.out.println("the user with Address:" + contractAdresse + " " + "does not have enough money");
			}

			if ((getAssetBalance(contractAdresse,getAsset().getAssetId()).compareTo(BigInteger.valueOf((long) preis)) >= 0)
					&& (getAssetBalance(partnerAdresse, asset.getAssetId())
							.compareTo(BigInteger.valueOf((long) preis)) >= 0))

				if (getMychoice() < getOpponentschoice()) {
					this.sendAsset(new Transaction(contractAdresse, partnerAdresse, BigInteger.valueOf((long) preis),
							BigInteger.valueOf((long) preis), "wegen dem Wetten"));
				}

			if (getMychoice() > getOpponentschoice()) {
				this.sendAsset(new Transaction(partnerAdresse, contractAdresse, BigInteger.valueOf((long) preis),
						BigInteger.valueOf((long) preis), "wegen die das Wetten"));
			}

		}
	}

	public int getMychoice() {
		return mychoice;
	}

	public void setMychoice(int mychoice) {
		this.mychoice = mychoice;
	}

	public int getOpponentschoice() {
		return opponentschoice;
	}

	public void setOpponentschoice(int opponentschoice) {
		this.opponentschoice = opponentschoice;
	}

}
