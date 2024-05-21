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

heat_map = np.zeros((105, 68))
heat_map_loco = np.zeros((105, 68))

for home, away, loco in zip(times_home, times_away, times_loco):
    for p in home:
        x = int(p[0])
        y = int(p[1])
        if 0 <= x < 105 and 0 <= y < 68:
            heat_map[x][y] += 1

    for p in away:
        x = int(p[0])
        y = int(p[1])
        if 0 <= x < 105 and 0 <= y < 68:
            heat_map[x][y] += 1

    x = int(loco[0])
    y = int(loco[1])
    if 0 <= x < 105 and 0 <= y < 68:
        heat_map[x][y] += 1
        heat_map_loco[x][y] += 1

sns.heatmap(heat_map, cmap='RdYlGn_r', annot=False)
plt.show()

sns.heatmap(heat_map_loco, cmap='RdYlGn_r', annot=False)
plt.show()
