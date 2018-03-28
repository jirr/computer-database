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
        // TODO Auto-generated method stub
        try {
            this.lastPageIndex = ComputerService.INSTANCE.countAllComputers() / this.getSize();
        } catch (ServiceException e) {
            logger.error("Can't reach the database {}:", e.getMessage(), e);
            throw new ServiceException("Problem encounter in database.");
        }
    }

    @Override
    public void setContent(int offset) throws ServiceException {
        // TODO Auto-generated method stub
        this.content = ComputerService.INSTANCE.subListComputer(this.getOffset(), this.getSize(), this.getKeywords());
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) throws ServiceException {
        this.keywords = keywords;
        this.setContent(this.getOffset());
        this.setLastPageIndex();
        super.currentPageIndex = 0;
    }
}
