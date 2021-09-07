/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;

/**
 *
 * @author lachl
 */
public interface UserDAO {
    
    User saveUser(User user);
    
    User getUser(String userId);
    
    void deleteUser(User user);
}
