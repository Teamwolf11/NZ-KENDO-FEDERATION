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

	MemberDAO memberDao = new MemberCollectionsDAO();

	public MemberModule(MemberDAO memberDao) {
		get("/api/members/:email", (req) -> {
			String email = req.param("email").value();
			
			if (memberDao.getMember(email) == null) {
				return new Result().status(Status.NOT_FOUND);
			} else {
				return memberDao.getMember(email);
			}
		});
		
		post("/api/register", (req, rsp) -> {
			Member member = req.body().to(Member.class);
			memberDao.saveMember(member);
			rsp.status(Status.CREATED);
		});
	}
}