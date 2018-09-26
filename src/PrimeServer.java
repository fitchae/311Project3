/**
 * Author : Chris Bell
 * Date   : 03/14/2018
 */

import java.math.BigInteger;
import java.net.*;
import java.io.*;

public class PrimeServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java PrimeServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        ServerSocket socket = new ServerSocket(portNumber);

        while (true) {
            try (
                Socket client = socket.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            ) {
                String inputLine = in.readLine();
                BigInteger num, lower, upper;

                if (inputLine.split(" ").length == 1) {
                    num = new BigInteger(inputLine);
                    lower = new BigInteger("2");
                    upper = PrimeFactors.sqrt(num);
                } else {
                    num = new BigInteger(inputLine.split(" ")[0]);
                    lower = new BigInteger(inputLine.split(" ")[1]);
                    upper = new BigInteger(inputLine.split(" ")[2]);
                }

                out.println(PrimeFactors.primeFactors(num, lower, upper));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
