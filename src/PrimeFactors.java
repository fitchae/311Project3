/**
 * Author : Chris Bell
 * Date   : 03/14/2018
 */

import java.util.ArrayList;
import java.math.BigInteger;

public class PrimeFactors {

    /**
     * Computes the square root of a big integer
     * @param x The number to find the square root of
     * @return THe square root truncated
     */
    public static BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;

        while (true) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }

    /**
     * Finds the prime factors of a given number over a range
     * @param n The number to find the prime factors of
     * @param lower The lower bound to search for prime numbers over
     * @param upper The upper bound to search for prime numbers over
     * @return List of prime factors.
     */
    public static ArrayList<BigInteger> primeFactors(BigInteger n, BigInteger lower, BigInteger upper) {
        ArrayList<BigInteger> factors = new ArrayList<>();

        if (n.isProbablePrime(1)) {
            factors.add(n);
            return factors;
        }

        BigInteger f = lower;
        while (f.compareTo(upper) == 0 || f.compareTo(upper) == -1) {
            if (n.mod(f).compareTo(BigInteger.ZERO) == 0) {
                if (f.isProbablePrime(1)) {
                    factors.add(f);
                } else {
                    return primeFactors(n, new BigInteger("2"), sqrt(n));
                }

                n = n.divide(f);
            } else {
                f = f.add(BigInteger.ONE);
            }
        }

        return factors;
    }

    /**
     * Aggregates the data of a distributed prime factorization together.
     * @param n The number to find the prime factors of.
     * @param factors THe list of redundant prime factors
     * @return A list of prime factors
     */
    public static ArrayList<BigInteger> simplify(BigInteger n, ArrayList<BigInteger> factors) {
        ArrayList<BigInteger> tmp = new ArrayList<>();

        int i = 0;
        while (n.compareTo(BigInteger.ONE) == 1) {
            if (n.mod(factors.get(i)).compareTo(BigInteger.ZERO) == 0) {
                tmp.add(factors.get(i));

                n = n.divide(factors.get(i));
                i++;
            }
        }

        return tmp;
    }

    public static void main(String [] args) {
        BigInteger num = new BigInteger("123456789");
        BigInteger upper = sqrt(num);

        ArrayList<BigInteger> factors = new ArrayList<>();

        ArrayList<BigInteger> factors1 = primeFactors(num, new BigInteger("2"), num.divide(new BigInteger("3")));
        ArrayList<BigInteger> factors2 = primeFactors(num, num.divide(new BigInteger("3")), num.divide(new BigInteger("3")).multiply(new BigInteger("2")));
        ArrayList<BigInteger> factors3 = primeFactors(num, num.divide(new BigInteger("3")).multiply(new BigInteger("2")), upper);

        factors.addAll(factors1);
        factors.addAll(factors2);
        factors.addAll(factors3);

        System.out.println(simplify(num, factors).toString());
    }
}
