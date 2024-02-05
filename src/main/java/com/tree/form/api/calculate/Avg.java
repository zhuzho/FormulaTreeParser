package com.tree.form.api.calculate;

import static com.tree.form.api.constant.CommonConstant.decimal_places_processing;
import static java.math.BigDecimal.ROUND_HALF_UP;

import com.tree.form.api.constant.FunctionEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @date 2020-06-09 16:00
 * @description
 */
public class Avg  extends MathFunctionHandle{

  @Override
  public Object calculate(List  params) {
    List newParams = getObject(params);
    newParams = (List) newParams.stream().filter(Objects::nonNull
    ).collect(Collectors.toList());
    Object result = FunctionEnum.PLUS.calculator.calculate(newParams);
    if (Objects.isNull(result)){
      return null;
    }
    return ((BigDecimal)result).divide(BigDecimal.valueOf(newParams.size()),decimal_places_processing,ROUND_HALF_UP);
  }

  @Override
  public FunctionEnum func() {
    return FunctionEnum.AVG;
  }


  @Override
  public Object executeInner(Object[] objects) {
    List<Object> bigDecimals = convert(objects);
    return this.calculate(bigDecimals);
  }
}
