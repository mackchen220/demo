package org.jeecg.modules.course.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: UserCourseVo
 * @description: 社区搜索实体
 * @author: LongXiang
 * @data: 2021-02-18 14:45
 * @version: V1.0
 */
@Data
public class UserCourseVo implements Serializable {

    private String userId;
    private String headImage;
    private String nickName;

    /**
     * 朋友圈类型:1 照片 2视频 3课程  4其他
     */
    @ApiModelProperty(value="朋友圈类型:1 照片 2视频 3课程  4其他  ")
    private Integer type;

    /**
     * 数据来源类型 1-朋友圈 2-课程 3-活动
     */
    private Integer courseType;
    /**
     * 数据来源主键ID
     */
    private String courseId;
    private String title;
    private String image;
    private Long watchNum;
    private Long goodNum;
    private Long starNum;
    private Long forwardNum;

    public static UserCourseVo valueOf(Integer courseType, String courseId, String title, String image, Long watchNum,
                                       Long goodNum, Long starNum, Long forwardNum) {
        UserCourseVo vo = new UserCourseVo();
        vo.setCourseType(courseType);
        vo.setCourseId(courseId);
        vo.setTitle(title);
        vo.setImage(image);
        vo.setWatchNum(watchNum);
        vo.setGoodNum(goodNum);
        vo.setStarNum(starNum);
        vo.setForwardNum(forwardNum);
        return vo;
    }

    public static UserCourseVo valueOf(Integer courseType, String courseId, String title, String image, Long watchNum,
                                       Long goodNum, Long starNum, Long forwardNum,Integer type) {
        UserCourseVo vo = new UserCourseVo();
        vo.setCourseType(courseType);
        vo.setCourseId(courseId);
        vo.setTitle(title);
        vo.setImage(image);
        vo.setWatchNum(watchNum);
        vo.setGoodNum(goodNum);
        vo.setStarNum(starNum);
        vo.setForwardNum(forwardNum);
        vo.setType(type);
        return vo;
    }

}
