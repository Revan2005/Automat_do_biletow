package credit_agricole.ticket_automat;

public class Automat {
	private TicketResource ticketResource;
	private CoinResource coinResource;
	private Menu menu;
	//private Order order;
	//private Payment payment;
	
	public Automat(){
		ticketResource = new TicketResource();
		coinResource = new CoinResource();
		menu = new Menu(ticketResource, coinResource);
	}

}
