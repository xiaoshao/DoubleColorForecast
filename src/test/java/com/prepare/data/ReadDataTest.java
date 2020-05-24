package com.prepare.data;

import com.data.Record;
import com.google.common.collect.Lists;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by zwshao on 6/4/2018.
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.net.ssl.*"})
public class ReadDataTest {

    @Test
    public void should_read_data_from_sina_correctly() throws Exception {
        // <i>10</i><i>11</i><i>12</i><i>13</i><i>26</i><i>28</i><i class='blue'>11</i>
        ReadData readData = new ReadData();

        Method readDataMethod = PowerMockito.method(ReadData.class, "getRecord", String.class);

        Optional<Record> recordOpt = (Optional<Record>) readDataMethod.invoke(readData, "2003001");

        List<Integer> reds = Lists.newArrayList(10, 11, 12, 13, 26, 28);
        Record expectedData = new Record("2003001", reds, 11);

        assertThat(recordOpt.get(), Is.is(expectedData));
    }



    @Test
    public void should_parse_the_record_correctly() throws InvocationTargetException, IllegalAccessException {
        ReadData readData = new ReadData();
        String inputData = "<i>18</i><i>19</i><i>21</i><i>26</i><i>27</i><i>33</i><i class='blue'>16</i>";
        Method parseRecord = PowerMockito.method(ReadData.class, "parseOpenResult", String.class, String.class);

        Record record = (Record) parseRecord.invoke(readData, "parseOpenResult", inputData);

        List<Integer> reds = Lists.newArrayList(18, 19, 21, 26, 27, 33);
        Record expectedData = new Record("", reds, 16);
        assertThat(record, Is.is(expectedData));
    }
}