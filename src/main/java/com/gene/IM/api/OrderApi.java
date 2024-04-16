package com.gene.IM.api;

import cn.hutool.json.JSONObject;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.entity.OrderInfo;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    /**
     *
     * @return
     */
    @NotNeedJWT
    @GetMapping("/showWaitingTask")
    public Map<String,Object> showWaitingList(){
        return orderService.showWaitingList();
    }

    /**
     *
     * @return
     */
    @NotNeedJWT
    @GetMapping("/showDoingTask")
    public Map<String,Object> showDoingList(){
        return orderService.showDoingList();
    }

    /**
     *
     * @param id
     * @return
     */
    @NotNeedJWT
    @GetMapping("/showDetail")
    public Map<String,Object> showOrderDetail(@RequestParam("orderID") int id){
        return orderService.showOrderDetail(id);
    }

    /**
     *
     * @param situation
     * @param sort
     * @return
     */
    @NotNeedJWT
    @GetMapping("/sortSelect")
    public Map<String,Object> sort(@RequestParam("situation") int situation,@RequestParam("sort") String sort){
        return orderService.orderBySituation(situation,sort);
    }

    /**
     *
     * @param body
     * @return
     */
    @NotNeedJWT
    @PostMapping("/vagueSelect")
    public Map<String,Object> vagueSelect(@RequestBody Map<String,Object> body){
        String source = (String) body.get("source");
        String key = (String) body.get("key");
        return orderService.vagueSelect(source,key);
    }

    /**
     *
     * @return
     */
    @NotNeedJWT
    @GetMapping("/showDoneTask")
    public Map<String,Object> showDoneList(){
        return orderService.showDoneList();
    }

    /**
     *
     * @param o
     * @return
     */
    @NotNeedJWT
    @PostMapping("/addNewOrder")
    public Map<String,Object> addNewOrder(@RequestBody OrderInfo o){
        return orderService.addOrder(o);
    }

    /**
     * 获取合格率
     *
     * @return
     */
    @NotNeedJWT
    @GetMapping("/getPassRate")
    public CommonResult<JSONObject> getPassRate(){
        int lineId =1;
        JSONObject json = new JSONObject().set("line",lineId).set("rate",orderService.getPassRate(lineId));
        return new CommonResult<JSONObject>(json);
    }

}
