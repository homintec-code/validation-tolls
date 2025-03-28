package com.tgms.validationtolls.validations.dto;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> content;
    private long totalCount;
    private int totalPages;
    private int currentPage;



    public void setContent(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }


    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalCount() {

        return totalCount;
    }

    public void setTotalPages(int totalPages) {

        this.totalPages = totalPages;

    }
    public int getTotalPages() {

        return totalPages;
    }
    public void setCurrentPage(int currentPage) {

        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    // Constructors, getters and setters
}
