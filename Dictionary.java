import java.io.*;
import java.nio.file.Files;
import java.util.*;

abstract class Dictionary implements IDictionary
{
    private final File file; // мы не меняем файл после инициализации
    protected Map<String, String> entries = new HashMap<>(); // храним пары ключ-значение

    public Dictionary(File file)
    {
        this.file = file;
        loadFromFile();
    }
    /// проверяем, подходит ли ключ под требования
    protected abstract boolean checkValidate(String key);
    /// значение всегда должно быть на русском языке
    protected boolean validateRussian(String translation)
    {
        return translation.matches("[а-яА-ЯёЁ\\s]+");
    }
    /// загружаем пары ключ-значение из файла
    private void loadFromFile()
    {
        entries.clear();
        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                String[] parts = scanner.nextLine().split(" - ");
                if (parts.length == 2 && checkValidate(parts[0]))
                {
                    entries.put(parts[0], parts[1]);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл не найден: " + e.getMessage());
        }
    }
    ///  выводим выбранный словарь
    @Override
    public void printAll()
    {
        if (entries.isEmpty())
        {
            System.out.println("Словарь пуст");
        }
        else
        {
            int count = 1;
            for (Map.Entry<String, String> entry : entries.entrySet()) // пробегаемся по парам
            {
                System.out.println(count++ + ". " + entry.getKey() + " - " + entry.getValue());
            }
        }
    }
    ///  добавляем ключ и значение в словарь
    @Override
    public boolean addInDictionary(String wordKey, String translationWord)
    {
        if (checkValidate(wordKey) && validateRussian(translationWord))
        {
            entries.put(wordKey, translationWord);
            saveToFile();
            return true;
        }
        return false;
    }
    ///  удаляем ключ и значение из словаря
    @Override
    public boolean removeByKey(String keyWord) {
        if (entries.remove(keyWord) != null)
        {
            try
            {
                List<String> lines = Files.readAllLines(file.toPath());

                PrintWriter writer = new PrintWriter(file);
                for (String line : lines) // перезаписываем файл без удаленной строки
                {
                    String[] parts = line.split(" - ");
                    if (parts.length != 2 || !parts[0].equals(keyWord))
                    {
                        writer.println(line);
                    }
                }
                writer.close();

                return true;
            }
            catch (IOException e)
            {
                System.out.println("Ошибка при обновлении файла: " + e.getMessage());
            }
        }
        return false;
    }
    ///  ищем значение по ключу
    @Override
    public String findByKey(String wordKey)
    {
        return entries.get(wordKey);
    }
    ///  сохраняем новые данные в файл
    private void saveToFile()
    {
        Map<String, String> allEntries = new HashMap<>();
        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                String[] parts = scanner.nextLine().split(" - ");
                if (parts.length == 2)
                {
                    allEntries.put(parts[0], parts[1]);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл не найден: " + e.getMessage());
        }

        for (Map.Entry<String, String> entry : entries.entrySet()) // обновляем записи текущего словаря
        {
            allEntries.put(entry.getKey(), entry.getValue());
        }

        try (PrintWriter writer = new PrintWriter(file)) // записываем обратно в файл
        {
            for (Map.Entry<String, String> entry : allEntries.entrySet())
            {
                writer.println(entry.getKey() + " - " + entry.getValue());
            }
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }
}
