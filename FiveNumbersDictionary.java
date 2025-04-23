import java.io.*;

class FiveNumbersDictionary extends Dictionary
{
    public FiveNumbersDictionary(File file)
    {
        this.dictionaryFile = file;
    }

    @Override
    public String getName()
    {
        return "Цифровой словарь (5 цифр)";
    }
    ///  ключ должен быть строго из 5 цифр
    @Override
    protected boolean validateWord(String word)
    {
        return word.matches("\\d{5}");
    }

    @Override
    public void addWord(String word, String translation)
    {
        if (validateWord(word) && isCyrillic(translation))
        {
            keys.add(word);
            values.add(translation);
            System.out.println("Слово добавлено");
        }
        else
        {
            System.out.println("Ошибка: слово должно содержать 5 цифр, перевод - кириллицу");
        }
    }
}