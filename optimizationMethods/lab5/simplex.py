# c=(10, 5, -25, 5, 0)
# b=(32, 1, 15)
# A=((8, 16, 8, 8, 24), (0, 2, -1, 1, 1), (0, 3, 2, -1, 1))

def min_check(seq):
    x = 0
    # убираем все `-1', потом ищем минимальный
    while x < len(seq):
        while x < len(seq) and seq[x] == '-1':
            seq.pop(x)
        x += 1
    return min(seq)

def calculate_bases(a, b, c, counter):
    bases = []
    for x in range(len(a[0])):
        column = [row[x] for row in a]
        if column.count(1) == 1 and column.count(0) == len(column) - 1:
            bases.append(column.index(1))
        else:
            bases.append(-1)
    baseExists = []
    for x in range(0, len(b)):
        baseExists.append(False)
    for x in range(0, len(a[0])):
        if bases[x] > -1:
            baseExists[bases[x]] = True
    for x in range(0, len(b)):
        if not baseExists[x]:
            c.append(1)
            counter += 1
            for y in range(0, len(b)):
                a[y].append(0)
            a[x][len(a[0]) - 1] = 1
            bases.append(x)
    return bases

def calculate_potential(a, b, index):
    potential = []
    for y in range(0, len(b)):
        if a[y][index] > 0:
            potential.append(b[y] / a[y][index])
        else:
            potential.append('-1')
    return potential

def calculate_result(a, b, bases, counter):
    result = []
    bases = bases[:len(bases) - counter]
    for x in range(0, len(bases)):
        if bases[x] > -1:
            result.append(b[bases[x]])
        else:
            result.append(0)
    return result
def my_simplex(a, b, c, flip, counter):
    # преобразование задачи из макс. в мин.
    if flip:
        for x in range(0, len(c)):
            c[x] = -c[x]
    # базисы определяются значением индекса, на котором стоит единица
    # иначе значение индекса -1
    bases = []
    for x in range(len(a[0])):
        column = [row[x] for row in a]
        if column.count(1) == 1 and column.count(0) == len(column) - 1:
            bases.append(column.index(1))
        else:
            bases.append(-1)
    baseExists = []
    # далее проверяем, для всех ли строк существует базисный столбец
    # для строки существует базисный столбец, если есть такой базисный столбец
    # у которого на этой строке есть единица
    for x in range(0, len(b)):
        baseExists.append(False)
    for x in range(0, len(a[0])):
        if bases[x] > -1:
            baseExists[bases[x]] = True
    # если где-то нет базиса, добавляем
    # если базиса для такой строки нет, то добавляем единицу в эту строку
    # в остальные - нули
    # в уравнение минимизации добавляем единицу
    for x in range(0, len(b)):
        if not baseExists[x]:
            c.append(1)
            counter += 1
            for y in range(0, len(b)):
                a[y].append(0)
            a[x][len(a[0]) - 1] = 1
            bases.append(x)
    # подсчёт оценок
    deltas = []
    for y in range(0, len(a[0])):
        d = 0
        for x in range(0, len(bases)):
            if bases[x] > -1:
                d += a[bases[x]][y] * c[x]
        d -= c[y]
        deltas.append(d)
    # если все оценки неположительные, то алгоритм заканчивает свою работу
    success = True
    for x in range(1, len(deltas)):
        success = deltas[x] <= 0 and success
    # пока есть положительные оценки:
    while not success:
        # ищем максимальную оценку
        index = deltas.index(max(deltas))
        # в столбце с макс. оценкой находим строку, у которой деление ограничения
        # на соотв. значение из симпл. таблицы минимальное
        # при этом не забываем, что делитель должен быть больше нуля
        # иначе добавляем '-1'
        potential = []
        for y in range(0, len(b)):
            if a[y][index] > 0:
                potential.append(b[y] / a[y][index])
            else:
                potential.append('-1')
        potential_prime = []
        # да, именно так, иначе питон не понимает, что я хочу создать дубликат
        for x in range(0, len(potential)):
            potential_prime.append(potential[x])
        # minp - минимальный элемент
        minp = min_check(potential)
        # index2 - индекс скроки
        index2 = potential_prime.index(minp)
        # смена базисных столбцов
        # заменяемый базисный столбец теперь получает индекс -1
        # а новый получает индекс строки
        index3 = bases.index(index2)
        bases[index] = index2
        bases[index3] = -1
        # теперь мы должны пересчитать симплекс таблицу
        # сначала делим строку на элемент, чтобы получить один
        # далее прибавляем эту строку, умноженную на минус элемент этого же столбца, но других строк
        # к остальным строкам, чтобы получить нули
        divider = a[index2][index]
        for x in range(0, len(a[0])):
            a[index2][x] /= divider
        b[index2] /= divider
        for y in range(0, len(a)):
            if y != index2:
                multiplier = -a[y][index]
                for x in range(0, len(a[0])):
                    a[y][x] += a[index2][x] * multiplier
                b[y] += b[index2] * multiplier
        # пересчёт оценок
        deltas = []
        for y in range(0, len(a[0])):
            d = 0
            for x in range(0, len(bases)):
                if bases[x] > -1:
                    d += a[bases[x]][y] * c[x]
            d -= c[y]
            deltas.append(d)
        # проверка на окончание алгоритма
        success = True
        for x in range(0, len(deltas)):
            success = deltas[x] <= 0 and success
    # после окончания работы алгоритма переходим к непосредтсвенному выводу ответа
    # result - значения переменных целевой функции
    result = []
    # убираем дополнительные базисы
    bases = bases[:len(bases) - counter]
    for x in range(0, len(bases)):
        if bases[x] > -1:
            result.append(b[bases[x]])
        else:
            result.append(0)
    # подсчёт значения целевой функции
    f = 0
    for x in range(0, len(bases)):
        if bases[x] > -1:
            f += b[bases[x]] * c[x]
    if flip:
        f = -f
    return [f, result]


result = my_simplex(
    [[8, 16, 8, 8, 24], [0, 2, -1, 1, 1], [0, 3, 2, -1, 1]], [32, 1, 15], [10, 5, -25, 5, 0],
    False, 0)

print(result)
