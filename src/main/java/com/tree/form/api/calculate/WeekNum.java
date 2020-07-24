package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author zhuzhong@tree.com
 * @date 2020-07-06 15:36
 * @description
 */
public class WeekNum extends MathFunctionHandle{

  @Override
  public FunctionEnum func() {
    return FunctionEnum.WEEK_NUM;
  }

  @Override
  public Object executeInner(Object[] objects) {
    return null;
  }

  @Override
  public Object calculate(List params) {
    if (CollectionUtils.isEmpty(params)){
      throwCalculate();
    }
    Object object = params.get(0);
    if (object instanceof List && CollectionUtils.isEmpty((List) object)){
      throwCalculate();
    }
    object = object instanceof List ? ((List) object).get(0):object;
    if (!(object instanceof Long)){
      throwCalculate();
    }
    Long timeMillis = (Long)(object);
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(timeMillis);
    return BigDecimal.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
  }
}
