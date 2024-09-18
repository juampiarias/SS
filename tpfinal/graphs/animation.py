import configparser
import parser
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib import animation


config = configparser.ConfigParser()
config.read('../configs/app.config')

fans = parser.parse_person(config['DEFAULT']['python'] + config['DEFAULT']['output'] + "_fan" + config['DEFAULT']['guardStart'] + ".csv")
guards = parser.parse_person(config['DEFAULT']['python'] + config['DEFAULT']['output'] + "_guard" + config['DEFAULT']['guardStart'] + ".csv")

patches_guard = []
patches_fan = []
for guard in guards[0]:
    patches_guard.append(plt.Circle(xy=(guard[1], guard[2]), radius=0.25, animated=True, color='r'))

for fan in fans[0]:
    patches_fan.append(plt.Circle(xy=(fan[1], fan[2]), radius=0.25, animated=True, color='b'))

fig, ax = plt.subplots()
ax.add_patch(patches.Circle((0,0), radius=0.25, color='k'))
circle_radius = float(config['DEFAULT']['circleRadius'])
circle_extension = float(config['DEFAULT']['circleExtension'])
ax.add_patch(patches.Circle((0,0), radius=circle_radius, edgecolor='k', facecolor='none', linestyle="-"))

ax.set_xlim(-circle_radius-circle_extension, circle_radius+circle_extension)
ax.set_ylim(-circle_radius-circle_extension, circle_radius+circle_extension)

def init():
    for p in patches_guard:
        ax.add_patch(p)
    for p in patches_fan:
        ax.add_patch(p)

    return patches_guard+patches_fan

speed = 10
def animate(i):
    i = i*speed
    for patch, guard in zip(patches_guard, guards[i]):
        patch.set_center((guard[1], guard[2]))
    for patch, fan in zip(patches_fan, fans[i]):
        patch.set_center((fan[1], fan[2]))

    return patches_guard+patches_fan

numframes = int(len(fans) / speed)
anim = animation.FuncAnimation(fig, animate, init_func=init, frames=numframes, interval=1, blit=True)

plt.show()

writer_video = animation.FFMpegWriter(fps=60)
anim.save('catch17.mp4', writer=writer_video)