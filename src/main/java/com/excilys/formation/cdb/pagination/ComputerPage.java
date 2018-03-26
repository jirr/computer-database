package com.excilys.formation.cdb.pagination;

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

    public ComputerPage(int size) {
        super(size);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setLastPageIndex() {
        // TODO Auto-generated method stub
        try {
            this.lastPageIndex = ComputerService.INSTANCE.countAllComputers() / this.getSize();
        } catch (ServiceException e) {
            logger.error("Can't reach the database {}:", e.getMessage(), e);
        }
    }

    @Override
    public void setContent(int offset) {
        // TODO Auto-generated method stub
        try {
            this.content = ComputerService.INSTANCE.subListComputer(this.getOffset(), this.getSize());
        } catch (ServiceException e) {
            logger.error("Can't reach the database {}:", e.getMessage(), e);
        }
    }
}
