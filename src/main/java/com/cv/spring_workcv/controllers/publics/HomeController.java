package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.domain.Category;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.CatergoryService;
import com.cv.spring_workcv.services.RecruitmentService;
import com.cv.spring_workcv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    CatergoryService catergoryService;

    @Autowired
    UserService userService;

    @ModelAttribute
    public void addModel(Model model)
    {
        Sort sort = Sort.by("view").descending();
        Sort sortCategory = Sort.by("numberChoose").descending();
        List<User> users = userService.getAll();
        List<Recruitment> recruitments = recruitmentService.getAll(sort);
        List<Recruitment> recruitmentList = recruitments.stream().limit(10).collect(Collectors.toList());
        List<Category> categories = catergoryService.getAll(sortCategory).stream().collect(Collectors.toList());
        List<User> listCandidate  = (List<User>) users.stream().filter(x -> x.getRole().getId() == 1).collect(Collectors.toList());
        List<User> listCompany  = (List<User>) users.stream().filter(x -> x.getRole().getId() == 2).collect(Collectors.toList());
        model.addAttribute("recruitments", recruitmentList);
        model.addAttribute("categories", categories);
        model.addAttribute("numberCompany", listCompany.size());
        model.addAttribute("numberCandidate", listCandidate.size());
        model.addAttribute("numberRecruitment", recruitments.size());
    }
    @GetMapping({"/" , "/index"})
    public ModelAndView loadHomePage()
    {
        ModelAndView mv = new ModelAndView("public/home");
        mv.addObject("activeHome",true);
        return mv;
    }


}
