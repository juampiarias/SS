import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib import animation
from datetime import datetime
import parser  # Assuming parser is a module in your project
import configparser

# Field dimensions
field_length = 105
field_width = 68

# Create football field
def create_field():
    fig, ax = plt.subplots(figsize=(12, 7))

    # Extend dimensions for green field area
    extended_length = field_length + 2  # extend by 1 meter on each side
    extended_width = field_width + 20  # extend by 1 meter on each side

    # Draw the extended green field area
    extended_field = patches.Rectangle((-1, -1), extended_length, extended_width, linewidth=0, edgecolor='none', facecolor='green')
    ax.add_patch(extended_field)

    # Draw the white sidelines (representing the actual field boundaries)
    sidelines = patches.Rectangle((0, 0), field_length, field_width, linewidth=2, edgecolor='white', facecolor='none')
    ax.add_patch(sidelines)

    # Draw the center line
    center_line = patches.ConnectionPatch((field_length / 2, 0), (field_length / 2, field_width), "data", "data", edgecolor='white', linewidth=2)
    ax.add_patch(center_line)

    # Draw the center circle
    center_circle = patches.Circle((field_length / 2, field_width / 2), 9.15, edgecolor='white', facecolor='none', linewidth=2)
    ax.add_patch(center_circle)

    # Draw the center spot
    center_spot = patches.Circle((field_length / 2, field_width / 2), 0.2, edgecolor='white', facecolor='white')
    ax.add_patch(center_spot)

    # Draw the penalty areas
    penalty_area_length = 16.5
    penalty_area_width = 40.3

    # Left penalty area
    left_penalty_area = patches.Rectangle((0, (field_width - penalty_area_width) / 2), penalty_area_length, penalty_area_width, edgecolor='white', facecolor='none', linewidth=2)
    ax.add_patch(left_penalty_area)

    # Right penalty area
    right_penalty_area = patches.Rectangle((field_length - penalty_area_length, (field_width - penalty_area_width) / 2), penalty_area_length, penalty_area_width, edgecolor='white', facecolor='none', linewidth=2)
    ax.add_patch(right_penalty_area)

    # Draw the goal areas
    goal_area_length = 5.5
    goal_area_width = 18.32

    # Left goal area
    left_goal_area = patches.Rectangle((0, (field_width - goal_area_width) / 2), goal_area_length, goal_area_width, edgecolor='white', facecolor='none', linewidth=2)
    ax.add_patch(left_goal_area)

    # Right goal area
    right_goal_area = patches.Rectangle((field_length - goal_area_length, (field_width - goal_area_width) / 2), goal_area_length, goal_area_width, edgecolor='white', facecolor='none', linewidth=2)
    ax.add_patch(right_goal_area)

    # Draw the penalty spots
    penalty_spot_distance = 11

    # Left penalty spot
    left_penalty_spot = patches.Circle((penalty_spot_distance, field_width / 2), 0.2, edgecolor='white', facecolor='white')
    ax.add_patch(left_penalty_spot)

    # Right penalty spot
    right_penalty_spot = patches.Circle((field_length - penalty_spot_distance, field_width / 2), 0.2, edgecolor='white', facecolor='white')
    ax.add_patch(right_penalty_spot)

    # Draw the substitute benches
    bench_width = 2
    bench_length = 10

    # Both benches on the same side (bottom side of the plot)
    away_bench = patches.Rectangle((field_length / 2 - bench_length - 2, field_width + 3), bench_length, bench_width, linewidth=2, edgecolor='red', facecolor='red')
    home_bench = patches.Rectangle((field_length / 2 + 2, field_width + 3), bench_length, bench_width, linewidth=2, edgecolor='blue', facecolor='blue')

    ax.add_patch(home_bench)
    ax.add_patch(away_bench)

    # Set the limits and invert y-axis
    ax.set_xlim(-5, field_length + 5)
    ax.set_ylim(field_width + 10, -10)  # Invert y-axis
    ax.set_aspect('equal', adjustable='box')

    # Remove axis labels and ticks
    ax.axis('off')

    return fig, ax

config = configparser.ConfigParser()
config.read('../configs/app.config')

start_time = 25*60*int(config['DEFAULT']['start_time'])
end_time = 25*60*int(config['DEFAULT']['end_time'])

times_home, times_away, times_ball, times = parser.parse_players(start_time, end_time)
times_loco = parser.parse_loco(config['DEFAULT']['python'] + config['DEFAULT']['output'] + ".csv")

# Create field
fig, ax = create_field()

patches_home = []
patches_away = []
patches_ball = []
patches_loco = []

home_color = 'b'
away_color = 'r'
ball_color = 'white'
loco_color = 'k'

game_time_text = ax.text(field_length, field_width+5, '',
                         color='white', ha='right', va='bottom', fontsize=12)

for player in times_home[0]:
    patches_home.append(plt.Circle(xy=(player[0], player[1]), radius=1, animated=True, color=home_color))

for player in times_away[0]:
    patches_away.append(plt.Circle(xy=(player[0], player[1]), radius=1, animated=True, color=away_color))

patches_ball.append(plt.Circle(xy=(times_ball[0][0], times_ball[0][1]), radius=0.8, animated=True, color=ball_color))
patches_loco.append(plt.Circle(xy=(times_loco[0][0], times_loco[0][1]), radius=1, animated=True, color=loco_color))


def format_time(time_seconds):
    minutes = int(time_seconds // 60)
    seconds = int(time_seconds % 60)
    return f"{minutes:02d}:{seconds:02d}"


def init():
    for p in patches_home:
        ax.add_patch(p)
    for p in patches_away:
        ax.add_patch(p)
    ax.add_patch(patches_ball[0])
    ax.add_patch(patches_loco[0])

    game_time_text.set_text(format_time(times[0]))

    return patches_home+patches_away+patches_ball+patches_loco+[game_time_text]

speed = 4
#
def animate(i):
    i = i*speed
    for patch, p in zip(patches_home, times_home[i]):
        patch.set_center((p[0], p[1]))
    for patch, p in zip(patches_away, times_away[i]):
        patch.set_center((p[0], p[1]))
    patches_ball[0].set_center((times_ball[i][0], times_ball[i][1]))
    patches_loco[0].set_center((times_loco[i][0], times_loco[i][1]))

    game_time_text.set_text(format_time(times[i]))

    return patches_home+patches_away+patches_ball+patches_loco+[game_time_text]


numframes = int(len(times_loco) / speed)
anim = animation.FuncAnimation(fig, animate, init_func=init, frames=numframes, interval=1, blit=True)

# Show plot
plt.show()

writer_video = animation.FFMpegWriter(fps=60)
# anim.save('loco5.mp4', writer=writer_video)
