package com.excilys.formation.cdb.service.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;

/**
 * @author jirr
 *
 */
public class ComputerPage extends Page<Computer> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public ComputerPage(int size) throws ServiceException {
        super(size);
    }

    @Override
    public void setLastPageIndex() throws ServiceException {
        try {
            this.lastPageIndex = ComputerService.INSTANCE.countAllComputers(this.getKeywords()) / this.getSize();
        } catch (ServiceException e) {
            logger.error("Can't reach the database {}:", e.getMessage(), e);
            throw new ServiceException("Problem encounter in database.");
        }
    }

    @Override
    public void setContent(int offset) throws ServiceException {
        this.content = ComputerService.INSTANCE.subListComputer(this.getOffset(), this.getSize(), this.getKeywords(), this.getSortBy(), this.isAsc());
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
