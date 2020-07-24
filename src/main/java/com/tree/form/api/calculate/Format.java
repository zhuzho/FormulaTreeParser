package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhuzhong@tree.com
 * @date 2020-07-07 9:30
 * @description
 */
public class Format extends MathFunctionHandle{

  @Override
  public FunctionEnum func() {
    return FunctionEnum.DATE_FORMAT;
  }

  @Override
  public Object executeInner(Object[] objects)   {
    return null;
  }

  @Override
  public Object calculate(List params) {
    needParamSize(params,2);
    String pattern = (String) params.get(1);
    Object data = params.get(0);
    data = (data instanceof List ? ((List) data).get(0):data);
    if (data instanceof BigDecimal){
      data = ((BigDecimal)data).longValue();
    }else if (!(data instanceof Long)){
      throwCalculate();
    }
    Long millsTime =  (Long) data  ;
    DateFormat dateFormat = new SimpleDateFormat(pattern);
    return dateFormat.format(new Date(millsTime));
  }
}
