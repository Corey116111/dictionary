import java.io.*;

class FourLettersDictionary extends Dictionary
{
    public FourLettersDictionary(File file)
    {
        super(file);
    }

    @Override
    protected boolean checkValidate(String key)
    {
        return key.matches("[a-zA-Z]{4}");
    }
}
