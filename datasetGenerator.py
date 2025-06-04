#CCP6214 Algorithm Design and Analysis
# Lecture Section : TC5L
# Tutorial Section : T20L 
#Group Number : Group 14
#Group Members
#Nur Arissa Hanani binti Mohamed Adzlan
#Pavithira Saravanan
#Tharraniah Tamilwanan
#Zainal Zaim Hakimi Bin Zainal Effendy




import random
import string
import csv


def generate_random_string(length=5):
# Function to generate a random lowercase string with the default length of 5
    return ''.join(random.choices(string.ascii_lowercase, k=length))

# Function to generate n unique integers within the given range
def generate_unique_integers(n, min_value= 1, max_value= 1000000000):
    return random.sample(range(min_value, max_value), n)

# Main function to generate and save the dataset
def generate_dataset(n, filename):
    integers = generate_unique_integers(n)
    dataset_rows = [(i, generate_random_string()) for i in integers]

    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for row in dataset_rows:
            writer.writerow(row)

    print(f"Dataset with {n} rows will be printed in {filename}")

# Run the program
if __name__ == "__main__":
    try:
        n = int(input("Enter a number for the dataset size : "))
        filename = f"dataset_{n}.csv"
        generate_dataset(n, filename)
    except ValueError:
        print("Invalid input. Please enter a valid number.")  #Invalid input like (e.g. special characters)