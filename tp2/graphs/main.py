import configparser
import csv
import math

import matplotlib.pyplot as plt
import numpy as np
from numpy import double
from matplotlib.animation import FuncAnimation

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])

# file a leer
filename = config['DEFAULT']['python'] + config['DEFAULT']['output']

n = config['DEFAULT']['n']
side = config['DEFAULT']['l']
v = config['DEFAULT']['v']
m = config['DEFAULT']['m']
rc = config['DEFAULT']['rc']
iteration = config['DEFAULT']['iter']


def parse_csv(filename):
    coordinates_list = []

    with (open(filename, 'r') as file):
        reader = csv.reader(file, delimiter=';')
        coordinates = []
        for row in reader:
            if row:  # Check if row is not empty
                if row[0].startswith('t'):  # If row starts with 't', it's a timestamp
                    if coordinates:  # If numbers list is not empty, append it to the main list
                        coordinates_list.append(coordinates)
                        coordinates = []  # Reset coordinates for the next set
                else:
                    x = float(row[1])
                    y = float(row[2])
                    cos = math.cos(math.radians(float(row[3]))) * float(row[5])
                    sin = math.sin(math.radians(float(row[3]))) * float(row[5])
                    coordinates.append([x, y, cos, sin])

        if coordinates:  # Append the last set of coordinates after the loop
            coordinates_list.append(coordinates)

    return coordinates_list

particles = parse_csv(filename)