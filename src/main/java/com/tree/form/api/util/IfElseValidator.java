package com.yunsom.form.api.util;

import com.yunsom.common.base.exception.BusinessException;
import com.yunsom.form.api.constant.ResultCodeEnum;
import com.yunsom.form.api.dto.Expression;
import java.util.Objects;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author zhuzhong@yunsom.com
 * @date 2019-05-22 15:44
 * @description
 */
public class IfElseValidator extends FunctionValidator {

  @Override
  public void validate(Expression eg) {
    if (CollectionUtils.isEmpty(eg.getItems())){
      ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
    long elseCount = eg.getItems().stream()
        .filter( Objects::nonNull)
        .filter(t-> StringUtils.isEmpty(t.getFunc()))
        .count();
    if (elseCount>1){
      ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_FUNC_ONLY_ONE_ELSE;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
    validate(eg.getItems());
  }
}
