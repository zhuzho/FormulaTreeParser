package com.yunsom.form.api.calculate;

import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 14:42
 * @description
 */
public class Plus extends MathFunctionHandle {


  @Override
  public BigDecimal calculate(List  params) {
    if (CollectionUtils.isEmpty(params)){
      throwCalculate();
    }
    params = getBigDecimal(params);
    BigDecimal result = BigDecimal.valueOf(0);
    for (Object bigDecimal:params){
      if (Objects.isNull(bigDecimal)){
        throwCalculate();
      }
      if (bigDecimal instanceof List ){
        if (((List) bigDecimal).size()>1){
          throwCalculate();
        }
        result = result.add((BigDecimal) ((List) bigDecimal).get(0));
      }else{
        result = result.add((BigDecimal) bigDecimal);
      }
    }
    return result;
  }

  public FunctionEnum func() {
    return FunctionEnum.PLUS;
  }


  @Override
  public Object executeInner(Object[] objects)  {
    List<Object> bigDecimals = convert(objects);
    return this.calculate(bigDecimals);
  }
}
