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
    for patch, p in zip(patches, particles[fps[i]]):
        patch.set_center((p[1],p[2]))
    return patches + center

#
numframes = len(fps)
anim = FuncAnimation(fig, animate, init_func=init,
                     frames=numframes, interval=1, blit=True)

plt.show()

writer_video = animation.FFMpegWriter(fps=60)
# anim.save('balls.mp4', writer=writer_video)
