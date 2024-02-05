package com.tree.form.api.util;

import com.tree.common.base.exception.BusinessException;
import com.tree.form.api.constant.ResultCodeEnum;
import com.tree.form.api.dto.Expression;
import org.springframework.util.CollectionUtils;

/**
 * @author onep@tree.com
 * @date 2019-05-21 18:13
 * @description
 */
public class NormalMathValidator extends FunctionValidator {

  @Override
  public void validate(Expression eg) {
    if (CollectionUtils.isEmpty(eg.getItems())||eg.getItems().size()<2){
      ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
    validate(eg.getItems());
  }
}
