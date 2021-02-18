package tools;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Luffy
 * @Classname DateTools
 * @Description 日期工具类
 * @Date 2020/12/21 16:11
 */
public class DateTools {
    /**
     * 获取距离指定日期的第distance天
     *
     * @param distance 正的就是往后，负的就是往前
     * @return 日期
     */
    public static Date getDistanceDate(Date beginDate, int distance) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + distance);
        return calendar.getTime();
    }
}
