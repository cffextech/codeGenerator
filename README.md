# code_generator
<!--TODO 持续更新中-->
## 需求分析：   
1. 多种模型表现方式；
2. 多种模板；
3. 命令行接口；
4. 参数设置：模型文件（文件夹），模板文件（文件夹）, 输出文件（文件夹）
5. 前提
（1）pdm产生的json格式是固定的，是规定好的；
（2）用户可以自定义自己的模型和模板，编写特定的json文件和tpl文件；
6. 测试用例，依据模型不同，总共8组测试用例；
<table>
<tr>
    <th>模型(pdm或者json)</th> 
    <th>模板</th>
    <th>输出</th>
</tr>
<tr>
<td>file1</td>
<td>file2</td>
<td>file3</td>
</tr>
<tr>
<td>file1</td>
<td>directory1</td>
<td>directory3</td>
</tr>
<tr>
<td>directory1</td>
<td>file2</td>
<td>file3</td>
</tr>
<tr>
<td>directory1</td>
<td>directory2</td>
<td>directory3</td>
</tr>
</table>

##命令行方式
1. 输入源是pdm或者json
2. 生成out文件夹；
3. 打包成tar包；

##设计模式构建

##存储
1. Mongodb数据库；

##后端
1. 框架：play framework;
2. 接口：restful API（待更新）

##前端
1. 框架：Angular；
2. 设计图
