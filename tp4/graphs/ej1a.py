import configparser
import parser
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
import math

g = 6.693 * (10 ** (-20))
sun_mass = 1988500 * (10 ** 24)
earth_mass = 5.97219 * (10 ** 24)
mars_mass = 6.4171 * (10 ** 23)
ship_mass = 2 * (10 ** 5)
masses = [sun_mass, earth_mass, mars_mass, ship_mass]

def calculate_kinetics(day):
    kinetics = 0
    for i in range(4):
        kinetics += 0.5 * masses[i] * ((day[i][3] ** 2) + (day[i][4] ** 2))
    return kinetics

def calculate_potential(day):
    potential = 0
    for i in range(3):
        j = i+1
        while j < 4:
            distance = math.sqrt(((day[i][1] - day[j][1]) ** 2) + ((day[i][2] - day[j][2]) ** 2))
            potential += g * (-1) * ((masses[i] * masses[j])/distance)
            j += 1
    return potential

def calculate_day_energys(day_energys, days):
    day0_total_energy = calculate_potential(days[0]) + calculate_kinetics(days[0])
    total_energy = 0
    for day in days:
        total_energy = calculate_kinetics(day) + calculate_potential(day)
        day_energys.append((total_energy - day0_total_energy) / day0_total_energy)
        # day0_total_energy = total_energy

days_1_second = parser.parse_csv('../ios/simulation_1_second0.csv')
days_1_minute = parser.parse_csv('../ios/simulation_1_minute0.csv')
days_1_hour = parser.parse_csv('../ios/simulation_1_hour0.csv')
days_1_day = parser.parse_csv('../ios/simulation_1_day0.csv')

day_energys_1_minute = []
day_energys_1_second = []
day_energys_1_hour = []
day_energys_1_day = []

calculate_day_energys(day_energys_1_minute, days_1_minute)
calculate_day_energys(day_energys_1_second, days_1_second)
calculate_day_energys(day_energys_1_hour, days_1_hour)
calculate_day_energys(day_energys_1_day, days_1_day)

days_dim_1_minute = range(len(days_1_minute))
days_dim_1_second = range(len(days_1_second))
days_dim_1_hour = range(len(days_1_hour))
days_dim_1_day = range(len(days_1_day))

plt.scatter(days_dim_1_second, day_energys_1_second, label='delta t = 1')
plt.scatter(days_dim_1_minute, day_energys_1_minute, label='delta t = 60')
# plt.scatter(days_dim_1_hour, day_energys_1_hour, label='delta t = 3600')
# plt.scatter(days_dim_1_day, day_energys_1_day, label='delta t = 84600')
# plt.yscale('symlog')
# Agregar etiquetas y título
plt.xlabel('Dias')
plt.ylabel('Enegia')
plt.legend()  # Mostrar leyenda con los nombres de las funciones

# Mostrar el gráfico
plt.grid(True)  # Agregar rejilla
plt.show()

