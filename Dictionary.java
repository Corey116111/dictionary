import java.io.*;
import java.util.*;

abstract class Dictionary
{
    protected List<String> keys = new ArrayList<>();
    protected List<String> values = new ArrayList<>();
    protected File dictionaryFile;
    ///  устанавливаем словарь
    public static File setDictionaryFile()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу словаря: ");
        return new File(scanner.nextLine());
    }
    ///  загружаем словарь
    public void loadDictionary()
    {
        try (Scanner fileScanner = new Scanner(dictionaryFile))
        {
            while (fileScanner.hasNextLine())
            {
                String[] pair = fileScanner.nextLine().split(" - ");
                if (pair.length == 2) {
                    keys.add(pair[0]);
                    values.add(pair[1]);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл не найден. Будет создан новый словарь.");
        }
    }
    ///  сохраняем словарь
    public void saveDictionary()
    {
        try (PrintWriter writer = new PrintWriter(dictionaryFile))
        {
            for (int i = 0; i < keys.size(); i++)
            {
                writer.println(keys.get(i) + " - " + values.get(i));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Ошибка при сохранении словаря.");
        }
    }
    /// удалить слово
    public void removeWord(String word)
    {
        int index = keys.indexOf(word);
        if (index != -1)
        {
            keys.remove(index);
            values.remove(index);
            System.out.println("Слово удалено");
        }
        else
        {
            System.out.println("Слово не найдено");
        }
    }
    ///  найти слово
    public String findWord(String word)
    {
        int index = keys.indexOf(word);
        return index != -1 ? values.get(index) : null;
    }
    ///  вывести словарь
    public void printDictionary()
    {
        System.out.println("\n" + getName() + ":");
        if (keys.isEmpty())
        {
            System.out.println("Словарь пуст");
        }
        else
        {
            for (int i = 0; i < keys.size(); i++)
            {
                System.out.println(keys.get(i) + " - " + values.get(i));
            }
        }
    }

    public abstract String getName();
    public abstract void addWord(String word, String translation);
}