## Launch log CalcDemo:
| Number | GC   | -Xms | -Xmx | Additional set                                                         | Fix (int) | Result                            |
|--------|------|------|------|------------------------------------------------------------------------|-----------|-----------------------------------|
| 1      | G1GC | 256  | 256  | -                                                                      | no        | OutOfMemoryError: Java heap space |
| 2      | G1GC | 384  | 384  | -                                                                      | no        | spend msec:10377, sec:10          |
| 3      | G1GC | 384  | 384  | -XX:MaxGCPauseMillis=200, -XX:ParallelGCThreads=8, -XX:ConcGCThreads=4 | no        | spend msec:9965, sec:9            | 
| 4      | G1GC | 512  | 512  |                                                                        | no        | spend msec:9338, sec:9            |
| 5      | G1GC | 1024 | 1024 |                                                                        | no        | spend msec:8266, sec:8            |
| 6      | G1GC | 2048 | 2048 |                                                                        | no        | spend msec:7290, sec:7            |
| 7      | ZGC  | 2048 | 2048 |                                                                        | no        | spend msec:5048, sec:5            |
| 8      | G1GC | 2048 | 2048 |                                                                        | yes       | **spend msec:1685, sec:1**        |
| 9      | G2GC | 512  | 512  |                                                                        | yes       | **spend msec:1697, sec:1**        |
| 10     | G2GC | 256  | 256  |                                                                        | yes       | spend msec:2391, sec:2            |
| 11     | ZGC  | 256  | 256  |                                                                        | yes       | spend msec:2122, sec:2            |

Исходя из проведенных тестов видно, что наиболее заметный прирост во времени
исполнения программы - дает смена типа полей на примитивный (Integer -> int). 
Запуск №8. Уменьшение параметров -Xms -Xmx до 512m не оказал влияния на результат (запуск №9).
Дальнейшее уменьшение данных параметров негативно сказалось на производительности.
Приблизительно минимальыно необходимый размер хипа 384m (Запуск №2). Опытным путем, после нескольких рядов тестов с 
дополнительными настройками удалось выбить еще чуть чуть на том же объеме (Запуск №3).
Параметр указывющий на приемлемое максимальное время сборки мусора (MaxGCPauseMillis) равный 200мс оказался "золотой серидиной" для 
небольших размерах хипа. Количество потоков для пометок (ConcGCThreads) не более 4 не влияло на результат, а вот менее 
уже существенно замедляло итоговое выполнение. Аналогичная ситуация и с 8ю потоками на сборку мусора (ParallelGCThreads)
При дальнейшем увеличении объема хипа, данные настройки не оказывают существенное влияние на результат.

Стоит отметить что доработка задания проходила на ноуте в режиме не максимальной производительности
что немного исказило первых 7 запусков (хотя их было больше), но в целом картины не поменяло.

Оптимальные настройки для текущей конфигурции с учетом использования примитивов:
- java.vm.vendor=BellSoft
- java.vm.version=17.0.2+9-LTS
- Ubuntu 22.04/Core 6\12/16Gb

**-Xms512 -Xmx512**




