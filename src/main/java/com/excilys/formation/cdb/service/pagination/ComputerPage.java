package com.excilys.formation.cdb.service.pagination;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;

/**
 * @author jirr
 *
 */
public class ComputerPage extends Page<Computer> {

    private ComputerService computerService;

    public ComputerPage(int size, ComputerService computerService) throws ServiceException {
        super(size);
        this.computerService = computerService;
        this.setLastPageIndex();
        this.setContent(this.getOffset());
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }

    @Override
    public void setLastPageIndex() throws ServiceException {
        this.lastPageIndex = (computerService.countAllComputers(this.getKeywords())-1) / this.getSize();
    }

    @Override
    public void setContent(int offset) throws ServiceException {
        this.content = computerService.subListComputer(this.getOffset(), this.getSize(), this.getKeywords(), this.getSortBy(), this.isAsc());
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) throws ServiceException {
        super.keywords = keywords;
        this.setContent(this.getOffset());
        this.setLastPageIndex();
        super.currentPageIndex = 0;
    }
}
