package dao;

import Database.DatabaseConnector;
import domain.Club;
import domain.Event;
import domain.Grade;
import domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Event;
import domain.Member;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletableFuture;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author lachl
 */
public class EmailJdbcDAO {

    private Connection con;

    /**
     * Gets individual grade
     *
     * @param gradeId
     * @param memberId
     * @return returns grade
     */
    public List<Member> getPotentialMembers(Event event) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT DISTINCT gv.*, m.*, m.member_id AS m_id, ma.name AS ma_name, ma.martial_art_id, g.name FROM email.grading_vault gv JOIN public.member m ON gv.member_id = m.member_id  JOIN public.grading g ON gv.grading_id = g.grading_id JOIN public.martial_arts ma ON g.martial_art_id = ma.martial_art_id WHERE current_grade = 'Y' AND grade_level < (SELECT grade_level FROM public.grading WHERE grading_id = ?) AND ma.martial_art_id = ?";            
            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                
                stmt.setInt(1, Integer.parseInt(event.getHighestGradeAvailable().getGradeId()));
                stmt.setInt(2, Integer.parseInt(event.getHighestGradeAvailable().getArtId()));
                ResultSet rs = stmt.executeQuery();
                Member member;
                List<Member> mList = new ArrayList<>();
                
                while (rs.next()) {
                    // grade fields
                    String gradeId = Integer.toString(rs.getInt("grading_id"));
                    String artId = Integer.toString(rs.getInt("martial_art_id"));
                    String gradeName = rs.getString("name");
                    String martialArt = rs.getString("ma_name");
                    String dateReceived = rs.getString("date_received");
                    String nextDateAvailable = rs.getString("date_next_grade_available");
                    String eventId = Integer.toString(rs.getInt("event_id"));
                    if (eventId.equals("0"))  eventId = null;

                    Grade grade = new Grade(gradeName, martialArt, nextDateAvailable, dateReceived, gradeId, artId, null, null, eventId);
                    
                    String memberId = Integer.toString(rs.getInt("m_id"));
                    String nzkfId = rs.getString("nzkf_membership_id");
                    String joinDate = rs.getString("join_date");
                    String nzkfIdRenewDate = rs.getString("nzkf_membership_renew_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    //char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    String email = rs.getString("email");
                    String dob = rs.getString("date_of_birth");
                    String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");
                    
                    member =  new Member(memberId, nzkfId, nzkfIdRenewDate, null, email, password, dob, joinDate, fName, lName, mName, (char) 0, ethnicity, phoneNum);
                    member.addGrade(grade);
                    mList.add(member);
                }
                
                sendGradingEmail(event, mList);
                return mList;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }
    
    public void sendConfirmationEmail(Member member) {
        CompletableFuture.runAsync(() -> {
            String newEmail = member.getEmail();
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqac"));
            email.setSSLOnConnect(true);

            try {
                email.setFrom("benjaminm.12184@gmail.com");
                email.setSubject("New Member #" + member.getMemberId());
                email.setMsg("New Member sign up details for " + member.getfName() + 
                        " " + member.getlName() + "." + " Your username is " + 
                         member.getEmail());

                email.addTo(newEmail);
                email.send();
            } catch (EmailException ex) {
                Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    
    public void sendGradingEmail(Event event, List<Member> mList){
    CompletableFuture.runAsync(() -> {
           
               String newEmail = mList.toString();
               Email email = new SimpleEmail();
               email.setHostName("smtp.gmail.com");
               email.setSmtpPort(587);
               email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
               email.setSSLOnConnect(true);
               List<Grade> grades;

               try {
                    for (Member member : mList){
                        grades = member.getGrades();
                            email.setFrom("benjaminm.12184@gmail.com");
                       email.setSubject("Upcoming Grading Event" + event.getStartDateTime());
                       email.setMsg("New grading event: " + event.getName()+ 
                               " is being held on " + event.getStartDateTime()+ "." + 
                               " You are eligible to participate in this grading event as your current grade is " + grades.indexOf(grades.size()-1));
                       email.addTo(newEmail);
                       email.send();
                    }
                   
               } catch (EmailException ex) {
                   Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
               }

           });
    }
    
    
public void sendExpiryEmail(List<Member> mList) throws Exception {
DatabaseConnector db = new DatabaseConnector();
Connection con = db.connect();
    
        CompletableFuture.runAsync(() -> {
 
            String newEmail = mList.toString();
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
            email.setSSLOnConnect(true);
            try {
                for (Member member : mList) {
                    String sql = "INSERT INTO public.expiry_email_store (member_id, nzkf_membership_renew_date, status) VALUES (?, ?, 'Attempted')";

                try (PreparedStatement stmt = con.prepareStatement(sql);) {
                        stmt.setInt(1, Integer.parseInt(member.getMemberId()));
                        stmt.setString(2, member.getNzkfRenewDate());
                        ResultSet rs = stmt.executeQuery();
                }   catch (SQLException ex) {
                        Logger.getLogger(EmailJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    email.setFrom("benjaminm.12184@gmail.com");
                    email.setSubject("Membership expiry for " + member.getfName() + " " + member.getlName());
                    email.setMsg("Your membership for NZ Kendo federation is set to expire on " + member.getNzkfRenewDate());
                    email.addTo(newEmail);
                    email.send();
                    
                }
 
            } catch (EmailException ex) {
                Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
 
            } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
            }
 
        });
    }
    
    public List<Member> getExpiredMembershipMembers() throws Exception {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();
 
            String sql = "SELECT m.* FROM member m JOIN expiry_email_store ee ON m.member_id = ee.member_id AND m.nzkf_membership_renew_date = ee.nzkf_membership_renew_date WHERE TO_DATE(m.nzkf_membership_renew_date, 'DD-MM-YYYY') <= NOW() + interval '20 days' AND ee.status IS NULL";
 
            try ( PreparedStatement stmt = con.prepareStatement(sql);) {
 
                ResultSet rs = stmt.executeQuery();
                Member member;
                List<Member> mList = new ArrayList<>();
 
                while (rs.next()) {
 
                    String memberId = Integer.toString(rs.getInt("m_id"));
                    String nzkfId = rs.getString("nzkf_membership_id");
                    String joinDate = rs.getString("join_date");
                    String nzkfIdRenewDate = rs.getString("nzkf_membership_renew_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    //char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    String email = rs.getString("email");
                    String dob = rs.getString("date_of_birth");
                    String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");
 
                    member = new Member(memberId, nzkfId, nzkfIdRenewDate, null, email, password, dob, joinDate, fName, lName, mName, (char) 0, ethnicity, phoneNum);
                    mList.add(member);
                }
 
                sendExpiryEmail(mList);
                return mList;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }
    
}



//    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//    
//    public static void main(String[]args){
//        Member emailMember= new Member();
//        emailMember.setEmail(emailMember.getEmail());
//        emailMember.setfName(emailMember.getfName());
//        emailMember.setlName(emailMember.getlName());
//        emailMember.setPassword(emailMember.getPassword());
//        EmailDAO newEmailDao = new EmailDAO();
//        newEmailDao.sendGradingEmail(emailMember);
//    }
//    public void email(Member member) {
//            CompletableFuture.runAsync(() -> {
//                String newEmail = member.getEmail();
//                Email email = new SimpleEmail();
//                email.setHostName("smtp.gmail.com");
//                email.setSmtpPort(587);
//                email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
//                email.setSSLOnConnect(true);
//
//                try {
//                    email.setFrom("benjaminm.12184@gmail.com");
//                    email.setSubject("New Member #" + member.getMemberId());
//                    email.setMsg("New Member sign up details for " + member.getfName() + 
//                            " " + member.getlName() + "." + " Your username and password are " + 
//                            member.getPassword() + " and " + member.getEmail());
//
//                    email.addTo(newEmail);
//                    email.send();
//                } catch (EmailException ex) {
//                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            });
//	}
//    
//    public void gradingEmail(Event event, Member member, List mList){
//         CompletableFuture.runAsync(() -> {
//                
//                String newEmail = mList.toString();
//                Email email = new SimpleEmail();
//                email.setHostName("smtp.gmail.com");
//                email.setSmtpPort(587);
//                email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
//                email.setSSLOnConnect(true);
//
//                try {
//                    email.setFrom("benjaminm.12184@gmail.com");
//                    email.setSubject("Upcoming Grading Event" + event.getStartDateTime());
//                    email.setMsg("New grading event: " + event.getName()+ 
//                            " is being held on " + event.getStartDateTime()+ "." + 
//                            " You are eligible to participate in this grading event as your current grade is " + mList.toString());
//                    email.addTo(newEmail);
//                    email.send();
//                } catch (EmailException ex) {
//                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            });
//    }
//    
//    public void expiryEmail(Member member){
//        CompletableFuture.runAsync(() -> {
//                String newEmail = member.getEmail();
//                Email email = new SimpleEmail();
//                email.setHostName("smtp.gmail.com");
//                email.setSmtpPort(587);
//                email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
//                email.setSSLOnConnect(true);
//
//                try {
//                    email.setFrom("benjaminm.12184@gmail.com");
//                    email.setSubject("Membership expiry for " + member.getfName() +" "+ member.getlName());
//                    email.setMsg("Your membership for NZ Kendo federation is set to expire on " + member.getJoinDate());
//
//                    email.addTo(newEmail);
//                    email.send();
//                } catch (EmailException ex) {
//                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            });
//    }
//   
//    public void sendConfirmationEmail(Member member){
//    EmailDAO newEmailConfirmation = new EmailDAO();
//    newEmailConfirmation.email(member);
//    }
//    
//    public void sendExpiryEmail(Member member){
//        EmailDAO newExpiryEmail = new EmailDAO();
//        newExpiryEmail.expiryEmail(member);
//    }
//    
//    public void sendGradingEmail(Member member){
//        
//        //Member testMember = MemberDAO.getMember(getId);
//        
//        EmailDAO newEmailGrading = new EmailDAO();
//        Runnable gradingEmail = () -> {
//            
//        newEmailGrading.email(member);
//            //System.out.println("ben"); 
//        };
//        ScheduledFuture<?> emailHandle = scheduler.scheduleAtFixedRate(gradingEmail, 0, 10, SECONDS);
//        Runnable canceller = () -> emailHandle.cancel(false);
//        scheduler.schedule(canceller, 1, HOURS);
//    }
//}