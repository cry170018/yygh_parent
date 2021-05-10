package com.xdu.hospital.mapper;

import com.xdu.hospital.model.HospitalSet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;

@Mapper
public interface HospitalSetMapper extends BaseMapper<HospitalSet> {

}
