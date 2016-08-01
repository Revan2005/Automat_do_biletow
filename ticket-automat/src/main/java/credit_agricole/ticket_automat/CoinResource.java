package credit_agricole.ticket_automat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class CoinResource {
	private TreeMap<BigDecimal, Integer> coinQuantities;
	private ArrayList<BigDecimal> coinsDenominations;
	
	public CoinResource(){
		initializeCoinQuantities();
		coinsDenominations = new ArrayList<BigDecimal>(coinQuantities.keySet());
		Collections.sort(coinsDenominations, Collections.reverseOrder());
	}
	
	public void addCoins(BigDecimal denomination, int numCoins){
		coinQuantities.put(denomination, coinQuantities.get(denomination) + numCoins);
	}
	
	public void removeCoins(BigDecimal denomination, int numCoins) throws LackOfCoinsException{
		if(coinQuantities.get(denomination).compareTo(numCoins) < 0)
			throw new LackOfCoinsException();
		else
			coinQuantities.put(denomination, coinQuantities.get(denomination) - numCoins);
	}
	
	public BigDecimal getBiggestCoinLessOrEqualThan(BigDecimal amount) throws LackOfCoinsException {
		for(BigDecimal coin : coinsDenominations){
			if(coinQuantities.get(coin) > 0){
				if(coin.compareTo(amount) <= 0){
					return coin;
				}
			}
		}
		throw new LackOfCoinsException();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Liczby monet poszczególnych nominałów w automacie:\n");
		for(BigDecimal coinDenomination: coinsDenominations){
			if(coinQuantities.containsKey(coinDenomination)){
				sb.append(coinDenomination + "zł. - " + coinQuantities.get(coinDenomination) +" sztuk\n");
			} else {
				sb.append(coinDenomination + "zł. - 0 sztuk\n");
			}
		}
		BigDecimal sum = calculateSumOfMoney();
		sb.append("Suma: " + sum + "zł.");
		return new String(sb);
	}
	
	public ArrayList<BigDecimal> getCoinsDenominations(){
		return new ArrayList<BigDecimal>(coinsDenominations);
	}
	
	private BigDecimal calculateSumOfMoney(){
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal valueOfDenominaiton;
		Integer coinQuantity;
		for(BigDecimal coinDenomination: coinsDenominations){
			if(coinQuantities.containsKey(coinDenomination)){
				coinQuantity = coinQuantities.get(coinDenomination);
				valueOfDenominaiton = coinDenomination.multiply(new BigDecimal(coinQuantity));
				sum = sum.add(valueOfDenominaiton);
			}
		}
		return sum;
	}
	
	private void initializeCoinQuantities(){
		coinQuantities = new TreeMap<BigDecimal, Integer>();
		coinQuantities.put(new BigDecimal("0.10"), 10);
		coinQuantities.put(new BigDecimal("0.20"), 10);
		coinQuantities.put(new BigDecimal("0.50"), 10);
		coinQuantities.put(new BigDecimal("1.00"), 10);
		coinQuantities.put(new BigDecimal("2.00"), 10);
		coinQuantities.put(new BigDecimal("5.00"), 10);
	}

}
