package com.tree.form.api.calculate;

import static java.math.BigDecimal.ROUND_HALF_UP;

import com.tree.form.api.constant.FunctionEnum;
import java.math.BigDecimal;
import java.util.List;

/**
  * @date 2020-06-09 16:00
 * @description
 */
public class Avg  extends MathFunctionHandle{

  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    return FunctionEnum.PLUS.calculator.calculate(params).divide(new BigDecimal(params.size()),2,ROUND_HALF_UP);
  }

  @Override
  public FunctionEnum func() {
    return null;
  }
}
