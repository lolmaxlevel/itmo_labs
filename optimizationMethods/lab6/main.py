import random
import numpy as np

# Матрица расстояний между городами
distances = np.array([
    [0, 1, 1, 5, 3],
    [1, 0, 3, 1, 5],
    [1, 3, 0, 11, 1],
    [5, 1, 11, 0, 1],
    [3, 5, 1, 1, 0]
])

# Параметры алгоритма
POPULATION_SIZE = 4
MUTATION_RATE = 0.01
GENERATIONS = 3


# Генерация начальной популяции
def create_population(size, num_cities):
    population = []
    for _ in range(size):
        individual = list(range(num_cities))
        random.shuffle(individual)
        population.append(individual)
    return population


# Функция вычисления расстояния для одного индивидуума
def calculate_fitness(individual, distances):
    distance = 0
    for i in range(len(individual)):
        from_city = individual[i]
        to_city = individual[(i + 1) % len(individual)]
        distance += distances[from_city, to_city]
    return distance


# Оператор селекции (турнирный отбор)
def select_parents(population, fitnesses):
    while True:
        selected = random.choices(population, fitnesses, k=2)
        if selected[0] != selected[1]:
            break
    return selected


# Оператор кроссинговера (частично сопоставленный кроссовер)
def crossover(parent1, parent2):
    size = len(parent1)
    start, end = sorted(random.sample(range(size), 2))

    # Прибавить 1 к каждому городу, чтобы не было города с номером 0
    parent1_copy = [city + 1 for city in parent1]
    parent2_copy = [city + 1 for city in parent2]
    p1 = ''.join(map(str, parent1_copy[:start] + ['|'] + parent1_copy[start:end] + ['|'] + parent1_copy[end:]))
    p2 = ''.join(map(str, parent2_copy[:start] + ['|'] + parent2_copy[start:end] + ['|'] + parent2_copy[end:]))

    child1_middle = parent2[start:end + 1]
    child2_middle = parent1[start:end + 1]

    def fill_child(child_middle, parent):
        child = [None] * size
        child[start:end + 1] = child_middle

        current_pos = (end + 1) % size
        parent_pos = (end + 1) % size
        while None in child:
            if parent[parent_pos] not in child:
                child[current_pos] = parent[parent_pos]
                current_pos = (current_pos + 1) % size
            parent_pos = (parent_pos + 1) % size

        return child

    child1 = fill_child(child1_middle, parent1)
    child2 = fill_child(child2_middle, parent2)


    def log_crossover(p1, p2, c1, c2):
        print("№\tРодители\tПотомки\tЗначение целевой функции для потомков")
        print(f"1\t{p1}\t{''.join(map(str, [c+1 for c in c1]))}\t{calculate_fitness(c1, distances)}")
        print(f"2\t{p2}\t{''.join(map(str, [c+1 for c in c2]))}\t{calculate_fitness(c2, distances)}")

    log_crossover(p1, p2, child1, child2)
    return child1, child2


# Оператор мутации
def mutate(individual, mutation_rate):
    if random.random() < mutation_rate:
        idx1, idx2 = random.sample(range(len(individual)), 2)
        individual[idx1], individual[idx2] = individual[idx2], individual[idx1]


# Генетический алгоритм
def genetic_algorithm(distances, population_size, mutation_rate, generations):
    num_cities = len(distances)
    population = create_population(population_size, num_cities)
    start_population = population.copy()

    def log_population(population, generation, fitnesses):
        print(f"Поколение {generation}")
        print("№\tПопуляция\tЗначение целевой функции")
        for i, (individual, fitness) in enumerate(zip(population, fitnesses)):
            route = ''.join(map(lambda x: str(x + 1), individual))
            print(f"{i + 1}\t{route}\t{fitness}")

    def log_final_population(population, fitnesses):
        probabilities = [1 / fitness for fitness in fitnesses]
        total = sum(probabilities)
        probabilities = [p / total for p in probabilities]
        print("Популяция первого поколения после отсечения худших особей в результате работы оператора редукции:")
        print("№\tКод\tЗначение целевой функции\tВероятность участия в процессе размножения")
        for i, (individual, fitness, prob) in enumerate(zip(population, fitnesses, probabilities)):
            route = ''.join(map(lambda x: str(x + 1), individual))
            print(f"{i + 1}\t{route}\t{fitness}\t{prob:.2f}")

    for generation in range(1, generations + 1):
        fitnesses = [calculate_fitness(ind, distances) for ind in population]
        log_population(population, generation, fitnesses)

        new_population = population.copy()
        parents_log = []
        for _ in range(population_size // 2):
            parent1, parent2 = select_parents(population, fitnesses)
            parents_log.append((parent1, parent2))
            child1, child2 = crossover(parent1, parent2)
            mutate(child1, mutation_rate)
            mutate(child2, mutation_rate)
            new_population.append(child1)
            new_population.append(child2)

        population = sorted(new_population, key=lambda ind: calculate_fitness(ind, distances))[:population_size]
        fitnesses = [calculate_fitness(ind, distances) for ind in population]

        log_final_population(population, fitnesses)

    best_individual = min(population, key=lambda ind: calculate_fitness(ind, distances))
    best_fitness = calculate_fitness(best_individual, distances)

    return best_individual, best_fitness, start_population


# Запуск алгоритма
best_route, best_distance, start = genetic_algorithm(distances, POPULATION_SIZE, MUTATION_RATE, GENERATIONS)
print(f"Лучший маршрут: {''.join(map(lambda x: str(x + 1), best_route))}")
print(f"Кратчайшее расстояние: {best_distance}")
print(f"Среднее значение функции изменилось на {np.mean([calculate_fitness(ind, distances) for ind in start]) - best_distance}")
print(f"Лучшее значение функции изменилось на {np.min([calculate_fitness(ind, distances) for ind in start]) - best_distance}")
