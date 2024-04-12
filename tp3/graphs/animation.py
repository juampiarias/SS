import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from parser import particles
import parser

patches = []
for p in particles[0]:
    patches.append(plt.Circle(xy=(p[1],p[2]), radius=p[6], animated=True))

center = [plt.Circle(xy=(parser.side/2, parser.side/2), radius=parser.centralR, color='y')]

fig = plt.figure()
ax = plt.axes(xlim=(0, parser.side), ylim=(0, parser.side))

#
def init():
    for p in patches:
        ax.add_patch(p)
    ax.add_patch(center[0])
    return patches + center

#
def animate(i):
    for patch, p in zip(patches, particles[i]):
        patch.set_center((p[1],p[2]))
    return patches + center

#
numframes = len(particles)
anim = FuncAnimation(fig, animate, init_func=init,
                     frames=numframes, interval=1, blit=True)

plt.show()