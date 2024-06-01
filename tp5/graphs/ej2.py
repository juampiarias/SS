import math
import parser
import configparser
import matplotlib.pyplot as plt
import numpy as np
import concurrent.futures

config = configparser.ConfigParser()
config.read('../configs/app.config')

start_time = 25*60*int(config['DEFAULT']['start_time'])
end_time = 25*60*int(config['DEFAULT']['end_time'])

times_home, times_away, times_ball, times = parser.parse_players(start_time, end_time)

def plot_distance(filename, ball_plot):
    times_loco = parser.parse_loco(filename)
    dist = distance_to_ball(ball_plot, times_loco)
    return dist

def distance_to_ball(balls, loco):
    dist = []
    for b,l in zip(balls, loco):
        dist.append(math.sqrt(math.pow(b[0]-l[0],2) + math.pow(b[1]-l[1],2)))

    return dist

desired = 0.1
step = 0.1
x = np.arange(desired, 1.1, step)

filenames = [config['DEFAULT']['python'] + config['DEFAULT']['output'] + "_tau" + str(i) + ".csv" for i in range(0, 10)]

max_threads = min(len(filenames), 60)
results = []

with concurrent.futures.ThreadPoolExecutor(max_workers=60) as executor:
    futures = [executor.submit(plot_distance, filename, times_ball) for filename in filenames]
    concurrent.futures.wait(futures)
    for future, filename in zip(futures, filenames):
        try:
            results.append(future.result())
        except Exception as e:
            print(f"An error occurred while processing {filename}: {e}")


means = []
stdlist = []

for i, res in enumerate(results):
    if i == 0 or i == 4 or i == 9:
        aux = "tau = " + str(x[i]) + "s"
        plt.scatter(times, res, s=5, label=aux)
    means.append(np.mean(res))
    stdlist.append(np.std(res)/math.sqrt(len(res)))

plt.ylabel("Distancia [m]")
plt.xlabel("Tiempo [s]")
plt.legend()
plt.grid()
plt.xlim(0)
plt.ylim(0)
plt.show()

plt.errorbar(x, means, yerr=stdlist, fmt='o', capsize=5)
plt.ylabel("Distancia Promedio [m]")
plt.xlabel("Tau [s]")
plt.grid()
plt.show()
