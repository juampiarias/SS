import matplotlib.pyplot as plt
import matplotlib.animation as animation
from main import particles

# Define the coordinates of the particle
# particle = [
#     [[0,0],[0,0]],
#     [[0,1],[1,0]],
#     [[0,2],[2,0]],
#     [[0,3],[3,0]],
#     [[0,4],[4,0]],
# ]
particle = [[0, 0, 1, 0], [1, 1, 1, 1], [1, 2, 1, 1], [1, 3, 1, 1]]

# Create a figure and axis for the plot
fig, ax = plt.subplots()

# Initialize an empty line object (the particle will be represented as a line)
# qax = ax.quiver([], [], [], linestyle='None')

# Set the axis limits
# ax.set_xlim(0, 5)
# ax.set_ylim(0, 5)

# Function to initialize the animation
# def init():
    # qax.set_data([], [], [], [])
    # return qax


# Function to update the animation at each frame
def update(frame):
    ax.clear()
    # x = [particle[frame][0]]
    # y = [particle[frame][1]]
    # cos = [particle[frame][2]]
    # sin = [particle[frame][3]]
    # print(x, y, cos, sin)
    x = [point[0] for point in particles[frame]]
    y = [point[1] for point in particles[frame]]
    cos = [point[2] for point in particles[frame]]
    sin = [point[3] for point in particles[frame]]
    ax.quiver(x, y, cos, sin, angles='xy', scale_units='xy', scale=1)
    ax.set_xlim(0, 5)
    ax.set_ylim(0, 5)


writers = animation.writers
writer = writers['ffmpeg']()

# Create the animation
ani = animation.FuncAnimation(fig, update, frames=len(particles), interval=50)


# Display the animation
plt.title('Movement of the Particle')
plt.xlabel('X-axis')
plt.ylabel('Y-axis')
# plt.grid(True)
plt.show()

ani.save('birds.mp4', writer=writer)
