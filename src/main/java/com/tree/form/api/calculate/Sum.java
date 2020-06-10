package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@tree.com
 * @date 2020-06-09 16:04
 * @description
 */
public class Sum extends MathFunctionHandle {

  @Override
  public FunctionEnum func() {
    return FunctionEnum.SUM;
  }

  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    return FunctionEnum.PLUS.calculator.calculate(params);
  }
}
