import csv
import sys

#quick_sort modify sikit so boleh user friendly terima input lain
def read_csv_subset(filename, start_row, end_row):
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile)
        data = [row for i, row in enumerate(reader) if start_row <= i+1 <= end_row]
    return [(int(row[0]), row[1]) for row in data]

def write_steps(filename, steps):
    with open(filename, 'w') as f:
        for step in steps:
            f.write(step + '\n')

def quick_sort(arr, low, high, steps):
    if low < high:
        pi = partition(arr, low, high, steps)
        steps.append(f"pi={pi} {format_list(arr)}")
        quick_sort(arr, low, pi - 1, steps)
        quick_sort(arr, pi + 1, high, steps)

def partition(arr, low, high, steps):
    pivot = arr[high][0]
    i = low - 1
    for j in range(low, high):
        if arr[j][0] < pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i+1], arr[high] = arr[high], arr[i+1]
    return i + 1

def format_list(arr):
    return "[" + ", ".join(f"{x[0]}/{x[1]}" for x in arr) + "]"

def main():
    if len(sys.argv) != 4:
        print("Usage: python quick_sort_step.py dataset_sample_1000.csv start_row end_row")
        return

    filename, start_row, end_row = sys.argv[1], int(sys.argv[2]), int(sys.argv[3])
    data = read_csv_subset(filename, start_row, end_row)
    steps = [format_list(data)]
    quick_sort(data, 0, len(data) - 1, steps)
    output_file = f"quick_sort_step_{start_row}_{end_row}.txt"
    write_steps(output_file, steps)

if __name__ == "__main__":
    main()