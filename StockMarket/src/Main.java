import Investors.Investor;
import Investors.InvestorRandom;
import Investors.InvestorSMA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Incorrect number of arguments");
            return;
        }

        String fileName = args[0]; // Get the file name from the command line arguments
        int numberOfTurns;

        try {
            numberOfTurns = Integer.parseInt(args[1]); // Get the number of turns from the command line arguments
        } catch (NumberFormatException e) {
            System.out.println("Invalid second argument");
            return;
        }

        Simulation simulation = new Simulation(numberOfTurns); // Create an instance of the Simulation class

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            int numberOfR = 0;
            int numberOfS = 0;

            // Reading the number of investors
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) { // Ignore lines starting with #
                    continue;
                }

                // Counting occurrences of 'R' and 'S' in the first line
                String[] tokens = line.split(" ");
                for (String token : tokens) {
                    if (token.equals("R")) {
                        numberOfR++;
                    } else if (token.equals("S")) {
                        numberOfS++;
                    }
                }

                break; // Break the loop after processing the first line
            }

            // Adding shares to the simulation
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) { // Ignore lines starting with #
                    continue;
                }

                String[] pairs = line.split(" ");

                for (String pair : pairs) {
                    String[] key_value = pair.split(":");
                    if (key_value.length == 2) {
                        String key = key_value[0];
                        int value = Integer.parseInt(key_value[1]);
                        simulation.addShares(key, value); // Adding a new share to the simulation
                    }
                }

                break; // Break the loop after processing the first line
            }

            // Reading investor information
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) { // Ignore lines starting with #
                    continue;
                }

                Scanner scanner = new Scanner(line);

                int amountOfMoney = scanner.nextInt(); // Read the amount of money for the investor

                int id = 0;
                Investor investor;

                // Adding investors to the simulation based on the read data
                for (int i = 0; i < numberOfR; i++) {
                    investor = new InvestorRandom(id, amountOfMoney); // Create a new random investor
                    simulation.addInvestor(investor); // Add the investor to the simulation
                    id++;
                }

                for (int i = 0; i < numberOfS; i++) {
                    investor = new InvestorSMA(id, amountOfMoney); // Create a new SMA investor
                    simulation.addInvestor(investor); // Add the investor to the simulation
                    id++;
                }

                // Adding shares to investors' portfolios
                while (scanner.hasNext()) {
                    String pair = scanner.next();
                    String[] key_value = pair.split(":");
                    if (key_value.length == 2) {
                        String key = key_value[0];
                        int value = Integer.parseInt(key_value[1]);
                        simulation.addSharesToPortfolio(key, value); // Add shares to investors' portfolios
                    }
                }

                break; // Break the loop after processing the first line
            }

        } catch (IOException e) {
            System.out.println("Error reading the file");
            return;
        }

        // Initialize investors before starting the simulation
        simulation.initializeInvestors();

        // Run the entire simulation
        simulation.runSimulation();
    }
}
