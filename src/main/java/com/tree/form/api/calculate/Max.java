package com.tree.form.api.calculate;

import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.util.CollectionUtils;
import com.tree.form.api.util.ParamParser;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhuzhong@tree.com
 * @date 2020-06-10 16:11
 * @description
 */
public class Max  extends MathFunctionHandle{

  @Override
  public FunctionEnum func() {
    return FunctionEnum.MAX;
  }

  @Override
  public Object calculate(List params) {
    if (CollectionUtils.isEmpty(params)){
      return null;
    }
    params = (List) params.stream().filter(Objects::nonNull
    ).collect(Collectors.toList());
    List<Object> newParams = getObject(params);
    newParams = newParams.stream().map(t-> ParamParser.convert(t)).collect(Collectors.toList());
    newParams.stream().forEach(t->{
      if (!(t instanceof BigDecimal)){
        throwCalculate();;
      }
    });
    if (newParams.size()==1){
      return  newParams.get(0);
    }
    BigDecimal result = (BigDecimal) newParams.get(0);
    for (Object bigDecimal:newParams){
      result =  ((BigDecimal)bigDecimal).max(result);
    }
    return result;
  }


  @Override
  public Object executeInner(Object[] objects)  {
    List<Object> bigDecimals = convert(objects);
    return calculate(bigDecimals);
  }
}
