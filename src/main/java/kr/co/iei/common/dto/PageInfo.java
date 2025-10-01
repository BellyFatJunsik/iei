package kr.co.iei.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int currentPage;
    private int totalPage;
    private int totalCount;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrev;
}
