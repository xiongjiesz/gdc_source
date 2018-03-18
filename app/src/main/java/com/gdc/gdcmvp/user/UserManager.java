package com.gdc.gdcmvp.user;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gdc.gdcmvp.network.http.config.Constant;

/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.user
 * @file UserManager
 * @brief 用户管理类
 * @date 2017/6/12
 * @since appVer
 */
public class UserManager implements IUserManager{
    /**
     * 用户全局变量
     */
    private UserGlobalEntity        userGlobalEntity;


    private UserManager() {
        userGlobalEntity = new UserGlobalEntity();
    }

    private static class UserManagerHolder {
        static UserManager instance = new UserManager();
    }

    public static UserManager getInstance() {
        return UserManagerHolder.instance;
    }

    public static
    @NonNull
    UserGlobalEntity getUser() {
        return getInstance().getUserGlobalEntity();
    }

    public UserGlobalEntity getUserGlobalEntity() {
        return userGlobalEntity;
    }

    @Override
    public boolean isGuest() {
        return isGuest(userGlobalEntity.getCurrentAccount());
    }

    /**
     * @return true 游客 false 会员
     * @Description 判断给定用户是否是游客
     * @author huangwr
     * @date 2016/9/3
     */
    public boolean isGuest(String account) {
        if (TextUtils.isEmpty(account)) {
            return true;
        }
        // 与游客标记进行比较
        String guestTag = Constant.VISITORACCOUNT_LOGO;
        return guestTag.equals(account);
    }


}
