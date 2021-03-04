package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.shiro.crypto.hash.Hash;
import org.checkerframework.checker.nullness.NullnessUtil;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.util.DateHelper;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.*;
import org.jeecg.modules.user.model.*;
import org.jeecg.modules.user.model.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;


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
    @Resource
    private UserIncomeMapper userIncomeMapper;
    @Resource
    private UserModelMapper userModelMapper;
    @Resource
    private VipModelMapper vipModelMapper;
    @Resource
    private UserAgencyModelMapper userAgencyModelMapper;
    @Resource
    private UserModelService userModelService;


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
        List<UserProjectVo> talentHospitals = null;
        if (ValidateTool.isNull(search)) {
            //取出达人和机构id
            talentHospitals = talentHospitalMapper.loadAllTalentId(pageList);
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
        } else {
            //按条件搜索包含此项目的机构
            talentHospitals = talentHospitalMapper.getHospitalIdByProjectName(pageList, search);
            for (UserProjectVo talentHospital : talentHospitals) {
                //根据机构随机取出一个达人信息
                UserProjectVo userProjectVo = talentHospitalMapper.loadAllTalentByHospitalId(talentHospital.getHospitalId());
                if (ValidateTool.isNotNull(userProjectVo)) {
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

        if (ValidateTool.isNull(idNum)) {
            throw new JeecgBootException("身份证号码不能为空");
        }
        if (ValidateTool.isNull(name)) {
            throw new JeecgBootException("请填写真实姓名");
        }
        if (ValidateTool.isNull(year)) {
            throw new JeecgBootException("请填写从业年限");
        }
        if (ValidateTool.isNull(city)) {
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
        if (ValidateTool.isNull(talentInfoModel)) {
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
        String sumMoney = userIncomeDetailMapper.getSumMoney(userModel.getId(), Constant.CHECKTYPE4, null, null);
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

    @Override
    public Map loadCustomrInfo(String talentId, String id, String userId) {
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null);

        Map<String, Object> map = new HashMap<>();
        map.put("nickName", userModel.getNickName());
        map.put("headImage", userModel.getHeadImage());
        map.put("username", userModel.getUserName());
        map.put("city", userModel.getCity());
        map.put("sign", userModel.getSign());
        map.put("gender", userModel.getGender());

        TalentCustomer talentCustomer = talentCustomerMapper.selectByPrimaryKey(id);
        map.put("orderMoney", talentCustomer.getOrderMoney());
        map.put("orderNum", talentCustomer.getOrderNum());
        map.put("commissionMoney", talentCustomer.getCommissionMoney());

        return map;
    }

    @Override
    public Map loadCustomrIncome(String userId, String year, String month, Page<UserIncomeDetailVo> page) {

        Map<String, Object> map = new HashMap<>();

        String lastDayOfMonth = DateHelper.getLastDayOfMonth(Integer.valueOf(month));
        String firstDayOfMonth = DateHelper.getFirstDayOfMonth(Integer.valueOf(month));

        String startTime = lastDayOfMonth.replace(firstDayOfMonth.substring(4), year);
        String endTime = lastDayOfMonth.replace(lastDayOfMonth.substring(4), year);

        Page<UserIncomeDetailVo> userIncomeDetailVoPage = userModelService.loadIncomeDetail(userId, page, 6, startTime, endTime);

        //总成交额
        String sumMoney = userIncomeDetailMapper.getSumMoney(userId, "6", null, null);
        map.put("sumMoney", sumMoney);
        //本月成交额
        String monthMoney = userIncomeDetailMapper.getSumMoney(userId, "6", startTime, endTime);
        map.put("monthMoney", monthMoney);

        map.put("page", userIncomeDetailVoPage);


        return map;
    }

    @Override
    public Map loadExtensionCenter(UserModel userModel) {

        Map map = new HashMap<>();

        map.put("headImage", userModel.getHeadImage());
        map.put("nickName", userModel.getNickName());

        VipModel vipModel = vipModelMapper.selectByPrimaryKey(userModel.getVipId());

        map.put("vipName", ValidateTool.isNull(vipModel) ? "" : vipModel.getVipName());

        //今日收入
        String startTimeToday = DateHelper.getToday() + DateHelper.DEFUALT_TIME_START;
        String endTimeToday = DateHelper.getToday() + DateHelper.DEFUALT_TIME_END;
        String incomeToday = userIncomeMapper.getIncomeByTime(userModel.getId(), startTimeToday, endTimeToday);
        map.put("incomeToday", ValidateTool.isNull(incomeToday) ? 0 : incomeToday);

        //本月收入
        String firstDayOfMonth = DateHelper.getFirstDayOfMonth();
        String lastDayOfMonth = DateHelper.getLastDayOfMonth();
        String incomeMonth = userIncomeMapper.getIncomeByTime(userModel.getId(), firstDayOfMonth, lastDayOfMonth);
        map.put("incomeMonth", ValidateTool.isNull(incomeMonth) ? 0 : incomeMonth);

        //总收入
        String incomeTotal = userIncomeMapper.getIncomeByTime(userModel.getId(), firstDayOfMonth, lastDayOfMonth);

        map.put("incomeTotal", ValidateTool.isNull(incomeTotal) ? 0 : incomeTotal);

        String userNum = userAgencyModelMapper.countUserNum(userModel.getId());

        map.put("userNum", userNum);

        return map;
    }


    @Override
    public Page<ExtensionVo> loadExtensionIncome(String userId, Page<ExtensionVo> page, Integer sortModel) {

        List<ExtensionVo> extensionVos = talentCustomerMapper.loadExtensionIncome(page, userId);
        if (extensionVos.size() > 0) {
            for (ExtensionVo extensionVo : extensionVos) {
                //今日收入
                String startTimeToday = DateHelper.getToday() + DateHelper.DEFUALT_TIME_START;
                String endTimeToday = DateHelper.getToday() + DateHelper.DEFUALT_TIME_END;
                String incomeToday = userIncomeMapper.getIncomeByTime(extensionVo.getUserId(), startTimeToday, endTimeToday);
                extensionVo.setDayMoney(ValidateTool.isNull(incomeToday) ? 0 : Long.valueOf(incomeToday));
                //本月收入
                String firstDayOfMonth = DateHelper.getFirstDayOfMonth();
                String lastDayOfMonth = DateHelper.getLastDayOfMonth();
                String incomeMonth = userIncomeMapper.getIncomeByTime(extensionVo.getUserId(), firstDayOfMonth, lastDayOfMonth);
                extensionVo.setMonthMoney(ValidateTool.isNull(incomeMonth) ? 0 : Long.valueOf(incomeMonth));
                //总收入
                String incomeTotal = userIncomeMapper.getIncomeByTime(extensionVo.getUserId(), firstDayOfMonth, lastDayOfMonth);
                extensionVo.setTotelMoney(ValidateTool.isNull(incomeTotal) ? 0 : Long.valueOf(incomeTotal));
            }
        }
        //sortModel 1 收入高 2收入地 3 等级高  4 等级低 5 时间
        if (Constant.TYPE_INT_1==sortModel){
            extensionVos.stream().sorted(new Comparator<ExtensionVo>() {
                @Override
                public int compare(ExtensionVo o1, ExtensionVo o2) {
                    return o1.getTotelMoney()>o2.getTotelMoney()?1:0;

                }
            });
        }
        if (Constant.TYPE_INT_2==sortModel){
            extensionVos.stream().sorted(new Comparator<ExtensionVo>() {
                @Override
                public int compare(ExtensionVo o1, ExtensionVo o2) {
                    return o1.getTotelMoney()>o2.getTotelMoney()?0:1;

                }
            });
        }
//        if (Constant.TYPE_INT_3==sortModel){
//            extensionVos.stream().sorted(new Comparator<ExtensionVo>() {
//                @Override
//                public int compare(ExtensionVo o1, ExtensionVo o2) {
//                    return o1.getTotelMoney()>o2.getTotelMoney()?0:1;
//                }
//            });
//        }
//        if (Constant.TYPE_INT_4==sortModel){
//            extensionVos.stream().sorted(new Comparator<ExtensionVo>() {
//                @Override
//                public int compare(ExtensionVo o1, ExtensionVo o2) {
//                    return o1.getTotelMoney()>o2.getTotelMoney()?0:1;
//
//                }
//            });
//        }
        return page.setRecords(extensionVos);
    }
}
