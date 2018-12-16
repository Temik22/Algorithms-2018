package lesson6

import kotlin.test.assertEquals

abstract class AbstractDynamicTests {
    fun longestCommonSubSequence(longestCommonSubSequence: (String, String) -> String) {
        assertEquals("", longestCommonSubSequence("мой мир", "я"))
        assertEquals("1", longestCommonSubSequence("1", "1"))
        assertEquals("13", longestCommonSubSequence("123", "13"))
        assertEquals("здс", longestCommonSubSequence("здравствуй мир", "мы здесь"))
        assertEquals("emt ole", longestCommonSubSequence("nematode knowledge", "empty bottle"))
        assertEquals("e kerwelkkd r", longestCommonSubSequence(
                "oiweijgw kejrhwejelkrw kjhdkfjs hrk",
                "perhkhk lerkerorwetp lkjklvvd durltr"
        ))
        assertEquals(""" дд саы чтых,
евшнео ваа се сви дн.
        """.trimIndent(), longestCommonSubSequence(
                """
Мой дядя самых честных правил,
Когда не в шутку занемог,
Он уважать себя заставил
И лучше выдумать не мог.
                """.trimIndent(),
                """
Так думал молодой повеса,
Летя в пыли на почтовых,
Всевышней волею Зевеса
Наследник всех своих родных.
                """.trimIndent()
        ))
        assertEquals("""овнй жад ми
В усте молил есикрй ерам
 ерептне вл
 л а он
о у отверс вин
Как у пун лел,
И  ао м зв:
сань, ори в, и вн,
Иоль л ое
И о м ими,
ал грей""".trimIndent() ,  longestCommonSubSequence(
                """Духовной жаждою томим,
В пустыне мрачной я влачился,
И шестикрылый серафим
На перепутье мне явился.
Перстами легкими как сон
Моих зениц коснулся он:
Отверзлись вещие зеницы,
Как у испуганной орлицы.
Моих ушей коснулся он,
И их наполнил шум и звон:
И внял я неба содроганье,
И горний ангелов полет,
И гад морских подводный ход,
И дольней лозы прозябанье.
И он к устам моим приник,
И вырвал грешный мой язык,""".trimIndent(),
                """И празднословный и лукавый,
И жало мудрыя змеи
В уста замершие мои
Вложил десницею кровавой.
И он мне грудь рассек мечом,
И сердце трепетное вынул,
И угль, пылающий огнем,
Во грудь отверстую водвинул.
Как труп в пустыне я лежал,
И бога глас ко мне воззвал:
"Востань, пророк, и виждь, и внемли,
Исполнись волею моей
И, обходя моря и земли,
Глаголом жги сердца людей.""".trimIndent()))
    }

    fun longestIncreasingSubSequence(longestIncreasingSubSequence: (List<Int>) -> List<Int>) {
        assertEquals(listOf(), longestIncreasingSubSequence(listOf()))
        assertEquals(listOf(1), longestIncreasingSubSequence(listOf(1)))
        assertEquals(listOf(1, 2), longestIncreasingSubSequence(listOf(1, 2)))
        assertEquals(listOf(2), longestIncreasingSubSequence(listOf(2, 1)))
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                longestIncreasingSubSequence(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        )
        assertEquals(listOf(2, 8, 9, 12), longestIncreasingSubSequence(listOf(2, 8, 5, 9, 12, 6)))
        assertEquals(listOf(23, 34, 56, 87, 91, 98, 140, 349), longestIncreasingSubSequence(listOf(
                23, 76, 34, 93, 123, 21, 56, 87, 91, 12, 45, 98, 140, 12, 5, 38, 349, 65, 94,
                45, 76, 15, 99, 100, 88, 84, 35, 88
        )))
        assertEquals(listOf(1, 10, 13, 15, 42, 56, 78, 80, 128, 154, 345, 430, 561, 720),
                longestIncreasingSubSequence(listOf(1, 10, 13, 15, 42, 40, 56, 78, 45, 72, 61, 20, 40, 10, 80,
                        73, 154, 128, 481, 154, 825, 635, 145, 475, 345, 90, 10, 15, 75, 430, 561, 80, 12, 159, 250,
                720, 360, 40, 350, 104, 250, 350, 126, 50, 64)))
    }

    fun shortestPathOnField(shortestPathOnField: (String) -> Int) {
        assertEquals(1, shortestPathOnField("input/field_in2.txt"))
        assertEquals(12, shortestPathOnField("input/field_in1.txt"))
        assertEquals(43, shortestPathOnField("input/field_in3.txt"))
        assertEquals(67, shortestPathOnField("input/field_in4.txt"))
        assertEquals(0, shortestPathOnField("input/field_in5.txt"))
    }

}