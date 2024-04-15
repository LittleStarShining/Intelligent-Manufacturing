import random
import time
import math
import airsim
import os
import csv
import json

# 参数
agent_num = 10
initial_position = [[0, 0], [-5, 5], [12, 9], [6, -5], [5, 9], [-2, -6], [7, -9], [11, -8], [-8, 4], [13, 5]]
client = airsim.MultirotorClient()
k_sep = 7
k_coh = 1
k_mig = 1
Beta = 0.01
Eta = 5


# 计算距离函数
def distance(i, j):
    a = "UAV" + str(i)
    b = "UAV" + str(j)
    x = (client.getMultirotorState(vehicle_name=a).kinematics_estimated.position.x_val + initial_position[i - 1][0]) - (
            client.getMultirotorState(
                vehicle_name=b).kinematics_estimated.position.x_val + initial_position[j - 1][0])
    y = (client.getMultirotorState(vehicle_name=a).kinematics_estimated.position.y_val + initial_position[i - 1][1]) - (
            client.getMultirotorState(
                vehicle_name=b).kinematics_estimated.position.y_val + initial_position[j - 1][1])
    return math.sqrt(x * x + y * y)


# 位置函数返回无人机位置参数
def getpos(a):
    a = "UAV" + str(a)
    return client.simGetGroundTruthKinematics(vehicle_name=a).position


# 常值错误
def consterr(i, v_sep, v_coh, vlist):
    vlist[0] = 0
    vlist[1] = 0
    v_sep[i - 1][0] = 0
    v_sep[i - 1][1] = 0
    v_coh[i - 1][0] = 0
    v_coh[i - 1][1] = 0


# 随机错误
def randerr(i, v_sep, v_coh, vlist):
    vlist[0] = random.uniform(-10, 10)
    vlist[1] = random.uniform(-10, 10)
    v_sep[i - 1][0] = random.uniform(-10, 10)
    v_sep[i - 1][1] = random.uniform(-10, 10)
    v_coh[i - 1][0] = random.uniform(-10, 10)
    v_coh[i - 1][1] = random.uniform(-10, 10)


# 相反值错误
def reverseerr(i, v_sep, v_coh, vlist):
    vlist[0] = -vlist[0]
    vlist[1] = -vlist[1]
    v_sep[i - 1][0] = -v_sep[i - 1][0]
    v_sep[i - 1][1] = -v_sep[i - 1][1]
    v_coh[i - 1][0] = -v_coh[i - 1][0]
    v_coh[i - 1][1] = -v_coh[i - 1][1]


def fly(err_agent, t_limit, init_velocity):
    err_agent = err_agent
    # 错误无人机列表，只包含编号
    abnormal_agent = []
    for err in err_agent:
        abnormal_agent.append(err[0])
    # 无人机的拓扑结构, listx表示与x相连的agent
    # 无人机号,是否同步,距离,可信度

    Topology = [[1, 0, 0, 1, 0, 0, 0, 1, 0, 0],
                [0, 1, 0, 1, 0, 0, 1, 0, 1, 0],
                [0, 0, 1, 0, 1, 1, 0, 0, 0, 1],
                [1, 1, 0, 1, 1, 1, 0, 1, 1, 1],
                [0, 0, 1, 1, 1, 1, 1, 1, 1, 0],
                [0, 0, 1, 1, 1, 1, 1, 0, 1, 0],
                [0, 1, 0, 0, 1, 1, 1, 1, 0, 1],
                [1, 0, 0, 1, 1, 0, 1, 1, 0, 0],
                [0, 1, 0, 1, 1, 1, 0, 0, 1, 0],
                [0, 0, 1, 1, 0, 0, 1, 0, 0, 1]]

    # 1为正常连接，0为原本就不连接，2为距离过大掉线
    list = [0 for i in range(agent_num)]
    Dist = [[0] * agent_num for i in range(agent_num)]  # 距离
    Cre = [[[] * 10 for i in range(agent_num)] for j in range(agent_num)]  # 可信度
    Reward = [[[] * 10 for i in range(agent_num)] for j in range(agent_num)]
    Weight = [[[] * 10 for i in range(agent_num)] for j in range(agent_num)]
    Cre_sum = [[] * 10 for i in range(agent_num)]
    Weight_sum = [[] * 10 for i in range(agent_num)]
    VX = [[]*t_limit for i in range(agent_num)]
    VY = [[]*t_limit for i in range(agent_num)]
    for index1, value1 in enumerate(Topology):
        for index2, value2 in enumerate(value1):
            if index1 == index2:
                continue
            if value2 == 1:
                Cre[index1][index2].append(1)
    # 错误类型：
    # 1 错误：常值 避碰速度，聚集速度，同步速度都为0
    # 2 错误：随机值 随机生成避碰 聚集 同步速度
    # 3 错误：相反值 避碰速度 聚集速度，同步速度取反
    # 错误周期类型：
    # 0 一直发生错误
    # 1 拜占庭错误
    # 2 周期错误
    # err_agentL列表记录错误无人机编号，错误类型，错误周期类型

    v_sep = [[0, 0] for _ in range(agent_num)]
    v_coh = [[0, 0] for _ in range(agent_num)]
    # 初始化无人机速度
    for i in range(1, agent_num + 1):
        locals()['v' + str(i)] = []
        locals()['v' + str(i)].append(init_velocity[i - 1][0])
        locals()['v' + str(i)].append(init_velocity[i - 1][1])

    # 连接无人机，解除锁，起飞
    for i in range(1, agent_num + 1):
        name = "UAV" + str(i)
        client.enableApiControl(True, name)
        client.armDisarm(True, name)
    for i in range(1, agent_num + 1):
        name = "UAV" + str(i)
        client.takeoffAsync(vehicle_name=name)

    time.sleep(2)

    # boid flock算法原理：避碰速度v_sep+聚集速度v_coh ? 速度一致
    # 所有的速度计算是建立在同一拓扑结构下的
    # 避碰速度：( -k * r_ij / np.linalg.norm(r_ij) ) / N_i + 1
    # 聚集速度：( k * r_ij ) / N_i + 1
    # 速度一致：在拓扑结构里做平均
    flag2 = False  # 周期错误状态
    T = 5  # 周期错误周期
    dis = 25  # 判定距离
    for t in range(t_limit):
        for temp in Topology:
            print(temp)
        print("_________________")
        for index1, value1 in enumerate(Topology):  # index1 第几个无人机
            for index2, value2 in enumerate(value1):
                if value2 == 0 or index1 == index2:  # 原本就不连接的和自己本身跳过
                    Dist[index1][index2] = 0
                    continue
                Dist[index1][index2] = distance(index1 + 1, index2 + 1)

        for i in range(1, agent_num + 1):
            name = "UAV" + str(i)
            client.moveByVelocityZAsync(locals()['v' + str(i)][0] + (0.2 * v_sep[i - 1][0] + 0.3 * v_coh[i - 1][0]),
                                        locals()['v' + str(i)][1] + (0.2 * v_sep[i - 1][1] + 0.3 * v_coh[i - 1][1]),
                                        random.randint(-5, -2),
                                        1.2, vehicle_name=name)
        time.sleep(1)
        for i, value1 in enumerate(Topology):
            name_i = "UAV" + str(i + 1)
            N_agent = value1.count(1)
            sum_cre_i = 0
            sum_weight = 0
            for j, value2 in enumerate(value1):
                if i == j:
                    continue
                if value2 == 0 or value2 == 2:
                    continue
                name_j = "UAV" + str(j + 1)
                r_ij = distance(i + 1, j + 1)
                print("无人机", i + 1, "与无人机", j + 1, "之间的距离：", distance(i + 1, j + 1))

                rx_ij = (getpos(i + 1).x_val + initial_position[i][0]) - (getpos(j + 1).x_val + initial_position[j][0])
                ry_ij = (getpos(i + 1).y_val + initial_position[i][1]) - (getpos(j + 1).y_val + initial_position[j][1])
                v_sep[i][0] += k_sep * rx_ij / r_ij
                v_sep[i][1] += k_sep * ry_ij / r_ij
                v_coh[i][0] += -k_coh * rx_ij
                v_coh[i][1] += -k_coh * ry_ij
                Reward[i][j].append(math.exp(-abs(distance(i + 1, j + 1)) * Beta))
                # print(i,j,t,Re[i][j][t])
                Cre[i][j].append(Cre[i][j][t] + Eta * (Reward[i][j][t] - Cre[i][j][t]))
                sum_cre_i += Cre[i][j][t + 1]
            Cre_sum[i].append(sum_cre_i)
            v_sep[i][0] = v_sep[i][0] / N_agent
            v_sep[i][1] = v_sep[i][1] / N_agent
            v_coh[i][0] = v_coh[i][0] / N_agent
            v_coh[i][1] = v_coh[i][1] / N_agent

        for i, value1 in enumerate(Topology):
            sum_weight_i = 0
            for j, value2 in enumerate(value1):
                if i == j:
                    continue
                if value2 == 2 or value2 == 0:
                    continue
                Weight[i][j].append((1 - 1 / (agent_num + 1)) * Cre[i][j][t + 1] / Cre_sum[i][t])
                sum_weight_i += Weight[i][j][t]
                locals()['v' + str(i + 1)][0] += value2 * locals()['v' + str(j + 1)][0]
                locals()['v' + str(i + 1)][1] += value2 * locals()['v' + str(j + 1)][1]
            Weight_sum[i].append(sum_weight_i)
            locals()['v' + str(i + 1)][0] /= value1.count(1)
            locals()['v' + str(i + 1)][1] /= value1.count(1)

        for i in range(agent_num):
            for j, value in enumerate(Topology[i]):
                if i == j:
                    continue
                if value == 0:
                    continue
                locals()['v' + str(i + 1)][0] += Weight[i][j][t] * (
                        locals()['v' + str(i + 1)][0] - locals()['v' + str(j + 1)][0])
                locals()['v' + str(i + 1)][1] += Weight[i][j][t] * (
                        locals()['v' + str(i + 1)][1] - locals()['v' + str(j + 1)][1])

        if err_agent:
            for erragent in err_agent:
                index = erragent[0]  # 无人机编号
                errtype = erragent[1]  # 错误类型
                errperiod = erragent[2]  # 错误周期
                vlist = locals()['v' + str(index)]
                if errperiod == 0:
                    if errtype == 1:
                        consterr(index, v_sep, v_coh, vlist)
                    elif errtype == 2:
                        randerr(index, v_sep, v_coh, vlist)
                    elif errtype == 3:
                        reverseerr(index, v_sep, v_coh, vlist)
                elif errperiod == 1:  # 拜占庭错误
                    iserror = bool(random.getrandbits(1))
                    if not iserror:
                        pass
                    else:
                        if errtype == 1:
                            consterr(index, v_sep, v_coh, vlist)
                        elif errtype == 2:
                            randerr(index, v_sep, v_coh, vlist)
                        elif errtype == 3:
                            reverseerr(index, v_sep, v_coh, vlist)
                elif errperiod == 2:  # 周期错误 周期为5
                    if t % T == 0:
                        if not flag2:
                            flag2 = True
                        else:
                            flag2 = False
                    if flag2:
                        if errtype == 1:
                            consterr(index, v_sep, v_coh, vlist)
                        elif errtype == 2:
                            randerr(index, v_sep, v_coh, vlist)
                        elif errtype == 3:
                            reverseerr(index, v_sep, v_coh, vlist)
                    else:
                        pass

        # 记录每一个时间T，VX和VY
        """
        每一行代表一个无人机在所有t下的速度
        """
        for i in range(agent_num):
            VX[i].append(round(locals()['v'+str(i+1)][0], 2))
    return VX, VY


def main(err_agent=[[1, 3, 0], [2, 3, 0]], t_limit=30, init_velocity=[[0, 0], [4, -1], [-5, 3], [3, -1], [3, 2], [0, 0], [3, 1], [4, -2], [2, -5], [-4, -2]]):
    VX, VY = fly(err_agent, t_limit, init_velocity)
    #for i in range(agent_num):
    #    print(VX[i])
    return VX, VY


if __name__ == "__main__":
    main()