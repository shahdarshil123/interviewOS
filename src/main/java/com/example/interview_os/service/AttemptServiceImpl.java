package com.example.interview_os.service;

import com.example.interview_os.dto.AttemptRequestDTO;
import com.example.interview_os.dto.AttemptResponseDTO;
import com.example.interview_os.entity.Question;
import com.example.interview_os.entity.QuestionAttempt;
import com.example.interview_os.exception.ResourceNotFoundException;
import com.example.interview_os.mapper.AttemptMapper;
import com.example.interview_os.repository.QuestionAttemptRepository;
import com.example.interview_os.repository.QuestionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttemptServiceImpl implements AttemptService{

    private final QuestionRepository questionRepository;
    private final QuestionAttemptRepository attemptRepository;
    private final AttemptMapper attemptMapper;

    public AttemptServiceImpl(QuestionRepository questionRepository, QuestionAttemptRepository attemptRepository,
                              AttemptMapper attemptMapper){
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.attemptMapper = attemptMapper;
    }

    @Override
    @Transactional
    public AttemptResponseDTO createAttempt(Long questionId, AttemptRequestDTO requestDTO) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question", questionId));
        QuestionAttempt attempt = attemptMapper.toEntity(requestDTO);
        attempt.setQuestion(question);

        QuestionAttempt saved = attemptRepository.save(attempt);

        question.updateFromAttempt(saved);
        questionRepository.save(question);
        
        return attemptMapper.toResponseDTO(saved);
    }

    @Override
    public List<AttemptResponseDTO> getAttemptsByQuestionId(Long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", questionId));

        return attemptRepository.findByQuestionId(questionId).stream().map(attemptMapper::toResponseDTO).collect(Collectors.toList());
    }
}
