package com.atwj.yygh.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atwj.yygh.mapper.DictMapper;
import com.atwj.yygh.model.cmn.Dict;
import com.atwj.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-01 14:08
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    //读取上传表中的数据，然后写入到数据库中
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
