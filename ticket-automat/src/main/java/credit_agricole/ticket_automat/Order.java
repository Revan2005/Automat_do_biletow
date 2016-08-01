package credit_agricole.ticket_automat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Order {
	private Scanner input;
	private TicketResource ticketResource;
	private CoinResource coinResource;
	private TreeMap<TicketType, Integer> choosenTickets;
	private BigDecimal totalPrice;
	private Payment payment;
	
	public Order(Scanner input, TicketResource ticketReaource, CoinResource coinResource){
		this.input = input;
		this.ticketResource = ticketReaource;
		this.coinResource = coinResource;
		choosenTickets = new TreeMap<TicketType, Integer>();
		totalPrice = new BigDecimal("0.00");
		payment = new Payment(input, totalPrice, coinResource);
	}
	
	public void addTickets(TicketType type, int numTickets) throws LackOfTicketsException{
		choosenTickets.put(type, numTickets);
		BigDecimal price = ticketResource.getPrice(type).multiply(new BigDecimal(numTickets));
		totalPrice = totalPrice.add(price);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Twoje zamówienie: \n");
		for (Map.Entry<TicketType, Integer> entry : choosenTickets.entrySet()) {
		    sb.append("Typ: " + entry.getKey() + ", liczba: " + entry.getValue() + "\n");
		}
		sb.append("Łączna cena: " + totalPrice + "\n\n");
		return sb.toString();
	}
	
	public void buy() throws LackOfTicketsException{
		for(TicketType type : TicketType.values()){
			int numOrderedTickets = 0;
			if(choosenTickets.containsKey(type)){
				numOrderedTickets = choosenTickets.get(type);
			}
			if(!ticketResource.ticketsAvaliable(type, numOrderedTickets)){
				cancel();
				throw new LackOfTicketsException();
			}
		}
		payment = new Payment(input, totalPrice, coinResource);
		payment.pay();
		if(payment.isPaymentDone()){
			printTickets();
			resetOrder();
		} else {
			cancel();
		}
	}
	
	public void cancel(){
		choosenTickets = new TreeMap<TicketType, Integer>();
		totalPrice = new BigDecimal("0.00");
		payment.cancel();
	}
	
	private void printTickets(){
		for(TicketType type : TicketType.values()){
			if(choosenTickets.containsKey(type)){
				try {
					ticketResource.removeTickets(type, choosenTickets.get(type));
					totalPrice = new BigDecimal("0.00");
				} catch (LackOfTicketsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("\nDrukowanie biletów typu: " + type + 
									" w liczbie: " + choosenTickets.get(type)+"\n");
			}
		}
	}
	
	private void resetOrder(){
		choosenTickets = new TreeMap<TicketType, Integer>();
		totalPrice = new BigDecimal("0.00");
	}

}
