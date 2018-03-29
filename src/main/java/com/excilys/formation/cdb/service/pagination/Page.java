package com.excilys.formation.cdb.service.pagination;

import java.util.List;

import com.excilys.formation.cdb.service.ServiceException;

/**
 * @author jirr
 *
 * @param <T> Computer or Company
 */
public abstract class Page<T> {

    private int size;
    protected int currentPageIndex;
    protected int lastPageIndex;
    protected List<T> content = null;
    protected String keywords;

    /**
     * @param size Size of the page
     * @throws ServiceException 
     */
    public Page(int size) throws ServiceException {
        this.currentPageIndex = 0;
        this.keywords = "";
        this.size = size;
        this.setContent(this.getOffset());
        this.setLastPageIndex();
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) throws ServiceException {
        this.size = size;
        this.setContent(this.getOffset());
        this.setLastPageIndex();
        this.currentPageIndex = 0;
    }

    public int getOffset() {
        int offset = (this.currentPageIndex == 0) ? 0 : this.currentPageIndex * this.size;
        return offset;
    }

    public abstract void setLastPageIndex() throws ServiceException;

    public int getLastPageIndex() {
        return this.lastPageIndex;
    }

    public abstract void setContent(int offset) throws ServiceException;

    public List<T> getContent() {
        return this.content;
    }

    public List<T> goToPage(int index) throws ServiceException {
        if (index >= 0 && index < this.lastPageIndex + 1) {
            this.currentPageIndex = index;
        }
        this.setContent(this.getOffset());
        return this.content;
    }

    public List<T> firstPage() throws ServiceException {
        this.currentPageIndex = 0;
        this.setContent(this.getOffset());
        return this.content;
    }

    public List<T> lastPage() throws ServiceException {
        this.currentPageIndex = this.lastPageIndex;
        this.setContent(this.getOffset());
        return this.content;
    }
}
