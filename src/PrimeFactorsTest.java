/**
 * Author : Chris Bell
 * Date   : 03/14/2018
 */

import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class PrimeFactorsTest {

    @Test public void primeTest() {
        BigInteger num = new BigInteger("29");
        BigInteger lower = new BigInteger("2");
        BigInteger upper = PrimeFactors.sqrt(num);

        ArrayList<BigInteger> factors = new ArrayList<>();
        factors.add(new BigInteger("29"));

        assertEquals(PrimeFactors.primeFactors(num, lower, upper), factors);

    }

    @Test public void basicTest() {
        BigInteger num = new BigInteger("100");
        BigInteger lower = new BigInteger("2");
        BigInteger upper = PrimeFactors.sqrt(num);

        ArrayList<BigInteger> factors = new ArrayList<>();
        factors.add(new BigInteger("2"));
        factors.add(new BigInteger("2"));
        factors.add(new BigInteger("5"));
        factors.add(new BigInteger("5"));

        assertEquals(PrimeFactors.primeFactors(num, lower, upper), factors);

    }

    @Test public void baseTest() {
        BigInteger num = new BigInteger("123456789");
        BigInteger lower = new BigInteger("2");
        BigInteger upper = PrimeFactors.sqrt(num);

        ArrayList<BigInteger> factors = new ArrayList<>();
        factors.addAll(PrimeFactors.primeFactors(num, lower, new BigInteger("3703")));
        factors.addAll(PrimeFactors.primeFactors(num, new BigInteger("3703"), new BigInteger("7407")));
        factors.addAll(PrimeFactors.primeFactors(num, new BigInteger("7408"), new BigInteger("11111")));

        factors = PrimeFactors.simplify(num, factors);

        ArrayList<BigInteger> answer = new ArrayList<>();
        answer.add(new BigInteger("3"));
        answer.add(new BigInteger("3"));
        answer.add(new BigInteger("3607"));
        answer.add(new BigInteger("3803"));

        assertEquals(factors, answer);

    }
}
