package com.zxf.demo;

import com.zxf.util.StringUtil;

import java.util.*;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/12/29
 */
public class UrlCount {
    private String url;
    private Long count;


    private long maxDuration = -1;
    private long minDuration = -1;
    private long eveDuration = -1;

    private List<Duration> durationList = new ArrayList<>();
    private Map<String, Duration> durationMap = new HashMap<>();

    public Duration getDuration(String trackNum){
        if(null == durationMap.get(trackNum)){
            Duration d = new Duration();
            d.setTrackNum(trackNum);
            durationMap.put(trackNum, d);
            durationList.add(d);
        }
        return durationMap.get(trackNum);
    }

    public void cal(){
        long all = 0;
        for(Duration d : durationList){
            long duration = d.getDuration();
            if(duration != -1){
                if(maxDuration == -1 || duration > maxDuration){
                    maxDuration = duration;
                }
                if(minDuration == -1 || duration < minDuration){
                    minDuration = duration;
                }
                all += duration;
            }
        }
        eveDuration = all/durationList.size();
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public long getMinDuration() {
        return minDuration;
    }

    public long getEveDuration() {
        return eveDuration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


    class Duration{
        private String trackNum;
        private Date startTime;
        private Date endTime;

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getTrackNum() {
            return trackNum;
        }

        public void setTrackNum(String trackNum) {
            this.trackNum = trackNum;
        }

        @Override
        public boolean equals(Object anObject){
            if(null == anObject){
                return false;
            }
            if (this == anObject) {
                return true;
            }
            if (anObject instanceof Duration) {
                if(StringUtil.isNotEmptyString(this.getTrackNum())
                        && StringUtil.isNotEmptyString(((Duration) anObject).getTrackNum())){

                    return this.getTrackNum().equals(((Duration) anObject).getTrackNum());
                }
            }
            return false;
        }

        public long getDuration(){
            if(null != startTime && null != endTime){
                return endTime.getTime()-startTime.getTime();
            }
            return -1;
        }
    }

}
