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
public  interface EmailDAO {
 
    public List getPotentialMembers(Event event);
    public void email(Member member);
    public void gradingEmail(Event event, Member member, List mList);
    public void expiryEmail(Member member);
    public void sendConfirmationEmail(Member member);
    public void sendExpiryEmail(Member member);
    public void sendGradingEmail(Member member);
    }
    

