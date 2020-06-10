package com.yunsom.form.api.calculate;

import static com.yunsom.form.api.constant.FunctionEnum.MULTIPLY;

import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 15:51
 * @description
 */
public class Multiply extends MathFunctionHandle  {

  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    if (CollectionUtils.isEmpty(params)){
      return null;
    }
    BigDecimal result = new BigDecimal(1);
    for (BigDecimal bigDecimal:params){
      result = result.multiply(bigDecimal);
    }
    return result;
  }

  @Override
  public FunctionEnum func() {
    return MULTIPLY;
  }
}
