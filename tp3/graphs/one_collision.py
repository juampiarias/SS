from parser import collisions, times
import parser
import configparser
import matplotlib.pyplot as plt

config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])
files = ['outputv1.csv', 'outputv3.csv', 'outputv6.csv', 'outputv10.csv']

delta_t = 0.01

def collision(file):
    ids = {0}
    ids.remove(0)
    time = 0.01
    counter = 0
    x = [0]
    counts = [0]
    sixty = 0
    particles, times, collisions = parser.parse_csv(file)
    for c, t in zip(collisions, times):
        if t >= time:
            counts.append(counter)
            x.append(time)
            if sixty == 0 and counter >= 120:
                sixty = time
            time += delta_t


        if c[0] == 0:
            if c[1] not in ids:
                counter += 1
                ids.add(c[1])
    return counts, x, sixty

counts1, time1, sixty1 = collision(config['DEFAULT']['python'] + files[0])
counts3, time3, sixty3 = collision(config['DEFAULT']['python'] + files[1])
counts6, time6, sixty6 = collision(config['DEFAULT']['python'] + files[2])
counts10, time10, sixty10 = collision(config['DEFAULT']['python'] + files[3])

print(sixty1, sixty3, sixty6, sixty10)

plt.plot(time1, [120]*len(time1))
plt.plot(time1, counts1, label='v=1')
plt.plot(time3, counts3, label='v=3')
plt.plot(time6, counts6, label='v=6')
plt.plot(time10, counts10, label='v=10')

# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Cantidad de colisiones')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()
