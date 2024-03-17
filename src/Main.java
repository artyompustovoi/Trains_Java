import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<Train> trains = new ArrayList<>();
        ///////////PART1
//        trains = readTrainsFromFile("trains.txt");
//        trains.forEach(System.out::println);
        ///////////PART2
//        trains = readTrainsFromFileSortedByTravelTime("trains.txt");
//        trains.forEach(System.out::println);
        ///////////PART3
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Введите город отправления: ");
//        String fromCity = scanner.nextLine();
//        System.out.print("Введите дату и время в формате dd-MM-yyyy HH:mm:ss: ");
//        String dateTimeString = scanner.nextLine();
//        LocalDateTime departureDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
//        List<Train> filteredTrains = filterTrainsByDeparture(trains, fromCity, departureDateTime);
//        filteredTrains.forEach(System.out::println);
        ///////////PART4
//        Map<String, List<Train>> trainsMap = readTrainsFromFileToMap("trains.txt");
//        trainsMap.forEach((city, trains_) -> {
//            System.out.println("Город: " + city);
//            trains_.forEach(System.out::println);
//            System.out.println();
//        });
        ///////////PART4
//        Map<String, Set<String>> cityConnections = readCityConnectionsFromFile("trains.txt");
//        cityConnections.forEach((city, connections) -> {
//            System.out.println("Город отправления: " + city);
//            System.out.println("Достижимые города: " + connections);
//            System.out.println();
//        });
    }
    public static Map<String, Set<String>> readCityConnectionsFromFile(String filename) {
        Map<String, Set<String>> cityConnections = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            cityConnections = reader.lines()
                    .flatMap(Main::extractCityConnections)
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,
                            Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityConnections;
    }
    public static Map<String, List<Train>> readTrainsFromFileToMap(String filename) {
        Map<String, List<Train>> trainMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            trainMap = reader.lines()
                    .map(Main::parseTrainFromString)
                    .collect(Collectors.groupingBy(Train::from));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trainMap;
    }
    public static List<Train> readTrainsFromFileSortedByTravelTime(String filename) {
        List<Train> allTrains = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            allTrains = reader.lines()
                    .map(Main::parseTrainFromString)
                    .filter(Main::isTravelTimeLessThanOrEqualThreeDays)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allTrains;
    }
    public static List<Train> readTrainsFromFile(String filename) {
        List<Train> trains = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            trains = reader.lines()
                    .map(Main::parseTrainFromString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trains;
    }
    public static Stream<Map.Entry<String, String>> extractCityConnections(String line) {
        String[] parts = line.split("[{};]");
        String from = parts[1].split(":")[1].trim();
        String to = parts[2].split(":")[1].trim();
        return Stream.of(Map.entry(from, to), Map.entry(to, from));
    }
    public static boolean isTravelTimeLessThanOrEqualThreeDays(Train train) {
        long travelDays = Duration.between(train.getDeparture(), train.getArrival()).toDays();
        return travelDays <= 3;
    }
    public static List<Train> filterTrainsByDeparture(List<Train> trains, String fromCity, LocalDateTime departureDateTime) {
        return trains.stream()
                .filter(train -> train.from().equals(fromCity) && train.departure().isBefore(departureDateTime))
                .collect(Collectors.toList());
    }
    public static Train parseTrainFromString(String line) {
        // Пример строки: "Train 145 {from: Kyiv; to: Wien; depart: 20-06-1916 17:09:00; arrive: 22-06-1916 19:09:00}"
        String[] parts = line.split("[{};]");
        long number = Long.parseLong(parts[0].split("\\s+")[1]);
        String from = parts[1].split(":")[1].trim();
        String to = parts[2].split(":")[1].trim();
        //LocalDate.parse(parts[3].substring(0, 20), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        LocalDateTime departure = LocalDateTime.parse(parts[3].substring(9, 28), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        LocalDateTime arrival = LocalDateTime.parse(parts[4].substring(9, 28), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        return new Train(number, from, to, departure, arrival);
    }
}


