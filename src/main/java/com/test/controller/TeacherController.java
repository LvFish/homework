package com.test.controller;

import com.test.dao.*;
import com.test.model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;
    @Autowired
    @Qualifier("courseDao")
    private CourseDao courseDao;
    @Autowired
    @Qualifier("correctDao")
    private CorrectDao correctDao;
    @Autowired
    @Qualifier("homeworkDao")
    private HomeworkDao homeworkDao;
    @Autowired
    @Qualifier("innerCourseStudentDao")
    private InnerCourseStudentDao innerCourseStudentDao;
    @Autowired
    @Qualifier("studentHomeworkDao")
    private StudentHomeworkDao studentHomeworkDao;
    @Autowired
    @Qualifier("teacherCorrectDao")
    private TeacherCorrectDao teacherCorrectDao;

    @Autowired
    @Qualifier("appealDao")
    private AppealDao appealDao;

    @RequestMapping(value="/addHomework",method = RequestMethod.POST)
    public void addHomework(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String deadline = request.getParameter("deadline");
        String courseId = request.getParameter("courseId");
        String name = request.getParameter("name");
        String percent = request.getParameter("percent");
        HomeworkEntity homework = new HomeworkEntity();
        homework.setCourseId(Integer.valueOf(courseId));
        homework.setDeadline(new Timestamp(Long.valueOf(deadline)));
        homework.setName(name);
        homework.setPercent(new Double(percent).intValue());
        homeworkDao.save(homework);
        HomeworkEntity home = homeworkDao.getNewLast();
        System.out.println(home.getHomeworkId());
        List<InnerCourseStudentEntity> list = innerCourseStudentDao.getByCourseId(courseId);
        for(int i=0;i<list.size();i++){
            StudentHomeworkEntity st = new StudentHomeworkEntity();
            st.setStudentId(list.get(i).getStudentId());
            st.setHomeworkId(home.getHomeworkId());
            studentHomeworkDao.save(st);
            studentHomeworkDao.getSession().clear();
        }
    }

    @RequestMapping(value="/getCourse",method = RequestMethod.POST)
    public void getCourse(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String userId = session.getAttribute("userId").toString();
        List<Object[]> list = courseDao.getCourseByTeacher(userId);
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Map<String ,Object> map = new HashMap<>();
            map.put("courseId",list.get(i)[0]);
            map.put("courseName",list.get(i)[1]);
            map.put("cnt",list.get(i)[2]);
            list2.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("courseList",list2);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/getHomework",method = RequestMethod.POST)
    public void getHomework(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String userId = session.getAttribute("userId").toString();
        JSONObject json = new JSONObject();
        json.put("homeworkList",homeworkDao.getHomeworkByTeacher(userId));
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/getHomeworkDetail",method = RequestMethod.POST)
    public void getHomeworkDetail(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String homeworkId = request.getParameter("homeworkId");
        JSONObject json = new JSONObject();
        json.put("homeworkDetailList",studentHomeworkDao.getHomeworkDetail(homeworkId));
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/getCorrectDetail",method = RequestMethod.POST)
    public void getCorrectDetail(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String studentHomeworkId = request.getParameter("studentHomeworkId");
        JSONObject json = new JSONObject();
        List<Map> list = correctDao.getCorrectDetail(studentHomeworkId);
        list.addAll(correctDao.getCorrectDetailByTeacher(studentHomeworkId));
        json.put("correctList",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/getCorrectHomework",method = RequestMethod.POST)
    public void getCorrectHomework(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String userId = session.getAttribute("userId").toString();
        JSONObject json = new JSONObject();
        List<Map> list = homeworkDao.getCorrectHomework(userId);
        json.put("correctList",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/deleteCorrect",method = RequestMethod.POST)
    public void deleteCorrect(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        TeacherCorrectEntity tc = teacherCorrectDao.getById(Integer.valueOf(id));
        teacherCorrectDao.delete(tc);
    }


    @RequestMapping(value="/getAppeal",method = RequestMethod.POST)
    public void getAppeal(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String userId = session.getAttribute("userId").toString();
        JSONObject json = new JSONObject();
        List<Map> list = appealDao.getAppealByTeacher(userId);
        json.put("appealList",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }


    @RequestMapping(value="/updateGrade",method = RequestMethod.POST)
    public void updateGrade(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String studentHomeworkId = request.getParameter("studentHomeworkId");
        String grade = request.getParameter("grade");
        StudentHomeworkEntity studentHomeworkEntity = studentHomeworkDao.getById(Integer.valueOf(studentHomeworkId));
        studentHomeworkEntity.setGrade(new BigDecimal(grade));
        studentHomeworkDao.save(studentHomeworkEntity);
    }

    @RequestMapping(value="/updateAppealStatus",method = RequestMethod.POST)
    public void updateAppealStatus(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        String appealId = request.getParameter("appealId");
        String status  = request.getParameter("status");
        AppealEntity appeal = appealDao.getById(Integer.valueOf(appealId));
        appeal.setStatus(Integer.valueOf(status));
        appealDao.save(appeal);
    }


    @RequestMapping(value="/addGrade",method = RequestMethod.POST)
    public void addGrade(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String id = request.getParameter("id");
        String grade = request.getParameter("grade");
        TeacherCorrectEntity tc = teacherCorrectDao.getById(Integer.valueOf(id));
        tc.setGrade(Double.valueOf(grade).intValue());
        teacherCorrectDao.save(tc);
        List<CorrectEntity> list = correctDao.getByStudentHomeworkId(tc.getStudentHomeworkId().toString());
        if(list.size()>=3){
            StudentHomeworkEntity sh = studentHomeworkDao.getById(tc.getStudentHomeworkId());
            HomeworkEntity h = homeworkDao.getById(sh.getHomeworkId());
            Double t = 0.0;
            for(int i=0;i<list.size();i++){
                t += Double.valueOf(list.get(i).getGrade().toString());
            }
            t /= list.size();
            t = new Double(h.getPercent()*Double.valueOf(tc.getGrade().toString())/100)+t*(100-h.getPercent())/100;
            sh.setGrade(new BigDecimal(t));
            studentHomeworkDao.save(sh);
        }

    }





    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(HttpSession session){
        try{
            Object teacher = session.getAttribute("userId");
            if(teacher == null){
                return "redirect:/login";
            }
            return "teacherIndex";
        }catch(Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/publish",method = RequestMethod.GET)
    public String publish(HttpSession session){
        try{
            Object teacher = session.getAttribute("userId");
            if(teacher == null){
                return "redirect:/login";
            }
            return "publish";
        }catch(Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/detail",method = RequestMethod.GET)
    public String detail(HttpSession session){
        try{
            Object teacher = session.getAttribute("userId");
            if(teacher == null){
                return "redirect:/login";
            }
            return "detail";
        }catch(Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/correctDetail",method = RequestMethod.GET)
    public String correctDetail(HttpSession session){
        try{
            Object teacher = session.getAttribute("userId");
            if(teacher == null){
                return "redirect:/login";
            }
            return "correctDetail";
        }catch(Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/correct",method = RequestMethod.GET)
    public String correct(HttpSession session){
        try{
            Object teacher = session.getAttribute("userId");
            if(teacher == null){
                return "redirect:/login";
            }
            return "teacherCorrect";
        }catch(Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/dealQuestion",method = RequestMethod.GET)
    public String dealQuestion(HttpSession session){
        try{
            Object teacher = session.getAttribute("userId");
            if(teacher == null){
                return "redirect:/login";
            }
            return "dealQuestion";
        }catch(Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/change",method = RequestMethod.GET)
    public String change(HttpSession session){
        try{
            Object user = session.getAttribute("userId");
            if(user != null){
                return "teacherChange";
            }
        }catch(Exception e){
            return "redirect:/login";
        }
        return "redirect:/login";
    }
    @RequestMapping(value="/change",method = RequestMethod.POST)
    public String change(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String userId = session.getAttribute("userId").toString();
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        UserEntity user = userDao.getById(Integer.valueOf(userId));
        if(!password1.equals(password2)){
            session.setAttribute("changeMessage","密码不一致");
            return "redirect:/teacher/change";
        }else{
            if(!password.equals(user.getPassword())){
                session.setAttribute("changeMessage","密码不正确");
                return "redirect:/teacher/change";
            }
            user.setPassword(password1);
            userDao.save(user);
            session.setAttribute("changeMessage","修改成功");
            return "redirect:/teacher/change";
        }

    }
}
