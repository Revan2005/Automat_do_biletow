package credit_agricole.ticket_automat;

import java.util.Scanner;

public class Menu {
	private Scanner input;
	private Order order;
	private TicketResource ticketResource;
	private CoinResource coinResource;
	//private Automat automat;
	
	public Menu(TicketResource ticketResource, CoinResource coinResource){
		this.ticketResource = ticketResource;
		this.coinResource = coinResource;
		input = new Scanner(System.in);
		order = new Order(input, ticketResource, coinResource);
		mainMenu();
	}

	public void mainMenu(){
		//order.cancel();
		System.out.println(" ");
		System.out.println("Wybierz akcję");
		System.out.println("[1] Kup bilet");
		System.out.println("[2] Wyświetl dostępne bilety");
		System.out.println("[3] Wyświetl zasób monet w automacie");
		
		String choiceString = input.nextLine();
		try {
			int choice = Integer.parseInt(choiceString);
			switch (choice) {
			case 1:
				buyTickets();
				break;
			case 2:
				showTickets();
				break;
			case 3:
				showCoins();
				break;
			default:
				mainMenu();
				break;
			}
		} catch (NumberFormatException e) {
			System.out.println("Błędna wartość");
			mainMenu();
		}
	}
	
	private void showCoins(){
		System.out.println(" ");
		System.out.println("Zasób nominałów w automacie:");
		System.out.println(coinResource.toString());
		System.out.println("Aby powrócić do głównego menu wybierz dowolny klawisz");
		String choiceString = input.nextLine();
		mainMenu();
	}
	
	private void showTickets(){
		System.out.println(" ");
		System.out.println("Ceny biletów:");
		System.out.println(ticketResource.toString());
		System.out.println("Aby powrócić do głównego menu wybierz dowolny klawisz");
		String choiceString = input.nextLine();
		mainMenu();
	}
	
	private void buyTickets(){
		System.out.println(" ");
		System.out.println("Wybierz rodzaj");
		System.out.println("[1] Normalny");
		System.out.println("[2] Ulgowy");
		System.out.println("[p] Powrót");
		System.out.println("[m] Menu główne");
		
		String choiceString = input.nextLine();
		if(choiceString.length() == 0){
			System.out.println("Błędna wartość");
			buyTickets();
		}
		else {
			if( (choiceString.charAt(0) == 'm') || (choiceString.charAt(0) == 'p') ){
				mainMenu();
			} else {
				try {
					int choice = Integer.parseInt(choiceString);
					switch (choice) {
					case 1:
						buyNormalTickets();
						break;
					case 2:
						buyReducedTickets();
						break;
					default:
						buyTickets();
						break;
					}
				} catch (NumberFormatException e){
					System.out.println("Błędna wartość");
					buyTickets();
				}
			}
		}
	}
	
	private void buyNormalTickets(){
		System.out.println(" ");
		System.out.println("Bilety pełne, wybierz rodzaj");
		System.out.println("[1] Jednorazowy normalny");
		System.out.println("[2] Jednorazowy specjalny");
		System.out.println("[3] Czasowy 30 min.");
		System.out.println("[4] Czasowy 60 min.");
		System.out.println("[5] Czasowy 90 min.");
		System.out.println("[6] Czasowy 24 h");
		System.out.println("[p] Powrót");
		System.out.println("[m] Menu główne");
		
		String choiceString = input.nextLine();
		if(choiceString.length() == 0){
			System.out.println("Błędna wartość");
			buyNormalTickets();
		}
		else {
			if( (choiceString.charAt(0) == 'm') || (choiceString.charAt(0) == 'p') ){
				if(choiceString.charAt(0) == 'm')
					mainMenu();
				else
					buyTickets();
			} else {
				try{
					int choice = Integer.parseInt(choiceString);
					switch (choice) {
					case 1:
						buyChoosenTickets(TicketType.DISPOSABLE_NORMAL);
						break;
					case 2:
						buyChoosenTickets(TicketType.DISPOSABLE_SPECIAL);
						break;
					case 3: 
						buyChoosenTickets(TicketType.TEMPORAL_30_MIN);
						break;
					case 4: 
						buyChoosenTickets(TicketType.TEMPORAL_60_MIN);
						break;
					case 5: 
						buyChoosenTickets(TicketType.TEMPORAL_90_MIN);
						break;
					case 6: 
						buyChoosenTickets(TicketType.TEMPORAL_24_H);
						break;
					default:
						System.out.println("Błędna wartość");
						buyNormalTickets();
						break;
					}
				} catch (NumberFormatException e){
					System.out.println("Błędna wartość");
					buyNormalTickets();
				}

			}
		}
	}
	
	private void buyReducedTickets(){
		System.out.println(" ");
		System.out.println("Bilety ulgowe, wybierz rodzaj");
		System.out.println("[1] Jednorazowy normalny ulgowy");
		System.out.println("[2] Jednorazowy specjalny ulgowy");
		System.out.println("[3] Czasowy 30 min. ulgowy");
		System.out.println("[4] Czasowy 60 min. ulgowy");
		System.out.println("[5] Czasowy 90 min. ulgowy");
		System.out.println("[6] Czasowy 24 h ulgowy");
		System.out.println("[p] Powrót");
		System.out.println("[m] Menu główne");
		
		String choiceString = input.nextLine();
		if(choiceString.length() == 0){
			System.out.println("Błędna wartość");
			buyReducedTickets();
		}
		else {
			if( (choiceString.charAt(0) == 'm') || (choiceString.charAt(0) == 'p') ){
				if(choiceString.charAt(0) == 'm')
					mainMenu();
				else
					buyTickets();
			} else {
				try {
					int choice = Integer.parseInt(choiceString);
					switch (choice) {
					case 1:
						buyChoosenTickets(TicketType.DISPOSABLE_NORMAL_REDUCED);
						break;
					case 2:
						buyChoosenTickets(TicketType.DISPOSABLE_SPECIAL_REDUCED);
						break;
					case 3: 
						buyChoosenTickets(TicketType.TEMPORAL_30_MIN_REDUCED);
						break;
					case 4: 
						buyChoosenTickets(TicketType.TEMPORAL_60_MIN_REDUCED);
						break;
					case 5: 
						buyChoosenTickets(TicketType.TEMPORAL_90_MIN_REDUCED);
						break;
					case 6: 
						buyChoosenTickets(TicketType.TEMPORAL_24_H_REDUCED);
						break;
					default:
						System.out.println("Błędna wartość");
						buyReducedTickets();
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Błędna wartość");
					buyReducedTickets();
				}
			}
		}
	}
	
	private void buyChoosenTickets(TicketType type){
		System.out.println(" ");
		System.out.println("Wybrano bilet: " + type.toString() + " cena 1 szt.: " + ticketResource.getPrice(type));
		System.out.println("Podaj liczbę biletów");
		System.out.println("[p] Powrót");
		System.out.println("[m] Menu główne");
		String choiceString = input.nextLine();
		if(choiceString.length() == 0){
			System.out.println("Błędna wartość");
			buyReducedTickets();
		} else {
			if( (choiceString.charAt(0) == 'm') || (choiceString.charAt(0) == 'p') ){
				if(choiceString.charAt(0) == 'm')
					mainMenu();
				else
					buyTickets();
			} else {
				try {
					int numTickets = Integer.parseInt(choiceString);
					order.addTickets(type, numTickets);
					order.buy();
					mainMenu();
				} catch (NumberFormatException e) {
					System.out.println("Wprowadź liczbę naturalną");
					buyChoosenTickets(type);
				} catch (LackOfTicketsException e) {
					System.out.println("Brak wybranych biletów w wymaganej ilości");
					mainMenu();
				}
			}
		}
	}
	
}
