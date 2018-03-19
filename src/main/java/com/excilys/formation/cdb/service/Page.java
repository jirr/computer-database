package com.excilys.formation.cdb.service;

import java.util.List;

public abstract class Page<T> {

    private int size = 20;
    private int pageNumber;
    protected List<T> content = null;

    /**
     * @param pageNumber the number of the current page
     * @param content the content of the page
     */
    public Page(int pageNumber, List<T> content) {
        this.setPageNumber(pageNumber);
        this.content = content;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return the content
     */
    public List<T> getContent() {
        return this.content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return (this.size * this.pageNumber);
    }
}
