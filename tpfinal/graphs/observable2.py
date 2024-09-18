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
guards_number = list(range(1, 39))
media_fails_array = []
media_successes_array = []
std_successes_array = []
std_fails_array = []
media_fails_dim = []
media_successes_dim = []
current_successes = []
current_fails = []

for i in guards_number:
    file_name = '../ios/simulation' + str(i) + '.csv'
    media_successes = 0.0
    media_fails = 0.0
    successes = 0
    fails = 0
    current_successes = []
    current_fails = []
    with open(file_name, mode='r') as file:
        lector_csv = csv.reader(file)
        for j in lector_csv:
            if int(j[0]) == 1: #promedio llegada
                current_successes.append(float(j[1]))
            else:
                aux = float(j[1])
                if aux < 15:
                    current_fails.append(aux)
                # current_fails.append(float(j[1]))
        #media_successes = media_successes/successes
        #media_fails = media_fails/fails
        media_successes_array.append(np.mean(current_successes))
        media_fails_array.append(np.mean(current_fails))
        std_successes_array.append(np.std(current_successes))
        std_fails_array.append(np.std(current_fails))

# print(media_successes_array)
plt.errorbar(guards_number, media_successes_array, yerr=std_successes_array, fmt='o', capsize=5)

plt.xlabel('Cantidad de guardias')
plt.ylabel('Promedio tiempo de éxito de fan')
# plt.yscale('log')
# plt.legend()

plt.grid(True)  
plt.show()

# print(media_fails_array)
plt.errorbar(guards_number, media_fails_array, yerr=std_fails_array, fmt='o', capsize=5)

plt.xlabel('Cantidad de guardias')
plt.ylabel('Promedio tiempo de fallo de fan')
# plt.legend()

plt.grid(True)  
plt.show()