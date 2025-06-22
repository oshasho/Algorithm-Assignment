import sys
from typing import List, Tuple

class DataElement:
    def __init__(self, numeric_value: int, text_value: str):
        self.numericValue = numeric_value
        self.textValue = text_value.strip()

sorting_steps = []

def main(args: List[str]) -> None:
    if len(args) != 3:
        print("Usage: python merge_sort_step.py <input_file> <start_row> <end_row>")
        return

    input_filename = args[0]
    start_row = int(args[1])
    end_row = int(args[2])

    elements = load_dataset_subset(input_filename, start_row, end_row)
    sorting_steps.clear()
    sorting_steps.append(format_elements(elements))

    perform_merge_sort(elements, 0, len(elements) - 1)
    save_sorting_steps(f"merge_sort_step_{start_row}_{end_row}.txt")

def load_dataset_subset(filename: str, start_row: int, end_row: int) -> List[DataElement]:
    elements = []
    
    try:
        with open(filename, 'r') as file_reader:
            current_row = 0
            for line in file_reader:
                current_row += 1
                if current_row >= start_row and current_row <= end_row:
                    columns = line.split(",", 1)
                    if len(columns) == 2:
                        elements.append(DataElement(int(columns[0]), columns[1]))
                if current_row > end_row:
                    break
    except IOError as e:
        print(f"Error processing file: {e}", file=sys.stderr)
        sys.exit(1)

    return elements

def perform_merge_sort(arr: List[DataElement], start: int, end: int) -> None:
    if start < end:
        middle = (start + end) // 2
        perform_merge_sort(arr, start, middle)
        perform_merge_sort(arr, middle + 1, end)
        merge_elements(arr, start, middle, end)

def merge_elements(arr: List[DataElement], start: int, middle: int, end: int) -> None:
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

    sorting_steps.append(format_elements(arr))

def format_elements(elements: List[DataElement]) -> str:
    sb = ["["]
    for i, e in enumerate(elements):
        if i > 0:
            sb.append(", ")
        sb.append(f"{e.numericValue}/{e.textValue}")
    sb.append("]")
    return "".join(sb)

def save_sorting_steps(filename: str) -> None:
    try:
        with open(filename, 'w') as writer:
            for step in sorting_steps:
                writer.write(step + "\n")
    except IOError as e:
        print(f"Error writing output file: {e}", file=sys.stderr)

if __name__ == "__main__":
    main(sys.argv[1:])
