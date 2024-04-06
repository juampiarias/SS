import math

import matplotlib.pyplot as plt
import numpy as np
import parser
import copy

class zoneOBC:
    def __init__(self, x, y, actual_visitors, timestamps):
        self.x = x
        self.y = y
        self.timestamps = timestamps
        self.actual_visitors = actual_visitors

def visitasOBC(zones, particles, particles_dim, timestamps_dim):
    for i in range(timestamps_dim):
        for zone in zones:
            for j in range(particles_dim):
                if math.sqrt((particles[i][j][1] - zone.y)**2 + (particles[i][j][0] - zone.x)**2) <= 0.5:
                    if zone.actual_visitors[j] == 0:
                        zone.timestamps[i] += 1
                        zone.actual_visitors[j] = 1
                    else:
                        pass
                elif (i!=0 and cruzo_borde(particles[i-1][j], particles[i][j])):
                    zone.actual_visitors[j] = 0

def cruzo_borde(particle1, particle2):
    if ((particle1[0] - particle2[0]) > parser.v or (particle1[0] - particle2[0]) < -parser.v or 
        (particle1[1] - particle2[1]) > parser.v or (particle1[1] - particle2[1]) < -parser.v) :
        return True
    return False

if __name__ == "__main__":
    zone_timestamps = []
    total_timestamps = []

    files = ['output1000eta05.csv', 'output1000eta23.csv', 'output1000eta7.csv', 'output1000eta5.csv', 'output1000eta10.csv']

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

        zonesOBC = [
            zoneOBC(1.927489723947892, 4.017456341876351, visitors, timestamps),
            zoneOBC(2.657205763528561, 4.593027456837620, visitors2, timestamps2),
            zoneOBC(0.502348793856942, 3.578419012939278, visitors3, timestamps3),
            zoneOBC(3.278639721038147, 2.987487901283908, visitors4, timestamps4)
        ]

        particles = parser.parse_csv(parser.config['DEFAULT']['python'] + file)
        visitasOBC(zonesOBC, particles, parser.n, parser.iteration)


        for i in range(1, parser.iteration):
            timestamps[i] += timestamps[i-1]
            timestamps2[i] += timestamps2[i-1]
            timestamps3[i] += timestamps3[i-1]
            timestamps4[i] += timestamps4[i-1]    
            total_timestamp[i] = timestamps[i] + timestamps2[i] + timestamps3[i] + timestamps4[i]
            
        zone_timestamps.append([timestamps, timestamps2, timestamps3, timestamps4])
        total_timestamps.append(total_timestamp)


    plt.plot(range(0, parser.iteration), total_timestamps[0], label='PBC con eta = 0.5')
    plt.plot(range(0, parser.iteration), total_timestamps[1], label='PBC con eta = 2.3')
    plt.plot(range(0, parser.iteration), total_timestamps[2], label='PBC con eta = 7')
    plt.plot(range(0, parser.iteration), total_timestamps[3], label='PBC con eta = 5')
    plt.plot(range(0, parser.iteration), total_timestamps[4], label='PBC con eta = 10')

    # Agregar etiquetas de ejes y título
    plt.xlabel('Tiempo')
    plt.ylabel('Cantidad de visitas')
    # Agregar una leyenda
    plt.legend()
    # Mostrar el gráfico
    plt.show()