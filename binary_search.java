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
        Scanner scanner = new Scanner(System.in);

        // Get current working directory
        String currentDir = System.getProperty("user.dir");

        // Dataset selection
        System.out.println("Choose dataset:");
        System.out.println("1. quick_sort_1000.csv");
        // System.out.println("2. merge_sort_100000.csv");
        // System.out.println("3. quick_sort_100000.csv");
        System.out.print("Enter choice (1-3): ");
        String choice = scanner.nextLine();

        String filename = null;

        switch (choice) {
            case "1":
                filename = currentDir + File.separator + "quick_sort_1000.csv";
                break;
            // case "2":
            //     filename = currentDir + File.separator + "merge_sort_100000.csv";
            //     break;
            // case "3":
            //     filename = currentDir + File.separator + "quick_sort_100000.csv";
            //     break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

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
            writer.printf("Best case : %.2f ns%n", bestTime);
            writer.printf("Average case : %.2f ns%n", avgTime);
            writer.printf("Worst case : %.2f ns%n", worstTime);
        }

        System.out.println("Results written to " + outputFile);
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
