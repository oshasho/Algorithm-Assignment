import csv
import os

def load_dataset(filename):
    with open(filename, newline='') as file:
        reader = csv.reader(file)
        return [(int(row[0]), row[1]) for row in reader]

def binary_search_step(dataset, target, output_filename):
    left = 0
    right = len(dataset) - 1

    with open(output_filename, "w") as f:
        while left <= right:
            mid = (left + right) // 2
            number, text = dataset[mid]

            f.write(f"{mid + 1}: {number}/{text}\n")

            if number == target:
                return
            elif number < target:
                left = mid + 1
            else:
                right = mid - 1

        f.write("-1\n")

# Get the directory where this script is located
script_dir = os.path.dirname(os.path.abspath(__file__))

csv_files = [f for f in os.listdir(script_dir) if f.lower().endswith(".csv")]

if not csv_files:
    print("No CSV files found in the current directory.")
    exit()

# Display options to user
print("Choose a dataset:")
for idx, fname in enumerate(csv_files, start=1):
    print(f"{idx}. {fname}")

try:
    choice = int(input(f"Enter choice (1-{len(csv_files)}): "))
    if choice < 1 or choice > len(csv_files):
        raise ValueError
except ValueError:
    print("Invalid choice.")
    exit()

filename = csv_files[choice - 1]
full_path = os.path.join(script_dir, filename)

# Ask for target
try:
    target = int(input("Enter target value to search: "))
except ValueError:
    print("Invalid input. Please enter an integer.")
    exit()

output_file = f"binary_search_step_{target}.txt"

data = load_dataset(full_path)

binary_search_step(data, target, output_file)

print(f"Search steps saved to {output_file}")
