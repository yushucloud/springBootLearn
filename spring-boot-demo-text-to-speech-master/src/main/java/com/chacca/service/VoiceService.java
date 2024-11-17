package com.chacca.service;

import com.chacca.entity.Voice;

import java.util.List;

public interface VoiceService {
    List<Voice> QueryAll(int page,int limit);
    int QueryCount();
}
