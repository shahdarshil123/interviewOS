package com.example.interview_os.service;

import com.example.interview_os.dto.QuestionRequestDTO;
import com.example.interview_os.dto.QuestionResponseDTO;
import com.example.interview_os.entity.Question;
import com.example.interview_os.entity.User;
import com.example.interview_os.exception.ResourceNotFoundException;
import com.example.interview_os.mapper.QuestionMapper;
import com.example.interview_os.repository.QuestionRepository;
import com.example.interview_os.security.SecurityUtils;
import com.example.interview_os.specification.QuestionSpecification;
import com.example.interview_os.enums.Difficulty;
import com.example.interview_os.enums.Topic;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final SecurityUtils securityUtils;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               QuestionMapper questionMapper,
                               SecurityUtils securityUtils) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public QuestionResponseDTO createQuestion(QuestionRequestDTO requestDTO) {
        User currentUser = securityUtils.getCurrentUser();
        Question question = questionMapper.toEntity(requestDTO);
        question.setUser(currentUser);
        Question saved = questionRepository.save(question);
        return questionMapper.toResponseDTO(saved);
    }

    @Override
    public List<QuestionResponseDTO> getAllQuestions(
            Topic topic, Difficulty difficulty,
            String status, String companyTag) {
        User currentUser = securityUtils.getCurrentUser();

        Specification<Question> spec = Specification
                .where(QuestionSpecification.hasTopic(topic))
                .and(QuestionSpecification.hasDifficulty(difficulty))
                .and(QuestionSpecification.hasStatus(status))
                .and(QuestionSpecification.hasUser(currentUser.getId()));

        return questionRepository.findAll(spec)
                .stream()
                .map(questionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionResponseDTO getQuestionById(Long id) {
        User currentUser = securityUtils.getCurrentUser();
        Question question = questionRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question", id));
        return questionMapper.toResponseDTO(question);
    }

    @Override
    public QuestionResponseDTO updateQuestion(Long id,
                                              QuestionRequestDTO requestDTO) {
        User currentUser = securityUtils.getCurrentUser();
        Question question = questionRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question", id));

        question.setTitle(requestDTO.getTitle());
        question.setDescription(requestDTO.getDescription());
        question.setTopic(requestDTO.getTopic());
        question.setDifficulty(requestDTO.getDifficulty());
        question.setCompanyTag(requestDTO.getCompanyTag());

        Question updated = questionRepository.save(question);
        return questionMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteQuestion(Long id) {
        User currentUser = securityUtils.getCurrentUser();
        Question question = questionRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question", id));
        questionRepository.deleteById(question.getId());
    }
}