import configparser
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


filepath = config['DEFAULT']['python'] + 'va_output'
# files = ['5','10','15','20','25','35','50','75','100','150','200','250']
# files = ['25','50','75','100','150','200','250','500','625','750','1000',
#          '1250','1500','2000','2500','3000','4000','5000','6000']
files = ['25','20','15','10','5']

def parse_va(filename):
    float_list = []
    df = pd.read_csv(filename)
    float_list = df.values.flatten().astype(float).tolist()
    return float_list


files_list = []
for file in files:
    files_list.append(parse_va(filepath + file + '.csv'))
