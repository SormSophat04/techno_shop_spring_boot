package com.springboot.project.techno_shop.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageUtil {
    int DEFAULT_PAGE_NUMBER = 1;
    int DEFAULT_PAGE_LIMIT = 4;
    String PAGE_NUMBER = "_page";
    String PAGE_LIMIT = "_limit";

    static Pageable getPageable(int pageNumber, int pageLimit) {
        if (pageNumber < 1){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageLimit < 1){
            pageLimit = DEFAULT_PAGE_LIMIT;
        }

        return PageRequest.of(pageNumber-1, pageLimit);
    }
}
