import configparser
import csv
import math
import pandas as pd

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])

n = int(config['DEFAULT']['n'])
side = float(config['DEFAULT']['l'])
v = float(config['DEFAULT']['v'])
m = int(config['DEFAULT']['m'])
rc = float(config['DEFAULT']['rc'])
iteration = int(config['DEFAULT']['iter'])


filepath = config['DEFAULT']['python'] + 'va_output100_5_'
files = ['001','05','10','15','20',
         '25','30','35','40','45','50']

def parse_va(filename):
    float_list = []
    df = pd.read_csv(filename)
    float_list = df.values.flatten().astype(float).tolist()
    return float_list


files_list = []
for file in files:
    files_list.append(parse_va(filepath + file + '.csv'))
