package com.tree.form.api.calculate;

import static com.tree.form.api.constant.CommonConstant.decimal_places_processing;
import static com.tree.form.api.constant.FunctionEnum.DIVIDE;
import static java.math.BigDecimal.ROUND_HALF_UP;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhuzhong@tree.com
 * @date 2020-06-09 15:53
 * @description
 */
public class Divide extends MathFunctionHandle{

  @Override
  public BigDecimal calculate(List params) {
    needParamSize(params,2);
    params = getBigDecimal(params);
    if (BigDecimal.ZERO.compareTo((BigDecimal)params.get(1))==0){
      throwCalculate();
    }
    if (!(params.get(0) instanceof BigDecimal) || !(params.get(1) instanceof BigDecimal)){
      throwCalculate();
    }
    return ((BigDecimal)params.get(0)).divide(((BigDecimal)params.get(1)),decimal_places_processing,ROUND_HALF_UP);
  }

  @Override
  public FunctionEnum func() {
    return DIVIDE;
  }

  @Override
  public Object executeInner(Object[] objects) {
    if (Objects.isNull(objects) || objects.length!=2){
      return null;
    }
    List<Object> bigDecimals = convert(objects);
    return calculate(bigDecimals);
  }
}
