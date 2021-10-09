/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

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
 * @author benmcmahon
 */
public  class EmailDAO extends EmailJdbcDAO{
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public static void main(String[]args){
        Member emailMember= new Member();
        emailMember.setEmail(emailMember.getEmail());
        emailMember.setfName(emailMember.getfName());
        emailMember.setlName(emailMember.getlName());
        emailMember.setPassword(emailMember.getPassword());
        EmailDAO newEmailDao = new EmailDAO();
        newEmailDao.sendGradingEmail(emailMember);
    }
    public void email(Member member) {
            CompletableFuture.runAsync(() -> {
                String newEmail = member.getEmail();
                Email email = new SimpleEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(587);
                email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
                email.setSSLOnConnect(true);

                try {
                    email.setFrom("benjaminm.12184@gmail.com");
                    email.setSubject("New Member #" + member.getMemberId());
                    email.setMsg("New Member sign up details for " + member.getfName() + 
                            " " + member.getlName() + "." + " Your username and password are " + 
                            member.getPassword() + " and " + member.getEmail());

                    email.addTo(newEmail);
                    email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
	}
    
    public void gradingEmail(Event event, Member member, List mList){
         CompletableFuture.runAsync(() -> {
                
                String newEmail = mList.toString();
                Email email = new SimpleEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(587);
                email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
                email.setSSLOnConnect(true);

                try {
                    email.setFrom("benjaminm.12184@gmail.com");
                    email.setSubject("Upcoming Grading Event" + event.getStartDateTime());
                    email.setMsg("New grading event: " + event.getName()+ 
                            " is being held on " + event.getStartDateTime()+ "." + 
                            " You are eligible to participate in this grading event as your current grade is " + mList.toString());
                    email.addTo(newEmail);
                    email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
    }
    
    public void expiryEmail(Member member){
        CompletableFuture.runAsync(() -> {
                String newEmail = member.getEmail();
                Email email = new SimpleEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(587);
                email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
                email.setSSLOnConnect(true);

                try {
                    email.setFrom("benjaminm.12184@gmail.com");
                    email.setSubject("Membership expiry for " + member.getfName() +" "+ member.getlName());
                    email.setMsg("Your membership for NZ Kendo federation is set to expire on " + member.getJoinDate());

                    email.addTo(newEmail);
                    email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
    }
   
    public void sendConfirmationEmail(Member member){
    EmailDAO newEmailConfirmation = new EmailDAO();
    newEmailConfirmation.email(member);
    }
    
    public void sendExpiryEmail(Member member){
        EmailDAO newExpiryEmail = new EmailDAO();
        newExpiryEmail.expiryEmail(member);
    }
    
    public void sendGradingEmail(Member member){
        
        //Member testMember = MemberDAO.getMember(getId);
        
        EmailDAO newEmailGrading = new EmailDAO();
        Runnable gradingEmail = () -> {
            
        newEmailGrading.email(member);
            //System.out.println("ben"); 
        };
        ScheduledFuture<?> emailHandle = scheduler.scheduleAtFixedRate(gradingEmail, 0, 10, SECONDS);
        Runnable canceller = () -> emailHandle.cancel(false);
        scheduler.schedule(canceller, 1, HOURS);
    }
}
