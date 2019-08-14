package com.es.phoneshop.model.dos;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DosProtectionServiceImpl implements DosProtectionService {
    private static DosProtectionServiceImpl instance = new DosProtectionServiceImpl();

    private static long MAX_REQUEST_COUNT = 500;
    private static long TEN_MINUTES = 600000;
    private Map<String, AtomicLong> countMap;
    private Date startTime;

    private DosProtectionServiceImpl() {
        countMap = Collections.synchronizedMap(new HashMap<>());
    }

    public static DosProtectionServiceImpl getInstance() {
        return instance;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean isAllowed(String ip) {
        long timeAtTheMoment = System.currentTimeMillis();
        if (timeAtTheMoment - startTime.getTime() > TEN_MINUTES) {
            countMap.clear();
            startTime.setTime(timeAtTheMoment);
        }
        AtomicLong count = countMap.get(ip);
        if (count == null) {
            count = new AtomicLong(1);
            countMap.put(ip, count);
        }
        return count.incrementAndGet() < MAX_REQUEST_COUNT;
    }
}
