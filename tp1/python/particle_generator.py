import csv
import random

# Number of particles
num_particles = 300

# Size of the space
space_size = 20

# Open CSV file for writing
with open('300.csv', 'w', newline='') as csvfile:
    fieldnames = ['particle_id', 'x_coordinate', 'y_coordinate', 'particle_radius']
    writer = csv.DictWriter(csvfile, fieldnames=fieldnames, delimiter=';')
    writer.writeheader()

    # Generate particles
    for particle_id in range(1, num_particles + 1):
        x = random.uniform(0, space_size)
        y = random.uniform(0, space_size)

        # Write to CSV
        writer.writerow({'particle_id': particle_id,
                         'x_coordinate': x,
                         'y_coordinate': y,
                         'particle_radius': 0.25})
