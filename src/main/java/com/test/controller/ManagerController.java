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
import java.util.List;
import java.util.Map;

/**
 * Created by liujiawang on 2019/5/17.
 */

@Controller
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    @Qualifier("managerDao")
    private ManagerDao managerDao;

    @Autowired
    @Qualifier("correctDao")
    private CorrectDao correctDao;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("courseDao")
    private CourseDao courseDao;

    @Autowired
    @Qualifier("innerCourseTeacherDao")
    private InnerCourseTeacherDao innerCourseTeacherDao;

    @Autowired
    @Qualifier("innerCourseStudentDao")
    private InnerCourseStudentDao innerCourseStudentDao;

    @RequestMapping(value="/getCourse",method = RequestMethod.POST)
    public void getCourse(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        List<Map> list = correctDao.getCourseByManager();
        JSONObject json = new JSONObject();
        json.put("list",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());

    }

    @RequestMapping(value="/deleteCourse",method = RequestMethod.POST)
    public void deleteCourse(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        String courseId = request.getParameter("courseId");
        CourseEntity course = courseDao.getById(Integer.valueOf(courseId));
        courseDao.delete(course);
    }

    @RequestMapping(value="/editCourse",method = RequestMethod.POST)
    public void editCourse(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String id = request.getParameter("id");
        String courseName = request.getParameter("courseName");
        String teacherId = request.getParameter("teacherId");
        String ictId = request.getParameter("ictId");
        CourseEntity course = courseDao.getById(Integer.valueOf(id));
        course.setCourseName(courseName);
        courseDao.save(course);
        InnerCourseTeacherEntity ict = innerCourseTeacherDao.getById(Integer.valueOf(ictId));
        ict.setTeacherId(Integer.valueOf(teacherId));
        innerCourseTeacherDao.save(ict);

    }
    @RequestMapping(value="/addCourse",method = RequestMethod.POST)
    public void addCourse(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String courseName = request.getParameter("courseName");
        String teacherId = request.getParameter("teacherId");
        CourseEntity course = new CourseEntity();
        course.setCourseName(courseName);
        courseDao.save(course);
        course = courseDao.getByCourseName(courseName).get(0);
        InnerCourseTeacherEntity ict = new InnerCourseTeacherEntity();
        ict.setTeacherId(Integer.valueOf(teacherId));
        ict.setCourseId(course.getCourseId());
        innerCourseTeacherDao.save(ict);
    }

    @RequestMapping(value="/addTeacher",method = RequestMethod.POST)
    public void addTeacher(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setIsTeacher(1);
        userDao.save(user);
    }

    @RequestMapping(value="/addStudent",method = RequestMethod.POST)
    public void addStudent(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setIsTeacher(0);
        userDao.save(user);
    }

    @RequestMapping(value="/editTeacher",method = RequestMethod.POST)
    public void editTeacher(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String id=  request.getParameter("id");
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserEntity user = userDao.getById(Integer.valueOf(id));
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        userDao.save(user);
    }

    @RequestMapping(value="/getAllTeacher",method = RequestMethod.POST)
    public void getAllTeacher(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        List<UserEntity> list = userDao.getAllTeacher();
        JSONObject json = new JSONObject();
        json.put("list",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/getCourseDetail",method = RequestMethod.POST)
    public void getCourseDetail(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        String courseId = request.getParameter("courseId");
        List<Map> list = courseDao.getAllStudentByCourseId(courseId);
        JSONObject json = new JSONObject();
        json.put("list",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/deleteStudentCourse",method = RequestMethod.POST)
    public void deleteStudentCourse(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String icsId = request.getParameter("icsId");
        innerCourseStudentDao.delete(innerCourseStudentDao.getById(Integer.valueOf(icsId)));
    }

    @RequestMapping(value="/getStudentNotInCourse",method = RequestMethod.POST)
    public void getStudentNotInCourse(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        String courseId = request.getParameter("courseId");
        List<UserEntity> list = userDao.getStudentNotInCourse(courseId);
        JSONObject json = new JSONObject();
        json.put("list",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }
    @RequestMapping(value="/addStudentCourse",method = RequestMethod.POST)
    public void addStudentCourse(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        InnerCourseStudentEntity ics = new InnerCourseStudentEntity();
        ics.setCourseId(Integer.valueOf(courseId));
        ics.setStudentId(Integer.valueOf(studentId));
        innerCourseStudentDao.save(ics);
    }

    @RequestMapping(value="/getAllStudent",method = RequestMethod.POST)
    public void getAllStudent(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        List<UserEntity> list = userDao.getAllStudent();
        JSONObject json = new JSONObject();
        json.put("list",list);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/deleteStudent",method = RequestMethod.POST)
    public void deleteStudent(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        String userId = request.getParameter("userId");
        UserEntity user = userDao.getById(Integer.valueOf(userId));
        userDao.delete(user);
    }
    @RequestMapping(value="/deleteTeacher",method = RequestMethod.POST)
    public void deleteTeacher(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
        String userId = request.getParameter("userId");
        UserEntity user = userDao.getById(Integer.valueOf(userId));
        userDao.delete(user);
    }


    @RequestMapping(value="/courseDetail",method = RequestMethod.GET)
    public String courseDetail(HttpSession session){
        try{
            Object user = session.getAttribute("managerId");
           // System.out.println(user);
            if(user != null){
                return "courseDetail";
            }
        }catch(Exception e){
            return "redirect:/managerLogin";
        }
        return "redirect:/managerLogin";
    }

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(HttpSession session){
        try{
            Object user = session.getAttribute("managerId");
            System.out.println(user);
            if(user != null){
                return "managerIndex";
            }
        }catch(Exception e){
            return "redirect:/managerLogin";
        }
        return "redirect:/managerLogin";
    }

    @RequestMapping(value="/teacher",method = RequestMethod.GET)
    public String teacher(HttpSession session){
        try{
            Object user = session.getAttribute("managerId");
            System.out.println(user);
            if(user != null){
                return "managerTeacher";
            }
        }catch(Exception e){
            return "redirect:/managerLogin";
        }
        return "redirect:/managerLogin";
    }

    @RequestMapping(value="/student",method = RequestMethod.GET)
    public String student(HttpSession session){
        try{
            Object user = session.getAttribute("managerId");
            System.out.println(user);
            if(user != null){
                return "managerStudent";
            }
        }catch(Exception e){
            return "redirect:/managerLogin";
        }
        return "redirect:/managerLogin";
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ManagerEntity manager = managerDao.getByUsername(username);
        if(manager==null){
            session.setAttribute("managerMessage","该账号不存在");
        }else{
            if(manager.getPassword().equals(password)){
                session.setAttribute("managerId",manager.getManagerId());
                return "managerIndex";
            }
            session.setAttribute("managerMessage","密码错误");
        }
        return "redirect:/managerLogin";

    }
}
