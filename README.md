# tushare-java-utils

tushare pro的java工具包 tushare相关参考https://tushare.pro/

DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);

类似tushare pro的python里实现的接口

tushareStockDataService.XXXX(param1,param2,....);（XXXX为apiName）

或者

tushareStockDataService.query(String apiName, Map<String, String> params, List<String> fields);
  
返回为:

com.tushare.bean.ApiResponse

具体可以参考 src/test/java/com/tushare/core/TushareStockDataServiceTest.java 里的测试用例代码
 
