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

    public Boolean getHasNextPage(){
        return hasNextPage;
    }
    
    public Long getNextCursor(){
        return nextCursor;
    }
    public List<NewsDto> getNews(){
        return news;
    } 
}
