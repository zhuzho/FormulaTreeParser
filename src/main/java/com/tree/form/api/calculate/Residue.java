package com.tree.form.api.calculate;

import static com.tree.form.api.constant.FunctionEnum.RESIDUE;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@tree.com
 * @date 2020-06-09 15:55
 * @description
 */
public class Residue extends MathFunctionHandle{

  @Override
  public BigDecimal calculate(List  params) {
    if (CollectionUtils.isEmpty(params) || params.size()!=2){
      throwCalculate();
    }
    params = getBigDecimal(params);
    return ((BigDecimal)params.get(0)).remainder((BigDecimal)params.get(1));
  }

  @Override
  public FunctionEnum func() {
    return RESIDUE;
  }

  @Override
  public Object executeInner(Object[] objects)  {
    List<Object> bigDecimals = convert(objects);
    return this.calculate(bigDecimals);
  }
}
