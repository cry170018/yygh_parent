package com.xdu.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.cmn.Dict;
import com.xdu.yygh.model.hosp.HospitalSet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict>{//mp自带的service

    //根据数据id查询子数据列表
    List<Dict> findChlidData(Long id);
    //导出数据字典接口
    void exportDictData(HttpServletResponse response);
    //导入数据字典
    void importDictData(MultipartFile file);
    //根据dictcode和value查询
    String getDictName(String s, String value);

    List<Dict> findByDictCode(String dictCode);
}
