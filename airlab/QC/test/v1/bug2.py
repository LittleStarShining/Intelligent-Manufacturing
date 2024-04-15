import airsim
client = airsim.MultirotorClient()
name = "UAV3"
client.reset()
while 50:
    client.moveByVelocityZAsync(10, 10, 0,
                                1.5, vehicle_name=name)
    print(client.simGetGroundTruthKinematics(vehicle_name=name).linear_velocity.x_val)

print(client.simGetGroundTruthKinematics(vehicle_name=name).linear_velocity.x_val)