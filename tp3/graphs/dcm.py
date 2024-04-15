from parser import particles, times
import parser
import math
import matplotlib.pyplot as plt


dcm = []
for p in particles:
    aux = math.sqrt(math.pow(p[0][1] - parser.side/2,2) + math.pow(p[0][2] - parser.side/2,2))
    dcm.append(aux)

plt.plot(times, dcm)
# Agregar etiquetas de ejes y título
plt.xlabel('Tiempo [s]')
plt.ylabel('|Z|')
plt.grid(True)
# Mostrar el gráfico
plt.show()
