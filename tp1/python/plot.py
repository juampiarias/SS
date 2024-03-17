import matplotlib.pyplot as plt
import csv
import pandas as pd

if __name__ == "__main__":
    with open("../out.csv", "r") as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=';')
        particles = []
        neighbors = []
        for row in csv_reader:
            particles.append(int(row[0]))
            neighbors.append([int(x) for x in row[1].split(',') if x.strip()])

    # Read the CSV file into a DataFrame
    df = pd.read_csv('../particles.csv', delimiter=';')
    # Extracting x, y coordinates, and radii from the DataFrame
    ids = df['particle_id']
    x_coords = df['x_coordinate']
    y_coords = df['y_coordinate']
    radii = df['particle_radius']

    print(particles[0])
    print(neighbors[0])

    # Plotting
    plt.figure(figsize=(8, 6))

    for i, x, y, r in zip(ids, x_coords, y_coords, radii):
        if i == particles[0] or i in neighbors[0]:
            circle = plt.Circle((x, y), r, color='r', fill=True)
        else:
            circle = plt.Circle((x, y), r, color='b', fill=True)
        if i == particles[0]:
            plt.gca().add_artist(plt.Circle((x, y), r+0.9, color='r', fill=False))
        plt.gca().add_artist(circle)

    plt.scatter(x_coords, y_coords, color='r', label='Particles')

    plt.xlabel('X Coordinate')
    plt.ylabel('Y Coordinate')
    plt.title('Particle Plot')
    plt.axis('equal')  # Equal aspect ratio ensures circles are circular
    plt.legend()
    plt.grid(True)
    plt.show()