import java.io.*;
import java.util.*;

public class binary_search_step {

    static class DataRow {
        int number;
        String word;

        DataRow(int number, String word) {
            this.number = number;
            this.word = word;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Get current working directory
        String currentDir = System.getProperty("user.dir");

        // List all CSV files in the directory
        File dir = new File(currentDir);
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".csv"));

        if (files == null || files.length == 0) {
            System.out.println("No CSV files found in the current directory.");
            return;
        }

        // Display dataset options
        System.out.println("Choose a dataset:");
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

        // Prompt user to enter the target integer
        System.out.print("Enter target integer to search: ");
        int target = scanner.nextInt();

        String outputFile = "binary_search_step_" + target + ".txt";

        List<DataRow> dataset = loadCSV(filename);
        binarySearchStep(dataset, target, outputFile);

        System.out.println("Search steps saved to " + outputFile);
    }

    public static List<DataRow> loadCSV(String filename) throws IOException {
        List<DataRow> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        boolean skipFirst = true; // Skip header if present

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            try {
                int number = Integer.parseInt(parts[0]);
                String word = parts[1];
                list.add(new DataRow(number, word));
            } catch (NumberFormatException e) {
                if (skipFirst) {
                    skipFirst = false;
                    continue;
                }
            }
        }
        br.close();
        return list;
    }

    public static void binarySearchStep(List<DataRow> data, int target, String outputFile) throws IOException {
        int left = 0;
        int right = data.size() - 1;

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        while (left <= right) {
            int mid = (left + right) / 2;
            DataRow row = data.get(mid);
            writer.write((mid + 1) + ": " + row.number + "/" + row.word + "\n");

            if (row.number == target) {
                writer.close();
                return;
            } else if (row.number < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Target not found
        writer.write("-1\n");
        writer.close();
    }
}
