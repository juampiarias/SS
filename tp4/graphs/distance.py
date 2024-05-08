import configparser
import math

import parser
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
import concurrent.futures

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])


def find_minimum_distance(filename):
    days = parser.parse_csv(filename)
    dist = 1e10
    for day in days:
        aux = math.sqrt(math.pow(day[2][1] - day[3][1], 2) + math.pow(day[2][2] - day[3][2], 2))
        if aux < dist:
            dist = aux
    return dist

start = 0
end = 365
filenames = [config['DEFAULT']['python'] + "simulation" + str(i) + ".csv" for i in range(start, end)]



# Max number of threads to use
max_threads = min(len(filenames), 5)  # Let's limit it to a maximum of 5 threads
results = []

# Create a thread pool executor
with concurrent.futures.ThreadPoolExecutor(max_workers=max_threads) as executor:
    # Submit a task for each file to the executor
    futures = [executor.submit(find_minimum_distance, filename) for filename in filenames]

    # Wait for all tasks to complete
    concurrent.futures.wait(futures)

    # Get the results
    for future, filename in zip(futures, filenames):
        try:
            results.append(future.result())
        except Exception as e:
            print(f"An error occurred while processing {filename}: {e}")


plt.scatter([i for i in range(start, end)], results)
plt.show()