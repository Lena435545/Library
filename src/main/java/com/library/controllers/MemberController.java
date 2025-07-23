package com.library.controllers;

import com.library.dao.MemberDao;
import com.library.models.Member;
import com.library.repositories.MemberRepository;
import com.library.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberDao memberDao;
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberDao memberDao, MemberService memberService) {
        this.memberDao = memberDao;
        this.memberService = memberService;;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("members", memberService.findAll());
        return ("members/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("member", memberService.findById(id));
        model.addAttribute("books", memberDao.getBooksByMemberId(id));
        model.addAttribute("films", memberDao.getFilmsByMemberId(id));
        model.addAttribute("journals", memberDao.getJournalsByMemberId(id));
        return ("members/show");
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("member") Member member) {
        return ("members/new");
    }

    @PostMapping
    public String save(@ModelAttribute("member") @Valid Member member, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return ("members/new");

        memberService.save(member);
        return ("redirect:/members");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("member", memberService.findById(id));
        return ("members/edit");
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("member") @Valid Member member, BindingResult bindingResult,
                         @PathVariable("id") int id){

        if(bindingResult.hasErrors())
            return ("members/edit");

        memberService.save(member);
        return ("redirect:/members");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        memberService.delete(id);
        return ("redirect:/members");
    }
}
