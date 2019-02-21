package com.ken.config;

import com.ken.constants.PieceConstants;
import com.ken.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Shiro业务配置<br/>
 * 内部业务逻辑需要自己实现
 */
public class PieceShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Session session = SecurityUtils.getSubject().getSession();
        // 查询用户的权限
        User user = (User) session.getAttribute(PieceConstants.SESSION_USER_INFO);
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String roleName = String.valueOf(user.getRoleId());
        authorizationInfo.addRole(roleName);
        authorizationInfo.addStringPermission(roleName);
        return authorizationInfo;
    }

    /**
     * 验证当前登录的Subject<br/>
     * login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String loginName = (String) token.getPrincipal();
        // 获取用户密码
        String password = new String((char[]) token.getCredentials());
        // 根据loginNmae & password验证用户数据
        if (!"ken".equals(loginName) || !"kenpw".equals(password)) {
            throw new UnknownAccountException();
        }
        User user = User.builder().name(loginName).roleId(1).build();
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getName(), password, getName());
        // 将用户信息放入session中，session设置永不过期
        SecurityUtils.getSubject().getSession().setTimeout(-1000L);
        SecurityUtils.getSubject().getSession().setAttribute(PieceConstants.SESSION_USER_INFO, user);
        return authenticationInfo;
    }
}

