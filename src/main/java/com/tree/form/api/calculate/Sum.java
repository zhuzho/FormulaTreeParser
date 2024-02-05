package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author onep@tree.com
 * @date 2020-06-09 16:04
 * @description
 */
public class Sum extends MathFunctionHandle {

  @Override
  public FunctionEnum func() {
    return FunctionEnum.SUM;
  }

  @Override
  public BigDecimal calculate(List  params) {
    params = (List) params.stream().filter(Objects::nonNull
    ).collect(Collectors.toList());
    List newParams = getObject(params);
    return (BigDecimal) FunctionEnum.PLUS.calculator.calculate(newParams);
  }

  @Override
  public Object executeInner(Object[] objects)   {
    List<Object> bigDecimals = convert(objects);
    return this.calculate(bigDecimals);
  }
}
