package com.gene.IM.mapper;

import com.gene.IM.DTO.DeviceInfo;
import com.gene.IM.entity.Device;
import com.gene.IM.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceMapper {

    public DeviceInfo selectDeviceByMac(String mac);
    //插入新的设备
    public int insertDevice(Device device);
    //更新旧有设备，不光可以设备的基本信息，同时可以更新湿度，通过率那些
    public int updateDevice(Device device);
    public int deleteDevice(String mac);
    public OrderInfo getLineOrderDetail(int lineID);
}
