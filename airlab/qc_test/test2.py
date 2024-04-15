import csv
import random
import os
import airsim
import qc2
import noy_qc2
from enum import Enum
# 枚举
class ErrorType(Enum):  # 错误类型
    CONSTANT = 1
    RANDOM = 2
    REVERSE = 3
class ErrorPeriod(Enum):  # 错误周期
    ALWAYS = 0
    BYZANTINE = 1
    PERIODIC = 2
# 参数
agent_num = 10
client = airsim.MultirotorClient()
# 生成错误无人机
err_agent = [[1, 1, 0]]
# 生成总步数
t_limit = 30
# 初始化无人机速度
# init_velocity = [[] for _ in range(agent_num)]  # 初始化速度
#for i in range(1, agent_num + 1):
#    init_velocity[i - 1].append(random.randint(-5, 5))
#    init_velocity[i - 1].append(random.randint(-5, 5))
init_velocity = [[0, 0], [4, -1], [-5, 3], [3, -1], [3, 2], [0, 0], [3, 1], [4, -2], [2, -5], [-4, -2]]
qc_Tlist = []
notqc_Tlist = []

for error_type in ErrorType:
    for error_period in ErrorPeriod:
        # 新建文件夹
        folder_name = f'folder_{error_type}_{error_period}'
        if not os.path.exists(folder_name):
            os.makedirs(folder_name)
        filename_qc = f'result_{error_type}_{error_period}.csv'
        filename_notqc = f'result_{error_type}_{error_period}.csv'
        filepath_qc = os.path.join(folder_name, filename_qc)
        filepath_notqc = os.path.join(folder_name, filename_notqc)
        with open(filepath_qc, 'a') as f_qc, open(filepath_notqc, 'a') as f_notqc:
            writer_qc = csv.writer(f_qc)
            writer_notqc = csv.writer(f_notqc)
            title1 = ['异常无人机数量qc', '一致所需T']
            title2 = ['异常无人机数量notqc', '一致所需T']
            writer_qc.writerow(title1)
            writer_notqc.writerow(title2)
            for j in range(1, 11):
                if j == 1:
                    err_agent = [[1, error_type, error_period]]
                else:
                    err_agent.append([j, error_type, error_period])

                # 不使用qc算法
                client.reset()
                T1 = noy_qc2.main(err_agent, t_limit, init_velocity)
                if T1 is None:
                    T1 = 999
                # 记录不使用qc算法
                data_notqc = [j, noy_qc2.main(err_agent, t_limit, init_velocity)]
                writer_notqc.writerow(data_notqc)
                # 使用qc算法
                client.reset()
                T2 = qc2.main(err_agent, t_limit, init_velocity)
                if T2 is None:
                    T2 = 999
                # 记录使用qc算法
                data_qc = [j, qc2.main(err_agent, t_limit, init_velocity)]
                writer_qc.writerow(data_qc)

print("OK")
