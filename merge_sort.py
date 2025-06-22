import sys
import time

class MergeSort:
    class DataElement:
        def __init__(self, numeric_value, text_value):
            self.numericValue = numeric_value
            self.textValue = text_value.strip()

    def __init__(self):
        pass

    def run(self, args):
        if len(args) != 1:
            print("Usage: python merge_sort.py dataset_n.csv")
            return

        input_filename = args[0]
        dataset = self.load_dataset(input_filename)

        sorting_start = time.time_ns()
        self.perform_merge_sort(dataset, 0, len(dataset) - 1)
        sorting_end = time.time_ns()

        output_filename = "merge_sort_" + str(len(dataset)) + ".csv"
        self.save_sorted_results(dataset, output_filename)

        running_time = (sorting_end - sorting_start) / 1_000_000_000.0
        print(f"Sorting completed time: {running_time}seconds")

    def load_dataset(self, filename):
        elements = []
        try:
            with open(filename, 'r') as file_reader:
                for line in file_reader:
                    columns = line.split(",", 1)
                    if len(columns) == 2:
                        elements.append(self.DataElement(
                            int(columns[0].strip()),
                            columns[1]
                        ))
        except IOError as e:
            print(f"Error processing file: {e}", file=sys.stderr)
            sys.exit(1)

        return elements

    def perform_merge_sort(self, arr, start, end):
        if start < end:
            middle = (start + end) // 2
            self.perform_merge_sort(arr, start, middle)
            self.perform_merge_sort(arr, middle + 1, end)
            self.merge_elements(arr, start, middle, end)

    def merge_elements(self, arr, start, middle, end):
        temporary = []
        i = start
        j = middle + 1

        while i <= middle and j <= end:
            if arr[i].numericValue <= arr[j].numericValue:
                temporary.append(arr[i])
                i += 1
            else:
                temporary.append(arr[j])
                j += 1

        while i <= middle:
            temporary.append(arr[i])
            i += 1

        while j <= end:
            temporary.append(arr[j])
            j += 1

        for k in range(len(temporary)):
            arr[start + k] = temporary[k]

    def save_sorted_results(self, elements, filename):
        try:
            with open(filename, 'w') as writer:
                for element in elements:
                    writer.write(f"{element.numericValue},{element.textValue}\n")
        except IOError as e:
            print(f"Error writing output file: {e}", file=sys.stderr)

if __name__ == "__main__":
    MergeSort().run(sys.argv[1:])
