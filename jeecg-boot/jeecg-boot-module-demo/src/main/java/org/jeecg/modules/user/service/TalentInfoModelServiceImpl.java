package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.HospitalProjectMapper;
import org.jeecg.modules.user.mapper.TalentHospitalMapper;
import org.jeecg.modules.user.model.TalentHospital;
import org.jeecg.modules.user.model.vo.ProjectInfoVo;
import org.jeecg.modules.user.model.vo.TalentInfoVo;
import org.jeecg.modules.user.model.vo.UserModelVo;
import org.jeecg.modules.user.model.vo.UserProjectVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.jeecg.modules.user.mapper.TalentInfoModelMapper;
import org.jeecg.modules.user.model.TalentInfoModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class TalentInfoModelServiceImpl implements TalentInfoModelService {

    @Resource
    private TalentInfoModelMapper talentInfoModelMapper;
    @Resource
    private TalentHospitalMapper talentHospitalMapper;
    @Resource
    private HospitalProjectMapper hospitalProjectMapper;


    @Override
    public int deleteByPrimaryKey(String id) {
        return talentInfoModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TalentInfoModel record) {
        return talentInfoModelMapper.insert(record);
    }

    @Override
    public int insertSelective(TalentInfoModel record) {
        return talentInfoModelMapper.insertSelective(record);
    }

    @Override
    public TalentInfoModel selectByPrimaryKey(String id) {
        return talentInfoModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TalentInfoModel record) {
        return talentInfoModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TalentInfoModel record) {
        return talentInfoModelMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<TalentInfoVo> loadTalentList(Page<TalentInfoVo> pageList, String search, String city) {
        List<TalentInfoVo> talentInfoVos = talentInfoModelMapper.loadTalentList(pageList, search, city);
        return pageList.setRecords(talentInfoVos);
    }

    @Override
    public TalentInfoModel getTalentByUserId(String userId) {
        return talentInfoModelMapper.selectByUserId(userId);
    }

    @Override
    public int addAdvisoryNum(String userId) {
        return talentInfoModelMapper.increase(userId, 1);
    }


    @Override
    public List loadOtherTalentList(String search) {
        List<TalentInfoVo> talentInfoVos = talentInfoModelMapper.loadOtherTalentList(search, "4");
        return talentInfoVos;
    }

    @Override
    public Page<UserProjectVo> loadProjectlist(String search, Page<UserProjectVo> pageList) {
        List<UserProjectVo> talentHospitals=null;
        if (ValidateTool.isNull(search)){
            //取出达人和机构id
            talentHospitals= talentHospitalMapper.loadAllTalentId(pageList);
            for (UserProjectVo model : talentHospitals) {
                List<String> list = talentHospitalMapper.getHospitalIdByTalentId(model.getTalentId());
                model.setHospitalId(list.get(new Random().nextInt(list.size())));
                //获取达人的机构下任意一个项目
                List<ProjectInfoVo> projectInfoVos = hospitalProjectMapper.loadProjectByHospitalId(model.getHospitalId());
                //随机一个项目
                if (projectInfoVos.size() > 0) {
                    ProjectInfoVo projectInfoVo = projectInfoVos.get(new Random().nextInt(projectInfoVos.size()));
                    model.setPriceHigh(projectInfoVo.getPriceHigh());
                    model.setPriceLow(projectInfoVo.getPriceLow());
                    model.setProjectName(projectInfoVo.getProjectName());
                }
            }
        }else {
            //按条件搜索包含此项目的机构
             talentHospitals = talentHospitalMapper.getHospitalIdByProjectName(pageList, search);
            for (UserProjectVo talentHospital : talentHospitals) {
                //根据机构随机取出一个达人信息
                UserProjectVo userProjectVo = talentHospitalMapper.loadAllTalentByHospitalId(talentHospital.getHospitalId());
                if (ValidateTool.isNotNull(userProjectVo)){
                    talentHospital.setHeadImage(userProjectVo.getHeadImage());
                    talentHospital.setNickName(userProjectVo.getNickName());
                    talentHospital.setUserName(userProjectVo.getUserName());
                    talentHospital.setNum(userProjectVo.getNum());
                    talentHospital.setAverageScore(userProjectVo.getAverageScore());
                    talentHospital.setTalentId(userProjectVo.getTalentId());
                }
            }
        }

        return pageList.setRecords(talentHospitals);

    }
}
