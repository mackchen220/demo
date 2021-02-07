package org.jeecg.modules.user.service;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.model.CaseModel;
import org.jeecg.modules.user.mapper.CaseModelMapper;

@Service
public class CaseModelServiceImpl implements CaseModelService{

    @Resource
    private CaseModelMapper caseModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return caseModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CaseModel record,String id) {
        if (ValidateTool.isNull(record) || ValidateTool.isNull(record.getType())){
            throw new JeecgBootException("请输入项目类别");
        }
        if (ValidateTool.isNull(record.getContent())){
            throw new JeecgBootException("请输入项目内容");
        }
        if (ValidateTool.isNull(record.getContent())){
            throw new JeecgBootException("请输入项目内容");
        }
        if (ValidateTool.isNull(record.getTime())){
            throw new JeecgBootException("请选择时间");
        }
        record.setId(SeqUtils.nextIdStr());
        record.setUserId(id);
        return caseModelMapper.insertSelective(record);
    }



    @Override
    public CaseModel selectByPrimaryKey(String id) {
        return caseModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CaseModel record) {
        return caseModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CaseModel record) {
        return caseModelMapper.updateByPrimaryKey(record);
    }

}
