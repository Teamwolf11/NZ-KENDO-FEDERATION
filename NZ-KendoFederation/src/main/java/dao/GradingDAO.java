/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Grade;
import domain.Member;
import java.util.List;

/**
 *
 * @author lachl
 */
public interface GradingDAO {
    
    Grade getGrade(String gradeId);
    
    void saveGrade(Grade grade, Member member);
    
    void deleteGrade(Grade grade);
    
    List<Grade> getAllForMember(Member member);
    

}
