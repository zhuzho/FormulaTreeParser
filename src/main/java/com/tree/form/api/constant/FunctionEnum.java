package com.tree.form.api.constant;

import com.tree.form.api.calculate.Avg;
import com.tree.form.api.calculate.Divide;
import com.tree.form.api.calculate.Minus;
import com.tree.form.api.calculate.Multiply;
import com.tree.form.api.calculate.Plus;
import com.tree.form.api.calculate.Residue;
import com.tree.form.api.calculate.Sum;
import com.tree.form.api.service.MathCalculate;
import com.tree.form.api.util.FunctionValidator;
import com.tree.form.api.util.IfElseValidator;
import com.tree.form.api.util.NormalMathValidator;
import java.util.Objects;
import org.springframework.util.StringUtils;

/**
 * @author zhuzhong@yunsom.com
 * @date 2019-05-21 17:40
 * @description
 */
public enum FunctionEnum {

   PLUS("plus","+",new NormalMathValidator(),new Plus())
  ,MINUS("minus","-",new NormalMathValidator(),new Minus())
      /**"乘",*/
  ,MULTIPLY("multiply","*",new NormalMathValidator(),new Multiply())
      /**"除",*/
  ,DIVIDE("divide","/",new NormalMathValidator(),new Divide())
    /**,"余"*/
  ,RESIDUE("residue","%",new NormalMathValidator(),new Residue())
  ,AVG("avg","avg",new NormalMathValidator(),new Avg())
  ,SUM("sum","sum",new NormalMathValidator(),new Sum())

  ,IF_ELSE("ie","if",new IfElseValidator(),null)
  ,OR("or","||",null,null)
  ,AND("and","&&",null,null)
  ,GREATER_THAN("gt",">",null,null)
  ,GREATER_EQUAL("ge",">=",null,null)
  ,EQUAL("eq","=",null,null)
  ,NOT_EQUAL("ne","!=",null,null)
  ,LESS_THAN("lt","<",null,null)
  ,LESS_EQUAL("le","<=",null,null)
  ;

  public final String method ;
  public final String sign;
  public final FunctionValidator validator;
  public final MathCalculate calculator;

  FunctionEnum(String method,String sign,  FunctionValidator validator,MathCalculate calculator) {
    this.method = method;
    this.sign = sign;
    this.validator=validator;
    this.calculator = calculator;
  }

  public static FunctionEnum get(String method){
    if (StringUtils.isEmpty(method)){
      return null;
    }
    for(FunctionEnum mt:FunctionEnum.values()){
      if (Objects.equals(mt.method,method)){
        return mt;
      }
    }
    return null;
  }

  public static FunctionEnum get(char method){
    for (FunctionEnum fn:FunctionEnum.values()){
      if (Objects.equals(fn.sign.charAt(0),method)){
        return fn;
      }
    }
    return null;
  }

  public FunctionValidator getValidator() {
    return validator;
  }}
