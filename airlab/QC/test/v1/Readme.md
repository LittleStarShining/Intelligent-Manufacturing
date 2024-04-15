# 测试1
## 测试任务
记录不同条件下（3*3*3)，以下属性与T（15，30，50）的关系，计算出四个值：state的平均值和方差、速度的平均值和方差，对比所有的情况下，qc算法和不使用
qc算法的四个值，在以上基础上，调整参数看影响
- 条件：错误类型3种*错误周期3种*错误无人机个数（1，4，7）
- 属性：每个无人机的vx, vy, state(最终与邻居中心的距离)
- 参数：学习率，随机错误中速度变化范围，周期变化频率，拓扑结构变化范围
## 测试方法
使用Test.py进行测试，循环遍历所有条件，将记录的信息保存在csv中，输出最终结果在控制台并保存到csv中
## 测试结果格式
- final.csv
- /xxx-xxx-xxx/xxx-xxx-xxx-QC.csv
- /xxx-xxx-xxx/xxx-xxx-xxx.csv
- /xx-xxx-xxx/xxx-xxx-xxx-QC.png
- /xxx-xxx-xxx/xxx-xxx-xxx.png
- /xxx-xxx-xxx/result.csv
- /xxx-xxx-xxx/result-QC.csv
### /xxx-xxx-xxx/xxx-xxx-xxx.csv
首列为10个无人机的名字，每一行表示一个时间T，记录数据格式为[vx, vy]
### /xxx-xxx-xxx/xxx-xxx-xxx-QC.csv
首列为10个无人机的名字，每一行表示一个时间T，记录数据格式为[vx, vy]
### /xxx-xxx-xxx/result.csv
每一行代表每一个无人机最终T时的state
多两行记录平均state和方差state
### /xxx-xxx-xxx/result-QC.csv
每一行代表每一个无人机最终T时的state
多两行记录平均state和方差state
### finial.csv
记录：xxx-xxx-xxx:[[qc的平均state，qc的state方差],[Notqc的平均state，NOtqc的state方差]]