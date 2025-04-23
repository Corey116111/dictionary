import java.io.*;

class FourLettersDictionary extends Dictionary
{
    public FourLettersDictionary(File file)
    {
        this.dictionaryFile = file;
    }

    @Override
    public String getName()
    {
        return "Англоязычный словарь (4 буквы)";
    }
    ///  значение ключа строго из 4 букв
    @Override
    protected boolean validateWord(String word)
    {
        return word.matches("[a-zA-Z]{4}");
    }

    @Override
    public void addWord(String word, String translation)
    {
        if (validateWord(word) && isCyrillic(translation)) // ключ из 4 букв и перевод кириллица
        {
            keys.add(word);
            values.add(translation);
            System.out.println("Слово добавлено");
        }
        else
        {
            System.out.println("Ошибка: слово должно содержать 4 латинские буквы, перевод - кириллицу");
        }
    }
}