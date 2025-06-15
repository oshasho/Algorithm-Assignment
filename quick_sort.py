import csv
import sys
import time

def read_csv(filename):
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile)
        return [(int(row[0]), row[1]) for row in reader]

def write_csv(filename, data):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        for item in data:
            writer.writerow([item[0], item[1]])

def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    pivot = arr[-1][0]
    less = [x for x in arr[:-1] if x[0] < pivot]
    equal = [x for x in arr if x[0] == pivot]
    greater = [x for x in arr[:-1] if x[0] > pivot]
    return quick_sort(less) + equal + quick_sort(greater)

def main():
    if len(sys.argv) != 2:
        print("Usage: python quick_sort.py dataset_n.csv")
        return

    input_file = sys.argv[1]
    data = read_csv(input_file)

    start_time = time.time()
    sorted_data = quick_sort(data)
    end_time = time.time()

    output_file = f"quick_sort_{len(data)}.csv"
    write_csv(output_file, sorted_data)

    print(f"Sorted {len(data)} elements.")
    print(f"Output written to: {output_file}")
    print(f"Sorting time: {end_time - start_time:.6f} seconds")

if __name__ == "__main__":
    main()