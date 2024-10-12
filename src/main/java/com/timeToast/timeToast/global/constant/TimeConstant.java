package com.timeToast.timeToast.global.constant;

public enum TimeConstant {

    ONE_HOUR(1000 * 60 * 60),
    ONE_DAY(1000 * 60 * 60 * 24 );

    private final long time;

    TimeConstant(long time) {
        this.time = time;
    }

    public long time(){
        return time;
    }
}
