import configparser
import csv

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])

# file a leer
filename = config['DEFAULT']['python'] + config['DEFAULT']['output']

n = int(config['DEFAULT']['n'])
side = float(config['DEFAULT']['l'])
centralR = float(config['DEFAULT']['centralR'])
r = float(config['DEFAULT']['r'])
v = float(config['DEFAULT']['v'])
mass = float(config['DEFAULT']['mass'])
iteration = int(config['DEFAULT']['iter'])


def parse_csv(file_name):
    times_list = []
    times = []
    collisions = []

    with(open(file_name, 'r') as file):
        reader = csv.reader(file, delimiter=';')
        coordinates = []

        for row in reader:
            if row:
                if row[0].startswith('t'):
                    numbers = row[0].split()
                    new_s = numbers[0][1:]
                    times.append(float(new_s))
                    if len(numbers) > 1:
                        collisions.append([int(numbers[1]), int(numbers[2])])
                    # index_of_space = row[0].find(' ')
                    # new_s = str(row[0][1:index_of_space])
                    # times.append(float(new_s))
                    if coordinates:
                        times_list.append(coordinates)
                        coordinates = []
                else:
                    idd = int(row[0])
                    rx = float(row[1])
                    ry = float(row[2])
                    vx = float(row[3])
                    vy = float(row[4])
                    m = float(row[5])
                    radius = float(row[6])
                    coordinates.append([idd, rx, ry, vx, vy, m, radius])

        if coordinates:
            times_list.append(coordinates)

    return times_list, times, collisions


particles, times, collisions = parse_csv(filename)
