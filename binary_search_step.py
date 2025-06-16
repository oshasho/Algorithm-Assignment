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

# User menu to select dataset file
print("Select dataset:")
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

full_path = os.path.join(script_dir, filename)

target = int(input("Enter target value to search: "))

output_file = f"binary_search_step_{target}.txt"

data = load_dataset(full_path)

for i, (num, txt) in enumerate(data[:10]):
    print(f"{i}: {num}/{txt}")
    
binary_search_step(data, target, output_file)

print(f"Search steps saved to {output_file}")
