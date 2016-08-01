package credit_agricole.ticket_automat;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class CoinResourceTest extends TestCase {
	
	public void testGetBiggestCoinLessOrEqualThan() throws LackOfCoinsException{
		CoinResource cr = new CoinResource();
		
		//args equal to all kinds of coins
		BigDecimal result5 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("5.00"));
		assertEquals(new BigDecimal("5.00"), result5);
		BigDecimal result2 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("2.00"));
		assertEquals(new BigDecimal("2.00"), result2);
		BigDecimal result1 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("1.00"));
		assertEquals(new BigDecimal("1.00"), result1);
		BigDecimal result05 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.50"));
		assertEquals(new BigDecimal("0.50"), result05);
		BigDecimal result02 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.20"));
		assertEquals(new BigDecimal("0.20"), result02);
		BigDecimal result01 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.10"));
		assertEquals(new BigDecimal("0.10"), result01);
		
		//for args greater than 5.00 method should return 5.00
		BigDecimal result501 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("5.01"));
		assertEquals(new BigDecimal("5.00"), result501);
		BigDecimal result6 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("6.00"));
		assertEquals(new BigDecimal("5.00"), result6);
		BigDecimal result10000 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("10000.00"));
		assertEquals(new BigDecimal("5.00"), result10000);
		
		//for args little less than nominal shud return greater nominal less than arg
		BigDecimal result499 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("4.99"));
		assertEquals(new BigDecimal("2.00"), result499);
		BigDecimal result199 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("1.99"));
		assertEquals(new BigDecimal("1.00"), result199);
		BigDecimal result099 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.99"));
		assertEquals(new BigDecimal("0.50"), result099);
		BigDecimal result049 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.49"));
		assertEquals(new BigDecimal("0.20"), result049);
		BigDecimal result019 = cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.19"));
		assertEquals(new BigDecimal("0.10"), result019);
		
		//for args less than the smallest nominal (0.10zl) method should throw an exception (LackOfCoinsException)
	    try {
	    	cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.09"));
	        fail("expected exception was not occured.");
	    } catch(LackOfCoinsException e) {
	    }
	    try {
	    	cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.01"));
	        fail("expected exception was not occured.");
	    } catch(LackOfCoinsException e) {
	    }
	    try {
	    	cr.getBiggestCoinLessOrEqualThan(new BigDecimal("0.00"));
	        fail("expected exception was not occured.");
	    } catch(LackOfCoinsException e) {
	    }
	    try {
	    	cr.getBiggestCoinLessOrEqualThan(new BigDecimal("-0.10"));
	        fail("expected exception was not occured.");
	    } catch(LackOfCoinsException e) {
	    }
	    try {
	    	cr.getBiggestCoinLessOrEqualThan(new BigDecimal("-1.00"));
	        fail("expected exception was not occured.");
	    } catch(LackOfCoinsException e) {
	    }
	    try {
	    	cr.getBiggestCoinLessOrEqualThan(new BigDecimal("-5.68"));
	        fail("expected exception was not occured.");
	    } catch(LackOfCoinsException e) {
	    }
	
	}
	


}
