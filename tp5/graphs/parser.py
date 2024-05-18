import csv

x_side = 105
y_side = 68

def parse_players():
    times_local = []
    times_away = []
    times_ball = []
    times = []

    with(open("../ios/TrackingData_Local.csv", "r") as file_local):
        with(open("../ios/TrackingData_Visitante.csv") as file_away):

            reader_local = csv.reader(file_local, delimiter=',')
            reader_away = csv.reader(file_away, delimiter=',')
            coordinates_local = []
            coordinates_away = []

            for i in range(3):
                next(reader_local)
                next(reader_away)

            for row_local, row_away in zip(reader_local, reader_away):

                times.append(float(row_local[2]))

                for i in range(3, len(row_local)-2, 2):
                    if row_local[i] == 'NaN':
                        coordinates_local.append([0.6*x_side, 1.1*y_side])
                    else:
                        coordinates_local.append([float(row_local[i])*x_side, float(row_local[i+1])*y_side])

                times_local.append(coordinates_local)
                coordinates_local = []

                for i in range(3, len(row_away)-2, 2):
                    if row_away[i] == 'NaN':
                        coordinates_away.append([0.4*x_side, 1.1*y_side])
                    else:
                        coordinates_away.append([float(row_away[i])*x_side, float(row_away[i+1])*y_side])

                times_away.append(coordinates_away)
                coordinates_away = []

                if row_local[31] == 'NaN':
                    times_ball.append([0.5*x_side, 1.1*y_side, 2])
                else:
                    times_ball.append([float(row_local[31])*x_side, float(row_local[32])*y_side, 0])


    return times_local, times_away, times_ball, times
