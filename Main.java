import java.io.*;
import java.util.*;

public class Main
{ // C:\Users\Vitaminez\IdeaProjects\dictionary123\numberDict.txt
    private enum DictionaryType
    {
        FOUR_LETTERS,
        FIVE_NUMBERS
    }

    private static DictionaryType currentType;
    private static IDictionary currentDictionary;
    private static File dictionaryFile;

    public static void main(String[] args)
    {
        initialize();
        runMenu();
    }
    /// точка входа в программу
    private static void initialize()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу словаря: ");
        String path = scanner.nextLine().trim();
        dictionaryFile = new File(path);

        if (!dictionaryFile.exists())
        {
            System.out.println("Файл не найден. Создаем новый на рабочем столе.");
            String desktopPath = System.getProperty("user.home") + "/Desktop/dictionary.txt";
            dictionaryFile = new File(desktopPath);
            try
            {
                dictionaryFile.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
                System.exit(1);
            }
        }

        selectDictionaryType();
        loadCurrentDictionary();
    }

    private static void selectDictionaryType()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите словарь:");
        System.out.println("1. 4-буквенный");
        System.out.println("2. 5-цифровой");

        int choice;
        while (true)
        {
            try
            {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1 || choice == 2)
                {
                    currentType = (choice == 1) ? DictionaryType.FOUR_LETTERS : DictionaryType.FIVE_NUMBERS;
                    break;
                }
                System.out.println("Введите 1 или 2");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Некорректный ввод. Введите число.");
            }
        }
    }
    /// загружаем текущий словарь
    private static void loadCurrentDictionary()
    {
        currentDictionary = (currentType == DictionaryType.FOUR_LETTERS) ? new FourLettersDictionary(dictionaryFile)  : new FiveNumbersDictionary(dictionaryFile); // создаем экземпляр словаря в зависимости от типа
    }
    /// юзабельное меню
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
                case "6": System.exit(0);
                default: System.out.println("Неверный ввод");
            }
        }
    }
    ///  текстовое меню
    private static void printMenu()
    {
        System.out.println("\nМеню (" + (currentType) + " словарь):");
        System.out.println("1. Сменить словарь");
        System.out.println("2. Добавить слово");
        System.out.println("3. Удалить слово");
        System.out.println("4. Найти перевод");
        System.out.println("5. Показать текущий словарь");
        System.out.println("6. Выйти");
        System.out.print("Выбор: ");
    }
    ///  меняем словарь
    private static void switchDictionary()
    {
        selectDictionaryType();
        loadCurrentDictionary();
        System.out.println("Текущий словарь: " + (currentType));
    }
    ///  добавляем слово в словарь
    private static void addWord(Scanner scanner)
    {
        System.out.print("Введите ключ: ");
        String word = scanner.nextLine();
        System.out.print("Введите перевод: ");
        String translation = scanner.nextLine();

        if (currentDictionary.addInDictionary(word, translation))
        {
            System.out.println("Слово добавлено, словарь обновлен");
        }
        else
        {
            System.out.println("Ошибка: неверный формат ключа или перевода");
        }
    }
    /// удаляем слово по ключу
    private static void removeWord(Scanner scanner)
    {
        System.out.print("Введите ключ для удаления: ");
        if (currentDictionary.removeByKey(scanner.nextLine()))
        {
            System.out.println("Слово удалено, словарь обновлен");
        }
        else
        {
            System.out.println("Ключ не найден");
        }
    }
    ///  ищем слово в словаре по ключу
    private static void findWord(Scanner scanner)
    {
        System.out.print("Введите ключ для поиска: ");
        String result = currentDictionary.findByKey(scanner.nextLine());
        System.out.println(result != null ? "Перевод: " + result : "Ключ не найден");
    }
    /// выводим запись текущего словаря
    private static void printAllDictionaries()
    {
        System.out.println("Записи текущего словаря (" + currentType + ")");
        currentDictionary.printAll();
    }
}