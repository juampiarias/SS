from parser import collisions, times
import parser
import matplotlib.pyplot as plt

counter = 0
delta_t = 0.01
time = 0.01
counts = []
ids = {1}
ids.remove(1)

for c, t in zip(collisions, times):
    if t >= time:
        counts.append(counter)
        time += delta_t

    if c[1] == parser.n+2:
        if c[0] not in ids:
            counter += 1
            ids.add(c[0])


plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time / delta_t))], counts)

# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Cantidad de visitas')
plt.grid(True)
# Mostrar el gráfico
plt.show()
