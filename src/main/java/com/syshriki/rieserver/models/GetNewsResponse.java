package com.syshriki.rieserver.models;

import java.util.List;

public record GetNewsResponse(Boolean hasNextPage, Long nextCursor, List<News> news) {
    
}
