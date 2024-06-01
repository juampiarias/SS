import csv

x_side = 105
y_side = 68

def parse_players(start_time, end_time):
    times_home = []
    times_away = []
    times_ball = []
    times = []

    with(open("../ios/TrackingData_Local.csv", "r") as file_home):
        with(open("../ios/TrackingData_Visitante.csv") as file_away):

            reader_home = csv.reader(file_home, delimiter=',')
            reader_away = csv.reader(file_away, delimiter=',')
            coordinates_home = []
            coordinates_away = []

            for i in range(3):
                next(reader_home)
                next(reader_away)

            for i in range(start_time):
                next(reader_home)
                next(reader_away)

            counter = start_time
            for row_home, row_away in zip(reader_home, reader_away):

                if counter < end_time:
                    pass
                else:
                    break

                if row_home[31] != 'NaN':
                    times.append(float(row_home[2]))

                    for i in range(3, len(row_home)-2, 2):
                        if row_home[i] == 'NaN':
                            coordinates_home.append([0.6*x_side, 1.1*y_side])
                        else:
                            coordinates_home.append([float(row_home[i])*x_side, float(row_home[i+1])*y_side])

                    times_home.append(coordinates_home)
                    coordinates_home = []

                    for i in range(3, len(row_away)-2, 2):
                        if row_away[i] == 'NaN':
                            coordinates_away.append([0.4*x_side, 1.1*y_side])
                        else:
                            coordinates_away.append([float(row_away[i])*x_side, float(row_away[i+1])*y_side])

                    times_away.append(coordinates_away)
                    coordinates_away = []

                    times_ball.append([float(row_home[31])*x_side, float(row_home[32])*y_side])

                counter += 1

    return times_home, times_away, times_ball, times

def parse_loco(filename):
    times = []

    with(open(filename, "r") as file):
        reader = csv.reader(file, delimiter=',')

        for row in reader:
            times.append([float(row[0]), float(row[1]), float(row[2]), float(row[3])])

    return times

