package com.tree.form.api.calculate;

import static com.tree.form.api.constant.FunctionEnum.ADD_DAY;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@tree.com
 * @date 2020-07-09 14:27
 * @description
 */
public class AddDay extends MathFunctionHandle {

  @Override
  public FunctionEnum func() {
    return ADD_DAY;
  }

  @Override
  public Object executeInner(Object[] objects)   {
    return null;
  }

  @Override
  public Object calculate(List params) {
    needParamSize(params,2);
    if (params.get(0) instanceof List && CollectionUtils.isEmpty((List) params.get(0))){
      throwCalculate();
    }
    if (params.get(1) instanceof List && CollectionUtils.isEmpty((List) params.get(1))){
      throwCalculate();
    }
    Object time = params.get(0) instanceof List ? ((List) params.get(0)).get(0):params.get(0);
    long paramReal = time instanceof Long ?(Long) time:( (BigDecimal) time).longValue();
    Object num = params.get(1);
    BigDecimal paramNum = (BigDecimal)(num instanceof List ? ((List) num).get(0):num);
    return paramReal+ paramNum.longValue()*24*3600*1000;
  }
}
