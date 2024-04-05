import matplotlib.pyplot as plt
import numpy as np
from vaxroparser import files_list

labels = ['0.8','1.25','2.2','5','20']
# Plotting the numbers with their positions as x-values
for i, va_list in enumerate(files_list):
    plt.plot(va_list, label=labels[i])

# Adding labels and title
plt.xlabel('t')
plt.ylabel('Va')
plt.title(r"VAxT for different $\rho$")
plt.legend(title=r"$\rho$", bbox_to_anchor=(1.04, 1), borderaxespad=0)
plt.grid(True)

# Displaying the plot
plt.show()

mean_list = []
std_list = []

for va_list in files_list:
    stationary = va_list[-500:]
    va_mean = np.mean(stationary)
    va_std = np.std(stationary)
    mean_list.append(va_mean)
    std_list.append(va_std)

plt.errorbar(range(len(files_list)), mean_list, yerr=std_list, fmt='o', capsize=5)
plt.title(r"VAx$\rho$ Stationary Means")
plt.xlabel(r"$\rho$")
plt.ylabel('Va')
plt.xticks(range(len(labels)), labels=labels)
plt.grid(True)
plt.show()