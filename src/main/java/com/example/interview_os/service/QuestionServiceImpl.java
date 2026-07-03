package com.example.interview_os.service;

import com.example.interview_os.dto.QuestionRequestDTO;
import com.example.interview_os.dto.QuestionResponseDTO;
import com.example.interview_os.entity.Question;
import com.example.interview_os.repository.QuestionRepository;
import com.example.interview_os.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionResponseDTO createQuestion(QuestionRequestDTO requestDTO){
        Question question = new Question();
        question.setTitle(requestDTO.getTitle());
        question.setDescription(requestDTO.getDescription());
        question.setTopic(requestDTO.getTopic());
        question.setDifficulty(requestDTO.getDifficulty());
        question.setCompanyTag(requestDTO.getCompanyTag());

        Question saved = questionRepository.save(question);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<QuestionResponseDTO> getAllQuestions(){
        return questionRepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public QuestionResponseDTO getQuestionById(Long id){
        Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question", id));
        return mapToResponseDTO(question);
    }

    @Override
    public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO requestDTO){
        Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question", id));

        question.setTitle(requestDTO.getTitle());
        question.setDescription(requestDTO.getDescription());
        question.setTopic(requestDTO.getTopic());
        question.setDifficulty(requestDTO.getDifficulty());
        question.setCompanyTag(requestDTO.getCompanyTag());

        Question saved = questionRepository.save(question);
        return mapToResponseDTO(saved);

    }

    @Override
    public void deleteQuestion(Long id){
        questionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Question", id));
        questionRepository.deleteById(id);
    }

    private QuestionResponseDTO mapToResponseDTO(Question question){
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setDescription(question.getDescription());
        dto.setTopic(question.getTopic());
        dto.setDifficulty(question.getDifficulty());
        dto.setCompanyTag(question.getCompanyTag());
        dto.setStatus(question.getStatus());
        dto.setCreatedAt(question.getCreatedAt());
        return dto;
    }
}
