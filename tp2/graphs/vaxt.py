import matplotlib.pyplot as plt
import numpy as np
from vaparser import files_list

# Plotting the numbers with their positions as x-values
for va_list in files_list:
    plt.plot(va_list)

# Adding labels and title
plt.xlabel('t')
plt.ylabel('Va')
plt.title('VAxT for different n')
plt.grid(True)

# Displaying the plot
plt.show()

mean_list = []
std_list = []

for va_list in files_list:
    stationary = va_list[-80:]
    va_mean = np.mean(stationary)
    va_std = np.std(stationary)
    mean_list.append(va_mean)
    std_list.append(va_std)

plt.errorbar(range(len(files_list)), mean_list, yerr=std_list, fmt='o', capsize=5)
plt.title('VAxETA Stationary Means')
plt.xlabel('Eta')
plt.ylabel('Va')
plt.xticks(range(len(files_list)), labels=np.arange(0, 5.5, step=0.5))
plt.grid(True)
plt.show()