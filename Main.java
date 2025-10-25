void main() {
    List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
    List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
    Collection<Person> persons = new ArrayList<>();

    // Оптимизация: создаем один объект Random для всех итераций
    Random random = new Random();
    for (int i = 0; i < 10_000_000; i++) {
        persons.add(new Person(
                names.get(random.nextInt(names.size())),
                families.get(random.nextInt(families.size())),
                random.nextInt(100),
                Sex.values()[random.nextInt(Sex.values().length)],
                Education.values()[random.nextInt(Education.values().length)])
        );
    }

    // 1. Найти количество несовершеннолетних (т.е. Людей младше 18 лет)
    long minorsCount = persons.stream()
            .filter(Objects::nonNull)
            .filter(person -> person.getAge() < 18)
            .count();
    IO.println("Количество несовершеннолетних: " + minorsCount);

    // 2. Получить список фамилий призывников (т.е. Мужчин от 18 и до 27 лет)
    List<String> conscripts = persons.stream()
            .filter(person -> Sex.MAN.equals(person.getSex()))
            .filter(person -> person.getAge() >= 18 && person.getAge() < 27)
            .map(Person::getFamily)
            .toList();
    IO.println("Количество призывников: " + conscripts.size());

    // 3. Получить отсортированный по фамилии список потенциально работоспособных людей
    // с высшим образованием (т.е. Мужчин от 18 до 60 лет и женщин от 18 до 65 лет)
    List<Person> workablePeople = persons.stream()
            .filter(person -> person.getEducation().equals(Education.HIGHER))
            .filter(person -> (person.getSex().equals(Sex.MAN) && person.getAge() >= 18 && person.getAge() < 60) ||
                    (person.getSex().equals(Sex.WOMAN) && person.getAge() >= 18 && person.getAge() < 65))
            .sorted(Comparator.comparing(Person::getFamily))
            .toList();

    IO.println("Количество потенциально работоспособных людей с высшим образованием: " + workablePeople.size());
    IO.println("Первые 10 человек:");
    workablePeople.stream()
            .limit(10)
            .forEach(System.out::println);
}
