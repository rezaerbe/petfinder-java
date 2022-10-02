package com.erbeandroid.petfinder.core.network.model;

import com.squareup.moshi.Json;

public class PaginationResponse {
    @Json(name = "count_per_page")
    private final Integer countPerPage;
    @Json(name = "total_count")
    private final Integer totalCount;
    @Json(name = "current_page")
    private final Integer currentPage;
    @Json(name = "total_pages")
    private final Integer totalPages;

    public PaginationResponse(
        Integer countPerPage,
        Integer totalCount,
        Integer currentPage,
        Integer totalPages
    ) {
        this.countPerPage = countPerPage;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public Integer getCountPerPage() {
        return countPerPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}