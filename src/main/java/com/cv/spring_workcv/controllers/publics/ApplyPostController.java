package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.*;
import com.cv.spring_workcv.services.ApplyPostService;
import com.cv.spring_workcv.services.CvService;
import com.cv.spring_workcv.services.RecruitmentService;
import com.cv.spring_workcv.utils.FileUtil;
import com.cv.spring_workcv.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class ApplyPostController {

    @Autowired
    ApplyPostService applyPostService;

    @Autowired
    CvService cvService;

    @Autowired
    RecruitmentService recruitmentService;

    Middleware middleware = new Middleware();

    @PostMapping("/apply-job")
    public @ResponseBody String apply(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        boolean check = middleware.middleware(request);
        if(check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            String idRecuitement = request.getParameter("idRe");
            String text = request.getParameter("text");
            Recruitment recruitment = recruitmentService.getRecruitmentById(Integer.parseInt(idRecuitement));
            ApplyPost applyPostCheck = applyPostService.findApplyPostByRecruitmentAndUser(recruitment,user);
            String name =  FileUtil.uploadPdf(request,file);
            if (Objects.nonNull(applyPostCheck))  {
                return "exist";
            }else{
                ApplyPost applyPost = new ApplyPost();
                applyPost.setUser(user);
                applyPost.setText(text);
                applyPost.setCreatedAt(java.time.LocalDate.now().toString());
                applyPost.setRecruitment(recruitment);
                applyPost.setNameCv(name);
                applyPostService.save(applyPost);
                return "true";
            }
        }else{
            return "false";
        }
    }
}
