import configparser
import parser
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
import math

def find_minimum_distance(filename):
    days = parser.parse_csv(filename)
    dist = 1e10
    iaux = 0
    for i, day in enumerate(days):
        aux = math.sqrt(math.pow(day[2][1] - day[3][1], 2) + math.pow(day[2][2] - day[3][2], 2))
        if aux < dist:
            iaux = i
            dist = aux
    return dist, iaux

def is_arrival_date(min_dist, filename):
    days = parser.parse_csv(filename)
    for i, day in enumerate(days):
        aux = math.sqrt(math.pow(day[2][1] - day[3][1], 2) + math.pow(day[2][2] - day[3][2], 2))
        if aux <= min_dist:
            return i
    return i

dist_to_compare, arrival_day = find_minimum_distance('../ios/simulationv8_65.csv')
arrival_days = []
for i in range(1, 21):
    filename = '../ios/simulationv' + str(i) + '_65.csv'
    min_arrival_day = is_arrival_date(dist_to_compare, filename)
    arrival_days.append(min_arrival_day)

# plt.scatter(days_dim[:arrival_day], velocitys[:arrival_day], label='Evolución temporal de la velocidad ')

plt.scatter(range(1, 21), arrival_days)

plt.xlabel('Velocidad inicial')
plt.ylabel('Dia de llegada')
plt.legend()

plt.grid(True)  
plt.show()
