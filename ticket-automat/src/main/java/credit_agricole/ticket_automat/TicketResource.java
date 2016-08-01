package credit_agricole.ticket_automat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class TicketResource {
	private HashMap<TicketType, BigDecimal> ticketPrices;
	private HashMap<TicketType, Integer> ticketQuantities;
	private BigDecimal discountBD = new BigDecimal("0.35");
	
	public TicketResource(){
		initializeTicketPrices();
		initializeTicketQuantities();
	}
	
	public BigDecimal getPrice(TicketType type){
		return ticketPrices.get(type);
	}
	
	public boolean ticketsAvaliable(TicketType type, int numTickets){
		if(ticketQuantities.get(type) >= numTickets)
			return true;
		return false;
	}
	
	public int getNumAvaliableTickets(TicketType type){
		return ticketQuantities.get(type);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Cena       Liczba            Rodzaj \n");
		for(TicketType type : TicketType.values()){
			if(ticketQuantities.containsKey(type)){
				sb.append(ticketPrices.get(type) + "zł.    " + ticketQuantities.get(type) + " sztuk      " + type + "\n");
			} else {
				sb.append(ticketPrices.get(type) + "zł.    0 sztuk      " + type + "\n");
			}
		}
		return new String(sb);
	}
	
	public void removeTickets(TicketType type, int numTickets) throws LackOfTicketsException{
		if(ticketQuantities.get(type).compareTo(numTickets) < 0)
			throw new LackOfTicketsException();
		else
			ticketQuantities.put(type, ticketQuantities.get(type) - numTickets);
	}
	
	private void initializeTicketPrices(){
		ticketPrices = new HashMap<TicketType, BigDecimal>();
		BigDecimal disposableNormalPrice = new BigDecimal("3.00");
		BigDecimal disposableSpecialPrice = new BigDecimal("3.20");
		BigDecimal temporal30minPrice = new BigDecimal("3.00");
		BigDecimal temporal60minPrice = new BigDecimal("4.50");
		BigDecimal temporal90minPrice = new BigDecimal("6.00");
		BigDecimal temporal24hPrice = new BigDecimal("11.00");
		//standard tickets
		ticketPrices.put(TicketType.DISPOSABLE_NORMAL, disposableNormalPrice);
		ticketPrices.put(TicketType.DISPOSABLE_SPECIAL, disposableSpecialPrice);
		ticketPrices.put(TicketType.TEMPORAL_30_MIN, temporal30minPrice);
		ticketPrices.put(TicketType.TEMPORAL_60_MIN, temporal60minPrice);
		ticketPrices.put(TicketType.TEMPORAL_90_MIN, temporal90minPrice);
		ticketPrices.put(TicketType.TEMPORAL_24_H, temporal24hPrice);
		//reduced tickets
		ticketPrices.put(TicketType.DISPOSABLE_NORMAL_REDUCED, calculateReducedPrice(disposableNormalPrice));
		ticketPrices.put(TicketType.DISPOSABLE_SPECIAL_REDUCED, calculateReducedPrice(disposableSpecialPrice));
		ticketPrices.put(TicketType.TEMPORAL_30_MIN_REDUCED, calculateReducedPrice(temporal30minPrice));
		ticketPrices.put(TicketType.TEMPORAL_60_MIN_REDUCED, calculateReducedPrice(temporal60minPrice));
		ticketPrices.put(TicketType.TEMPORAL_90_MIN_REDUCED, calculateReducedPrice(temporal90minPrice));
		ticketPrices.put(TicketType.TEMPORAL_24_H_REDUCED, calculateReducedPrice(temporal24hPrice));
	}
	
	private BigDecimal calculateReducedPrice(BigDecimal standardPrice){
		BigDecimal amountOfDiscount = standardPrice.multiply(discountBD);
		BigDecimal reducedPrice = standardPrice.subtract(amountOfDiscount);
		BigDecimal roundedPrice = reducedPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		return roundedPrice;
	}
	
	private void initializeTicketQuantities(){
		ticketQuantities = new HashMap<TicketType, Integer>();
		//standard tickets
		ticketQuantities.put(TicketType.DISPOSABLE_NORMAL, 10);
		ticketQuantities.put(TicketType.DISPOSABLE_SPECIAL, 10);
		ticketQuantities.put(TicketType.TEMPORAL_30_MIN, 10);
		ticketQuantities.put(TicketType.TEMPORAL_60_MIN, 10);
		ticketQuantities.put(TicketType.TEMPORAL_90_MIN, 10);
		ticketQuantities.put(TicketType.TEMPORAL_24_H, 10);	
		//reduced tickets
		ticketQuantities.put(TicketType.DISPOSABLE_NORMAL_REDUCED, 10);
		ticketQuantities.put(TicketType.DISPOSABLE_SPECIAL_REDUCED, 10);
		ticketQuantities.put(TicketType.TEMPORAL_30_MIN_REDUCED, 10);
		ticketQuantities.put(TicketType.TEMPORAL_60_MIN_REDUCED, 10);
		ticketQuantities.put(TicketType.TEMPORAL_90_MIN_REDUCED, 10);
		ticketQuantities.put(TicketType.TEMPORAL_24_H_REDUCED, 10);
	}

}
