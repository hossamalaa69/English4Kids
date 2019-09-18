package com.example.android.english4kids;

public class Word {
    private String DefaultWord,MiwokWord;
    private int ImageID;
    private int AudioID;

    public Word(String x,String y,int z)
    {
        DefaultWord=x;
        MiwokWord=y;
        AudioID=z;
    }

    public Word(String x,String y,int z,int w)
    {
        DefaultWord=x;
        MiwokWord=y;
        ImageID=z;
        AudioID=w;
    }
    public String getDefault()
    {
        return DefaultWord;
    }
    public String getMiwok()
    {
        return MiwokWord;
    }
    public int getImageID()
    {
        return ImageID;
    }
    public int getSong(){ return AudioID; }
}
