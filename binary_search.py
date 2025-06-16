import csv
import time
import os

def load_dataset(filename):
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile)
        return [(int(row[0]), row[1]) for row in reader]

def binary_search(data, target):
    left, right = 0, len(data) - 1
    while left <= right:
        mid = (left + right) // 2
        mid_val = data[mid][0]
        if mid_val == target:
            return mid
        elif mid_val < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1

def measure_time(data, target):
    n = len(data)
    start = time.perf_counter_ns()
    for _ in range(n):
        binary_search(data, target)
    end = time.perf_counter_ns()
    return (end - start) / n

# Get the directory where this script is located
script_dir = os.path.dirname(os.path.abspath(__file__))

# User Dataset selection
print("Choose dataset:")
print("1. quick_sort_1000.csv")
# print("2. merge_sort_100000.csv")
# print("3. quick_sort_100000.csv")
choice = input("Enter choice (1-3): ")

dataset_map = {
    "1": "quick_sort_1000.csv",
    # "2": "merge_sort_100000.csv",
    # "3": "quick_sort_100000.csv"
}

filename = dataset_map.get(choice)
if filename is None:
    print("Invalid choice.")
    exit()

# Full path to dataset in same folder
full_path = os.path.join(script_dir, filename)

# Load the selected dataset
data = load_dataset(full_path)
n = len(data)

# Pick targets
best_target = data[n // 2][0]
average_target = data[n // 4][0]
worst_target = 999999999  # Not in dataset

# Run timing
best_time = measure_time(data, best_target)
avg_time = measure_time(data, average_target)
worst_time = measure_time(data, worst_target)

# Save result to file (in same folder)
output_file = os.path.join(script_dir, f"binary_search_{n}.txt")
with open(output_file, "w") as f:
    f.write(f"Best case : {best_time:.2f} ns\n")
    f.write(f"Average case : {avg_time:.2f} ns\n")
    f.write(f"Worst case : {worst_time:.2f} ns\n")

print(f"\nResults written to {output_file}")
