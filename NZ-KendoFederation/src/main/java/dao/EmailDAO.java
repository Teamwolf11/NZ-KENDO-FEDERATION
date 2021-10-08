///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package dao;
//
//import domain.Member;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.CompletableFuture;
//import static java.util.concurrent.TimeUnit.HOURS;
//import static java.util.concurrent.TimeUnit.SECONDS;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.SimpleEmail;
//
///**
// *
// * @author benmcmahon
// */
//public  class EmailDAO {
//    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//    
//    public static void main(String[]args){
//        Member emailMember= new Member();
//        emailMember.setEmail("example@gamil.com");
//        emailMember.setfName("b");
//        emailMember.setlName("m");
//        emailMember.setPassword("l");
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
//                            " " + member.getlName() + "Your username and password are" + 
//                            member.getPassword() + member.getEmail());
//
//                    email.addTo("benjaminm.12184@gmail.com");
//                    email.send();
//                } catch (EmailException ex) {
//                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            });
//	}
//   
//    
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
