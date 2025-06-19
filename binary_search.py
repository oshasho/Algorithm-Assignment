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

# Get current directory
script_dir = os.path.dirname(os.path.abspath(__file__))

# List all .csv files in the directory
csv_files = [f for f in os.listdir(script_dir) if f.lower().endswith('.csv')]

if not csv_files:
    print("No CSV files found in the current directory.")
    exit()

# Show list to user
print("Choose dataset:")
for idx, fname in enumerate(csv_files, start=1):
    print(f"{idx}. {fname}")

try:
    choice = int(input(f"Enter choice (1-{len(csv_files)}): "))
    if not (1 <= choice <= len(csv_files)):
        raise ValueError
except ValueError:
    print("Invalid choice.")
    exit()

# Get full file path
filename = csv_files[choice - 1]
full_path = os.path.join(script_dir, filename)

# Load dataset
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

# Save result to file
output_file = os.path.join(script_dir, f"binary_search_{n}.txt")
with open(output_file, "w") as f:
    f.write(f"Best case time    : {best_time:.2f} ns\n")
    f.write(f"Average case time : {avg_time:.2f} ns\n")
    f.write(f"Worst case time   : {worst_time:.2f} ns\n")

print(f"\nResults written to {output_file}")