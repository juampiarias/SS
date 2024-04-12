import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
from parser import particles, times
import parser
import math

delta_t = 0.05
time = 0.05
desired_count = 60

fps = []
aux = []

for i, t in enumerate(times):
    if t >= time:
        time += delta_t
        step_size = len(aux) / desired_count
        selected_numbers = [aux[math.floor(i * step_size)] for i in range(desired_count)]
        fps += selected_numbers
        aux = []

    aux.append(i)

if len(aux) > desired_count:
    step_size = len(aux) / desired_count
    selected_numbers = [aux[math.floor(i * step_size)] for i in range(desired_count)]
    fps += selected_numbers
else:
    fps += aux

patches = []
patches.append(plt.Circle(xy=(particles[0][0][1], particles[0][0][2]), radius=particles[0][0][6], color='y', animated=True))
for i in range(1, len(particles[0])):
    patches.append(plt.Circle(xy=(particles[0][i][1], particles[0][i][2]), radius=particles[0][i][6], animated=True))

fig = plt.figure()
ax = plt.axes(xlim=(0, parser.side), ylim=(0, parser.side))

#
def init():
    for p in patches:
        ax.add_patch(p)
    return patches

#
def animate(i):
    for patch, p in zip(patches, particles[fps[i]]):
        patch.set_center((p[1],p[2]))
    return patches

#
numframes = len(fps)
anim = FuncAnimation(fig, animate, init_func=init,
                     frames=numframes, interval=1, blit=True)

plt.show()

writer_video = animation.FFMpegWriter(fps=60)
# anim.save('balls.mp4', writer=writer_video)
