import java.io.*;
import java.util.*;

public class binary_search {
    static class DataRow {
        int number;
        String text;

        DataRow(int number, String text) {
            this.number = number;
            this.text = text;
        }
    }

    public static void main(String[] args) throws IOException {
    try (Scanner scanner = new Scanner(System.in)) {
        // Get current working directory
        String currentDir = System.getProperty("user.dir");
        File dir = new File(currentDir);

        
        File[] files = dir.listFiles((_, name) -> name.toLowerCase().endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.out.println("No CSV files found in the current directory.");
            return;
        }

        // Display dataset options
        System.out.println("Choose dataset:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        System.out.print("Enter your choice (1-" + files.length + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice < 1 || choice > files.length) {
            System.out.println("Invalid choice.");
            return;
        }

        String filename = files[choice - 1].getAbsolutePath();

        // Load dataset
        List<DataRow> data = loadDataset(filename);
        int n = data.size();

        // Choose targets
        int bestTarget = data.get(n / 2).number;
        int averageTarget = data.get(n / 4).number;
        int worstTarget = 999999999; // not in dataset

        // Measure time 
        double bestTime = measureTime(data, bestTarget);
        double avgTime = measureTime(data, averageTarget);
        double worstTime = measureTime(data, worstTarget);

        // Output
        String outputFile = "binary_search_" + n + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.printf("Best case time       : %.2f ns%n", bestTime);
            writer.printf("Average case time    : %.2f ns%n", avgTime);
            writer.printf("Worst case time      : %.2f ns%n", worstTime);
        }

        System.out.println("Results written to " + outputFile);
    }
    }


    public static List<DataRow> loadDataset(String filename) throws IOException {
        List<DataRow> dataset = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int number = Integer.parseInt(parts[0]);
            String text = parts[1];
            dataset.add(new DataRow(number, text));
        }

        reader.close();
        return dataset;
    }

    public static int binarySearch(List<DataRow> data, int target) {
        int left = 0, right = data.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int value = data.get(mid).number;
            if (value == target) return mid;
            if (value < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    public static double measureTime(List<DataRow> data, int target) {
        int n = data.size();
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            binarySearch(data, target);
        }
        long end = System.nanoTime();
        return (end - start) / (double) n;
    }
}
