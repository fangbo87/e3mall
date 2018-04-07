package jit.wxs.web.content;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jit.wxs.common.pojo.EasyUIDataGrid;
import jit.wxs.common.utils.E3Result;
import jit.wxs.content.service.TbContentService;
import jit.wxs.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 内容Controller
 *
 * @author jitwxs
 * @date 2018/4/4 0:08
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private TbContentService tbContentService;

    @GetMapping("/query/list")
    public EasyUIDataGrid listContent(Long categoryId, int page, int rows) {
        Page<TbContent> contentPage = tbContentService.selectPage(
                new Page<>(page, rows),
                new EntityWrapper<TbContent>().eq("category_id", categoryId));
        List<TbContent> list = contentPage.getRecords();

        EasyUIDataGrid result = new EasyUIDataGrid();
        result.setTotal(contentPage.getTotal());
        result.setRows(list);

        return result;
    }

    @PostMapping("/delete")
    public E3Result deleteContents(Long[] ids) {
        for (Long id : ids) {
            if (id != null) {
                tbContentService.deleteById(id);
            }
        }
        return E3Result.ok();
    }

    @PostMapping("/save")
    public E3Result saveContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        tbContentService.insert(content);
        return E3Result.ok();
    }
}