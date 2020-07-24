package com.yunsom.form.api.calculate;

import com.yunsom.form.api.constant.FunctionEnum;
import java.util.List;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-07-06 14:53
 * @description
 */
public class Now extends MathFunctionHandle{

  @Override
  public Object calculate(List params) {
    return System.currentTimeMillis();
  }

  @Override
  public FunctionEnum func() {
    return FunctionEnum.NOW;
  }

  @Override
  public Object executeInner(Object[] objects) {
    return System.currentTimeMillis();
  }
}
