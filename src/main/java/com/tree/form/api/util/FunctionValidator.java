package com.tree.form.api.util;


import com.tree.common.base.exception.BusinessException;
import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.constant.ResultCodeEnum;
import com.tree.form.api.dto.Expression;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author onep@tree.com
 * @date 2019-05-21 17:57
 * @description
 */
public abstract class FunctionValidator {
  public abstract void validate(Expression eg);

  public void validate(List<Expression> items){
    if (CollectionUtils.isEmpty(items)){
      return;
    }
    items.stream().forEach(t->{
      if (StringUtils.isEmpty(t.getFunc())){
        return;
      }
      FunctionEnum en = FunctionEnum.get(t.getFunc());
      if (Objects.isNull(en)  ){
        return;
      }
      if (en == null){
        ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_FUNC_NOT_EXISTS;
        throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
      }
      if (en.getValidator()==null){
        if (!CollectionUtils.isEmpty(t.getItems())){
          validate(t.getItems());
        }
      }else {
        en.getValidator().validate(t);
      }
    });
  }
}
