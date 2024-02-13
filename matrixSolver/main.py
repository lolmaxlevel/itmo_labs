import copy


def print_matrix(array):
    for _ in array:
        for _1 in _:
            print('{:^5}'.format(_1), end="")
        print()


def gauss_method(matrix):
    ans_det = 1

    def divide_array(array, number):
        for _ in range(len(array)):
            array[_] /= number

    for nrow in range(len(matrix)):

        divider = matrix[nrow][nrow]

        if divider == 0:
            return 0, []

        ans_det *= divider
        divide_array(matrix[nrow], divider)

        for k in range(nrow + 1, len(matrix)):
            factor = matrix[k][nrow]
            for j in range(len(matrix[k])):
                matrix[k][j] -= factor * matrix[nrow][j]

    for nrow in range(len(matrix) - 1, 0, -1):

        row = matrix[nrow]

        for new_row in range(nrow):
            factor = matrix[new_row][nrow]
            for j in range(len(matrix[new_row])):
                matrix[new_row][j] -= factor * row[j]

    return ans_det, matrix


fname = input("input file name\n")
splitter = input("input splitter in your file.\n")
with open(fname, 'r') as f:
    input_matrix = []
    try:
        for line in f:
            m = [float(num) for num in line.replace("  ", " ").split(splitter)]
            input_matrix.append(m)
    except ValueError as e:
        print("ошибка чтения матрицы, поправьте пожалуйста =(", e)

copied_matrix = copy.deepcopy(input_matrix)

determinant, answer_matrix = gauss_method(input_matrix)

if determinant == 0:
    print("Решения нет")
    exit(-1)
discrepancies = []
for i in range(len(copied_matrix)):
    temp = 0
    for j in range(len(copied_matrix[i]) - 1):
        temp += copied_matrix[i][j] * answer_matrix[j][-1]
    discrepancies.append(abs(copied_matrix[i][-1] - temp))

print("Детерминант: ", determinant)
print("Итоговая матрица:")
print()
print("И ответ:")
for num, i in enumerate(answer_matrix):
    print("{:^3} = {:^5}, невязка: {:^5}".format("x" + str(num + 1), i[-1], discrepancies[num]))
print(discrepancies)