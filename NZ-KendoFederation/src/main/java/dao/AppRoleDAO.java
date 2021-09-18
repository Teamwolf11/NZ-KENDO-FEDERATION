/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.AppRoles;

/**
 *
 * @author lachl
 */
public interface AppRoleDAO {
    
    AppRoles getAppRole(String roleId);
    
    AppRoles saveAppRole(AppRoles role);
    
    void deleteAppRole(AppRoles role);
}
