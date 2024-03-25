import math
import os
import random
import re
import sys

k = 0.4
a = 0.9


def first_function(args: []) -> float:
    return args[0] + args[1] - 2


def second_function(args: []) -> float:
    return args[0] + args[1] - 3


def third_function(args: []) -> float:
    return math.tan(args[0] * args[1] + k) - pow(args[0], 2)


def fourth_function(args: []) -> float:
    return a * pow(args[0], 2) + 2 * pow(args[1], 2) - 1


def fifth_function(args: []) -> float:
    return pow(args[0], 2) + pow(args[1], 2) + pow(args[2], 2) - 1


def six_function(args: []) -> float:
    return 2 * pow(args[0], 2) + pow(args[1], 2) - 4 * args[2]


def seven_function(args: []) -> float:
    return 3 * pow(args[0], 2) - 4 * args[1] + pow(args[2], 2)


def default_function(args: []) -> float:
    return 0.0


# How to use this function:
# funcs = Result.get_functions(4)
# funcs[0](0.01)
def get_functions(n: int):
    if n == 5:
        return [first_function, second_function]
    elif n == 2:
        k = 0.4
        a = 0.9
        return [third_function, fourth_function]
    elif n == 3:
        k = 0
        a = 0.5
        return [third_function, fourth_function]
    elif n == 4:
        return [fifth_function, six_function, seven_function]
    else:
        return [default_function]


def solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations, max_iterations):
    functions = get_functions(system_id)
    def find_jacobian(functions, variables):
        jacobian_matrix = [[0] * number_of_unknowns for _ in range(number_of_unknowns)]
        for i in range(number_of_unknowns):
            for j in range(number_of_unknowns):
                small_change = [0] * number_of_unknowns
                small_change[j] = 1e-6
                jacobian_matrix[i][j] = (functions[i]([variables[dim] + small_change[dim] for dim in range(number_of_unknowns)]) - functions[i](variables)) / 1e-6
        return jacobian_matrix

    def normalize(function_values):
        return math.sqrt(sum(value ** 2 for value in function_values))

    def subtract_vectors(vector_a, vector_b):
        return [a_i - b_i for a_i, b_i in zip(vector_a, vector_b)]

    def solve_linear_system(jacobian_matrix, function_values):
        number_of_variables = len(jacobian_matrix)
        augmented_matrix = jacobian_matrix

        # Append function_values to the matrix augmented_matrix
        for i in range(number_of_variables):
            augmented_matrix[i].append(function_values[i])

        # Gaussian elimination
        for i in range(number_of_variables):
            for j in range(i + 1, number_of_variables):
                ratio = augmented_matrix[j][i] / augmented_matrix[i][i]
                for k in range(number_of_variables + 1):
                    augmented_matrix[j][k] -= ratio * augmented_matrix[i][k]

        # Back substitution
        solutions = [0 for _ in range(number_of_variables)]
        solutions[number_of_variables - 1] = (augmented_matrix[number_of_variables - 1][number_of_variables] /
                                              augmented_matrix[number_of_variables - 1][number_of_variables - 1])
        for i in range(number_of_variables - 2, -1, -1):
            solutions[i] = augmented_matrix[i][number_of_variables]
            for j in range(i + 1, number_of_variables):
                solutions[i] -= augmented_matrix[i][j] * solutions[j]
            solutions[i] /= augmented_matrix[i][i]
        return solutions

    variables = initial_approximations
    for i in range(max_iterations):
        function_values = [function(variables) for function in functions]
        if normalize(function_values) < 1e-6:
            return variables
        jacobian_matrix = find_jacobian(functions, variables)
        for i in range(len(jacobian_matrix)):
            jacobian_matrix[i][i] += 1e-6
        variables = subtract_vectors(variables, solve_linear_system(jacobian_matrix, function_values))

    raise RuntimeError("Метод Ньютона не сошелся за {} итераций".format(max_iterations))


if __name__ == '__main__':
    system_number = int(input().strip())

    number_of_unknowns = int(input().strip())

    initial_approximations = []

    for _ in range(number_of_unknowns):
        initial_approximation = float(input().strip())
        initial_approximations.append(initial_approximation)
    try:
        result = solve_by_fixed_point_iterations(system_number, number_of_unknowns, initial_approximations, 1000)
    except RuntimeError as e:
        print("Произошла ошибка, перепроверьте что система имеет решение:", e)
        sys.exit(1)
    print("Ответ:", " ".join(str(value) for value in result))
