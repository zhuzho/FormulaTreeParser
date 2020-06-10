package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import java.math.BigDecimal;
import java.util.List;

/**
 * @date 2020-06-09 17:00
 * @description
 */
public class Count extends MathFunctionHandle {

  @Override
  public FunctionEnum func() {
    return null;
  }

  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    return BigDecimal.valueOf(params.size());
  }
}
