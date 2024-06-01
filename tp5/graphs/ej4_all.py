import numpy as np
import configparser
import parser
import matplotlib.pyplot as plt

config = configparser.ConfigParser()
config.read('../configs/app.config')

size_x = 105
size_y = 68

cell_x = 21
cell_y = 17

# FIRST HALF
start_time = 25*60*0
end_time = 25*60*45

times_home_1f, times_away_1f, times_ball_1f, times_1f = parser.parse_players(start_time, end_time)
times_loco_1f = parser.parse_loco(config['DEFAULT']['python'] + "simulation_first_half" + ".csv")

heat_map_1f = np.zeros((cell_y, cell_x))
heat_map_loco_1f = np.zeros((cell_y, cell_x))

heat_map_home_1f = [np.zeros((cell_y, cell_x)) for _ in range(len(times_home_1f[0]))]
heat_map_away_1f = [np.zeros((cell_y, cell_x)) for _ in range(len(times_away_1f[0]))]

for home, away, loco in zip(times_home_1f, times_away_1f, times_loco_1f):
    for i, p in enumerate(home):
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map_1f[y][x] += 1
            heat_map_home_1f[i][y][x] += 1

    for i, p in enumerate(away):
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map_1f[y][x] += 1
            heat_map_away_1f[i][y][x] += 1

    x = int(loco[0]/(size_x/cell_x))
    y = int(loco[1]/(size_y/cell_y))
    if 0 <= x < cell_x and 0 <= y < cell_y:
        heat_map_1f[y][x] += 1
        heat_map_loco_1f[y][x] += 1

# SECOND HALF
start_time = 25*60*45
end_time = 25*60*99

times_home_2f, times_away_2f, times_ball_2f, times_2f = parser.parse_players(start_time, end_time)
times_loco_2f = parser.parse_loco(config['DEFAULT']['python'] + "simulation_second_half" + ".csv")

heat_map_2f = np.zeros((cell_y, cell_x))
heat_map_loco_2f = np.zeros((cell_y, cell_x))

heat_map_home_2f = [np.zeros((cell_y, cell_x)) for _ in range(len(times_home_2f[0]))]
heat_map_away_2f = [np.zeros((cell_y, cell_x)) for _ in range(len(times_away_2f[0]))]

for home, away, loco in zip(times_home_2f, times_away_2f, times_loco_2f):
    for i, p in enumerate(home):
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map_2f[y][x] += 1
            heat_map_home_2f[i][y][x] += 1

    for i, p in enumerate(away):
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map_2f[y][x] += 1
            heat_map_away_2f[i][y][x] += 1

    x = int(loco[0]/(size_x/cell_x))
    y = int(loco[1]/(size_y/cell_y))
    if 0 <= x < cell_x and 0 <= y < cell_y:
        heat_map_2f[y][x] += 1
        heat_map_loco_2f[y][x] += 1

fig, ax = plt.subplots(1, 2, subplot_kw={'xticks': [], 'yticks': []})
ax[0].imshow(heat_map_1f, cmap='RdYlGn_r', interpolation='spline16')
ax[1].imshow(heat_map_2f, cmap='RdYlGn_r', interpolation='spline16')
plt.show()

fig, ax = plt.subplots(1, 2, subplot_kw={'xticks': [], 'yticks': []})
ax[0].imshow(heat_map_loco_1f, cmap='RdYlGn_r', interpolation='spline16')
ax[1].imshow(heat_map_loco_2f, cmap='RdYlGn_r', interpolation='spline16')
plt.show()

fig, ax = plt.subplots(1, 2, subplot_kw={'xticks': [], 'yticks': []})
ax[0].imshow(heat_map_home_1f[0], cmap='RdYlGn_r', interpolation='spline16')
ax[1].imshow(heat_map_home_2f[0], cmap='RdYlGn_r', interpolation='spline16')
plt.show()

fig, ax = plt.subplots(1, 2, subplot_kw={'xticks': [], 'yticks': []})
ax[0].imshow(heat_map_home_1f[4], cmap='RdYlGn_r', interpolation='spline16')
ax[1].imshow(heat_map_home_2f[4], cmap='RdYlGn_r', interpolation='spline16')
plt.show()

fig, ax = plt.subplots(1, 2, subplot_kw={'xticks': [], 'yticks': []})
ax[0].imshow(heat_map_home_1f[8], cmap='RdYlGn_r', interpolation='spline16')
ax[1].imshow(heat_map_home_2f[8], cmap='RdYlGn_r', interpolation='spline16')
plt.show()
