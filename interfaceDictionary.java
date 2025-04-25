interface IDictionary
{
    void printAll();
    boolean addInDictionary(String wordKey, String translationWord);
    boolean removeByKey(String keyWord);
    String findByKey(String wordKey);
}