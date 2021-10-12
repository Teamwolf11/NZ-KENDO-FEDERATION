/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import domain.Member;

/**
 *
 * @author william
 */
public interface CredentialsValidator {

    Member signIn(String email, String password);
    
//    boolean validateCredentials(String userName, String password) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
}
