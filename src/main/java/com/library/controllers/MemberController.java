package com.library.controllers;

import com.library.dao.MemberDao;
import com.library.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberDao memberDao;

    @Autowired
    public MemberController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("members", memberDao.index());
        return ("members/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("member", memberDao.show(id));
        return ("members/show");
    }

    @GetMapping("/new")
    public String newMember(@ModelAttribute("member") Member member) {
        return ("members/new");
    }

    @PostMapping
    public String create(@ModelAttribute("member") Member member){
        memberDao.save(member);
        return ("redirect:/members");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("member", memberDao.show(id));
        return ("members/edit");
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("member") Member member, @PathVariable("id") int id){
        memberDao.update(id, member);
        return ("redirect:/members");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        memberDao.delete(id);
        return ("redirect:/members");
    }
}
