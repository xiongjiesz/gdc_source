package com.gdc.gdcmvp.user;

/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.user
 * @file UserGlobalEntity
 * @brief 存放全局的用户变量,添加任何新的全局用户变量，必须在文件顶部注释里写明添加原因
 * @date 2017/6/12
 * @since appVer
 */
public class UserGlobalEntity {
    private String currentAccount;

    public String getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String currentAccount) {
        this.currentAccount = currentAccount;
    }
}
