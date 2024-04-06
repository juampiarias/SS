import math

import matplotlib.pyplot as plt
import numpy as np
import parser
import copy

class zonePBC:
    def __init__(self, x, y, visitors, timestamps):
        self.x = x
        self.y = y
        self.visitors = visitors
        self.timestamps = timestamps

def visitasPBC(zones, particles, particles_dim, timestamps_dim, total_timestamp):
    for i in range(timestamps_dim):
        for zone in zones:
            for j in range(particles_dim):
                if math.sqrt((particles[i][j][1] - zone.y)**2 + (particles[i][j][0] - zone.x)**2) <= 0.5 and (zone.visitors[j] == 0):
                    zone.visitors[j] = 1
                    total_timestamp[i] += 1
                    zone.timestamps[i] += 1

if __name__ == "__main__":

    zone_timestamps = []
    total_timestamps = []
    times_tograph = []

    files = ['output1000eta05.csv', 'output1000eta23.csv', 'output1000eta3.csv', 'output1000eta5.csv']

    for file in files:
        visitors = [0] * parser.n
        timestamps = [0] * parser.iteration
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
            zonePBC(0.502348793856942, 3.578419012939278, visitors3, timestamps3),
            zonePBC(3.278639721038147, 2.987487901283908, visitors4, timestamps4)
        ]

        particles = parser.parse_csv(parser.config['DEFAULT']['python'] + file)
        visitasPBC(zonesPBC, particles, parser.n, parser.iteration, total_timestamp)

        aux = [0 ,0, 0, 0]

        for i in range(1, parser.iteration):
            timestamps[i] += timestamps[i-1]
            timestamps2[i] += timestamps2[i-1]
            timestamps3[i] += timestamps3[i-1]
            timestamps4[i] += timestamps4[i-1]    
            total_timestamp[i] += total_timestamp[i-1]

            if timestamps[i] >= 180 and aux[0] == 0:
                aux[0] = i
            if timestamps2[i] >= 180 and aux[1] == 0:
                aux[1] = i
            if timestamps3[i] >= 180 and aux[2] == 0:
                aux[2] = i
            if timestamps4[i] >= 180 and aux[3] == 0:
                aux[3] = i
            
        zone_timestamps.append([timestamps, timestamps2, timestamps3, timestamps4])
        total_timestamps.append(total_timestamp)
        times_tograph.append(aux)


    plt.plot(range(0, parser.iteration), zone_timestamps[0][0], label='PBC con eta = 0.5', color='blue')
    plt.plot(range(0, parser.iteration), zone_timestamps[1][0], label='PBC con eta = 2.3', color='red')
    plt.plot(range(0, parser.iteration), zone_timestamps[2][0], label='PBC con eta = 3', color='green')
    plt.plot(range(0, parser.iteration), zone_timestamps[3][0], label='PBC con eta = 5', color='black')
    # Graficar las funciones
    for i in range(1,4):
        plt.plot(range(0, parser.iteration), zone_timestamps[0][i], color='blue')
        plt.plot(range(0, parser.iteration), zone_timestamps[1][i], color='red')
        plt.plot(range(0, parser.iteration), zone_timestamps[2][i], color='green')
        plt.plot(range(0, parser.iteration), zone_timestamps[3][i], color='black')

    # Agregar etiquetas de ejes y título
    plt.xlabel('Tiempo')
    plt.ylabel('Cantidad de visitas')
    # Agregar una leyenda
    plt.legend()
    # Mostrar el gráfico
    plt.show()

    mean_list = []
    std_list = []
    labels = ['0.5', '2.3', '3', '5']

    for stationary in times_tograph:
        eta_mean = np.mean(stationary)
        eta_std = np.std(stationary)
        mean_list.append(eta_mean)
        std_list.append(eta_std)

    plt.errorbar(range(len(labels)), mean_list, yerr=std_list, fmt='o', capsize=5)
    plt.xlabel("Ruido")
    plt.ylabel('Promedio en llegar al 60%')
    plt.xticks(range(len(labels)), labels=labels)
    plt.grid(True)
    plt.show()
