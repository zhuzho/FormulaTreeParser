package com.tree.form.api.calculate;

import static com.tree.form.api.constant.FunctionEnum.MULTIPLY;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author onep@tree.com
 * @date 2020-06-09 15:51
 * @description
 */
public class Multiply extends MathFunctionHandle  {

  @Override
  public BigDecimal calculate(List  params) {
    if (CollectionUtils.isEmpty(params)){
      return null;
    }
    if (params.size()==1){
      throwCalculate();
    }
    params = getBigDecimal(params);
    BigDecimal result = BigDecimal.valueOf(1);
    for (Object bigDecimal:params){
      if (Objects.isNull(bigDecimal)){
        throwCalculate();
      }
      if (bigDecimal instanceof List ){
        if (((List) bigDecimal).size()>1){
          throwCalculate();
        }
        result = result.multiply((BigDecimal) ((List) bigDecimal).get(0));
      }else{
        result = result.multiply((BigDecimal) bigDecimal);
      }
    }
    return result;
  }

  @Override
  public FunctionEnum func() {
    return MULTIPLY;
  }

  @Override
  public Object executeInner(Object[] objects) {
    List<Object> bigDecimals = convert(objects);
    return this.calculate(bigDecimals);  }
}
