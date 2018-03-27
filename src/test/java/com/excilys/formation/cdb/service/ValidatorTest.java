package com.excilys.formation.cdb.service;


import static org.junit.Assert.assertTrue;
import java.time.LocalDate;

import org.junit.Test;


public class ValidatorTest {

    Validator validator = Validator.INSTANCE;

    @Test
    public void testDatesValidation() throws Exception {
        validator.datesValidation(LocalDate.of(1993, 8, 2), LocalDate.of(1992, 7, 1));
        assertTrue(true);
    }

    @Test(expected= NullPointerException.class)
    public void testDatesValidationNull() throws Exception {
        validator.datesValidation(null, LocalDate.of(1992, 7, 1));
        assertTrue(true);
    }

    @Test(expected= ServiceException.class)
    public void testDatesValidationWrong() throws Exception {
        validator.datesValidation(LocalDate.of(1992, 7, 1), LocalDate.of(1993, 8, 2));
        assertTrue(true);
    }
}
