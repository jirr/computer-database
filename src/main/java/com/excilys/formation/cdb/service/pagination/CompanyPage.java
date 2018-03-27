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
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setLastPageIndex() throws ServiceException {
        // TODO Auto-generated method stub
        this.lastPageIndex = (CompanyService.INSTANCE.countAllCompanies() / this.getSize());
    }

    @Override
    public void setContent(int offset) throws ServiceException {
        // TODO Auto-generated method stub
        this.content = CompanyService.INSTANCE.subListCompany(this.getOffset(), this.getSize());
    }
}
