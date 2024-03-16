import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DisplayItems {
    public static void main(String[] args) {
        // Create an ObjectMapper instance for JSON parsing.
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file into a list of Item objects, filtering out null or blank
            // names.
            List<Item> items = objectMapper.readValue(new File("items.json"), new TypeReference<List<Item>>() {
            });
            items = items.stream()
                    .filter(item -> item.getName() != null && !item.getName().trim().isEmpty())
                    .collect(Collectors.toList());

            // Group the filtered items by listId.
            Map<Integer, List<Item>> groupedItems = items.stream()
                    .collect(Collectors.groupingBy(Item::getListId));

            // Iterate through each group, sorting by name and printing items.
            groupedItems.forEach((listId, itemList) -> {
                System.out.println("ListId: " + listId);
                itemList.stream()
                        .sorted(Comparator.comparing(Item::getName))
                        .forEach(item -> System.out.println("  - Item " + item.getId() + ": " + item.getName()));
            });

        } catch (IOException e) {
            // Handle potential IOException from reading the JSON file.
            e.printStackTrace();
        }
    }
}
