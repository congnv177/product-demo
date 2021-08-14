package vn.nvc.product.controller;

import lombok.val;
import org.springframework.web.bind.annotation.*;
import vn.nvc.product.base.model.Metadata;
import vn.nvc.product.model.category.CategoryFilterRequest;
import vn.nvc.product.model.category.CategoryRequest;
import vn.nvc.product.model.category.CategoryResponse;
import vn.nvc.product.model.category.ListCategoryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {
    private final List<CategoryResponse> CATEGORIES = addDefaultData();

    private List<CategoryResponse> addDefaultData() {
        List<CategoryResponse> responses = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            val category = new CategoryResponse();
            category.setId(i);
            category.setName("Name thứ " + i);
            category.setDescription("Description thứ " + i);
            responses.add(category);
        }
        return responses;
    }

    @GetMapping()
    public ListCategoryResponse filter(CategoryFilterRequest model) {
        val result = CATEGORIES.stream()
                .filter(c -> c.getId() <= model.getPage() * model.getLimit())
                .filter(c -> c.getId() > (model.getPage() - 1) * model.getLimit())
                .collect(Collectors.toList());

        return new ListCategoryResponse(result, new Metadata(CATEGORIES.size(), model.getPage(), model.getLimit()));
    }

    @PostMapping()
    public CategoryResponse insert(@RequestBody CategoryRequest req) {
        val lastCategory = CATEGORIES.get(CATEGORIES.size() - 1);
        val newId = lastCategory.getId() + 1;
        val newCategory = new CategoryResponse(newId, req.getName(), req.getDescription());
        CATEGORIES.add(newCategory);
        return newCategory;
    }

    @GetMapping(value = "/{id}")
    public CategoryResponse getById(@PathVariable("id") int id) {
        return CATEGORIES.get(id - 1);
    }

    @PutMapping(value = "/{id}")
    public CategoryResponse update(@PathVariable("id") int id, @RequestBody CategoryRequest req) {
        val category = CATEGORIES.get(id - 1);
        category.setDescription(req.getDescription());
        category.setName(req.getName());
        return category;
    }
}
