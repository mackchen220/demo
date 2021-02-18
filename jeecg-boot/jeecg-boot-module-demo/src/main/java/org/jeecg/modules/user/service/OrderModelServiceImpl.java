package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.ProjectInfoMapper;
import org.jeecg.modules.user.mapper.ProjectMapper;
import org.jeecg.modules.user.model.vo.OrderModelVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.OrderModelMapper;
import org.jeecg.modules.user.model.OrderModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderModelServiceImpl implements OrderModelService{

    @Resource
    private OrderModelMapper orderModelMapper;
    @Resource
    private ProjectMapper projectMapper;

    @Override
    public int insertSelective(OrderModel record) {
        return orderModelMapper.insertSelective(record);
    }

    @Override
    public OrderModel selectByPrimaryKey(String id) {
        return orderModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderModel record) {
        return orderModelMapper.updateByPrimaryKeySelective(record);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void payProjectDeposit(String projectIds, String talentId, String userId, String amount, String num, String hospitalId) {
        if (ValidateTool.isNull(projectIds)|| projectIds.split(",").length<1){
            throw new JeecgBootException("请选择项目");
        }
        if (ValidateTool.isNull(amount)|| Long.valueOf(amount)<1){
            throw new JeecgBootException("定金金额异常");
        }
        OrderModel orderModel = new OrderModel();
        orderModel.setId(SeqUtils.nextIdStr());
        orderModel.setProjectId(projectIds);
        orderModel.setTalentId(talentId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        orderModel.setNum(Integer.valueOf(num));
        orderModel.setHospitalId(hospitalId);
        orderModel.setOperationType(1);
        String[] split = projectIds.split(",");
        String projectName = projectMapper.getProjectName(split[0]);
        orderModel.setContent(projectName+"定金");
        orderModelMapper.insertSelective(orderModel);
    }


    @Override
    public Page<OrderModelVo> loadOrderList(String userId, String optStatus, Page<OrderModelVo> page) {

        List<OrderModelVo> orderModelVos = orderModelMapper.loadOrderList(userId, optStatus,page);

        return page.setRecords(orderModelVos);
    }




}
