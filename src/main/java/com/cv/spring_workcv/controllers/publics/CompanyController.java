package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.CatergoryService;
import com.cv.spring_workcv.services.CompanyService;
import com.cv.spring_workcv.services.RecruitmentService;
import com.cv.spring_workcv.services.UserService;
import com.cv.spring_workcv.utils.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user")
public class CompanyController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CatergoryService catergoryService;

    @PostMapping("/update-company")
    public ModelAndView updateProfile(@ModelAttribute("company") Company company, HttpServletRequest request,  RedirectAttributes rd, @RequestParam("file") MultipartFile file){
        String user_id = request.getParameter("user_id");
        User userCheck = userService.getUserById(Integer.parseInt(user_id));
        Company company1 = companyService.getCompanyByUser(userCheck);
        company.setUser(userCheck);
        String nameImage = "";
        nameImage = FileUtil.uploadPdf(request,file);
        if (nameImage == "null") {
            company.setLogo(company1.getLogo());
        } else {
            company.setLogo(nameImage);
        }
        company.setId(company.getId());
        String url = "redirect:profile/" + user_id;
        companyService.save(company);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("update_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @GetMapping("/list-post")
    public  ModelAndView getListPost(HttpServletRequest request, Model model,@RequestParam("page") Optional<Integer> page){

        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);
        if (auth) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            Company company = companyService.getCompanyByUser(user);
            Sort sort = Sort.by("id").descending();
            Pageable pageable = PageRequest.of(page.orElse(0), 5, sort);
            Page<Recruitment> recruitments =  recruitmentService.getRecruitmentByCompany(company,pageable);
            List<Recruitment> recruitmentList = recruitmentService.getRecruitmentByCompany(company);
            int numberPage = recruitmentList.size() / 5;
            if (recruitmentList.size() % 5 != 0){
                numberPage = numberPage +1;
            }
            List<Recruitment> recruitmentSize = recruitmentList.stream().limit(numberPage).collect(Collectors.toList());
            model.addAttribute("list", recruitments);
            model.addAttribute("recruitmentList", recruitmentSize);
            model.addAttribute("numberPage",page.orElse(0).intValue());
            mv = new ModelAndView("public/post-list");
        } else {
            mv = new ModelAndView("redirect:/auth/login");
        }
        return mv;
    }
}
