package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "questionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyService surveyService;

    @Override
    public Question findById(long id) throws ResourceNotFoundException{

        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question " + id + " Not Found"));
    }

    @Override
    public List<Question> findAllQuestions() {
        List<Question> q = new ArrayList<>();

        questionRepository.findAll().iterator().forEachRemaining(q::add);
        return q;
    }

    @Override
    public List<Question> findAllBySurveyId(long surveyId) {
        List<Question> q = new ArrayList<>();

        questionRepository.findAllBySurvey_Surveyid(surveyId).iterator().forEachRemaining(q::add);
        return q;
    }

    @Transactional
    @Override
    public void delete(long id, User user) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question " + id + " Not Found"));

        // delete the question if the associated topic owner is the current user
        if (user.getUserid() == question.getSurvey().getTopic().getOwner().getUserid()) {
            questionRepository.delete(question);
        } else {
            throw new ResourceNotFoundException("Not authorized to perform this action");
        }

    }

    @Transactional
    @Override
    public Question save(Question question) {

        Question newQuestion = new Question();

        if (question.getQuestionid() != 0) {
            Question oldQuestion = questionRepository.findById(question.getQuestionid())
                    .orElseThrow(() -> new ResourceNotFoundException("Question " + question.getQuestionid() + " Not Found"));
            newQuestion.setQuestionid(question.getQuestionid());
        }
        newQuestion.setBody(question.getBody());
        newQuestion.setLeader(question.isLeader());
        newQuestion.setType(question.getType());
        Survey survey = surveyService.findById(question.getSurvey().getSurveyid());
        if (survey != null) {
            newQuestion.setSurvey(survey);
            return questionRepository.save(newQuestion);
        } else {
            throw new ResourceNotFoundException("Survey Id " + question.getSurvey().getSurveyid() + " Not Found");
        }

    }

    @Transactional
    @Override
    public Question update(Question question) {
        return null;
    }


}
