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


    @Override
    public void addWord(String word, String translation)
    {

    }
}