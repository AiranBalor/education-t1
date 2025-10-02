package ru.t1.education;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PeopleNumberGrouping {
    
    public static Map<Integer, List<String>> processPeople(Path filePath) throws IOException {
        return Files.lines(filePath)
            .parallel()
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .map(line -> line.split(":"))
            .filter(parts -> parts.length == 2)
            .filter(parts -> !parts[1].trim().isEmpty())
            .map(parts -> new Person(
                capitalizeName(parts[0].trim().toLowerCase()),
                Integer.parseInt(parts[1].trim())
            ))
            .collect(Collectors.groupingByConcurrent(
                Person::getNumber,
                Collectors.mapping(Person::getName, Collectors.toList())
            ));
    }
    
    static String capitalizeName(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        String[] parts = str.split("-");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                String capitalizedPart = parts[i].substring(0, 1).toUpperCase() + 
                                       parts[i].substring(1).toLowerCase();
                result.append(capitalizedPart);
            }
            
            if (i < parts.length - 1) {
                result.append("-");
            }
        }
        
        return result.toString();
    }
    
    private static class Person {
        private final String name;
        private final int number;
        
        public Person(String name, int number) {
            this.name = name;
            this.number = number;
        }
        
        public String getName() { return name; }
        public int getNumber() { return number; }
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java PeopleNumberGrouping <filename>");
            return;
        }
        
        Path filePath = Path.of(args[0]);
        Map<Integer, List<String>> result = processPeople(filePath);
        
        System.out.println("Grouped people by numbers:");
        result.forEach((number, names) -> {
            System.out.println(number + ": " + names);
        });
    }
}