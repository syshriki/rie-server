package com.syshriki.rieserver.models;

import java.util.List;

public class GetNewsResponse{
    Boolean hasNextPage; 
    Long nextCursor;
    List<NewsDto> news; 
    
    public GetNewsResponse(Boolean hasNextPage, Long nextCursor, List<NewsDto> news){
        this.hasNextPage = hasNextPage;
        this.nextCursor = nextCursor;
        this.news = news;
    }

    public Boolean hasNextPage(){
        return hasNextPage;
    }
    
    public Long nextCursor(){
        return nextCursor;
    }
    public List<NewsDto> news(){
        return news;
    } 
}
