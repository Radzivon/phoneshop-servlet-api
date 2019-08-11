package com.es.phoneshop.model.dos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DosProtectionServiceImplTest {
    @Mock
    private Map<String, AtomicLong> countMap;
    @Mock
    private Date startTime;
    @Mock
    private AtomicLong count;
    @InjectMocks
    DosProtectionServiceImpl dosProtectionService;
    private String ip = "ip";

    @Test
    public void getInstance() {
        DosProtectionServiceImpl temp = DosProtectionServiceImpl.getInstance();
        Assert.assertNotNull(temp);
    }

    @Test
    public void isAllowedTimeIsLessThanTenMinute() {
        when(startTime.getTime()).thenReturn(System.currentTimeMillis());
        dosProtectionService.isAllowed(ip);

        verify(countMap, never()).clear();
        verify(startTime, never()).setTime(anyLong());
    }

    @Test
    public void isAllowedTimeIsMoreThanTenMinute() {
        long time = 1L;
        when(startTime.getTime()).thenReturn(time);
        dosProtectionService.isAllowed(ip);

        verify(countMap).clear();
        verify(startTime).setTime(anyLong());
    }

    @Test
    public void isAllowedCountIsNull() {
        long time = 1L;
        when(startTime.getTime()).thenReturn(time);
        when(countMap.get(anyString())).thenReturn(null);
        dosProtectionService.isAllowed(ip);

        verify(countMap).put(anyString(), any());
    }
    @Test
    public void isAllowedCountIsNotNull() {
        long time = 1L;
        when(startTime.getTime()).thenReturn(time);
        when(countMap.get(anyString())).thenReturn(count);
        dosProtectionService.isAllowed(ip);

        verify(countMap, never()).put(anyString(), any());
    }

}
