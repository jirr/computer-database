package com.excilys.formation.cdb.service;

import java.util.List;

/**
 * @author jirr
 *
 * @param <T> Computer or Company
 */
public abstract class Page<T> {

    private int size;
    private int currentPageIndex;
    protected int lastPageIndex;
    protected List<T> content = null;

    /**
     * @param size Size of the page
     */
    public Page(int size) {
        this.currentPageIndex = 0;
        this.size = size;
        this.setLastPageIndex();
        this.setContent(this.getOffset());
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        int offset = (this.currentPageIndex == 0) ? 1 : this.currentPageIndex * this.size;
        return offset;
    }

    public abstract void setLastPageIndex();

    public int getLastPageIndex() {
        return this.lastPageIndex;
    }

    public abstract void setContent(int offset);

    public List<T> getContent() {
        return this.content;
    }
    
    public List<T> goToPage(int index) {
        if (index > 0 && index < this.lastPageIndex-1) {
            this.currentPageIndex = index;
        }
        this.setContent(this.getOffset());
        return this.content;
    }

    public List<T> previousPage() {
        if (this.currentPageIndex > 0) {
            this.currentPageIndex--;
        }
        this.setContent(this.getOffset());
        return this.content;
    }

    public List<T> nextPage() {
        if (this.currentPageIndex < this.lastPageIndex) {
            this.currentPageIndex++;
        }
        this.setContent(this.getOffset());
        return this.content;
    }

    public List<T> firstPage() {
        this.currentPageIndex = 0;
        this.setContent(this.getOffset());
        return this.content;
    }

    public List<T> lastPage() {
        this.currentPageIndex = this.lastPageIndex;
        this.setContent(this.getOffset());
        return this.content;
    }
}
