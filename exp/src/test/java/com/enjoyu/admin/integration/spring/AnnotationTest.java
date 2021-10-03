package com.enjoyu.admin.integration.spring;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class AnnotationTest {
    @Test
    public void test() {
        System.out.println("ParentController getAnnotation : " + AnnotationUtils.getAnnotation(ParentController.class, Controller.class));
        System.out.println("ChildController getAnnotation : " + AnnotationUtils.getAnnotation(ChildController.class, Controller.class));
        System.out.println("ChildController getAnnotation : " + AnnotationUtils.getAnnotation(ChildController.class, RestController.class));
        System.out.println();

        System.out.println("ParentController findAnnotation : " + AnnotationUtils.findAnnotation(ParentController.class, Controller.class));
        System.out.println("ChildController findAnnotation : " + AnnotationUtils.findAnnotation(ChildController.class, Controller.class));
        System.out.println("ChildController findAnnotation : " + AnnotationUtils.findAnnotation(ChildController.class, RestController.class));
        System.out.println();

        System.out.println("ParentController isAnnotated : " + AnnotatedElementUtils.isAnnotated(ParentController.class, Controller.class));
        System.out.println("ParentController getMergedAnnotation : " + AnnotatedElementUtils.getMergedAnnotation(ParentController.class, Controller.class));
        System.out.println("ChildController isAnnotated : " + AnnotatedElementUtils.isAnnotated(ChildController.class, Controller.class));
        System.out.println("ChildController getMergedAnnotation : " + AnnotatedElementUtils.getMergedAnnotation(ChildController.class, Controller.class));
        System.out.println("ChildController getMergedAnnotation : " + AnnotatedElementUtils.getMergedAnnotation(ChildController.class, RestController.class));
        System.out.println("ChildController getAllMergedAnnotations : " + AnnotatedElementUtils.getAllMergedAnnotations(ChildController.class, RestController.class));
        System.out.println();

        System.out.println("ParentController hasAnnotation : " + AnnotatedElementUtils.hasAnnotation(ParentController.class, Controller.class));
        System.out.println("ParentController findMergedAnnotation : " + AnnotatedElementUtils.findMergedAnnotation(ParentController.class, Controller.class));
        System.out.println("ChildController hasAnnotation : " + AnnotatedElementUtils.hasAnnotation(ChildController.class, Controller.class));
        System.out.println("ChildController findMergedAnnotation : " + AnnotatedElementUtils.findMergedAnnotation(ChildController.class, Controller.class));
        System.out.println("ChildController findMergedAnnotation : " + AnnotatedElementUtils.findMergedAnnotation(ChildController.class, RestController.class));
        System.out.println("ChildController findAllMergedAnnotations : " + AnnotatedElementUtils.findAllMergedAnnotations(ChildController.class, RestController.class));
    }
}

@RestController("p")
class ParentController {
}
@Controller("c")
class ChildController extends ParentController {
}
