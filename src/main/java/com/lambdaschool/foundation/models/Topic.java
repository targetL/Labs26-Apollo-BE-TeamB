package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
public class Topic extends Auditable {

    /**
     * The primary key (long) of the topics table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long topicId;

    /**
     * The title (String). Cannot be null
     */
    @NotNull
    @Column(nullable = false)
    private String title;

    /**
     * One to One relationship between topic and owner (User)
     * one Topic has one owner
     */
    @OneToOne
    @JoinColumn(name = "ownerId")
    private User owner;

    /**
     * The surveyid of the survey assigned to this topic is what is stored in the database.
     * This is the entire survey object!
     * <p>
     * Forms a Many to One relationship between topics and survey.
     * A survey can have many topics.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "surveyId")
    @JsonIgnoreProperties(value = "topic", allowSetters = true)
    private Survey survey;

    /**
     * Forms an One to Many relationship between topic and users.
     * A topic can have many users.
     */
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "topic", allowSetters = true)
    private List<TopicUsers> users = new ArrayList<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public Topic() {
    }

    /**
     * Given the params, create a new topic object
     * <p>
     * topicid is autogenerated
     *
     * @param title      The title (String) of the topic
     * @param owner      The owner (User) of the topic
     * @param survey     The survey (String) connected to the topic
     * @param topicUsers The users (List) of the topic
     */
    public Topic(String title, User owner, Survey survey, List<TopicUsers> topicUsers) {
        setTitle(title);
        setOwner(owner);
        setSurvey(survey);
        for (TopicUsers tu : topicUsers) {
            tu.setTopic(this);
        }
        setUsers(topicUsers);
    }

    /**
     * Getter for topicid
     *
     * @return the topicid (long) of the topic
     */
    public long getTopicId() {
        return topicId;
    }

    /**
     * Setter for topicid. Used primary for seeding data
     *
     * @param topicId the new topicId (long) of the topic
     */
    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    /**
     * Getter for title
     *
     * @return the title (String) of the topic
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title. Used primary for seeding data
     *
     * @param title the new title (long) of the topic
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for owner
     *
     * @return the owner (User) of the topic
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Setter for owner. Used primary for seeding data
     *
     * @param owner the new owner (long) of the topic
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Getter for survey
     *
     * @return the survey (Survey) connected to the topic
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Setter for survey. Used primary for seeding data
     *
     * @param survey the new survey (Survey) connected to the topic
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * Getter for users
     *
     * @return the users (List) of the topic
     */
    public List<TopicUsers> getUsers() {
        return users;
    }

    /**
     * Setter for users. Used primary for seeding data
     *
     * @param users the new users (List) of the topic
     */
    public void setUsers(List<TopicUsers> users) {
        this.users = users;
    }

    /**
     * function to add user to a topic
     *
     * @param user the new user (User) of the topic
     */
    public void addUser(User user) {
        users.add(new TopicUsers(this, user));
    }
}
