package tasks;

import common.Person;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  // NOTE: Изменять приходящий список persons (как в исходном коде) - по-моему, не очень практика))
  // NOTE: Что касается первой персоны, по смыслу мы её не удаляем, а просто игнорируем.
  // NOTE: Поэтому remove не нужен, а стримовский skip тут как раз хорошо ложится.
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  // NOTE: исходный код - антипример, который даже на лекции был. Если нам просто нужно создать сет,
  // NOTE: банально используем имеющийся конструктор.
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  // NOTE: Во-первых, имхо, название метода неудачное. Никак не отражает то, что мы возвращаем именно полное имя.
  // NOTE: Скорее похоже на какую-то непонятную альтернативу toString(). Во-вторых, в работе метода почему-то
  // NOTE: два раза обрабатывается secondName, а про middleName вообще забыли.
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // NOTE: Исходный код более громоздкий, на стримах более читаемо и оптимизировано.
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString, (n1, n2) -> n1));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // NOTE: код на стримах, по сравнению с исходным, более лаконичный и читаемый.
  // NOTE: а также работает за O(n) за счёт сета (хотя моя прошлая версия работала за O(n^2), поправил).
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> persons2set = new HashSet<>(persons2);
    return persons1.stream()
            .anyMatch(persons2set::contains);
  }

  // NOTE: комментарий "..." убран. и так понятно, что делает метод :)
  public long countEven(Stream<Integer> numbers) {
    // NOTE: тк у стримов есть встроенный метод-счётчик, переменная count для подсчёта не нужна
    // NOTE: (уж тем более нет смысла делать это полем целого класса, когда оно используется только в одном методе).
    // NOTE: также исходный код падал на параллельных стримах (как раз за счёт классового count).
    return numbers
            .filter(num -> num % 2 == 0)
            .count();
  }
}
