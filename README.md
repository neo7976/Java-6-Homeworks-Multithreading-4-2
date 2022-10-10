## Задача 2. Разница в производительности

### Описание
Попробуйте оценить производительность двух вариантов мап: ConcurrentHashMap и многопоточную обертку над HashMap `Collections.synchronizedMap(new HashMap<>()`

### Работа программы
1. Создайте две мапы разных типов. Пусть они хранят, например, Integer
2. Сгенерируйте массив чисел для добавления в мапы
3. Напишите эмуляцию чтения и записи для каждой из мап в несколько потоков. Проведите оба эксперимента с замером времени и выведите результаты на консоль.
4. Попробуйте существенно увеличить или уменьшить количество добавляемых элементов для повторного эксперимента. В комментариях к задаче укажите как именно изменились результаты