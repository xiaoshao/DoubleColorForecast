package com.prepare.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by zwshao on 6/4/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReadDataTest {

    @Test
    public void test() throws Exception {
        ReadData readData = new ReadData();

        readData.readAllData();

    }
}