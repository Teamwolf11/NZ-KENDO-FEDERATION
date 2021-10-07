package dao;

import domain.Grade;
import domain.Member;
import java.util.List;

/**
 *
 * @author lachl
 */
public interface GradeDAO {
    
    Grade getMemberGrade(String gradeId, String memberId);
    
    Member saveGrade(Grade grade, Member member);
    
    void deleteGrade(Grade grade, String memberId);
    
    List getAllForObj(Object obj);
}