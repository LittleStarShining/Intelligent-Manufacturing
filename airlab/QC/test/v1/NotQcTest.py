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


# 速度更新函数
def fly(err_agent, T_limit, randmax, T_switch, dis):
    err_agent = err_agent
    T_switch = T_switch
    dis = dis
    T_limit = T_limit
    randmax = randmax
    result = [[[] for _ in range(10)] for _ in range(T_limit)]
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
    Dist = [list for i in range(agent_num)]  # 距离
    Trust = [list for i in range(agent_num)]  # 距离变化量之和
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
    v1 = [-8, 8]
    v2 = [3, -7]
    v3 = [9, -8]
    v4 = [-4, -3]
    v5 = [6, -1]
    v6 = [2, 1]
    v7 = [6, -5]
    v8 = [0, -2]
    v9 = [7, -3]
    v10 = [9, 3]
    #for i in range(1, agent_num + 1):
        #locals()['v' + str(i)] = []
        #locals()['v' + str(i)].append(random.randint(-10, 10))
        #locals()['v' + str(i)].append(random.randint(-10, 10))
    for erragent in  err_agent:
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
        for index1, value1 in enumerate(Topology):  # index1 第几个无人机
            for index2,value2 in  enumerate(value1):
                if value2 == 0 or index2 == index1:  # 原本就不连接的和自己本身跳过
                    continue
                if distance(index1+1, index2+1) > dis:
                    Topology[index1][index2] = 2  # 掉线
                else:
                    Topology[index1][index2] = 1  # 重连接

        for i in range(agent_num):
            # 记录vx，vy
            result[t][i].append(round(locals()['v' + str(i+1)][0], 2))
            result[t][i].append(round(locals()['v' + str(i+1)][1], 2))

        for i in range(1, agent_num + 1):
            # 飞
            name = "UAV" + str(i)
            client.moveByVelocityZAsync(locals()['v' + str(i)][0] + (0.2 * v_sep[i - 1][0] + 0.3 * v_coh[i - 1][0]),
                                        locals()['v' + str(i)][1] + (0.2 * v_sep[i - 1][1] + 0.3 * v_coh[i - 1][1]),
                                        random.randint(-5, -2),
                                        1.5, vehicle_name=name)
        time.sleep(2)
        # 用实际速度替代
        for i in range(1, agent_num + 1):
            name = "UAV" + str(i)
            v = client.simGetGroundTruthKinematics(vehicle_name=name).linear_velocity
            if i ==2:
                print(round(locals()['v'+str(i)][0], 3), round(v.x_val, 3))
            locals()['v' + str(i)][0] = round(v.x_val, 3)
            locals()['v' + str(i)][1] = round(v.y_val, 3)
        for i, value1 in enumerate(Topology):
            name_i = "UAV" + str(i+1)
            N_agent = value1.count(1)
            for j,value2 in  enumerate(value1):
                if i == j:
                    continue
                if value2 ==0 or value2 == 2:
                    continue
                name_j = "UAV" + str(j+1)
                r_ij = distance(i + 1, j + 1)
                #print("无人机", i+1, "与无人机", j+1, "之间的距离：", distance(i+1, j+1))

                rx_ij = (getpos(i+1).x_val + initial_position[i][0]) - (getpos(j+1).x_val + initial_position[j][0])
                ry_ij = (getpos(i+1).y_val + initial_position[i][1]) - (getpos(j+1).y_val + initial_position[j][1])
                v_sep[i][0] += k_sep * rx_ij / r_ij
                v_sep[i][1] += k_sep * ry_ij / r_ij
                v_coh[i][0] += -k_coh * rx_ij
                v_coh[i][1] += -k_coh * ry_ij
            v_sep[i][0] = v_sep[i][0] / N_agent
            v_sep[i][1] = v_sep[i][1] / N_agent
            v_coh[i][0] = v_coh[i][0] / N_agent
            v_coh[i][1] = v_coh[i][1] / N_agent

        for index1, value1 in enumerate(Topology):
            for index2,value2 in  enumerate(value1):
                if index1==index2:
                    continue
                if value2 == 2:
                    continue
                locals()['v' + str(index1+1)][0]+=value2* locals()['v' + str(index2+1)][0]
                locals()['v' + str(index1 + 1)][1]+=value2* locals()['v' + str(index2+1)][1]
            locals()['v' + str(index1+1)][0]/=value1.count(1)
            locals()['v' + str(index1+1)][1]/=value1.count(1)

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
                        randerr(index, v_sep, v_coh, vlist, randmax = 10)
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
                            randerr(index, v_sep, v_coh, vlist,randmax = 10)
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
                            randerr(index, v_sep, v_coh, vlist, randmax = 10)
                        elif errtype == 3:
                            reverseerr(index, v_sep, v_coh, vlist)
                    else:
                        pass
    # 循环结束
    # 计算中心点centerpoints
    centerpoint_list = []
    for index1, value1 in enumerate(Topology):
        centerpoint_x = 0
        centerpoint_y = 0
        num = 0
        for index2, value2 in enumerate(value1):
            if value2 == 1:
                num += 1
                centerpoint_x += client.getMultirotorState(vehicle_name="UAV"+str(index2+1)).kinematics_estimated.position.x_val + initial_position[index2][0]
                centerpoint_y += client.getMultirotorState(vehicle_name="UAV"+str(index2+1)).kinematics_estimated.position.y_val + initial_position[index2][1]
        centerpoint = [round(centerpoint_x / num, 2), round(centerpoint_y / num, 2)]
        centerpoint_list.append(centerpoint)
    # 计算所有无人机的state
    for i in range(agent_num):
        state_x = client.getMultirotorState(vehicle_name="UAV"+str(i+1)).kinematics_estimated.position.x_val + initial_position[i][0] - centerpoint_list[i][0]
        state_y = client.getMultirotorState(vehicle_name="UAV"+str(i+1)).kinematics_estimated.position.y_val + initial_position[i][1] - centerpoint_list[i][1]
        state = round(math.sqrt(state_x * state_x + state_y * state_y), 2)
        result[-1][i].append(state)
    return result

"""
输入无人机错误数组、T_limit、randmax,T_switch, dis
输出一个二维数组，有T行，每一行有num列，每一个列的数据是[vx, vy]，最后一列是[vx, vy, state]
"""


def main(err_agent=[[1,1,0]], T_limit=10, randmax=10, T_switch=5, dis=25, Beta = 0.01, Eta = 5):
    client.reset()
    time.sleep(1)
    res = fly(err_agent, T_limit, randmax, T_switch, dis)
    return res


if __name__ == "__main__":
    main()
