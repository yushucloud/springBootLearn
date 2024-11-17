package com.chacca.mapper;

import com.chacca.entity.Voice;
import com.chacca.entity.VoiceExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface VoiceMapper {
    int countByExample(VoiceExample example);

    int deleteByExample(VoiceExample example);

    int insert(Voice record);

    int insertSelective(Voice record);

    List<Voice> selectByExampleWithRowbounds(VoiceExample example, RowBounds rowBounds);

    List<Voice> selectByExample(VoiceExample example);

    int updateByExampleSelective(@Param("record") Voice record, @Param("example") VoiceExample example);

    int updateByExample(@Param("record") Voice record, @Param("example") VoiceExample example);
}