import configparser
import parser
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])

# file a leer
filename = config['DEFAULT']['python'] + config['DEFAULT']['output']

particles = parser.parse_csv(filename)

side = 1e9/2
radius = 1e7

fig = plt.figure()
ax = plt.axes()
ax = plt.axes(xlim=(-side, side), ylim=(-side, side))

patches = []

for i in range(len(particles[0])):
    patches.append(plt.Circle(xy=(particles[0][i][1], particles[0][i][2]), radius=radius, animated=True))


def init():
    for p in patches:
        ax.add_patch(p)
    return patches

#
def animate(i):
    for patch, p in zip(patches, particles[i]):
        patch.set_center((p[1], p[2]))
    return patches

#
numframes = len(particles)
anim = FuncAnimation(fig, animate, init_func=init,
                     frames=numframes, interval=1, blit=True)

plt.show()

writer_video = animation.FFMpegWriter(fps=60)
# anim.save('planets.mp4', writer=writer_video)
