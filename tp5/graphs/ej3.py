import numpy as np
import configparser
import parser
import math
import matplotlib.pyplot as plt

config = configparser.ConfigParser()
config.read('../configs/app.config')

start_time = 24*60*int(config['DEFAULT']['start_time'])
end_time = 24*60*int(config['DEFAULT']['end_time'])

times_home, times_away, times_ball, times = parser.parse_players()
times_home = times_home[start_time: end_time]
times_away = times_away[start_time: end_time]
times_loco = parser.parse_loco(config['DEFAULT']['python'] + config['DEFAULT']['output'] + ".csv")

max_limit = 8

def format_num(num):
    # num *= 0.5
    # return int(num)
    return float(format(num, ".1f"))

def calculate_velocities(team):
    velocities = [[] for _ in range(len(team[0]))]
    dt = 1/24
    for i in range(1, len(team)):
        for j in range(len(team[i])):
            aux_x = (team[i][j][0] - team[i-1][j][0])/dt
            aux_y = (team[i][j][1] - team[i-1][j][1])/dt
            aux = format_num(math.sqrt(math.pow(aux_x, 2) + math.pow(aux_y, 2)))
            if 0 <= aux < max_limit:
                velocities[j].append(format_num(aux))

    return velocities

def calculate_vel_loco(loco):
    velocities = []
    for i in range(1, len(loco)):
        aux = math.sqrt(math.pow(loco[i][2], 2) + math.pow(loco[i][3], 2))
        velocities.append(format_num(aux))

    return velocities

def calculate_pdf(player):
    numbers, counts = np.unique(player, return_counts=True)
    total_elements = len(player)
    probabilities = counts / total_elements
    # Plot the PDF
    plt.plot(numbers, probabilities, marker="o")


calculate_pdf(calculate_vel_loco(times_loco))
calculate_pdf(calculate_velocities(times_home)[0])
calculate_pdf(calculate_velocities(times_away)[4])
calculate_pdf(calculate_velocities(times_away)[8])
plt.xlabel('Velocidad [m/s]')
plt.ylabel('Probabilidad')
# plt.title('Probability Distribution Function (PDF)')
plt.show()
