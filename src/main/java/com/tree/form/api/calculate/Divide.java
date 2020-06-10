package com.yunsom.form.api.calculate;

import static com.yunsom.form.api.constant.FunctionEnum.DIVIDE;
import static com.yunsom.form.api.constant.ResultCodeEnum.DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM;
import static java.math.BigDecimal.ROUND_HALF_UP;

import com.yunsom.common.base.exception.BusinessException;
import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.constant.ResultCodeEnum;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 15:53
 * @description
 */
public class Divide extends MathFunctionHandle{

  @Override
  public BigDecimal calculate(List<BigDecimal> params) {
    if (CollectionUtils.isEmpty(params) || params.size()!=2){
      return null;
    }
    if (params.get(1).intValue() == 0){
      ResultCodeEnum resultCodeEnum = DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM ;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
    return params.get(0).divide(params.get(1),2,ROUND_HALF_UP);
  }

  @Override
  public FunctionEnum func() {
    return DIVIDE;
  }
}
