package com.atwj.yygh.service;

import com.atwj.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-01 8:44
 */
@Service
public interface DictService extends IService<Dict> {
    //根据id查询数据
    List<Dict> findChildrenData(Long id);

    //导出数据字典接口
    void exportDictData(HttpServletResponse response);

    //导入数据字典接口
    void importData(MultipartFile file);

    //查询数据字典name值
    String getDictName(String parentDictCode, String value);

    //根据dict_code查询子数据列表
    List<Dict> getDictByDictCode(String dictCode);
}
