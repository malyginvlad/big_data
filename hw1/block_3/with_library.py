import pandas as pd
import numpy as np

df = pd.read_csv('AB_NYC_2019.csv')

print(f'Mean: {np.mean(df.price.values)}, Variance: {np.var(df.price.values)}')
