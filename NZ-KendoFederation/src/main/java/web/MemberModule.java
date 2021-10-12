/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.MemberCollectionsDAO;
import dao.MemberDAO;
import domain.Member;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Status;

/**
 *
 * @author dugwi731
 */
public class MemberModule extends Jooby {

    // MemberDAO memberDao = new MemberCollectionsDAO();
    public MemberModule(MemberDAO memberDao) {
        get("/api/members/:email", (req) -> {
            String email = req.param("email").value();

            return memberDao.signInSimple(email);
        });

        post("/api/register", (req, rsp) -> {
//                    String json = req.body().to(String.class);
//                    System.out.println(json);
            Member member = req.body().to(Member.class);
            memberDao.saveMember(member);
            rsp.status(Status.CREATED);
        });
    }
}
