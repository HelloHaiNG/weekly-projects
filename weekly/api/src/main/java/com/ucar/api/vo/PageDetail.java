package com.ucar.api.vo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 分页封装
 *
 * @author chuang.ma
 * @since 2018年08月06日
 */
public class PageDetail<T> {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private List<T> list;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int[] navigatepageNums;
    private int navigateFirstPage;
    private int navigateLastPage;

    public PageDetail() {
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
    }

    public PageDetail(List<T> list, int pageNum, int pageSize, long total) {
        this(list, 8, pageNum, pageSize, total);
    }

    public PageDetail(List<T> list, int navigatePages, int pageNum, int pageSize, long total) {
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
        if (list instanceof Collection) {
            this.pageNum = pageNum > 0 ? pageNum : 1;
            this.pageSize = pageSize > 0 ? pageSize : 10;
            this.pages = this.pageSize > 0 ? (int) Math.ceil((double) total / (double) pageSize) : 0;
            this.list = list;
            this.size = list.size();
            this.total = total;
            this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
            int end = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);
            this.endRow = end > (int) total ? (int) total : end;
            this.navigatePages = navigatePages;
            this.calcNavigatepageNums();
            this.calcPage();
            this.judgePageBoudary();
        }
    }

    private void calcNavigatepageNums() {
        if (this.pages <= this.navigatePages) {
            this.navigatepageNums = new int[this.pages];
            for (int i = 0; i < this.pages; ++i) {
                this.navigatepageNums[i] = i + 1;
            }
        } else {
            this.navigatepageNums = new int[this.navigatePages];
            int startNum = this.pageNum - this.navigatePages / 2;
            int endNum = this.pageNum + this.navigatePages / 2;
            this.calcNavigatepagePart(startNum, endNum);
        }

    }

    private void calcNavigatepagePart(int startNum, int endNum) {
        if (startNum < 1) {
            startNum = 1;
            for (int i = 0; i < this.navigatePages; ++i) {
                this.navigatepageNums[i] = i++;
            }
        } else if (endNum > this.pages) {
            endNum = this.pages;
            for (int i = this.navigatePages - 1; i >= 0; --i) {
                this.navigatepageNums[i] = endNum--;
            }
        } else {
            for (int i = 0; i < this.navigatePages; ++i) {
                this.navigatepageNums[i] = i++;
            }
        }
    }

    private void calcPage() {
        if (this.navigatepageNums != null && this.navigatepageNums.length > 0) {
            this.navigateFirstPage = this.navigatepageNums[0];
            this.navigateLastPage = this.navigatepageNums[this.navigatepageNums.length - 1];
            if (this.pageNum >= 1) {
                this.prePage = (this.pageNum - 1) > 0 ? (this.pageNum - 1) : 1;
            }

            if (this.pageNum <= this.pages) {
                this.nextPage = (this.pageNum + 1) > this.pages ? this.pages : (this.pageNum + 1);
            }
        }

    }

    private void judgePageBoudary() {
        this.isFirstPage = this.pageNum == 1;
        this.isLastPage = this.pageNum == this.pages || this.pages == 0;
        this.hasPreviousPage = this.pageNum > 1;
        this.hasNextPage = this.pageNum < this.pages;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int getFirstPage() {
        return this.navigateFirstPage;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setFirstPage(int firstPage) {
        this.navigateFirstPage = firstPage;
    }

    public int getPrePage() {
        return this.prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int getLastPage() {
        return this.navigateLastPage;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setLastPage(int lastPage) {
        this.navigateLastPage = lastPage;
    }

    public boolean isIsFirstPage() {
        return this.isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return this.isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return this.navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return this.navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getNavigateFirstPage() {
        return this.navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return this.navigateLastPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageDetail{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", size=").append(size);
        sb.append(", startRow=").append(startRow);
        sb.append(", endRow=").append(endRow);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", list=").append(list);
        sb.append(", prePage=").append(prePage);
        sb.append(", nextPage=").append(nextPage);
        sb.append(", isFirstPage=").append(isFirstPage);
        sb.append(", isLastPage=").append(isLastPage);
        sb.append(", hasPreviousPage=").append(hasPreviousPage);
        sb.append(", hasNextPage=").append(hasNextPage);
        sb.append(", navigatePages=").append(navigatePages);
        sb.append(", navigatepageNums=").append(Arrays.toString(navigatepageNums));
        sb.append(", navigateFirstPage=").append(navigateFirstPage);
        sb.append(", navigateLastPage=").append(navigateLastPage);
        sb.append('}');
        return sb.toString();
    }
}
