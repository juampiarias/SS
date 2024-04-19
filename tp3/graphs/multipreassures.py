import configparser
import parser
import csv
import matplotlib.pyplot as plt
import math
import pressions
import numpy as np

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])
side = float(config['DEFAULT']['l'])
means = []
stdlist = []
v = [1, 9, 36, 100]
# v = [1]
# files = ['outputv1.csv']

files = ['outputv1.csv', 'outputv3.csv', 'outputv6.csv', 'outputv10.csv']

for file in files:
    delta_ts, counts, o_counts = pressions.pressure(config['DEFAULT']['python'] + file)
    means.append(np.mean(counts))
    stdlist.append(np.std(counts))

# Agregar etiquetas de ejes y título

plt.errorbar(v, means, yerr=stdlist, fmt='o', capsize=5)
plt.xlabel('v^2')
plt.ylabel('Preassures')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()