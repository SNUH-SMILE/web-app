package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/config/context-*.xml")
public class IdGenerationServiceTest {

    @Autowired
    private IdGenerationService idGnrService;

    @Test
    public void doIdGeneration() throws FdlException {
        idGnrService.doIdGeneration();
    }

}