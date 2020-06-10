package com.yunsom.form.api.calculate;

import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 14:42
 * @description
 */
public class Plus extends MathFunctionHandle {


  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    if (CollectionUtils.isEmpty(params)){
      return null;
    }
    BigDecimal result = new BigDecimal(0);
    for (BigDecimal bigDecimal:params){
      result = result.add(bigDecimal);
    }
    return result;
  }

  public FunctionEnum func() {
    return FunctionEnum.PLUS;
  }
}
