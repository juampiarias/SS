import numpy as np
import configparser
import parser
import matplotlib.pyplot as plt
import seaborn as sns

config = configparser.ConfigParser()
config.read('../configs/app.config')

start_time = 24*60*int(config['DEFAULT']['start_time'])
end_time = 24*60*int(config['DEFAULT']['end_time'])

times_home, times_away, times_ball, times = parser.parse_players()
times_home = times_home[start_time: end_time]
times_away = times_away[start_time: end_time]
times_loco = parser.parse_loco(config['DEFAULT']['python'] + config['DEFAULT']['output'] + ".csv")

size_x = 105
size_y = 68

cell_x = 105
cell_y = 68

heat_map = np.zeros((cell_x, cell_y))
heat_map_loco = np.zeros((cell_x, cell_y))

for home, away, loco in zip(times_home, times_away, times_loco):
    for p in home:
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map[x][y] += 1

    for p in away:
        x = int(p[0]/(size_x/cell_x))
        y = int(p[1]/(size_y/cell_y))
        if 0 <= x < cell_x and 0 <= y < cell_y:
            heat_map[x][y] += 1

    x = int(loco[0]/(size_x/cell_x))
    y = int(loco[1]/(size_y/cell_y))
    if 0 <= x < cell_x and 0 <= y < cell_y:
        heat_map[x][y] += 1
        heat_map_loco[x][y] += 1

plt.figure(figsize=(105, 68))
ax = sns.heatmap(heat_map, cmap='RdYlGn_r', annot=False)
ax.tick_params(left=False, bottom=False)
plt.show()

plt.figure(figsize=(105, 68))
ax = sns.heatmap(heat_map_loco, cmap='RdYlGn_r', annot=False)
ax.tick_params(left=False, bottom=False)
plt.show()
