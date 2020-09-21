package com.lambdaschool.apollo;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Locale;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData
        implements CommandLineRunner {
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    /**
     * Connects the survey service to this process
     */
    @Autowired
    SurveyService surveyService;

    /**
     * Connects the context service to this process
     */
    @Autowired
    ContextService contextService;

    /**
     * Connects the question service to this process
     */
    @Autowired
    QuestionService questionService;

    /**
     * Connects the topic service to this process
     */
    @Autowired
    TopicService topicService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args)
            throws
            Exception {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
                r1));
        admins.add(new UserRoles(new User(),
                r2));
        admins.add(new UserRoles(new User(),
                r3));
        User u1 = new User("admin",
                "admin@lambdaschool.local",
                admins);

        userService.save(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                r3));
        datas.add(new UserRoles(new User(),
                r2));
        User u2 = new User("cinnamon",
                "cinnamon@lambdaschool.local",
                datas);
        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u3 = new User("barnbarn",
                "barnbarn@lambdaschool.local",
                users);
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u4 = new User("puttat",
                "puttat@school.lambda",
                users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u5 = new User("misskitty",
                "misskitty@school.lambda",
                users);
        userService.save(u5);

        // using JavaFaker create a bunch of regular users
        // https://www.baeldung.com/java-faker
        // https://www.baeldung.com/regular-expressions-java

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                new RandomService());
        Faker nameFaker = new Faker(new Locale("en-US"));

        for (int i = 0; i < 25; i++) {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                    r2));
            fakeUser = new User(nameFaker.name()
                    .username(),
                    nameFaker.internet()
                            .emailAddress(),
                    users);
            userService.save(fakeUser);
        }


        /******************************************************************/
        // survey
        Survey survey1 = new Survey();
        surveyService.save(survey1);

        Survey survey2 = new Survey();
        surveyService.save(survey2);

        Survey survey3 = new Survey();
        surveyService.save(survey3);

        Survey survey4 = new Survey();
        surveyService.save(survey4);

        Survey survey5 = new Survey();
        surveyService.save(survey5);

        /*******************************************************************/
        // context
        Context context1 = new Context("Product Leadership", survey1);
        contextService.save(context1);

        Context context2 = new Context("Delivery Management", survey2);
        contextService.save(context2);

        Context context3 = new Context("Project Management", survey3);
        contextService.save(context3);

        Context context4 = new Context("Design Leadership", survey4);
        contextService.save(context4);

        Context context5 = new Context("Engineering Leadership", survey5);
        contextService.save(context5);

        /*******************************************************************/
        // questions

        Question question1 = new Question("Leader Question 1", true, "text", survey1);
        questionService.save(question1);

        Question question2 = new Question("Leader Question 2", true, "text", survey1);
        questionService.save(question2);

        Question question3 = new Question("Leader Question 3", true, "text", survey1);
        questionService.save(question3);

        Question question4 = new Question("Leader Question 4", true, "text", survey2);
        questionService.save(question4);

        Question question5 = new Question("Leader Question 5", true, "text", survey2);
        questionService.save(question5);

        /*******************************************************************/
        // topics
//        ArrayList<TopicUsers> topicUsersArrayList1 = new ArrayList<>();
//        topicUsersArrayList1.add(new TopicUsers(new Topic(), u1));
//        topicUsersArrayList1.add(new TopicUsers(new Topic(), u2));
//        topicUsersArrayList1.add(new TopicUsers(new Topic(), u3));

        Topic topic1 = new Topic("Topic 1", u1, survey1);
        topicService.save(topic1);

        Topic topic2 = new Topic("Topic 2", u1, survey2);
        topicService.save(topic2);

        Topic topic3 = new Topic("Topic 3", u2, survey1);
        topicService.save(topic3);

//        ArrayList<TopicUsers> topicUsersArrayList2 = new ArrayList<>();
//        topicUsersArrayList2.add(new TopicUsers(new Topic(), u3));
//        topicUsersArrayList2.add(new TopicUsers(new Topic(), u4));
//        topicUsersArrayList2.add(new TopicUsers(new Topic(), u5));

        Topic topic4 = new Topic("Topic 4", u2, survey3);
        topicService.save(topic4);

        Topic topic5 = new Topic("Topic 5", u2, survey4);
        topicService.save(topic5);

        Topic topic6 = new Topic("Topic 6", u2, survey1);
        topicService.save(topic6);

    }
}