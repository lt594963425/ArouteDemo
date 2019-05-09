package com.example.modulebase.data.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * $activityName
 * 用户类型
 *
 * @author LiuTao
 * @date 2019/3/11/011
 */

@IntDef({UserType.UNKNOWN,
        UserType.OPERATOE_USER,
        UserType.PERSON_USER,
        UserType.BUSINESS_USER,
        UserType.AGENT_USER})
@Retention(RetentionPolicy.SOURCE)
public @interface UserType {
    int UNKNOWN = -1;
    int OPERATOE_USER = 1;
    int PERSON_USER = 2;
    int BUSINESS_USER = 3;
    int AGENT_USER = 4;
}
