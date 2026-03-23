package seedu.cardcollector;

import seedu.cardcollector.command.Command;
import seedu.cardcollector.exception.ParseInvalidArgumentException;
import seedu.cardcollector.exception.ParseUnknownCommandException;
import seedu.cardcollector.parsing.Parser;

import java.util.ArrayList;

public class CardCollector {
    private final Ui ui;
    private final CardsList inventory;

    public CardCollector() {
        ui = new Ui();
        inventory = new CardsList();
    }

    public void run() {
        ui.printWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readInput();

            try {
                Parser parser = new Parser();
                Command command = parser.parse(input);

                assert command != null;

                command.execute(ui, inventory);
            } catch (ParseInvalidArgumentException e) {
                ui.printInvalidArgumentWarning(e.getMessage(), e.getUsage());
            } catch (ParseUnknownCommandException e) {
                // TODO uncomment this line once the parser has been migrated
                // ui.printUnknownCommandWarning(e.getMessage());
            }

            String[] parts = input.split(" ", 2);
            String commandString = parts[0].toLowerCase();

            // TODO Please move into the parser, thanks!
            switch (commandString) {
            case "find":
                if (parts.length < 2) {
                    System.out.println("Usage: find [/n NAME] [/p PRICE] [/q QUANTITY]");
                    System.out.println("At least one field must be provided.");
                    break;
                }
                handleFind(parts[1]);
                break;

            case "list":
                assert inventory != null : "Inventory should be initialised before listing";
                int sizeBeforeListing = inventory.getSize();
                ui.printList(inventory);
                assert inventory.getSize() == sizeBeforeListing
                        : "Listing inventory should not modify its size";
                break;

            case "bye":
                ui.printExit();
                isRunning = false;
                break;

            default:
                System.out.println("DEPRECATED Unknown command!");
            }
        }
    }

    private void handleFind(String arguments) {
        // Precondition: Arguments passed from the main loop should never be null
        assert arguments != null : "Arguments string for find command should not be null";

        String name = null;
        Float price = null;
        Integer quantity = null;

        try {
            if (arguments.contains("/n")) {
                name = arguments.split("/n")[1].split("/q|/p")[0].trim();
            }
            if (arguments.contains("/q")) {
                quantity = Integer.parseInt(arguments.split("/q")[1].split("/n|/p")[0].trim());
            }
            if (arguments.contains("/p")) {
                price = Float.parseFloat(arguments.split("/p")[1].split("/n|/q")[0].trim());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for price or quantity.");
            return;
        }

        if (name == null && price == null && quantity == null) {
            System.out.println("At least one search field (/n, /p, /q) must be provided.");
            return;
        }

        ArrayList<Card> results = inventory.findCards(name, price, quantity);

        // Postcondition: Results must be successfully returned (even if empty) before printing
        assert results != null : "Returned search results should not be null";

        ui.printFound(results);
    }

    public static void main(String[] args) {
        new CardCollector().run();
    }
}
