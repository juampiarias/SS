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
# particle = [[0,0],[0,1],[0,2],[0,3]]

# Create a figure and axis for the plot
fig, ax = plt.subplots()

# Initialize an empty line object (the particle will be represented as a line)
line, = ax.plot([], [], marker='o', linestyle='None')

# Set the axis limits
ax.set_xlim(0, 5)
ax.set_ylim(0, 5)

# Function to initialize the animation
def init():
    line.set_data([], [])
    return line,

# Function to update the animation at each frame
def update(frame):
    # print(frame)
    # x,y = zip(*particle[frame])
    # y = [particle[frame][1]]
    x = [point[0] for point in particles[frame]]
    y = [point[1] for point in particles[frame]]
    # print(particle[:frame+1])
    # print(y)
    # x = particle[:frame][0]
    # y = particle[:frame][1]
    line.set_data(x, y)
    return line,

# Create the animation
ani = animation.FuncAnimation(fig, update, frames=len(particles), init_func=init, blit=True)

# Display the animation
plt.title('Movement of the Particle')
plt.xlabel('X-axis')
plt.ylabel('Y-axis')
# plt.grid(True)
plt.show()
