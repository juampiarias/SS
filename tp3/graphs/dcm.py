import configparser
import csv
import math
import matplotlib.pyplot as plt
import numpy as np

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])
side = float(config['DEFAULT']['l'])

def parse_times(file_name):
    adjusted_times = []
    coordinates = []
    times = []
    dcm = []
    delta_t = 0.01
    time = 0
    with(open(file_name, 'r') as file):
        reader = csv.reader(file, delimiter=';')

        for row in reader:
            if row:
                if row[0].startswith('t'):
                    numbers = row[0].split()
                    new_s = numbers[0][1:]
                    current_time = float(new_s)
                elif row[0].startswith('0') and current_time >= time:
                    idd = int(row[0])
                    rx = float(row[1])
                    ry = float(row[2])
                    vx = float(row[3])
                    vy = float(row[4])
                    m = float(row[5])
                    radius = float(row[6])
                    coordinates.append([idd, rx, ry, vx, vy, m, radius])
                    times.append(current_time)
                    adjusted_times.append(time)
                    aux = math.pow(rx - side/2,2) + math.pow(ry - side/2,2)
                    dcm.append(aux)
                    time += delta_t

    return times, coordinates, dcm, adjusted_times

dcms = []
y = []

for i in range(1,26):
    file = 'output' + str(i) + 'dcm.csv'
    times, coordinates, dcm, adjusted_times = parse_times(config['DEFAULT']['python'] + file)
    plt.plot(adjusted_times, dcm)
    dcm = dcm[:70]
    dcms.append(dcm)
    y = adjusted_times[:70]

for i,t in enumerate(y):
    if t == 0.2:
        print(y)


# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Z*Z')
plt.grid(True)
# Mostrar el gráfico
plt.show()

dcms = np.array(dcms)
x = np.mean(dcms, 0)
plt.plot(y, x)
# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Z*Z')
plt.grid(True)
# Mostrar el gráfico
plt.show()
