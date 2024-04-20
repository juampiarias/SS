from parser import collisions, times
import parser
import configparser
import matplotlib.pyplot as plt

delta_t = 0.01
config = configparser.ConfigParser()
config.read('../configs/app.config')
config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])
files = ['outputv1.csv', 'outputv3.csv', 'outputv6.csv', 'outputv10.csv']
j = 0
counts = []
counts3 = []
counts6 = []
counts10 = []

def collision(file):
    ids = {0}
    ids.remove(0)
    time = 0.01
    counter = 0
    delta_t = 0.01
    particles, times, collisions = parser.parse_csv(file)
    for c, t in zip(collisions, times):
        if t >= time:
            counts.append(counter)
            time += delta_t

        if c[0] == 0:
            if c[1] not in ids:
                counter += 1
                ids.add(c[1])
    return counts, time

for file in files:
    if j == 0:
        counts, time = collision(config['DEFAULT']['python'] + file)
    # elif j == 1:
    #     counts3, time3 = collision(config['DEFAULT']['python'] + file)
    # elif j == 2:
    #     counts6, time6 = collision(config['DEFAULT']['python'] + file)
    # elif j == 3:
    #     counts10, time10 = collision(config['DEFAULT']['python'] + file)
    # j += 1

plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time / delta_t)+1)], counts)
# plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time3 / delta_t)+1)], counts3)
# plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time6 / delta_t)+1)], counts6)
# plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time10 / delta_t)+1)], counts10)

# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Cantidad de colisiones')
plt.grid(True)
# Mostrar el gráfico
plt.show()
