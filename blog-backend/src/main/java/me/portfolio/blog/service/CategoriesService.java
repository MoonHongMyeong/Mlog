package me.portfolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.categories.CategoriesRepository;
import me.portfolio.blog.domain.categories.CategoriesRepositorySupport;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.categories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoriesRepositorySupport categoriesRepositorySupport;
    private final UserRepository userRepository;

    //카테고리 목록 조회
    @Transactional(readOnly = true)
    public List<CategoriesListResponseDto> getCategories(Long userId) {
        //해당 유저의 카테고리 목록과 그 카테고리 안에 있는 포스트들 가져오기
        User user = userRepository.findById(userId).get();
        return categoriesRepositorySupport.findByUserId(userId).stream()
                .map(category -> new CategoriesListResponseDto(category))
                .collect(Collectors.toList());
    }

    //카테고리 등록
    @Transactional
    public CategoriesResponseDto addCategory(SessionUser sessionUser, String name) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();
        CategoriesSaveRequestDto requestDto = CategoriesSaveRequestDto.builder()
                .name(name)
                .user(user)
                .build();
        Categories category = categoriesRepository.save(requestDto.toEntity());
        CategoriesResponseDto responseDto = new CategoriesResponseDto(category);
        return responseDto;
    }

    //카테고리 수정
    @Transactional
    public Long updateCategory(Long categoriesId, CategoriesUpdateRequestDto requestDto) {
        Categories category = categoriesRepository.findById(categoriesId).orElseThrow(
                () -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다." + categoriesId));
        category.update(requestDto.getName());
        return categoriesId;
    }

    //카테고리 삭제
    @Transactional
    public void deleteCategory(Long categoriesId) {
        Categories category = categoriesRepository.findById(categoriesId).orElseThrow(
                () -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다." + categoriesId));
        categoriesRepository.delete(category);
    }

    //포스트 등록 폼으로 이동 시, PostsController에 구현 함
    //현재 로그인한 유저의 카테고리 정보를 불러옴
    @Transactional(readOnly = true)
    public List<CategoriesResponseDto> getUserCategories(SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();
        return categoriesRepositorySupport.findNameByUserId(user.getId())
                .stream()
                .map(category -> new CategoriesResponseDto(category))
                .collect(Collectors.toList());
    }
}
