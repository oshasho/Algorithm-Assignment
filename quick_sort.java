import java.io.*;
import java.util.*;

public class quick_sort {

    static class Element {
        int number;
        String word;

        Element(int number, String word) {
            this.number = number;
            this.word = word;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java quick_sort dataset_n.csv");
            return;
        }

        String inputFile = args[0];
        List<Element> data;

        try {
            data = readCSV(inputFile);
        } catch (IOException e) {
            System.out.println("Error reading input file.");
            return;
        }

        long startTime = System.nanoTime();
        quickSort(data, 0, data.size() - 1);
        long endTime = System.nanoTime();

        String outputFile = "quick_sort_" + data.size() + ".csv";

        try {
            writeCSV(outputFile, data);
        } catch (IOException e) {
            System.out.println("Error writing output file.");
            return;
        }

        System.out.println("Sorted " + data.size() + " elements.");
        System.out.println("Output written to: " + outputFile);
        System.out.printf("Sorting time: %.6f seconds%n", (endTime - startTime) / 1e9);
    }

    static List<Element> readCSV(String filename) throws IOException {
        List<Element> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                list.add(new Element(Integer.parseInt(parts[0]), parts[1]));
            }
        }
        return list;
    }

    static void writeCSV(String filename, List<Element> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Element e : data) {
                writer.write(e.number + "," + e.word);
                writer.newLine();
            }
        }
    }

    static void quickSort(List<Element> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
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
}