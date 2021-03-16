package org.jeecg.modules.user.service;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.AddressModelMapper;
import org.jeecg.modules.user.model.AddressModel;
import org.jeecg.modules.user.model.vo.AddressModelVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressModelServiceImpl implements AddressModelService{

    @Resource
    private AddressModelMapper addressModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return addressModelMapper.deleteByPrimaryKey(id);
    }




    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertSelective(AddressModelVo record) {
        if (ValidateTool.isNull(record.getPhone())){
            throw  new JeecgBootException("请输入手机号码");
        }
        if (ValidateTool.isNull(record.getCity())){
            throw  new JeecgBootException("请选择城市");
        }
        if (ValidateTool.isNull(record.getAddress())){
            throw  new JeecgBootException("请输入详细地址");
        }
        List<AddressModelVo> addressModelVos = addressModelMapper.loadUserAddressList(record.getUserId());
        if (addressModelVos.size()==0){
            record.setDefaultFlag(1);
        }
        record.setId(SeqUtils.nextIdStr());
        return addressModelMapper.insertSelective(record);
    }

    @Override
    public AddressModel selectByPrimaryKey(String id) {
        return addressModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AddressModel record) {
        return addressModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AddressModel record) {
        return addressModelMapper.updateByPrimaryKey(record);
    }


    @Override
    public List loadUserAddressList(String userId) {

        List<AddressModelVo> addressModelVos = addressModelMapper.loadUserAddressList(userId);

        return addressModelVos;
    }
}
