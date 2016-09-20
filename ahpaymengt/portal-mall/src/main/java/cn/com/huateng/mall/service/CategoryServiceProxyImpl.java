package cn.com.huateng.mall.service;

import com.aixforce.category.dto.RichCategory;
import com.aixforce.category.model.Category;
import com.aixforce.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-22
 */
@Service("categoryServiceProxy")
public class CategoryServiceProxyImpl implements CategoryService {
    @Autowired
    private CategoryService categoryService;

    @Override
    public void create(Category category) {
        categoryService.create(category);
    }

    @Override
    public void update(Category category) {
        categoryService.update(category);
    }

    @Nullable
    @Override
    public Category findById(Long id) {
        return categoryService.findById(id);
    }

    @Override
    public void delete(Long id) {
        categoryService.delete(id);
    }

    @Nonnull
    @Override
    public List<RichCategory> childrenOf(Long id) {
        List<RichCategory> originResult = categoryService.childrenOf(id);
        return new ArrayList<RichCategory>(originResult);
    }

    @Nonnull
    @Override
    public Set<Long> ancestorsOf(Long id) {
        return categoryService.ancestorsOf(id);
    }
}
