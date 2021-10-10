#!/usr/bin/python
import sys


def calc_mean(chunk_size_prev, mean_prev, chunk_size_cur, mean_cur):
    mean_value = (chunk_size_prev * mean_prev + chunk_size_cur * mean_cur) / (chunk_size_prev + chunk_size_cur)
    return mean_value

def calc_var(chunk_size_prev, var_prev, mean_prev, chunk_size_cur, mean_cur, var_cur):
    var_value = (chunk_size_prev * var_prev + chunk_size_cur * var_cur) / (chunk_size_prev + chunk_size_cur)
    var_value += chunk_size_prev * chunk_size_cur * (((mean_prev - mean_cur) / (chunk_size_prev + chunk_size_cur)) ** 2)
    return var_value

def main():
    chunk_size = 0
    var_cur = 0
    mean_cur = None
    chunk_size_prev = 0

    for line in sys.stdin:
        key, value = line.strip().split("\t")
        key = float(key)
        value = float(value)

        if key == mean_cur:
            chunk_size += value
        else:
            if mean_cur is not None:
                chunk_size_prev += chunk_size
                var_prev = var_cur
                mean_prev = mean_cur

                mean_cur = key
                chunk_size = value

                mean_cur = calc_mean(chunk_size_prev, mean_prev, chunk_size, mean_cur)
                var_cur = calc_var(chunk_size_prev, var_prev, mean_prev, chunk_size, key, 0)
                continue

            mean_cur = key
            chunk_size = value
    
    print(f'Mean: {mean_cur}, Variance: {var_cur}')


if __name__ == "__main__":
    main()

