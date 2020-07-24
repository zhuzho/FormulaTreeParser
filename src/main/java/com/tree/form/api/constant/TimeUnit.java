package com.tree.form.api.constant;

import java.util.Calendar;
import java.util.Objects;

/**
 * @author zhuzhong@tree.com
 * @date 2020-07-06 17:30
 * @description
 */
public enum TimeUnit {
  SECOND("s",1l*1000,"yyyyMMddHHmmss")
  ,MINUTE("m",SECOND.code*60l,"yyyyMMddHHmm")
  ,HOUR("h",MINUTE.code*60l,"yyyyMMddHH")
  ,DAY("d",HOUR.code*24,"yyyyMMdd")
  ,MONTH("M",Calendar.MONTH,"yyyyMM")
  ,YEAR("y",Calendar.YEAR,"yyyy");
  public String alias;
  public long code;
  public String truncStr ;
  TimeUnit(String alias, long code,String truncStr){
    this.alias=alias;
    this.code=code;
    this.truncStr=truncStr;
  }
  public static TimeUnit get(String alias){
    for (TimeUnit unit:TimeUnit.values()){
      if (Objects.equals(unit.alias,alias)){
        return unit;
      }
    }
    return null;
  }
}
