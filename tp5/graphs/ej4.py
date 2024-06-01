import numpy as np
import configparser
import parser
import matplotlib.pyplot as plt

config = configparser.ConfigParser()
config.read('../configs/app.config')

start_time = 25*60*int(config['DEFAULT']['start_time'])
end_time = 25*60*int(config['DEFAULT']['end_time'])

times_home, times_away, times_ball, times = parser.parse_players(start_time, end_time)
times_loco = parser.parse_loco(config['DEFAULT']['python'] + config['DEFAULT']['output'] + ".csv")

size_x = 105
size_y = 68

cell_x = 21
cell_y = 17

heat_map = np.zeros((cell_y, cell_x))
heat_map_loco = np.zeros((cell_y, cell_x))

heat_map_home = [np.zeros((cell_y, cell_x)) for _ in range(len(times_home[0]))]
heat_map_away = [np.zeros((cell_y, cell_x)) for _ in range(len(times_away[0]))]

for home, away, loco in zip(times_home, times_away, times_loco):
    for i, p in enumerate(home):
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map[y][x] += 1
            heat_map_home[i][y][x] += 1

    for i, p in enumerate(away):
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map[y][x] += 1
            heat_map_away[i][y][x] += 1

    x = int(loco[0]/(size_x/cell_x))
    y = int(loco[1]/(size_y/cell_y))
    if 0 <= x < cell_x and 0 <= y < cell_y:
        heat_map[y][x] += 1
        heat_map_loco[y][x] += 1

ax = plt.imshow(heat_map, cmap='RdYlGn_r', interpolation='spline16')
ax.axes.xaxis.set_ticks([])
ax.axes.yaxis.set_ticks([])
plt.show()

ax = plt.imshow(heat_map_loco, cmap='RdYlGn_r', interpolation='spline16')
ax.axes.xaxis.set_ticks([])
ax.axes.yaxis.set_ticks([])
plt.show()

ax = plt.imshow(heat_map_home[0], cmap='RdYlGn_r', interpolation='spline16')
ax.axes.xaxis.set_ticks([])
ax.axes.yaxis.set_ticks([])
plt.show()

ax = plt.imshow(heat_map_home[4], cmap='RdYlGn_r', interpolation='spline16')
ax.axes.xaxis.set_ticks([])
ax.axes.yaxis.set_ticks([])
plt.show()

ax = plt.imshow(heat_map_home[8], cmap='RdYlGn_r', interpolation='spline16')
ax.axes.xaxis.set_ticks([])
ax.axes.yaxis.set_ticks([])
plt.show()
