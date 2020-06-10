package com.yunsom.form.api.calculate;

import static com.yunsom.form.api.constant.ResultCodeEnum.DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM;

import com.yunsom.common.base.exception.BusinessException;
import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.constant.ResultCodeEnum;
import com.yunsom.form.api.dto.Expression;
import com.yunsom.form.api.service.MathCalculate;
import com.yunsom.form.api.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 14:42
 * @description
 */
public abstract class MathFunctionHandle implements MathCalculate {

  public List<BigDecimal> calculate(List<Expression> params, Map<String, Object> paramVar){
    List<BigDecimal> result = new ArrayList<>();
    for (Expression expression:params){
      List<BigDecimal>  bigDecimal = this.calculate(expression,paramVar);
      if (CollectionUtils.isNotEmpty(bigDecimal)){
        result.addAll(bigDecimal);
      }
    }
    return result;
  }

  public List<BigDecimal> calculate(Expression expression, Map<String, Object> paramVar){
    FunctionEnum functionEnum = FunctionEnum.get(expression.getFunc());
    if (Objects.isNull(functionEnum)){
      List<BigDecimal> bigDecimal = this.getNumber(expression,paramVar);
      if (CollectionUtils.isNotEmpty(bigDecimal)){
        return bigDecimal;
      }
    }
    MathCalculate handle = functionEnum.calculator;
    if (Objects.nonNull(handle) && CollectionUtils.isNotEmpty(expression.getItems())){
      List<BigDecimal> result =  handle.calculate(expression.getItems(),paramVar);
      BigDecimal bigDecimal = handle.calculate(result);
      return Objects.nonNull(bigDecimal)?Arrays.asList(bigDecimal):null;
    }
    return null;
  }

  public abstract FunctionEnum func();

  List<BigDecimal> getNumber(Expression expression ,  Map<String, Object> paramVar){
    Object value = expression.getExpreVar();
    BigDecimal constant = this.convert(value);
    if (Objects.nonNull(constant)){
      return Arrays.asList(constant);
    }
    List<BigDecimal> result = new ArrayList<>();
    if ( expression.getExpreVar() instanceof String){
      Object object = paramVar.get(expression.getExpreVar());
      BigDecimal bigDecimal;
      if (object instanceof Collection){
        for (Object obj:(Collection)object){
          bigDecimal = this.convert(obj);
          if (Objects.nonNull(bigDecimal)){
            result.add(bigDecimal);
          }
        }
      }else{
        bigDecimal = this.convert(object);
        if (Objects.nonNull(bigDecimal)){
          result.add(bigDecimal);
        }
      }
    }
    if (Objects.isNull(result)){
      ResultCodeEnum resultCodeEnum = DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM ;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
    return result;
  }

  BigDecimal convert(Object value ){
    if (Objects.isNull(value)){
      return null;
    }
    if(value instanceof  Float ){
      return new BigDecimal((Float) value);
    }else if ( value instanceof  Double){
      return new BigDecimal((Double) value);
    }else if (value instanceof  Integer ){
      return new BigDecimal((Integer) value);
    }else if ( value  instanceof  Long){
      return new BigDecimal((Long) value);
    }
    return null;
  }
}
