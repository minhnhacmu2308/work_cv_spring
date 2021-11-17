package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.CatergoryService;
import com.cv.spring_workcv.services.CompanyService;
import com.cv.spring_workcv.services.UserService;
import com.cv.spring_workcv.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("user")
public class CompanyController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

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
    public  ModelAndView getListPost(){
        ModelAndView mv = new ModelAndView("public/post-list");
        return mv;
    }
}
