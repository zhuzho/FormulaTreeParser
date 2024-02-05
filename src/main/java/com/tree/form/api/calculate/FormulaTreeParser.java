package com.tree.form.api.calculate;

import static com.tree.form.api.constant.FunctionEnum.MINUS;

import com.tree.common.base.exception.BusinessException;
import com.tree.form.api.constant.FunctionEnum;
import com.tree.form.api.constant.ResultCodeEnum;
import com.tree.form.api.dto.Expression;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

/**
 * 数学公式计算解析,暂时不支持函数内嵌套函数或公式
 * <br/>
 *  ($wget_1_1#+0.7*0.3/0.1-1)*avg(1,3,4,5,$aa#)*($wget_1_2#+0.9)-100" ;
 *  <br/>
 *  ($wget_1_1#+0.7)*($wget_1_2#+0.9)-100+50-($wget_1_3#+0.7)*($wget_1_4#+0.9) ;
 *
 *   <br/>
 *   ($wget_1_1#+0.7)*($wget_1_2#+0.9)-100+50-($wget_1_3#+0.7)*($wget_1_4#+0.9)
 *    <br/>
 *   ($x#-$y#)*3 ;
 *   <br/>
 *   1*3*4+1-2*3+123 ;
 * @author onep@tree.com
 * @date 2019-05-23 17:34
 * @description
 */
public class FormulaTreeParser {

  String expression;

  /**注册自定义函数*/
  List<String> functionRegister = FunctionEnum.getDiyFunc();

  Stack<Expression> stack = new Stack<>();

  int index;

  Object var ;

  static List<Character>  numbers = Arrays.asList('0','1','2','3','4','5','6','7','8','9');

  static List<Character>  judgingNegativeNumbers = Arrays.asList('（',  '(',  '+',  '-',  '*',  '/',  '%', ',' ,'"','\'');
  public FormulaTreeParser(String expression ){
    this(expression,0);
  }

  public FormulaTreeParser(String expression,int index,String ... fn){
    this.expression=  expression.trim();//.replace(" ","");
    this.index =index;
    if (fn!=null){
      functionRegister.addAll(Arrays.asList(fn));
    }
    functionRegister.stream().filter(t->!StringUtils.isEmpty(t)).distinct().collect(Collectors.toList());
  }
  public Expression parse(){
    return parse(null);
  }
  public Expression parse(Expression parent){
    if (Objects.nonNull(parent)){
      stack.push(parent);
    }
    FormulaTreeParser newParse;
    Expression expVar;
    while (true){
      Part part = next();
      switch (part){
        case ERROR:
          ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_GRAMMA_INVALID ;
          throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
        case OPEN:
          newParse =  new FormulaTreeParser(expression,index);
          Expression includeExpre = newParse.parse(null);
          var = includeExpre;
          this.index=newParse.index;
          break;
        case CLOSE:
          if (stack.isEmpty()){
            resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_GRAMMA_INVALID ;
            throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
          }
          if (var instanceof Expression){
            stack.peek().add((Expression) var);
          }else{
            expVar = new Expression();
            expVar.setExpreVar(var);
            stack.peek().add(expVar);
          }
          return stack.firstElement();
        case VAR:
          break;
        case FUNCTION:
          newParse =  new FormulaTreeParser(expression,index+1);
          expVar = newParse.parse(stack.pop());
          var = expVar;
          this.index=newParse.index;
          break;
        case MD:
          findMulDiv(stack.pop());
          break;
        case MP:
          findMinPlu(stack.pop());
          break;
        case SPLIT:
          if (var instanceof Expression){
            expVar = (Expression) var;
          }else{
            expVar = new Expression();
            expVar.setExpreVar(var);
          }
          if (stack.size()>0){
            stack.peek().add(expVar);
          }
          //  内嵌函数是 如果stack.size>1表示内嵌 需要弹出
          if (stack.size()>1){
            stack.pop();
          }
          break;
        case END:
          expVar = new Expression();
          if (var instanceof Expression){
            if (stack.size()>0){
              stack.peek().add((Expression)var);;
            }else{
              stack.add((Expression) var);
            }
          }else{
            expVar.setExpreVar(var);
            stack.peek().add(expVar);
          }
          return stack.isEmpty()?null:stack.firstElement();
      }
    }
  }

  Part strConst(char quotationMark){
    int nextQuotationMark=expression.indexOf(quotationMark,index);
    var = expression.substring(index,nextQuotationMark);
    index+=((String) var).length()+1;
    return Part.VAR;
  }

  private Part next(){
    if (index>=expression.length()){
      return Part.END;
    }
    int indexThisPart =index;
    char nextPart  =expression.charAt(index);
    index++;
    if (nextPart==' '|| nextPart=='\t'||nextPart=='\n'){
      return next();
    }else if ('(' == nextPart||'（' == nextPart){
      return Part.OPEN;
    }else if(')' == nextPart||'）' == nextPart){
      return Part.CLOSE;
    }else if ('$' ==nextPart ){
      int indexVar = expression.indexOf('#',index);
      var = '$'+expression.substring(index,indexVar)+'#';
      index+=((String)var).length()-1;
      return Part.VAR;
    }else if (numbers.contains(nextPart)){
      String numberStr = nextPart+"";
      for (int i =index;i<expression.length();i++){
        nextPart = expression.charAt(index);
        if ((numbers.contains(nextPart))||(nextPart=='.'&& !numberStr.contains("."))){
          numberStr+=nextPart;
          index++;
        }else {
          break;
        }
      }
      var = numberStr.contains(".")?Double.parseDouble(numberStr):Long.parseLong(numberStr);
      return Part.VAR;
    }else if ('\''==nextPart){
      return strConst('\'');
    }else if ('"' == nextPart  ){
      return strConst('"');
    }else if (nextPart=='+'  ){
      Expression expr = new Expression();
      expr.setFunc(FunctionEnum.PLUS.method);
      stack.push(expr);
      return Part.MP;
    } else if ( nextPart=='-'  ){
      String fn = FunctionEnum.get(nextPart).method;
      Expression expr = new Expression();
      if (indexThisPart== 0
        || judgingNegativeNumbers.contains(expression.charAt(indexThisPart-1))){
        next();
        expr.setExpreVar(var);
        Expression negative = new Expression();
        negative.add(expr);
        negative.setFunc(FunctionEnum.NEGATIVE.method);
        var = negative;
        return Part.VAR;
      }else{
        expr.setFunc(fn);
        stack.push(expr);
      }
      return Part.MP;
    } else if ( nextPart=='/' ||nextPart=='*' ||nextPart=='%'){
      String fn = FunctionEnum.get(nextPart).method;
      Expression expr = new Expression();
      expr.setFunc(fn);
      stack.push(expr);
      return Part.MD;
    }else if(nextPart ==','){
      return Part.SPLIT;
    }else{
      for (String func:functionRegister){
        if (expression.indexOf(func,index-1)== index-1){
          index+=func.length()-1;
          Expression expr = new Expression();
          expr.setFunc(func);
          stack.push(expr);
          return Part.FUNCTION;
        }
      }
      ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_GRAMMA_INVALID ;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
  }

  private  void findMinPlu(Expression exp){
    Expression expVar  ;
    if (Objects.isNull(var)|| StringUtils.isEmpty(var.toString())){
      ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_GRAMMA_INVALID ;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
    if (var instanceof Expression){
      expVar = (Expression) var;
      var= null;
    }else{
      expVar = new Expression();
      expVar.setExpreVar(var);
    }

    if (stack.isEmpty()){
      stack.push(exp);
      stack.peek(). add(expVar);
      return ;
    }
    if (functionRegister.contains(stack.peek().getFunc())){
      // 处理自定义函数内嵌套函数
      exp.add(expVar);
      stack.peek().add(exp);
    }else {
      stack.peek().add(expVar);
      Expression topFunc = stack.firstElement();
      if (functionRegister.contains(stack.firstElement().getFunc())){
        exp. add(stack.peek());
        topFunc.add(exp);
        topFunc.remove(stack.peek());
        stack.clear();
        stack.push(topFunc);
      }else{
        stack.clear();
        exp. add(topFunc);
      }
    }
    stack.push(exp);
  }

  /**
   *  exp=stack.pop()
   * @param exp
   */
  private void findMulDiv(Expression exp){
    if (Objects.isNull(var)|| StringUtils.isEmpty(var.toString())){
      ResultCodeEnum resultCodeEnum = ResultCodeEnum.DEFINE_FORM_EXPRE_GRAMMA_INVALID ;
      throw new BusinessException(resultCodeEnum.message,resultCodeEnum.code);
    }
    Expression expVar = new Expression();
    if (var instanceof Expression){
      expVar = (Expression) var;
      var= null;
    }else{
      expVar.setExpreVar(var);
    }
    if(stack.isEmpty()){
      stack.push(exp);
      stack.peek().add(expVar);
      return ;
    }
    Expression expPop = stack.pop();
    if(Objects.equals(expPop.getFunc(),FunctionEnum.DIVIDE.method)
        ||Objects.equals(expPop.getFunc(),FunctionEnum.MULTIPLY.method)){
      exp.add(expPop);
      expPop.add(expVar);
      if(!stack.isEmpty()){
        stack.peek().remove(expPop);
        stack.peek().add(exp);
      }
      stack.push(exp);
    } else if(Objects.equals(expPop.getFunc(),FunctionEnum.PLUS.method)
        ||Objects.equals(expPop.getFunc(), MINUS.method)
        ||functionRegister.contains(expPop.getFunc())
    ){
      expPop. add(exp);
      exp.add(expVar);
      stack.push(expPop);
      stack.push(exp);
    }
  }
  enum Part{
    OPEN,
    MP ,//加减
    MD,//乘、除、求余
    VAR,
    FUNCTION,
    CLOSE,
    SPLIT,
    END,
    ERROR
  }
}
