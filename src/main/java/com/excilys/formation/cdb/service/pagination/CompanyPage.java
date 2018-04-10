package com.excilys.formation.cdb.service.pagination;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ServiceException;

/**
 * @author jirr
 *
 */
public class CompanyPage extends Page<Company> {

    public CompanyPage(int size) throws ServiceException {
        super(size);
    }

    @Override
    public void setLastPageIndex() throws ServiceException {
        this.lastPageIndex = (CompanyService.INSTANCE.countAllCompanies() / this.getSize());
    }

    @Override
    public void setContent(int offset) throws ServiceException {
        this.content = CompanyService.INSTANCE.subListCompany(this.getOffset(), this.getSize());
    }
}
