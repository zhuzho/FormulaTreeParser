package com.tree.form.api.calculate;

import static com.tree.form.api.util.ParamParser.truncateTime;

import com.tree.form.api.constant.FunctionEnum;
import java.util.List;

/**
 * @author zhuzhong@tree.com
 * @date 2020-07-06 14:53
 * @description
 */
public class Today extends MathFunctionHandle{

  @Override
  public Object calculate(List params) {
    return truncateTime(System.currentTimeMillis(),"yyyyMMdd");
  }

  @Override
  public FunctionEnum func() {
    return FunctionEnum.TODAY;
  }

  @Override
  public Object executeInner(Object[] objects) {
    return truncateTime(System.currentTimeMillis(),"yyyyMMdd");
  }
}
