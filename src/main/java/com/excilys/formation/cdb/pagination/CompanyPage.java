package com.excilys.formation.cdb.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ServiceException;

/**
 * @author jirr
 *
 */
public class CompanyPage extends Page<Company> {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public CompanyPage(int size) {
        super(size);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setLastPageIndex() {
        // TODO Auto-generated method stub
        try {
            this.lastPageIndex = (CompanyService.INSTANCE.countAllCompanies() / this.getSize());
        } catch (ServiceException e) {
            logger.error("Can't reach the database: {}", e.getMessage(), e);
        }
    }

    @Override
    public void setContent(int offset) {
        // TODO Auto-generated method stub
        try {
            this.content = CompanyService.INSTANCE.subListCompany(this.getOffset(), this.getSize());
        } catch (ServiceException e) {
            logger.error("Can't reach the database: {}", e.getMessage(), e);
        }
    }
}
