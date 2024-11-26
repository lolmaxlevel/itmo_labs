import subprocess
import re
from concurrent.futures import ProcessPoolExecutor, as_completed
import matplotlib.pyplot as plt

# Function to run monolith.exe and extract the elapsed time
def run_monolith(load):
    result = subprocess.run(["C://monolith.exe", "-lr", "-r", str(load)], capture_output=True, text=True)
    match = re.search(r"Elapsed time:\s+([0-9.]+)\s+sec", result.stdout)
    if match:
        return float(match.group(1))
    return None

# Function to run tests with different numbers of processes and load
def run_tests():
    results = []
    total_load = 6000
    num_repeats = 1
    for num_processes in range(3, 4):
        print(f"Running test with {num_processes} processes...")
        load_per_process = total_load // num_processes
        average_times = []
        max_times = []
        for _ in range(num_repeats):
            with ProcessPoolExecutor(max_workers=num_processes) as executor:
                futures = [executor.submit(run_monolith, load_per_process) for _ in range(num_processes)]
                elapsed_times = [future.result() for future in as_completed(futures) if future.result() is not None]

            if elapsed_times:
                average_times.append(sum(elapsed_times) / len(elapsed_times))
                max_times.append(max(elapsed_times))

        if average_times and max_times:
            avg_time = sum(average_times) / len(average_times)
            max_time = sum(max_times) / len(max_times)
            results.append((num_processes, avg_time, max_time))
        else:
            results.append((num_processes, None, None))
    return results

# Function to plot the results
def plot_results(results):
    num_processes = [result[0] for result in results]
    average_times = [result[1] for result in results]
    max_times = [result[2] for result in results]

    plt.figure(figsize=(10, 5))
    plt.plot(num_processes, average_times, label='Average Time')
    plt.plot(num_processes, max_times, label='Max Time')
    plt.xlabel('Number of Processes')
    plt.ylabel('Time (sec)')
    plt.title('Performance with Different Number of Processes')
    plt.legend()
    plt.grid(True)
    plt.show()

if __name__ == "__main__":
    results = run_tests()
    plot_results(results)