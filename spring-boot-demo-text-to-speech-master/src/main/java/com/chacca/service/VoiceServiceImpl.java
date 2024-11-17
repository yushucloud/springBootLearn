package com.chacca.service;

import com.chacca.entity.Voice;
import com.chacca.entity.VoiceExample;
import com.chacca.mapper.VoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoiceServiceImpl implements VoiceService {
    @Autowired
    private VoiceMapper mapper;
    @Override
    public List<Voice> QueryAll(int page, int limit) {
        VoiceExample example = new VoiceExample();
        VoiceExample.Criteria criteria = example.createCriteria();
        return null;
    }

    @Override
    public int QueryCount() {
        VoiceExample example = new VoiceExample();
        VoiceExample.Criteria criteria = example.createCriteria();
        criteria.getAllCriteria();
        List<Voice> list = mapper.selectByExample(example);
        int count = 0;
        for (Voice voice : list) {
            count++;
        }
        return count;
    }
}
