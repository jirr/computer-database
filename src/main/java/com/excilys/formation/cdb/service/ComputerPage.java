package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.model.Computer;

/**
 * @author jirr
 *
 */
public class ComputerPage extends Page<Computer> {

    public ComputerPage(int size) {
        super(size);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setLastPageIndex() {
        // TODO Auto-generated method stub
        this.lastPageIndex = (ComputerService.INSTANCE.countAllComputers() / this.getSize());
    }

    @Override
    public void setContent(int offset) {
        // TODO Auto-generated method stub
        this.content = ComputerService.INSTANCE.subListComputer(this.getOffset(), this.getSize());
    }
}
