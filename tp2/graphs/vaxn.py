import matplotlib.pyplot as plt
import numpy as np
from vaxnparser import files_list

# Plotting the numbers with their positions as x-values
labels = ['0.01','0.5','1.0','1.5','2.0','2.5',
          '3.0','3.5','4.0','4.5','5.0']

for i, va_list in enumerate(files_list):
    plt.plot(va_list, label=labels[i])

# Adding labels and title
plt.xlabel('t')
plt.ylabel('Va')
plt.title('VAxT for different n')
plt.legend(bbox_to_anchor=(1.04, 1), borderaxespad=0)
plt.grid(True)

# Displaying the plot
plt.show()

mean_list = []
std_list = []

for va_list in files_list:
    # stationary = va_list[-70:]
    stationary = va_list[-300:]
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