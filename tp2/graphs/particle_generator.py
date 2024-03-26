import csv
import random

# Number of particles
num_particles = 1000

# Size of the space
space_size = 5

# Open CSV file for writing
with open('../ios/input1000.csv', 'w') as csvfile:
    fieldnames = ['particle_id', 'x_coordinate', 'y_coordinate', 'angle']
    writer = csv.DictWriter(csvfile, fieldnames=fieldnames, delimiter=';')
    writer.writeheader()

    # Generate particles
    for particle_id in range(1, num_particles + 1):
        x = random.uniform(0, space_size)
        y = random.uniform(0, space_size)
        angle = random.uniform(0, 360)

        # Write to CSV
        writer.writerow({'particle_id': particle_id,
                         'x_coordinate': x,
                         'y_coordinate': y,
                         'angle': angle,
                         })
