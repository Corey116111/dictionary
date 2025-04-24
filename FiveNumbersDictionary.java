import java.io.*;

class FiveNumbersDictionary extends Dictionary
{
    public FiveNumbersDictionary(File file)
    {
        super(file);
    }

    @Override
    protected boolean checkValidate(String key)
    {
        return key.matches("\\d{5}");
    }
}