package com.yunsom.form.api.calculate;

import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 15:55
 * @description
 */
public class Residue extends MathFunctionHandle{

  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    if (CollectionUtils.isEmpty(params) || params.size()!=2){
      return null;
    }
    return params.get(0).remainder(params.get(1));
  }

  @Override
  public FunctionEnum func() {
    return null;
  }
}
