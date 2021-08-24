package com.example.toll;

import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.security.oauth2.client.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


@RestController
public class TollController  {

    @RequestMapping("/")
    public String loadHome(){
        return "home";
    }


    @RequestMapping("/date")
    public String date(){
        LocalDateTime dateObj=LocalDateTime.now();
        String str=dateObj.toString();
        return str;
    }



    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File convertFile = new File("/resources/templates/"+file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return "File is upload successfully";
    }


    
    @RequestMapping("/xml")
    public @ResponseBody ModelAndView xml(){
        List<Student> list=new ArrayList<Student>();
        ModelAndView  modelAndView=new ModelAndView();
        modelAndView.setViewName("XmlView.html");

        File file=new File("C:\\Users\\sukakade\\Documents\\xmlFile.xml");
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList nodeList=document.getElementsByTagName("student");
            for (int i=0; i<nodeList.getLength();i++) {
                Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //to retrieve a node elements
                    Element element=(Element)node;
                    Student stud=new Student();
                    stud.setId(new Integer(element.getElementsByTagName("id").item(0).getTextContent()));
                    stud.setName(element.getElementsByTagName("firstname").item(0).getTextContent());
                    /**
                    modelAndView.addObject("id", stud.getId());
                    modelAndView.addObject("name", stud.getName());
                     */
                    list.add(stud);
                }
            }
        } //try
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /**
        modelAndView.setViewName("XmlView.html");
        Student s1=new Student(101,"Suraj");
        modelAndView.addObject("id", s1.getId());
        modelAndView.addObject("name", s1.getName());
        */
        for(Student student:list) {
            modelAndView.addObject("student", student.getId());
        }

        return modelAndView;

    }


    @GetMapping("/akshay")
    public String akshay(){ return "Akshay Khabale bhosdi wala"; }


}//controller class

enum Status{
    SUCCESS, FAILURE
}
