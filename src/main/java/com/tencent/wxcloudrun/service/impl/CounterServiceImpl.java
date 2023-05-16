package com.tencent.wxcloudrun.service.impl;

import cn.hutool.http.HttpUtil;
import com.tencent.wxcloudrun.dao.CountersMapper;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CounterServiceImpl implements CounterService {

  final CountersMapper countersMapper;

  public CounterServiceImpl(@Autowired CountersMapper countersMapper) {
    this.countersMapper = countersMapper;
  }
  private static final String GET_TIME = "https://mallwx.ltg.cn/timeAndPerformanceAction/findScenicSpotsTime";

  @Override
  public Optional<Counter> getCounter(Integer id) {
    try {
      this.getTimes();
    }catch (Exception e){
      e.printStackTrace();
    }
    return Optional.ofNullable(countersMapper.getCounter(id));
  }

  @Override
  public void upsertCount(Counter counter) {
    countersMapper.upsertCount(counter);
  }

  @Override
  public void clearCount(Integer id) {
    countersMapper.clearCount(id);
  }


  /**
   * 获取时间段
   * orderNetType XCXGP -- APPGP
   */
  public void getTimes() throws JSONException {
    Map<Long, Integer> timeMap = new HashMap<>();
    JSONObject timeParam = new JSONObject();
    timeParam.put("businessOrgId", "1");
    timeParam.put("scenicSpotsId", "10000000000002");
    timeParam.put("timeControlType", "XSSKKC");
    timeParam.put("controlDate", LocalDate.now().plusDays(1));
    timeParam.put("orderNetType", "XCXGP");
    String result = HttpUtil.post(GET_TIME,timeParam.toString());
    System.out.println(result);
  }

}
