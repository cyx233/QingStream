package com.java.tanghao;

import androidx.room.*;
import com.google.gson.Gson;

@Entity(tableName = "news")
@TypeConverters({StringConverter.class, AuthorConverter.class, EntitiesConverter.class, GeoInfoConverter.class, RelatedEventsConverter.class})
public class News {
    @PrimaryKey
    private String _id;

    private Author authors[];
    private String category;
    private String content;
    private String date;
    private String doi;
    private Entities entities[];
    private GeoInfo geoInfo[];
    private String id;
    private Double influence;
    private String lang;
    private String pdf;
    private String[] regionIds;
    private RelatedEvents related_events[];
    private String seg_text;
    private String source;
    private Long tflag;
    private String time;
    private String title;
    private String type;
    private String urls[];
    private Long year;

    public String get_id(){return _id;}
    public Author[] getAuthors(){return authors;}
    public String getContent(){return content;}
    public String getDate(){return date;}
    public String getDoi(){return doi;}
    public Entities[] getEntities(){return entities;}
    public GeoInfo[] geoInfo(){return geoInfo;}
    public String getId(){return id;}
    public Double getInfluence(){return influence;}
    public String getLang(){return lang;}
    public String getPdf(){return pdf;}
    public String[] getRegionIds(){return regionIds;}
    public RelatedEvents[] getRekatedEvents(){return related_events;}
    public String getSeg_text(){return seg_text;}
    public String getSource(){return source;}
    public Long getTflag(){return tflag;}
    public String getTime(){return time;}
    public String getTitle(){return title;}
    public String getType(){return type;}
    public String[] getUrls(){return urls;}
    public Long getYear(){return year;}
}

class Author{
   private String name;
   public String getName(){return name;}

   @Override
    public String toString(){
        return name;
   }

   Author(String name){
       this.name = name;
   }
}

class Entities{
    private String label;
    private String url;
    public String getLabel(){return label;}
    public String getUrl(){return url;}

    @Override
    public String toString(){
        String es = "#EntitiesSplit#";
        return label+es+url;
    }

    Entities(String label, String url){
        this.label = label;
        this.url = url;
    }
}

class GeoInfo{
    private String geoName;
    private String latitude;
    private String longtitude;
    private String originText;
    public String getGeoName(){return geoName;}
    public String getLatitude(){return latitude;}
    public String getLongtitude(){return longtitude;}
    public String getOriginText(){return originText;}

    @Override
    public String toString(){
        String gs = "#GeoInfoSplit#";
        return geoName+gs+latitude+gs+longtitude+gs+originText;
    }

    GeoInfo(String geoName, String latitude, String longtitude, String originText){
        this.geoName = geoName;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.originText = originText;
    }
}

class RelatedEvents{
    private String id;
    private Double score;
    public String getId(){return id;}
    public Double getScore(){return score;}

    @Override
    public String toString(){
        String rs = "#RelatedEventsSplit#";
        return id+rs+score.toString();
    }

    RelatedEvents(String id, Double score){
        this.id = id;
        this.score = score;
    }
}