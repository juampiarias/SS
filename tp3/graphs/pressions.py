from parser import collisions, times, particles
import parser
import matplotlib.pyplot as plt
import math

#p[c[0]][3] para vx p[c[0]][4] para vy
counter = 0
o_counter = 0
time = 0.02
delta_t = 0.02
counts = []
o_counts = []

for c, t, p in zip(collisions, times, particles):
    if t >= time:
        counter /= ((parser.side * 4) * delta_t)
        o_counter /= (2*math.pi*parser.centralR*delta_t)
        counts.append(counter)
        o_counts.append(o_counter)
        time += delta_t
        counter = 0
        o_counter = 0
    
    if c[1] == parser.n:
        counter += abs(p[c[0]][3]*2)
        
    if c[1] == parser.n + 1:
        counter += abs(p[c[0]][4]*2)
    
    if c[0] == 0:
        dist = math.sqrt(math.pow(p[c[1]][1] - p[c[0]][1], 2) + math.pow(p[c[1]][2] - p[c[0]][2], 2))
        ex = (p[c[1]][1] - p[c[0]][1]) / dist
        ey = (p[c[1]][2] - p[c[0]][2]) / dist
        o_pression = (ex * p[c[1]][3]) + (ey * p[c[1]][4])
        print(o_pression)
        o_counter += abs(o_pression * 2)


plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time / delta_t))], counts, label = 'Pression for walls')
plt.plot([i * delta_t for i in range(int(delta_t / delta_t), int(time / delta_t))], o_counts, label = 'Pression for center particle')


# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('Presion')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()