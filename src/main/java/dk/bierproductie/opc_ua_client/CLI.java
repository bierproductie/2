package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import dk.bierproductie.opc_ua_client.handlers.HandlerFactory;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class CLI {

    public static void handleCommand(String command) throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String[] cmd = command.toLowerCase().split(" ");
        switch (cmd[0]) {
            case "batch":
                System.out.println("Run on simulator? [Y/n]");
                String resp = scanner.nextLine();
                boolean isSimulator;
                if (resp.isEmpty()) {
                    isSimulator = true;
                } else {
                    isSimulator = false;
                }
                System.out.println("New batch setup initiated");
                System.out.println("Give the bach an ID");
                int batchId = scanner.nextInt();
                System.out.println("Please choose a recipe");
                for (Products product : Products.values()) {
                    System.out.println(product + " : " + product.ordinal());
                }
                int recipe = scanner.nextInt();
                if (recipe > Products.values().length) {
                    System.out.println("No such recipe");
                    return;
                }
                System.out.println(Products.values()[recipe] + " chosen");
                System.out.println("Now select a speed within this range: 0-" + Products.values()[recipe].speedLimit);
                float speed = scanner.nextFloat();
                System.out.println(speed + " set, now choose amount of products to create");
                int amount = scanner.nextInt();
                System.out.println("All parameters now set batch overview shown below:");
                System.out.println("BatchId: " + batchId + ", recipe: " + Products.values()[recipe] + ", speed: " + speed + ", amount: " + amount);
                System.out.println("Run this batch [Y/n]");
                String run = scanner.nextLine();
                if (run.isEmpty()) {
                    startBatch(batchId, recipe, speed, amount, isSimulator);
                    System.out.println("Batch finished...");
                    System.out.println("Run another? [Y/n]");
                    run = scanner.nextLine();
                    if (run.isEmpty()) {
                        handleCommand("Batch");
                    }
                } else {
                    System.out.println("Restarting batch creation process");
                    handleCommand("Batch");
                }
        }
    }

    public static void startBatch(int batchId, int recipe, float speed, float amount, boolean isSimulator) throws ExecutionException, InterruptedException {
        Batch batch = new Batch(batchId, Products.values()[recipe], speed, amount);
        HandlerFactory handlerFactory = new HandlerFactory(isSimulator);
        BatchHandler.getInstance().startBatch(batch);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String cmd = scanner.nextLine();
        handleCommand(cmd);
    }
}
