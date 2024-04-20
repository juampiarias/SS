import configparser
import parser
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])
files = ['outputv1.csv', 'outputv3.csv', 'outputv6.csv', 'outputv10.csv']

delta_t = 0.01

def multicol(file):
    counter = 0
    time = 0.01
    counts = [0]
    x = [0]
    fit_x = [[0]]
    particles, times, collisions = parser.parse_csv(file)
    for c, t in zip(collisions, times):
        if t >= time:
            counts.append(counter)
            x.append(time)
            fit_x.append([time])
            time += delta_t

        if c[0] == 0:
            counter += 1

    return counts, x, fit_x

counts1, time1, fit_x1 = multicol(config['DEFAULT']['python'] + files[0])
counts3, time3, fit_x3 = multicol(config['DEFAULT']['python'] + files[1])
counts6, time6, fit_x6 = multicol(config['DEFAULT']['python'] + files[2])
counts10, time10, fit_x10 = multicol(config['DEFAULT']['python'] + files[3])

reg1 = LinearRegression()
reg3 = LinearRegression()
reg6 = LinearRegression()
reg10 = LinearRegression()

reg1.fit(fit_x1, counts1)
reg3.fit(fit_x3, counts3)
reg6.fit(fit_x6, counts6)
reg10.fit(fit_x10, counts10)

# new_y1 = reg1.predict(fit_x1)
# new_y3 = reg3.predict(fit_x3)
# new_y6 = reg6.predict(fit_x6)
# new_y10 = reg1.predict(fit_x10)
# new_y = reg.predict(fit_x)
# eq = 'm = ' + str(reg.coef_)

plt.plot(time1, counts1, label='v1 m='+ str(reg1.coef_))
plt.plot(time3, counts3, label='v3 m='+ str(reg3.coef_))
plt.plot(time6, counts6, label='v6 m='+ str(reg6.coef_))
plt.plot(time10, counts10, label='v10 m='+ str(reg10.coef_))

# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Cantidad de colisiones')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()
