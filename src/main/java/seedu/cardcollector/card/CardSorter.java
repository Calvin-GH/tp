package seedu.cardcollector.card;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardSorter {
    public static Comparator<Card> getSortComparator(CardSortCriteria criteria) {
        switch (criteria) {
        case INDEX -> {
            assert false : "index criteria should not use a comparator";
        }
        case NAME -> {
            return Comparator.comparing(Card::getName);
        }
        case QUANTITY -> {
            return Comparator.comparingInt(Card::getQuantity);
        }
        case PRICE -> {
            return Comparator.comparingDouble(Card::getPrice);
        }
        case SET -> {
            return Comparator.comparing(Card::getCardSet,
                    Comparator.nullsFirst(Comparator.naturalOrder()));
        }
        case RARITY -> {
            return Comparator.comparing(Card::getRarity,
                    Comparator.nullsFirst(Comparator.naturalOrder()));
        }
        case CONDITION -> {
            return Comparator.comparing(Card::getCondition,
                    Comparator.nullsFirst(Comparator.naturalOrder()));
        }
        case LANGUAGE -> {
            return Comparator.comparing(Card::getLanguage,
                    Comparator.nullsFirst(Comparator.naturalOrder()));
        }
        case NUMBER -> {
            return Comparator.comparing(Card::getCardNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()));
        }
        case ADDED -> {
            return Comparator.comparing(Card::getLastAdded,
                    Comparator.nullsFirst(Instant::compareTo));
        }
        case MODIFIED -> {
            return Comparator.comparing(Card::getLastModified,
                    Comparator.nullsFirst(Instant::compareTo));
        }
        case REMOVED -> {
            return Comparator.comparing(Card::getLastRemoved,
                    Comparator.nullsFirst(Instant::compareTo));
        }
        default -> {
            assert false : "Unhandled CardSortCriteria";
        }
        }
        return null;
    }

    public static ArrayList<Card> sort(
            ArrayList<Card> cards,
            CardSortCriteria criteria,
            int maxLimit,
            int defaultMaxLimit,
            boolean isDescending) {

        if (cards.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<Card> cardsCopy = new ArrayList<>(cards);

        if (criteria == CardSortCriteria.INDEX && isDescending) {
            // Directly apply ascending/descending order,
            // without using comparator
            Collections.reverse(cardsCopy);
        }

        Stream<Card> cardsStream = cardsCopy.stream();
        if (criteria != CardSortCriteria.INDEX) {
            Comparator<Card> comparator = getSortComparator(criteria);

            assert comparator != null : "No available comparator for criteria";

            // Apply ascending/descending order using comparator
            if (isDescending) {
                comparator = comparator.reversed();
            }

            cardsStream = cardsStream.sorted(comparator);
        }

        int recordsLimit = (maxLimit == -1) ? defaultMaxLimit :
                Math.min(cards.size(), maxLimit);

        return cardsStream
                .limit(recordsLimit)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
