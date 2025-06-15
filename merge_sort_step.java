import java.io.*;
import java.util.*;

public class MergeSortStep {

    private static class DataElement {
        final int numericValue;
        final String textValue;

        public DataElement(int numericValue, String textValue) {
            this.numericValue = numericValue;
            this.textValue = textValue.trim();
        }
    }

    private static List<String> sortingSteps = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java MergeSort <input_file> <start_row> <end_row>");
            return;
        }
        String inputFilename = args[0];
        int startRow = Integer.parseInt(args[1]);
        int endRow = Integer.parseInt(args[2]);

        List<DataElement> elements = loadDatasetSubset(inputFilename, startRow, endRow);
        sortingSteps.clear();
        sortingSteps.add(formatElements(elements));

        performMergeSort(elements, 0, elements.size() - 1);
        saveSortingSteps("merge_sort_step_" + startRow + "_" + endRow + ".txt");
    }
    
    private static List<DataElement> loadDatasetSubset(String filename, int startRow, int endRow) {
        List<DataElement> elements = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line;
            int currentRow = 0;

            while ((line = fileReader.readLine()) != null) {
                currentRow++;
                if (currentRow >= startRow && currentRow <= endRow) {
                    String[] columns = line.split(",", 2);
                    if (columns.length == 2) {
                        elements.add(new DataElement(Integer.parseInt(columns[0]), columns[1]));
                    }
                }
                if (currentRow > endRow) break;
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            System.exit(1);
        }

        return elements;
    }

    private static void performMergeSort(List<DataElement> arr, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            performMergeSort(arr, start, middle);
            performMergeSort(arr, middle + 1, end);
            mergeElements(arr, start, middle, end);
        }
    }

    private static void mergeElements(List<DataElement> arr, int start, int middle, int end) {
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

        sortingSteps.add(formatElements(arr));
    }

    private static String formatElements(List<DataElement> elements) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) sb.append(", ");
            DataElement e = elements.get(i);
            sb.append(e.numericValue).append("/").append(e.textValue);
        }
        return sb.append("]").toString();
    }

    private static void saveSortingSteps(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String step : sortingSteps) {
                writer.println(step);
            }
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
        }
    }
}
