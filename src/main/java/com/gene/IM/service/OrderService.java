package com.gene.IM.service;

import java.util.Map;

public interface OrderService {
    public Map<String,Object> showWaitingList();
    public Map<String,Object> showDoingList();
    public Map<String,Object> showDoneList();
    public Map<String,Object> showOrderDetail(int id);

    public Map<String,Object> orderBySituation(int situation, String sort);

    public Map<String,Object> vagueSelect(String source,String key);

}
