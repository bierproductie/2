package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import dk.bierproductie.opc_ua_client.handlers.HTTPHandler;
import dk.bierproductie.opc_ua_client.handlers.HandlerFactory;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLI {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static String[] commands = {"batch", "help", "quit", "dino"};

    public static void handleCommand(String command) throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String[] cmd = command.toLowerCase().split(" ");
        String msg = "";
        switch (cmd[0]) {
            case "batch":
                LOGGER.log(Level.INFO, "Run on simulator? [Y/n]");
                String resp = scanner.nextLine();
                boolean isSimulator = resp.isEmpty();
                LOGGER.log(Level.INFO, "New batch setup initiated");
                LOGGER.log(Level.INFO, "Give the bach an ID");
                int batchId = scanner.nextInt();
                LOGGER.log(Level.INFO, "Please choose a recipe");
                for (Products product : Products.values()) {
                    String message = String.format("%s : %d", product, product.ordinal());
                    LOGGER.log(Level.INFO, message);
                }
                int recipe = scanner.nextInt();
                if (recipe > Products.values().length) {
                    LOGGER.log(Level.INFO, "No such recipe");
                    break;
                }
                msg = String.format("%s chosen", Products.values()[recipe]);
                LOGGER.log(Level.INFO, msg);
                msg = String.format("Now select a speed within this range: 0-%d", Products.values()[recipe].speedLimit);
                LOGGER.log(Level.INFO, msg);
                float speed = scanner.nextFloat();
                msg = String.format("%s set, now choose amount of products to create", speed);
                LOGGER.log(Level.INFO, msg);
                int amount = scanner.nextInt();
                LOGGER.log(Level.INFO, "All parameters now set batch overview shown below:");
                msg = String.format("BatchId: %d, recipe: %s, speed: %s, amount: %d", batchId, Products.values()[recipe], speed, amount);
                LOGGER.log(Level.INFO, msg);
                scanner.nextLine();
                LOGGER.log(Level.INFO, "Run this batch [Y/n]");
                String run = scanner.nextLine();
                if (run.isEmpty()) {
                    startBatch(batchId, recipe, speed, amount, isSimulator);
                    while (BatchHandler.getCurrentBatch().isRunning()) {
                        Thread.sleep(1000);
                    }
                    LOGGER.log(Level.INFO, "Batch finished...");
                    LOGGER.log(Level.INFO, BatchHandler.getCurrentBatch().toString());
                    LOGGER.log(Level.INFO, "Run another? [Y/n]");
                    run = scanner.nextLine();
                    if (run.isEmpty()) {
                        handleCommand("Batch");
                    } else {
                        handleCommand("quit");
                    }
                } else {
                    LOGGER.log(Level.INFO, "Restarting batch creation process");
                    handleCommand("Batch");
                }
                break;
            case "help":
                LOGGER.log(Level.INFO, "Commands:");
                for (String str : commands) {
                    LOGGER.log(Level.INFO, str);
                }
                break;
            case "dino":
                msg = "                 \n" +
                        "               __\n" +
                        "              / _)\n" +
                        "     _.----._/ /\n" +
                        "    /         /\n" +
                        " __/ (  | (  |\n" +
                        "/__.-'|_|--|_|";
                LOGGER.log(Level.INFO, msg);
                break;
            case "quit":
                System.exit(0);
                break;
            default:
                LOGGER.log(Level.INFO, "Command not recognized");
                handleCommand("help");
        }
        getCommand();
    }

    public static void startBatch(int batchId, int recipe, float speed, float amount, boolean isSimulator) throws ExecutionException, InterruptedException {
        Batch batch = new Batch(batchId, Products.values()[recipe], speed, amount);
        HandlerFactory.getInstance(isSimulator);
        BatchHandler.getInstance().startBatch(batch);
    }

    public static void getCommand() throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String cmd = scanner.nextLine();
        handleCommand(cmd);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.setUseParentHandlers(false);
        CLIFormatter cliFormatter = new CLIFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(cliFormatter);
        LOGGER.addHandler(consoleHandler);
        handleCommand("help");
    }
}
