import java.io.File;
import java.util.Scanner;

public class Main
{
    private static Dictionary currentDictionary;
    private static File dictionaryFile;

    public static void main(String[] args)
    {
        dictionaryFile = Dictionary.setDictionaryFile();
        selectDictionary();
        runMenu();
    }
    ///  выбираем словарь
    private static void selectDictionary()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите словарь:\n1. 4-буквенный\n2. 5-цифровой");
        int choice = scanner.nextInt();
        scanner.nextLine();

        currentDictionary = (choice == 1)
                ? new FourLettersDictionary(dictionaryFile)
                : new FiveNumbersDictionary(dictionaryFile);

        currentDictionary.loadDictionary();
    }
    ///  меню
    private static void runMenu()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1": switchDictionary(); break;
                case "2": addWord(scanner); break;
                case "3": removeWord(scanner); break;
                case "4": findWord(scanner); break;
                case "5": printAllDictionaries(); break;
                case "6": saveDictionaries(); break;
                case "7": System.exit(0);
                default: System.out.println("Неверный ввод");
            }
        }
    }
    /// доступные действия
    private static void printMenu()
    {
        System.out.println("\nМеню (" + currentDictionary.getName() + "):");
        System.out.println("1. Сменить словарь");
        System.out.println("2. Добавить слово");
        System.out.println("3. Удалить слово");
        System.out.println("4. Найти перевод");
        System.out.println("5. Показать все словари");
        System.out.println("6. Сохранить словари");
        System.out.println("7. Выйти");
        System.out.print("Выбор: ");
    }
    ///  меняем словарь
    private static void switchDictionary()
    {
        currentDictionary = (currentDictionary instanceof FourLettersDictionary)
                ? new FiveNumbersDictionary(dictionaryFile)
                : new FourLettersDictionary(dictionaryFile);
        currentDictionary.loadDictionary();
    }
    ///  добавляем слово
    private static void addWord(Scanner scanner)
    {
        System.out.print("Введите слово: ");
        String word = scanner.nextLine();
        System.out.print("Введите перевод: ");
        String translation = scanner.nextLine();
        currentDictionary.addWord(word, translation);
    }
    ///  удаляем слово
    private static void removeWord(Scanner scanner)
    {
        System.out.print("Введите слово для удаления: ");
        currentDictionary.removeWord(scanner.nextLine());
    }
    /// ищем слово
    private static void findWord(Scanner scanner)
    {
        System.out.print("Введите слово для поиска: ");
        String translation = currentDictionary.findWord(scanner.nextLine());
        System.out.println(translation != null ? "Перевод: " + translation : "Слово не найдено");
    }
    ///  выводим все словари
    private static void printAllDictionaries()
    {
        new FourLettersDictionary(dictionaryFile).printDictionary();
        new FiveNumbersDictionary(dictionaryFile).printDictionary();
    }
    ///  сохраняем словари
    private static void saveDictionaries()
    {
        currentDictionary.saveDictionary();
        Dictionary otherDict = (currentDictionary instanceof FourLettersDictionary)
                ? new FiveNumbersDictionary(dictionaryFile)
                : new FourLettersDictionary(dictionaryFile);
        otherDict.saveDictionary();
        System.out.println("Словари сохранены");
    }
}