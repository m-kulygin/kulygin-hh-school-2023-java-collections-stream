package tasks;

import common.Person;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
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
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString, (n1, n2) -> n1));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream()
            .anyMatch(persons2::contains);
  }

  // NOTE: комментарий "..." убран. и так понятно, что делает метод :)
  public long countEven(Stream<Integer> numbers) {
    // NOTE: тк у стримов есть встроенный метод-счётчик, переменная count для подсчёта не нужна
    // NOTE: (уж тем более нет смысла делать это полем целого класса, когда оно используется только в одном методе)
    return numbers
            .filter(num -> num % 2 == 0)
            .count();
  }
}
