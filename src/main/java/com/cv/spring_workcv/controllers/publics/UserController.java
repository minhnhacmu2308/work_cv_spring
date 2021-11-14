package com.cv.spring_workcv.controllers.publics;

import com.cloudinary.Cloudinary;
import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.UserService;
import com.cv.spring_workcv.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ObjectUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    @GetMapping({"/profile/{id}" })
    public ModelAndView profile(@PathVariable int id)
    {
        ModelAndView mv = new ModelAndView("public/profile");
        User user = userService.getUserById(id);
        mv.addObject("userInformation",user);
        return mv;
    }

    @PostMapping("/confirm-account")
    public  ModelAndView comfirm(HttpServletRequest request, RedirectAttributes rd){
        String email = request.getParameter("email");
        User user = userService.checkEmailExist(email);
        String link = "http://localhost:8080/user/confirm/" + email;
        String html = "<div  class=\"container-fluid\" style=\"text-align: center\">\n" +
                "    <p style=\"font-size: 20px;font-weight: bold;color: #aaa;margin-top: 10px\">Confirm email login</p>\n" +
                "    <div style=\"width: 600px;height: 400px;border-radius: 5px;\n" +
                "    box-shadow: rgba(0, 0, 0, 0.4) 0px 0px 10px;margin: 20px auto;padding: 15px\">\n" +
                "        <p style=\"line-height: 35px;font-size: 16px\">Xin chào, <span style=\"font-weight: bold;color: black\" >"+user.getFullName()+"</span><br>\n" +
                "        <div style=\"height: 50px ;width: 100px;background-color: #7d90f6;\n" +
                "            border-radius: 5px;line-height:50px;padding-left:50px;margin: 10px auto;display: flex\">\n" +
                "            <a href="+link+" style=\"color: white;text-decoration: none\">Confirm</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <p>Contact WorkCV:<br></p>\n" +
                "            - Phone number:<span style=\"color:#5f80ec\">(024) 6680 5588</span><br>\n" +
                "            - Email: <a href=\"#\" style=\"color:#5f80ec\"> hotro@workcv.vn</a>\n" +
                "    </div>\n" +
                "</div>";

        try {
            MailUtil.sendHtmlMail(this.javaMailSenderImpl,email,"Bấm vào đường dẫn để xác thực tài khoản",html);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        String url = "redirect:profile/" + user.getId();
        rd.addFlashAttribute(CommonConstants.CONFIRM_ACCOUNT,
                messageSource.getMessage("confirm_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @GetMapping("/confirm/{email}")
    public ModelAndView confirmAccount(@PathVariable String email){
        User user = userService.checkEmailExist(email);
        user.setStatus(1);
        userService.save(user);
        String url = "redirect:/user/profile/" + user.getId();
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @PostMapping("/upload")
    public @ResponseBody String upload(@RequestParam("file") MultipartFile image,HttpServletRequest request){
        Map config = new HashMap();
        String email = request.getParameter("email");
        User user = userService.checkEmailExist(email);
        config.put("resource_type","auto");
        try {
            Map r =this.cloudinary.uploader().upload(image.getBytes(), config);
            System.out.println(r);
            String nameImage = (String) r.get("secure_url");
            user.setImage(nameImage);
            userService.save(user);
            return nameImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/update-profile")
    public ModelAndView updateProfile(@ModelAttribute("user") User user, RedirectAttributes rd){
        User userCheck = userService.checkEmailExist(user.getEmail());
        user.setId(userCheck.getId());
        user.setPassword(userCheck.getPassword());
        user.setRole(userCheck.getRole());
        user.setStatus(userCheck.getStatus());
        user.setImage(userCheck.getImage());
        String url = "redirect:profile/" + userCheck.getId();
        userService.save(user);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("update_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }
}
