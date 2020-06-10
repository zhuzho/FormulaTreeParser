
 # math formula parse 简单的数学公式解析，将数学公式解析为树形结构，使用BigDecimal做计算 支持部分计算函数

 *  数学公式计算解析,暂时不支持函数内嵌套函数或公式
 *  ($wget_1_1#+0.7*0.3/0.1-1)*avg(1,3,4,5,$aa#)*($wget_1_2#+0.9)-100" ;
 *  ($wget_1_1#+0.7)*($wget_1_2#+0.9)-100+50-($wget_1_3#+0.7)*($wget_1_4#+0.9) ;
 *   ($x#-$y#)*3 ;
 *   1*3*4+1-2*3+123 ;
``` java
 Map<String,Object> params = new HashMap<>(）;
 Expression formula = formula = new FormulaTreeParser("($wget_1_1#+0.7*0.3/0.1-1)*avg(1,sum(3,2),4,5,$aa#+6-1)*($wget_1_2#+0.9)-100",0).parse();
 FunctionEnum functionEnum = FunctionEnum.get(formula.getFunc());
 if (Objects.nonNull(functionEnum)){
  System.out.println(functionEnum.calculator.calculate(formula,params))
 }

```
