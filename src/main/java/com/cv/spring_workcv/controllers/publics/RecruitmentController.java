package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.*;
import com.cv.spring_workcv.services.*;
import com.cv.spring_workcv.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Objects;
import java.util.Optional;
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

    @Autowired
    ApplyPostService applyPostService;

    @GetMapping({"/index" })
    public ModelAndView index(HttpServletRequest request, Model model,@RequestParam("page") Optional<Integer> page)
    {
        ModelAndView mv = new ModelAndView("public/recruitment");
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<Recruitment> recruitments = recruitmentService.getList(pageable);
        List<Object[]> companies = companyService.getAll();
        List<Recruitment> recruitmentList = recruitmentService.getAll();
        int numberPage = recruitmentList.size() / 5;
        if (recruitmentList.size() % 5 != 0){
            numberPage = numberPage +1;
        }
        List<Recruitment> recruitmentSize = recruitmentList.stream().limit(numberPage).collect(Collectors.toList());
        mv.addObject("activeRe",true);
        mv.addObject("list", recruitments);
        model.addAttribute("recruitmentList", recruitmentSize);
        mv.addObject("companies", companies);
        mv.addObject("numberPage",page.orElse(0).intValue());
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

    @GetMapping({"/editpost/{id}" })
    public ModelAndView editpostRecruitment(@PathVariable int id,HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("public/edit-job");
        Sort sortCategory = Sort.by("numberChoose").descending();
        List<Category> categories = catergoryService.getAll(sortCategory).stream().collect(Collectors.toList());
        Recruitment recruitment = recruitmentService.getRecruitmentById(id);
        mv.addObject("categories", categories);
        mv.addObject("recruitment", recruitment);
        return mv;
    }

    @PostMapping ({"/add" })
    public ModelAndView add(@ModelAttribute("recruitment") Recruitment recruitment, RedirectAttributes rd, HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);
        if (auth) {
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
            mv = new ModelAndView("redirect:post");
        } else {
            mv = new ModelAndView("redirect:/auth/login");
        }
        return mv;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView getDetail(@PathVariable int id,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("public/detail-post");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        Recruitment recruitment = recruitmentService.getRecruitmentById(id);
        List<Recruitment> recruitmentListRelated = recruitmentService.getRelated(recruitment.getCategory());
        List<Recruitment> recruitmentList = recruitmentListRelated.stream().limit(5).collect(Collectors.toList());
        List<ApplyPost> applyPosts = applyPostService.getApplyPostsByRecruitment(recruitment);
        if (Objects.nonNull(user) && user.getId() == recruitment.getCompany().getUser().getId()) {
            mv.addObject("applyPosts", applyPosts);
        }
        mv.addObject("recruitment",recruitment);
        mv.addObject("listRelated",recruitmentList);

        return mv;
    }

    @PostMapping ({"/edit" })
    public ModelAndView edit(@ModelAttribute("recruitment") Recruitment recruitment, RedirectAttributes rd, HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);
        if (auth) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            String categoryId = request.getParameter("category_id");
            Company company = companyService.getCompanyByUser(user);
            Category category = catergoryService.getCategoryById(Integer.parseInt(categoryId));
            recruitment.setCategory(category);
            recruitment.setCompany(company);
            recruitmentService.save(recruitment);
            rd.addFlashAttribute(CommonConstants.SUCCESS,
                    messageSource.getMessage("update_success", null, Locale.getDefault()));
            mv = new ModelAndView("redirect:/user/list-post");
        } else {
            mv = new ModelAndView("redirect:/auth/login");
        }
        return mv;
    }
}
