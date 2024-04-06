import configparser
import csv
import math

import matplotlib.pyplot as plt
import numpy as np
from numpy import double
from matplotlib.animation import FuncAnimation
from parser import particles
import copy

class zonePBC:
    def __init__(self, x, y, visitors, timestamps):
        self.x = x
        self.y = y
        self.visitors = visitors
        self.timestamps = timestamps

class zoneOBC:
    def __init__(self, x, y, actual_visitors, timestamps):
        self.x = x
        self.y = y
        self.timestamps = timestamps
        self.actual_visitors = actual_visitors

def visitasPBC(zone_ratio, zones, particles, particles_dim, timestamps_dim, total_timestamp):
    for i in range(timestamps_dim):
        for zone in zones:
            for j in range(particles_dim):
                if math.sqrt((particles[i][j][1] - zone.y)**2 + (particles[i][j][0] - zone.x)**2) <= 0.5 and (zone.visitors[j] == 0):
                    zone.visitors[j] = 1
                    total_timestamp[i] += 1

def visitasOBC(zone_ratio, zones, particles, particles_dim, timestamps_dim):
    for i in range(timestamps_dim):
        for zone in zones:
            for j in range(particles_dim):
                if math.sqrt((particles[i][j][1] - zone.y)**2 + (particles[i][j][0] - zone.x)**2) <= 0.5:
                    if zone.actual_visitors[j] == 0:
                        zone.timestamps[i] += 1
                        zone.actual_visitors[j] = 1
                    else:
                        pass
                else:
                    zone.actual_visitors[j] = 0

if __name__ == "__main__":
    visitors = []
    timestamps = []
    for i in range(300):
        visitors.append(0)

    for j in range(50):
        timestamps.append(0)

    visitors2 = copy.deepcopy(visitors)
    timestamps2 = copy.deepcopy(timestamps)
    visitors3 = copy.deepcopy(visitors)
    timestamps3 = copy.deepcopy(timestamps)
    visitors4 = copy.deepcopy(visitors)
    timestamps4 = copy.deepcopy(timestamps)
    total_timestamp = copy.deepcopy(timestamps)

    zonesPBC = [
        zonePBC(1.927489723947892, 4.017456341876351, visitors, timestamps),
        zonePBC(2.657205763528561, 4.593027456837620, visitors2, timestamps2),
        zonePBC(0.102348793856942, 3.578419012939278, visitors3, timestamps3),
        zonePBC(3.278639721038147, 2.987487901283908, visitors4, timestamps4)
    ]

    zonesOBC = [
        zoneOBC(1.927489723947892, 4.017456341876351, visitors, timestamps),
        zoneOBC(2.657205763528561, 4.593027456837620, visitors2, timestamps2),
        zoneOBC(0.102348793856942, 3.578419012939278, visitors3, timestamps3),
        zoneOBC(3.278639721038147, 2.987487901283908, visitors4, timestamps4)
    ]

    visitasOBC(0.5, zonesOBC, particles, 300, 50)
    # print(particles)
    # print(visitors)
    # print(total_timestamp)
    for m in range(49):
        total_timestamp[m+1] += total_timestamp[m]
    # print(total_timestamp)

    # eta05 = [29, 36, 42, 48, 49, 56, 62, 70, 77, 86, 91, 95, 102, 107, 111, 114, 117, 121, 125, 129, 137, 142, 145, 149, 156, 157, 163, 167, 167, 170, 172, 176, 183, 184, 184, 191, 194, 197, 202, 209, 213, 216, 219, 226, 229, 231, 237, 242, 243, 248]
    # eta3 = [29, 34, 40, 45, 48, 54, 54, 56, 60, 61, 64, 66, 71, 74, 78, 78, 81, 84, 84, 85, 88, 89, 90, 92, 92, 93, 96, 96, 99, 101, 102, 108, 110, 110, 112, 112, 115, 117, 118, 119, 120, 126, 129, 131, 133, 134, 136, 139, 139, 140]
    # eta23 = [29, 35, 40, 46, 49, 51, 57, 64, 69, 71, 79, 86, 89, 97, 103, 104, 108, 112, 113, 118, 123, 125, 129, 133, 138, 141, 143, 148, 154, 158, 159, 164, 168, 170, 175, 179, 183, 187, 188, 193, 195, 198, 200, 202, 205, 207, 209, 211, 213, 216]
    # eta5 = [29, 39, 40, 41, 42, 42, 43, 45, 48, 50, 52, 54, 56, 56, 56, 58, 59, 59, 60, 62, 62, 64, 65, 68, 69, 70, 71, 71, 71, 71, 71, 72, 72, 74, 75, 79, 81, 82, 82, 83, 84, 85, 87, 88, 90, 91, 93, 94, 95, 98]

    time = []
    for t in range(50):
        time.append(t)

    print(timestamps)


    eta5 = [11, 1, 0, 2, 0, 1, 0, 3, 1, 3, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 2, 1, 2, 0, 1, 1, 0, 2, 1, 0, 0, 2, 1, 1, 1, 3, 0, 3, 0, 1, 1, 2, 1, 1, 2, 2, 1, 3]
    eta23 = [11, 2, 0, 3, 0, 3, 0, 2, 1, 0, 0, 2, 0, 0, 2, 0, 1, 1, 0, 1, 0, 2, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 2, 1, 3, 2, 2, 1, 1, 1, 0, 3, 3, 2, 2, 2, 3, 4]
    eta05 = [11, 2, 1, 2, 0, 1, 0, 0, 1, 1, 2, 0, 0, 1, 0, 0, 3, 1, 0, 2, 0, 0, 2, 2, 1, 3, 1, 1, 1, 0, 2, 2, 2, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0]
    eta3 = [11, 3, 1, 0, 0, 1, 2, 0, 0, 2, 0, 3, 0, 3, 1, 2, 2, 0, 1, 2, 1, 0, 2, 2, 3, 2, 2, 3, 1, 2, 1, 2, 1, 3, 0, 3, 2, 0, 4, 0, 1, 1, 2, 3, 1, 0, 0, 2, 2, 0]
    # Crear un nuevo gráfico
    plt.figure()

    # Graficar las funciones
    plt.plot(time, eta05, label='PBC con eta = 0.5', marker='o')
    plt.plot(time, eta23, label='PBC con eta = 2.3', marker='s')
    plt.plot(time, eta3, label='PBC con eta = 3', marker='^')
    plt.plot(time, eta5, label='PBC con eta = 5', marker='x')

    # Agregar etiquetas de ejes y título
    plt.xlabel('Tiempo')
    plt.ylabel('Cantidad de visitas')

    # Agregar una leyenda
    plt.legend()

    # Mostrar el gráfico
    plt.show()

    etas = [eta5, eta3, eta23, eta05]
    labels = ['5', '3', '2.3', '0.5']

    mean_list = []
    std_list = []

    for eta_list in etas:
        stationary = eta_list[-48:]
        eta_mean = np.mean(stationary)
        eta_std = np.std(stationary)
        mean_list.append(eta_mean)
        std_list.append(eta_std)

    plt.errorbar(range(len(etas)), mean_list, yerr=std_list, fmt='o', capsize=5)
    plt.xlabel("Ruido")
    plt.ylabel('Promedio Visitas')
    plt.xticks(range(len(labels)), labels=labels)
    plt.grid(True)
    plt.show()