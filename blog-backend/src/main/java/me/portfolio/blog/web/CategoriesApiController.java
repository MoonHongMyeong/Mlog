package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.LoginUser;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.service.CategoriesService;
import me.portfolio.blog.web.dto.categories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com", "http://localhost:8000", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/api/v2/user")
@RestController
public class CategoriesApiController {

    private final CategoriesService categoriesService;

    //해당 유저의 카테고리 목록 조회
    @GetMapping("/{userId}/categories")
    public List<CategoriesListResponseDto> getCategories(@PathVariable(name = "userId") Long userId) throws Exception{
        return categoriesService.getCategories(userId);
    }
    //카테고리 등록
    @PostMapping("/{userId}/categories")
    public ResponseEntity<CategoriesResponseDto> addCategory(@LoginUser SessionUser sessionUser, @RequestBody CategoriesSaveRequestDto requestDto) throws Exception{
        CategoriesResponseDto responseDto = categoriesService.addCategory(sessionUser, requestDto.getName());
        return new ResponseEntity<CategoriesResponseDto>(responseDto, HttpStatus.OK);
    }
    //카테고리 수정
    @PutMapping("/{userId}/categories/{categoriesId}")
    public Long updateCategory(@PathVariable(name = "categoriesId") Long categoriesId, @RequestBody CategoriesUpdateRequestDto requestDto) throws Exception{
        return categoriesService.updateCategory(categoriesId, requestDto);
    }
    //카테고리 삭제
    @DeleteMapping("/{userId}/categories/{categoriesId}")
    public Long deleteCategory(@PathVariable(name = "categoriesId") Long categoriesId) throws Exception{
        categoriesService.deleteCategory(categoriesId);
        return categoriesId;
    }

}
