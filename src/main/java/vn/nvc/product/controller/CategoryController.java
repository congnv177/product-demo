package vn.nvc.product.controller;

import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nvc.product.base.model.Metadata;
import vn.nvc.product.model.category.CategoryFilterRequest;
import vn.nvc.product.model.category.CategoryResponse;
import vn.nvc.product.model.category.ListCategoryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

    @GetMapping()
    public ListCategoryResponse getAll(CategoryFilterRequest model) {
        List<CategoryResponse> responses = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            val category = new CategoryResponse();
            category.setId(i);
            category.setName("Name thứ " + i);
            category.setDescription("Description thứ " + i);
            responses.add(category);
        }
        val result = responses.stream()
                .filter(c -> c.getId() <= model.getPage() * model.getLimit())
                .filter(c -> c.getId() > (model.getPage() - 1) * model.getLimit())
                .collect(Collectors.toList());

        return new ListCategoryResponse(result, new Metadata(responses.size(), model.getPage(), model.getLimit()));
    }
}
