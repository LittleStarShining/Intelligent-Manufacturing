import random
import time
import math
import airsim
import os
import json

# 参数
agent_num = 10
initial_position = [[0, 0], [-5, 5], [12, 9], [6, -5], [5, 9], [-2, -6], [7, -9], [11, -8], [-8, 4], [13, 5]]
client = airsim.MultirotorClient()
k_sep = 7
k_coh = 1
k_mig = 1



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
def randerr(i, v_sep, v_coh, vlist, randmax = 10):
    randmax = randmax
    vlist[0] = random.uniform(-randmax, randmax)
    vlist[1] = random.uniform(-randmax, randmax)
    v_sep[i - 1][0] = random.uniform(-randmax, randmax)
    v_sep[i - 1][1] = random.uniform(-randmax, randmax)
    v_coh[i - 1][0] = random.uniform(-randmax,randmax)
    v_coh[i - 1][1] = random.uniform(-randmax, randmax)


# 相反值错误
def reverseerr(i, v_sep, v_coh, vlist):
    vlist[0] = -vlist[0]
    vlist[1] = -vlist[1]
    v_sep[i - 1][0] = -v_sep[i - 1][0]
    v_sep[i - 1][1] = -v_sep[i - 1][1]
    v_coh[i - 1][0] = -v_coh[i - 1][0]
    v_coh[i - 1][1] = -v_coh[i - 1][1]


def fly(err_agent, T_limit, randmax, T_switch, dis, Beta, Eta):
    err_agent = err_agent
    T_switch = T_switch
    dis = dis
    T_limit = T_limit
    randmax = randmax
    Beta = Beta
    Eta = Eta
    # 1 坏 2 好 3 好
    # 1连接2 2连接13 3连接2
    # 看谁先2先于谁断开连接
    result = [[[] for _ in range(10)] for _ in range(T_limit)]
    Topology = [[1, 1, 0, 0, 0, 0, 0, 0, 0, 0],
                [1, 1, 1, 0, 0, 0, 0, 0, 0, 0],
                [0, 1, 1, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]

    # 1为正常连接，0为原本就不连接，2为距离过大掉线
    list = [0 for i in range(agent_num)]
    Dist = [[0] * agent_num for i in range(agent_num)]  # 距离
    Cre = [[[] for i in range(agent_num)] for j in range(agent_num)]  # 可信度
    Reward = [[[] for i in range(agent_num)] for j in range(agent_num)]
    Weight = [[[] for i in range(agent_num)] for j in range(agent_num)]
    Cre_sum = [[] * 10 for i in range(agent_num)]
    Weight_sum = [[] * 10 for i in range(agent_num)]
    # 处理可信度
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
    v1 = [-2, 2]
    v2 = [2, -2.5]
    v3 = [-2, -2]
    v4 = [-4, -3]
    v5 = [6, -1]
    v6 = [2, 1]
    v7 = [6, -5]
    v8 = [0, -2]
    v9 = [7, -3]
    v10 = [9, 3]
    for i in range(1, agent_num + 1):
        locals()['tmp' + str(i)] = [0,0]
        locals()['de_v' + str(i)] = [0,0]
    for erragent in err_agent:
        if erragent[1] == 1:
            temp = erragent[0]
            locals()['v' + str(temp)][0] = 0
            locals()['v' + str(temp)][1] = 0
    # 连接无人机，解除锁，起飞
    for i in range(1, agent_num + 1):
        name = "UAV" + str(i)
        client.enableApiControl(True, name)
        client.armDisarm(True, name)
    for i in range(1, agent_num + 1):
        name = "UAV" + str(i)
        client.takeoffAsync(vehicle_name=name)

    time.sleep(2)
    # 避碰速度：( -k * r_ij / np.linalg.norm(r_ij) ) / N_i + 1
    # 聚集速度：( k * r_ij ) / N_i + 1
    # 速度一致：在拓扑结构里做平均
    flag2 = False  # 周期错误状态
    for t in range(T_limit):
        # 动态调整拓扑结构
        for index1, value1 in enumerate(Topology):
            for index2, value2 in enumerate(value1):
                if value2 == 0 or index1 == index2:  # 原本就不连接的和自己本身跳过
                    Dist[index1][index2] = 0
                    continue
                if distance(index1 + 1, index2 + 1) > dis:
                    Topology[index1][index2] = 2  # 掉线
                else:
                    Topology[index1][index2] = 1  # 重连接
                Dist[index1][index2] = distance(index1 + 1, index2 + 1)

        # 执行飞行命令
        for i in range(1, agent_num + 1):
            name = "UAV" + str(i)
            if i > 3:
                client.moveByVelocityZAsync(locals()['v' + str(i)][0] + (0.2 * v_sep[i - 1][0] + 0.3 * v_coh[i - 1][0]),
                                            locals()['v' + str(i)][1] + (0.2 * v_sep[i - 1][1] + 0.3 * v_coh[i - 1][1]),
                                            -1,
                                            1.5, vehicle_name=name)
            else:
                client.moveByVelocityZAsync(locals()['v' + str(i)][0] + (0.2 * v_sep[i - 1][0] + 0.3 * v_coh[i - 1][0]),
                                            locals()['v' + str(i)][1] + (0.2 * v_sep[i - 1][1] + 0.3 * v_coh[i - 1][1]),
                                            random.randint(-5, -2),
                                            1.5, vehicle_name=name)
        time.sleep(2)
        V1 = client.simGetGroundTruthKinematics(vehicle_name="UAV1").linear_velocity
        V2 = client.simGetGroundTruthKinematics(vehicle_name="UAV2").linear_velocity
        V3 = client.simGetGroundTruthKinematics(vehicle_name="UAV3").linear_velocity
        print(f'{t}时')
        print(f'二号无人机到1号无人机位置{distance(2, 1)}')
        print(f'二号无人机到3号无人机位置{distance(2, 3)}')
        print(f'二号无人机速度{V2.x_val, V2.y_val}')
        print(f'三号无人机速度{V3.x_val, V3.y_val}')
        print(f'1号无人机速度{V1.x_val, V1.y_val}')
        print(f'二号无人机对1号无人机的可信度{round(Cre[1][0][-1], 3)}')
        print(f'二号无人机对3号无人机的可信度{round(Cre[1][2][-1], 3)}')
        print(f'二号无人机对1号无人机的权重{Weight[1][0]}')
        print(f'二号无人机对3号无人机的权重{Weight[1][2]}')
        #print(Reward)
        # 使用实际速度替换
        #for i in range(1, agent_num + 1):
        #    name = "UAV" + str(i)
        #    v = client.simGetGroundTruthKinematics(vehicle_name=name).linear_velocity
        #    locals()['v' + str(i)][0] = round(v.x_val, 3)
        #    locals()['v' + str(i)][1] = round(v.y_val, 3)

        for i, value1 in enumerate(Topology):
            name_i = "UAV" + str(i + 1)
            N_agent = value1.count(1) + 1
            sum_cre_i = 0
            sum_weight = 0
            for j, value2 in enumerate(value1):
                if i == j:
                    continue
                if value2 == 0 or value2 == 2:
                    continue
                name_j = "UAV" + str(j + 1)
                r_ij = distance(i + 1, j + 1)
                #print("无人机", i+1, "与无人机", j+1, "之间的距离：", distance(i+1, j+1))
                rx_ij = (getpos(i + 1).x_val + initial_position[i][0]) - (getpos(j + 1).x_val + initial_position[j][0])
                ry_ij = (getpos(i + 1).y_val + initial_position[i][1]) - (getpos(j + 1).y_val + initial_position[j][1])
                v_sep[i][0] += k_sep * rx_ij / r_ij
                v_sep[i][1] += k_sep * ry_ij / r_ij
                v_coh[i][0] += -k_coh * rx_ij
                v_coh[i][1] += -k_coh * ry_ij
                v_i = client.simGetGroundTruthKinematics(vehicle_name=name_i).linear_velocity
                v_j = client.simGetGroundTruthKinematics(vehicle_name=name_j).linear_velocity
                Reward[i][j].append(math.exp(abs(v_i.x_val - v_j.x_val) + abs(v_i.y_val - v_j.y_val) * Beta))
                # print(i,j,t,Re[i][j][t])

                Cre[i][j].append(Cre[i][j][-1] + Eta * (Reward[i][j][-1] - Cre[i][j][-1]))

                #print(t)
                #print(Cre)
                sum_cre_i += Cre[i][j][-1]
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
                Weight[i][j].append((1 - 1 / (agent_num + 1)) * Cre[i][j][-1] / Cre_sum[i][-1])
                sum_weight_i += Weight[i][j][-1]
            Weight_sum[i].append(sum_weight_i)

        for i in range(agent_num):
            for j, value in enumerate(Topology[i]):
                if i == j:
                    continue
                if value == 0 or 2:
                    continue
                locals()['v' + str(i + 1)][0] += Weight[i][j][-1] * (
                        locals()['v' + str(i + 1)][0] - locals()['v' + str(j + 1)][0])
                locals()['v' + str(i + 1)][1] += Weight[i][j][-1] * (
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
                        randerr(index, v_sep, v_coh, vlist, randmax)
                    elif errtype == 3:
                        reverseerr(index, v_sep, v_coh, vlist)
                elif errperiod == 1:  # 拜占庭错误
                    iserror = bool(random.getrandbits(1))
                    print(f'是否错误：{iserror}')
                    if not iserror:
                        pass
                    else:
                        if errtype == 1:
                            consterr(index, v_sep, v_coh, vlist)
                        elif errtype == 2:
                            randerr(index, v_sep, v_coh, vlist, randmax)
                        elif errtype == 3:
                            reverseerr(index, v_sep, v_coh, vlist)
                elif errperiod == 2:  # 周期错误 周期为5
                    if t % T_switch == 0:
                        if not flag2:
                            flag2 = True
                        else:
                            flag2 = False
                    if flag2:
                        if errtype == 1:
                            consterr(index, v_sep, v_coh, vlist)
                        elif errtype == 2:
                            randerr(index, v_sep, v_coh, vlist, randmax)
                        elif errtype == 3:
                            reverseerr(index, v_sep, v_coh, vlist)
                    else:
                        pass
    print(Topology)
    return result


"""
记录不同条件下（3*3*3)，以下属性与T（15，30，50）的关系
条件：错误类型3种*错误周期3种*错误无人机个数（1，4，7）
属性：每个无人机的vx, vy, state(与邻居中心的距离)
计算出四个值：state的平均值和方差、速度的平均值和方差
对比所有的情况下，qc算法和不使用qc算法的四个值
在以上基础上，调整参数（学习率，随机错误中速度变化范围，周期变化频率，拓扑结构变化范围）
"""


def main(err_agent=[[1,2,0], [4, 1, 0], [5, 1, 0], [6, 1, 0], [7, 1, 0], [8, 1, 0], [9, 1, 0], [10, 1, 0]], T_limit=30, randmax=10, T_switch=3, dis=25, Beta=0.01, Eta=5):
    client.reset()
    time.sleep(1)
    res = fly(err_agent, T_limit, randmax, T_switch, dis, Beta, Eta)
    return res


if __name__ == "__main__":
    main()