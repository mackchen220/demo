package org.jeecg.modules.user.service;

import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.HospitalProjectMapper;
import org.jeecg.modules.user.mapper.ProjectInfoMapper;
import org.jeecg.modules.user.mapper.ProjectMapper;
import org.jeecg.modules.user.mapper.TalentHospitalMapper;
import org.jeecg.modules.user.model.Project;
import org.jeecg.modules.user.model.TalentHospital;
import org.jeecg.modules.user.model.vo.HospitalVo;
import org.jeecg.modules.user.model.vo.ProjectInfoVo;
import org.jeecg.modules.user.model.vo.ProjectVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TalentHospitalServiceImpl implements TalentHospitalService {

    @Resource
    private TalentHospitalMapper talentHospitalMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectInfoMapper projectInfoMapper;
    @Resource
    private HospitalProjectMapper hospitalProjectMapper;


    @Override
    public int insertSelective(TalentHospital record) {
        return talentHospitalMapper.insertSelective(record);
    }

    @Override
    public TalentHospital selectByPrimaryKey(String id) {
        return talentHospitalMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TalentHospital record) {
        return talentHospitalMapper.updateByPrimaryKeySelective(record);
    }



    @Override
    public Map loadProjectList(String id, String projectId, String hospitalId) {
        Map map = new HashMap();
        //所有项目大类别
        List<ProjectVo> projectVos = projectMapper.laodProjectList(null);
        map.put("project", projectVos);
        if (projectVos.size() == 0) {
            //没有大类给空数据结构
            map.put("hospitals", new ArrayList<>());
            map.put("allProject", new ArrayList<>());
        }

        if (ValidateTool.isNotNull(projectId)) {
            //是否选择了大类，没选默认加载一个
            Project project = projectMapper.selectByPrimaryKey(projectId);
            if (ValidateTool.isNull(project)) {
                throw new JeecgBootException("项目已下架，请重新刷新");
            }
        }

        //达人的所有机构医院
        List<HospitalVo> hospitalModels = talentHospitalMapper.loadTalentHospitalLst(id);
        map.put("hospitals", hospitalModels);
        if (ValidateTool.isNull(projectId)) {
            //没选择机构，查询随机一个大类id下所有子类
            projectId = projectMapper.getId();
        }
        if (ValidateTool.isNull(hospitalId)) {
            //没选择机构，默认加载随机一个达人的机构
            if (hospitalModels.size() > 0) {
                hospitalId = hospitalModels.get(0).getHospitalId();
                log.info("默认的hospitalId{}", hospitalId);
            }
        }
        log.info("projectId{},hospitalId{}", projectId, hospitalId);
        List<ProjectInfoVo> projectInfos = projectInfoMapper.loadProjectInfoList(projectId, hospitalId);
        Map<Integer, List<ProjectInfoVo>> collect = projectInfos.stream().collect(Collectors.groupingBy(ProjectInfoVo::getType));
        map.put("allProject", collect);
        return map;
    }


    @Override
    public Map getQuotation(String projectIds, String materialId, String hospitalId) {
        Map map = new HashMap();
        if (ValidateTool.isNull(projectIds)) {
            throw new JeecgBootException("请选择项目");
        }
        if (ValidateTool.isNull(materialId)) {
            throw new JeecgBootException("请选择材料");
        }
        if (ValidateTool.isNull(hospitalId)) {
            throw new JeecgBootException("请选择机构");
        }
        String[] split = projectIds.split(",");

        String[] strs=new String[3];
        strs[0]=split[0];
        strs[1]=split[1];
        strs[2]=materialId;

        List<ProjectInfoVo> projectInfos = projectInfoMapper.loadProjectPrice(hospitalId, strs);
        Map<Integer, List<ProjectInfoVo>> collect = projectInfos.stream().collect(Collectors.groupingBy(ProjectInfoVo::getType));
        long priceLow = 0;
        long priceHigh = 0;
        long othrer = 0;
        if (collect.size() > 0) {
            //累计最低价和最高价
            List<ProjectInfoVo> projectInfoVos1 = collect.get(1);
            for (ProjectInfoVo projectInfoVo : projectInfoVos1) {
                priceLow = priceLow + projectInfoVo.getPriceLow();
                priceHigh = priceLow + projectInfoVo.getPriceHigh();
            }
            List<ProjectInfoVo> projectInfoVos2 = collect.get(2);
            for (ProjectInfoVo projectInfoVo : projectInfoVos2) {
                priceLow = priceLow + projectInfoVo.getPriceLow();
                priceHigh = priceLow + projectInfoVo.getPriceHigh();
            }
            List<ProjectInfoVo> projectInfoVos3 = collect.get(3);
            for (ProjectInfoVo projectInfoVo : projectInfoVos3) {
                othrer = othrer + projectInfoVo.getPriceLow();
            }
        }
        //其他杂项价格
        map.put("othrer", othrer);
        collect.remove(3);
        //项目预计价格最低与最高
        map.put("yuPiceLow", priceLow + othrer);
        map.put("yuPriceHigh", priceHigh + othrer);
        map.put("project", collect);

        //TODO 会员价

        return map;
    }


}
