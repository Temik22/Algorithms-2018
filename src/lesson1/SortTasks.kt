@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
 * каждый на отдельной строке. Пример:
 *
 * 13:15:19
 * 07:26:57
 * 10:00:03
 * 19:56:14
 * 13:15:19
 * 00:40:31
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 00:40:31
 * 07:26:57
 * 10:00:03
 * 13:15:19
 * 13:15:19
 * 19:56:14
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    val list = File(inputName).readLines().map { it -> it.split(':') }.map { it ->
        it.reversed().foldIndexed(0)
        { index, prev, elem -> Math.pow(60.0, index.toDouble()).toInt() * elem.toInt() + prev }
    }.toIntArray()
    insertionSort(list)
    val outputFile = File(outputName).bufferedWriter()
    for (i in 0..list.lastIndex) {
        outputFile.write("${toTwoDigit(list[i] / 3600)}:" + "${toTwoDigit((list[i] / 60) % 60)}:"
                + toTwoDigit(list[i] % 60))
        outputFile.newLine()
    }
    outputFile.close()
}

/**
 * Additional function
 */

fun toTwoDigit(x: Int): String {
    if (x in 0..9) return "0$x"
    return "$x"
}


/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val input = File(inputName).readLines().map { it -> it.toDouble() }.toDoubleArray()
    mergeDoubleSort(input, 0, input.size)
    val output = File(outputName).bufferedWriter()
    for (elem in input) {
        output.write(elem.toString())
        output.newLine()
    }
    output.close()
}

private fun merge(elements: DoubleArray, begin: Int, middle: Int, end: Int) {
    val left = Arrays.copyOfRange(elements, begin, middle)
    val right = Arrays.copyOfRange(elements, middle, end)
    var li = 0
    var ri = 0
    for (i in begin until end) {
        if (li < left.size && (ri == right.size || left[li] <= right[ri])) {
            elements[i] = left[li++]
        } else {
            elements[i] = right[ri++]
        }
    }
}

private fun mergeDoubleSort(elements: DoubleArray, begin: Int, end: Int) {
    if (end - begin <= 1) return
    val middle = (begin + end) / 2
    mergeDoubleSort(elements, begin, middle)
    mergeDoubleSort(elements, middle, end)
    merge(elements, begin, middle, end)
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    val input = File(inputName).readLines().map { it -> it.toInt() }.toIntArray()
    var max = 0
    for (elem in input) {
        if (elem > max) max = elem
    }
    val count = IntArray(max + 1)
    for (elem in input) {
        count[elem]++
    }
    var number = 0
    var counter = count[0]
    for (i in 1 until count.size) {
        if (count[i] > counter) {
            counter = count[i]
            number = i
        }
    }
    val output = File(outputName).bufferedWriter()
    for (elem in input) {
        if (elem != number) {
            output.write(elem.toString())
            output.newLine()
        }
    }
    var i = 0
    while (i != count[number]) {
        output.write(number.toString())
        output.newLine()
        i++
    }
    output.close()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    var li = 0
    var ri = first.size
    for (i in 0 until second.size) {
        if (li < first.size && (ri == second.size || first[li] <= second[ri]!!)) {
            second[i] = first[li++]
        } else {
            second[i] = second[ri++]
        }
    }
}

