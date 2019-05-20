package com.test.controller;

/**
 * Created by liujiawang on 2019/5/16.
 */

import com.test.dao.*;
import com.test.model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;


@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("courseDao")
    private CourseDao courseDao;

    @Autowired
    @Qualifier("homeworkDao")
    private HomeworkDao homeworkDao;

    @Autowired
    @Qualifier("studentHomeworkDao")
    private StudentHomeworkDao studentHomeworkDao;

    @Autowired
    @Qualifier("correctDao")
    private CorrectDao correctDao;

    @Autowired
    @Qualifier("teacherCorrectDao")
    private TeacherCorrectDao teacherCorrectDao;

    @Autowired
    @Qualifier("appealDao")
    private AppealDao appealDao;
    @Autowired
    @Qualifier("innerCourseTeacherDao")
    private InnerCourseTeacherDao innerCourseTeacherDao;


    @RequestMapping(value="/getCourse",method = RequestMethod.POST)
    public void getCourse(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String userId = session.getAttribute("userId").toString();
        List<Object[]> list = courseDao.getCourseByStudent(userId);
        List<Map> msg = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("courseName",list.get(i)[0]);
            map.put("teacherName",list.get(i)[1]);
            msg.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("msg",msg);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="getCorrectHomeworkByUser",method = RequestMethod.POST)
    public void getCorrectHomeworkByUser(HttpServletResponse response,HttpSession session) throws IOException {
        String userId = session.getAttribute("userId").toString();
        List<Object[]> list = homeworkDao.getCorrectHomeworkByUser(userId);
        List<Map> msg = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",list.get(i)[0]);
            map.put("fileUrl",list.get(i)[1]);
            map.put("homeworkName",list.get(i)[2]);
            map.put("courseName",list.get(i)[3]);
            map.put("teacherName",list.get(i)[4]);
            msg.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("msg",msg);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/addGrade",method = RequestMethod.POST)
    public void addGrade(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String id = request.getParameter("id");
        String grade = request.getParameter("grade");
        CorrectEntity correct = correctDao.getById(Integer.valueOf(id));
        correct.setGrade(new BigDecimal(grade));
        correctDao.save(correct);
        List<CorrectEntity> list = correctDao.getByStudentHomeworkId(correct.getStudentHomeworkId().toString());
        List<TeacherCorrectEntity> list2 = teacherCorrectDao.getByStudentHomeworkId(correct.getStudentHomeworkId().toString());
        if(list.size()==3){
            double t = 0;
            for(int i=0;i<list.size();i++){
                t += Double.valueOf(list.get(i).getGrade().toString());
            }
            t /= 3;
            StudentHomeworkEntity sh = studentHomeworkDao.getById(correct.getStudentHomeworkId());
            if(list2.size()!=0) {
                HomeworkEntity h = homeworkDao.getById(sh.getHomeworkId());
                t = new Double(h.getPercent()*Double.valueOf(list2.get(0).getGrade().toString())/100)+t*(100-h.getPercent())/100;
            }
            sh.setGrade(new BigDecimal(t));
            studentHomeworkDao.save(sh);
        }

    }

    @RequestMapping(value="/addAppeal",method = RequestMethod.POST)
    public void addAppeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String userId = session.getAttribute("userId").toString();
        String studentHomeworkId = request.getParameter("studentHomeworkId");
        AppealEntity appeal = new AppealEntity();
        appeal.setStudentId(Integer.valueOf(userId));
        appeal.setStudentHomeworkId(Integer.valueOf(studentHomeworkId));
        appeal.setStatus(0);
        appealDao.save(appeal);
    }

    @RequestMapping(value="/getHomeworkByUser",method = RequestMethod.POST)
    public void getHomeworkByUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String userId = session.getAttribute("userId").toString();
        List<Object[]> list = homeworkDao.getHomeworkByUser(userId);
        List<Map> msg = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",list.get(i)[0]);
            map.put("fileUrl",list.get(i)[1]);
            map.put("grade",list.get(i)[2]);
            map.put("name",list.get(i)[3]);
            map.put("courseName",list.get(i)[4]);
            map.put("teacherName",list.get(i)[5]);
            map.put("deadline",list.get(i)[6]);
            int id = Integer.valueOf(list.get(i)[0].toString());
            List<AppealEntity> appeal = appealDao.getByStudentAndHomework(userId,String.valueOf(id));
            if(appeal.size()<=0){
                map.put("status",-1);
            }else{
                map.put("status",appeal.get(0).getStatus());
            }
            msg.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("msg",msg);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String js = json.toString();
        response.getWriter().write(js.toString());
    }

    @RequestMapping(value="/downloadFile",method = RequestMethod.POST)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        String fileName = request.getParameter("fileName");
        File file = new File("");
        String filePath = file.getAbsolutePath();
        filePath = filePath + "/file"+"/"+fileName;
        file = new File(filePath);
        System.out.println("---------file:"+filePath);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            InputStream input = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            byte[] buff =new byte[1024];
            int index=0;
            while((index= input.read(buff))!= -1){
                out.write(buff, 0, index);
                out.flush();
            }
            out.close();
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequestMapping(value="/uploadFile",method = RequestMethod.POST)
    public void uploadFile(@RequestParam MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response,HttpSession session){
        String userId = session.getAttribute("userId").toString();
        String id = request.getParameter("id");
        if(uploadFile!=null){
            String fileName = uploadFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            fileName = String.valueOf(new Date().getTime());
            fileName = fileName + "." + suffix;
            File file = new File("");
            String filePath = file.getAbsolutePath();
            filePath += "/file";
            File t = new File(filePath);
            if(!t.exists()){
                t.mkdirs();
            }
            File newFile=new File(filePath+"/"+fileName);
            try {
                uploadFile.transferTo(newFile);
                StudentHomeworkEntity sh = studentHomeworkDao.getById(Integer.valueOf(id));
                sh.setFileUrl(fileName);
                sh.setStudentId(Integer.valueOf(userId));
                studentHomeworkDao.save(sh);
                List<StudentHomeworkEntity> list = studentHomeworkDao.getByHomeworkId(sh.getHomeworkId());
                List<Integer> user = new ArrayList<>();
                for(int i=0;i<3&&i<list.size();i++){
                    int tem;
                    while(true){
                        tem  = (int)(Math.random()*(list.size()-1+1));
                        int std = list.get(tem).getStudentId();
                        if(!user.contains(std)&&std!=sh.getStudentId()){
                            user.add(std);
                            break;
                        }
                    }
                }
                int shId = 0;
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getStudentId()==Integer.valueOf(userId)){
                        shId = list.get(i).getId();
                    }
                }
                HomeworkEntity home = homeworkDao.getById(sh.getHomeworkId());
                CourseEntity course = courseDao.getById(home.getCourseId());
                InnerCourseTeacherEntity ict = innerCourseTeacherDao.getByCourseId(course.getCourseId());
                TeacherCorrectEntity tc = new TeacherCorrectEntity();
                tc.setStudentHomeworkId(shId);
                tc.setTeacherId(ict.getTeacherId());
                teacherCorrectDao.save(tc);
                for(int i=0;i<user.size();i++){
                    CorrectEntity correctEntity = new CorrectEntity();
                    correctEntity.setStudentId(user.get(i));
                    correctEntity.setStudentHomeworkId(sh.getId());
                    correctDao.save(correctEntity);
                    correctDao.getSession().clear();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(HttpSession session){
        try{
            Object user = session.getAttribute("userId");
            if(user != null){
                return "index";
            }
        }catch(Exception e){
            return "redirect:/login";
        }
        return "redirect:/login";
    }

    @RequestMapping(value="/homework",method = RequestMethod.GET)
    public String homework(HttpSession session){
        try{
            Object user = session.getAttribute("userId");
            if(user != null){
                return "homework";
            }
        }catch(Exception e){
            return "redirect:/login";
        }
        return "redirect:/login";
    }

    @RequestMapping(value="/correct",method = RequestMethod.GET)
    public String correct(HttpSession session){
        try{
            Object user = session.getAttribute("userId");
            if(user != null){
                return "correct";
            }
        }catch(Exception e){
            return "redirect:/login";
        }
        return "redirect:/login";
    }
    @RequestMapping(value="/change",method = RequestMethod.GET)
    public String change(HttpSession session){
        try{
            Object user = session.getAttribute("userId");
            if(user != null){
                session.removeAttribute("changeMessage");
                return "change";
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
            return "redirect:/student/change";
        }else{
            if(!password.equals(user.getPassword())){
                session.setAttribute("changeMessage","密码不正确");
                return "redirect:/student/change";
            }
            user.setPassword(password1);
            userDao.save(user);
            session.setAttribute("changeMessage","修改成功");
            return "redirect:/student/change";
        }

    }
}
