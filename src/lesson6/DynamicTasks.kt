@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    /**
     * n = first.length
     * m = second.length
     * labor intensity: O(n * m)
     * resource intensity: O(n * m)
     */
    val data = Array(first.length + 1) { IntArray(second.length + 1) }
    for (i in 1 until data.size) {
        for (j in 1 until data[i].size) {
            if (first[i - 1] == second[j - 1]) {
                data[i][j] = data[i - 1][j - 1] + 1
            } else {
                data[i][j] = maxOf(data[i][j - 1], data[i - 1][j])
            }
        }
    }
    var maxSubString = ""
    var i = first.length
    var j = second.length
    while (i > 0 && j > 0) {
        when {
            first[i - 1] == second[j - 1] -> {
                maxSubString += first[i - 1]
                i--
                j--
            }
            data[i][j - 1] > data[i - 1][j] -> j--
            data[i][j - 1] < data[i - 1][j] -> i--
            else -> i--
        }
    }
    return maxSubString.reversed()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Средняя
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    /**
     * labor intensity: O(n * n/2)
     * resource intensity: O(n + n + n) = O(n)
     */
    if (list.isEmpty()) return emptyList()
    val array = list.toIntArray()
    val lengths = IntArray(list.size) { 1 }
    val indexes = IntArray(list.size)


    for (i in 0 until array.size) {
        lengths[i] = 1
        indexes[i] = i
    }

    for (i in 1 until array.size) {
        for (j in 0 until i) {
            if (array[i] > array[j]) {
                if (lengths[j] + 1 > lengths[i]) {
                    lengths[i] = lengths[j] + 1
                    indexes[i] = j
                }
            }
        }
    }

    var max = 0
    for (i in 0 until lengths.size) {
        if (lengths[i] > lengths[max]) {
            max = i
        }
    }

    var t: Int
    var newT = max
    val temp = mutableListOf<Int>()
    do {
        t = newT
        temp.add(array[t])
        newT = indexes[t]
    } while (t != newT)

    return temp.reversed()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Сложная
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    /**
     * labor intensity: O(n * m)
     * resource intensity: O(n * m)
     */
    val data = File(inputName).readLines()
            .map { it -> it.split(' ').map { it.toInt() }.toIntArray() }
            .toTypedArray()
    val n = data.size - 1
    val m = data[0].size - 1
    val temp = Array(n + 1) { Array(m + 1) { 0 } }
    var sum = 0

    for (i in 0..m) {
        sum += data[0][i]
        temp[0][i] = sum
    }

    sum = 0

    for (i in 0..n) {
        sum += data[i][0]
        temp[i][0] = sum
    }

    for (i in 1..n) {
        for (j in 1..m) {
            temp[i][j] = data[i][j] + minOf(temp[i - 1][j - 1], temp[i - 1][j], temp[i][j - 1])
        }
    }

    return temp[n][m]
}


// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5