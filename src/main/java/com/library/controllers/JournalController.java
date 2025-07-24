package com.library.controllers;

import com.library.models.Journal;
import com.library.models.Member;
import com.library.services.JournalService;
import com.library.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/journals")
public class JournalController {

    private final JournalService journalService;
    private final MemberService memberService;

    public JournalController(JournalService journalService, MemberService memberService) {
        this.memberService = memberService;
        this.journalService = journalService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("journals", journalService.findAll());
        return ("journals/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("member") Member member) {
        model.addAttribute("journal", journalService.findById(id));

        Optional<Member> journalOwner = journalService.getJournalOwner(id);

        if (journalOwner.isPresent())
            model.addAttribute("owner", journalOwner.get());
        else
            model.addAttribute("members", memberService.findAll());

        return ("journals/show");
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("journal") Journal journal) {
        return ("journals/new");
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("journal") @Valid Journal journal, BindingResult bindingResult,
                       @RequestParam("image")MultipartFile file) {
        if (bindingResult.hasErrors())
            return ("journals/new");
        journalService.save(journal, file);
        return ("redirect:/journals");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("journal", journalService.findById(id));
        return ("journals/edit");
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("journal") @Valid Journal journal,
                         BindingResult bindingResult, @RequestParam("image") MultipartFile file) {
        if (bindingResult.hasErrors())
            return ("journals/edit");
        journalService.update(id, journal, file);
        return ("redirect:/journals");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        journalService.delete(id);
        return ("redirect:/journals");
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("member") Member selectedMember){
        journalService.assign(id, selectedMember);
        return "redirect:/journals/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        journalService.release(id);
        return "redirect:/journals/" + id;
    }

}
