package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhuzhong@tree.com
 * @date 2020-06-09 17:00
 * @description
 */
public class Count extends MathFunctionHandle {

  @Override
  public FunctionEnum func() {
    return FunctionEnum.COUNT;
  }

  @Override
  public Object calculate(List params) {
    if (Objects.isNull(params)){
      throwCalculate();
    }
    params = (List) params.stream().filter(Objects::nonNull
    ).collect(Collectors.toList());
    if (params.get(0) instanceof List){
      return BigDecimal.valueOf(((List) params.get(0)).size());
    }
    return BigDecimal.valueOf(params.size());
  }


  @Override
  public Object executeInner(Object[] objects) {
    return Objects.isNull(objects)?BigDecimal.valueOf(0):objects.length;
  }
}
