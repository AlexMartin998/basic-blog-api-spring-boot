package com.alex.blog.dto;

import java.util.List;

public class PublicationResponse {

    private List<PublicationDTO> content;
    private int pageNumber;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean lastOne;

    

    public PublicationResponse() {}

    public PublicationResponse(List<PublicationDTO> content, int pageNumber, int size, long totalElements,
            int totalPages, boolean lastOne) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.lastOne = lastOne;
    }



    public List<PublicationDTO> getContent() {
        return content;
    }

    public void setContent(List<PublicationDTO> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLastOne() {
        return lastOne;
    }

    public void setLastOne(boolean lastOne) {
        this.lastOne = lastOne;
    }

    

    
}
