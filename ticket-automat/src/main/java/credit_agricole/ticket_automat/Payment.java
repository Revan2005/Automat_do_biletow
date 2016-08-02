package credit_agricole.ticket_automat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Payment {
	private Scanner input;
	private BigDecimal totalPrice;
	private CoinResource coinResource;
	private BigDecimal amountOfInsertedMoney;
	private boolean paymentDone = false;
	
	public Payment(Scanner input, BigDecimal totalPrice, CoinResource coinResource){
		this.input = input;
		this.totalPrice = totalPrice;
		this.coinResource = coinResource;
		amountOfInsertedMoney = new BigDecimal("0.00");
	}
	
	public boolean isPaymentDone(){
		return paymentDone;
	}
	
	public void pay(){
		insertCoins();
	}
	
	public void cancel(){
		try {
			paymentDone = false;
			spendTheRest(amountOfInsertedMoney);
		} catch (LackOfCoinsException e) {
			System.out.println("To nie może się pojawić!!");
		}
	}
	
	private void insertCoins(){
		System.out.println(" ");
		System.out.println("Cena: "+totalPrice+"zł. Dotychczas wpłacono: " + amountOfInsertedMoney + "zł. \nWybierz nominał:\naby anulować wybierz a");
		ArrayList<BigDecimal> coinsDenominations = coinResource.getCoinsDenominations();
		HashMap<Integer, BigDecimal> denominationsMenu = new HashMap<Integer, BigDecimal>();
		int i = 1;
		for(BigDecimal denomination : coinsDenominations){
			denominationsMenu.put(i, denomination);
			System.out.println("[" + i + "] " + denomination);
			i++;
		}
		
		String choiceString = input.nextLine();
		if(choiceString.length() == 0){
			System.out.println("Błędna wartość");
			insertCoins();
		} else {
			if(choiceString.charAt(0) == 'a'){
					;
			} else {
				try {
					int choice = Integer.parseInt(choiceString);
					if( (choice < 1) || (choice > coinsDenominations.size()) ){
						System.out.println("Niewłaściwy klawisz");
						insertCoins();
					} else {
						insertSpecificCoins(denominationsMenu.get(choice));
					}
				} catch (Exception e) {
					System.out.println("Niewłaściwy format wejścia");
					insertCoins();
				}
			}
		}
	}
	
	private void insertSpecificCoins(BigDecimal coin){
		System.out.println(" ");
		System.out.println("Wybrany nominał: " + coin);
		System.out.println("Podaj liczbę monet,\naby anulować wybierz a,\naby powrócić do poprzedniego menu wybierz p:");
		String choiceString = input.nextLine();
		if(choiceString.length() ==0){
			System.out.println("Błędna wartość");
			insertSpecificCoins(coin);
		} else {
			if( (choiceString.charAt(0) == 'a') || (choiceString.charAt(0) == 'p') ){
				if(choiceString.charAt(0) == 'a')
					;//cancel();
				else
					insertCoins();
			} else {
				try {
					int choosenNumCoins = Integer.parseInt(choiceString);
					BigDecimal valueOfCoins = coin.multiply(new BigDecimal(choosenNumCoins));
					addToCoinResource(coin, choosenNumCoins);
					amountOfInsertedMoney = amountOfInsertedMoney.add(valueOfCoins);
					if(amountOfInsertedMoney.compareTo(totalPrice) >= 0)
						finalizeTransaction();
					else
						insertCoins();
				} catch (NumberFormatException e) {
					System.out.println("Niewłaściwy format wejścia");
					insertSpecificCoins(coin);
				}
			}
		}
	}
	
	private void addToCoinResource(BigDecimal coin, int numCoins){
		coinResource.addCoins(coin, numCoins);
	}
	
	private void removeFromCoinResource(BigDecimal coin, int numCoins) throws LackOfCoinsException{
		coinResource.removeCoins(coin, numCoins);
	}
	
	private void finalizeTransaction(){
		BigDecimal rest = amountOfInsertedMoney.subtract(totalPrice);
		try {
			spendTheRest(rest);
			amountOfInsertedMoney = BigDecimal.ZERO;
			paymentDone = true;
		} catch (LackOfCoinsException e) {
			System.out.println("Brak monet w automacie do wydania reszty");
			//cancel();
		}
	}
	
	private void spendTheRest(BigDecimal rest) throws LackOfCoinsException{
		System.out.println("Cena: " + totalPrice + "zł." +
							" wpłacono: " + amountOfInsertedMoney + "zł. " + 
							"reszta: " + rest);
		//System.out.println(coinResource.toString());
		BigDecimal coin;
		if(isPossibleToSpendTheRest(rest)){
			while(rest.compareTo(BigDecimal.ZERO) > 0){
				coin = coinResource.getBiggestCoinLessOrEqualThan(rest);
				removeFromCoinResource(coin, 1);
				ejectCoin(coin);
				rest = rest.subtract(coin);
			}
		} else {
			throw new LackOfCoinsException();
		}
	}
	
	private void ejectCoin(BigDecimal coin){
		System.out.println("Automat wyrzuca monetę: " + coin + "zł");
	}
	
	private boolean isPossibleToSpendTheRest(BigDecimal rest){
		BigDecimal tmpRest = rest.multiply(new BigDecimal("1"));
		BigDecimal coin;
		while(tmpRest.compareTo(BigDecimal.ZERO) > 0){
			try {
				coin = coinResource.getBiggestCoinLessOrEqualThan(tmpRest);
				tmpRest = tmpRest.subtract(coin);
			} catch (LackOfCoinsException e) {
				return false;
			}
		}
		if(tmpRest.compareTo(BigDecimal.ZERO) < 0)
			return false;
		return true;
	}
	
}
