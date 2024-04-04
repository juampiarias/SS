import matplotlib.pyplot as plt
import matplotlib.animation as animation
import numpy as np
from parser import particles

# Create a figure and axis for the plot
fig, ax = plt.subplots()


# Function to update the animation at each frame
def update(frame):
    ax.clear()
    x = [point[0] for point in particles[frame]]
    y = [point[1] for point in particles[frame]]
    cos = np.array([point[2] for point in particles[frame]])
    sin = np.array([point[3] for point in particles[frame]])
    # Defining color
    n = -2
    color = np.sqrt(((cos-n)/2)*2 + ((sin-n)/2)*2)

    ax.quiver(x, y, cos, sin, color, angles='xy', scale_units='xy', scale=1)
    ax.set_xlim(0, 5)
    ax.set_ylim(0, 5)


writers = animation.writers
writer = writers['ffmpeg']()

# Create the animation
ani = animation.FuncAnimation(fig, update, frames=len(particles), interval=25)


# Display the animation
plt.title('Movement of the Particle')
plt.xlabel('X-axis')
plt.ylabel('Y-axis')
plt.show()

# ani.save('birds.mp4', writer=writer)
