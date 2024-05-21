import math
import parser
import configparser
import matplotlib.pyplot as plt
import numpy as np
import concurrent.futures

config = configparser.ConfigParser()
config.read('../configs/app.config')

start_time = 24*60*int(config['DEFAULT']['start_time'])
end_time = 24*60*int(config['DEFAULT']['end_time'])

times_home, times_away, times_ball, times = parser.parse_players()
ball_plot = times_ball[start_time:end_time]
time_plot = times[start_time:end_time]

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
x = np.arange(desired, 13.1, step)

filenames = [config['DEFAULT']['python'] + config['DEFAULT']['output'] + "_v" + str(i) + ".csv" for i in range(0, 130)]

max_threads = min(len(filenames), 60)
results = []

with concurrent.futures.ThreadPoolExecutor(max_workers=60) as executor:
    futures = [executor.submit(plot_distance, filename, ball_plot) for filename in filenames]
    concurrent.futures.wait(futures)
    for future, filename in zip(futures, filenames):
        try:
            results.append(future.result())
        except Exception as e:
            print(f"An error occurred while processing {filename}: {e}")


means = []
stdlist = []

for res in results:
    plt.plot(time_plot, res)
    means.append(np.mean(res))
    stdlist.append(np.std(res))

plt.show()

plt.errorbar(x, means, yerr=stdlist, fmt='o', capsize=5)
plt.show()



