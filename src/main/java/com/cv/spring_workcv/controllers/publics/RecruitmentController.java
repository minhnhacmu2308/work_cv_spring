package com.cv.spring_workcv.controllers.publics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("recruitment")
public class RecruitmentController {

    @GetMapping({"/index" })
    public ModelAndView index()
    {
        ModelAndView mv = new ModelAndView("public/listJob");
        mv.addObject("activeRe",true);
        return mv;
    }
}
