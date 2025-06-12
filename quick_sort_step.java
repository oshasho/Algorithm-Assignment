import java.io.*;
import java.util.*;

public class quick_sort_step {

    static class Element {
        int number;
        String word;

        Element(int number, String word) {
            this.number = number;
            this.word = word;
        }

        public String toString() {
            return number + "/" + word;
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java QuickSortStep dataset_sample_1000.csv startRow endRow");
            return;
        }

        String inputFile = args[0];
        int startRow = Integer.parseInt(args[1]);
        int endRow = Integer.parseInt(args[2]);

        try {
            List<Element> data = readSubset(inputFile, startRow, endRow);
            List<String> stepLog = new ArrayList<>();
            stepLog.add(formatList(data)); // Initial state
            quickSort(data, 0, data.size() - 1, stepLog);
            writeSteps("quick_sort_step_" + startRow + "_" + endRow + ".txt", stepLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<Element> readSubset(String filename, int startRow, int endRow) throws IOException {
        List<Element> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 1;
            while ((line = reader.readLine()) != null) {
                if (row >= startRow && row <= endRow) {
                    String[] parts = line.split(",", 2);
                    list.add(new Element(Integer.parseInt(parts[0]), parts[1]));
                }
                row++;
            }
        }
        return list;
    }

    static void writeSteps(String filename, List<String> steps) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String step : steps) {
                writer.write(step);
                writer.newLine();
            }
        }
    }

    static void quickSort(List<Element> arr, int low, int high, List<String> log) {
        if (low < high) {
            int pi = partition(arr, low, high);
            log.add("pi=" + pi + " " + formatList(arr));
            quickSort(arr, low, pi - 1, log);
            quickSort(arr, pi + 1, high, log);
        }
    }

    static int partition(List<Element> arr, int low, int high) {
        int pivot = arr.get(high).number;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr.get(j).number < pivot) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, high);
        return i + 1;
    }

    static String formatList(List<Element> arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.size(); i++) {
            sb.append(arr.get(i));
            if (i < arr.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}