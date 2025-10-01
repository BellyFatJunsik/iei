package kr.co.iei.common.util;

import kr.co.iei.common.dto.PageInfo;

public class PageInfoUtil {
    
    public static PageInfo createPageInfo(int currentPage, int totalCount, int pageSize) {
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        boolean hasNext = currentPage < totalPage;
        boolean hasPrev = currentPage > 1;
        
        return PageInfo.builder()
                .currentPage(currentPage)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSize(pageSize)
                .hasNext(hasNext)
                .hasPrev(hasPrev)
                .build();
    }
    
    public static int getOffset(int currentPage, int pageSize) {
        return (currentPage - 1) * pageSize;
    }
}
