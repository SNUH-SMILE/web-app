package kr.co.hconnect.vo;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 페이징 처리 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaginationInfoVO implements Serializable {

    private static final long serialVersionUID = 4432541200527358649L;

    /**
     * 현재 페이지 번호
     */
    private int currentPageNo;
    /**
     * 한 페이지당 게시되는 게시물 건수
     */
    private int recordCountPerPage;
    /**
     * 페이지 리스트에 게시되는 페이지 건수
     */
    private int pageSize;
    /**
     * 전체 게시물 건수
     */
    private int totalRecordCount;

    /**
     * 페이지 개수
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int totalPageCount;
    /**
     * 페이지 리스트의 첫 페이지 번호
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int firstPageNoOnPageList;
    /**
     * 페이지 리스트의 마지막 페이지 번호
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int lastPageNoOnPageList;
    /**
     * 페이지 offset
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int offsetCount;
    /**
     * 이전 페이지 존재여부
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean prevPageExists;
    /**
     * 이전 페이징 존재여부
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean prevPaginationExists;
    /**
     * 다음 페이지 존재여부
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean nextPageExists;
    /**
     * 다음 페이징 존재여부
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean nextPaginationExists;
    /**
     * 이전 페이지 번호 - 존재하지 않을 경우 -1
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int prevPageNo;
    /**
     * 다음 페이지 번호 - 존재하지 않을 경우 -1
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int nextPageNo;

    /**
     * 정렬 기준 컬럼
     */
    @Getter(AccessLevel.NONE)
    private String orderBy = null;
    /**
     * 정렬 기준 ASC, DESC
     */
    private String orderDir = null;

    public int getTotalPageCount() {
        totalPageCount = ((getTotalRecordCount() - 1) / getRecordCountPerPage()) + 1;
        return totalPageCount;
    }

    public int getFirstPageNo() {
        return 1;
    }

    public int getLastPageNo() {
        return getTotalPageCount();
    }

    public int getFirstPageNoOnPageList() {
        firstPageNoOnPageList = ((getCurrentPageNo() - 1) / getPageSize()) * getPageSize() + 1;
        return firstPageNoOnPageList;
    }

    public int getLastPageNoOnPageList() {
        lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;
        if (lastPageNoOnPageList > getTotalPageCount()) {
            lastPageNoOnPageList = getTotalPageCount();
        }
        return lastPageNoOnPageList;
    }

    public int getOffsetCount() {
        offsetCount = (getCurrentPageNo() - 1) * getRecordCountPerPage();
        return offsetCount;
    }

    public boolean getPrevPageExists() {
        prevPageExists = (getCurrentPageNo() > 1);
        return prevPageExists;
    }

    public boolean getPrevPaginationExists() {
        prevPaginationExists = (getFirstPageNoOnPageList() > 1);
        return prevPaginationExists;
    }

    public boolean getNextPageExists() {
        nextPageExists = (getTotalPageCount() > getCurrentPageNo());
        return nextPageExists;
    }

    public boolean getNextPaginationExists() {
        nextPaginationExists = (getTotalPageCount() > getLastPageNoOnPageList());
        return nextPaginationExists;
    }

    public int getPrevPageNo() {
        prevPageNo = getPrevPageExists() ? (getCurrentPageNo() - 1) : -1;
        return prevPageNo;
    }

    public int getNextPageNo() {
        nextPageNo = getNextPageExists() ? (getCurrentPageNo() + 1) : -1;
        return nextPageNo;
    }

    /**
     * 정렬 컬럼
     * @return Convert underscore
     */
    public String getOrderBy() {
        if (StringUtils.isEmpty(orderBy)) {
            return "";
        } else {
            return orderBy.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toUpperCase();
        }
    }

}
