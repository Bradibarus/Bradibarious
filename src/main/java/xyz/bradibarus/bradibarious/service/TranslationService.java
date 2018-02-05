package xyz.bradibarus.bradibarious.service;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import java.io.InputStream;

public class TranslationService {
    private Translate translate;
    private final String defaultSourceLang = "en";
    private final String defaultTargetLang = "ru";

    public TranslationService() {
        try {
            InputStream in = TranslationService.class.getResourceAsStream("/Bradibarious-39f9377b990e.json");
            ServiceAccountCredentials credential = ServiceAccountCredentials.fromStream(in);
            this.translate = TranslateOptions.newBuilder().setCredentials(credential).build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String translate(String word, String sourceLang, String targetLang){
        Translation translation = translate.translate(word,
                Translate.TranslateOption.sourceLanguage(sourceLang),
                Translate.TranslateOption.targetLanguage(targetLang));

        return translation.getTranslatedText();
    }

    public String translate(String word) {
        return translate(word, defaultSourceLang, defaultTargetLang);
    }
}
