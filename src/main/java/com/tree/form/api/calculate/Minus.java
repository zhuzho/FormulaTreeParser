package com.yunsom.form.api.calculate;

import static com.yunsom.form.api.constant.FunctionEnum.MINUS;
import static java.math.BigDecimal.ROUND_HALF_UP;

import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 15:47
 * @description
 */
public class Minus  extends MathFunctionHandle {

  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    if (CollectionUtils.isEmpty(params) || params.size()!=2){
      return null;
    }
    return params.get(0).subtract(params.get(1));
  }

  @Override
  public FunctionEnum func() {
    return MINUS;
  }
}
