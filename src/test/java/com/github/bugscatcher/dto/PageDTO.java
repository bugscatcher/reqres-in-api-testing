package com.github.bugscatcher.dto;

public class PageDTO {
    private String page;
    private String per_page;
    private String total;
    private String total_pages;

    public String getPage() {
        return page;
    }

    public PageDTO setPage(String page) {
        this.page = page;
        return this;
    }

    public String getPer_page() {
        return per_page;
    }

    public PageDTO setPer_page(String per_page) {
        this.per_page = per_page;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public PageDTO setTotal(String total) {
        this.total = total;
        return this;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public PageDTO setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
        return this;
    }
}
