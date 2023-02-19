package com.ielts.assistance.service.serviceImpl;

import com.ielts.assistance.service.UtilService;
import org.springframework.stereotype.Service;

@Service
public class UtilServiceImpl implements UtilService {
    static final int OUT = 0;
    static final int IN = 1;

    @Override
    public int countWords(String message)
    {
        int state = OUT;
        int wc = 0;
        int i = 0;

        while (i < message.length())
        {
            if (message.charAt(i) == ' ' || message.charAt(i) == '\n'
                    || message.charAt(i) == '\t')
                state = OUT;
            else if (state == OUT)
            {
                state = IN;
                ++wc;
            }
            ++i;
        }
        return wc;
    }
}
