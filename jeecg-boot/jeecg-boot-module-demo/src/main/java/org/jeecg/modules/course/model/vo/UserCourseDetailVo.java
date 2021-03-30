package org.jeecg.modules.course.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: UserCourseDetailVo
 * @description: 社区动态详情实体
 * @data: 2021-02-18 16:48
 * @version: V1.0
 */
@Data
public class UserCourseDetailVo extends UserCourseVo implements Serializable {

    /**
     * 是否关注
     */
    private boolean isFans;
    private String city;
    private String createTime;

    public static UserCourseDetailVo valueOf(Integer courseType, String courseId, String title, String image, Long watchNum, Long goodNum, Long starNum,
                                             Long forwardNum, String city, String createTime, Boolean isFans) {
        UserCourseDetailVo vo = new UserCourseDetailVo();
        vo.setCourseType(courseType);
        vo.setCourseId(courseId);
        vo.setTitle(title);
        vo.setImage(image);
        vo.setCreateTime(createTime);
        vo.setWatchNum(watchNum);
        vo.setGoodNum(goodNum);
        vo.setCity(city);
        vo.setStarNum(starNum);
        vo.setForwardNum(forwardNum);
        vo.setFans(isFans);
        return vo;
    }


}
