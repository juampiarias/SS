import configparser
import parser
import matplotlib.pyplot as plt
from matplotlib import animation


config = configparser.ConfigParser()
config.read('../configs/app.config')

fans = parser.parse_person(config['DEFAULT']['python'] + config['DEFAULT']['output'] + "_fan" + str(0) + ".csv")
guards = parser.parse_person(config['DEFAULT']['python'] + config['DEFAULT']['output'] + "_guard" + str(0) + ".csv")

patches_guard = []
patches_fan = []
for guard in guards[0]:
    patches_guard.append(plt.Circle(xy=(guard[1], guard[2]), radius=0.25, animated=True, color='r'))

for fan in fans[0]:
    patches_fan.append(plt.Circle(xy=(fan[1], fan[2]), radius=0.25, animated=True, color='b'))

fig, ax = plt.subplots(figsize=(12, 7))
ax.set_xlim(-50, 50)
ax.set_ylim(-50, 50)

def init():
    for p in patches_guard:
        ax.add_patch(p)
    for p in patches_fan:
        ax.add_patch(p)

    return patches_guard+patches_fan

def animate(i):
    for patch, guard in zip(patches_guard, guards[i]):
        patch.set_center((guard[1], guard[2]))
    for patch, fan in zip(patches_fan, fans[i]):
        patch.set_center((fan[1], fan[2]))

    return patches_guard+patches_fan

numframes = len(fans)
anim = animation.FuncAnimation(fig, animate, init_func=init, frames=numframes, interval=1, blit=True)

plt.show()

