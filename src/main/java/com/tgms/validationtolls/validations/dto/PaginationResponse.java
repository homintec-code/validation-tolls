package com.tgms.validationtolls.validations.dto;

import java.util.List;

public class PaginationResponse<T> {
    private List<T> data;
    private long total;
    private int totalPages;
    private int currentPage;
    private int limit;

    // Constructeur
    public PaginationResponse(List<T> data, long total, int totalPages, int currentPage, int limit) {
        this.data = data;
        this.total = total;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.limit = limit;
    }

    // Getters et Setters
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}