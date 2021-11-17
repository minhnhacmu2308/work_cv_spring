package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.Category;
import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.CatergoryService;
import com.cv.spring_workcv.services.CompanyService;
import com.cv.spring_workcv.services.RecruitmentService;
import com.cv.spring_workcv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("recruitment")
public class RecruitmentController {

    @Autowired
    CatergoryService catergoryService;

    @Autowired
    CompanyService companyService;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @GetMapping({"/index" })
    public ModelAndView index()
    {
        ModelAndView mv = new ModelAndView("public/listJob");
        mv.addObject("activeRe",true);
        return mv;
    }

    @GetMapping({"/post" })
    public ModelAndView postRecruitment(Model model)
    {
        Sort sortCategory = Sort.by("numberChoose").descending();
        List<Category> categories = catergoryService.getAll(sortCategory).stream().collect(Collectors.toList());
        model.addAttribute("categories", categories);
        ModelAndView mv = new ModelAndView("public/post-job");
        return mv;
    }

    @PostMapping ({"/add" })
    public ModelAndView add(@ModelAttribute("recruitment") Recruitment recruitment, RedirectAttributes rd, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        String categoryId = request.getParameter("category_id");
        Company company = companyService.getCompanyByUser(user);
        Category category = catergoryService.getCategoryById(Integer.parseInt(categoryId));
        recruitment.setCategory(category);
        recruitment.setCompany(company);
        recruitment.setStatus(1);
        recruitment.setCreatedAt(java.time.LocalDate.now().toString());
        category.setNumberChoose(category.getNumberChoose()+1);
        recruitmentService.save(recruitment);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("post_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView("redirect:post");
        return mv;
    }
}
