import sys
import csv

def read_csv_input(file):
    for row in csv.reader(file):
        yield row

def main():
    price_num_col = 0
    for i, line in enumerate(read_csv_input(sys.stdin)):
        if i == 0:
            for j, item in enumerate(line):
    	        if item == 'price':
    	            price_num_col = j
        else:
            if len(line) > price_num_col:
                print(str(line[price_num_col]), 1, sep="\t", end="\n")
	    

if __name__ == "__main__":
    main()
