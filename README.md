VERSION 1:
уровень коррекции L(Low): 41 цифра / 25 симовлов латинского алфавита / 17 бинарных данных
уровень коррекции M(Medium): 34 цифра / 20 симовлов латинского алфавита / 14 бинарных данных
уровень коррекции Q(Quartile): 27 цифра / 16 симовлов латинского алфавита / 11 бинарных данных
уровень коррекции H(High): 17 цифра / 10 симовлов латинского алфавита / 7 бинарных данных

закодировать число 1234:
1. число разбивается на трехзначные числа: "123" и "4"
2. каждое такое число переводится в двоичный вид (10 бит). 
Для последнего числа, которое может быть не трехзначным, 
может быть отведено 7 или 4 бита.
3. далее для этой последовательности в начале надо указать хэдэр для декодера 
(для числовых значений "0001")
4. сразу после хэдера надо указать число цифр в кодированном числе (для 1234 -> 4) в двоичном виде 
(для числовых значений отводится 10 бит)
5. дополнить последовательность до целого числа байт, добавляя в конец нули.
6. далее перевести последовательность в десятичный вид
7. найти генерирующий многочлен по таблице
8. 