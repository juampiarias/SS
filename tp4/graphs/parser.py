import csv

def parse_csv(filename):
    times = []

    with (open(filename, 'r') as file):
        reader = csv.reader(file, delimiter=',')
        coordinates = []

        for row in reader:
            if row:
                if row[0].startswith('D'):
                    if coordinates:
                        times.append(coordinates)
                        coordinates = []

                else:
                    idd = (row[0])
                    rx = float(row[1])
                    ry = float(row[2])
                    vx = float(row[3])
                    vy = float(row[4])
                    coordinates.append([idd, rx, ry, vx, vy])

        if coordinates:
            times.append(coordinates)

    return times
