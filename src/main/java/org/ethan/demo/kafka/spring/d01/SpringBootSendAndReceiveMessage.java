//package org.ethan.demo.kafka.spring.d01;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
///**
// * 使用spring boot来使用spring kafka
// * @author Ethan Huang
// * @since 2018/07/25
// */
//@SpringBootApplication
//public class SpringBootSendAndReceiveMessage {
//    public static void main(String[] args) {
//        SpringApplication.run(SpringBootSendAndReceiveMessage.class, args);
//    }
//}
//
//@Component
//class InitCommandLineRunner implements CommandLineRunner {
//
//    @Autowired
//    private KafkaTemplate<String, String> template;
//    private final CountDownLatch latch = new CountDownLatch(3);
//
//    @KafkaListener(topics = "myTopic")
//    public void listener(ConsumerRecord<?, ?> cr) {
//        System.out.println(cr.toString());
//        latch.countDown();
//    }
//
//    @Override
//    public void run(String... strings) throws Exception {
//        template.send("myTopic", "foo1");
//        template.send("myTopic", "foo2");
//        template.send("myTopic", "foo3");
//        latch.await(60, TimeUnit.SECONDS);
//        System.out.println("send over!");
//    }
//}
