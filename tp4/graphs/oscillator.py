import pandas as pd
import matplotlib.pyplot as plt
import configparser
import numpy as np

config = configparser.ConfigParser()
config.read('../configs/oscillator.config')

# file a leer
filename = config['DEFAULT']['python'] + 'oscillation_2.csv'

# Read the CSV file into a DataFrame
df = pd.read_csv(filename, delimiter=';')

# Access each column as a Pandas Series
times = df['time'].tolist()
analytic = df['analytic'].tolist()
gear = df['gear'].tolist()
beeman = df['beeman'].tolist()
verlet = df['verlet'].tolist()

plt.plot(times[1:], analytic[1:], label="Analitico")
plt.plot(times[1:], gear[:-1], label="Gear Predict-Correct")
plt.plot(times[1:], beeman[:-1], label="Beeman")
plt.plot(times[1:], verlet[:-1], label="Verlet")

# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Posición [m]')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()

# mse_gear = ((np.array(gear) - np.array(analytic))**2).mean(axis=0)
# mse_beeman = ((np.array(beeman) - np.array(analytic))**2).mean(axis=0)
# mse_verlet = ((np.array(verlet) - np.array(analytic))**2).mean(axis=0)
# print(mse_gear, mse_beeman, mse_verlet)


range_1 = 110
range_2 = 112
snapshot_times = times[range_1:range_2]
snapshot_analytic = analytic[range_1:range_2]
snapshot_gear = gear[range_1:range_2]
snapshot_beeman = beeman[range_1:range_2]
snapshot_verlet = verlet[range_1:range_2]

plt.plot(snapshot_times, snapshot_analytic, label="Analitico")
plt.plot(snapshot_times, snapshot_gear, label="Gear Predict-Correct")
plt.plot(snapshot_times, snapshot_beeman, label="Beeman")
plt.plot(snapshot_times, snapshot_verlet, label="Verlet")

# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Posición [m]')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()


def mse_calc(analytic, gear, beeman, verlet):
    aux_g = 0
    aux_b = 0
    aux_v = 0
    for a,g,b,v in zip(analytic, gear, beeman, verlet):
        aux_g += (g-a)**2
        aux_b += (b-a)**2
        aux_v += (v-a)**2

    aux_g /= len(analytic)
    aux_b /= len(analytic)
    aux_v /= len(analytic)

    return aux_g, aux_b, aux_v

# ------------- MSE --------------------#

files = ['oscillation_6.csv', 'oscillation_5.csv', 'oscillation_4.csv', 'oscillation_3.csv', 'oscillation_2.csv']

gear_mse = []
beeman_mse = []
verlet_mse = []
error = [1, 2, 3, 4, 5]
ticks = ['$10^{-6}$', '$10^{-5}$', '$10^{-4}$', '$10^{-3}$', '$10^{-2}$']

for file in files:
    filename = config['DEFAULT']['python'] + file

    df = pd.read_csv(filename, delimiter=';')
    # Access each column as a Pandas Series
    times = df['time'].tolist()
    analytic = df['analytic'].tolist()
    gear = df['gear'].tolist()
    beeman = df['beeman'].tolist()
    verlet = df['verlet'].tolist()

    mse_gear = ((np.array(gear[:-1]) - np.array(analytic[1:]))**2).mean(axis=0)
    mse_beeman = ((np.array(beeman[:-1]) - np.array(analytic[1:]))**2).mean(axis=0)
    mse_verlet = ((np.array(verlet[:-1]) - np.array(analytic[1:]))**2).mean(axis=0)
    # print(mse_gear, mse_beeman, mse_beeman)

    # mse_gear, mse_beeman, mse_verlet = mse_calc(analytic, verlet, beeman, gear)
    # print(mse_gear, mse_beeman, mse_beeman)

    gear_mse.append(mse_gear)
    beeman_mse.append(mse_beeman)
    verlet_mse.append(mse_verlet)


plt.scatter(error, gear_mse, label="Gear Predict-Correct", marker='o')
plt.scatter(error, beeman_mse, label="Beeman", marker='o')
plt.scatter(error, verlet_mse, label="Verlet", marker='o')

# Agregar etiquetas de ejes y título
plt.xlabel('Delta T [s]')
plt.xticks(error, labels=ticks)
plt.ylabel('ECM')
plt.yscale('log')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()



