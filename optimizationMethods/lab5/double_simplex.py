# c=(-5, -4, 0, 0, 0)
# b=(-2, -1, -7)
# A=((1, 2, -1, 0, 0), (-4, -3, 0, -1, 0), (-4, -4, -1, -1, -1))

def min_check(seq):
    x = 0
    # убираем все `-1', потом ищем минимальный
    while x < len(seq):
        while x < len(seq) and seq[x] == '-1':
            seq.pop(x)
        x += 1
    return min(seq)


def my_simplex_double(a, b, c, flip, saveslack, counter):
    # преобразование задачи из макс. в мин.
    if flip:
        for x in range(0, len(c)):
            c[x] = -c[x]
    # базисы определяются значением индекса, на котором стоит единица
    # иначе значение индекса -1
    bases = []
    # symbols - вспомогательный список для работы с преобразованием задачи в канонический вид
    # из каждого уравнения берёт последний элемент, который может быть знаком неравенства
    # если знак неравенства присутствует, то значение убирается для дальнейшего подсчёта
    # иначе считается, что между уравнением и ограничением стоит равенство
    symbols = []
    for x in range(0, len(a)):
        index = len(a[x]) - 1
        if type(a[x][index]) is str:
            symbols.append(a[x][index])
            a[x].pop(index)
        else:
            symbols.append(a[x][index])
    # сounter - переменная для удаления slack'ов
    # все доп. переменные обычно находятся в конце, и их легко удалить
    # counter - количество таких доп. переменных
    # преобразование в канонич. вид
    # если присутствует знак, то дальше идут действия в соответствии с ним
    for x in range(0, len(a)):
        # если знак присутствует, то в строке добавляется 1 (или -1), в остальных нули
        if symbols[x] == '<':
            c.append(0)
            counter += 1
            for y in range(0, len(a)):
                if y != x:
                    a[y].append(0)
                else:
                    a[y].append(1)
        elif symbols[x] == '>':
            c.append(0)
            counter += 1
            for y in range(0, len(a)):
                if y != x:
                    a[y].append(0)
                else:
                    a[y].append(-1)
    # далее для определения, существуют ли везде базисы, работает этот цикл
    # в первую очередь, мы определяем все уже существующие базисы
    for x in range(0, len(a[0])):
        # изначально мы предполагаем что столбец базисный, но с неопр. индексом (-1)
        y = 0
        baseIndex = -1
        isBase = True
        while isBase and y < len(b):
            # если мы находим 1 или 0, то всё хорошо
            isBase = a[y][x] == 0 or a[y][x] == 1
            # однако нужно проверить, какой раз мы "напоролись" на единицу
            # если уже не первый, то явно столбец тоже не базисный
            if a[y][x] == 1:
                if baseIndex > -1:
                    isBase = False
                    baseIndex = -1
                else:
                    baseIndex = y
            y += 1
            # если всё хорошо, добавляем новый базисный индекс, иначе -1
        if isBase:
            bases.append(baseIndex)
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
    # запоминаем, какие столбцы у нас изначально были базисными
    # понадобится для дальнейшей работы
    bases_prime = []
    for x in range(0, len(bases)):
        if bases[x] > -1:
            bases_prime.append(x)
    # подсчёт оценок
    deltas = []
    for y in range(0, len(a[0])):
        d = 0
        for x in range(0, len(bases)):
            if bases[x] > -1:
                d += a[bases[x]][y] * c[x]
        d -= c[y]
        deltas.append(d)
    # далее всё почти как в симплексном методе
    # меняется принцип работы
    # условие окончание теперь другое
    # работа алгоритма завершается, если все ограничения неотрицательные
    success = True
    for x in range(0, len(b)):
        success = b[x] >= 0 and success
    while not success:
        # ищется минимальное по значению ограничению
        index = b.index(min(b))
        # дальше ищется минимальное частное от значений
        # оценок на соотв. значения строки
        potential = []
        for y in range(0, len(deltas)):
            if a[index][y] != 0 and bases[y] == -1 and deltas[y] < 0:
                potential.append(deltas[y] / a[index][y])
            else:
                potential.append('-1')
        potential_prime = []
        for x in range(0, len(potential)):
            potential_prime.append(potential[x])
        minp = min_check(potential)
        index2 = potential_prime.index(minp)
        # смена базисных столбцов
        # заменяемый базисный столбец теперь получает индекс -1
        # а новый получает индекс строки
        index3 = bases.index(index)
        bases[index2] = index
        bases[index3] = -1
        # теперь мы должны пересчитать симплекс таблицу
        # сначала делим строку на элемент, чтобы получить один
        # далее прибавляем эту строку, умноженную на минус элемент этого же столбца, но других строк
        # к остальным строкам, чтобы получить нули
        divider = a[index][index2]
        for x in range(0, len(a[0])):
            a[index][x] /= divider
        b[index] /= divider
        for y in range(0, len(a)):
            if y != index:
                multiplier = -a[y][index2]
                for x in range(0, len(a[0])):
                    a[y][x] += a[index][x] * multiplier
                b[y] += b[index] * multiplier
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
        for x in range(0, len(b)):
            success = b[x] >= 0 and success
    # после окончания работы алгоритма переходим к непосредтсвенному выводу ответа
    # result - значения переменных целевой функции
    result = []
    # убираем дополнительные базисы
    bases = bases[:len(bases) - counter]
    # если нужно сохранить остальное, то для этого используется параметр saveslack
    if not saveslack:
        for x in range(0, len(a)):
            a[x] = a[x][:len(a[x]) - counter]
        c = c[: len(c) - counter]
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
    return [a, b, bases_prime, bases, f, result, c, counter]

print(my_simplex_double([[1, 2, -1, 0, 0], [-4, -3, 0, -1, 0], [-4, -4, -1, -1, -1]], [-2, -12, -10], [-5, -4, 0, 0, 0], False, False, 0))