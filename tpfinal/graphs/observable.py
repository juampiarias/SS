import math
import parser
import configparser
import matplotlib.pyplot as plt
import numpy as np
import concurrent.futures
import csv

config = configparser.ConfigParser()
config.read('../configs/app.config')

fan_success = []
simulation_time = []
guards_number = list(range(1, 29))

for i in range(28):
    file_name = '../ios/simulation' + str(i + 1) + '.csv'
    successes = 0
    with open(file_name, mode='r') as file:
        lector_csv = csv.reader(file)
        for j in lector_csv:
            successes = successes + int(j[0])
        fan_success.append(successes)
print(fan_success)

plt.scatter(guards_number, fan_success)

plt.xlabel('Cantidad de guardias')
plt.ylabel('Cantidad de éxitos del fan')
plt.legend()

plt.grid(True)  
plt.show()
