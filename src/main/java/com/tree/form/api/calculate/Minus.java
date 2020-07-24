package com.tree.form.api.calculate;

import static com.tree.form.api.constant.FunctionEnum.MINUS;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhuzhong@tree.com
 * @date 2020-06-09 15:47
 * @description
 */
public class Minus  extends MathFunctionHandle {

  @Override
  public BigDecimal calculate(List params) {
    needParamSize(params,2);
    params = getBigDecimal(params);
    if (!(params.get(0) instanceof BigDecimal) || !(params.get(1) instanceof BigDecimal)){
      throwCalculate();
    }
    return ((BigDecimal)params.get(0)).subtract((BigDecimal)params.get(1));
  }

  @Override
  public FunctionEnum func() {
    return MINUS;
  }


  @Override
  public Object executeInner(Object[] objects)  {
    List<Object> bigDecimals = convert(objects);
    return this.calculate(bigDecimals);  }
}
