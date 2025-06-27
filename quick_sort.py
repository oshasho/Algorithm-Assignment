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

def quick_sort(arr, low, high):
    if low < high:
        pi = partition(arr, low, high)
        quick_sort(arr, low, pi - 1)
        quick_sort(arr, pi + 1, high)

def partition(arr, low, high):
    pivot = arr[high][0]  # still using last element
    i = low - 1
    for j in range(low, high):
        if arr[j][0] < pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

def main():
    if len(sys.argv) != 2:
        print("Usage: python quick_sort.py dataset_n.csv")
        return

    input_file = sys.argv[1]
    data = read_csv(input_file)

    start_time = time.time()
    quick_sort(data, 0, len(data) - 1)
    end_time = time.time()

    output_file = f"quick_sort_{len(data)}.csv"
    write_csv(output_file, data)

    print(f"Sorted {len(data)} elements.")
    print(f"Output written to: {output_file}")
    print(f"Sorting time: {end_time - start_time:.6f} seconds")

if __name__ == "__main__":
    main()