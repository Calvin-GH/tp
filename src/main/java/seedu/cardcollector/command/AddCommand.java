package seedu.cardcollector.command;

import seedu.cardcollector.CardsList;
import seedu.cardcollector.Card;
import seedu.cardcollector.Ui;
import java.util.UUID;

public class AddCommand extends Command {
    private final UUID uid;
    private final String name;
    private final int quantity;
    private final float price;

    public AddCommand(UUID uid, String name, int quantity, float price) {
        this.uid = uid;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public CommandResult execute(Ui ui, CardsList inventory) {
        Card.Builder builder = new Card.Builder()
                .price(price)
                .name(name)
                .quantity(quantity);

        if (uid != null) {
            builder.uid(uid);
        }

        Card newCard = builder.build();

        inventory.addCard(newCard);
        ui.printAdded(inventory);
        return new CommandResult(false);
    }
}
