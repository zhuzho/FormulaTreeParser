package com.yunsom.form.api.calculate;

import static com.yunsom.form.api.util.ParamParser.truncateTime;

import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.constant.TimeUnit;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-07-06 15:35
 * @description
 */
public class DateDiff extends MathFunctionHandle {

  @Override
  public FunctionEnum func() {
    return FunctionEnum.DATE_DIFF;
  }

  @Override
  public Object executeInner(Object[] objects)  {
    return null;
  }

  @Override
  public Object calculate(List params) {
    needParamSize(params,3);
    if (params.get(0) instanceof List && CollectionUtils.isEmpty((List) params.get(0))){
      throwCalculate();
    }
    if (params.get(1) instanceof List && CollectionUtils.isEmpty((List) params.get(1))){
      throwCalculate();
    }
    Object bfObj = params.get(0) instanceof List
        ?((List)params.get(0)).get(0)
        :params.get(0);
    Object afObj = params.get(1) instanceof List
        ?((List)params.get(1)).get(0)
        :params.get(1);
    Long before =   (Long) bfObj ;
    Long after =  (Long) afObj ;
    if (!(before instanceof Long)||!(after instanceof Long)){
      throwCalculate();
    }
    String alias = (String) params.get(2);
    TimeUnit unit = TimeUnit.get(alias);
    if (Objects.equals(TimeUnit.SECOND,unit)){
      long mi = (before-after);
      return BigDecimal.valueOf(mi/TimeUnit.SECOND.code);
    }else if (
           Objects.equals(TimeUnit.MINUTE,unit)
      ||  Objects.equals(TimeUnit.HOUR,unit)
      ||  Objects.equals(TimeUnit.DAY,unit)
    ){
      long mi = truncateTime(before,unit.truncStr)-truncateTime(after,unit.truncStr);
      long result = mi/unit.code;
      return BigDecimal.valueOf(result);
    } else if(
         Objects.equals(TimeUnit.MONTH,unit)
      ||Objects.equals(TimeUnit.YEAR,unit)
    ){
      Calendar beCalendar = Calendar.getInstance();
      beCalendar.setTimeInMillis(before);
      Calendar afCalendar = Calendar.getInstance();
      afCalendar.setTimeInMillis(after);
      int yearBef = beCalendar.get(Calendar.YEAR);
      int yearAft = afCalendar.get(Calendar.YEAR);
      //DateDiff(bf=2020-10-10,af=2019-03-10)
      // 月差=(bf.year-af.year-1)*12+bf.month+(12-af.month)
      // ==>(bf.year-af.year)*11+bf.month-af.month
      return Objects.equals(TimeUnit.MONTH,unit)
          ?(yearBef-yearAft-1)*12+ (beCalendar.get(Calendar.MONTH)+12- afCalendar.get(Calendar.MONTH))
          :BigDecimal.valueOf(yearBef-yearAft);
    }else {
      throwCalculate();
    }
    return null;
  }
}
