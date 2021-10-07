package dao;

import domain.Grade;
import domain.Member;
import java.util.List;

/**
 *
 * @author lachl
 */
public interface GradeDAO {
    
    Grade getGrade(String gradeId);
    
    void saveGrade(Grade grade, Member member);
    
    void deleteGrade(Grade grade);
    
    List<Grade> getAllForMember(Member member);
}