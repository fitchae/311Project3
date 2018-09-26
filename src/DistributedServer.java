/**
 * Author : Chris Bell
 * Date   : 03/14/2018
 */

import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class DistributedServer {

    /**
     * Extracts list of Big Integers from a string
     * @param ret String of integers
     * @return List of big integers from string
     */
    public static ArrayList<BigInteger> getList(String ret) {
        ret = ret.replace("[", "").replace("]", "");

        ArrayList<BigInteger> nums = new ArrayList<>();
        for (String num : ret.split(", ")) {
            nums.add(new BigInteger(num));
        }

        return nums;
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.err.println("Usage: java EchoServer <port number> [<hostname> <port>]");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        String hostName = "localhost";

        ArrayList<Server> servers = new ArrayList<>();
        for (int i = 1; i < args.length; i+=2){
            servers.add(new Server(args[i], Integer.parseInt(args[i + 1])));
        }

        ServerSocket socket = new ServerSocket(portNumber);

        while (true) {
            try (
                    Socket clientSocket = socket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                String inputLine = in.readLine();
                BigInteger num = new BigInteger(inputLine);

                // Establish connection with prime worker servers
                ArrayList<Socket> sockets = new ArrayList<>();
                for (int i = 1; i <= servers.size(); i++) {
                    Socket tmpSocket = new Socket(servers.get(i - 1).getHostName(), servers.get(i - 1).getPort());
                    PrintWriter tmpWriter = new PrintWriter(tmpSocket.getOutputStream(), true);

                    sockets.add(tmpSocket);

                    BigInteger lower, upper;

                    String nServers = String.valueOf(servers.size());
                    BigInteger range = PrimeFactors.sqrt(num).subtract(new BigInteger("2")).divide(new BigInteger(nServers));

                    if (i == 1) {
                        lower = new BigInteger("2");
                        upper = range.multiply(new BigInteger(String.valueOf(i)));
                    } else if (i == servers.size()) {
                        lower = range.multiply(new BigInteger(String.valueOf(i - 1))).add(BigInteger.ONE);
                        upper = PrimeFactors.sqrt(num);
                    } else {
                        lower = range.multiply(new BigInteger(String.valueOf(i - 1))).add(BigInteger.ONE);
                        upper = range.multiply(new BigInteger(String.valueOf(i)));
                    }

                    tmpWriter.println(num.toString() + " " + lower.toString() + " " + upper.toString());
                }

                // Receive prime factors from each server
                ArrayList<BigInteger> factors = new ArrayList<>();
                for (Socket sock : sockets) {
                    BufferedReader tmpReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    factors.addAll(getList(tmpReader.readLine()));
                }

                // Aggregate data from prime servers back together
                out.println(PrimeFactors.simplify(num, factors).toString());

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
