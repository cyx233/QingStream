package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

@Entity(tableName = "news")
@TypeConverters({StringConverter.class, AuthorConverter.class, EntitiesConverter.class, GeoInfoConverter.class, RelatedEventsConverter.class})
public class News {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String _id;

    private Author authors[];
    private String category;
    private String content;
    private String date;
    private String doi;
    private Entities entities[];
    private GeoInfo geoInfo[];
    @Ignore
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
    public String getCategory(){return category;}
    public String getContent(){return content;}
    public String getDate(){return date;}
    public String getDoi(){return doi;}
    public Entities[] getEntities(){return entities;}
    public GeoInfo[] geoInfo(){return geoInfo;}
//    public String getId(){return id;}
    public Double getInfluence(){return influence;}
    public String getLang(){return lang;}
    public String getPdf(){return pdf;}
    public String[] getRegionIds(){return regionIds;}
    public RelatedEvents[] getRelated_events(){return related_events;}
    public String getSeg_text(){return seg_text;}
    public String getSource(){return source;}
    public Long getTflag(){return tflag;}
    public String getTime(){return time;}
    public String getTitle(){return title;}
    public String getType(){return type;}
    public String[] getUrls(){return urls;}
    public Long getYear(){return year;}

    public void set_id(String _id){this._id = _id;}
    public void setAuthors(Author[] authors){this.authors = authors;}
    public void setCategory(String category){this.category = category;}
    public void setContent(String content){this.content = content;}
    public void setDate(String date){this.date = date;}
    public void setDoi(String doi){this.doi = doi;}
    public void setEntities(Entities entities[]){this.entities = entities;}
    public void setGeoInfo(GeoInfo geoInfo[]){this.geoInfo = geoInfo;}
//    public void setId(String id){this.id = id;}
    public void setInfluence(Double influence){this.influence = influence;}
    public void setLang(String lang){this.lang = lang;}
    public void setPdf(String pdf){this.pdf = pdf;}
    public void setRegionIds(String regionIds[]){this.regionIds = regionIds;}
    public void setRelated_events(RelatedEvents related_events[]){this.related_events = related_events;}
    public void setSeg_text(String seg_text){this.seg_text = seg_text;}
    public void setSource(String source){this.source = source;}
    public void setTflag(Long tflag){this.tflag = tflag;}
    public void setTime(String time){this.time = time;}
    public void setTitle(String title){this.title = title;}
    public void setType(String type){this.type = type;}
    public void setUrls(String urls[]){this.urls = urls;}
    public void setYear(Long year){this.year = year;}

    News(
            String _id, Author authors[], String category, String content, String date,
            String doi, Entities entities[], GeoInfo geoInfo[], String id, Double influence,
            String lang, String pdf, String[] regionIds, RelatedEvents related_events[], String seg_text,
            String source, Long tflag, String time, String type, String urls[], Long year
    ){
        this._id = _id;
        this.authors = authors;
        this.category = category;
        this.content = content;
        this.date = date;
        this.doi = doi;
        this.entities = entities;
        this.geoInfo = geoInfo;
        this.id = id;
        this.influence = influence;
        this.lang = lang;
        this.pdf = pdf;
        this.regionIds = regionIds;
        this.related_events = related_events;
        this.seg_text = seg_text;
        this.source = source;
        this.tflag = tflag;
        this.time = time;
        this.type = type;
        this.urls = urls;
        this.year = year;
        this.isRead = false;
        this.isFavorite = false;
    }

    private Boolean isRead = false;
    private Boolean isFavorite = false;

    public Boolean getIsRead(){return isRead;}
    public Boolean getIsFavorite(){return isFavorite;}
    public void setIsRead(Boolean isRead){this.isRead = isRead;}
    public void setIsFavorite(Boolean isFavorite){this.isFavorite = isFavorite;}

    public String[] getLabel(){
        String[] label = new String[entities.length];
        for(int i = 0; i < entities.length; i++){
            label[i] = entities[i].getLabel();
        }
        return label;
    }
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

class NewsApi{
    private News data[];
    class Pagination{
        private Long page;
        private Long size;
        private Long total;
    }
    private Pagination pagination;
    private Boolean status;
    public News[] getData(){return data;}
}