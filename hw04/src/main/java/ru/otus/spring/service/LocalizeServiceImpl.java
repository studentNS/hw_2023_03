package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;

import java.util.Locale;

@Service
public class LocalizeServiceImpl implements LocalizeService {

    private final MessageSource messageSource;

    private final Locale locale;

    public LocalizeServiceImpl(MessageSource messageSource, AppProps appProps) {
        this.messageSource = messageSource;
        this.locale = appProps.getLocale();
    }

    @Override
    public String getMessage(String code) {
        return this.messageSource.getMessage(code, null, code, this.locale);
    }
}
