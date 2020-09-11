package com.java.tanghao;

import androidx.room.*;
import com.google.gson.Gson;

class StringConverter {
    @TypeConverter
    public static String[] fromTimestamp(String data) {
        if(data == null || data.equals(""))return new String[0];
        return data.split("#QingSteamSplit#");
    }

    @TypeConverter
    public static String toTimestamp(String[] data){
        if(data == null || data.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(String s:data){
            sb.append(s);
            sb.append("#QingSteamSplit#");
        }
        return sb.toString();
    }
}

class Integer2DConverter{
    @TypeConverter
    public static Integer[][] fromTimestamp(String data) {
        if(data == null || data.equals(""))return new Integer[0][];
        String[] ss = data.split("#QingSteamSplit#");
        Integer[][] result = new Integer[ss.length][];
        for(int i = 0; i < ss.length; i++){
            String[] sss = ss[i].split("#QingSteam2DSplit#");
            result[i] = new Integer[sss.length];
            for(int j = 0; j < sss.length; j++){
                result[i][j] = Integer.valueOf(sss[j]);
            }
        }
        return result;
    }

    @TypeConverter
    public static String toTimestamp(Integer[][] data){
        if(data == null || data.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[i].length; j++)
            {
                sb.append(data[i][j].toString());
                sb.append("#QingSteam2DSplit#");
            }
            sb.append("#QingSteamSplit#");
        }
        return sb.toString();
    }
}

class AuthorConverter{
    @TypeConverter
    public static Author[] fromTimestamp(String data) {
        if(data == null || data.equals(""))return new Author[0];
        String ss[] = data.split("#QingSteamSplit#");
        Author authors[] = new Author[ss.length];
        Gson gson = new Gson();
        for(int i = 0; i < ss.length; i++){
            authors[i] = gson.fromJson(ss[i], Author.class);
        }
        return authors;
    }

    @TypeConverter
    public static String toTimestamp(Author[] data){
        if(data == null || data.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        for(Author s:data){
            sb.append(gson.toJson(s));
            sb.append("#QingSteamSplit#");
        }
        return sb.toString();
    }
}

class EntitiesConverter{
    @TypeConverter
    public static Entities[] fromTimestamp(String data) {
        if(data == null || data.equals(""))return new Entities[0];
        String ss[] = data.split("#QingSteamSplit#");
        Entities entities[] = new Entities[ss.length];
        Gson gson = new Gson();
        for(int i = 0; i < ss.length; i++){
            entities[i] = gson.fromJson(ss[i], Entities.class);
        }
        return entities;
    }

    @TypeConverter
    public static String toTimestamp(Entities[] data){
        if(data == null || data.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        for(Entities s:data){
            sb.append(gson.toJson(s));
            sb.append("#QingSteamSplit#");
        }
        return sb.toString();
    }
}

class GeoInfoConverter{
    @TypeConverter
    public static GeoInfo[] fromTimestamp(String data) {
        if(data == null || data.equals(""))return new GeoInfo[0];
        String ss[] = data.split("#QingSteamSplit#");
        GeoInfo geoInfos[] = new GeoInfo[ss.length];
        Gson gson = new Gson();
        for(int i = 0; i < ss.length; i++){
            geoInfos[i] = gson.fromJson(ss[i], GeoInfo.class);
        }
        return geoInfos;
    }

    @TypeConverter
    public static String toTimestamp(GeoInfo[] data){
        if(data == null || data.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        for(GeoInfo s:data){
            sb.append(gson.toJson(s));
            sb.append("#QingSteamSplit#");
        }
        return sb.toString();
    }
}

class RelatedEventsConverter{
    @TypeConverter
    public static RelatedEvents[] fromTimestamp(String data) {
        if(data == null || data.equals(""))return new RelatedEvents[0];
        String ss[] = data.split("#QingSteamSplit#");
        RelatedEvents relatedEvents[] = new RelatedEvents[ss.length];
        Gson gson = new Gson();
        for(int i = 0; i < ss.length; i++){
            relatedEvents[i] = gson.fromJson(ss[i], RelatedEvents.class);
        }
        return relatedEvents;
    }

    @TypeConverter
    public static String toTimestamp(RelatedEvents[] data){
        if(data == null || data.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        for(RelatedEvents s:data){
            sb.append(gson.toJson(s));
            sb.append("#QingSteamSplit#");
        }
        return sb.toString();
    }
}

class DoubleConverter{
    @TypeConverter
    public static Double[] fromTimestamp(String data) {
        if(data == null || data.equals(""))return new Double[0];
        String[] ss = data.split("#QingSteamSplit#");
        Double[] result = new Double[ss.length];
        for(int i = 0; i < ss.length; i++){
            result[i] = Double.valueOf(ss[i]);
        }
        return result;
    }

    @TypeConverter
    public static String toTimestamp(Double[] data){
        if(data == null || data.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < data.length; i++){
            sb.append(data[i].toString());
            sb.append("#QingSteamSplit#");
        }
        return sb.toString();
    }
}

class IndiceConverter{
    @TypeConverter
    public static Indice fromTimestamp(String data) {
        if(data == null || data.equals(""))return new Indice();
        Indice indices = new Indice();
        Gson gson = new Gson();
        indices = gson.fromJson(data, Indice.class);
        return indices;
    }

    @TypeConverter
    public static String toTimestamp(Indice data){
        if(data == null){
            return "";
        }
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}

class ProfileConverter{
    @TypeConverter
    public static Profile fromTimestamp(String data) {
        if(data == null || data.equals(""))return new Profile();
        Profile profile = new Profile();
        Gson gson = new Gson();
        profile = gson.fromJson(data, Profile.class);
        return profile;
    }

    @TypeConverter
    public static String toTimestamp(Profile data){
        if(data == null){
            return "";
        }
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}