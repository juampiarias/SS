import configparser
import parser
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
import math

ship = 3
days = parser.parse_csv('../ios/simulation65.csv')

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

def get_speed_array(days):
    velocitys = []
    velocity = 0
    for day in days:
        velocity = math.sqrt((day[ship][3] ** 2) + (day[ship][4] ** 2))
        velocitys.append(velocity)
    return velocitys

dist, arrival_day = find_minimum_distance('../ios/simulation65.csv')
velocitys = get_speed_array(days)
days_dim = range(len(velocitys))
# plt.scatter(days_dim[:arrival_day], velocitys[:arrival_day], label='Evolución temporal de la velocidad ')
plt.scatter(days_dim, velocitys, label='Evolución temporal de la velocidad ')

plt.xlabel('Dias')
plt.ylabel('Velocidad')
plt.legend()

plt.grid(True)  
plt.show()
