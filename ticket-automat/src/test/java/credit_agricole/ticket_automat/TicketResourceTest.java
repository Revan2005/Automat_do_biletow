package credit_agricole.ticket_automat;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class TicketResourceTest extends TestCase {

	public void testRemoveTickets() throws LackOfTicketsException{
		TicketResource tr = new TicketResource();
		TicketType type = TicketType.TEMPORAL_30_MIN_REDUCED;
		
		//after initialization it should be 10 tickets (look at constructor of TicketResource)
		assertEquals(10, tr.getNumAvaliableTickets(type));
		
		//after removing of 5 tickets it should be only 5 left
		tr.removeTickets(type, 5);
		assertEquals(5, tr.getNumAvaliableTickets(type));

		//after removing of 2 tickets it should be only 3 left
		tr.removeTickets(type, 2);
		assertEquals(3, tr.getNumAvaliableTickets(type));
		
		//after removing of 1 tickets it should be only 2 left
		tr.removeTickets(type, 1);
		assertEquals(2, tr.getNumAvaliableTickets(type));
		
		//after removing of 2 tickets it should be only 0 left
		tr.removeTickets(type, 2);
		assertEquals(0, tr.getNumAvaliableTickets(type));
		
		//if we try to remove more than 10 tickets from newly initialized ticket resource it should be exception
	    tr = new TicketResource();
	    //it should be 10 tickets after initialization
	    assertEquals(10, tr.getNumAvaliableTickets(type));
	    
		try {
			tr.removeTickets(type, 11);
	        fail("expected exception was not occured.");
	    } catch(LackOfTicketsException e) {
	    }
		
		//it should be no changes
	    assertEquals(10, tr.getNumAvaliableTickets(type));

	    try {
	    	tr.removeTickets(type, 51);
	        fail("expected exception was not occured.");
	    } catch(LackOfTicketsException e) {
	    }
		
	    //it still should be the same
	    assertEquals(10, tr.getNumAvaliableTickets(type));
	}
	
	public void testTicketAvaliable() {
		TicketResource tr = new TicketResource();
		TicketType type = TicketType.DISPOSABLE_SPECIAL;
		//after initialization its 10 tickets of all kinds so 10 should be avaliable and 11 should not
		assertTrue(tr.ticketsAvaliable(type, 10));
		assertFalse(tr.ticketsAvaliable(type, 11));
	}

}
