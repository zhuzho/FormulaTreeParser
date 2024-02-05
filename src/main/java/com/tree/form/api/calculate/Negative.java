package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author onep@tree.com
 * @date 2020-06-30 19:02
 * @description
 */
public class Negative extends MathFunctionHandle {

  @Override
  public FunctionEnum func() {
    return FunctionEnum.NEGATIVE;
  }

  @Override
  public Object executeInner(Object[] objects)   {
    return null;
  }

  @Override
  public Object calculate(List params) {
    if (CollectionUtils.isEmpty(params)){
      throwCalculate();
    }
    if (params.size()!=1||!(params.get(0) instanceof BigDecimal)){
      throwCalculate();
    }
    return BigDecimal.ZERO.subtract((BigDecimal) params.get(0));
  }
}
