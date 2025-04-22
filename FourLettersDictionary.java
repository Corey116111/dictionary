import java.io.*;

class FourLettersDictionary extends Dictionary
{
    public FourLettersDictionary(File file)
    {
        this.dictionaryFile = file;
    }

    @Override
    public String getName() {
        return "Англоязычный словарь (4 буквы)";
    }

    @Override
    public void addWord(String word, String translation)
    {
       
    }
}