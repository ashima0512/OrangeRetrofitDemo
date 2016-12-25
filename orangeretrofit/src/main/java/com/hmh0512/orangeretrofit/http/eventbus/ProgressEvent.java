package com.hmh0512.orangeretrofit.http.eventbus;

/**
 * Created by Joshua on 2016/12/25.
 */

public class ProgressEvent {

    private long progress;
    private long total;

    public ProgressEvent(long progress, long total) {
        this.progress = progress;
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }
}
