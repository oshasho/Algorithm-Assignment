// HAVENT FINISHED DEBUGGING
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

        // Get the current working directory path
        String currentDir = System.getProperty("user.dir");

        // Prompt user to choose dataset (only one choice here)
        System.out.println("Choose dataset:");
        System.out.println("1. quick_sort_1000.csv");
        System.out.print("Enter choice (1-3): ");
        // System.out.println("2. merge_sort_100000.csv");
        // System.out.println("3. quick_sort_100000.csv");
        String choice = scanner.nextLine();

        String filename;
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

        // Prompt user to enter the target integer to search in the dataset
        System.out.print("Enter target integer to search: ");
        int target = scanner.nextInt();

        // Output filename 
        String outputFile = "binary_search_step_" + target + ".txt";

        List<DataRow> dataset = loadCSV(filename);

        binarySearchStep(dataset, target, outputFile);

        System.out.println("Search steps saved to " + outputFile);
    }

    public static List<DataRow> loadCSV(String filename) throws IOException {
        List<DataRow> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        boolean skipFirst = true; // Flag to skip header line

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            try {
                // Parse the first part as integer and second as word
                int number = Integer.parseInt(parts[0]);
                String word = parts[1];
                list.add(new DataRow(number, word));
            } catch (NumberFormatException e) {
                // If parsing fails and it's the first line, assume header and skip it
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

        // If target not found, write -1 to output file
        writer.write("-1\n");
        writer.close();
    }
}
