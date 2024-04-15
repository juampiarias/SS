from parser import collisions, times, particles
import parser
import matplotlib.pyplot as plt

#p[c[0]][3] para vx p[c[0]][4] para vy
counter = 0
delta_t = 0.01
time = 0.01
counts = []

for c, t, p in zip(collisions, times, particles):
    if t >= time:
        counter /= (parser.side * 4) * delta_t
        counts.append(counter)
        time += delta_t
        counter = 0
    
    if c[1] == parser.n:
        counter += abs(p[c[0]][3])
        
    if c[1] == parser.n + 1:
        counter += abs(p[c[0]][4])

plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time / delta_t))], counts)

# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Presion')
plt.grid(True)
# Mostrar el gráfico
plt.show()
