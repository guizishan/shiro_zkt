package cn.cxy.shiro.service;


import cn.cxy.shiro.dao.RoleDao;
import cn.cxy.shiro.dao.RoleDaoImpl;
import cn.cxy.shiro.entity.Role;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao = new RoleDaoImpl();


    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    /**
     * 添加角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        roleDao.correlationPermissions(roleId, permissionIds);
    }

    /**
     * 移除角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void unCorrelationPermissions(Long roleId, Long... permissionIds) {
        roleDao.unCorrelationPermissions(roleId, permissionIds);
    }

}
