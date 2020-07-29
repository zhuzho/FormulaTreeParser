package com.tree.form.api.constant;

import com.tree.form.api.calculate.AddDay;
import com.tree.form.api.calculate.Avg;
import com.tree.form.api.calculate.Count;
import com.tree.form.api.calculate.DateDiff;
import com.tree.form.api.calculate.Divide;
import com.tree.form.api.calculate.Format;
import com.tree.form.api.calculate.Max;
import com.tree.form.api.calculate.Min;
import com.tree.form.api.calculate.Minus;
import com.tree.form.api.calculate.Multiply;
import com.tree.form.api.calculate.Negative;
import com.tree.form.api.calculate.Now;
import com.tree.form.api.calculate.Plus;
import com.tree.form.api.calculate.Residue;
import com.tree.form.api.calculate.Sum;
import com.tree.form.api.calculate.Today;
import com.tree.form.api.calculate.WeekNum;
import com.tree.form.api.service.MathCalculate;
import com.tree.form.api.util.FunctionValidator;
import com.tree.form.api.util.IfElseValidator;
import com.tree.form.api.util.NormalMathValidator;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

/**
 * @author zhuzhong@tree.com
 * @date 2019-05-21 17:40
 * @description
 */
public enum FunctionEnum {

   PLUS("plus","+",new NormalMathValidator(),new Plus())
  ,MINUS("minus","-",new NormalMathValidator(),new Minus())
  ,MULTIPLY("multiply","*",new NormalMathValidator(),new Multiply())
  ,DIVIDE("divide","/",new NormalMathValidator(),new Divide())
  ,RESIDUE("residue","%",new NormalMathValidator(),new Residue())
  ,AVG("avg","avg",new NormalMathValidator(),new Avg())
  ,SUM("sum","sum",new NormalMathValidator(),new Sum())
  ,COUNT("count","count",new NormalMathValidator(),new Count())
  ,MAX("max","max",new NormalMathValidator(),new Max())
  ,MIN("min","min",new NormalMathValidator(),new Min())

  ,IF_ELSE("ie","if",new IfElseValidator(),null)

  ,OR("or","||",null,null)
  ,AND("and","&&",null,null)
  ,GREATER_THAN("gt",">",null,null)
  ,GREATER_EQUAL("ge",">=",null,null)
  ,EQUAL("eq","=",null,null)
  ,NOT_EQUAL("ne","!=",null,null)
  ,LESS_THAN("lt","<",null,null)
  ,LESS_EQUAL("le","<=",null,null)

  ,NEGATIVE("negative","negative",null,new Negative())
  ,TODAY("today","today",null,new Today())
  ,NOW("now","now",null,new Now())

  ,ADD_DAY("addday","addday",null,new AddDay())
  ,DATE_DIFF("datediff","datediff",null,new DateDiff())
  ,WEEK_NUM("weeknum","weeknum",null,new WeekNum())
  ,DATE_FORMAT("format","format",null,new Format())

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

  public static List<String> p_m_m_d_r = Arrays.asList(PLUS.method,MINUS.method,MULTIPLY.method,DIVIDE.method,RESIDUE.method);

  public static List<String> getDiyFunc(){
    return Arrays.stream(FunctionEnum.values())
        .filter(tt->Objects.nonNull(tt.calculator))
        .filter(tt->!p_m_m_d_r.contains(tt.method))
        .map(tt->tt.method)
        .collect(Collectors.toList());
  }

  public FunctionValidator getValidator() {
    return validator;
  }

  public static List<FunctionEnum> aggregationFunc = Arrays.asList(AVG,SUM,COUNT,MAX,MIN);

  public static List<FunctionEnum> arithmetic = Arrays.asList(
      PLUS,MINUS,MULTIPLY,DIVIDE,RESIDUE,ADD_DAY,DATE_DIFF,WEEK_NUM,DATE_FORMAT
  );

}
