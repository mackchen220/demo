package org.jeecg.modules.pay.servcie;

import net.sf.json.JSONArray;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.pay.model.PayModel;

public interface PayService {

    /**
     * 支付
     * @return
     */
    Result<?> wxOrderPay(PayModel payModel);


    /**
     * 支付回调
     * @param notifyDate
     * @return
     */
    JSONArray notify(String notifyDate);



}
