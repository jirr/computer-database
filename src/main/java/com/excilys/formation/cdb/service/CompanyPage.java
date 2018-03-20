package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.model.Company;

/**
 * @author jirr
 *
 */
public class CompanyPage extends Page<Company> {

    public CompanyPage(int size) {
        super(size);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setLastPageIndex() {
        // TODO Auto-generated method stub
        this.lastPageIndex = (CompanyService.INSTANCE.countAllCompanies() / this.getSize());
    }

    @Override
    public void setContent(int offset) {
        // TODO Auto-generated method stub
        this.content = CompanyService.INSTANCE.subListCompany(this.getOffset(), this.getSize());
    }
}
