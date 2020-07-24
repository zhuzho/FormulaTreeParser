package com.yunsom.form.api.calculate;

import static com.yunsom.form.api.constant.ResultCodeEnum.DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM;

import com.ql.util.express.Operator;
import com.yunsom.common.base.exception.BusinessException;
import com.yunsom.form.api.constant.FunctionEnum;
import com.yunsom.form.api.constant.ResultCodeEnum;
import com.yunsom.form.api.dto.Expression;
import com.yunsom.form.api.service.MathCalculate;
import com.yunsom.form.api.util.CollectionUtils;
import com.yunsom.form.api.util.ParamParser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 14:42
 * @description
 */
public abstract class MathFunctionHandle extends Operator implements MathCalculate {

  public List  calculate(List<Expression> params, Map<String, Object> paramVar,String calculateTableElement,Integer calculateTableRow){

    return null;
  }

  public Object  calculate(Expression expression, Map<String, Object> paramVar,String calculateTableElement,Integer calculateTableRow){
    FunctionEnum functionEnum ;
    if (Objects.isNull(expression.getFunc())||Objects.isNull(functionEnum=FunctionEnum.get(expression.getFunc()))){
      Object bigDecimal = this.getNumber(expression,paramVar);
      if (Objects.nonNull(bigDecimal)){
        return bigDecimal;
      }
      return null;
    }
    MathCalculate handle = functionEnum.calculator;
    if (Objects.nonNull(handle) && CollectionUtils.isNotEmpty(expression.getItems())){
      List  result = new ArrayList<>();
      Set<String> tableSet = (Set<String>) paramVar.get(calculateTableElement);
      //表格內四则运算
      for (int i=0;i<expression.getItems().size() ;i++){
        Expression expr = expression.getItems().get(i);
        if (FunctionEnum.arithmetic.contains(functionEnum)
            && ParamParser.isElementVar(expr.getExpreVar())
            && StringUtils.isNotEmpty(calculateTableElement)
            && Objects.nonNull(calculateTableRow)){
          if (CollectionUtils.isNotEmpty(tableSet)){
            if (tableSet.contains(expr.getExpreVar())){
              Object bigDecimal = this.getNumber(expr,paramVar);
              if (Objects.isNull(bigDecimal)||!(bigDecimal instanceof Collection)){
                throwCalculate();
              }
              result.add(((List)bigDecimal).get(calculateTableRow-1));
              continue;
            }
          }
        }
        Object  bigDecimal = this.calculate(expr,paramVar,calculateTableElement,calculateTableRow);
        result.add(bigDecimal);
      }
      Object bigDecimal = handle.calculate(result);
      return Objects.nonNull(bigDecimal)?bigDecimal:null;
    }
    return null;
  }

  public abstract FunctionEnum func();

  Object getNumber(Expression expression ,  Map<String, Object> paramVar){
    Object value = expression.getExpreVar();
    Object object = null;
    if (ParamParser.isElementVar(value)){
      object = paramVar.get(expression.getExpreVar());
      if (Objects.isNull(object)||!(object instanceof Collection)){
        return object;
      }
    }else{
      Object constant = ParamParser.convert(value);
      if (Objects.nonNull(constant)){
        return constant;
      }
    }
    List<Object> result = new ArrayList<>();
    Object bigDecimal;
    if (object instanceof Collection){
      for (Object obj:(Collection)object){
        bigDecimal = ParamParser.convert(obj);
        result.add(bigDecimal);
      }
    }
    return result;
  }

  public List<Object>  convert(Object[] objects){
    return ParamParser.convert(objects);
  }


  void throwCalculate(){
    ResultCodeEnum resultCodeEnum = DEFINE_FORM_EXPRE_FUNC_INVALID_PARAM ;
    throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
  }

  List getObject(List params){
    List newParams = new ArrayList();
    for (Object object:params){
      if (object instanceof Collection ){
        if (CollectionUtils.isNotEmpty((Collection<?>) object)){
          newParams.addAll((Collection) object);
        }
      }else {
        newParams.add ( object);
      }
    }
    return newParams;
  }
  List<BigDecimal> getBigDecimal(List params){
    List<BigDecimal> newParams = new ArrayList();
    Object convertResult = null;
    for (Object object:params){
      if (object instanceof List ){
        if (((List<?>) object).size()>1){
          throwCalculate();
        }
        convertResult = ParamParser.convert(((List) object).get(0));
      }else {
        convertResult = ParamParser.convert(object);
      }
      if (Objects.isNull(convertResult)||!(convertResult instanceof BigDecimal)){
        throwCalculate();
      }
      newParams.add ((BigDecimal) convertResult);
    }
    return newParams;
  }

  protected void needParamSize(List params,int size){
    if (CollectionUtils.isEmpty(params)){
      throwCalculate();
    }
    params = (List) params.stream().filter(Objects::nonNull
    ).collect(Collectors.toList());
    if (params.size()!=size){
      throwCalculate();
    }
  }
}
