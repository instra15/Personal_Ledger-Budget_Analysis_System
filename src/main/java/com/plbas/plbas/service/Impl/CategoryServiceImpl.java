package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Category;
import com.plbas.plbas.exception.BusinessException;
import com.plbas.plbas.repository.CategoryRepository;
import com.plbas.plbas.service.DTO.CategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Response<Void> createCategory(CategoryDTO categoryDTO)
    {
        if (categoryRepository.existsByName(categoryDTO.getName()))
        {
            throw new BusinessException("Category exists.");
        }
        Category category=categoryRepository.findByNameAndDirection(categoryDTO.getName(),categoryDTO.getDirection());
        if (category!=null)
        {
            throw new BusinessException("Category exists.");
        }
        categoryRepository.save(CategoryDTO.converter(categoryDTO));
        return Response.success(null);
    }

    public Response<List<CategoryDTO>> getAllCategory()
    {
        List<CategoryDTO> list=new ArrayList<>();
        list=categoryRepository.findAll().stream().map(CategoryDTO::converter).toList();
        return Response.success(list);
    }

    public Response<Void> deleteCategory(Long id)
    {
        if (!categoryRepository.existsById(id))
        {
            throw new BusinessException("Category does not exists.");
        }
        categoryRepository.deleteById(id);
        return Response.success(null);
    }
}
