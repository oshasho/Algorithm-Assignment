import java.io.*;
import java.util.*;

public class merge_sort {

    private class DataElement {
        final int numericValue;
        final String textValue;

        public DataElement(int numericValue, String textValue) {
            this.numericValue = numericValue;
            this.textValue = textValue.trim();
        }
    }

    public static void main(String[] args) {
        new merge_sort().run(args); // Create an instance and run
    }
    private void run(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java merge_sort dataset_n.csv");
            return;
        }

        String inputFilename = args[0];
        List<DataElement> dataset = loadDataset(inputFilename);

        long sortingStart = System.nanoTime();
        performMergeSort(dataset, 0, dataset.size() - 1);
        long sortingEnd = System.nanoTime();

        String outputFilename = "merge_sort_" + dataset.size() + ".csv";
        saveSortedResults(dataset, outputFilename);

        double runningTime = (sortingEnd - sortingStart) / 1_000_000_000.0;
        System.out.printf("Sorting completed time: " + runningTime + "seconds");
    }

    private List<DataElement> loadDataset(String filename) {
        List<DataElement> elements = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] columns = line.split(",", 2);
                if (columns.length == 2) {
                    elements.add(new DataElement(
                        Integer.parseInt(columns[0].trim()),
                        columns[1]
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            System.exit(1);
        }

        return elements;
    }

    private void performMergeSort(List<DataElement> arr, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            performMergeSort(arr, start, middle);
            performMergeSort(arr, middle + 1, end);
            mergeElements(arr, start, middle, end);
        }
    }

    private void mergeElements(List<DataElement> arr, int start, int middle, int end) {
        List<DataElement> temporary = new ArrayList<>();
        int i = start, j = middle + 1;

        while (i <= middle && j <= end) {
            if (arr.get(i).numericValue <= arr.get(j).numericValue) {
                temporary.add(arr.get(i++));
            } else {
                temporary.add(arr.get(j++));
            }
        }

        while (i <= middle) temporary.add(arr.get(i++)); 
        while (j <= end) temporary.add(arr.get(j++));
        
        for (int k = 0; k < temporary.size(); k++) {
            arr.set(start + k, temporary.get(k));
        }
    }

    private void saveSortedResults(List<DataElement> elements, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (DataElement element : elements) {
                writer.println(element.numericValue + "," + element.textValue);
            }
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
        }
    }
}
