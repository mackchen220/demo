package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.*;
import org.jeecg.modules.user.model.*;
import org.jeecg.modules.user.model.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    @Resource
    private OrderModelMapper orderModelMapper;
    @Resource
    private UserIncomeDetailMapper userIncomeDetailMapper;
    @Resource
    private TalentCustomerMapper talentCustomerMapper;


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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addTalentInfo(String userId, String idNum, String name, String year, String city) {

        if (ValidateTool.isNull(idNum)){
            throw new JeecgBootException("身份证号码不能为空");
        }
        if (ValidateTool.isNull(name)){
            throw new JeecgBootException("请填写真实姓名");
        }
        if (ValidateTool.isNull(year)){
            throw new JeecgBootException("请填写从业年限");
        }
        if (ValidateTool.isNull(city)){
            throw new JeecgBootException("城市不能为空");
        }
        TalentInfoModel talentInfoModel = new TalentInfoModel();
        talentInfoModel.setId(SeqUtils.nextIdStr());
        talentInfoModel.setUserId(userId);
        talentInfoModel.setCity(city);
        talentInfoModel.setTime(year);
        talentInfoModel.setIdCard(idNum);
        talentInfoModel.setName(name);
        talentInfoModelMapper.insertSelective(talentInfoModel);
        return 0;
    }

    @Override
    public String getTalentBond() {
        //Todo   保证金配置？？？？
        return "2000000";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTalentBond(String userId) {
        TalentInfoModel talentInfoModel = talentInfoModelMapper.selectByUserId(userId);
        if (ValidateTool.isNull(talentInfoModel)){
            throw new JeecgBootException("请先完成身份认证");
        }
        //更新保证金
        talentInfoModel.setDeposit(2000000L);
        talentInfoModelMapper.updateByPrimaryKeySelective(talentInfoModel);
    }


    @Override
    public Map loadTalentCenter(UserModel userModel) {
        OrderModel orderModel = orderModelMapper.getOrderNumAndMoney(userModel.getId());
        Map<String, Object> map = new HashMap<>();
        //订单量
        map.put("num", orderModel.getNum());
        //成交额
        map.put("orderMoney", orderModel.getPayMoney());
        //总提成收益
        String sumMoney = userIncomeDetailMapper.getSumMoney(userModel.getId(), Constant.CHECKTYPE4);
        map.put("sumMoney", ValidateTool.isNull(sumMoney) ? 0 : sumMoney);

        //头像
        map.put("headImage", userModel.getHeadImage());
        //昵称
        map.put("nickName", userModel.getNickName());
        //邀请码
        map.put("inviteCode", userModel.getInviteCode());
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCustomer(String talentId, String userId) {
        TalentCustomer customer = new TalentCustomer();
        customer.setId(SeqUtils.nextIdStr());
        customer.setUserId(userId);
        customer.setTalentId(talentId);
        talentCustomerMapper.insertSelective(customer);
    }


    @Override
    public Page<TalentCustomerVo> loadMyCustomer(Page<TalentCustomerVo> pageList, String userId) {
        List<TalentCustomerVo> talentCustomerVos = talentCustomerMapper.loadMyCustomer(pageList, userId);
        return pageList.setRecords(talentCustomerVos);
    }
}
